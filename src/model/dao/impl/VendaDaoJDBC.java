package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.VendaDao;
import model.dao.DaoFactory;
import model.dao.ProdutoDao;
import model.entities.Venda;
import model.entities.Vendedor;
import model.entities.Cliente;
import model.entities.ItemVenda;
import model.entities.Produto;
import model.exceptions.SisComException;

public class VendaDaoJDBC implements VendaDao {

	Connection conn;
	
	public VendaDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void fazerVenda(Venda obj) throws SisComException {
		PreparedStatement st = null;
		
		try {
			//Criar venda
			st = conn.prepareStatement(
					"INSERT INTO venda "
					+ "(CodCliente, CodVendedor, FormaPagamento, DataVenda) "
					+ "VALUES "
					+ "(?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, obj.getCliente().getCodigo());
			st.setInt(2, obj.getVendedor().getCodigo());
			st.setInt(3, obj.getFormaPagto());
			st.setDate(4, new java.sql.Date(obj.getDataVenda().getTime()));
			
			int linhasAfetadas = st.executeUpdate();
			
			if (linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int cod = rs.getInt(1);
					obj.setNumVenda(cod);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Erro! Nenhuma linha foi alterada!");
			}
			
			for (ItemVenda iv : obj.getVendaItens()) {
				criarItemVenda(iv.getProduto().getCodigo(), obj.getNumVenda(), iv.getQuantVenda(), iv.getValorVenda());
				atualizarEstoque(iv.getProduto().decrementarQuantidade(iv.getQuantVenda()), iv.getProduto().getCodigo());
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} catch (SisComException e) {
			throw new SisComException("Erro ao decrementar estoque!");
		} finally {
			DB.closeStatement(st);
		}
		
	}
	
	private void criarItemVenda(Integer codProduto, Integer codVenda, Integer qtd, Double valor) throws SQLException {
		PreparedStatement st = null;
		
		st = conn.prepareStatement(
				"INSERT INTO itemvenda "
				+ "(CodProduto, CodVenda, QuantVenda, valorVenda) "
				+ "VALUES "
				+ "(?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS);
		st.setInt(1, codProduto);
		st.setInt(2, codVenda);
		st.setInt(3, qtd);
		st.setDouble(4, valor);
		
		int linhasAfetadas = st.executeUpdate();
		
		if (linhasAfetadas == 0) {
			throw new DbException("Erro! Nenhuma linha foi alterada!");
		}
		DB.closeStatement(st);
	}

	private void atualizarEstoque(Integer qtd, Integer cod) throws SQLException {
		PreparedStatement st = null;
		st = conn.prepareStatement("UPDATE produto SET Estoque = ? WHERE CodProduto = ?");
		
		st.setInt(1, qtd);
		st.setInt(2, cod);
		
		int linhasAfetadas = st.executeUpdate();
		if (linhasAfetadas == 0) {
			throw new DbException("Erro! Nenhuma linha foi alterada!");
		}
		DB.closeStatement(st);
	}
	
	@Override
	public void deletarVenda(Integer codVenda) throws SisComException {
		ProdutoDao produtoDao = DaoFactory.criarProdutoDao();
		
		PreparedStatement st = null;
		PreparedStatement st2 = null;
		PreparedStatement st3 = null;
		
		ResultSet rs = null;
		
		try {
			//atualizar o estoque
			st = conn.prepareStatement("SELECT CodProduto, QuantVenda FROM itemvenda WHERE CodVenda = ?");
			st.setInt(1, codVenda);
			rs = st.executeQuery();
			
			if (!rs.next()) {
				throw new SisComException("Não foi encontrada a venda para o código");
			}
			
			while (rs.next()) {
				Produto produto = produtoDao.encontrarPorCodigo(rs.getInt("CodProduto"));
				produto.adicionarQuantidade(rs.getInt("QuantVenda"));
				atualizarEstoque(produto.getEstoque(), produto.getCodigo());
			}
			//apagar na tabela itemvenda
			st2 = conn.prepareStatement("DELETE FROM itemvenda WHERE CodVenda = ?");			
			st2.setInt(1, codVenda);			
			st2.executeUpdate();
			//apagar na tabela venda
			st3 = conn.prepareStatement("DELETE FROM venda WHERE CodVenda = ?");			
			st3.setInt(1, codVenda);			
			st3.executeUpdate();
		} catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeStatement(st2);
			DB.closeStatement(st3);
			DB.closeResultSet(rs);
		}		
	}

	@Override
	public List<Venda> encontrarVendas() { //deveria fazer retornando itemvenda para usar na tabela no FX
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT venda.*, vendedor.Nome as VendNome, cliente.Nome as CliNome "
					+ "FROM venda INNER JOIN vendedor "
					+ "ON venda.CodVendedor = vendedor.CodVendedor "
					+ "INNER JOIN cliente "
					+ "ON venda.CodCliente = cliente.CodCliente "
					+ "ORDER BY cliente.Nome");

			rs = st.executeQuery();
			
			List<Venda> list = new ArrayList<>();
			Map<Integer, Vendedor> map = new HashMap<>();
			Map<Integer, Cliente> map2 = new HashMap<>();
			
			while (rs.next()) {
				
				Vendedor vendedor = map.get(rs.getInt("CodVendedor"));
				if (vendedor == null) {
					vendedor = instanciarVendedor(rs);
					map.put(rs.getInt("CodVendedor"), vendedor);
				}
				
				Cliente cliente = map2.get(rs.getInt("CodCliente"));
				if (cliente == null) {
					cliente = instanciarCliente(rs);
					map2.put(rs.getInt("CodCliente"), cliente);
				}
							
				Venda obj = instanciarVenda(rs, vendedor, cliente);		
				list.add(obj);
			}
			return list;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}


	private Venda instanciarVenda(ResultSet rs, Vendedor vendedor, Cliente cliente) throws SQLException {
		Venda venda = new Venda();
		venda.setNumVenda(rs.getInt("CodVenda"));
		venda.setCliente(cliente);
		venda.setVendedor(vendedor);
		venda.setFormaPagto(rs.getInt("FormaPagamento"));
		venda.setDataVenda(rs.getDate("DataVenda"));
		return venda;
	}

	private Vendedor instanciarVendedor(ResultSet rs) throws SQLException {
		Vendedor vendedor = new Vendedor();
		vendedor.setCodigo(rs.getInt("CodVendedor"));
		vendedor.setNome(rs.getString("VendNome"));
		return vendedor;
	}
	
	private Cliente instanciarCliente(ResultSet rs) throws SQLException {
		Cliente cliente = new Cliente();
		cliente.setCodigo(rs.getInt("CodCliente"));
		cliente.setNome(rs.getString("CliNome"));
		return cliente;
	}
}
