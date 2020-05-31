package model.dao;

import model.entities.*;

public interface CompraDao {

	void fazerCompra(Compra compra);
	void deletarCompra(Integer cod);
}
