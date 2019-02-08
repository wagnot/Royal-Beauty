package br.com.royal.model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login {
	
	private int id;
	private String nome;
	private String user;
	private String senha;
	private String pergunta;
	
	public String getPergunta() {
		return pergunta;
	}
	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}
	private String resposta;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getSenha() {
		return senha;
	}
	public String getResposta() {
		return resposta;
	}
	public void setResposta(String resposta) {
		this.resposta = resposta;
	}
	public void setSenha(String senha) {
		this.senha = senha;
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
