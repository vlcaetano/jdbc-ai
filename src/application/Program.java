package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.FornecedorDao;
import model.entities.Fornecedor;

public class Program {

	public static void main(String[] args) {
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
		
		lista.sort((x1,x2) -> x1.compareTo(x2));
		lista.forEach(System.out::println);*/
		
		/*System.out.println("\n==== TEST 4: seller insert ====");
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, department);
		sellerDao.insert(newSeller);
		System.out.println("Inserted! New id = " + newSeller.getId());*/
		FornecedorDao fornecedorDao = DaoFactory.criarFornecedorDao();
		
		System.out.println("==== Teste: inserir fornecedor ====");		
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
		fornecedorDao.deletarFornecedor(fornecedorDao.encontrarPorCnpj("40568022000177"));
	}

}
