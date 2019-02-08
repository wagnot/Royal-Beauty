package br.com.royal.model;

import java.util.ArrayList;

public class ComandaServico {
	private int ComandaServico;
	private ArrayList<Servico> servicos;
	private double subTotalComanda;
	private String dataComanda;
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	private String observacao;

	public int getComandaServico() {
		return ComandaServico;
	}

	public void setComandaServico(int comandaServico) {
		ComandaServico = comandaServico;
	}

	public ArrayList<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(ArrayList<Servico> servicos) {
		this.servicos = servicos;
	}

	public double getSubTotalComanda() {
		return subTotalComanda;
	}

	public void setSubTotalComanda(double subTotalComanda) {
		this.subTotalComanda = subTotalComanda;
	}

	public String getDataComanda() {
		return dataComanda;
	}

	public void setDataComanda(String dataComanda) {
		this.dataComanda = dataComanda;
	}

}
