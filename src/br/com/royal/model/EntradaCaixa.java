package br.com.royal.model;

public class EntradaCaixa {
	private int cod;
	private double valor;
	private int codMotivoEntrada;
	private String dataEntradaCaixa;
	private int codFormaDePagamento;
	private int codCaixa;
	
	public int getCodCaixa() {
		return codCaixa;
	}
	public void setCodCaixa(int codCaixa) {
		this.codCaixa = codCaixa;
	}
	public int getCodFormaDePagamento() {
		return codFormaDePagamento;
	}
	public void setCodFormaDePagamento(int codFormaDePagamento) {
		this.codFormaDePagamento = codFormaDePagamento;
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
	public int getCodMotivoEntrada() {
		return codMotivoEntrada;
	}
	public void setCodMotivoEntrada(int codMotivoEntrada) {
		this.codMotivoEntrada = codMotivoEntrada;
	}
	public String getDataEntradaCaixa() {
		return dataEntradaCaixa;
	}
	public void setDataEntradaCaixa(String dataEntradaCaixa) {
		this.dataEntradaCaixa = dataEntradaCaixa;
	}
}
