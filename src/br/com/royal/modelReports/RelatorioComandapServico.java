package br.com.royal.modelReports;

public class RelatorioComandapServico {
	
	public String getNomeServico() {
		return nomeServico;
	}
	public void setNomeServico(String nomeServico) {
		this.nomeServico = nomeServico;
	}
	public float getValorServico() {
		return valorServico;
	}
	public void setValorServico(float valorServico) {
		this.valorServico = valorServico;
	}
	public String getDataComandaServico() {
		return dataComandaServico;
	}
	public void setDataComandaServico(String dataComandaServico) {
		this.dataComandaServico = dataComandaServico;
	}
	public double getValorTotalVendaServico() {
		return valorTotalVendaServico;
	}
	public void setValorTotalVendaServico(double valorTotalVendaServico) {
		this.valorTotalVendaServico = valorTotalVendaServico;
	}
	private String nomeServico;
	private float valorServico;
	private String dataComandaServico;
	private double valorTotalVendaServico;

}
