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
import br.com.royal.controller.SaidaCaixaController;
import br.com.royal.model.MotivoSaidaCaixa;
import br.com.royal.model.SaidaCaixa;

public class JanelaSaidaCaixa extends JFrame implements ActionListener,
		KeyListener, MouseListener, MouseMotionListener, WindowListener {

	private JNumberFormatField txtValorSaidaCaixa;
	private JLabel lblFundo, lblMinimize, lblClose, lblNavbar,
			lblValorSaidaCaixa, lblInfoCamposObrigatorios, lblSalvar,
			lblCancelar, lblNovo, lblRelatorioSaidaCaixa, lblDataSaida,
			lblMotivosSaida,lblValorCaixa ;
	private JFormattedTextField txtDataSaidaCaixa;
	private JComboBox<MotivoSaidaCaixa> cmbMotivoSaidaCaixa;
	private MaskFormatter mascaraData;
	private JButton btnAvancar, btnVoltar, btnPrimLinha, btnUltLinha;
	private Color corOriginalBtn = new Color(164, 6, 69);
	private JTable tblListar;
	private FTable modelT = new FTable();
	private int posX, posY, cod = 0;
	private String txtBtn = "";// serve para salver se o usuario clicou no botao
								// "editar" ou no botao "novo", mudando assim a
								// função do botao "salvar"
	private ArrayList<SaidaCaixa> listaAtualTabela = new ArrayList<SaidaCaixa>();

	Date data = new Date();
	SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	private JanelaHome jh;
	
	CaixaController caixaController = new CaixaController();

	public JanelaSaidaCaixa(JanelaHome jh) {
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}
		this.jh = jh;
		setTitle("Cadastro Saida Caixa");
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

		lblDataSaida = new JLabel("*Data:");
		lblDataSaida.setBounds(210, 140, 150, 30);
		lblDataSaida
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(lblDataSaida);
		
		lblValorCaixa = new JLabel("", JLabel.LEFT);
		lblValorCaixa.setBounds(lblDataSaida.getX()-17, 70, getWidth(), 30);
		lblValorCaixa.setFont(new Font("Century Gothic", Font.PLAIN, 24));
		lblValorCaixa.setForeground(new Color(158, 0, 57));
		add(lblValorCaixa);
		lblValorCaixa.setText("Valor atual do caixa: R$"
				+ String.format("%.2f", caixaController.getCaixaAtual().getValorAtual()));

		txtDataSaidaCaixa = new JFormattedTextField();
		txtDataSaidaCaixa.setBounds(lblDataSaida.getX(),
				lblDataSaida.getY() + 30, 150, 30);
		txtDataSaidaCaixa.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(txtDataSaidaCaixa);
		txtDataSaidaCaixa.addKeyListener(this);
		try {
			mascaraData = new MaskFormatter("##/##/####");
			mascaraData.setPlaceholderCharacter('_');
			mascaraData.install(txtDataSaidaCaixa);
		} catch (Exception ex) {
		}

		lblValorSaidaCaixa = new JLabel("*Valor:");
		lblValorSaidaCaixa.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		lblValorSaidaCaixa.setBounds(lblDataSaida.getX() + 170,
				lblDataSaida.getY(), 100, 30);
		add(lblValorSaidaCaixa);

		txtValorSaidaCaixa = new JNumberFormatField();
		txtValorSaidaCaixa.setLimit(7);
		txtValorSaidaCaixa.setBounds(lblValorSaidaCaixa.getX(),
				lblValorSaidaCaixa.getY() + 30, 150, 30);
		txtValorSaidaCaixa.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(txtValorSaidaCaixa);
		txtValorSaidaCaixa.addKeyListener(this);
		txtValorSaidaCaixa.setEnabled(false);

		lblMotivosSaida = new JLabel("*Motivo:");
		lblMotivosSaida.setBounds(lblDataSaida.getX(),
				lblValorSaidaCaixa.getY() + 70, 100, 30);
		lblMotivosSaida.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		add(lblMotivosSaida);
		
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
		cmbMotivoSaidaCaixa = new JComboBox<MotivoSaidaCaixa>();
		cmbMotivoSaidaCaixa.setBounds(lblMotivosSaida.getX(),
				lblMotivosSaida.getY() + 30, txtValorSaidaCaixa.getWidth(), 30);
		cmbMotivoSaidaCaixa.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 13));
		cmbMotivoSaidaCaixa.setEnabled(false);
		MotivoSaidaCaixa m = new MotivoSaidaCaixa();
		m.setDescricao("");
		cmbMotivoSaidaCaixa.addItem(m);
		for (MotivoSaidaCaixa ms : SaidaCaixaController.getMotivos())
			cmbMotivoSaidaCaixa.addItem(ms);

		add(cmbMotivoSaidaCaixa);
		
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

		lblNovo = new JLabel(new ImageIcon("Img/Saída de Caixa/Nova.png"));
		lblNovo.setBounds(670, 80, 61, 52);
		lblNovo.setToolTipText("Novo");
		lblNovo.addMouseListener(this);
		add(lblNovo);

		lblSalvar = new JLabel(new ImageIcon("Img/Saída de Caixa/Salvar.png"));
		lblSalvar.setBounds(lblNovo.getX(), lblNovo.getY() + 65, 61, 52);
		lblSalvar.setToolTipText("Salvar");
		lblSalvar.addMouseListener(this);
		lblSalvar.setEnabled(false);
		add(lblSalvar);

		lblCancelar = new JLabel(new ImageIcon("Img/Saída de Caixa/Cancelar.png"));
		lblCancelar.setBounds(lblSalvar.getX() +10, lblSalvar.getY() + 65, 43, 43);
		lblCancelar.setToolTipText("Cancelar");
		lblCancelar.addMouseListener(this);
		add(lblCancelar);

		lblRelatorioSaidaCaixa = new JLabel(new ImageIcon("Img/RelatorioMenor.png"));
		lblRelatorioSaidaCaixa.setBounds(lblCancelar.getX()+5, lblCancelar.getY() + 65, 35, 40);
		lblRelatorioSaidaCaixa.setToolTipText("Relatório");
		lblRelatorioSaidaCaixa.addMouseListener(this);
		add(lblRelatorioSaidaCaixa);

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
				new ImageIcon("Img/Saída de Caixa.png"));
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
		if (SaidaCaixaController.getSaidaCaixas(txtDataSaidaCaixa.getText()) != null) {
			listaAtualTabela = SaidaCaixaController
					.getSaidaCaixas(txtDataSaidaCaixa.getText());
			for (SaidaCaixa fp : SaidaCaixaController
					.getSaidaCaixas(txtDataSaidaCaixa.getText())) {
				modelo.addRow(new Object[] { fp.getValor(),
						fp.getDataSaidaCaixa(),
						SaidaCaixaController.getMotivo(fp.getCodMotivoSaida()) });

			}
		}
	}

	private void atualizaTabela(DefaultTableModel modelo) {
		modelo.setNumRows(0);
		if (SaidaCaixaController.getSaidaCaixas() != null) {
			listaAtualTabela = SaidaCaixaController.getSaidaCaixas();
			for (SaidaCaixa fp : SaidaCaixaController.getSaidaCaixas()) {
				modelo.addRow(new Object[] { fp.getValor(),
						fp.getDataSaidaCaixa(),
						SaidaCaixaController.getMotivo(fp.getCodMotivoSaida()) });

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

			txtDataSaidaCaixa.setText("" + formatador.format(data));
			setEnableCamposTrue();

			lblSalvar.setEnabled(true);
			lblNovo.setEnabled(false);

			txtBtn = "Novo";
			tblListar.setEnabled(false);
		}

		if (e.getSource() == lblSalvar && lblSalvar.isEnabled()) {
			SaidaCaixa sc = new SaidaCaixa();
			try {
				if (verificaCamposPreenchidos()) {
					sc.setValor(Double.parseDouble(txtValorSaidaCaixa.getText().substring(3)
							.replace(",", ".")));
					sc.setDataSaidaCaixa(formatador.format(data));
					sc.setCodMotivoSaida(SaidaCaixaController.getMotivo(
							cmbMotivoSaidaCaixa.getSelectedItem().toString())
							.getCod());
					sc.setCodCaixa(new CaixaController().getUltimoCaixa().getCod());
					if (txtBtn.equals("Novo")) {
						SaidaCaixaController.cadastrar(sc);
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
					txtValorSaidaCaixa.grabFocus();

//					jh.atualizaVisorCaixa();
					lblValorCaixa.setText("Valor atual do caixa: R$"
							+ String.format("%.2f", caixaController.getCaixaAtual().getValorAtual()));
				} else {
					JOptionPane
							.showMessageDialog(this,
									"Preencha todos os campos obrigatórios corretamente");
				}
			} catch (Exception ex) {
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
			txtValorSaidaCaixa.grabFocus();
		}

	}

	private void setCamposDados(SaidaCaixa f) {
		cod = f.getCod();
		txtValorSaidaCaixa.setText(new DecimalFormat("0.00").format(f.getValor()));
		txtDataSaidaCaixa.setText(f.getDataSaidaCaixa());

		for (int i = 0; i < cmbMotivoSaidaCaixa.getItemCount(); i++)
			if (cmbMotivoSaidaCaixa.getItemAt(i).toString().equals(
					SaidaCaixaController.getMotivo(f.getCodMotivoSaida())
							.getDescricao()))
				cmbMotivoSaidaCaixa.setSelectedIndex(i);
	}

	private void limpaCampos() {
		txtValorSaidaCaixa.setText("");
		txtDataSaidaCaixa.setText("");
		cmbMotivoSaidaCaixa.setSelectedIndex(0);
		cod = 0;
		txtBtn = "";
	}

	private boolean verificaCamposPreenchidos() {
		if (txtValorSaidaCaixa.getText().length() > 0
				&& txtDataSaidaCaixa.getText().replaceAll("[/ _]", "").length() > 0
				&& cmbMotivoSaidaCaixa.getSelectedIndex() > 0)
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
			setCamposDados(SaidaCaixaController.getSaidaCaixa(listaAtualTabela
					.get(tblListar.getSelectedRow()).getCod()));
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (txtDataSaidaCaixa.hasFocus()) {
			pesquisa();
		}

		if (tblListar.hasFocus() && tblListar.isEnabled()) {
			if (e.getKeyCode() == e.VK_DOWN) {
				setCamposDados(SaidaCaixaController
						.getSaidaCaixa(listaAtualTabela.get(
								tblListar.getSelectedRow()).getCod()));
			} else if (e.getKeyCode() == e.VK_UP) {
				setCamposDados(SaidaCaixaController
						.getSaidaCaixa(listaAtualTabela.get(
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
			setCamposDados(SaidaCaixaController.getSaidaCaixa(listaAtualTabela
					.get(tblListar.getSelectedRow()).getCod()));
			lblNovo.setEnabled(false);
			tblListar.grabFocus();

		}

		if (e.getSource() == btnUltLinha) {
			tblListar.setRowSelectionInterval(tblListar.getModel()
					.getRowCount() - 1, tblListar.getModel().getRowCount() - 1);
			setCamposDados(SaidaCaixaController.getSaidaCaixa(listaAtualTabela
					.get(tblListar.getSelectedRow()).getCod()));
			lblNovo.setEnabled(false);
			tblListar.grabFocus();

		}

		if (e.getSource() == btnAvancar) {
			if (tblListar.getSelectedRow() < tblListar.getModel().getRowCount() - 1) {
				tblListar.setRowSelectionInterval(
						tblListar.getSelectedRow() + 1,
						tblListar.getSelectedRow() + 1);
				setCamposDados(SaidaCaixaController
						.getSaidaCaixa(listaAtualTabela.get(
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

				setCamposDados(SaidaCaixaController
						.getSaidaCaixa(listaAtualTabela.get(
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
		txtValorSaidaCaixa.setEnabled(true);
		cmbMotivoSaidaCaixa.setEnabled(true);
		btnPrimLinha.setEnabled(false);
		btnUltLinha.setEnabled(false);
		btnVoltar.setEnabled(false);
		btnAvancar.setEnabled(false);
		lblRelatorioSaidaCaixa.setEnabled(false);
	}

	private void setEnableCamposFalse() {
		txtValorSaidaCaixa.setEnabled(false);
		cmbMotivoSaidaCaixa.setEnabled(false);
		btnPrimLinha.setEnabled(true);
		btnUltLinha.setEnabled(true);
		btnVoltar.setEnabled(true);
		btnAvancar.setEnabled(true);
		verificaRelatorio();
	}

	public boolean verificaRelatorio() {
		if (tblListar.getRowCount() == 0) {
			lblRelatorioSaidaCaixa.setEnabled(false);
			return false;
		}
		lblRelatorioSaidaCaixa.setEnabled(true);
		return true;
	}

	private void pesquisa() {
		if (txtDataSaidaCaixa.getText().replaceAll("[/_]", "").length() > 0) {
			atualizaTabelaPesquisa(modelT);
		} else
			atualizaTabela(modelT);
	}

}