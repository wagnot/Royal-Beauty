package br.com.royal.model;

public class Cheque {
	private int codCheque;
	private String nomeBanco;
	private int codParcela;
	private String numeroCheque;

	public int getCodCheque() {
		return codCheque;
	}

	public void setCodCheque(int codCheque) {
		this.codCheque = codCheque;
	}

	public String getNomeBanco() {
		return nomeBanco;
	}

	public void setNomeBanco(String nomeBanco) {
		this.nomeBanco = nomeBanco;
	}

	public int getCodParcela() {
		return codParcela;
	}

	public void setCodParcela(int codParcela) {
		this.codParcela = codParcela;
	}

	public String getNumeroCheque() {
		return numeroCheque;
	}

	public void setNumeroCheque(String numeroCheque) {
		this.numeroCheque = numeroCheque;
	}

}
