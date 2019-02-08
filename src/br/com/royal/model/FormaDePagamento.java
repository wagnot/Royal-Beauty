package br.com.royal.model;

public class FormaDePagamento {
	private int codFormaDePagamento;
	private String descricaoFormaDePagamento;
	private int statusFormaDePagamento;
	
	public int getCodFormaDePagamento() {
		return codFormaDePagamento;
	}
	public void setCodFormaDePagamento(int codFormaDePagamento) {
		this.codFormaDePagamento = codFormaDePagamento;
	}

	public String getDescricaFormaDePagamento() {
		return descricaoFormaDePagamento;
	}
	public void setDescricaoFormaDePagamento(String descricaoFormaDePagamento) {
		this.descricaoFormaDePagamento = descricaoFormaDePagamento;
	}
	public int getStatusFormaDePagamento() {
		return statusFormaDePagamento;
	}
	public void setStatusFormaDePagamento(int statusFormaDePagamento) {
		this.statusFormaDePagamento = statusFormaDePagamento;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return descricaoFormaDePagamento;
	}
}
