package sevenstarcode.ev.calendar.view;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Dia extends JLabel{

	/**
	 * Serialização
	 */
	private static final long serialVersionUID = 1L;

	private int dia;
	private int mes;
	private int ano;
	private DiaSemana diaSemana;
	private EstiloDia estilo;
	public int getDia() {
		return dia;
	}
	public int getMes() {
		return mes;
	}
	
	public int getAno() {
		return ano;
	}
	
	
	public DiaSemana getDiaSemana() {
		return diaSemana;
	}
	public void setDia(int dia) {
		this.dia = dia;
		setText(dia+"");
		if(dia>0)
			setVisible(true);
		else{
			setVisible(false);
		}
	}
	
	public void setMes(int mes) {
		this.mes = mes;
	}
	
	public void setAno(int ano) {
		this.ano = ano;
	}
	
	public void setDiaSemana(DiaSemana diaSemana) {
		this.diaSemana = diaSemana;
	}
	public EstiloDia getEstilo() {
		return estilo;
	}
	public void setEstilo(EstiloDia estilo) {
		
		if(estilo!=null){
			if(estilo.getCorFundo()!=null){
				this.estilo.setCorFundo(estilo.getCorFundo());
			}
			if(estilo.getCorTexto()!=null){
				this.estilo.setCorTexto(estilo.getCorTexto());
			}
			if(estilo.getFonte()!=null){
				this.estilo.setFonte(estilo.getFonte());
			}
			if(estilo.getBorda()!=null){
				this.estilo.setBorda(estilo.getBorda());
			}
		}
		atualizarEstilo();
	}

	public Dia(){
		setOpaque(true);
		Random r = new Random();
		estilo = new EstiloDia();
		estilo.setCorFundo(new Color(r.nextInt(255),
				r.nextInt(255),r.nextInt(255)));
		estilo.setFonte(new Font("Century Gothic",0,15));
		estilo.setCorTexto(Color.black);
		estilo.setBorda(BorderFactory.createLineBorder(Color.white));
		setHorizontalAlignment(SwingConstants.CENTER);
		atualizarEstilo();
	}
	
	public void atualizarEstilo(){
		setBackground(estilo.getCorFundo());
		setFont(estilo.getFonte());
		setForeground(estilo.getCorTexto());
		setBorder(estilo.getBorda());
	}
	
	
}
