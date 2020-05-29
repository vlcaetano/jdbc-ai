package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.ClienteDao;
import model.entities.Cliente;

public class ClienteDaoJDBC implements ClienteDao {

	Connection conn;
	
	public ClienteDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void inserirCliente(Cliente obj) {
		
		PreparedStatement st = null;
		
		try {
			//Teste para ver se cpf já está cadastrado
			if (encontrarPorCpf(obj.getCpf()) != null) {
				throw new DbIntegrityException("Erro! CPF já cadastrado!");
			}
			
			st = conn.prepareStatement(
					"INSERT INTO cliente "
					+ "(Nome, Telefone, Email, DataCadastro, Cpf, LimiteCredito) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNome());
			st.setString(2, obj.getTelefone());
			st.setString(3, obj.getEmail());
			st.setDate(4, new java.sql.Date(obj.getDataCad().getTime()));
			st.setString(5, obj.getCpf());
			st.setDouble(6, obj.getLimiteCredito());
			
			int linhasAfetadas = st.executeUpdate();
			
			if (linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int cod = rs.getInt(1);
					obj.setCodigo(cod);;
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Erro! Nenhuma linha foi alterada!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} catch (DbIntegrityException e) {
			System.out.println(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deletarCliente(Cliente obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("DELETE FROM cliente WHERE CodCliente = ?");
			
			st.setInt(1, obj.getCodigo());
			
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}	
	}

	@Override
	public List<Cliente> encontrarTodos() {
		List<Cliente> lista = new ArrayList<>();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT * "
					+ "FROM cliente");
			rs = st.executeQuery();
			
			while (rs.next()) {
				Cliente cliente = instanciarCliente(rs);
				lista.add(cliente);
			}
			return lista;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public Cliente encontrarPorCpf(String dado) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT * " 
					+ "FROM cliente "
					+ "WHERE Cpf = ?");
			
			st.setString(1, dado);			
			rs = st.executeQuery();
			
			if (rs.next()) {
				Cliente cliente = instanciarCliente(rs);
				return cliente;
			}			
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Cliente instanciarCliente(ResultSet rs) throws SQLException {
		Cliente obj = new Cliente();
		//CodCliente, Nome, Telefone, Email, DataCadastro, Cpf, LimiteCredito
		obj.setCodigo(rs.getInt("CodCliente"));
		obj.setNome(rs.getString("Nome"));
		obj.setTelefone(rs.getString("Telefone"));
		obj.setEmail(rs.getString("Email"));
		obj.setDataCad(new java.util.Date(rs.getTimestamp("DataCadastro").getTime()));
		obj.setCpf(rs.getString("Cpf"));
		obj.setLimiteCredito(rs.getDouble("LimiteCredito"));		
		return obj;
	}


}
