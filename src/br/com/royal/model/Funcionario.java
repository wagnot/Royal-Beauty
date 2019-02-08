package br.com.royal.model;

import java.util.ArrayList;

public class Funcionario implements Comparable<Funcionario>{
	private int codFuncionario;
	private String nomeFuncionario;
	private String rgFuncionario;
	private String cpfFuncionario;
	private String sexoFuncionario;
	private String dataNascFuncionario;
	private String dataAdmissaoFuncionario;
	private double salarioFuncionario;
	private String logradouroFuncionario;
	private String cepFuncionario;
	private String bairroFuncionario;
	private String cidadeFuncionario;
	private String complementoFuncionario;
	private String estadoFuncionario;
	private String numFuncionario;
	private ArrayList<Telefones> lstTelefones;
	private Object telefone[];
	private ArrayList<ServicoFuncionario> lstServicoFuncionario;
	private String emailFuncionario;
	
	public String getEmailFuncionario() {
		return emailFuncionario;
	}
	public void setEmailFuncionario(String emailFuncionario) {
		this.emailFuncionario = emailFuncionario;
	}
	public ArrayList<ServicoFuncionario> getLstServicoFuncionario() {
		return lstServicoFuncionario;
	}
	public void setLstServicoFuncionario(ArrayList<ServicoFuncionario> lstServicoFuncionario) {
		this.lstServicoFuncionario = lstServicoFuncionario;
	}
	
	public Object[] getTelefone() {
		return telefone;
	}
	public void setTelefone(Object[] telefone) {
		this.telefone = telefone;
	}
	public ArrayList<Telefones> getLstTelefones() {
		return lstTelefones;
	}
	public void setLstTelefones(ArrayList<Telefones> lstTelefones) {
		this.lstTelefones = lstTelefones;
	}
	public int getCodFuncionario() {
		return codFuncionario;
	}
	public void setCodFuncionario(int codFuncionario) {
		this.codFuncionario = codFuncionario;
	}
	public String getNomeFuncionario() {
		return nomeFuncionario;
	}
	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
	}
	public String getRgFuncionario() {
		return rgFuncionario;
	}
	public void setRgFuncionario(String rgFuncionario) {
		this.rgFuncionario = rgFuncionario;
	}
	public String getCpfFuncionario() {
		return cpfFuncionario;
	}
	public void setCpfFuncionario(String cpfFuncionario) {
		this.cpfFuncionario = cpfFuncionario;
	}
	public String getSexoFuncionario() {
		return sexoFuncionario;
	}
	public void setSexoFuncionario(String sexoFuncionario) {
		this.sexoFuncionario = sexoFuncionario;
	}
	public String getDataNascFuncionario() {
		return dataNascFuncionario;
	}
	public void setDataNascFuncionario(String dataNascFuncionario) {
		this.dataNascFuncionario = dataNascFuncionario;
	}
	public String getDataAdmissaoFuncionario() {
		return dataAdmissaoFuncionario;
	}
	public void setDataAdmissaoFuncionario(String dataAdmissaoFuncionario) {
		this.dataAdmissaoFuncionario = dataAdmissaoFuncionario;
	}
	public double getSalarioFuncionario() {
		return salarioFuncionario;
	}
	public void setSalarioFuncionario(double salarioFuncionario) {
		this.salarioFuncionario = salarioFuncionario;
	}
	public String getLogradouroFuncionario() {
		return logradouroFuncionario;
	}
	public void setLogradouroFuncionario(String logradouroFuncionario) {
		this.logradouroFuncionario = logradouroFuncionario;
	}
	public String getCepFuncionario() {
		return cepFuncionario;
	}
	public void setCepFuncionario(String cepFuncionario) {
		this.cepFuncionario = cepFuncionario;
	}
	public String getBairroFuncionario() {
		return bairroFuncionario;
	}
	public void setBairroFuncionario(String bairroFuncionario) {
		this.bairroFuncionario = bairroFuncionario;
	}
	public String getCidadeFuncionario() {
		return cidadeFuncionario;
	}
	public void setCidadeFuncionario(String cidadeFuncionario) {
		this.cidadeFuncionario = cidadeFuncionario;
	}
	public String getComplementoFuncionario() {
		return complementoFuncionario;
	}
	public void setComplementoFuncionario(String complementoFuncionario) {
		this.complementoFuncionario = complementoFuncionario;
	}
	public String getEstadoFuncionario() {
		return estadoFuncionario;
	}
	public void setEstadoFuncionario(String estadoFuncionario) {
		this.estadoFuncionario = estadoFuncionario;
	}
	public String getNumFuncionario() {
		return numFuncionario;
	}
	public void setNumFuncionario(String numFuncionario) {
		this.numFuncionario = numFuncionario;
	}
	
	@Override
	public String toString() {
		return nomeFuncionario;
	}
	
	@Override
	public int compareTo(Funcionario o) {
		return this.nomeFuncionario.compareToIgnoreCase(o.getNomeFuncionario());
	}
	
}
