package sevenstarcode.ev.calendar.view;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.SwingConstants;

public class CabecalhoSemana {

	private  List<Dia> titulos;
	
	public CabecalhoSemana(){
		titulos = new ArrayList<Dia>();
		for(int i=0;i<7;i++){
			Dia lbld = new Dia();
			Random r = new Random();
			lbld.setBackground(new Color(r.nextInt(255),
					r.nextInt(255),r.nextInt(255)));
			lbld.setFont(new Font("Century Gothic",0,15));
			lbld.setHorizontalAlignment(SwingConstants.CENTER);
			titulos.add(lbld);
		}
		titulos.get(0).setText("Seg");
		titulos.get(1).setText("Ter");
		titulos.get(2).setText("Qua");
		titulos.get(3).setText("Qui");
		titulos.get(4).setText("Sex");
		titulos.get(5).setText("Sab");
		titulos.get(6).setText("Dom");
	}
	
	public void setCorFundo(Color cor){
		for(Dia d : titulos){
			d.setBackground(cor);
		}
	}
	public void setCorTexto(Color cor){
		for(Dia d : titulos){
			d.setForeground(cor);
		}
	}
	public void setCorFonte(Font fonte){
		for(Dia d : titulos){
			d.setFont(fonte);
		}
	}
	public List<Dia> getTitulos() {
		
		return titulos;
	}
	
}
