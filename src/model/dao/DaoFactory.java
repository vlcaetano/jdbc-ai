package model.dao;

import db.DB;
import model.dao.impl.*;

public class DaoFactory {

	public static FornecedorDao criarFornecedorDao() {
		return new FornecedorDaoJDBC(DB.getConnection());
	}
	
	public static ClienteDao criarClienteDao() {
		return new ClienteDaoJDBC(DB.getConnection());
	}
	
	public static VendedorDao criarVendedorDao() {
		return new VendedorDaoJDBC(DB.getConnection());
	}
	
	public static ProdutoDao criarProdutoDao() {
		return new ProdutoDaoJDBC(DB.getConnection());
	}
	
	public static CompraDao criarCompraDao() {
		return new CompraDaoJDBC(DB.getConnection());
	}
	
	public static VendaDao criarVendaDao() {
		return new VendaDaoJDBC(DB.getConnection());
	}
}
