package model.dao;

import java.util.List;

import model.entities.*;
import model.exceptions.SisComException;
/**
 * 
 * @author Vitor Lima Caetano
 *
 */
public interface CompraDao {
/**
 * Interface CompraDao - Possui os métodos relacionados aos Data Access Object da compra
 */
	void fazerCompra(Compra compra);
	void deletarCompra(Integer cod) throws SisComException;
	List<Compra> encontrarCompras();
	List<Compra> encontrarComprasNomeFornecedor(String nome);
}
