package br.com.royal.model;

public class Parcela {
	private int codParcela;
	private int codQuantidadeParcela;
	private int codFormaDePagamento;
	private double valorParcela;
	private int codVendaProduto;
	private int codVendaServico;
	private int codPagamento;
	private String dataVencimento;
	
	public String getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(String dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public int getCodPagamento() {
		return codPagamento;
	}

	public void setCodPagamento(int codPagamento) {
		this.codPagamento = codPagamento;
	}

	public int getCodVendaProduto() {
		return codVendaProduto;
	}

	public void setCodVendaProduto(int codComandaProduto) {
		this.codVendaProduto = codComandaProduto;
	}

	public int getCodVendaServico() {
		return codVendaServico;
	}

	public void setCodVendaServico(int codComandaServico) {
		this.codVendaServico = codComandaServico;
	}

	public int getCodParcela() {
		return codParcela;
	}

	public int getCodFormaDePagamento() {
		return codFormaDePagamento;
	}

	public void setCodFormaDePagamento(int codFormaDePagamento) {
		this.codFormaDePagamento = codFormaDePagamento;
	}

	public void setCodParcela(int codParcela) {
		this.codParcela = codParcela;
	}

	public int getCodQuantidadeParcela() {
		return codQuantidadeParcela;
	}

	public void setCodQuantidadeParcela(int codQuantidadeParcela) {
		this.codQuantidadeParcela = codQuantidadeParcela;
	}

	public double getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(double valorParcela) {
		this.valorParcela = valorParcela;
	}
}
