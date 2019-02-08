package br.com.royal.modelReports;

import java.util.ArrayList;
import java.util.Calendar;

import br.com.royal.model.Produto;
import br.com.royal.model.Servico;

public class ReciboComanda {
	private String total, subtotalservicos, subtotalprodutos, nomecliente, desconto, 
	dataCompleta, observacoes, produtos, servicos;
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	public String getProdutos() {
		return produtos;
	}
	public void setProdutos(String produtos) {
		this.produtos = produtos;
	}
	public String getServicos() {
		return servicos;
	}
	public void setServicos(String servicos) {
		this.servicos = servicos;
	}
	public String getDesconto() {
		return desconto;
	}
	public void setDesconto(String desconto) {
		this.desconto = desconto;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getSubtotalservicos() {
		return subtotalservicos;
	}
	public void setSubtotalservicos(String subtotalservicos) {
		this.subtotalservicos = subtotalservicos;
	}
	public String getSubtotalprodutos() {
		return subtotalprodutos;
	}
	public void setSubtotalprodutos(String subtotalprodutos) {
		this.subtotalprodutos = subtotalprodutos;
	}
	public String getNomecliente() {
		return nomecliente;
	}
	public void setNomecliente(String nomecliente) {
		this.nomecliente = nomecliente;
	}
	public String getDataCompleta() {
		return dataCompleta;
	}
	public void setDataCompleta(String dataCompleta) {
		this.dataCompleta = setaDataFormatadaCompleta(dataCompleta);
	}
	
	public String setaDataFormatada(String dataFormatada) {
		Calendar dataAtual = Calendar.getInstance();
		String diaDaSemana = "";
		String mes = "";
		switch (dataAtual.get(Calendar.DAY_OF_WEEK)) {
		case 1:
			diaDaSemana = "Domingo";
			break;
		case 2:
			diaDaSemana = "Segunda-Feira";
			break;
		case 3:
			diaDaSemana = "Terça-Feira";
			break;
		case 4:
			diaDaSemana = "Quarta-Feira";
			break;
		case 5:
			diaDaSemana = "Quinta-Feira";
			break;
		case 6:
			diaDaSemana = "Sexta-Feira";
			break;
		case 7:
			diaDaSemana = "Sábado";
			break;
		}

		switch (dataAtual.get(Calendar.MONTH)) {
		case 1:
			mes = "Janeiro";
			break;
		case 2:
			mes = "Fevereiro";
			break;
		case 3:
			mes = "Março";
			break;
		case 4:
			mes = "Abril";
			break;
		case 5:
			mes = "Maio";
			break;
		case 6:
			mes = "Junho";
			break;
		case 7:
			mes = "Julho";
			break;
		case 8:
			mes = "Agosto";
			break;
		case 9:
			mes = "Setembro";
			break;
		case 10:
			mes = "Outubro";
			break;
		case 11:
			mes = "Novembro";
			break;
		case 12:
			mes = "Dezembro";
			break;
		}
		dataFormatada = diaDaSemana + ", " + dataAtual.get(Calendar.DAY_OF_MONTH) + " de " + mes + " de "
				+ dataAtual.get(Calendar.YEAR) + ".";
		return dataFormatada;
	}
	
	public String setaDataFormatadaCompleta(String dataFormatadaCompleta) {
		Calendar dataAtual = Calendar.getInstance();
		String diaDaSemana = "";
		String mes = "";
		switch (dataAtual.get(Calendar.DAY_OF_WEEK)) {
		case 1:
			diaDaSemana = "Domingo";
			break;
		case 2:
			diaDaSemana = "Segunda-Feira";
			break;
		case 3:
			diaDaSemana = "Terça-Feira";
			break;
		case 4:
			diaDaSemana = "Quarta-Feira";
			break;
		case 5:
			diaDaSemana = "Quinta-Feira";
			break;
		case 6:
			diaDaSemana = "Sexta-Feira";
			break;
		case 7:
			diaDaSemana = "Sábado";
			break;
		}

		switch (dataAtual.get(Calendar.MONTH)) {
		case 1:
			mes = "Janeiro";
			break;
		case 2:
			mes = "Fevereiro";
			break;
		case 3:
			mes = "Março";
			break;
		case 4:
			mes = "Abril";
			break;
		case 5:
			mes = "Maio";
			break;
		case 6:
			mes = "Junho";
			break;
		case 7:
			mes = "Julho";
			break;
		case 8:
			mes = "Agosto";
			break;
		case 9:
			mes = "Setembro";
			break;
		case 10:
			mes = "Outubro";
			break;
		case 11:
			mes = "Novembro";
			break;
		case 12:
			mes = "Dezembro";
			break;
		}
		int min = dataAtual.get(Calendar.MINUTE);
		String minutos="";
		if(min<10)
			minutos="0"+min;
		else
			minutos=min+"";
		int hor = dataAtual.get(Calendar.HOUR_OF_DAY);
		String horas="";
		if(hor<10)
			horas="0"+hor;
		else
			horas=hor+"";
		dataFormatadaCompleta = diaDaSemana + ", " + dataAtual.get(Calendar.DAY_OF_MONTH) + " de " + mes + " de "
				+ dataAtual.get(Calendar.YEAR)+" - "+horas+":"+minutos;
		dataAtual.get(Calendar.MINUTE);
		return dataFormatadaCompleta;
	}


}
