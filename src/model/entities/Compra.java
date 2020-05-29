package model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Compra implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer numCompra;
	private Fornecedor fornecedor;
	private ArrayList<ItemCompra> compraItens;
	private Date dataCompra;
	
	public Compra() {
	}

	public Compra(Integer numCompra, Fornecedor fornecedor, ArrayList<ItemCompra> compraItens, Date dataCompra) {
		this.numCompra = numCompra;
		this.fornecedor = fornecedor;
		this.compraItens = compraItens;
		this.dataCompra = dataCompra;
	}

	public Integer getNumCompra() {
		return numCompra;
	}

	public void setNumCompra(Integer numCompra) {
		this.numCompra = numCompra;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<ItemCompra> getCompraItens() {
		return compraItens;
	}

	public void setCompraItens(ArrayList<ItemCompra> compraItens) {
		this.compraItens = compraItens;
	}

	public Date getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(Date dataCompra) {
		this.dataCompra = dataCompra;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numCompra == null) ? 0 : numCompra.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Compra other = (Compra) obj;
		if (numCompra == null) {
			if (other.numCompra != null)
				return false;
		} else if (!numCompra.equals(other.numCompra))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Compra [numCompra=" + numCompra + ", fornecedor=" + fornecedor + ", compraItens=" + compraItens
				+ ", dataCompra=" + dataCompra + "]";
	}
}
