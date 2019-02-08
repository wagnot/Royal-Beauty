package br.com.royal.model;

public class Fiado {
	private int codFiado;
	private String dataAbate;
	private int codParcela;
	private boolean statusFiado;
	private String nomeCliente;
	private double valorParcela;
	private int codComandaProduto;
	private int codComandaServico;
	private int codVendaProduto;
	private int codVendaServico;
	private int codCliente;
	

	public boolean getStatusFiado() {
		return statusFiado;
	}

	public void setStatusFiado(boolean statusFiado) {
		this.statusFiado = statusFiado;
	}

	public int getCodFiado() {
		return codFiado;
	}

	public void setCodFiado(int codFiado) {
		this.codFiado = codFiado;
	}

	public String getDataAbate() {
		return dataAbate;
	}

	public void setDataAbate(String dataAbate) {
		this.dataAbate = dataAbate;
	}

	public int getCodParcela() {
		return codParcela;
	}

	public void setCodParcela(int codParcela) {
		this.codParcela = codParcela;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public double getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(double valorParcela) {
		this.valorParcela = valorParcela;
	}

	public int getCodComandaProduto() {
		return codComandaProduto;
	}

	public void setCodComandaProduto(int codComandaProduto) {
		this.codComandaProduto = codComandaProduto;
	}

	public int getCodComandaServico() {
		return codComandaServico;
	}

	public void setCodComandaServico(int codComandaServico) {
		this.codComandaServico = codComandaServico;
	}

	public int getCodVendaProduto() {
		return codVendaProduto;
	}

	public void setCodVendaProduto(int codVendaProduto) {
		this.codVendaProduto = codVendaProduto;
	}

	public int getCodVendaServico() {
		return codVendaServico;
	}

	public void setCodVendaServico(int codVendaServico) {
		this.codVendaServico = codVendaServico;
	}

	public int getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(int codCliente) {
		this.codCliente = codCliente;
	}

}