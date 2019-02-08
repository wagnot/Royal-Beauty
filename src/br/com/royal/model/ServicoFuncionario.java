package br.com.royal.model;

public class ServicoFuncionario {
	private int codServicoFuncionario;
	private String Servico;
	private String Funcionario;
	
	public int getCodServicoFuncionario() {
		return codServicoFuncionario;
	}
	public void setCodServicoFuncionario(int codServicoFuncionario) {
		this.codServicoFuncionario = codServicoFuncionario;
	}
	public String getServico() {
		return Servico;
	}
	public void setServico(String servico) {
		Servico = servico;
	}
	public String getFuncionario() {
		return Funcionario;
	}
	public void setFuncionario(String funcionario) {
		Funcionario = funcionario;
	}

}
