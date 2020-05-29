package model.exceptions;

public class SisComException extends Exception {
	
	private static final long serialVersionUID = 1L;

	private String nomeProduto;
	private Integer estoque;
	private String mensagemErro;
	
	public SisComException(String nomeProduto, Integer estoque, String mensagemErro) {
		super(mensagemErro);
		this.nomeProduto = nomeProduto;
		this.estoque = estoque;
		//this.mensagemErro = mensagemErro;
	}
	
	public SisComException(String mensagemErro) {
		super(mensagemErro);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public Integer getEstoque() {
		return estoque;
	}

	public String getMensagemErro() {
		return mensagemErro;
	}
}
