package br.com.royal.model;

public class MotivoEntrada {
	private int codMotivoEntrada;
	private String descricao;
	public int getCodMotivoEntrada() {
		return codMotivoEntrada;
	}
	public void setCodMotivoEntrada(int codMotivoEntrada) {
		this.codMotivoEntrada = codMotivoEntrada;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String toString(){
		return getDescricao();
	}
}
