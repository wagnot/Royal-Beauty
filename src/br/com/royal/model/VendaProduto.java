package br.com.royal.model;

public class VendaProduto {
	private int codVendaProduto;
	private double valorTotalProduto;
	private int codCliente;
	private int codFuncionarioProduto;
	private int codComandaProduto;

	public int getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(int codCliente) {
		this.codCliente = codCliente;
	}

	public int getCodVendaProduto() {
		return codVendaProduto;
	}

	public void setCodVendaProduto(int codVendaProduto) {
		this.codVendaProduto = codVendaProduto;
	}

	public double getValorTotalProduto() {
		return valorTotalProduto;
	}

	public void setValorTotalProduto(double valorTotalProduto) {
		this.valorTotalProduto = valorTotalProduto;
	}

	public int getCodFuncionarioProduto() {
		return codFuncionarioProduto;
	}

	public void setCodFuncionarioProduto(int codFuncionarioProduto) {
		this.codFuncionarioProduto = codFuncionarioProduto;
	}

	public int getCodComandaProduto() {
		return codComandaProduto;
	}

	public void setCodComandaProduto(int codComandaProduto) {
		this.codComandaProduto = codComandaProduto;
	}

}
