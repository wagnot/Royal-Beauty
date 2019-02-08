package br.com.royal.model;

public class QuantidadeParcela {
	private int codParcela;
	private String descricaoParcela;

	public int getCodParcela() {
		return codParcela;
	}
	public void setCodParcela(int codParcela) {
		this.codParcela = codParcela;
	}
	public String getDescricaoParcela() {
		return descricaoParcela;
	}
	public void setDescricaoParcela(String descricaoParcela) {
		this.descricaoParcela = descricaoParcela;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return descricaoParcela;
	}
}