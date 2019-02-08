package br.com.royal.view;

import java.awt.Color;
import java.awt.Font;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import br.com.royal.controller.MotivoEntradaCaixaController;
import br.com.royal.model.MotivoEntradaCaixa;

public class JanelaMotivoEntradaCaixa extends JFrame implements ActionListener,
		KeyListener, MouseListener, MouseMotionListener, WindowListener {

	private JTextField txtMotivoEntradaCaixa;
	private JLabel lblFundo, lblMinimize, lblClose, lblNavbar, lblMotivoEntradaCaixa,
			lblInfoCamposObrigatorios, lblSalvar, lblCancelar, lblEditar,
			lblNovo, lblExcluir, lblInfoEntradaCaixa;
	private JButton btnAvancar, btnVoltar, btnPrimLinha, btnUltLinha;
	private Color corOriginalBtn = new Color(164, 6, 69);
	private JTable tblListar;
	private FTable modelT = new FTable();
	private int posX, posY, cod = 0;
	private String txtBtn = "";// serve para salver se o usuario clicou no botao
								// "editar" ou no botao "novo", mudando assim a
								// função do botao "salvar"
	private ArrayList<MotivoEntradaCaixa> listaAtualTabela = new ArrayList<MotivoEntradaCaixa>();

	private JanelaHome jh;

	public JanelaMotivoEntradaCaixa(JanelaHome jh) {
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}
		this.jh = jh;
		setTitle("Cadastro Motivo Entrada Caixa");
		setSize(820, 570);
		setResizable(false);
		setLayout(null);
		setUndecorated(true);
		setLocationRelativeTo(null);
		addWindowListener(this);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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

		lblMotivoEntradaCaixa = new JLabel("* Motivo Entrada Caixa:");
		lblMotivoEntradaCaixa.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		lblMotivoEntradaCaixa.setBounds(170, 120, 300, 30);
		add(lblMotivoEntradaCaixa);

		txtMotivoEntradaCaixa = new JTextField();
		txtMotivoEntradaCaixa
				.setBounds(lblMotivoEntradaCaixa.getX(), lblMotivoEntradaCaixa.getY() + 30, 400, 30);
		txtMotivoEntradaCaixa.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(txtMotivoEntradaCaixa);
		txtMotivoEntradaCaixa.addKeyListener(this);

		lblInfoEntradaCaixa = new JLabel();
		lblInfoEntradaCaixa.setBounds(txtMotivoEntradaCaixa.getX(), txtMotivoEntradaCaixa.getY() + 25,
				270, 30);
		lblInfoEntradaCaixa.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				12));
		lblInfoEntradaCaixa.setForeground(Color.RED);
		add(lblInfoEntradaCaixa);

		lblInfoCamposObrigatorios = new JLabel("* Campos Obrigatórios");
		lblInfoCamposObrigatorios.setBounds(110, 360, 170, 30);
		lblInfoCamposObrigatorios.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblInfoCamposObrigatorios);

		lblNovo = new JLabel(new ImageIcon("Img/Serviço/Novo.png"));
		lblNovo.setBounds(670, 40, 61, 52);
		lblNovo.setToolTipText("Novo");
		lblNovo.addMouseListener(this);
		add(lblNovo);

		lblSalvar = new JLabel(new ImageIcon("Img/Serviço/Salvar.png"));
		lblSalvar.setBounds(lblNovo.getX(), lblNovo.getY() + 65, 61, 52);
		lblSalvar.setToolTipText("Salvar");
		lblSalvar.addMouseListener(this);
		lblSalvar.setEnabled(false);
		add(lblSalvar);

		lblEditar = new JLabel(new ImageIcon("Img/Serviço/Editar.png"));
		lblEditar.setBounds(lblSalvar.getX(), lblSalvar.getY() + 65, 61, 52);
		lblEditar.setToolTipText("Editar");
		lblEditar.addMouseListener(this);
		lblEditar.setEnabled(false);
		add(lblEditar);

		lblExcluir = new JLabel(new ImageIcon("Img/Serviço/Remover.png"));
		lblExcluir.setBounds(lblEditar.getX(), lblEditar.getY() + 65, 61, 52);
		lblExcluir.setToolTipText("Excluir");
		lblExcluir.addMouseListener(this);
		lblExcluir.setEnabled(false);
		add(lblExcluir);

		lblCancelar = new JLabel(new ImageIcon("Img/Serviço/Cancelar.png"));
		lblCancelar.setBounds(lblExcluir.getX() - 5, lblExcluir.getY() + 65,
				61, 52);
		lblCancelar.setToolTipText("Cancelar");
		lblCancelar.addMouseListener(this);
		add(lblCancelar);

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
				new ImageIcon("Img/Gerenciamento de Serviços.png"));
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
		scroll.setBounds(55, 400, 700, 130);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroll);

		modelT.addColumn("Motivo entrada caixa");

		atualizaTabela(modelT);

		tblListar.getColumnModel().getColumn(0)
				.setPreferredWidth(scroll.getWidth() - 20);

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
		if (MotivoEntradaCaixaController.getMotivoEntradaCaixas(txtMotivoEntradaCaixa.getText()) != null) {
			listaAtualTabela = MotivoEntradaCaixaController
					.getMotivoEntradaCaixas(txtMotivoEntradaCaixa.getText());
			for (MotivoEntradaCaixa fp : MotivoEntradaCaixaController
					.getMotivoEntradaCaixas(txtMotivoEntradaCaixa.getText()))
				modelo.addRow(new Object[] { fp.getDescricao() });
		}
	}

	private void atualizaTabela(DefaultTableModel modelo) {
		modelo.setNumRows(0);
		if (MotivoEntradaCaixaController.getMotivoEntradaCaixas() != null) {
			listaAtualTabela = MotivoEntradaCaixaController.getMotivoEntradaCaixas();
			for (MotivoEntradaCaixa fp : MotivoEntradaCaixaController
					.getMotivoEntradaCaixas())
				modelo.addRow(new Object[] { fp.getDescricao() });
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
			setEnableCamposTrue();
			
			lblSalvar.setEnabled(true);
			lblNovo.setEnabled(false);
			lblEditar.setEnabled(false);
			lblExcluir.setEnabled(false);

			txtBtn = "Novo";
			tblListar.setEnabled(false);
		}

		if (e.getSource() == lblSalvar && lblSalvar.isEnabled()) {
			MotivoEntradaCaixa fp = new MotivoEntradaCaixa();
			try {
				if (lblInfoEntradaCaixa.getText().length() < 2) {
					if (verificaCamposPreenchidos()) {
						fp.setDescricao(txtMotivoEntradaCaixa.getText());

						if (txtBtn.equals("Novo")) {
							MotivoEntradaCaixaController.cadastrar(fp);
							JOptionPane.showMessageDialog(this,
									"Motivo cadastrado com sucesso");
						} else {
							MotivoEntradaCaixaController.editar(fp, cod);

							JOptionPane.showMessageDialog(this,
									"Motivo editado com sucesso");
						}

						setEnableCamposFalse();
						txtBtn = "";
						limpaCampos();
						lblNovo.setEnabled(true);
						lblSalvar.setEnabled(false);
						tblListar.getSelectionModel().clearSelection();
						tblListar.setEnabled(true);
						atualizaTabela(modelT);
						txtMotivoEntradaCaixa.grabFocus();

					} else {
						JOptionPane
								.showMessageDialog(this,
										"Preencha todos os campos obrigatórios corretamente");
					}
				} else
					JOptionPane.showMessageDialog(this,
							"Este nome já está sendo usado, insira outro");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this,
						"Preencha todos os campos obrigatórios corretamente");
			}
		}

		// Função botão editar
		if (e.getSource() == lblEditar && lblEditar.isEnabled()) {
			setEnableCamposTrue();
			
			// setando todos os campos com enable = true
			// setando enable = true no botao salvar
			lblSalvar.setEnabled(true);
			// setando enable = false nos botoes novo, editar e excluir
			lblNovo.setEnabled(false);
			lblEditar.setEnabled(false);
			lblExcluir.setEnabled(false);

			txtBtn = "Editar";
			tblListar.getSelectionModel().clearSelection();
			tblListar.setEnabled(false);
			// deixando a txtNome focada
			txtMotivoEntradaCaixa.grabFocus();

		}

		// Função botão excluir
		if (e.getSource() == lblExcluir && lblExcluir.isEnabled()) {
			if (tblListar.getSelectedRow() != -1) {
				MotivoEntradaCaixa f = MotivoEntradaCaixaController
						.getMotivoEntradaCaixa(listaAtualTabela.get(
								tblListar.getSelectedRow()).getCod());
				int cod = f.getCod();
				int resposta = JOptionPane.showConfirmDialog(this,
						"Deseja exluir '" + f.getDescricao() + "'?", "",
						JOptionPane.YES_NO_OPTION);
				if (resposta == JOptionPane.YES_OPTION) {
					MotivoEntradaCaixaController.excluir(cod);
					JOptionPane.showMessageDialog(this, "Motivo da entreda de caixa excluído");
					// atualizando a tabela
					atualizaTabela(modelT);
					txtMotivoEntradaCaixa.grabFocus();
					lblEditar.setEnabled(false);
					lblExcluir.setEnabled(false);
					lblNovo.setEnabled(true);
					limpaCampos();
				}
			}
		}

		if (e.getSource() == lblCancelar && lblCancelar.isEnabled()) {

			setEnableCamposFalse();
			limpaCampos();
			lblEditar.setEnabled(false);
			lblExcluir.setEnabled(false);
			lblNovo.setEnabled(true);
			lblSalvar.setEnabled(false);
			atualizaTabela(modelT);
			tblListar.setEnabled(true);
			txtBtn = "";
			txtMotivoEntradaCaixa.grabFocus();
		}

	}

	private void setCamposDados(MotivoEntradaCaixa f) {
		cod = f.getCod();
		txtMotivoEntradaCaixa.setText(f.getDescricao());
	}

	private void limpaCampos() {
		txtMotivoEntradaCaixa.setText("");
		lblInfoEntradaCaixa.setText("");
		cod = 0;
		txtBtn = "";
	}

	private boolean verificaCamposPreenchidos() {
		if (txtMotivoEntradaCaixa.getText().length() > 0)
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
			lblEditar.setEnabled(true);
			lblExcluir.setEnabled(true);
			setCamposDados(MotivoEntradaCaixaController
					.getMotivoEntradaCaixa(listaAtualTabela.get(
							tblListar.getSelectedRow()).getCod()));
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(txtMotivoEntradaCaixa.hasFocus()){
			pesquisa();
		}
		
		if (txtMotivoEntradaCaixa.hasFocus() && !txtBtn.equals("")) {
			if (MotivoEntradaCaixaController.conferirDuplicidadeDescricao(
					txtMotivoEntradaCaixa.getText(), cod)) {
				lblInfoEntradaCaixa
						.setText("Este Motivo já foi cadastrado. Insira outro");
			} else {
				lblInfoEntradaCaixa.setText("");
			}
		}

		if (tblListar.hasFocus() && tblListar.isEnabled()) {
			if (e.getKeyCode() == e.VK_DOWN) {
				setCamposDados(MotivoEntradaCaixaController
						.getMotivoEntradaCaixa(listaAtualTabela.get(
								tblListar.getSelectedRow()).getCod()));
			} else if (e.getKeyCode() == e.VK_UP) {
				setCamposDados(MotivoEntradaCaixaController
						.getMotivoEntradaCaixa(listaAtualTabela.get(
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
			lblEditar.setEnabled(true);
			lblExcluir.setEnabled(true);
			setCamposDados(MotivoEntradaCaixaController
					.getMotivoEntradaCaixa(listaAtualTabela.get(
							tblListar.getSelectedRow()).getCod()));
			lblNovo.setEnabled(false);
			tblListar.grabFocus();

		}

		if (e.getSource() == btnUltLinha) {
			tblListar.setRowSelectionInterval(tblListar.getModel()
					.getRowCount() - 1, tblListar.getModel().getRowCount() - 1);
			lblEditar.setEnabled(true);
			lblExcluir.setEnabled(true);
			setCamposDados(MotivoEntradaCaixaController
					.getMotivoEntradaCaixa(listaAtualTabela.get(
							tblListar.getSelectedRow()).getCod()));
			lblNovo.setEnabled(false);
			tblListar.grabFocus();

		}

		if (e.getSource() == btnAvancar) {
			if (tblListar.getSelectedRow() < tblListar.getModel().getRowCount() - 1) {
				tblListar.setRowSelectionInterval(
						tblListar.getSelectedRow() + 1,
						tblListar.getSelectedRow() + 1);
				lblEditar.setEnabled(true);
				lblExcluir.setEnabled(true);
				setCamposDados(MotivoEntradaCaixaController
						.getMotivoEntradaCaixa(listaAtualTabela.get(
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

				lblEditar.setEnabled(true);
				lblExcluir.setEnabled(true);
				lblNovo.setEnabled(false);

				setCamposDados(MotivoEntradaCaixaController
						.getMotivoEntradaCaixa(listaAtualTabela.get(
								tblListar.getSelectedRow()).getCod()));
				tblListar.grabFocus();
			} else {
				tblListar.clearSelection();
				lblEditar.setEnabled(false);
				lblExcluir.setEnabled(false);
				limpaCampos();
				lblNovo.setEnabled(true);
			}
		}
	}
	
	private void setEnableCamposTrue() {
		btnPrimLinha.setEnabled(false);
		btnUltLinha.setEnabled(false);
		btnVoltar.setEnabled(false);
		btnAvancar.setEnabled(false);
	}
	
	private void setEnableCamposFalse() {
		btnPrimLinha.setEnabled(true);
		btnUltLinha.setEnabled(true);
		btnVoltar.setEnabled(true);
		btnAvancar.setEnabled(true);
	}
	
	private void pesquisa() {
		if (txtMotivoEntradaCaixa.getText().length() > 0) {
			atualizaTabelaPesquisa(modelT);
		} else
			atualizaTabela(modelT);
	}

}
