package model.dao;

import java.util.List;

import model.entities.*;

public interface CompraDao {

	void fazerCompra(Compra compra);
	void deletarCompra(Integer cod);
	List<Compra> encontrarCompras();
}
