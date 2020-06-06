package model.entities;

import java.util.Date;
/**
 * 
 * @author Vitor Lima Caetano
 *
 */
public class Vendedor extends Pessoa {

	private static final long serialVersionUID = 1L;
	
	private String cpf;
	private Double metaMensal;
	
	/**
	 * Método construtor padrão
	 */
	public Vendedor() {
	}

	/**
	 * Método construtor da classe
	 * @param codigo
	 * @param nome
	 * @param telefone
	 * @param email
	 * @param dataCad
	 * @param cpf
	 * @param metaMensal
	 */
	public Vendedor(Integer codigo, String nome, String telefone, String email, Date dataCad, String cpf,
			Double metaMensal) {
		super(codigo, nome, telefone, email, dataCad);
		this.cpf = cpf;
		this.metaMensal = metaMensal;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Double getMetaMensal() {
		return metaMensal;
	}

	public void setMetaMensal(Double metaMensal) {
		this.metaMensal = metaMensal;
	}

	@Override
	public Vendedor tipoPessoa() {
		return this;
	}

	@Override
	public String toString() {
		return super.toString() + " - CPF: " + cpf + " - Meta mensal: R$" + String.format("%.2f", metaMensal);
	}
}
