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
 * Classe InterfComercial - métodos para usuário utilizar o programa através do console
 */
	private static Comercial objBiz = new Comercial();

	/**
	 * Método main
	 * @param args
	 */
	public static void main(String[] args) {
		menu();
	}

	/**
	 * Método para mostrar o menu ao usuário e chamar os devidos métodos
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
			System.out.println("9 - Estatísticas...");			
			System.out.println("0 - Sair");
			opcao = Console.readInt("Digite a opção desejada:");
			
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
	 * Método para mostrar as estatísticas
	 */
	private static void estatisticas() {
		int selecao;
		List<String> lista = new ArrayList<>();
		do {
			selecao = Console.readInt("Deseja estatíscas dos (1)Fornecedores, (2)Clientes, (3)Vendedores:");
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
	 * Método para listar compras e vendas por período
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
			System.out.println("Data Inválida!");
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
	 * Método para excluir dados do banco
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
					System.out.println("Fornecedor não encontrado!");
				}
				break;
			case 2:
				cpf = Console.readLine("CPF do cliente:");
				Cliente cliente = objBiz.pesquisarCliente(cpf);
				if (cliente != null) {
					objBiz.deletarCliente(cliente);
					System.out.println("Cliente deletado com sucesso!");
				} else {
					System.out.println("Cliente não encontrado!");
				}
				break;
			case 3:
				cpf = Console.readLine("CPF do vendedor:");
				Vendedor vendedor = objBiz.pesquisarVendedor(cpf);
				if (vendedor != null) {
					objBiz.deletarVendedor(vendedor);
					System.out.println("Vendedor deletado com sucesso!");
				} else {
					System.out.println("Vendedor não encontrado!");
				}
				break;
			case 4:
				cod = Console.readInt("Código do produto:");
				Produto produto = objBiz.pesquisarProduto(cod);
				if (produto != null) {
					objBiz.deletarProduto(cod);
					System.out.println("Produto deletado com sucesso!");
				} else {
					System.out.println("Produto não encontrado!");
				}
				break;
			case 5:
				cod = Console.readInt("Código da compra:");
				objBiz.deletarCompra(cod);
				System.out.println("Compra deletada com sucesso!");
				break;
			case 6:
				cod = Console.readInt("Código da venda:");
				objBiz.deletarVenda(cod);
				System.out.println("Venda deletada com sucesso!");
				break;
			}
		} catch (DbIntegrityException e) {
			System.out.println("Não é possível deletar! Há conexão de outras tabelas com o elemento");
		} catch (SisComException e) {
			System.out.println(e.getMensagemErro());
		}
	}

	/**
	 * Método para listar todos as pessoas ou produtos, dependendo da escolha do usuário
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
				selecao2 = Console.readInt("Listar (1)Produtos abaixo do estoque mínimo (2)Todos:");
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
				System.out.println("Não foram encontrados produtos");
			}
			break;
		}
	}

	/**
	 * Método para consultar em específico uma pessoa ou produto, dependendo da escolha do usuário
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
				System.out.println("Fornecedor não encontrado!");
			}
			break;
		case 2:
			cpf = Console.readLine("CPF do cliente:");
			Cliente cliente = objBiz.pesquisarCliente(cpf);
			if (cliente != null) {
				System.out.println(cliente);
			} else {
				System.out.println("Cliente não encontrado!");
			}
			break;
		case 3:
			cpf = Console.readLine("CPF do vendedor:");
			Vendedor vendedor = objBiz.pesquisarVendedor(cpf);
			if (vendedor != null) {
				System.out.println(vendedor);
			} else {
				System.out.println("Vendedor não encontrado!");
			}
			break;
		case 4:
			cod = Console.readInt("Código do produto:");
			Produto produto = objBiz.pesquisarProduto(cod);
			if (produto != null) {
				System.out.println(produto);
			} else {
				System.out.println("Produto não encontrado!");
			}
			break;
		}
	}

	/**
	 * Método para receber dados da venda e inserir no banco de dados
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
				cod = Console.readInt("Informe o código do produto:");
				
				for (ItemVenda iv : listaVenda) {
					if (cod == iv.getProduto().getCodigo()) {
						System.out.println("Erro! Produto já cadastrado");
						teste = false;
						break;
					}
				}
				
				produto = objBiz.pesquisarProduto(cod);
				if (produto == null) {
					teste = false;
					System.out.println("Erro! Produto não encontrado");
				}				
			} while (!teste);

			do {
				qtd = Console.readInt("Informe a quantidade vendida:");
				if (produto.getEstoque() < qtd) {
					System.out.println("Erro! O estoque total do produto é: " + produto.getEstoque());
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
			System.out.println("Venda não pode acontecer, cliente sem limite de crédito");
		} else {
			try {
				objBiz.fazerVenda(novaVenda);
				System.out.println("Venda realizada! Código = " + novaVenda.getNumVenda());
			} catch (SisComException e) {
				System.out.println(e.getMensagemErro());
			}
		}
	}

	/**
	 * Método para receber dados da compra e inserir no banco de dados
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
				cod = Console.readInt("Informe o código do produto:");
				for (ItemCompra ic : listaCompra) {
					if (cod == ic.getProduto().getCodigo()) {
						System.out.println("Erro! Produto já cadastrado na compra");
						teste = false;
						break;
					}
				}
				if (objBiz.pesquisarProduto(cod) == null) {
					teste = false;
					System.out.println("Erro! Produto não encontrado");
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
		
		System.out.println("Compra realizada! Código = " + novaCompra.getNumCompra());
	}

	/**
	 * Método para inserir produto no banco de dados
	 */
	private static void inserirProduto() {
		String nome = Console.readLine("Nome:");
		Double precoUnitario;
		Integer estoque;
		Integer estoqueMinimo;
		do {
			precoUnitario = Console.readDouble("Preço unitário:");
			estoque = Console.readInt("Estoque:");
			estoqueMinimo = Console.readInt("Estoque mínimo:");
			if (precoUnitario <= 0|| estoque <= 0 || estoqueMinimo <= 0) {
				System.out.println("Erro! O preço e/ou os valores de estoque têm de ser positivos");
			}
		} while (precoUnitario <= 0|| estoque <= 0 || estoqueMinimo <= 0);
		
		Date dataCad = new Date();
		Produto produto = new Produto(null, nome, precoUnitario, estoque, estoqueMinimo, dataCad);
		
		objBiz.inserirProduto(produto);
		System.out.println("Inserido com sucesso!");
	}

	/**
	 * Método para inserir uma pessoa no banco de dados
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
				Double limiteCredito = Console.readDouble("Limite de crédito:");
				
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
