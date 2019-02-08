package br.com.royal.modelReports;

import java.util.Calendar;

public class RelatorioEntradaProduto {
	private String quantidade, dataEntrada, motivo, lote, validade, fornecedor, produto,
	data, dataCompleta;

	public String getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}

	public String getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(String dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public String getValidade() {
		return validade;
	}

	public void setValidade(String validade) {
		this.validade = validade;
	}

	public String getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = setaDataFormatada(data);
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
