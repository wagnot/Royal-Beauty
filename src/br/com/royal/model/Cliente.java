package br.com.royal.model;

import java.util.ArrayList;

public class Cliente implements Comparable<Cliente>{
	private int cod;
	private String nome, dataNasc, rg, cpf, sexo, logradouro, numCliente, cep, bairro, cidade,
	complemento, estado, email, fotoCliente;
	private ArrayList<Telefones> lstTelefones;
	private Object telefone[];	
	
	public String getFotoCliente() {
		return fotoCliente;
	}
	public void setFotoCliente(String fotoCliente) {
		this.fotoCliente = fotoCliente;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public ArrayList<Telefones> getLstTelefones() {
		return lstTelefones;
	}
	public void setLstTelefones(ArrayList<Telefones> lstTelefones) {
		this.lstTelefones = lstTelefones;
	}
	
	public int getCod() {
		return cod;
	}
	public void setCod(int cod) {
		this.cod = cod;
	}
	
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
	
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	
	public String getNumCliente() {
		return numCliente;
	}
	public void setNumCliente(String numCliente) {
		this.numCliente = numCliente;
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
	
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public Object[] getTelefone() {
		return telefone;
	}
	public void setTelefone(Object[] telefone) {
		this.telefone = telefone;
	}
	@Override
	public int compareTo(Cliente c) {
		return this.nome.compareToIgnoreCase(c.getNome());
	}
	
	@Override
	public String toString() {
		return nome;
	}
	
}
