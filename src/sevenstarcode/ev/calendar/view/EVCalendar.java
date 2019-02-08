package sevenstarcode.ev.calendar.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import sevenstarcode.ev.calendar.formatter.FormatarData;

public class EVCalendar extends JPanel implements ActionListener , MouseListener {
	
	/**
	 * Serialização
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblFundo;
	private JLabel lblCabecalho;
	private Calendar data;
	private JButton btnAnterior, btnAvancar;
	private List<Dia> dias;
	private Set<Dia> agendados;
	private int diaFinal;
	private CabecalhoSemana cabecalhoSemana;
	private IActionEVCalendar actionEVCalendar;
	
	public IActionEVCalendar getActionEVCalendar() {
		return actionEVCalendar;
	}
	public void setActionEVCalendar(IActionEVCalendar actionEVCalendar) {
		this.actionEVCalendar = actionEVCalendar;
	}
	public CabecalhoSemana getCabecalhoSemana() {
		return cabecalhoSemana;
	}
	public JButton getBtnAnterior() {
		return btnAnterior;
	}
	public void setBtnAnterior(JButton btnAnterior) {
		this.btnAnterior = btnAnterior;
	}
	public JButton getBtnAvancar() {
		return btnAvancar;
	}
	public void setBtnAvancar(JButton btnAvancar) {
		this.btnAvancar = btnAvancar;
	}
	private static HashMap<Integer, DiaSemana> mapSemana = new HashMap<Integer, DiaSemana>();
	static{
		mapSemana.put(0, DiaSemana.SEGUNDA);
		mapSemana.put(1, DiaSemana.TERCA);
		mapSemana.put(2, DiaSemana.QUARTA);
		mapSemana.put(3, DiaSemana.QUINTA);
		mapSemana.put(4, DiaSemana.SEXTA);
		mapSemana.put(5, DiaSemana.SABADO);
		mapSemana.put(6, DiaSemana.DOMINGO);
	}
	public EVCalendar(){
		setLayout(null);
		agendados = new HashSet<Dia>();
		data = Calendar.getInstance();
		dias = new ArrayList<Dia>();
		init();
		
		new Thread(new Runnable() {
			public void run() {
				while(true){
					try {
						Thread.sleep(50l);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					responsividade();
				}
			}
		}).start();
		setVisible(true);
	}


	private void init() {
		this.cabecalhoSemana = new CabecalhoSemana();
		for(Dia titulo : cabecalhoSemana.getTitulos()){
			dias.add(titulo);
			titulo.setBackground(Color.white);
			add(titulo);
		}
		this.cabecalhoSemana.setCorFundo(new Color (161, 0, 64));
		this.cabecalhoSemana.setCorTexto(Color.WHITE);
		
		for(int i=0;i<37;i++){
			Dia lbld = new Dia();
			lbld.setDiaSemana(mapSemana.get(i%7));
			
			dias.add(lbld);
			lbld.addMouseListener(this);
			add(lbld);
		}
		
		lblCabecalho = new JLabel();
		lblCabecalho.setText(new FormatarData().getCabecalho(data));
		lblCabecalho.setBounds(0, -11, getWidth(), 40);
		lblCabecalho.setHorizontalAlignment(SwingConstants.CENTER);
		lblCabecalho.setFont(new Font("Century Gothic",0,18));
		add(lblCabecalho);
		
		btnAnterior = new JButton("<");
		btnAnterior.setBounds(50,5,40,20);
		btnAnterior.setFont(new Font("Century Gothic",0,15));
		btnAnterior.setBackground(new Color (161, 0, 64));
		btnAnterior.setForeground(Color.WHITE);
		add(btnAnterior);
		btnAnterior.addActionListener(this);
		
		btnAvancar = new JButton(">");
		btnAvancar.setBounds(getWidth()-105,5,40,20);
		btnAvancar.setFont(new Font("Century Gothic",0,15));
		btnAvancar.setBackground(new Color (161, 0, 64));
		btnAvancar.setForeground(Color.WHITE);
		add(btnAvancar);
		btnAvancar.addActionListener(this);
		
		
		lblFundo = new JLabel();
		lblFundo.setBounds(0, 0, getWidth(), getHeight());
		lblFundo.setOpaque(true);
		lblFundo.setBackground(new Color (242, 250, 223));
		
		add(lblFundo);
		preencherDias();
	}
	
	public void preencherDias(){
		for(int i=7;i<dias.size();i++){
			dias.get(i).setText("");
			dias.get(i).setVisible(false);
		}
		
		int dia = data.get(Calendar.DAY_OF_MONTH);
		
		data.add(Calendar.DAY_OF_MONTH, - (dia-1));
		int diaSemana = data.get(Calendar.DAY_OF_WEEK);
		int mes = data.get(Calendar.MONTH) + 1;
		int ano = data.get(Calendar.YEAR);
		data.add(Calendar.MONTH, 1);
		data.add(Calendar.DAY_OF_MONTH,-1);
		
		diaFinal = data.get(Calendar.DAY_OF_MONTH);
		diaSemana-=2;
		if(diaSemana==-1){
			diaSemana=6;
		}
		
		int contador=1;
		for(int i=(7+diaSemana);i<44;i++){
			if(contador>diaFinal){
				break;
			}
			dias.get(i).setDia(contador);
			dias.get(i).setMes(mes);
			dias.get(i).setAno(ano);
			dias.get(i).setEstilo(EstiloDia.getEstiloPadrao());
			
			for (Dia d : agendados){
				if(contador == d.getDia() && mes == d.getMes()
						&& d.getAno() == ano){
					dias.get(i).setEstilo(d.getEstilo());
					
				}
			}
			contador++;
		}
		
		
	}
	
	
	public List<Dia> getDias() {
		return dias;
	}
	public void setDias(List<Dia> dias) {
		this.dias = dias;
	}
	public void responsividade(){
		lblFundo.setSize( getWidth(), getHeight());
		lblCabecalho.setSize(getWidth(), 40);
		btnAnterior.setBounds(25,-1,40,25);
		btnAvancar.setBounds(getWidth()-65,-1,40,25);
		montarCalendario();
	}
	
	public void montarCalendario(){
		int largura = getWidth();
		int cabecalho = (int)(getHeight() * 0.1);
		int altura = (int)(getHeight() * 0.9);
		int cadaLar = largura / 7;
		int cadaAlt = altura / 7;
		int pesoLargura = 0;
		int pesoAltura = cabecalho;
		int contador=0;
		for(JLabel dia : dias){
			if(contador == 7){
				contador=0;
				pesoLargura=0;
				pesoAltura+=(cadaAlt);
			}
			dia.setBounds(0+pesoLargura, pesoAltura, cadaLar, (cadaAlt));
			pesoLargura+=cadaLar;
			contador++;
		}
		
		
		
	}

	public void removerAgendamento(Dia d){
		for(Dia dia: agendados){
			if(dia.getDia()==d.getDia() &&
					dia.getMes() == d.getMes() &&
					dia.getAno() == d.getAno()){
				agendados.remove(dia);
				break;
			}
		}

		preencherDias();
	}
	public void addAgendamento(Dia d){
		
		agendados.add(d);
		preencherDias();
	}
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource().equals(btnAnterior)){
			data.add(Calendar.MONTH,-1);
		}else if(arg0.getSource().equals(btnAvancar)){
			data.add(Calendar.MONTH,1);
		}
		lblCabecalho.setText(new FormatarData().getCabecalho(data));
		
		preencherDias();
	}

	public void mudarCorDaSemana(DiaSemana semana, Color cor){
		for(Dia d : dias){
			if(d.getDiaSemana()!=null && d.getDiaSemana().equals(semana)){
				d.setBackground(cor);
			}
		}
	}

	public boolean isAgendado(Dia dia){
		for(Dia d: agendados){
			if(dia.getDia()==d.getDia() &&
					dia.getMes() == d.getMes() &&
					dia.getAno() == d.getAno()){
				
				return true;
			}
		}
		
		return false;
	}
	
	public void mouseClicked(MouseEvent arg0) {
		Dia dia = (Dia) arg0.getSource();
		if(arg0.getClickCount()==2){
			if (actionEVCalendar!=null){
				actionEVCalendar.doubleClickedDay(dia);
			}
		}else{
			//Random r = new Random();
			//dia.setBackground(new Color(r.nextInt(255)
				//	,r.nextInt(255),r.nextInt(255)));
			//JOptionPane.showMessageDialog(null, "Clicou no dia "+
				//	dia.getText()+" de "+lblCabecalho.getText());
			if (actionEVCalendar!=null){
				actionEVCalendar.clickedDay(dia);
			}
		}
	}


	public void mouseEntered(MouseEvent arg0) {
		if (actionEVCalendar!=null){
			Dia dia = (Dia) arg0.getSource();
			
			actionEVCalendar.mouseEnteredDay(dia);
		}
	}


	public void mouseExited(MouseEvent arg0) {
		if (actionEVCalendar!=null){
			Dia dia = (Dia) arg0.getSource();
			actionEVCalendar.mouseExited(dia);
		}
	}


	public void mousePressed(MouseEvent arg0) {

	}


	public void mouseReleased(MouseEvent arg0) {

	}
}
