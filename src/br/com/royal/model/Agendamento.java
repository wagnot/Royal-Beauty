package br.com.royal.model;

import java.util.Date;

import br.com.royal.controller.ClienteController;

public class Agendamento {
	private int codigo, codigoCliente, codigoHorarioInicio, codigoServico, codigoFuncionario;
	private Date dataAgendamento;
	private String nomeCliente, horarioFim;
	private int status;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getCodigoFuncionario() {
		return codigoFuncionario;
	}
	public void setCodigoFuncionario(int codigoFuncionario) {
		this.codigoFuncionario = codigoFuncionario;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getCodigoCliente() {
		return codigoCliente;
	}
	public void setCodigoCliente(int codigoCliente) {
		this.codigoCliente = codigoCliente;
	}
	public int getCodigoHorarioInicio() {
		return codigoHorarioInicio;
	}
	public void setCodigoHorarioInicio(int codigoHorarioInicio) {
		this.codigoHorarioInicio = codigoHorarioInicio;
	}
	public String getHorarioFim() {
		return horarioFim;
	}
	public void setHorarioFim(String codHorarioFim) {
		this.horarioFim = codHorarioFim;
	}
	public Date getDataAgendamento() {
		return dataAgendamento;
	}
	public void setDataAgendamento(Date dataAgendamento) {
		this.dataAgendamento = dataAgendamento;
	}
	public int getCodigoServico() {
		return codigoServico;
	}
	public void setCodigoServico(int codServico) {
		this.codigoServico = codServico;
	}
}
