package br.com.royal.model;

public class Fabricante {
	private int codFabricante;
	private String nomeFabricante;
	public int getCodFabricante() {
		return codFabricante;
	}
	public void setCodFabricante(int codFabricante) {
		this.codFabricante = codFabricante;
	}
	public String getNomeFabricante() {
		return nomeFabricante;
	}
	public void setNomeFabricante(String nomeFabricante) {
		this.nomeFabricante = nomeFabricante;
	}
	public String toString(){
		return getNomeFabricante();
	}
}
