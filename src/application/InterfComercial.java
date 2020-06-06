package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controller.Comercial;
import db.DbIntegrityException;
import model.entities.Cliente;
import model.entities.Compra;
import model.entities.Fornecedor;
import model.entities.ItemCompra;
import model.entities.ItemVenda;
import model.entities.Produto;
import model.entities.Venda;
import model.entities.Vendedor;
import model.exceptions.SisComException;
import util.Console;
/**
 * 
 * @author Vitor Lima Caetano
 *
 */
public class InterfComercial {
/**
 * Classe InterfComercial - m�todos para usu�rio utilizar o programa atrav�s do console
 */
	private static Comercial objBiz = new Comercial();

	/**
	 * M�todo main
	 * @param args
	 */
	public static void main(String[] args) {
		menu();
	}

	/**
	 * M�todo para mostrar o menu ao usu�rio e chamar os devidos m�todos
	 */
	private static void menu() {
		System.out.println("******** Controle de vendas ********");
		int opcao = 0;
		do {
			System.out.println("1 - Inserir pessoa");
			System.out.println("2 - Inserir produto");
			System.out.println("3 - Fazer compra");
			System.out.println("4 - Fazer venda");
			System.out.println("5 - Consultar...");
			System.out.println("6 - Listar...");
			System.out.println("7 - Excluir...");
			System.out.println("8 - Listar por periodo...");
			System.out.println("9 - Estat�sticas...");			
			System.out.println("0 - Sair");
			opcao = Console.readInt("Digite a op��o desejada:");
			
			switch (opcao) {
			case 1:
				inserirPessoa();
				break;
			case 2:
				inserirProduto();
				break;
			case 3:
				fazerCompra();
				break;
			case 4:
				fazerVenda();
				break;
			case 5:
				consultar();
				break;
			case 6:
				listar();
				break;
			case 7:
				excluir();
				break;
			case 8:
				listarPorPeriodo();
				break;
			case 9:
				estatisticas();
				break;
			case 0:
				// Sair
				break;
			default:
				break;
			}
		} while (opcao != 0);

	}

	/**
	 * M�todo para mostrar as estat�sticas
	 */
	private static void estatisticas() {
		int selecao;
		List<String> lista = new ArrayList<>();
		do {
			selecao = Console.readInt("Deseja estat�scas dos (1)Fornecedores, (2)Clientes, (3)Vendedores:");
		} while (selecao < 1 || selecao > 3);
		
		switch (selecao) {
		case 1:
			lista = objBiz.estatisticasFornecedores();
			break;
		case 2:
			lista = objBiz.estatisticasClientes();
			break;
		case 3:
			lista = objBiz.estatisticasVendedores();
			break;
		}
		if (lista == null) {
			System.out.println("Erro, lista vazia!");
		}
		for (String s : lista) {
			System.out.println(s);
		}
	}

	/**
	 * M�todo para listar compras e vendas por per�odo
	 */
	private static void listarPorPeriodo() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date dataInicio = null;
		Date dataFinal = null;
		String nome;
		
		try {
			dataInicio = sdf.parse(Console.readLine("Informe a data inicial:"));
			dataFinal = sdf.parse(Console.readLine("Informe a data final:"));
		} catch (ParseException e) {
			System.out.println("Data Inv�lida!");
		}
		
		int selecao;
		do {
			selecao = Console.readInt("Deseja listar (1)Compras (2)Vendas");
		} while (selecao != 1 && selecao != 2);
		if (selecao == 1) {
			nome = Console.readLine("Nome do fornecedor:");
			List<Compra> listaCompra = objBiz.listarComprasPorFornecedor(dataInicio, dataFinal, nome);
			if (listaCompra == null) {
				System.out.println("Nenhum fornecedor encontrado!");
				return;
			}
			for (Compra c : listaCompra) {
				System.out.println(c);
				
				System.out.println("Produtos comprados:");
				for (ItemCompra ic : c.getCompraItens()) {
					System.out.println("	" + ic);
				}
				System.out.println();
			}
		} else {
			List<Venda> listaVenda = new ArrayList<>();
			do {
				selecao = Console.readInt("Buscar lista por (1)Cliente (2)Vendedor");
			} while (selecao != 1 && selecao != 2);
			
			if (selecao == 1) {
				nome = Console.readLine("Nome do cliente:");
				listaVenda = objBiz.listarVendasPorCliente(dataInicio, dataFinal, nome);
				if (listaVenda == null) {
					System.out.println("Nenhum cliente encontrado!");
					return;
				}
			} else {
				nome = Console.readLine("Nome do vendedor:");
				listaVenda = objBiz.listarVendasPorVendedor(dataInicio, dataFinal, nome);
				if (listaVenda == null) {
					System.out.println("Nenhum vendedor encontrado!");
					return;
				}
			}
			
			listaVenda.sort((c1, c2) -> c2.getDataVenda().compareTo(c1.getDataVenda()));
			
			for (Venda v : listaVenda) {
				System.out.println(v);
				
				System.out.println("Produtos comprados:");
				for (ItemVenda iv : v.getVendaItens()) {
					System.out.println("	" + iv);
				}
				System.out.println();
			}
		}
	}

	/**
	 * M�todo para excluir dados do banco
	 */
	private static void excluir() {
		String cpf, cnpj;
		int cod;
		int selecao;
		do {
			selecao = Console.readInt("Deseja excluir (1)Fornecedor, (2)Cliente, (3)Vendedor, "
					+ "\n(4)Produto, (5)Compra, (6)Venda:");
		} while (selecao < 1 || selecao > 6);
		try {
			switch (selecao) {
			case 1:
				cnpj = Console.readLine("CNPJ do fornecedor:");
				Fornecedor fornecedor = objBiz.pesquisarFornecedor(cnpj);
				if (fornecedor != null) {
					objBiz.deletarFonecedor(fornecedor);
					System.out.println("Fornecedor deletado com sucesso!");
				} else {
					System.out.println("Fornecedor n�o encontrado!");
				}
				break;
			case 2:
				cpf = Console.readLine("CPF do cliente:");
				Cliente cliente = objBiz.pesquisarCliente(cpf);
				if (cliente != null) {
					objBiz.deletarCliente(cliente);
					System.out.println("Cliente deletado com sucesso!");
				} else {
					System.out.println("Cliente n�o encontrado!");
				}
				break;
			case 3:
				cpf = Console.readLine("CPF do vendedor:");
				Vendedor vendedor = objBiz.pesquisarVendedor(cpf);
				if (vendedor != null) {
					objBiz.deletarVendedor(vendedor);
					System.out.println("Vendedor deletado com sucesso!");
				} else {
					System.out.println("Vendedor n�o encontrado!");
				}
				break;
			case 4:
				cod = Console.readInt("C�digo do produto:");
				Produto produto = objBiz.pesquisarProduto(cod);
				if (produto != null) {
					objBiz.deletarProduto(cod);
					System.out.println("Produto deletado com sucesso!");
				} else {
					System.out.println("Produto n�o encontrado!");
				}
				break;
			case 5:
				cod = Console.readInt("C�digo da compra:");
				objBiz.deletarCompra(cod);
				System.out.println("Compra deletada com sucesso!");
				break;
			case 6:
				cod = Console.readInt("C�digo da venda:");
				objBiz.deletarVenda(cod);
				System.out.println("Venda deletada com sucesso!");
				break;
			}
		} catch (DbIntegrityException e) {
			System.out.println("N�o � poss�vel deletar! H� conex�o de outras tabelas com o elemento");
		} catch (SisComException e) {
			System.out.println(e.getMensagemErro());
		}
	}

	/**
	 * M�todo para listar todos as pessoas ou produtos, dependendo da escolha do usu�rio
	 */
	private static void listar() {
		int selecao;
		do {
			selecao = Console.readInt("Deseja listar (1)Fornecedores, (2)Clientes, (3)Vendedores, (4)Produtos:");
		} while (selecao < 1 || selecao > 4);
		
		switch (selecao) {
		case 1:
			List<Fornecedor> listaFornecedor = new ArrayList<>();
			listaFornecedor = objBiz.listarFornecedores();
			//listaFornecedor.sort((x1,x2) -> x1.compareTo(x2)); Listas ordenadas pela query com ORDER BY
			for (Fornecedor f : listaFornecedor) {
				System.out.println(f);
			}
			break;
		case 2:
			List<Cliente> listaCliente = new ArrayList<>();
			listaCliente = objBiz.listarClientes();
			//listaCliente.sort((x1,x2) -> x1.compareTo(x2));
			for (Cliente c : listaCliente) {
				System.out.println(c);
			}
			break;
		case 3:
			List<Vendedor> listaVendedor = new ArrayList<>();
			listaVendedor = objBiz.listarVendedores();
			//listaVendedor.sort((x1,x2) -> x1.compareTo(x2));
			for (Vendedor v : listaVendedor) {
				System.out.println(v);
			}
			break;
		case 4:
			List<Produto> listaProduto = new ArrayList<>();
			int selecao2;
			do {
				selecao2 = Console.readInt("Listar (1)Produtos abaixo do estoque m�nimo (2)Todos:");
			} while (selecao2 != 1 && selecao2 != 2);
			
			if (selecao2 == 1) {
				listaProduto = objBiz.listarAbaixoEstoqueMin();
			} else {
				listaProduto = objBiz.listarProdutos();
			}
			
			if (listaProduto != null) {
				//listaProduto.sort((x1,x2) -> x1.compareTo(x2));
				for (Produto p : listaProduto) {
					System.out.println(p);
				}
			} else {
				System.out.println("N�o foram encontrados produtos");
			}
			break;
		}
	}

	/**
	 * M�todo para consultar em espec�fico uma pessoa ou produto, dependendo da escolha do usu�rio
	 */
	private static void consultar() {
		String cpf, cnpj;
		int cod;
		int selecao;
		do {
			selecao = Console.readInt("Deseja consultar (1)Fornecedor, (2)Cliente, (3)Vendedor, (4)Produto:");
		} while (selecao < 1 || selecao > 4);
		
		switch (selecao) {
		case 1:
			cnpj = Console.readLine("CNPJ do fornecedor:");
			Fornecedor fornecedor = objBiz.pesquisarFornecedor(cnpj);
			if (fornecedor != null) {
				System.out.println(fornecedor);
			} else {
				System.out.println("Fornecedor n�o encontrado!");
			}
			break;
		case 2:
			cpf = Console.readLine("CPF do cliente:");
			Cliente cliente = objBiz.pesquisarCliente(cpf);
			if (cliente != null) {
				System.out.println(cliente);
			} else {
				System.out.println("Cliente n�o encontrado!");
			}
			break;
		case 3:
			cpf = Console.readLine("CPF do vendedor:");
			Vendedor vendedor = objBiz.pesquisarVendedor(cpf);
			if (vendedor != null) {
				System.out.println(vendedor);
			} else {
				System.out.println("Vendedor n�o encontrado!");
			}
			break;
		case 4:
			cod = Console.readInt("C�digo do produto:");
			Produto produto = objBiz.pesquisarProduto(cod);
			if (produto != null) {
				System.out.println(produto);
			} else {
				System.out.println("Produto n�o encontrado!");
			}
			break;
		}
	}

	/**
	 * M�todo para receber dados da venda e inserir no banco de dados
	 */
	private static void fazerVenda() {
		List<ItemVenda> listaVenda = new ArrayList<>();
		int cod;
		int qtd;
		int selecao;
		Produto produto;
		boolean teste;
		do {
			do {
				teste = true;
				cod = Console.readInt("Informe o c�digo do produto:");
				
				for (ItemVenda iv : listaVenda) {
					if (cod == iv.getProduto().getCodigo()) {
						System.out.println("Erro! Produto j� cadastrado");
						teste = false;
						break;
					}
				}
				
				produto = objBiz.pesquisarProduto(cod);
				if (produto == null) {
					teste = false;
					System.out.println("Erro! Produto n�o encontrado");
				}				
			} while (!teste);

			do {
				qtd = Console.readInt("Informe a quantidade vendida:");
				if (produto.getEstoque() < qtd) {
					System.out.println("Erro! O estoque total do produto �: " + produto.getEstoque());
				}
			} while (produto.getEstoque() < qtd);
			
			listaVenda.add(new ItemVenda(produto, qtd));
			
			selecao = Console.readInt("Deseja adicionar mais produtos(1) ou finalizar(2):");
		} while (selecao != 2);
		
		String cpf;
		Vendedor vendedor;
		do {
			cpf = Console.readLine("CPF do vendedor:");
			vendedor = objBiz.pesquisarVendedor(cpf);
		} while (vendedor == null);		
		
		Cliente cliente;
		do {
			cpf = Console.readLine("CPF do cliente:");
			cliente = objBiz.pesquisarCliente(cpf);
		} while (cliente == null);
		
		int formaPagamento;
		do {
			formaPagamento = Console.readInt("Pagamento a (1)vista, (2)prazo:");
		} while (formaPagamento != 1 && formaPagamento != 2);
		
		
		Venda novaVenda = new Venda(null, cliente, vendedor, listaVenda, formaPagamento, new Date());
		
		double valorTotal = 0;
		for (ItemVenda iv : listaVenda) {
			valorTotal += iv.getValorVenda();
		}
		
		if (formaPagamento == 2 && valorTotal > cliente.getLimiteCredito()) {
			System.out.println("Venda n�o pode acontecer, cliente sem limite de cr�dito");
		} else {
			try {
				objBiz.fazerVenda(novaVenda);
				System.out.println("Venda realizada! C�digo = " + novaVenda.getNumVenda());
			} catch (SisComException e) {
				System.out.println(e.getMensagemErro());
			}
		}
	}

	/**
	 * M�todo para receber dados da compra e inserir no banco de dados
	 */
	private static void fazerCompra() {
		List<ItemCompra> listaCompra = new ArrayList<>();
		int selecao = 0;
		int cod;
		int qtd;
		Produto produto;
		boolean teste;
		do {
			do {
				teste = true;
				cod = Console.readInt("Informe o c�digo do produto:");
				for (ItemCompra ic : listaCompra) {
					if (cod == ic.getProduto().getCodigo()) {
						System.out.println("Erro! Produto j� cadastrado na compra");
						teste = false;
						break;
					}
				}
				if (objBiz.pesquisarProduto(cod) == null) {
					teste = false;
					System.out.println("Erro! Produto n�o encontrado");
				}
			} while (!teste);
			
			produto = objBiz.pesquisarProduto(cod);
			
			qtd = Console.readInt("Informe a quantidade comprada:");
			
			listaCompra.add(new ItemCompra(produto, qtd));
			
			selecao = Console.readInt("Deseja adicionar mais produtos(1) ou finalizar(2):");
		} while (selecao != 2);
		
		String cnpj;
		Fornecedor fornecedor;
		do {
			cnpj = Console.readLine("CNPJ do fornecedor:");
			fornecedor = objBiz.pesquisarFornecedor(cnpj);
		} while (fornecedor == null);
		
		Compra novaCompra = new Compra(null, fornecedor, listaCompra, new Date());
		
		objBiz.fazerCompra(novaCompra);
		
		System.out.println("Compra realizada! C�digo = " + novaCompra.getNumCompra());
	}

	/**
	 * M�todo para inserir produto no banco de dados
	 */
	private static void inserirProduto() {
		String nome = Console.readLine("Nome:");
		Double precoUnitario;
		Integer estoque;
		Integer estoqueMinimo;
		do {
			precoUnitario = Console.readDouble("Pre�o unit�rio:");
			estoque = Console.readInt("Estoque:");
			estoqueMinimo = Console.readInt("Estoque m�nimo:");
			if (precoUnitario <= 0|| estoque <= 0 || estoqueMinimo <= 0) {
				System.out.println("Erro! O pre�o e/ou os valores de estoque t�m de ser positivos");
			}
		} while (precoUnitario <= 0|| estoque <= 0 || estoqueMinimo <= 0);
		
		Date dataCad = new Date();
		Produto produto = new Produto(null, nome, precoUnitario, estoque, estoqueMinimo, dataCad);
		
		objBiz.inserirProduto(produto);
		System.out.println("Inserido com sucesso!");
	}

	/**
	 * M�todo para inserir uma pessoa no banco de dados
	 */
	private static void inserirPessoa() {
		String nome = Console.readLine("Nome:");
		String tel = Console.readLine("Tel:");
		String email = Console.readLine("Email:");
		Date dataCad = new Date();
		
		int selecao;

		do {
			selecao = Console.readInt("Deseja efetuar o cadastro de Cliente(1), Vendedor(2) ou Fornecedor(3):");
		} while (selecao < 1 || selecao > 3);
		try {
			if (selecao == 1) {
				String cpf = Console.readLine("CPF:");
				Double limiteCredito = Console.readDouble("Limite de cr�dito:");
				
				Cliente cliente = new Cliente(null, nome, tel, email, dataCad, cpf, limiteCredito);
				
				objBiz.inserirPessoa(cliente);
			} else if (selecao == 2) {
				String cpf = Console.readLine("CPF:");
				Double metaMensal;
				do {
					metaMensal = Console.readDouble("Meta mensal:");
				} while (metaMensal <= 0);

				Vendedor vendedor = new Vendedor(null, nome, tel, email, dataCad, cpf, metaMensal);
				
				objBiz.inserirPessoa(vendedor);
			} else {
				String cnpj = Console.readLine("CNPJ:");
				String nomeContato = Console.readLine("Nome contato:");
				
				Fornecedor fornecedor = new Fornecedor(null, nome, tel, email, dataCad, cnpj, nomeContato);
				
				objBiz.inserirPessoa(fornecedor);
			}
			System.out.println("Inserido com sucesso!");
		} catch (SisComException e) {
			System.out.println(e.getMensagemErro());
		}
	}
}
