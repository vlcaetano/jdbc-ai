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
/**
 * 
 * @author Vitor Lima Caetano
 * 
 */
public class Comercial {
	/**
	 * Classe Comercial - m�todos que tratam das regras do n�gocio e da intera��o com as classes de entidade com o Banco de Dados.
	 */
	
	/*private List<Pessoa> pessoas = new ArrayList<>();
	private List<Produto> produtos = new ArrayList<>();
	private List<Compra> compras;  = new ArrayList<>();
	private List<Venda> vendas  = new ArrayList<>();*/ //Controle feito por banco de dados
	
	private FornecedorDao fornecedorDao = DaoFactory.criarFornecedorDao();
	private ClienteDao clienteDao = DaoFactory.criarClienteDao();
	private VendedorDao vendedorDao = DaoFactory.criarVendedorDao();
	private ProdutoDao produtoDao = DaoFactory.criarProdutoDao();
	private CompraDao compraDao = DaoFactory.criarCompraDao();
	private VendaDao vendaDao = DaoFactory.criarVendaDao();
	
	/**
	 * M�todo construtor padr�o
	 */
	public Comercial() {
	}
	
	/**
	 * M�todo para inserir pessoa no banco de dados
	 * @param pessoa
	 * @throws SisComException
	 */
	public void inserirPessoa(Pessoa pessoa) throws SisComException {
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
	
	/**
	 * M�todo para pesquisar fornecedor por cpf
	 * @param cnpj
	 * @return objeto Fornecedor
	 */
	public Fornecedor pesquisarFornecedor(String cnpj) {
		return fornecedorDao.encontrarPorCnpj(cnpj);
	}
	
	/**
	 * M�todo para pesquisar cliente por cpf
	 * @param cpf
	 * @return objeto Cliente
	 */
	public Cliente pesquisarCliente(String cpf) {
		return clienteDao.encontrarPorCpf(cpf);
	}
	
	/**
	 * M�todo para pesquisar vendedor por cpf
	 * @param cpf
	 * @return objeto Vendedor
	 */
	public Vendedor pesquisarVendedor(String cpf) {
		return vendedorDao.encontrarPorCpf(cpf);
	}
	
	/**
	 * M�todo para listar todos os fornecedores
	 * @return lista de Fornecedores
	 */
	public List<Fornecedor> listarFornecedores(){
		return fornecedorDao.encontrarTodos();
	}
	
	/**
	 * M�todo para listar todos os clientes
	 * @return lista de clietnes
	 */
	public List<Cliente> listarClientes(){
		return clienteDao.encontrarTodos();
	}
	
	/**
	 * M�todo para listar todos os vendedores
	 * @return lista de vendedores
	 */
	public List<Vendedor> listarVendedores(){
		return vendedorDao.encontrarTodos();
	}
	
	/**
	 * M�todo para deletar um fornecedor do banco de dados
	 * @param obj
	 */
	public void deletarFonecedor(Fornecedor obj) {
		fornecedorDao.deletarFornecedor(obj);
	}
	
	/**
	 * M�todo para deletar um cliente do banco de dados
	 * @param obj
	 */
	public void deletarCliente(Cliente obj) {
		clienteDao.deletarCliente(obj);
	}
	
	/**
	 * M�todo para deletar um vendedor do banco de dados
	 * @param obj
	 */
	public void deletarVendedor(Vendedor obj) {
		vendedorDao.deletarVendedor(obj);
	}
	
	/**
	 * M�todo para obter estat�stica de compras agrupado por fornecedor
	 * @return lista de String com os dados
	 */
	public List<String> estatisticasFornecedores() {
		return fornecedorDao.estatisticaFornecedor();
	}
	
	/**
	 * M�todo para obter estat�stica de vendas agrupado por cliente
	 * @return lista de String com os dados
	 */
	public List<String> estatisticasClientes() {
		return clienteDao.estatisticaCliente();
	}
	
	/**
	 * M�todo para obter estat�stica de vendas agrupado por vendedor
	 * @return lista de String com os dados
	 */
	public List<String> estatisticasVendedores(){
		return vendedorDao.estatisticaVendedor();
	}
	
	/**
	 * M�todo para inserir produto no banco de dados
	 * @param produto
	 */
	public void inserirProduto(Produto produto) {
		produtoDao.inserirProduto(produto);
	}
	
	/**
	 * M�todo para pesquisar produto atrav�s do c�digo
	 * @param cod
	 * @return objeto Produto
	 */
	public Produto pesquisarProduto(Integer cod) {
		return produtoDao.encontrarPorCodigo(cod);
	}
	
	/**
	 * M�todo para listar todos os produtos
	 * @return lista de produtos
	 */
	public List<Produto> listarProdutos(){
		return produtoDao.encontrarTodos();
	}
	
	/**
	 * M�todo para listar apenas os produtos abaixo do estoque m�nimo
	 * @return lista de produtos
	 */
	public List<Produto> listarAbaixoEstoqueMin(){
		return produtoDao.encontrarAbaixoEstoqueMin();
	}
	
	/**
	 * M�todo para deletar produto do banco de dados
	 * @param cod
	 */
	public void deletarProduto(Integer cod) {
		produtoDao.deletarProduto(cod);
	}
	
	/**
	 * M�todo para cadastrar uma compra no banco de dados
	 * @param compra
	 */
	public void fazerCompra(Compra compra) {
		compraDao.fazerCompra(compra);
	}
	
	/**
	 * M�todo para deletar uma compra do banco de dados
	 * @param cod
	 * @throws SisComException
	 */
	public void deletarCompra(Integer cod) throws SisComException {
		compraDao.deletarCompra(cod);
	}
	
	/**
	 * M�todo para listar as compras de um determinado fornecedor por per�odo
	 * @param dataInicio
	 * @param dataFinal
	 * @param nome
	 * @return lista de compras
	 */
	public List<Compra> listarComprasPorFornecedor(Date dataInicio, Date dataFinal, String nome){
		List<Compra> lista = compraDao.encontrarComprasNomeFornecedor(nome);
		if (lista == null) {
			return null;
		}
		
		List<Compra> listaPorPeriodo = new ArrayList<>();
		for (Compra c : lista) {
			if (c.getDataCompra().compareTo(dataInicio) >= 0 && 
					c.getDataCompra().compareTo(dataFinal) <= 0) {
				listaPorPeriodo.add(c);
			}
		}
		listaPorPeriodo.sort((c1, c2) -> c2.getDataCompra().compareTo(c1.getDataCompra()));
		return listaPorPeriodo;
	}
	
	/**
	 * M�todo para cadastrar uma venda no banco de dados
	 * @param venda
	 * @throws SisComException
	 */
	public void fazerVenda(Venda venda) throws SisComException {
		vendaDao.fazerVenda(venda);
	}
	
	/**
	 * M�todo para deletar uma venda do banco de dados
	 * @param cod
	 * @throws SisComException
	 */
	public void deletarVenda(Integer cod) throws SisComException {
		vendaDao.deletarVenda(cod);
	}
	
	/**
	 * M�todo para listar as vendas de um determinado cliente por per�odo
	 * @param dataInicio
	 * @param dataFinal
	 * @param nome
	 * @return lista de clientes
	 */
	public List<Venda> listarVendasPorCliente(Date dataInicio, Date dataFinal, String nome){
		List<Venda> lista = vendaDao.encontrarVendasNomeCliente(nome);
		if (lista == null) {
			return null;
		}
		
		List<Venda> listaPorPeriodo = new ArrayList<>();
		for (Venda v : lista) {
			if (v.getDataVenda().compareTo(dataInicio) >= 0 && 
					v.getDataVenda().compareTo(dataFinal) <= 0) {
				listaPorPeriodo.add(v);
			}
		}
		listaPorPeriodo.sort((c1, c2) -> c2.getDataVenda().compareTo(c1.getDataVenda()));
		return listaPorPeriodo;
	}
	
	/**
	 * M�todo para listar as vendas de um determinado vendedor por per�odo
	 * @param dataInicio
	 * @param dataFinal
	 * @param nome
	 * @return lista de vendedores
	 */
	public List<Venda> listarVendasPorVendedor(Date dataInicio, Date dataFinal, String nome){
		List<Venda> lista = vendaDao.encontrarVendasNomeVendedor(nome);
		if (lista == null) {
			return null;
		}
		
		List<Venda> listaPorPeriodo = new ArrayList<>();
		for (Venda v : lista) {
			if (v.getDataVenda().compareTo(dataInicio) >= 0 && 
					v.getDataVenda().compareTo(dataFinal) <= 0) {
				listaPorPeriodo.add(v);
			}
		}
		listaPorPeriodo.sort((c1, c2) -> c2.getDataVenda().compareTo(c1.getDataVenda()));
		return listaPorPeriodo;
	}
}
