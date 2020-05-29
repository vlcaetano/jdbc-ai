package model.dao;

import java.util.List;

import model.entities.Fornecedor;

public interface FornecedorDao {

	void inserirFornecedor(Fornecedor obj);
	void deletarFornecedor(Fornecedor obj);
	List<Fornecedor> encontrarTodos();
	Fornecedor encontrarPorCnpj(String cnpj);
}
