package br.com.royal.controller;

import java.util.Calendar;

import javax.swing.DefaultListModel;

public class Validacoes {
	
	public int getCaretPositionData(String txt){
		if(txt.replaceAll("[/_]", "").length()<=1)
			return txt.replaceAll("[_/]", "").length();
		else if(txt.replaceAll("[/_]", "").length()<=3)
			return (txt.replaceAll("[_/]", "").length()+1);
		else
			return (txt.replaceAll("[_/]", "").length()+2);
	}

	public int getCaretPositionTelefone(String txt){
		if(txt.replaceAll("[()_ -]", "").length()<=1)
			return (txt.replaceAll("[()_ -]", "").length()+1);
		else if(txt.replaceAll("[()_ -]", "").length()<=5)
			return (txt.replaceAll("[() _-]", "").length()+3);
		else
			return (txt.replaceAll("[() _-]", "").length()+4);
	}
	
	public int getCaretPositionCep(String txt){
		if(txt.replaceAll("[_ -]", "").length()<=4)
			return (txt.replaceAll("[_ -]", "").length());
		else
			return (txt.replaceAll("[_ -]", "").length()+1);
	}
	
	public int getCaretPositionCpf(String txt){
		if(txt.replaceAll("[_.-]", "").length()<=2)	
			return (txt.replaceAll("[_.-]", "").length());
		else if(txt.replaceAll("[_.-]", "").length()<=5)
			return (txt.replaceAll("[_.-]", "").length()+1);
		else if(txt.replaceAll("[_.-]", "").length()<=8)	
			return (txt.replaceAll("[_.-]", "").length()+2);
		else 
			return (txt.replaceAll("[_.-]", "").length()+3);
	}
	
	public int getCaretPositionCnpj(String txt) {
		if(txt.replaceAll("[_./-]", "").length()<2)	
			return txt.replaceAll("[_./-]", "").length();
		else if(txt.replaceAll("[_./-]", "").length()<=4)
			return txt.replaceAll("[_./-]", "").length()+1;
		else if(txt.replaceAll("[_./-]", "").length()<=7)
			return txt.replaceAll("[_./-]", "").length()+2;
		else if(txt.replaceAll("[_./-]", "").length()<=11)
			return txt.replaceAll("[_./-]", "").length()+3;
		else
			return txt.replaceAll("[_./-]", "").length()+4;	
	}
	
	public boolean validaCnpj(String cnpj) {
		cnpj = cnpj.replaceAll("[/ ._-]", "");
		if (!cnpj.equals("00000000000000") && !cnpj.equals("11111111111111")
				&& !cnpj.equals("22222222222222") && !cnpj.equals("33333333333333")
				&& !cnpj.equals("44444444444444") && !cnpj.equals("55555555555555")
				&& !cnpj.equals("66666666666666") && !cnpj.equals("77777777777777")
				&& !cnpj.equals("88888888888888") && !cnpj.equals("99999999999999")) {
			int digito1 = 0, digito2 = 0, m = 5;
			for (int i = 1; i <= cnpj.length() - 2; i++) {
				digito1 += Integer.parseInt(cnpj.substring(i - 1, i)) * m;
				if (m == 2)
					m = 9;
				else
					m--;
			}
			digito1 = digito1 % 11;
			if (digito1 < 2)
				digito1 = 0;
			else
				digito1 = 11 - digito1;

			m = 6;
			for (int i = 1; i <= cnpj.length() - 1; i++) {
				digito2 += Integer.parseInt(cnpj.substring(i - 1, i)) * m;
				if (m == 2)
					m = 9;
				else
					m--;
			}
			digito2 = digito2 % 11;
			if (digito2 < 2)
				digito2 = 0;
			else
				digito2 = 11 - digito2;
			if (digito1 == Integer
					.parseInt(cnpj.charAt(cnpj.length() - 2) + "")
					&& digito2 == Integer
							.parseInt(cnpj.charAt(cnpj.length() - 1) + ""))
				return true;
		}
		return false;
	}

	public static boolean validaCpf(String cpf) {
		cpf = cpf.replaceAll("[/. _-]", "");
		if (!cpf.equals("00000000000") && !cpf.equals("11111111111")
				&& !cpf.equals("22222222222") && !cpf.equals("33333333333")
				&& !cpf.equals("44444444444") && !cpf.equals("55555555555")
				&& !cpf.equals("66666666666") && !cpf.equals("77777777777")
				&& !cpf.equals("88888888888") && !cpf.equals("99999999999")
				&& cpf.length()==11)	{
			int digito1 = 0, digito2 = 0;
			for (int i = 0; i < cpf.length() - 2; i++) {
				digito1 += Integer.parseInt(cpf.charAt(i) + "") * (10 - i);
			}
			if (11 - (digito1 % 11) > 9) {
				digito1 = 0;
			} else {
				digito1 = 11 - (digito1 % 11);
			}

			for (int j = 0; j < cpf.length() - 1; j++) {
				digito2 += Integer.parseInt(cpf.charAt(j) + "") * (11 - j);
			}
			if (11 - (digito2 % 11) > 9) {
				digito2 = 0;
			} else {
				digito2 = 11 - (digito2 % 11);
			}
			if (digito1 == Integer.parseInt(cpf.charAt(cpf.length() - 2) + "")
					&& digito2 == Integer.parseInt(cpf.charAt(cpf.length() - 1)
							+ ""))
				return true;
		}
		return false;
	}

	public static boolean validaPIS(String pis) {
		pis = pis.replaceAll("[/. _-]", "");
		if (!pis.equals("00000000000") && !pis.equals("11111111111")
				&& !pis.equals("22222222222") && !pis.equals("33333333333")
				&& !pis.equals("44444444444") && !pis.equals("5555555555")
				&& !pis.equals("66666666666") && !pis.equals("77777777777")
				&& !pis.equals("88888888888") && !pis.equals("99999999999")) {
			int digito1 = 0, m = 3;
			for (int i = 0; i < pis.length() - 1; i++) {
				digito1 += Integer.parseInt(pis.charAt(i) + "") * m;
				if (m == 2)
					m = 9;
				else
					m--;
			}
			if (11 - (digito1 % 11) > 9) {
				digito1 = 0;
			} else {
				digito1 = 11 - (digito1 % 11);
			}
			if (digito1 == Integer.parseInt(pis.charAt(pis.length() - 1) + ""))
				return true;
		}
		return false;

	}

	public static boolean verificaTelefoneDuplicado(DefaultListModel model,
			String tel) {
		for (Object ob : model.toArray()) {
			if (!tel.replace("_", "").equals(ob.toString())) {
				return false;
			}
		}
		return true;
	}

	public static boolean verificaDDITelefone(String tel) {
		tel = tel.replaceAll("[-._()]", "").substring(0, 2);
		if (tel.replace(" ", "").length() == 2)
			return true;
		return false;
	}

	public static boolean validaData(String data) {
		try {
			int dia = Integer.parseInt(data.substring(0, 2)), mes = Integer
					.parseInt(data.substring(2, 4)), ano = Integer
					.parseInt(data.substring(4, 8));
			if (dia > 0 && dia <= 31 && mes > 0 && mes <= 12 && ano > 1900) {
				if (verificaData(dia, mes, ano))
					if (((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia <= 30)
							|| ((mes == 1 || mes == 3 || mes == 5 || mes == 7
									|| mes == 8 || mes == 10 || mes == 12) && dia <= 31)
							|| (mes == 2 && (((ano % 4) == 0 && dia <= 29) || (dia <= 28)))) {
						return true;
					}

			}
		} catch (Exception e) {
		}
		return false;
	}

	private static boolean verificaData(int dia, int mes, int ano) {
		Calendar date = Calendar.getInstance();
		if (ano <= date.get(Calendar.YEAR))
			if (ano < date.get(Calendar.YEAR))
				return true;
			else if (mes < date.get(Calendar.MONTH) + 1)
				return true;
			else if (dia <= date.get(Calendar.DAY_OF_MONTH))
				return true;
		return false;
	}

	public static boolean validaEmail(String email) {
		int contador = 0;
		for (int i = 0; i < email.length(); i++)
			if ((email.charAt(i) + "").equals("@"))
				contador++;
		if (email.indexOf("@") > 1
				&& email.indexOf(".") < email.length() - 1 && contador == 1)
			return true;
		return false;
	}

}
