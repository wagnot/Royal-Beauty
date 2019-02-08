package sevenstarcode.ev.calendar.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class EstiloDia {

	private Color corFundo;
	private Color corTexto;
	private Font fonte;
	private Border borda;
	public Color getCorFundo() {
		return corFundo;
	}
	public void setCorFundo(Color corFundo) {
		this.corFundo = corFundo;
	}
	public Color getCorTexto() {
		return corTexto;
	}
	public void setCorTexto(Color corTexto) {
		this.corTexto = corTexto;
	}
	public Font getFonte() {
		return fonte;
	}
	public void setFonte(Font fonte) {
		this.fonte = fonte;
	}
	public void setBorda(Border createLineBorder) {
		borda = createLineBorder;
	}
	
	public Border getBorda() {
		return borda;
	}
	public static EstiloDia getEstiloPadrao(){
		EstiloDia estilo = new EstiloDia();
		estilo.setCorFundo(new Color (221, 220, 220));
		estilo.setFonte(new Font("Century Gothic",0,15));
		estilo.setCorTexto(Color.black);
		estilo.setBorda(BorderFactory.createLineBorder(Color.white));
		return estilo;
	}
}
