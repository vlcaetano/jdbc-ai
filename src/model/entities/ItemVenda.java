package model.entities;

import java.io.Serializable;

public class ItemVenda implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Produto produto;
	private Integer quantVenda;
	private Double valorVenda;
	
	public ItemVenda() {
	}

	public ItemVenda(Produto produto, Integer quantVenda, Double valorVenda) {
		this.produto = produto;
		this.quantVenda = quantVenda;
		this.valorVenda = valorVenda;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Integer getQuantVenda() {
		return quantVenda;
	}

	public void setQuantVenda(Integer quantVenda) {
		this.quantVenda = quantVenda;
	}

	public Double getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(Double valorVenda) {
		this.valorVenda = valorVenda;
	}

	@Override
	public String toString() {
		return "ItemVenda [produto=" + produto + ", quantVenda=" + quantVenda + ", valorVenda=" + valorVenda + "]";
	}
}