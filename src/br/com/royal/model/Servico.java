package br.com.royal.model;

public class Servico implements Comparable<Servico>{
	private int codServico;
	private String nomeServico, descricaoServico;
	private double valorServico;
	
	public int getCodServico() {
		return codServico;
	}
	public void setCodServico(int codServico) {
		this.codServico = codServico;
	}
	
	public String getNomeServico() {
		return nomeServico;
	}
	public void setNomeServico(String nomeServico) {
		this.nomeServico = nomeServico;
	}
	
	public String getDescricaoServico() {
		return descricaoServico;
	}
	public void setDescricaoServico(String descricaoServico) {
		this.descricaoServico = descricaoServico;
	}
		
	public double getValorServico() {
		return valorServico;
	}
	public void setValorServico(double valorServico) {
		this.valorServico = valorServico;
	}
	@Override
	public String toString() {
		return nomeServico;
	}
	
	@Override
	public int compareTo(Servico s) {
		return this.nomeServico.compareToIgnoreCase(s.getNomeServico());
	}

}
