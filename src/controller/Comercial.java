package controller;

import java.util.List;

import model.dao.ClienteDao;
import model.dao.CompraDao;
import model.dao.DaoFactory;
import model.dao.FornecedorDao;
import model.dao.ProdutoDao;
import model.dao.VendaDao;
import model.dao.VendedorDao;
import model.entities.*;
import model.exceptions.SisComException;

public class Comercial {

	/*private List<Pessoa> pessoas = new ArrayList<>();
	private List<Produto> produtos = new ArrayList<>();
	private List<Compra> compras;  = new ArrayList<>();
	private List<Venda> vendas  = new ArrayList<>();*/
	
	private FornecedorDao fornecedorDao = DaoFactory.criarFornecedorDao();
	private ClienteDao clienteDao = DaoFactory.criarClienteDao();
	private VendedorDao vendedorDao = DaoFactory.criarVendedorDao();
	private ProdutoDao produtoDao = DaoFactory.criarProdutoDao();
	private CompraDao compraDao = DaoFactory.criarCompraDao();
	private VendaDao vendaDao = DaoFactory.criarVendaDao();
	
	public Comercial() {
	}
	
	public void inserirPessoa(Pessoa pessoa) {
		if(pessoa instanceof Cliente) {
			clienteDao.inserirCliente((Cliente)pessoa);
		}
		if(pessoa instanceof Vendedor) {
			vendedorDao.inserirVendedor((Vendedor)pessoa);
		}
		if(pessoa instanceof Fornecedor) {
			fornecedorDao.inserirFornecedor((Fornecedor)pessoa);
		}
	}
	
	public Fornecedor pesquisarFornecedor(String cnpj) {
		return fornecedorDao.encontrarPorCnpj(cnpj);
	}
	
	public Cliente pesquisarCliente(String cpf) {
		return clienteDao.encontrarPorCpf(cpf);
	}
	
	public Vendedor pesquisarVendedor(String cpf) {
		return vendedorDao.encontrarPorCpf(cpf);
	}
	
	public void inserirProduto(Produto produto) {
		produtoDao.inserirProduto(produto);
	}
	
	public Produto pesquisarProduto(Integer cod) {
		return produtoDao.encontrarPorCodigo(cod);
	}
	
	public void fazerCompra(Compra compra) {
		compraDao.fazerCompra(compra);
	}
	
	public void fazerVenda(Venda venda) throws SisComException {
		vendaDao.fazerVenda(venda);
	}
}
