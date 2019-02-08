package br.com.royal.model;

public class SaidaCaixa {
	private int cod;
	private double valor;
	private int codMotivoSaida;
	private String dataSaidaCaixa;
	private int codCaixa;
	
	public int getCodCaixa() {
		return codCaixa;
	}
	public void setCodCaixa(int codCaixa) {
		this.codCaixa = codCaixa;
	}
	public int getCod() {
		return cod;
	}
	public void setCod(int cod) {
		this.cod = cod;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public int getCodMotivoSaida() {
		return codMotivoSaida;
	}
	public void setCodMotivoSaida(int codMotivoSaida) {
		this.codMotivoSaida = codMotivoSaida;
	}
	public String getDataSaidaCaixa() {
		return dataSaidaCaixa;
	}
	public void setDataSaidaCaixa(String dataSaidaCaixa) {
		this.dataSaidaCaixa = dataSaidaCaixa;
	}
}
