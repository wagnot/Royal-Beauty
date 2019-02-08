package br.com.royal.model;

import java.util.ArrayList;

public class Fornecedor implements Comparable<Fornecedor>{
	private int codFornecedor;
	private String nomeFornecedor;
	private String contatoFornecedor;
	private String cnpjFornecedor;
	private String cpfFornecedor;
	private ArrayList<TelFornecedor> lstTelefones;

	public ArrayList<TelFornecedor> getLstTelefones() {
		return lstTelefones;
	}

	public void setLstTelefones(ArrayList<TelFornecedor> lstTelefones) {
		this.lstTelefones = lstTelefones;
	}

	private Object telefone[];

	public int getCodFornecedor() {
		return codFornecedor;
	}

	public void setCodFornecedor(int codFornecedor) {
		this.codFornecedor = codFornecedor;
	}

	public String getNomeFornecedor() {
		return nomeFornecedor;
	}

	public void setNomeFornecedor(String nomeFornecedor) {
		this.nomeFornecedor = nomeFornecedor;
	}

	public String getContatoFornecedor() {
		return contatoFornecedor;
	}

	public void setContatoFornecedor(String contatoFornecedor) {
		this.contatoFornecedor = contatoFornecedor;
	}

	public String getCnpjFornecedor() {
		return cnpjFornecedor;
	}

	public void setCnpjFornecedor(String cnpjFornecedor) {
		this.cnpjFornecedor = cnpjFornecedor;
	}

	public Object[] getTelefone() {
		return telefone;
	}

	public void setTelefone(Object[] telefone) {
		this.telefone = telefone;
	}

	public String getCpfFornecedor() {
		return cpfFornecedor;
	}

	public void setCpfFornecedor(String cpfFornecedor) {
		this.cpfFornecedor = cpfFornecedor;
	}

	@Override
	public String toString() {
		return getNomeFornecedor();
	}

	@Override
	public int compareTo(Fornecedor f) {
		return this.nomeFornecedor.compareToIgnoreCase(f.getNomeFornecedor());
	}
}
