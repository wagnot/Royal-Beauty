package br.com.royal.model;

public class Pagamento {
	private int codPagamento;
	private int codComandaServico;
	private int codComandaProduto;
	private int codCliente;
	private double totalPagamento;
	private double desconto;
	private String dataPagamento;

	public String getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(String dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public double getDesconto() {
		return desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}

	public int getCodPagamento() {
		return codPagamento;
	}

	public void setCodPagamento(int pagamento) {
		this.codPagamento = pagamento;
	}

	public int getCodComandaServico() {
		return codComandaServico;
	}

	public void setCodComandaServico(int codComandaServico) {
		this.codComandaServico = codComandaServico;
	}

	public int getCodComandaProduto() {
		return codComandaProduto;
	}

	public void setCodComandaProduto(int codComandaProduto) {
		this.codComandaProduto = codComandaProduto;
	}

	public int getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(int codCliente) {
		this.codCliente = codCliente;
	}

	public double getTotalPagamento() {
		return totalPagamento;
	}

	public void setTotalPagamento(double totalPagamento) {
		this.totalPagamento = totalPagamento;
	}

}
