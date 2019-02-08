package br.com.royal.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableCellRenderer;

import sevenstarcode.ev.calendar.view.Dia;
import sevenstarcode.ev.calendar.view.DiaSemana;
import sevenstarcode.ev.calendar.view.EVCalendar;
import sevenstarcode.ev.calendar.view.EstiloDia;
import sevenstarcode.ev.calendar.view.IActionEVCalendar;
import br.com.royal.controller.AgendamentoController;
import br.com.royal.controller.ClienteController;
import br.com.royal.controller.FuncionarioController;
import br.com.royal.controller.HorarioController;
import br.com.royal.controller.ServicoController;
import br.com.royal.model.Agendamento;
import br.com.royal.model.Cliente;
import br.com.royal.model.Funcionario;
import br.com.royal.model.Horario;
import br.com.royal.model.Servico;

public class JanelaAgendamento extends JFrame
		implements MouseListener, MouseMotionListener, KeyListener, WindowListener, IActionEVCalendar,
		ItemListener, ActionListener {
	private Color corCerejaForte = new Color(161, 0, 64);
	private int diaPraCadastrar = 0, mesPraCadastrar = 0, anoPraCadastrar = 0;
	private String horaCelulaFinal = "", minutoCelulaFinal = "";
	private AgendamentoController ac = new AgendamentoController();
	private JanelaHome jh;
	private String txtBtn = "";
	private Thread tempoAtual;
	private JLabel lblMinimize, lblClose, lblNavbar, lblFundo, lblMolduraCalendario, 
	lblClientes, lblServicos, lblDuracao, lblSalvarAgendamento, lblEditarAgendamento, 
	lblExcluirAgendamento, lblCancelar, lblAddHorario, lblImg, lblNomeCliente, lblEmailCliente,
	lblCpfCliente, lblInfoFoto, lblInfoNomeCompleto, lblDataCompleta;
	private ArrayList<Funcionario> funcionariosTabela = new ArrayList<Funcionario>();
	private Funcionario funcionarioClicado;
	ArrayList<Horario> horarios;
	Dia anterior = new Dia();
	private Calendar hoje, dataBusca;
	BufferedImage imagem;
	private Agendamento agendamentoPraEditar;
	private JTable tblAgendamentos;
	private FTable modeloAgendamento;
	private JComboBox<Servico> cmbServico;
	private JComboBox<Cliente> cmbCliente;
	private JComboBox<String> cmbDuracao;
	private JButton btnAddCliente, btnAddServico;
	private int posX, posY;
	private AgendamentoController aController = new AgendamentoController();
	private EVCalendar calendario;
	private EstiloDia estiloCalendar, estiloDiaAtual;
	
	public int getDiaPraCadastrar() {
		return diaPraCadastrar;
	}

	public void setDiaPraCadastrar(int diaPraCadastrar) {
		this.diaPraCadastrar = diaPraCadastrar;
	}

	public int getMesPraCadastrar() {
		return mesPraCadastrar;
	}

	public void setMesPraCadastrar(int mesPraCadastrar) {
		this.mesPraCadastrar = mesPraCadastrar;
	}

	public int getAnoPraCadastrar() {
		return anoPraCadastrar;
	}

	public void setAnoPraCadastrar(int anoPraCadastrar) {
		this.anoPraCadastrar = anoPraCadastrar;
	}

	
	public JanelaAgendamento(JanelaHome jh) {
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		this.jh = jh;
		setTitle("Gerenciamento de Agenda");
		setSize(1080, 700);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		addWindowListener(this);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setUndecorated(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Img/Rosa.png"));
		IC();
		camposFalse();
		setVisible(true);
	}

	public void IC() {
		tempoAtual = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						hoje = Calendar.getInstance();
						Thread.sleep(1000);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

			}
		});
		
		tempoAtual.start();
		lblMinimize = new JLabel();
		// lblMinimize.setOpaque(true);
		// lblMinimize.setBackground(Color.GREEN);
		lblMinimize.setBounds(982, 10, 42, 20);
		lblMinimize.addMouseListener(this);
		add(lblMinimize);

		lblClose = new JLabel();
		// lblClose.setOpaque(true);
		// lblClose.setBackground(Color.GREEN);
		lblClose.setBounds(1032, 6, 43, 28);
		lblClose.addMouseListener(this);
		add(lblClose);

		lblNavbar = new JLabel();
		// lblNavbar.setOpaque(true);
		// lblNavbar.setBackground(Color.GREEN);
		lblNavbar.setBounds(0, 0, getWidth(), 41);
		lblNavbar.addMouseListener(this);
		lblNavbar.addMouseMotionListener(this);
		add(lblNavbar);

		calendario = new EVCalendar();
		calendario.setBounds(80, 105, getWidth() - 765, getHeight() - 455);
		calendario.setActionEVCalendar(this);
		add(calendario);

		lblMolduraCalendario = new JLabel(new ImageIcon("Img/moldura rosa claro.png"));
		lblMolduraCalendario.setBounds(calendario.getX() - 62, calendario.getY() - 55, 439, 339);
		add(lblMolduraCalendario);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		modeloAgendamento = new FTable();
		tblAgendamentos = new JTable(modeloAgendamento);
		tblAgendamentos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblAgendamentos.getTableHeader().setResizingAllowed(false);
		tblAgendamentos.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		tblAgendamentos.setRowSelectionAllowed(true);
		tblAgendamentos.setSelectionBackground(new Color(250, 208, 213));
		tblAgendamentos.setSelectionForeground(Color.BLACK);
		tblAgendamentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblAgendamentos.getTableHeader().setDefaultRenderer(new RTableAgendamento());

		tblAgendamentos.setColumnSelectionAllowed(true);
		tblAgendamentos.addKeyListener(this);
		tblAgendamentos.addMouseListener(this);
		JScrollPane scroll = new JScrollPane(tblAgendamentos);
		for (int i = 0; i < tblAgendamentos.getRowCount(); i++) {
			tblAgendamentos.columnAtPoint(new Point(0, 0));
		}
		scroll.setBounds(lblMolduraCalendario.getX() + 23, lblMolduraCalendario.getY() + 400, 1000, 200);
		scroll.setColumnHeader(new JViewport() {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 40;
				return d;
			}
		});

		add(scroll);

		modeloAgendamento.addColumn("Horários");
		for (Funcionario f : new FuncionarioController().getFuncionarios()) {
			modeloAgendamento.addColumn(f);
			funcionariosTabela.add(f);
		}
		for (int i = 0; i < tblAgendamentos.getColumnCount(); i++) {
			tblAgendamentos.getColumnModel().getColumn(i).setPreferredWidth(200);
		}
		for (int i = 0; i < tblAgendamentos.getRowCount(); i++) {
			tblAgendamentos.setRowHeight(i, 40);
		}
		tblAgendamentos.getTableHeader().setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		((DefaultTableCellRenderer) tblAgendamentos.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);
		tblAgendamentos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		for (int i = 0; i < tblAgendamentos.getColumnCount(); i++) {
			DefaultTableCellRenderer cellRender = new DefaultTableCellRenderer();
			cellRender.setHorizontalAlignment(SwingConstants.CENTER);
			tblAgendamentos.getColumnModel().getColumn(i).setCellRenderer(cellRender);
		}

		tblAgendamentos.getTableHeader().setReorderingAllowed(false);

		lblClientes = new JLabel("Selecione o Cliente:");
		// lblClientes.setOpaque(true);
		// lblClientes.setBackground(Color.GREEN);
		lblClientes.setBounds(lblMolduraCalendario.getX() + lblMolduraCalendario.getWidth() + 15,
				lblMolduraCalendario.getY() + 40, 146, 30);
		lblClientes.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 15));
		add(lblClientes);

		cmbCliente = new JComboBox<Cliente>();
		cmbCliente.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		cmbCliente.setBounds(lblClientes.getX(), lblClientes.getY() + 30, lblClientes.getWidth(),
				lblClientes.getHeight());
		cmbCliente.addItemListener(this);
		add(cmbCliente);

		lblServicos = new JLabel("Selecione o Serviço:");
		// lblServicos.setOpaque(true);
		// lblServicos.setBackground(Color.GREEN);
		lblServicos.setBounds(lblClientes.getX() + lblClientes.getWidth() + 60, lblClientes.getY(), 150, 30);
		lblServicos.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 15));
		add(lblServicos);

		cmbServico = new JComboBox<Servico>();
		cmbServico.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		cmbServico.setBounds(lblServicos.getX(), lblServicos.getY() + 30, lblServicos.getWidth(),
				lblServicos.getHeight());
		add(cmbServico);

		lblDuracao = new JLabel("Qual a duração?");
		// lblDuracao.setOpaque(true);
		// lblDuracao.setBackground(Color.GREEN);
		lblDuracao.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 15));
		lblDuracao.setBounds(lblServicos.getX() + lblServicos.getWidth() + 60, lblServicos.getY(), 126, 30);
		add(lblDuracao);

		cmbDuracao = new JComboBox<String>();
		cmbDuracao.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		cmbDuracao.setBounds(lblDuracao.getX(), lblDuracao.getY() + 30, lblDuracao.getWidth(), lblDuracao.getHeight());
		cmbDuracao.addItemListener(this);
		add(cmbDuracao);

		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		btnAddCliente = new JButton("+");
		btnAddCliente.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		btnAddCliente.setToolTipText("Adicionar um cliente");
		btnAddCliente.setBackground(corCerejaForte);
		btnAddCliente.setForeground(Color.WHITE);
		btnAddCliente.addActionListener(this);
		btnAddCliente.setBounds(cmbCliente.getX() + cmbCliente.getWidth() + 5, cmbCliente.getY() + 2, 45, 25);
		add(btnAddCliente);

		btnAddServico = new JButton("+");
		btnAddServico.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		btnAddServico.setToolTipText("Adicionar um serviço");
		btnAddServico.setBackground(corCerejaForte);
		btnAddServico.setForeground(Color.WHITE);
		btnAddServico.addActionListener(this);
		btnAddServico.setBounds(cmbServico.getX() + cmbServico.getWidth() + 5, cmbServico.getY() + 2, 45, 25);
		add(btnAddServico);

		lblImg = new JLabel("");
		lblImg.setBounds(cmbCliente.getX(), cmbCliente.getY() + 40, 175, 175);
		lblImg.setBorder(BorderFactory.createLineBorder(new Color(161, 0, 64)));
		add(lblImg);

		lblSalvarAgendamento = new JLabel(new ImageIcon("Img/Agendamento/Salvar.png"));
		lblSalvarAgendamento.setToolTipText("Salvar Agendamento");
		lblSalvarAgendamento.setBounds(lblImg.getX() + 140, lblImg.getY() + lblImg.getHeight() + 25, 40, 43);
		lblSalvarAgendamento.addMouseListener(this);
		add(lblSalvarAgendamento);

		lblEditarAgendamento = new JLabel(new ImageIcon("Img/Agendamento/Editar.png"));
		lblEditarAgendamento.setToolTipText("Editar Agendamento");
		lblEditarAgendamento.setBounds(lblSalvarAgendamento.getX() + lblSalvarAgendamento.getWidth() + 13,
				lblSalvarAgendamento.getY(), 40, 43);
		lblEditarAgendamento.addMouseListener(this);
		add(lblEditarAgendamento);

		lblExcluirAgendamento = new JLabel(new ImageIcon("Img/Agendamento/Excluir.png"));
		lblExcluirAgendamento.setToolTipText("Excluir Agendamento");
		lblExcluirAgendamento.setBounds(lblEditarAgendamento.getX() + lblEditarAgendamento.getWidth() + 13,
				lblEditarAgendamento.getY(), 40, 43);
		lblExcluirAgendamento.addMouseListener(this);
		add(lblExcluirAgendamento);

		lblAddHorario = new JLabel(new ImageIcon("Img/Horário/Novo.png"));
		lblAddHorario.setToolTipText("Adicionar horário");
		lblAddHorario.setBounds(lblExcluirAgendamento.getX() + lblExcluirAgendamento.getWidth() + 10,
				lblExcluirAgendamento.getY() + 1, 40, 43);
		lblAddHorario.addMouseListener(this);
		add(lblAddHorario);

		lblCancelar = new JLabel(new ImageIcon("Img/Agendamento/Cancelar.png"));
		lblCancelar.setToolTipText("Cancelar");
		lblCancelar.setBounds(lblAddHorario.getX() + lblAddHorario.getWidth() + 10, lblAddHorario.getY(), 36, 36);
		lblCancelar.addMouseListener(this);
		add(lblCancelar);

		lblNomeCliente = new JLabel("Nome: ");
		lblNomeCliente.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		// lblNomeCliente.setOpaque(true);
		// lblNomeCliente.setBackground(Color.GREEN);
		lblNomeCliente.setBounds(lblImg.getX() + lblImg.getWidth() + 5, lblImg.getY() + 85, 295, 30);
		add(lblNomeCliente);

		lblCpfCliente = new JLabel("CPF: ");
		lblCpfCliente.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		// lblCpfCliente.setOpaque(true);
		// lblCpfCliente.setBackground(Color.GREEN);
		lblCpfCliente.setBounds(lblNomeCliente.getX(), lblNomeCliente.getY() + 30, lblNomeCliente.getWidth(),
				lblNomeCliente.getHeight());
		add(lblCpfCliente);

		lblEmailCliente = new JLabel("E-Mail: ");
		lblEmailCliente.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		// lblEmailCliente.setOpaque(true);
		// lblEmailCliente.setBackground(Color.GREEN);
		lblEmailCliente.setBounds(lblCpfCliente.getX(), lblCpfCliente.getY() + 30, lblCpfCliente.getWidth(),
				lblCpfCliente.getHeight());
		add(lblEmailCliente);

		lblInfoFoto = new JLabel();
		lblInfoFoto.setBounds(lblImg.getX() + 22, lblImg.getY() + 70, 260, 30);
		lblInfoFoto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		lblInfoFoto.setForeground(Color.RED);
		add(lblInfoFoto);

		lblInfoNomeCompleto = new JLabel(new ImageIcon("Img/info.png"));
		lblInfoNomeCompleto.setBounds(lblNomeCliente.getX() + lblNomeCliente.getWidth() - 65, lblNomeCliente.getY() + 1,
				150, 30);
		lblInfoNomeCompleto.setVisible(false);
		add(lblInfoNomeCompleto);
		
		lblDataCompleta = new JLabel();
		lblDataCompleta.setFont(new Font ("Century Gothic", Font.TRUETYPE_FONT, 15));
		lblDataCompleta.setForeground(new Color (161, 0, 68));
//		lblDataCompleta.setOpaque(true);
//		lblDataCompleta.setBackground(Color.GREEN);
		lblDataCompleta.setHorizontalAlignment(SwingConstants.CENTER);
		lblDataCompleta.setBounds(scroll.getX(), scroll.getY()-60, 390, 30);
		add(lblDataCompleta);
		
		setaDiaAtualNoCalendario();
		
		lblFundo = new JLabel(new ImageIcon("Img/Gerenciamento de Agenda.png"));
		lblFundo.setBounds(0, 0, getWidth(), getHeight());
		add(lblFundo);
		preencherTabelaAgendamento();
		populaCelulas();
	}

	private boolean verificaIntervaloData() {
		if (diaPraCadastrar < hoje.get(Calendar.DAY_OF_MONTH) && mesPraCadastrar == (hoje.get(Calendar.MONTH) + 1)
				&& anoPraCadastrar == hoje.get(Calendar.YEAR)) {
			JOptionPane.showMessageDialog(null, "Não é possível agendar para esse dia!");
			return false;
		} else if (mesPraCadastrar < (hoje.get(Calendar.MONTH) + 1) && anoPraCadastrar == hoje.get(Calendar.YEAR)) {
			JOptionPane.showMessageDialog(null, "Não é possível agendar para esse mês!");
			return false;
		} else if (anoPraCadastrar < hoje.get(Calendar.YEAR)) {
			JOptionPane.showMessageDialog(null, "Não é possível agendar para esse ano!");
			return false;
		} else {
			return true;
		}
	}

	private boolean verificaPreenchimentoCampos() {
		if (!verificaIntervaloData()) {
			return false;
		} else if (!verificaIntervaloHorario()) {
			return false;
		} else if (cmbCliente.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(null, "Selecione um cliente!");
			return false;
		} else if (cmbServico.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(null, "Selecione um serviço!");
			return false;
		} else if (cmbDuracao.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(null, "Selecione uma duração!");
			return false;
		} else
			return true;
	}

	public void preencherTabelaAgendamento() {
		modeloAgendamento.setRowCount(0);
		horarios = new ArrayList<Horario>();
		int nFuncionarios = new FuncionarioController().getFuncionarios().size();
		Horario hr = new Horario();
		String verDia="", verMes="", verAno="";
		if (diaPraCadastrar<10)
			verDia="0"+diaPraCadastrar;
		else
			verDia=diaPraCadastrar+"";
		if (mesPraCadastrar<10)
			verMes="0"+mesPraCadastrar;
		else
			verMes=mesPraCadastrar+"";
		verAno=anoPraCadastrar+"";
		hr.setDataHorario(verDia+"/"+verMes+"/"+verAno);
		for (Horario h : new HorarioController().listarHorariosEspecificos(hr)){
			horarios.add(h);
		}
		for (Horario h : new HorarioController().listarHorariosNaoEspecificos()){
			horarios.add(h);
		}
		Collections.sort(horarios);
		for (Horario h : horarios) {

			Object[] b = new Object[nFuncionarios + 1];
			b[0] = h;
			for (int i = 1; i < nFuncionarios + 1; i++) {
				b[i] = "";
			}
			modeloAgendamento.addRow(b);
		}
		for (int i = 0; i < tblAgendamentos.getRowCount(); i++) {
			tblAgendamentos.setRowHeight(i, 40);
		}

	}

	public void preencherCombos() {
		cmbCliente.removeAllItems();
		cmbCliente.addItem(null);
		for (Cliente c : new ClienteController().getClientes()) {
			cmbCliente.addItem(c);
		}

		cmbServico.removeAllItems();
		cmbServico.addItem(null);
		for (Servico s : new ServicoController().getServicosPorFuncionario(funcionarioClicado)){
			cmbServico.addItem(s);
		}

		cmbDuracao.removeAllItems();
		cmbDuracao.addItem(null);
		cmbDuracao.addItem("00:30");
		cmbDuracao.addItem("01:00");
		cmbDuracao.addItem("01:30");
		cmbDuracao.addItem("02:00");
		cmbDuracao.addItem("02:30");
		cmbDuracao.addItem("03:00");
		cmbDuracao.addItem("03:30");
		cmbDuracao.addItem("04:00");
	}

	public void camposFalse() {
		lblAddHorario.setEnabled(true);
		calendario.setEnabled(true);
		calendario.getBtnAnterior().setEnabled(true);
		calendario.getBtnAvancar().setEnabled(true);
		lblSalvarAgendamento.setEnabled(false);
		lblEditarAgendamento.setEnabled(false);
		lblExcluirAgendamento.setEnabled(false);
		cmbDuracao.setEnabled(false);
		cmbCliente.setEnabled(false);
		cmbServico.setEnabled(false);
		tblAgendamentos.setEnabled(true);
		btnAddCliente.setEnabled(false);
		btnAddServico.setEnabled(false);
	}

	public void camposTrue() {
		tblAgendamentos.setEnabled(false);
		calendario.setEnabled(false);
		calendario.getBtnAnterior().setEnabled(false);
		calendario.getBtnAvancar().setEnabled(false);
		lblAddHorario.setEnabled(false);
		lblSalvarAgendamento.setEnabled(true);
		lblEditarAgendamento.setEnabled(false);
		lblExcluirAgendamento.setEnabled(false);
		cmbDuracao.setEnabled(true);
		cmbCliente.setEnabled(true);
		cmbServico.setEnabled(true);
		btnAddCliente.setEnabled(true);
		btnAddServico.setEnabled(true);
	}

	public void setaDiaAtualNoCalendario() {
		Dia d = new Dia();
		diaPraCadastrar = hoje.get(Calendar.DAY_OF_MONTH);
		mesPraCadastrar = (hoje.get(Calendar.MONTH) + 1);
		anoPraCadastrar = hoje.get(Calendar.YEAR);
		d.setDia(diaPraCadastrar);
		d.setMes(mesPraCadastrar);
		d.setAno(anoPraCadastrar);
		estiloCalendar = new EstiloDia();
		estiloCalendar.setCorFundo(new Color(248, 186, 192));
		anterior = d;
		d.setEstilo(estiloCalendar);
		calendario.addAgendamento(d);
		preencheDataCompleta(d);
	}
	
	public void preencheDataCompleta(Dia d){
		String diaSemana="", diaMes="", mes="", ano="";
		DiaSemana ds=null;
		for (Dia dia : calendario.getDias()){
			if (dia.getDia()==d.getDia() && dia.getMes()==d.getMes() && dia.getAno()==
					d.getAno()){
				ds=dia.getDiaSemana();
			}
		}
		switch (ds){
			case SEGUNDA:
				diaSemana="Segunda-Feira";
				break;
			case TERCA:
				diaSemana="Terça-Feira";
				break;
			case QUARTA:
				diaSemana="Quarta-Feira";
				break;
			case QUINTA:
				diaSemana="Quinta-Feira";
				break;
			case SEXTA:
				diaSemana="Sexta-Feira";
				break;
			case SABADO:
				diaSemana="Sábado";
				break;
			case DOMINGO:
				diaSemana="Domingo";
				break;
		}
		switch (d.getMes()){
		case 1:
			mes="Janeiro";
			break;
		case 2:
			mes="Fevereiro";
			break;
		case 3:
			mes="Março";
			break;
		case 4:
			mes="Abril";
			break;
		case 5:
			mes="Maio";
			break;
		case 6:
			mes="Junho";
			break;
		case 7:
			mes="Julho";
			break;
		case 8:
			mes="Agosto";
			break;
		case 9:
			mes="Setembro";
			break;
		case 10:
			mes="Outubro";
			break;
		case 11:
			mes="Novembro";
			break;
		case 12:
			mes="Dezembro";
			break;
		}
		if (d.getDia()<10)
			diaMes="0"+d.getDia();
		else
			diaMes=d.getDia()+"";
		ano=d.getAno()+"";
		lblDataCompleta.setText(diaSemana+", "+diaMes+" de "+mes+" de "+ano);
	}
	
	public void limpaCampos() {
		cmbCliente.setSelectedItem(null);
		cmbServico.setSelectedItem(null);
		cmbDuracao.setSelectedItem(null);
		lblImg.setIcon(null);
		lblInfoNomeCompleto.setVisible(false);
		lblInfoFoto.setText("");
		lblNomeCliente.setText("Nome: ");
		lblCpfCliente.setText("CPF: ");
		lblEmailCliente.setText("E-Mail: ");
		tblAgendamentos.clearSelection();
		calendario.grabFocus();
		txtBtn = "";
	}

	public BufferedImage setImagemDimensao(String caminhoImg, Integer imgLargura, Integer imgAltura) {
		Double novaImgLargura = null;
		Double novaImgAltura = null;
		Double imgProporcao = null;
		Graphics2D g2d = null;
		imagem = null;
		BufferedImage novaImagem = null;
		try {
			// pegando a imagem que vai ser redimensionada
			imagem = ImageIO.read(new File(caminhoImg));
		} catch (IOException ex) {
			System.err.println("Quebro na setImgDimensao:" + ex.getMessage());
			if (caminhoImg.length() > 30)
				lblInfoFoto.setText("Foto não encontrada.");
			else
				lblInfoFoto.setText("");
			Logger.getLogger(JanelaAgendamento.class.getName()).log(Level.SEVERE, null, ex);
		}

		// pegando a largura da img
		try {
			novaImgLargura = (double) imagem.getWidth();

			// pegando a altura
			novaImgAltura = (double) imagem.getHeight();

			// verificando se a largura e altura e maior do que os recebidos pra
			// redimensionar
			if (novaImgLargura >= imgLargura) {
				imgProporcao = (novaImgAltura / novaImgLargura);
				novaImgLargura = (double) imgLargura;

				// --- altura deve <= ao parÃ¢metro imgAltura e proporcional a
				// largura ---
				novaImgAltura = (novaImgLargura * imgProporcao);

				// --- se altura for maior do que o parÃ¢metro imgAltura,
				// diminui-se
				// a largura de ---
				// --- forma que a altura seja igual ao parÃ¢metro imgAltura e
				// proporcional a largura ---
				while (novaImgAltura > imgAltura) {
					novaImgLargura = (double) (--imgLargura);
					novaImgAltura = (novaImgLargura * imgProporcao);
				}
			} else if (novaImgAltura >= imgAltura) {
				imgProporcao = (novaImgLargura / novaImgAltura);// calcula a
																// proporÃ§Ã£o
				novaImgAltura = (double) imgAltura;

				// --- se largura for maior do que o parÃ¢metro imgLargura,
				// diminui-se a altura de ---
				// --- forma que a largura seja igual ao parÃ¢metro imglargura e
				// proporcional a altura ---
				while (novaImgLargura > imgLargura) {
					novaImgAltura = (double) (--imgAltura);
					novaImgLargura = (novaImgAltura * imgProporcao);
				}
			}

			novaImagem = new BufferedImage(novaImgLargura.intValue(), novaImgAltura.intValue(),
					BufferedImage.TYPE_INT_RGB);
			g2d = novaImagem.createGraphics();
			g2d.drawImage(imagem, 0, 0, novaImgLargura.intValue(), novaImgAltura.intValue(), null);

			return novaImagem;
		} catch (Exception ex) {
			System.err.println("Quebro na setImgDimensao(2):" + ex.getMessage());
		}
		return null;
	}

	private boolean verificaIntervaloHorario() {
		String horarioClicado = tblAgendamentos.getValueAt(tblAgendamentos.getSelectedRow(), 0).toString();
		int horaClicada = 0, minutoClicado = 0;
		try {
			horaClicada = Integer.parseInt(horarioClicado.substring(0, 2));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			minutoClicado = Integer.parseInt(horarioClicado.substring(3));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (diaPraCadastrar == hoje.get(Calendar.DAY_OF_MONTH) && mesPraCadastrar == (hoje.get(Calendar.MONTH) + 1)
				&& anoPraCadastrar == hoje.get(Calendar.YEAR)) {
			if (horaClicada < hoje.get(Calendar.HOUR_OF_DAY)
					|| horaClicada == hoje.get(Calendar.HOUR_OF_DAY) && minutoClicado < hoje.get(Calendar.MINUTE)) {
				JOptionPane.showMessageDialog(null, "Não é possível realizar o agendamento "
																	+ "para esse horário!");
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	public boolean addAgendamento(String tempo) {
		Cliente c = (Cliente) cmbCliente.getSelectedItem();
		Servico s = (Servico) cmbServico.getSelectedItem();
		int horaFinal = 0;
		int minutoFinal = 0;
		int horaAtual = Integer.parseInt(tempo.substring(0, 2));
		int minutoAtual = Integer.parseInt(tempo.substring(3));
		int horasAdd = Integer.parseInt(cmbDuracao.getSelectedItem().toString().substring(0, 2));
		int minutosAdd = Integer.parseInt(cmbDuracao.getSelectedItem().toString().substring(3));

		if (minutoAtual + minutosAdd > 59) {
			minutoFinal = (minutoAtual + minutosAdd) - 60;
			horasAdd++;
		} else
			minutoFinal = minutoAtual + minutosAdd;

		horaFinal = horaAtual + horasAdd;
		if (horaFinal < 10)
			horaCelulaFinal = "0" + horaFinal;
		else
			horaCelulaFinal = "" + horaFinal;
		if (minutoFinal < 10)
			minutoCelulaFinal = "0" + minutoFinal;
		else
			minutoCelulaFinal = "" + minutoFinal;
		Calendar dataCadastrar = Calendar.getInstance();
		dataCadastrar.set(Calendar.DAY_OF_MONTH, diaPraCadastrar);
		dataCadastrar.set(Calendar.MONTH, mesPraCadastrar - 1);
		dataCadastrar.set(Calendar.YEAR, anoPraCadastrar);
		Agendamento a = new Agendamento();
		a.setDataAgendamento(dataCadastrar.getTime());
		a.setCodigoCliente(c.getCod());
		a.setCodigoServico(s.getCodServico());
		a.setCodigoFuncionario(funcionariosTabela.get(tblAgendamentos.getSelectedColumn() - 1).getCodFuncionario());
		Horario h = new Horario();
		h.setHoraInicio(tblAgendamentos.getValueAt(tblAgendamentos.getSelectedRow(), 0).toString());
		h.setDataHorario(horarios.get(tblAgendamentos.getSelectedRow()).getDataHorario());
		a.setCodigoHorarioInicio(new HorarioController().buscaIdHorario(h).getCodHorario());
		a.setHorarioFim(horaCelulaFinal + ":" + minutoCelulaFinal);
		if (verificaIntervaloAgendamento(a)) {
			ac.addAgendamento(a);
			JOptionPane.showMessageDialog(null, "Agendamento realizado com sucesso!");
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "A duração do agendamento conflita com outro agendamento marcado!");
			return false;
		}

	}

	public boolean editaAgendamento(Agendamento a) {
		Cliente c = (Cliente) cmbCliente.getSelectedItem();
		Servico s = (Servico) cmbServico.getSelectedItem();
		Horario h = new Horario();
		h.setCodHorario(a.getCodigoHorarioInicio());
		h.setHoraInicio(new HorarioController().encontreHorarioPorID(h).getHoraInicio());
		int horaFinal = 0;
		int minutoFinal = 0;
		int horaAtual = Integer.parseInt(h.getHoraInicio().substring(0, 2));
		int minutoAtual = Integer.parseInt(h.getHoraInicio().substring(3));
		int horasAdd = Integer.parseInt(cmbDuracao.getSelectedItem().toString().substring(0, 2));
		int minutosAdd = Integer.parseInt(cmbDuracao.getSelectedItem().toString().substring(3));

		if (minutoAtual + minutosAdd > 59) {
			minutoFinal = (minutoAtual + minutosAdd) - 60;
			horasAdd++;
		} else
			minutoFinal = minutoAtual + minutosAdd;

		horaFinal = horaAtual + horasAdd;
		if (horaFinal < 10)
			horaCelulaFinal = "0" + horaFinal;
		else
			horaCelulaFinal = "" + horaFinal;
		if (minutoFinal < 10)
			minutoCelulaFinal = "0" + minutoFinal;
		else
			minutoCelulaFinal = "" + minutoFinal;
		a.setCodigoCliente(c.getCod());
		a.setCodigoServico(s.getCodServico());
		a.setHorarioFim(horaCelulaFinal + ":" + minutoCelulaFinal);
		a.setCodigoFuncionario(funcionariosTabela.get(tblAgendamentos.getSelectedColumn() - 1).getCodFuncionario());
		if (verificaIntervaloAgendamento(a)) {
			ac.editaAgendamento(a);
			JOptionPane.showMessageDialog(null, "Agendamento editado com sucesso!");
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "A duração do agendamento conflita com outro agendamento marcado!");
			return false;
		}
	}

	
	public boolean verificaIntervaloAgendamento(Agendamento a) {
		for (Agendamento ag : ac.verificaIntervaloAgendamentos(a)) {
			int horaConferida = 0, minutoConferido = 0, horaAgendada = 0, minutoAgendado = 0
					, horaInicialAgendada = 0, minutoInicialAgendado = 0;
			try {
				Horario hi = new Horario();
				hi.setCodHorario(a.getCodigoHorarioInicio());
				String horaI = new HorarioController().encontreHorarioPorID(hi).getHoraInicio();
				horaInicialAgendada = Integer.parseInt(horaI.substring(0, 2));
				minutoInicialAgendado = Integer.parseInt(horaI.substring(3));
				horaAgendada = Integer.parseInt(a.getHorarioFim().substring(0, 2));
				minutoAgendado = Integer.parseInt(a.getHorarioFim().substring(3));
				Horario h = new Horario();
				h.setCodHorario(ag.getCodigoHorarioInicio());
				String hora = new HorarioController().encontreHorarioPorID(h).getHoraInicio();
				horaConferida = Integer.parseInt(hora.substring(0, 2));
				minutoConferido = Integer.parseInt(hora.substring(3));
				if (horaConferida>horaInicialAgendada || horaConferida == horaInicialAgendada
						&& minutoConferido>minutoInicialAgendado){
					if ((horaConferida == horaAgendada && minutoConferido < minutoAgendado)
							|| (horaConferida < horaAgendada && minutoConferido >= minutoAgendado)
							|| (horaConferida < horaAgendada && minutoConferido < minutoAgendado)) {
						return false;
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}

		}
		return true;
	}

	public void limpaCelulas() {
		for (int i = 0; i < tblAgendamentos.getRowCount(); i++) {
			for (int j = 1; j < tblAgendamentos.getColumnCount(); j++) {
				tblAgendamentos.setValueAt("", i, j);
			}
		}
	}

	public void populaCelulas() {
		setaDataFiltro();
		limpaCelulas();
		for (Agendamento a : ac.listarAgendamentos(dataBusca)) {
			int horaFinal = 0, minutoFinal = 0;
			int linhaConferir = 0, colunaConferir = 0, colunaPreencher = 0;
			try {
				horaFinal = Integer.parseInt(a.getHorarioFim().substring(0, 2));
				minutoFinal = Integer.parseInt(a.getHorarioFim().substring(3));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			Funcionario f = new FuncionarioController().getFuncionario(a.getCodigoFuncionario());
			Cliente c = new ClienteController().getCliente(a.getCodigoCliente());
			Servico s = new ServicoController().getServico(a.getCodigoServico());
			Horario h = new Horario();
			h.setCodHorario(a.getCodigoHorarioInicio());
			h.setDataHorario(horarios.get(linhaConferir).getDataHorario());
			h = new HorarioController().encontreHorarioPorID(h);
			for (int i = 0; i < tblAgendamentos.getRowCount(); i++) {
				if (tblAgendamentos.getValueAt(i, 0).toString().equals(h.getHoraInicio())) {
					linhaConferir = i;
				}
			}
			for (int i = 1; i < tblAgendamentos.getColumnCount(); i++) {
				if (tblAgendamentos.getColumnName(i).equals(f.getNomeFuncionario())) {
					colunaPreencher = i;
				}
			}
			for (int i = linhaConferir; i < tblAgendamentos.getRowCount(); i++) {
				String tempo = h.getHoraInicio();
				String valorHora = tblAgendamentos.getValueAt(i, colunaConferir).toString().substring(0, 2);
				String valorMinuto = tblAgendamentos.getValueAt(i, colunaConferir).toString().substring(3);
				int horaCelula = Integer.parseInt(valorHora);
				int minutoCelula = Integer.parseInt(valorMinuto);
				if (horaFinal < 10)
					horaCelulaFinal = "0" + horaFinal;
				else
					horaCelulaFinal = horaFinal + "";
				if (minutoFinal < 10)
					minutoCelulaFinal = "0" + minutoFinal;
				else
					minutoCelulaFinal = minutoFinal + "";
				if ((horaCelula == horaFinal && minutoCelula < minutoFinal)
						|| (horaCelula < horaFinal && minutoCelula >= minutoFinal)
						|| (horaCelula < horaFinal && minutoCelula < minutoFinal)) {
					if (c.getNome().length() <= 14) {
						tblAgendamentos.setValueAt("<html><bgcolor='#FAD0D5' width='145px' height='20' "
								+ "align='center'><center> <font size='20px'> (" + tempo + " - " + horaCelulaFinal + ":"
								+ minutoCelulaFinal + ") " + c.getNome() + "<br>" + s.getNomeServico()
								+ "</font></center></html>", i, colunaPreencher);
					} else {
						tblAgendamentos.setValueAt("<html><bgcolor='#FAD0D5' width='145px' height='20' "
								+ "align='center'><center> <font size='20px'> (" + tempo + " - " + horaCelulaFinal + ":"
								+ minutoCelulaFinal + ") " + c.getNome().substring(0, 15) + "<br>" + s.getNomeServico()
								+ "</font></center>" + "</html>", i, colunaPreencher);
					}

				}
			}
		}
		tblAgendamentos.clearSelection();
	}

	public Agendamento setaDadosAgendamentoClicado(String info) {
		Agendamento a = new Agendamento();
		Horario h = new Horario();
		h.setHoraInicio(info.substring(info.indexOf("(") + 1, info.indexOf("(") + 6));
		for (Horario hor : horarios){
			if (hor.getHoraInicio().equals(info.substring(info.indexOf("(")+1, info.indexOf
					("(")+6))){
				h.setDataHorario(hor.getDataHorario());
			}
		}
		a.setCodigoHorarioInicio(new HorarioController().buscaIdHorario(h).getCodHorario());
		a.setHorarioFim(info.substring(info.indexOf("(") + 9, info.indexOf("(") + 14));
		a.setCodigoFuncionario(funcionarioClicado.getCodFuncionario());
		try {
			return ac.encontraInfoAgendamento(a, dataBusca);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public void preencheCamposAgendados(Agendamento a) {
		Cliente c = new ClienteController().getCliente(a.getCodigoCliente());
		Servico s = new ServicoController().getServico(a.getCodigoServico());
		for (int i = 1; i < cmbCliente.getItemCount(); i++) {
			Cliente cl = cmbCliente.getItemAt(i);
			if (cl.getCod() == c.getCod()) {
				cmbCliente.setSelectedItem(cmbCliente.getItemAt(i));
				break;
			}
		}
		for (int i = 1; i < cmbServico.getItemCount(); i++) {
			Servico se = cmbServico.getItemAt(i);
			if (se.getCodServico() == s.getCodServico()) {
				cmbServico.setSelectedItem(cmbServico.getItemAt(i));
				break;
			}
		}
		int horaInicio = 0, minutoInicio = 0, horaFim = 0, minutoFim = 0, difHora = 0, difMin = 0;
		Horario h = new Horario();
		h.setCodHorario(a.getCodigoHorarioInicio());
		Horario hr = new HorarioController().encontreHorarioPorID(h);
		try {
			horaInicio = Integer.parseInt(hr.getHoraInicio().substring(0, 2));
			minutoInicio = Integer.parseInt(hr.getHoraInicio().substring(3));
			horaFim = Integer.parseInt(a.getHorarioFim().substring(0, 2));
			minutoFim = Integer.parseInt(a.getHorarioFim().substring(3));
			difHora = horaFim - horaInicio;
			difMin = minutoFim - minutoInicio;
			String duracaoCompleta = "", duracaoHora = "", duracaoMin = "";
			if (difHora < 10)
				duracaoHora = "0" + difHora;
			else
				duracaoHora = difHora + "";
			if (difMin < 10)
				duracaoMin = "0" + difMin;
			else
				duracaoMin = difMin + "";
			duracaoCompleta = duracaoHora + ":" + duracaoMin;
			cmbDuracao.setSelectedItem(duracaoCompleta);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void preencheRodapeData(){
		
	}
	
	public void setaDataFiltro() {
		dataBusca = Calendar.getInstance();
		dataBusca.set(Calendar.DAY_OF_MONTH, diaPraCadastrar);
		dataBusca.set(Calendar.MONTH, (mesPraCadastrar - 1));
		dataBusca.set(Calendar.YEAR, anoPraCadastrar);
	}

	public EVCalendar getCalendario() {
		return calendario;
	}

	public void setCalendario(EVCalendar calendario) {
		this.calendario = calendario;
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		if (jh != null)
			jh.setVisible(true);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		setLocation(getLocation().x + e.getX() - posX, getLocation().y + e.getY() - posY);

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(lblMinimize)) {
			this.setExtendedState(JFrame.ICONIFIED);
		}

		if (e.getSource().equals(tblAgendamentos) && tblAgendamentos.isEnabled()) {
			funcionarioClicado = funcionariosTabela.get(tblAgendamentos.
					getSelectedColumn()-1);
			preencherCombos();
			String selecionado = tblAgendamentos
					.getValueAt(tblAgendamentos.getSelectedRow(), tblAgendamentos.getSelectedColumn()).toString();
			if (!selecionado.equals("") && selecionado.length() > 5) {
				preencheCamposAgendados(setaDadosAgendamentoClicado(selecionado));
				agendamentoPraEditar = setaDadosAgendamentoClicado(selecionado);
				lblEditarAgendamento.setEnabled(true);
				lblExcluirAgendamento.setEnabled(true);
			} else {
				cmbCliente.setSelectedItem(null);
				cmbServico.setSelectedItem(null);
				cmbDuracao.setSelectedItem(null);
				agendamentoPraEditar=null;
				lblEditarAgendamento.setEnabled(false);
				lblExcluirAgendamento.setEnabled(false);
			}
			if (e.getClickCount() == 2 && tblAgendamentos.getSelectedRow() != -1) {
				if (selecionado.equals("")) {
					if (verificaIntervaloData()) {
						if (verificaIntervaloHorario()) {
							txtBtn = "Novo";
							camposTrue();
						}
					}
				}
			}
		}

		if (e.getSource().equals(lblClose)) {
			this.dispose();
		}

		if (e.getSource().equals(lblSalvarAgendamento) && lblSalvarAgendamento.isEnabled()) {
			if (verificaPreenchimentoCampos()) {
				if (txtBtn.equals("Novo")) {
					if (JOptionPane.showConfirmDialog(null, "Deseja efetuar o agendamento?") == JOptionPane.YES_OPTION) {
						if (addAgendamento(tblAgendamentos.getValueAt(tblAgendamentos.getSelectedRow(), 0).toString())) {
							
							limpaCampos(); //a
							camposFalse();
							populaCelulas();
						}

					}
				}else if (txtBtn.equals("Editar")){
					if (JOptionPane.showConfirmDialog(null, "Deseja realizar as alterações?")==JOptionPane.YES_OPTION)
						if (editaAgendamento(agendamentoPraEditar)){
							limpaCampos();
							camposFalse();
							populaCelulas();
						}
				}
			}
		}

		if (e.getSource().equals(lblEditarAgendamento) && lblEditarAgendamento.isEnabled()) {
			txtBtn = "Editar";
			camposTrue();
			
		}

		if (e.getSource().equals(lblExcluirAgendamento) && lblExcluirAgendamento.isEnabled()) {
			if (JOptionPane.showConfirmDialog(null, "Deseja excluir o agendamento?")==
					JOptionPane.YES_OPTION){
				ac.removeAgendamento(agendamentoPraEditar);
				limpaCampos();
				populaCelulas();
				camposFalse();
			}
		}

		if (e.getSource().equals(lblAddHorario)) {
			new JanelaHorario(this);
		}

		if (e.getSource().equals(lblCancelar) && lblCancelar.isEnabled()) {
			limpaCampos();
			camposFalse();
			//setaDiaAtualNoCalendario();
			populaCelulas();
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource().equals(lblSalvarAgendamento)) {
			lblSalvarAgendamento.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblEditarAgendamento)) {
			lblEditarAgendamento.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblExcluirAgendamento)) {
			lblExcluirAgendamento.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblAddHorario)) {
			lblAddHorario.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblCancelar)) {
			lblCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource().equals(lblSalvarAgendamento)) {
			lblSalvarAgendamento.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource().equals(lblEditarAgendamento)) {
			lblEditarAgendamento.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource().equals(lblExcluirAgendamento)) {
			lblExcluirAgendamento.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource().equals(lblAddHorario)) {
			lblAddHorario.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource().equals(lblCancelar)) {
			lblCancelar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource().equals(lblNavbar)) {
			posX = e.getX();
			posY = e.getY();
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clickedDay(Dia dia) {
		if (calendario.isEnabled()) {
			cmbServico.setSelectedItem(null);
			cmbCliente.setSelectedItem(null);
			cmbDuracao.setSelectedItem(null);
			for (Dia d : calendario.getDias()) {
				if (d.getEstilo().getCorFundo().getRed() == 248 && d.getEstilo().getCorFundo().getGreen() == 186
						&& d.getEstilo().getCorFundo().getBlue() == 192) {
					calendario.removerAgendamento(d);
				}
			}
			EstiloDia ed = new EstiloDia();
			preencheDataCompleta(dia);
			if (anterior.getDia() != dia.getDia()) {
				ed.setCorFundo(new Color(221, 220, 220));
				anterior.setEstilo(ed);
			}
			ed.setCorFundo(new Color(248, 186, 192));
			diaPraCadastrar = dia.getDia();
			mesPraCadastrar = dia.getMes();
			anoPraCadastrar = dia.getAno();
			tblAgendamentos.setEnabled(true);
			dia.setEstilo(ed);
			//System.out.println(dia.getDia());
			preencherTabelaAgendamento();
			populaCelulas();
		}

	}

	@Override
	public void doubleClickedDay(Dia dia) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEnteredDay(Dia dia) {
		dia.setCursor(new Cursor(Cursor.HAND_CURSOR));

	}

	@Override
	public void mouseExited(Dia dia) {
		dia.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource().equals(cmbCliente)) {
			if (cmbCliente.getSelectedItem() != null) {

				Cliente c = (Cliente) cmbCliente.getSelectedItem();
				lblImg.setIcon(null);
				lblInfoFoto.setText("");
				try {
					if (c.getFotoCliente().length() > 30) {
						lblImg.setIcon(new ImageIcon(
								setImagemDimensao(c.getFotoCliente(), lblImg.getWidth(), lblImg.getWidth())));
					} else {
						lblInfoFoto.setText("Foto não encontrada.");
					}
				} catch (Exception ex) {

				}
				if (c.getNome().length() > 31) {
					lblNomeCliente.setText("Nome: " + c.getNome().substring(0, 32) + "...");
					lblInfoNomeCompleto.setVisible(true);
					lblInfoNomeCompleto.setToolTipText(c.getNome());
				} else {
					lblNomeCliente.setText("Nome: " + c.getNome());
					lblInfoNomeCompleto.setVisible(false);
				}
				if (!c.getCpf().equals("___.___.___-__"))
					lblCpfCliente.setText("CPF: " + c.getCpf());
				else
					lblCpfCliente.setText("CPF: --------------");
				if (!c.getEmail().equals(""))
					lblEmailCliente.setText("E-Mail: " + c.getEmail());
				else
					lblEmailCliente.setText("E-Mail: --------------" + "-----------------------------------");
			} else {
				lblImg.setIcon(null);
				lblInfoFoto.setText("");
				lblNomeCliente.setText("");
				lblInfoNomeCompleto.setVisible(false);
				lblCpfCliente.setText("");
				lblEmailCliente.setText("");
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnAddCliente)){
			new JanelaCliente(null, this);
		}
		if (e.getSource().equals(btnAddServico)){
			new JanelaServico(null, null, this);
		}
	}

}
