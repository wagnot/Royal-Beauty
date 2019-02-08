package br.com.royal.model;

public class VendaServico {
	private int codVendaServico;
	private double valorTotalServico;
	private int codCliente;
	private int codFuncionario;
	private int codComandaServico;

	public int getCodVendaServico() {
		return codVendaServico;
	}

	public void setCodVendaServico(int codVendaServico) {
		this.codVendaServico = codVendaServico;
	}

	public double getValorTotalServico() {
		return valorTotalServico;
	}

	public void setValorTotalServico(double valorTotalServico) {
		this.valorTotalServico = valorTotalServico;
	}

	public int getCodFuncionario() {
		return codFuncionario;
	}

	public void setCodFuncionario(int codFuncionario) {
		this.codFuncionario = codFuncionario;
	}

	public int getCodComandaServico() {
		return codComandaServico;
	}

	public void setCodComandaServico(int codComandaServico) {
		this.codComandaServico = codComandaServico;
	}

	public int getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(int codCliente) {
		this.codCliente = codCliente;
	}

}
