package br.com.royal.model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Usuario {
	private int cod;
	private String login;
	private String senha;
	private String tipo;
	private String pergunta;
	private String resposta;
	private Funcionario Funcionario;

	public Funcionario getFuncionario() {
		return Funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		Funcionario = funcionario;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getPergunta() {
		return pergunta;
	}

	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}
	
	public void setSenhaCriptografada(String senha) throws UnsupportedEncodingException, NoSuchAlgorithmException{
		MessageDigest algoritmo = MessageDigest.getInstance("SHA-256");
		byte messageDigest[] = algoritmo.digest(senha.getBytes("UTF-8"));
		
		StringBuilder hexString = new StringBuilder();
		for(byte b: messageDigest){
			hexString.append(String.format("%02X", 0xFF & b));
			
		}
		
		this.senha = hexString.toString();
	}
}
