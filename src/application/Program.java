package application;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import model.dao.*;
import model.entities.*;

public class Program {

	public static void main(String[] args) {
		FornecedorDao fornecedorDao = DaoFactory.criarFornecedorDao();
		ClienteDao clienteDao = DaoFactory.criarClienteDao();
		VendedorDao vendedorDao = DaoFactory.criarVendedorDao();
		ProdutoDao produtoDao = DaoFactory.criarProdutoDao();
		CompraDao compraDao = DaoFactory.criarCompraDao();
		
		/*Connection conn = DB.getConnection();
		DB.closeConnection();*/
		
		/*List<Pessoa> lista = new ArrayList<>();
		lista.add(new Cliente(null, "Jorge", null, null, null, null, null));
		lista.add(new Cliente(null, "Carlos", null, null, null, null, null));
		lista.add(new Vendedor(null, "Alberto", null, null, null, null, null));
		lista.add(new Cliente(null, "Zaion", null, null, null, null, null));
		lista.add(new Vendedor(null, "Jose", null, null, null, null, null));
		
		lista.forEach(System.out::println);
		
		System.out.println();
		System.out.println();
		
		lista.sort((c1,c2) -> c1.compareTo(c2));
		lista.forEach(System.out::println);*/
		
		/*System.out.println("\n==== TEST 4: seller insert ====");
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, department);
		sellerDao.insert(newSeller);
		System.out.println("Inserted! New id = " + newSeller.getId());*/
		

		/*System.out.println("==== Teste: inserir fornecedor ====");		
		Fornecedor novoFornecedor = new Fornecedor(null, "Leroy Merlin", "33334444", "leroy@email.com", new Date(), "54698598000163", "Vitor");
		Fornecedor novoFornecedor2 = new Fornecedor(null, "ABC da Construção", "33332222", "abc@email.com", new Date(), "40568022000177", "Jorge");
		fornecedorDao.inserirFornecedor(novoFornecedor);
		fornecedorDao.inserirFornecedor(novoFornecedor2);
		//System.out.println("Inserido! Novo cod = " + novoFornecedor.getCodigo());
		
		System.out.println("==== Teste: Encontrar por CNPJ ====");
		Fornecedor fornecedor = fornecedorDao.encontrarPorCnpj("54698598000163");
		System.out.println(fornecedor);
		
		System.out.println("==== Teste: Encontrar todos ====");
		List<Fornecedor> lista = fornecedorDao.encontrarTodos();
		for (Fornecedor f : lista) {
			System.out.println(f);
		}
		
		System.out.println("==== Teste: Deletar dado ====");
		fornecedorDao.deletarFornecedor(fornecedorDao.encontrarPorCnpj("40568022000177"));*/
		
		/*System.out.println("==== Teste: inserir cliente ====");
		Cliente novoCliente = new Cliente(null, "José", "999995555", "jose@email.com", new Date(), "00306099098", 2000.00);
		clienteDao.inserirCliente(novoCliente);
		//System.out.println("Inserido! Novo cod = " + novoCliente.getCodigo());
		
		System.out.println("==== Teste: inserir cliente ====");
		Vendedor novoVendedor = new Vendedor(null, "Carlo", "999996666", "carlo@email.com", new Date(), "23332566059", 2000.00);
		vendedorDao.inserirVendedor(novoVendedor);
		System.out.println("Inserido! Novo cod = " + novoVendedor.getCodigo());*/
		
		
		/*
		System.out.println("==== Teste: inserir produto ====");
		Produto novoProduto = new Produto(null, "Máscara de Tecido Reutilizável Estival", 6.99, 100, 10, new Date());
		produtoDao.inserirProduto(novoProduto);
		System.out.println("Inserido! Novo cod = " + novoProduto.getCodigo());
		
		System.out.println("\n\n==== Teste: encontrar produto ====");
		System.out.println(produtoDao.encontrarPorCodigo(1));
		
		System.out.println("\n\n==== Teste: encontrar todos ====");
		List<Produto> lista = produtoDao.encontrarTodos();
		for (Produto p : lista) {
			System.out.println(p);
		}*/
		
		
		/*System.out.println("\n\n==== Teste: Compra ====");
		List<ItemCompra> listaCompra = new ArrayList<>();
		listaCompra.add(new ItemCompra(produtoDao.encontrarPorCodigo(1), 11));
		listaCompra.add(new ItemCompra(produtoDao.encontrarPorCodigo(2), 22));
		
		Compra novaCompra = new Compra(null, fornecedorDao.encontrarPorCnpj("54698598000163"), listaCompra, new Date());
		
		compraDao.fazerCompra(novaCompra);
		
		System.out.println("Inserido! Novo cod = " + novaCompra.getNumCompra());*/
		
		System.out.println("\n\n==== Teste: Deletar Compra ====");
		compraDao.deletarCompra(2);
	}

}
