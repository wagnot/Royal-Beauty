package br.com.royal.model;

public class Caixa {
	private int cod;
	private boolean status;
	private double valorAtual;
	private String dataCaixa;
	private double valorInicial;

	public String getDataCaixa() {
		return dataCaixa;
	}

	public void setDataCaixa(String dataCaixa) {
		this.dataCaixa = dataCaixa;
	}

	public double getValorInicial() {
		return valorInicial;
	}

	public void setValorInicial(double valorInicial) {
		this.valorInicial = valorInicial;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public double getValorAtual() {
		return valorAtual;
	}

	public void setValorAtual(double valor) {
		this.valorAtual = valor;
	}
}
