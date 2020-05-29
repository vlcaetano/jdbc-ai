package model.dao;

import java.util.List;

import model.entities.Cliente;

public interface ClienteDao {

	void inserirCliente(Cliente obj);
	void deletarCliente(Cliente obj);
	List<Cliente> encontrarTodos();
	Cliente encontrarPorCpf(String cpf);
}
