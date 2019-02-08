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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
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

import br.com.royal.controller.CaixaController;
import br.com.royal.controller.EntradaCaixaController;
import br.com.royal.model.Caixa;
import br.com.royal.model.EntradaCaixa;
import br.com.royal.model.MotivoEntradaCaixa;

public class JanelaEntradaCaixa extends JFrame implements ActionListener,
		KeyListener, MouseListener, MouseMotionListener, WindowListener {

	private JNumberFormatField txtValorEntradaCaixa;
	private JLabel lblFundo, lblMinimize, lblClose, lblNavbar,
			lblValorEntradaCaixa, lblInfoCamposObrigatorios, lblSalvar,
			lblCancelar, lblNovo, lblRelatorioEntradaCaixa, lblDataEntrada,
			lblMotivosEntrada, lblValorCaixa;
	private JFormattedTextField txtDataEntradaCaixa;
	private JComboBox<MotivoEntradaCaixa> cmbMotivoEntradaCaixa;
	private MaskFormatter mascaraData;
	private JButton btnAvancar, btnVoltar, btnPrimLinha, btnUltLinha;
	private Color corOriginalBtn = new Color(164, 6, 69);
	private JTable tblListar;
	private FTable modelT = new FTable();
	private int posX, posY, cod = 0;
	private String txtBtn = "";// serve para salver se o usuario clicou no botao
								// "editar" ou no botao "novo", mudando assim a
								// função do botao "salvar"
	private ArrayList<EntradaCaixa> listaAtualTabela = new ArrayList<EntradaCaixa>();

	Date data = new Date();
	SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	private JanelaHome jh;
	
	private CaixaController caixaController = new CaixaController();

	public JanelaEntradaCaixa(JanelaHome jh) {
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}
		this.jh = jh;
		setTitle("Entrada de Caixa");
		setSize(820, 570);
		setResizable(false);
		setLayout(null);
		setUndecorated(true);
		setLocationRelativeTo(null);
		addWindowListener(this);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Img/Rosa.png"));
		IC();

		this.addKeyListener(this);
		setVisible(true);
	}

	private void IC() {
		criaTabela();
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		lblDataEntrada = new JLabel("*Data:");
		lblDataEntrada.setBounds(210, 140, 150, 30);
		lblDataEntrada
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(lblDataEntrada);

		lblValorCaixa = new JLabel("", JLabel.LEFT);
		lblValorCaixa.setBounds(lblDataEntrada.getX() - 17, 70, getWidth(), 30);
		lblValorCaixa.setFont(new Font("Century Gothic", Font.PLAIN, 24));
		lblValorCaixa.setForeground(new Color(158, 0, 57));
		add(lblValorCaixa);
		lblValorCaixa.setText("Valor atual do caixa: R$"
				+ String.format("%.2f", caixaController.getCaixaAtual().getValorAtual()));

		txtDataEntradaCaixa = new JFormattedTextField();
		txtDataEntradaCaixa.setBounds(lblDataEntrada.getX(),
				lblDataEntrada.getY() + 30, 150, 30);
		txtDataEntradaCaixa.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(txtDataEntradaCaixa);
		txtDataEntradaCaixa.addKeyListener(this);
		try {
			mascaraData = new MaskFormatter("##/##/####");
			mascaraData.setPlaceholderCharacter('_');
			mascaraData.install(txtDataEntradaCaixa);
		} catch (Exception ex) {
		}

		lblValorEntradaCaixa = new JLabel("*Valor:");
		lblValorEntradaCaixa.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		lblValorEntradaCaixa.setBounds(lblDataEntrada.getX() + 170,
				lblDataEntrada.getY(), 100, 30);
		add(lblValorEntradaCaixa);

		txtValorEntradaCaixa = new JNumberFormatField();
		txtValorEntradaCaixa.setLimit(7);
		txtValorEntradaCaixa.setBounds(lblValorEntradaCaixa.getX(),
				lblValorEntradaCaixa.getY() + 30, 150, 30);
		txtValorEntradaCaixa.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(txtValorEntradaCaixa);
		txtValorEntradaCaixa.addKeyListener(this);
		txtValorEntradaCaixa.setEnabled(false);

		lblMotivosEntrada = new JLabel("*Motivo:");
		lblMotivosEntrada.setBounds(lblDataEntrada.getX(),
				lblValorEntradaCaixa.getY() + 70, 100, 30);
		lblMotivosEntrada.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		add(lblMotivosEntrada);

		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
		cmbMotivoEntradaCaixa = new JComboBox<MotivoEntradaCaixa>();
		cmbMotivoEntradaCaixa.setBounds(lblMotivosEntrada.getX(),
				lblMotivosEntrada.getY() + 30, txtValorEntradaCaixa.getWidth(), 30);
		cmbMotivoEntradaCaixa.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 13));
		cmbMotivoEntradaCaixa.setEnabled(false);
		MotivoEntradaCaixa m = new MotivoEntradaCaixa();
		m.setDescricao("");
		cmbMotivoEntradaCaixa.addItem(m);
		for (MotivoEntradaCaixa ms : EntradaCaixaController.getMotivos())
			cmbMotivoEntradaCaixa.addItem(ms);

		add(cmbMotivoEntradaCaixa);
		
		try{
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
		lblInfoCamposObrigatorios = new JLabel("* Campos Obrigatórios");
		lblInfoCamposObrigatorios.setBounds(110, 360, 170, 30);
		lblInfoCamposObrigatorios.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblInfoCamposObrigatorios);

		lblNovo = new JLabel(new ImageIcon("Img/Entrada de Caixa/Nova.png"));
		lblNovo.setBounds(670, 80, 61, 52);
		lblNovo.setToolTipText("Novo");
		lblNovo.addMouseListener(this);
		add(lblNovo);

		lblSalvar = new JLabel(new ImageIcon("Img/Entrada de Caixa/Salvar.png"));
		lblSalvar.setBounds(lblNovo.getX(), lblNovo.getY() + 65, 61, 52);
		lblSalvar.setToolTipText("Salvar");
		lblSalvar.addMouseListener(this);
		lblSalvar.setEnabled(false);
		add(lblSalvar);

		lblCancelar = new JLabel(new ImageIcon("Img/Entrada de Caixa/Cancelar.png"));
		lblCancelar.setBounds(lblSalvar.getX() +5, lblSalvar.getY() + 65, 43, 43);
		lblCancelar.setToolTipText("Cancelar");
		lblCancelar.addMouseListener(this);
		add(lblCancelar);

		lblRelatorioEntradaCaixa = new JLabel(new ImageIcon("Img/RelatorioMenor.png"));
		lblRelatorioEntradaCaixa.setBounds(lblCancelar.getX()+5, lblCancelar.getY() + 65, 35, 40);
		lblRelatorioEntradaCaixa.setToolTipText("Relatório");
		lblRelatorioEntradaCaixa.addMouseListener(this);
		add(lblRelatorioEntradaCaixa);

		btnPrimLinha = new JButton("<<");
		btnPrimLinha.setBackground(corOriginalBtn);
		btnPrimLinha.setForeground(Color.WHITE);
		btnPrimLinha.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
		btnPrimLinha.setBounds(300, 345, 52, 40);
		btnPrimLinha.setToolTipText("Primeira linha");
		add(btnPrimLinha);
		btnPrimLinha.addActionListener(this);

		btnVoltar = new JButton("<");
		btnVoltar.setBackground(corOriginalBtn);
		btnVoltar.setForeground(Color.WHITE);
		btnVoltar.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
		btnVoltar.setBounds(btnPrimLinha.getX() + 53, btnPrimLinha.getY(),
				btnPrimLinha.getWidth() - 5, btnPrimLinha.getHeight());
		btnVoltar.setToolTipText("Voltar uma linha");
		add(btnVoltar);
		btnVoltar.addActionListener(this);

		btnAvancar = new JButton(">");
		btnAvancar.setBackground(corOriginalBtn);
		btnAvancar.setForeground(Color.WHITE);
		btnAvancar.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
		btnAvancar.setBounds(btnVoltar.getX() + 48, btnVoltar.getY(),
				btnVoltar.getWidth(), btnVoltar.getHeight());
		btnAvancar.setToolTipText("Avançar uma linha");
		add(btnAvancar);
		btnAvancar.addActionListener(this);

		btnUltLinha = new JButton(">>");
		btnUltLinha.setBackground(corOriginalBtn);
		btnUltLinha.setForeground(Color.WHITE);
		btnUltLinha.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
		btnUltLinha.setBounds(btnAvancar.getX() + 48, btnAvancar.getY(),
				btnPrimLinha.getWidth(), btnPrimLinha.getHeight());
		btnUltLinha.setToolTipText("Ultima linha");
		add(btnUltLinha);
		btnUltLinha.addActionListener(this);

		lblMinimize = new JLabel();
		lblMinimize.setBounds(742, 7, 35, 17);
		lblMinimize.addMouseListener(this);
		add(lblMinimize);

		lblClose = new JLabel();
		// lblClose.setOpaque(true);
		// lblClose.setBackground(Color.GREEN);
		lblClose.setBounds(782, 5, 32, 25);
		lblClose.addMouseListener(this);
		add(lblClose);

		lblNavbar = new JLabel();
		// lblNavbar.setOpaque(true);
		// lblNavbar.setBackground(Color.GREEN);
		lblNavbar.setBounds(0, 0, getWidth(), 41);
		lblNavbar.addMouseListener(this);
		lblNavbar.addMouseMotionListener(this);
		add(lblNavbar);

		lblFundo = new JLabel(
				new ImageIcon("Img/Entrada de Caixa.png"));
		lblFundo.setBounds(0, 0, getWidth(), getHeight());
		add(lblFundo);
	}

	private void criaTabela() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		tblListar = new JTable(modelT);
		tblListar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblListar.getTableHeader().setResizingAllowed(false);
		tblListar.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		tblListar.setSelectionBackground(new Color(222, 54, 121));
		tblListar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblListar.getTableHeader().setDefaultRenderer(new RTable());
		tblListar.addKeyListener(this);
		JScrollPane scroll = new JScrollPane(tblListar);
		scroll.setBounds(80, 400, 670, 130);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroll);

		modelT.addColumn("Valor");
		modelT.addColumn("Data");
		modelT.addColumn("Motivo");

		atualizaTabela(modelT);

		tblListar.getColumnModel().getColumn(0).setPreferredWidth(180);
		tblListar.getColumnModel().getColumn(1).setPreferredWidth(180);
		tblListar.getColumnModel().getColumn(2).setPreferredWidth(290);

		tblListar.getTableHeader().setReorderingAllowed(false);
		tblListar.addMouseListener(this);
		tblListar.getTableHeader().setFont(
				new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		((DefaultTableCellRenderer) tblListar.getTableHeader()
				.getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);

		for (int i = 0; i < tblListar.getColumnCount(); i++) {
			DefaultTableCellRenderer cellRender = new DefaultTableCellRenderer();
			cellRender.setHorizontalAlignment(SwingConstants.CENTER);
			tblListar.getColumnModel().getColumn(i).setCellRenderer(cellRender);
		}
	}

	private void atualizaTabelaPesquisa(DefaultTableModel modelo) {
		modelo.setNumRows(0);
		if (EntradaCaixaController.getEntradaCaixas(txtDataEntradaCaixa.getText()) != null) {
			listaAtualTabela = EntradaCaixaController
					.getEntradaCaixas(txtDataEntradaCaixa.getText());
			for (EntradaCaixa fp : EntradaCaixaController
					.getEntradaCaixas(txtDataEntradaCaixa.getText())) {
				modelo.addRow(new Object[] { fp.getValor(),
						fp.getDataEntradaCaixa(),
						EntradaCaixaController.getMotivo(fp.getCodMotivoEntrada()) });

			}
		}
	}

	private void atualizaTabela(DefaultTableModel modelo) {
		modelo.setNumRows(0);
		if (EntradaCaixaController.getEntradaCaixas() != null) {
			listaAtualTabela = EntradaCaixaController.getEntradaCaixas();
			for (EntradaCaixa fp : EntradaCaixaController.getEntradaCaixas()) {
				modelo.addRow(new Object[] { fp.getValor(),
						fp.getDataEntradaCaixa(),
						EntradaCaixaController.getMotivo(fp.getCodMotivoEntrada()) });

			}
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

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

	@Override
	public void mouseDragged(MouseEvent e) {
		setLocation(getLocation().x + e.getX() - posX,
				getLocation().y + e.getY() - posY);
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getSource().equals(lblMinimize)) {
			this.setExtendedState(JFrame.ICONIFIED);
		}

		else if (e.getSource().equals(lblClose)) {
			this.dispose();
		}

		if (e.getSource() == lblNovo && lblNovo.isEnabled()) {

			txtDataEntradaCaixa.setText("" + formatador.format(data));
			setEnableCamposTrue();

			lblSalvar.setEnabled(true);
			lblNovo.setEnabled(false);

			txtBtn = "Novo";
			tblListar.setEnabled(false);
		}

		if (e.getSource() == lblSalvar && lblSalvar.isEnabled()) {
			EntradaCaixa ec = new EntradaCaixa();
			try {
				if (verificaCamposPreenchidos()) {
					ec.setValor(Double.parseDouble(txtValorEntradaCaixa.getText().substring(3)
							.replace(",", ".")));
					ec.setDataEntradaCaixa(formatador.format(data));
					ec.setCodMotivoEntrada(EntradaCaixaController.getMotivo(
							cmbMotivoEntradaCaixa.getSelectedItem().toString())
							.getCod());
					if (txtBtn.equals("Novo")) {
						new CaixaController().cadastrarEntradaCaixa(ec);
						JOptionPane.showMessageDialog(this,
								" cadastrado com sucesso");
					}

					setEnableCamposFalse();
					txtBtn = "";
					limpaCampos();
					lblNovo.setEnabled(true);
					lblSalvar.setEnabled(false);
					tblListar.getSelectionModel().clearSelection();
					tblListar.setEnabled(true);
					atualizaTabela(modelT);
					txtValorEntradaCaixa.grabFocus();

//					jh.atualizaVisorCaixa();
					lblValorCaixa.setText("Valor atual do caixa: R$"
							+ String.format("%.2f", caixaController.getCaixaAtual().getValorAtual()));
				} else {
					JOptionPane
							.showMessageDialog(this,
									"Preencha todos os campos obrigatórios corretamente");
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(this,
						"Preencha todos os campos obrigatórios corretamente");
			}
		}

		if (e.getSource() == lblCancelar && lblCancelar.isEnabled()) {

			setEnableCamposFalse();
			limpaCampos();
			lblNovo.setEnabled(true);
			lblSalvar.setEnabled(false);
			atualizaTabela(modelT);
			tblListar.setEnabled(true);
			txtBtn = "";
			txtValorEntradaCaixa.grabFocus();
		}

		if (e.getSource().equals(lblRelatorioEntradaCaixa)
				&& lblRelatorioEntradaCaixa.isEnabled()) {
			
		}
	}

	private void setCamposDados(EntradaCaixa f) {
		cod = f.getCod();
		txtValorEntradaCaixa.setText(new DecimalFormat("0.00").format(f.getValor()));
		txtDataEntradaCaixa.setText(f.getDataEntradaCaixa());

		for (int i = 0; i < cmbMotivoEntradaCaixa.getItemCount(); i++)
			if (cmbMotivoEntradaCaixa
					.getItemAt(i)
					.toString()
					.equals(EntradaCaixaController.getMotivo(
							f.getCodMotivoEntrada()).getDescricao()))
				cmbMotivoEntradaCaixa.setSelectedIndex(i);
	}

	private void limpaCampos() {
		txtValorEntradaCaixa.setText("");
		txtDataEntradaCaixa.setText("");
		cmbMotivoEntradaCaixa.setSelectedIndex(0);
		cod = 0;
		txtBtn = "";
	}

	private boolean verificaCamposPreenchidos() {
		if (txtValorEntradaCaixa.getText().length() > 0
				&& txtDataEntradaCaixa.getText().replaceAll("[/ _]", "").length() > 0
				&& cmbMotivoEntradaCaixa.getSelectedIndex() > 0)
			return true;
		return false;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

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
		if (tblListar.getSelectedRow() != -1) {
			lblNovo.setEnabled(false);
			setCamposDados(EntradaCaixaController.getEntradaCaixa(listaAtualTabela
					.get(tblListar.getSelectedRow()).getCod()));
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (txtDataEntradaCaixa.hasFocus()) {
			pesquisa();
		}

		if (tblListar.hasFocus() && tblListar.isEnabled()) {
			if (e.getKeyCode() == e.VK_DOWN) {
				setCamposDados(EntradaCaixaController
						.getEntradaCaixa(listaAtualTabela.get(
								tblListar.getSelectedRow()).getCod()));
			} else if (e.getKeyCode() == e.VK_UP) {
				setCamposDados(EntradaCaixaController
						.getEntradaCaixa(listaAtualTabela.get(
								tblListar.getSelectedRow()).getCod()));
			}
			tblListar.grabFocus();
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnPrimLinha) {
			tblListar.setRowSelectionInterval(0, 0);
			setCamposDados(EntradaCaixaController.getEntradaCaixa(listaAtualTabela
					.get(tblListar.getSelectedRow()).getCod()));
			lblNovo.setEnabled(false);
			tblListar.grabFocus();

		}

		if (e.getSource() == btnUltLinha) {
			tblListar.setRowSelectionInterval(tblListar.getModel()
					.getRowCount() - 1, tblListar.getModel().getRowCount() - 1);
			setCamposDados(EntradaCaixaController.getEntradaCaixa(listaAtualTabela
					.get(tblListar.getSelectedRow()).getCod()));
			lblNovo.setEnabled(false);
			tblListar.grabFocus();

		}

		if (e.getSource() == btnAvancar) {
			if (tblListar.getSelectedRow() < tblListar.getModel().getRowCount() - 1) {
				tblListar.setRowSelectionInterval(
						tblListar.getSelectedRow() + 1,
						tblListar.getSelectedRow() + 1);
				setCamposDados(EntradaCaixaController
						.getEntradaCaixa(listaAtualTabela.get(
								tblListar.getSelectedRow()).getCod()));
				tblListar.grabFocus();

				lblNovo.setEnabled(false);
			}
		}

		if (e.getSource() == btnVoltar) {
			if (tblListar.getSelectedRow() > 0) {
				tblListar.setRowSelectionInterval(
						tblListar.getSelectedRow() - 1,
						tblListar.getSelectedRow() - 1);

				// tblListar.setSelectionMode(0);

				lblNovo.setEnabled(false);

				setCamposDados(EntradaCaixaController
						.getEntradaCaixa(listaAtualTabela.get(
								tblListar.getSelectedRow()).getCod()));
				tblListar.grabFocus();
			} else {
				tblListar.clearSelection();
				limpaCampos();
				lblNovo.setEnabled(true);
			}
		}
	}

	private void setEnableCamposTrue() {
		txtValorEntradaCaixa.setEnabled(true);
		cmbMotivoEntradaCaixa.setEnabled(true);
		btnPrimLinha.setEnabled(false);
		btnUltLinha.setEnabled(false);
		btnVoltar.setEnabled(false);
		btnAvancar.setEnabled(false);
		lblRelatorioEntradaCaixa.setEnabled(false);
	}

	private void setEnableCamposFalse() {
		txtValorEntradaCaixa.setEnabled(false);
		cmbMotivoEntradaCaixa.setEnabled(false);
		btnPrimLinha.setEnabled(true);
		btnUltLinha.setEnabled(true);
		btnVoltar.setEnabled(true);
		btnAvancar.setEnabled(true);
		verificaRelatorio();
	}

	public boolean verificaRelatorio() {
		if (tblListar.getRowCount() == 0) {
			lblRelatorioEntradaCaixa.setEnabled(false);
			return false;
		}
		lblRelatorioEntradaCaixa.setEnabled(true);
		return true;
	}

	private void pesquisa() {
		if (txtDataEntradaCaixa.getText().replaceAll("[/_]", "").length() > 0) {
			atualizaTabelaPesquisa(modelT);
		} else
			atualizaTabela(modelT);
	}

}