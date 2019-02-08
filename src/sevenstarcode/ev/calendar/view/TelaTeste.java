package sevenstarcode.ev.calendar.view;

import java.awt.Color;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;

public class TelaTeste extends JFrame implements IActionEVCalendar{
	EVCalendar calendario;
	EstiloDia e;
	public TelaTeste(){
		setSize(700, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		calendario = new EVCalendar();
		calendario.setBounds(30,30, getWidth()-65, getHeight()-90);
		calendario.setActionEVCalendar(this);
		Dia d = new Dia();
		Calendar dataAtual = Calendar.getInstance();
		int dia = dataAtual.get(Calendar.DAY_OF_MONTH);
		int mes = (dataAtual.get(Calendar.MONTH)+1);
		int ano = dataAtual.get(Calendar.YEAR);
		d.setDia(dia);
		d.setMes(mes);
		d.setAno(ano);
		e = new EstiloDia();
		e.setCorFundo(Color.blue);
		e.setCorTexto(Color.white);
		d.setEstilo(e);
		calendario.addAgendamento(d);
		add(calendario);
		setLayout(null);
		setLocationRelativeTo(null);
		setVisible(true);
		//calendario.mudarCorDaSemana(DiaSemana.DOMINGO, Color.red);
		new Thread(new Runnable() {
			
			public void run() {
				while(true){
					try {
						Thread.sleep(50l);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					calendario.setSize(getWidth()-65, getHeight()-90);
				}
			}
		}).start();
	}
	//doubleClickedDay
	public void doubleClickedDay(Dia dia) {
		Dia d = new Dia();
		d.setDia(dia.getDia());
		d.setMes(dia.getMes());
		d.setAno(dia.getAno());
		e = new EstiloDia();
		e.setCorFundo(Color.red);
		e.setCorTexto(Color.white);
		d.setEstilo(e);
		calendario.addAgendamento(d);
	}
	
	public void clickedDay(Dia dia) {
		Dia d = new Dia();
		d.setDia(dia.getDia());
		d.setMes(dia.getMes());
		d.setAno(dia.getAno());
		calendario.removerAgendamento(d);
	}
	public void mouseEnteredDay(Dia dia) {
		
		if(!calendario.isAgendado(dia)){
			EstiloDia e =dia.getEstilo();
			e.setCorFundo(Color.ORANGE);
			dia.setEstilo(e);
		}
	}
	public void mouseExited(Dia dia) {
		if(!calendario.isAgendado(dia)){
			dia.setEstilo(EstiloDia.getEstiloPadrao());
		}
	}
}
