package model.dao;

import java.util.List;

import model.entities.Produto;
/**
 * 
 * @author Vitor Lima Caetano
 *
 */
public interface ProdutoDao {
/**
 * Interface ProdutoDao - Possui os m�todos relacionados aos Data Access Object do produto
 */
	void inserirProduto(Produto obj);
	void deletarProduto(Integer cod);
	List<Produto> encontrarTodos();
	List<Produto> encontrarAbaixoEstoqueMin();
	Produto encontrarPorCodigo(Integer cod);
}
