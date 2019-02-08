package br.com.royal.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import br.com.royal.controller.HorarioController;
import br.com.royal.model.Horario;

public class JanelaHorario extends JDialog
		implements ActionListener, MouseListener, MouseMotionListener, KeyListener, WindowListener {
	private JanelaAgendamento ja;
	private JLabel lblHorario, lblFundo, lblNavbar, lblClose, lblNovoHorario, lblSalvarHorario, lblEditarHorario,
			lblExcluirHorario, lblCancelar, lblDataReferencia;
	private JFormattedTextField txtHoraInicio;
	private MaskFormatter mskHoraInicio = null;
	private HorarioController hController = new HorarioController();
	private Color corOriginalBtn = new Color(164, 6, 69);
	private JTable tabela;
	private JScrollPane scrollTabela;
	private FTable modelo = new FTable();
	private String txtBtn = "";
	private JButton btnPrimLinha, btnVoltar, btnAvancar, btnUltLinha;
	private String horarioInicio = "", dataHorario="", retornoHoraInicio="";
	private int posX, posY;

	public JanelaHorario(JanelaAgendamento ja) {
		super(ja, true);
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		this.ja = ja;

		setLayout(null);
		setSize(602, 420);
		setTitle("Horário");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setUndecorated(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Img/Rosa.png"));
		inicializarComponentes();
		setVisible(true);
		addWindowListener(this);
	}

	public void inicializarComponentes() {
		criaTabela();
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		lblHorario = new JLabel("Horário:");
		lblHorario.setForeground(Color.BLACK);
		// lblHorario.setOpaque(true);
		// lblHorario.setBackground(Color.GREEN);
		lblHorario.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 19));
		lblHorario.setBounds(257, 110, 105, 20);
		add(lblHorario);

		txtHoraInicio = new JFormattedTextField();
		txtHoraInicio.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		txtHoraInicio.setBounds(lblHorario.getX() - 15, lblHorario.getY() +30, 100, 30);
		txtHoraInicio.setHorizontalAlignment(SwingConstants.CENTER);
		txtHoraInicio.addKeyListener(this);
		add(txtHoraInicio);

		try {
			mskHoraInicio = new MaskFormatter("##:##");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		mskHoraInicio.setPlaceholderCharacter('-');
		mskHoraInicio.install(txtHoraInicio);

		lblNovoHorario = new JLabel(new ImageIcon("Img/Horário/Novo.png"));
		lblNovoHorario.setForeground(Color.WHITE);
		lblNovoHorario.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		lblNovoHorario.setBounds(480, 40, 40, 43);
		lblNovoHorario.setToolTipText("Novo");
		lblNovoHorario.addMouseListener(this);
		add(lblNovoHorario);

		lblSalvarHorario = new JLabel(new ImageIcon("Img/Horário/Salvar.png"));
		lblSalvarHorario.setForeground(Color.WHITE);
		lblSalvarHorario.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		lblSalvarHorario.setBounds(lblNovoHorario.getX(), lblNovoHorario.getY() + 45, 40, 43);
		lblSalvarHorario.setToolTipText("Salvar");
		lblSalvarHorario.addMouseListener(this);
		add(lblSalvarHorario);

		lblEditarHorario = new JLabel(new ImageIcon("Img/Horário/Editar.png"));
		lblEditarHorario.setForeground(Color.WHITE);
		lblEditarHorario.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		lblEditarHorario.setBounds(lblNovoHorario.getX(), lblSalvarHorario.getY() + 45, 40, 43);
		lblEditarHorario.setToolTipText("Editar");
		lblEditarHorario.addMouseListener(this);
		add(lblEditarHorario);
		
		lblExcluirHorario = new JLabel(new ImageIcon("Img/Horário/Excluir.png"));
		lblExcluirHorario.setForeground(Color.WHITE);
		lblExcluirHorario.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		lblExcluirHorario.setBounds(lblEditarHorario.getX(), lblEditarHorario.getY() + 45, 40, 43);
		lblExcluirHorario.setToolTipText("Editar");
		lblExcluirHorario.addMouseListener(this);
		add(lblExcluirHorario);
		
		lblCancelar = new JLabel(new ImageIcon("Img/CancelarMenor.png"));
		lblCancelar.setForeground(Color.WHITE);
		lblCancelar.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		lblCancelar.setBounds(lblExcluirHorario.getX()+2, lblExcluirHorario.getY() + 48, 33, 33);
		lblCancelar.setToolTipText("Cancelar");
		lblCancelar.addMouseListener(this);
		add(lblCancelar);

		btnPrimLinha = new JButton("<<");
		btnPrimLinha.setBackground(corOriginalBtn);
		btnPrimLinha.setForeground(Color.WHITE);
		btnPrimLinha.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
		btnPrimLinha.setToolTipText("Primeira linha");
		btnPrimLinha.setBounds(190, 225, 53, 40);
		add(btnPrimLinha);
		btnPrimLinha.addActionListener(this);

		btnVoltar = new JButton("<");
		btnVoltar.setBackground(corOriginalBtn);
		btnVoltar.setForeground(Color.WHITE);
		btnVoltar.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
		btnVoltar.setBounds(btnPrimLinha.getX() + 55, btnPrimLinha.getY(), btnPrimLinha.getWidth() - 10,
				btnPrimLinha.getHeight());
		btnVoltar.setToolTipText("Voltar uma linha");
		add(btnVoltar);
		btnVoltar.addActionListener(this);

		btnAvancar = new JButton(">");
		btnAvancar.setBackground(corOriginalBtn);
		btnAvancar.setForeground(Color.WHITE);
		btnAvancar.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
		btnAvancar.setBounds(btnVoltar.getX() + 50, btnVoltar.getY(), btnVoltar.getWidth(), btnVoltar.getHeight());
		btnAvancar.setToolTipText("Avançar linha");
		add(btnAvancar);
		btnAvancar.addActionListener(this);

		btnUltLinha = new JButton(">>");
		btnUltLinha.setBackground(corOriginalBtn);
		btnUltLinha.setForeground(Color.WHITE);
		btnUltLinha.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
		btnUltLinha.setBounds(btnAvancar.getX() + 45, btnAvancar.getY(), btnPrimLinha.getWidth(),
				btnAvancar.getHeight());

		btnUltLinha.setToolTipText("Ultima linha");
		add(btnUltLinha);
		btnUltLinha.addActionListener(this);

		lblEditarHorario.setEnabled(false);

		lblNovoHorario.setEnabled(true);

		lblSalvarHorario.setEnabled(false);
		
		lblExcluirHorario.setEnabled(false);

		lblClose = new JLabel();
		// lblClose.setOpaque(true);
		// lblClose.setBackground(Color.GREEN);
		lblClose.setBounds(577, 3, 22, 20);
		lblClose.addMouseListener(this);
		add(lblClose);

		lblNavbar = new JLabel();
		// lblNavbar.setOpaque(true);
		// lblNavbar.setBackground(Color.GREEN);
		lblNavbar.setBounds(0, 0, getWidth(), 33);
		lblNavbar.addMouseListener(this);
		lblNavbar.addMouseMotionListener(this);
		add(lblNavbar);
		
		lblDataReferencia = new JLabel();
		lblDataReferencia.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
//		lblDataReferencia.setOpaque(true);
//		lblDataReferencia.setBackground(Color.GREEN);
		lblDataReferencia.setBounds(lblHorario.getX()-75, lblHorario.getY()-80, 225, 30);
		add(lblDataReferencia);
		
		preencheLabelReferencia();
		
		lblFundo = new JLabel(new ImageIcon("Img/Gerenciamento de Horários.png"));
		lblFundo.setBounds(0, 0, getWidth(), getHeight());
		add(lblFundo);
	}

	private void criaTabela() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		tabela = new JTable(modelo);
		tabela.addKeyListener(this);
		tabela.addMouseListener(this);
		tabela.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabela.setSelectionBackground(new Color(222, 54, 121));
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.getTableHeader().setDefaultRenderer(new RTable());
		scrollTabela = new JScrollPane(tabela);

		// scrollTabela.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		scrollTabela.setBounds(60, 270, 480, 130);
		tabela.getTableHeader().setResizingAllowed(false);

		add(scrollTabela);

		modelo.addColumn("Horário");
		modelo.addColumn("Data de Funcionamento");

		preencherJTable(modelo);

		tabela.getColumnModel().getColumn(0).setPreferredWidth(242);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(241);
		tabela.getTableHeader().setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		((DefaultTableCellRenderer) tabela.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);
		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		for (int i = 0; i < tabela.getColumnCount(); i++) {
			DefaultTableCellRenderer cellRender = new DefaultTableCellRenderer();
			cellRender.setHorizontalAlignment(SwingConstants.CENTER);
			tabela.getColumnModel().getColumn(i).setCellRenderer(cellRender);
		}

		tabela.getTableHeader().setReorderingAllowed(false);
	}

	public void preencherJTable(DefaultTableModel modelo) {
		modelo.setRowCount(0);
		for (Horario h : hController.listarHorariosGerais()) {
			String data = h.getDataHorario();
			modelo.addRow(new Object[] { h , data});

		}

	}

	public void pesquisaJTable(DefaultTableModel modelo) {
		modelo.setRowCount(0);
		for (Horario h : hController.pesquisaHorario(txtHoraInicio.getText().replace("-", ""))) {
			String data = h.getDataHorario();
			if (data==null)
				data="TODAS";
			modelo.addRow(new Object[] { h, data });
		}

	}

	public boolean inserir() {
		try {
			if (txtHoraInicio.getText().length() > 0) {
				Horario h = new Horario();
				if (validaHorario()){
					Object opcoesCadastro [] = new Object[]{"Apenas a de referência", 
																	"Todas as datas"};
					h.setHoraInicio(retornoHoraInicio);
					if (JOptionPane.showOptionDialog(null, "Em quais datas este haverá esse "
						+ "horário?", "Confirmar horário", JOptionPane.UNDEFINED_CONDITION, 
						JOptionPane.QUESTION_MESSAGE, null, opcoesCadastro, opcoesCadastro)==0){
						String data = lblDataReferencia.getText();
						h.setDataHorario(data.substring(data.length()-10));
					}else{
						h.setDataHorario("TODAS");
					}
					if (!hController.jaExiste(h)) {
						if (hController.jaExisteInativo(h)!=null){
							hController.reativaHorario(h);
							JOptionPane.showMessageDialog(null, "Horário inserido com sucesso!");
							preencherJTable(modelo);
							return true;
						}else if (hController.verificaSubstituicao(h)!=null){
							h = hController.verificaSubstituicao(h);
							hController.setaHorarioPraTodasAsDatas(h);
							JOptionPane.showMessageDialog(null, "Horário inserido com sucesso!");
							preencherJTable(modelo);
							return true;
						}else{
							hController.inserir(h);
							JOptionPane.showMessageDialog(null, "Horário inserido com sucesso!");
							preencherJTable(modelo);
							return true;
						}
					} else {
						JOptionPane.showMessageDialog(null, "Horário já cadastrado, insira outro!");
						txtHoraInicio.setText("");
						return false;
					}
				}
				return false;
			} else {
				JOptionPane.showMessageDialog(null, "O campo 'horário de início' é obrigatório!");
				return false;
			}
		} catch (Exception e) {
			System.out.println("Erroooow:" + e.getMessage());
			return false;
		}

	}

	public boolean editar() {
	 try {
		if (txtHoraInicio.getText().length() > 0) {
			Horario h = new Horario();
			h.setHoraInicio(horarioInicio);
			h.setDataHorario(dataHorario);
			hController.buscaIdHorario(h);
			if (validaHorario()){
				h.setHoraInicio(retornoHoraInicio);
				if (!hController.verificaDuplicidadeNaEdicao(h)) {
					hController.editarHorario(h);
					JOptionPane.showMessageDialog(null, "Horário editado com sucesso!");
					preencherJTable(modelo);
					return true;

				} else {
					JOptionPane.showMessageDialog(null, "Horário já cadastrado, insira outro!");
					txtHoraInicio.setText("");
					return false;
				}
			}
			return false;
		} else {
			JOptionPane.showMessageDialog(null, "O campo 'hora de início' é obrigatório!");
			return false;
		}
		} catch (Exception e) {
		System.out.println("Erroooow:" + e.getMessage());
		return false;
	}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnPrimLinha) {
			lblNovoHorario.setEnabled(false);
			tabela.setRowSelectionInterval(0, 0);
			lblEditarHorario.setEnabled(true);
			lblExcluirHorario.setEnabled(true);
			campoFalse();

			dadosTabela();
			tabela.grabFocus();

		}
		if (e.getSource() == btnUltLinha) {
			lblNovoHorario.setEnabled(false);
			tabela.setRowSelectionInterval(tabela.getModel().getRowCount() - 1, tabela.getModel().getRowCount() - 1);
			lblEditarHorario.setEnabled(true);
			lblExcluirHorario.setEnabled(true);
			campoFalse();

			dadosTabela();
			tabela.grabFocus();

		}
		if (e.getSource() == btnAvancar) {
			if (tabela.getSelectedRow() < tabela.getModel().getRowCount() - 1) {
				lblNovoHorario.setEnabled(false);
				tabela.setRowSelectionInterval(tabela.getSelectedRow() + 1, tabela.getSelectedRow() + 1);
				lblEditarHorario.setEnabled(true);
				lblExcluirHorario.setEnabled(true);
				campoFalse();
				dadosTabela();
				tabela.grabFocus();

			}
		}
		if (e.getSource() == btnVoltar) {
			lblNovoHorario.setEnabled(false);
			if (tabela.getSelectedRow() > 0) {
				tabela.setRowSelectionInterval(tabela.getSelectedRow() - 1, tabela.getSelectedRow() - 1);
				lblEditarHorario.setEnabled(true);
				lblExcluirHorario.setEnabled(true);
				campoFalse();
				dadosTabela();
				tabela.grabFocus();

			} else {
				tabela.clearSelection();
				lblNovoHorario.setEnabled(true);
				txtHoraInicio.setEnabled(true);
				lblEditarHorario.setEnabled(false);
				lblExcluirHorario.setEnabled(false);
				limpaCampos();
			}
		}

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
		if (e.getSource().equals(lblClose)) {
			this.dispose();
		}

		if (e.getSource().equals(lblCancelar) && lblCancelar.isEnabled()) {
			limpaCampos();
			btnPrimLinha.setEnabled(true);
			btnVoltar.setEnabled(true);
			btnAvancar.setEnabled(true);
			btnUltLinha.setEnabled(true);
			lblExcluirHorario.setEnabled(false);
			lblEditarHorario.setEnabled(false);
			lblNovoHorario.setEnabled(true);
			lblSalvarHorario.setEnabled(false);
			txtBtn = "";
			pesquisa();
			tabela.setEnabled(true);
			txtHoraInicio.setEnabled(true);
			txtHoraInicio.grabFocus();

		}
		if (e.getSource().equals(lblNovoHorario) && lblNovoHorario.isEnabled()) {
			btnPrimLinha.setEnabled(false);
			btnVoltar.setEnabled(false);
			btnAvancar.setEnabled(false);
			btnUltLinha.setEnabled(false);
			tabela.clearSelection();
			txtHoraInicio.setEnabled(true);
			lblSalvarHorario.setEnabled(true);
			lblEditarHorario.setEnabled(false);
			lblExcluirHorario.setEnabled(false);
			lblNovoHorario.setEnabled(false);
			txtBtn = "Novo";
			tabela.setEnabled(false);
			if (txtHoraInicio.getText().length() < 1) {
				txtHoraInicio.grabFocus();
			}
		}

		if (e.getSource().equals(lblSalvarHorario) && lblSalvarHorario.isEnabled()) {

			if (txtBtn.equals("Novo")) {
				if (inserir()) {
					txtBtn = "";
					limpaCampos();

					lblEditarHorario.setEnabled(false);
					lblExcluirHorario.setEnabled(false);
					lblNovoHorario.setEnabled(true);
					lblSalvarHorario.setEnabled(false);
					tabela.getSelectionModel().clearSelection();
					tabela.setEnabled(true);
					// preencherJtable(modelo);
					txtHoraInicio.grabFocus();
					txtHoraInicio.setEnabled(true);
					btnPrimLinha.setEnabled(true);
					btnVoltar.setEnabled(true);
					btnAvancar.setEnabled(true);
					btnUltLinha.setEnabled(true);
					tabela.setEnabled(true);
				} else {

				}
			} else {
				if (editar()) {
					txtBtn = "";
					limpaCampos();

					lblEditarHorario.setEnabled(false);
					lblExcluirHorario.setEnabled(false);
					lblNovoHorario.setEnabled(true);
					lblSalvarHorario.setEnabled(false);
					tabela.getSelectionModel().clearSelection();
					tabela.setEnabled(true);
					txtHoraInicio.setEnabled(true);
					// preencherJtable(modelo);
					txtHoraInicio.grabFocus();
					tabela.setEnabled(true);

				}
			}
		}
		if (e.getSource().equals(lblEditarHorario) && lblEditarHorario.isEnabled()) {
			btnPrimLinha.setEnabled(false);
			btnVoltar.setEnabled(false);
			btnAvancar.setEnabled(false);
			horarioInicio = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();
			dataHorario = tabela.getValueAt(tabela.getSelectedRow(), 1).toString();
			btnUltLinha.setEnabled(false);
			if (tabela.getSelectedRow() != -1) {
				txtHoraInicio.setEnabled(true);

				lblSalvarHorario.setEnabled(true);
				// setando enable = false nos botoes novo, editar e excluir
				lblNovoHorario.setEnabled(false);
				lblEditarHorario.setEnabled(false);
				lblExcluirHorario.setEnabled(false);
				dadosTabela();

				txtBtn = "Editar";

				tabela.setEnabled(false);
				tabela.getSelectionModel().clearSelection();

				txtHoraInicio.grabFocus();

			} else {
				JOptionPane.showMessageDialog(null, "Selecione um Fabricante para editar");
			}

		}
		
		if (e.getSource().equals(lblExcluirHorario) && lblExcluirHorario.isEnabled()){
			horarioInicio = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();
			dataHorario = tabela.getValueAt(tabela.getSelectedRow(), 1).toString();
			Horario h = new Horario();
			h.setHoraInicio(horarioInicio);
			h.setDataHorario(dataHorario);
			h.setCodHorario(hController.buscaIdHorario(h).getCodHorario());
			if (JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover o horário?")==JOptionPane.YES_OPTION){
				hController.excluirHorario(h);
				tabela.getSelectionModel().clearSelection();
				preencherJTable(modelo);
				limpaCampos();
				txtHoraInicio.setEnabled(true);
				txtHoraInicio.grabFocus();
				lblExcluirHorario.setEnabled(false);
				lblEditarHorario.setEnabled(false);
				lblNovoHorario.setEnabled(true);
			}
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource().equals(lblNavbar)) {
			posX = e.getX();
			posY = e.getY();
		} else if (tabela.getSelectedRow() != -1) {
			dadosTabela();
			campoFalse();
			lblNovoHorario.setEnabled(false);
			lblEditarHorario.setEnabled(true);
			lblExcluirHorario.setEnabled(true);
		} else {
			lblEditarHorario.setEnabled(false);
			lblExcluirHorario.setEnabled(false);
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void limpaCampos() {
		txtHoraInicio.setText("");
	}

	public void dadosTabela() {
		txtHoraInicio.setText(tabela.getValueAt(tabela.getSelectedRow(), 0).toString());
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (tabela.isEnabled() && tabela.getSelectedRow() > -1) {
			if (e.getKeyCode() == e.VK_DOWN || e.getKeyCode() == e.VK_RIGHT)
				dadosTabela();
			if (e.getKeyCode() == e.VK_UP || e.getKeyCode() == e.VK_LEFT)
				dadosTabela();
		}

		if (txtHoraInicio.hasFocus() && txtBtn.equals("")) {
			pesquisa();
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void pesquisa() {
		if (txtHoraInicio.getText().length() > 0) {
			pesquisaJTable(modelo);

		} else if (txtHoraInicio.getText().replaceAll("[-:]", "").length() == 0) {
			preencherJTable(modelo);
		}

	}

	public void campoFalse() {
		txtHoraInicio.setEnabled(false);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		ja.preencherTabelaAgendamento();
		ja.populaCelulas();
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	private void preencheLabelReferencia(){
		int diaI = ja.getDiaPraCadastrar(), mesI = ja.getMesPraCadastrar(), 
											anoI = ja.getAnoPraCadastrar();
		String diaRF="", mesRF="", anoRF="";
		if (diaI<10)
			diaRF="0"+diaI;
		else
			diaRF=diaI+"";
		if (mesI<10)
			mesRF="0"+mesI;
		else
			mesRF=mesI+"";
		anoRF=anoI+"";
		lblDataReferencia.setText("Data de Referência: "+diaRF+"/"+mesRF+"/"+anoRF);
	}
	
	public boolean validaHorario(){
		if (txtHoraInicio.getText().replaceAll("[-:]","").length()<4){
			JOptionPane.showMessageDialog(null, "Um horário deve conter 2 dígitos pra minutos "
					+ "e dois digitos pra horas!");
			return false;	
		}else{
			String horaS="", minutoS="";
			int hora = Integer.parseInt(txtHoraInicio.getText().substring(0,2));
			int minuto = Integer.parseInt(txtHoraInicio.getText().substring(3));
			if (hora<0 || hora>24 || minuto<0 || minuto>59){
				JOptionPane.showMessageDialog(null, "Horário inválido!");
				return false;
			}
			else if (hora==24)
				horaS="00";
			else if (hora<10)
				horaS="0"+hora;
			else
				horaS=hora+"";
			if (minuto<10)
				minutoS="0"+minuto;
			else
				minutoS=minuto+"";
			retornoHoraInicio = horaS+":"+minutoS;
			return true;
		}
	}

}
