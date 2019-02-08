package br.com.royal.model;

import java.util.ArrayList;

public class ComandaProduto {
	private int codComandaProduto;
	private ArrayList<Produto> produtos;
	private double subTotalProduto;
	private String dataComanda;

	public int getCodComandaProduto() {
		return codComandaProduto;
	}

	public void setCodComandaProduto(int codComandaProduto) {
		this.codComandaProduto = codComandaProduto;
	}

	public String getDataComanda() {
		return dataComanda;
	}

	public void setDataComanda(String dataComanda) {
		this.dataComanda = dataComanda;
	}

	public ArrayList<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(ArrayList<Produto> produtos) {
		this.produtos = produtos;
	}

	public double getSubTotalProduto() {
		return subTotalProduto;
	}

	public void setSubTotalProduto(double subTotalProduto) {
		this.subTotalProduto = subTotalProduto;
	}

}
