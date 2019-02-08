package sevenstarcode.ev.calendar.formatter;

import java.util.Calendar;
import java.util.HashMap;

public class FormatarData {
	private static HashMap<Integer, String> mapMes = 
			new HashMap<Integer, String>();
	static{
		mapMes.put(0, "Janeiro");
		mapMes.put(1, "Fevereiro");
		mapMes.put(2, "Março");
		mapMes.put(3, "Abril");
		mapMes.put(4, "Maio");
		mapMes.put(5, "Junho");
		mapMes.put(6, "Julho");
		mapMes.put(7, "Agosto");
		mapMes.put(8, "Setembro");
		mapMes.put(9, "Outubro");
		mapMes.put(10, "Novembro");
		mapMes.put(11, "Dezembro");
		
	}
	
	public String getCabecalho(Calendar c){
		String cabeca = mapMes.get(c.get(Calendar.MONTH)) +
				" de "+ c.get(Calendar.YEAR);
		return cabeca;
	}
}
