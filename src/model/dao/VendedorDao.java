package model.dao;

import java.util.List;

import model.entities.Vendedor;

public interface VendedorDao {

	void inserirVendedor(Vendedor obj);
	void deletarVendedor(Vendedor obj);
	List<Vendedor> encontrarTodos();
	Vendedor encontrarPorCpf(String cpf);
}
