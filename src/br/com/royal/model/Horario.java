package br.com.royal.model;

import java.util.Date;

public class Horario implements Comparable<Horario>{
	private int codHorario;
	private String horaInicio;
	private String dataHorario;
	private int status;
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDataHorario() {
		return dataHorario;
	}

	public void setDataHorario(String dataHorario) {
		this.dataHorario = dataHorario;
	}

	public int getCodHorario() {
		return codHorario;
	}

	public void setCodHorario(int codHorario) {
		this.codHorario = codHorario;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	
	@Override
	public String toString() {
		return horaInicio;
	}

	@Override
	public int compareTo(Horario h) {
		return this.horaInicio.compareTo(h.horaInicio);
	}
}
