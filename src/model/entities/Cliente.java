package model.entities;

import java.util.Date;
/**
 * 
 * @author Vitor Lima Caetano
 *
 */
public class Cliente extends Pessoa {

	private static final long serialVersionUID = 1L;
	
	private String cpf;
	private Double limiteCredito;
	
	/**
	 * M�todo construtor padr�o
	 */
	public Cliente() {
	}
	
	/**
	 * M�todo construtor da classe
	 * @param codigo
	 * @param nome
	 * @param telefone
	 * @param email
	 * @param dataCad
	 * @param cpf
	 * @param limiteCredito
	 */
	public Cliente(Integer codigo, String nome, String telefone, String email, Date dataCad, String cpf,
			Double limiteCredito) {
		super(codigo, nome, telefone, email, dataCad);
		this.cpf = cpf;
		this.limiteCredito = limiteCredito;
	}
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Double getLimiteCredito() {
		return limiteCredito;
	}

	public void setLimiteCredito(Double limiteCredito) {
		this.limiteCredito = limiteCredito;
	}

	@Override
	public Cliente tipoPessoa() {
		return this;
	}

	@Override
	public String toString() {
		return super.toString() + " - CPF: " + cpf + " - Limite de Cr�dito: R$" + String.format("%.2f", limiteCredito);
	}
}
