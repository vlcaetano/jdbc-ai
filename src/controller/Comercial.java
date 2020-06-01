package controller;

import java.util.ArrayList;
import java.util.Date;
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
	
	public List<Fornecedor> listarFornecedores(){
		return fornecedorDao.encontrarTodos();
	}
	
	public List<Cliente> listarClientes(){
		return clienteDao.encontrarTodos();
	}
	
	public List<Vendedor> listarVendedores(){
		return vendedorDao.encontrarTodos();
	}
	
	public void deletarFonecedor(Fornecedor obj) {
		fornecedorDao.deletarFornecedor(obj);
	}
	
	public void deletarCliente(Cliente obj) {
		clienteDao.deletarCliente(obj);
	}
	
	public void deletarVendedor(Vendedor obj) {
		vendedorDao.deletarVendedor(obj);
	}
	
	public void inserirProduto(Produto produto) {
		produtoDao.inserirProduto(produto);
	}
	
	public Produto pesquisarProduto(Integer cod) {
		return produtoDao.encontrarPorCodigo(cod);
	}
	
	public List<Produto> listarProdutos(){
		return produtoDao.encontrarTodos();
	}
	
	public List<Produto> listarAbaixoEstoqueMin(){
		return produtoDao.encontrarAbaixoEstoqueMin();
	}
	
	public void deletarProduto(Integer cod) {
		produtoDao.deletarProduto(cod);
	}
	
	public void fazerCompra(Compra compra) {
		compraDao.fazerCompra(compra);
	}
	
	public void deletarCompra(Integer cod) throws SisComException {
		compraDao.deletarCompra(cod);
	}
	
	public List<Compra> listarCompras(Date dataInicio, Date dataFinal){
		List<Compra> lista = new ArrayList<>();
		List<Compra> listaPorPeriodo = new ArrayList<>();
		lista = compraDao.encontrarCompras();
		for (Compra c : lista) {
			if (c.getDataCompra().compareTo(dataInicio) >= 0 && 
					c.getDataCompra().compareTo(dataFinal) <= 0) {
				listaPorPeriodo.add(c);
			}
		}
		listaPorPeriodo.sort((c1, c2) -> c1.getFornecedor().getNome().toUpperCase()
				.compareTo(c2.getFornecedor().getNome().toUpperCase()));
		listaPorPeriodo.sort((c1, c2) -> c2.getDataCompra().compareTo(c1.getDataCompra()));
		return listaPorPeriodo;
	}
	
	public void fazerVenda(Venda venda) throws SisComException {
		vendaDao.fazerVenda(venda);
	}
	
	public void deletarVenda(Integer cod) throws SisComException {
		vendaDao.deletarVenda(cod);
	}
}
