package br.com.royal.modelReports;

import java.util.ArrayList;
import java.util.Calendar;

public class RelatorioClientes {
	private String nome, dataNasc, rg, cpf, sexo, endereco, cep, bairro, cidade, estado, 
	complemento, email, numero, data, dataCompleta, foto;
	private ArrayList<String> telefone;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(String dataNasc) {
		this.dataNasc = dataNasc;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public ArrayList<String> getTelefone() {
		return telefone;
	}

	public void setTelefone(ArrayList<String> telefone) {
		this.telefone = telefone;
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
	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
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
