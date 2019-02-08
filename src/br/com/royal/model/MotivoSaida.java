package br.com.royal.model;

public class MotivoSaida {
	private int codMotivoSaida;
	private String descricaoMotivoSaida;
	public int getCodMotivoSaida() {
		return codMotivoSaida;
	}
	public void setCodMotivoSaida(int codMotivoSaida) {
		this.codMotivoSaida = codMotivoSaida;
	}
	public String getDescricaoMotivoSaida() {
		return descricaoMotivoSaida;
	}
	public void setDescricaoMotivoSaida(String descricaoMotivoSaida) {
		this.descricaoMotivoSaida = descricaoMotivoSaida;
	}
	public String toString(){
		return getDescricaoMotivoSaida();
	}
}
