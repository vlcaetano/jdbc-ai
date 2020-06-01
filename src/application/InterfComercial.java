package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controller.Comercial;
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

public class InterfComercial {
	
	private static Comercial objBiz = new Comercial();

	public static void main(String[] args) {
		menu();
	}

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
			System.out.println("8 - ");
			System.out.println("9 - ");			
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
			/*case 5:
				
				break;
			/*case 6:
				
				break;
			/*case 7:
				
				break;
			/*case 8:
				
				break;
			/*case 9:
				
				break;*/
			case 0:
				// Sair
				break;
			default:
				break;
			}
		} while (opcao != 0);

	}

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
				qtd = Console.readInt("Informe a quantidade comprada:");
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

	private static void inserirProduto() {
		String nome = Console.readLine("Nome:");
		Double precoUnitario = Console.readDouble("Preço unitário:");
		Integer estoque = Console.readInt("Estoque:");
		Integer estoqueMinimo = Console.readInt("Estoque mínimo:");
		Date dataCad = new Date();
		
		Produto produto = new Produto(null, nome, precoUnitario, estoque, estoqueMinimo, dataCad);
		
		objBiz.inserirProduto(produto);
		System.out.println("Inserido com sucesso!");
	}

	private static void inserirPessoa() {
		String nome = Console.readLine("Nome:");
		String tel = Console.readLine("Tel:");
		String email = Console.readLine("Email:");
		Date dataCad = new Date();
		
		int selecao;

		selecao = Console.readInt("Deseja efetuar o cadastro de Cliente(1), Vendedor(2) ou Fornecedor(3):");
		if (selecao == 1) {
			String cpf = Console.readLine("CPF:");
			Double limiteCredito = Console.readDouble("Limite de crédito:");
			
			Cliente cliente = new Cliente(null, nome, tel, email, dataCad, cpf, limiteCredito);
			
			objBiz.inserirPessoa(cliente);
		} else if (selecao == 2) {
			String cpf = Console.readLine("CPF:");
			Double metaMensal = Console.readDouble("Meta mensal:");
			
			Vendedor vendedor = new Vendedor(null, nome, tel, email, dataCad, cpf, metaMensal);
			
			objBiz.inserirPessoa(vendedor);
		} else {
			String cnpj = Console.readLine("CNPJ:");
			String nomeContato = Console.readLine("Nome contato:");
			
			Fornecedor fornecedor = new Fornecedor(null, nome, tel, email, dataCad, cnpj, nomeContato);
			
			objBiz.inserirPessoa(fornecedor);
		}
		System.out.println("Inserido com sucesso!");
	}

}
