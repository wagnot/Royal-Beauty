package br.com.royal.view;

import java.awt.Color;
import java.awt.Cursor;
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

import br.com.royal.controller.ServicoController;
import br.com.royal.controllerReports.GerarRelatorioServico;
import br.com.royal.model.Servico;
import br.com.royal.modelReports.RelatorioServicos;

public class JanelaServico extends JFrame implements ActionListener,
		KeyListener, MouseListener, MouseMotionListener, WindowListener {

	private JTextField txtNomeServico, txtDescricaoServico;
	private JNumberFormatField txtValorServico;
	private JLabel lblFundo, lblMinimize, lblClose, lblNavbar, lblNomeServico,
			lblDescricaoServico, lblValorServico, lblInfoCamposObrigatorios,
			lblInfoNome, lblNovoServico, lblSalvarServico, lblEditarServico,
			lblExcluirServico, lblCancelar, lblRelatorioServico;
	private JButton btnAvancar, btnVoltar, btnPrimLinha, btnUltLinha;
	private Color corOriginalBtn = new Color(164, 6, 69);
	private JTable tblListar;
	private FTable modelT = new FTable();
	private int posX, posY;
	private int cod = 0;
	private String txtBtn = "";// serve para salver se o usuario clicou no botao
								// "editar" ou no botao "novo", mudando assim a
								// função do botao "salvar"
	private ArrayList<Servico> listaAtualTabela = new ArrayList<Servico>();
	private JanelaHome jh;
	private JanelaComanda jc;
	private JanelaAgendamento ja;

	public JanelaServico(JanelaHome jh, JanelaComanda jc, JanelaAgendamento ja) {
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}
		this.ja = ja;
		this.jh = jh;
		this.jc = jc;
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Img/Rosa.png"));
		setTitle("Cadastro Servico");
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
		lblNomeServico = new JLabel("* Nome:");
		lblNomeServico.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		lblNomeServico.setBounds(170, 80, 100, 30);
		add(lblNomeServico);

		txtNomeServico = new JTextFieldLimitada(50);
		txtNomeServico.setBounds(lblNomeServico.getX(),
				lblNomeServico.getY() + 30, 400, 30);
		txtNomeServico.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				12));
		add(txtNomeServico);
		txtNomeServico.addKeyListener(this);

		lblInfoNome = new JLabel();
		lblInfoNome.setBounds(txtNomeServico.getX(),
				txtNomeServico.getY() + 25, 300, 30);
		lblInfoNome.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(lblInfoNome);

		lblDescricaoServico = new JLabel("* Descrição:");
		lblDescricaoServico.setBounds(lblNomeServico.getX(),
				lblNomeServico.getY() + 80, 100, 30);
		lblDescricaoServico.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblDescricaoServico);

		txtDescricaoServico = new JTextFieldLimitada(50);
		txtDescricaoServico.setBounds(lblDescricaoServico.getX(),
				lblDescricaoServico.getY() + 30, 400, 30);
		txtDescricaoServico.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 12));
		add(txtDescricaoServico);
		txtDescricaoServico.setEnabled(false);

		lblValorServico = new JLabel("* Valor(R$):");
		lblValorServico.setBounds(lblNomeServico.getX(),
				lblDescricaoServico.getY() + 80, 80, 30);
		lblValorServico.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		add(lblValorServico);

		txtValorServico = new JNumberFormatField();
		txtValorServico.setLimit(7);
		txtValorServico.setBounds(lblValorServico.getX(),
				lblValorServico.getY() + 30, 150, 30);
		txtValorServico.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				12));
		add(txtValorServico);
		txtValorServico.setEnabled(false);

		lblInfoCamposObrigatorios = new JLabel("* Campos Obrigatórios");
		lblInfoCamposObrigatorios.setBounds(110, 390, 170, 30);
		lblInfoCamposObrigatorios.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblInfoCamposObrigatorios);

		lblNovoServico = new JLabel(new ImageIcon("Img/Serviço/Novo.png"));
		// lblNovoServico.setOpaque(true);
		// lblNovoServico.setBackground(Color.GREEN);
		lblNovoServico.setBounds(670, 40, 61, 52);
		lblNovoServico.setToolTipText("Novo");
		lblNovoServico.addMouseListener(this);
		add(lblNovoServico);

		lblSalvarServico = new JLabel(new ImageIcon("Img/Serviço/Salvar.png"));
		// lblSalvarServico.setOpaque(true);
		// lblSalvarServico.setBackground(Color.GREEN);
		lblSalvarServico.setBounds(lblNovoServico.getX(),
				lblNovoServico.getY() + 65, 61, 52);
		lblSalvarServico.setToolTipText("Salvar");
		lblSalvarServico.addMouseListener(this);
		lblSalvarServico.setEnabled(false);
		add(lblSalvarServico);

		lblEditarServico = new JLabel(new ImageIcon("Img/Serviço/Editar.png"));
		// lblEditarServico.setOpaque(true);
		// lblEditarServico.setBackground(Color.GREEN);
		lblEditarServico.setBounds(lblSalvarServico.getX(),
				lblSalvarServico.getY() + 65, 61, 52);
		lblEditarServico.setToolTipText("Editar");
		lblEditarServico.addMouseListener(this);
		lblEditarServico.setEnabled(false);
		add(lblEditarServico);

		// lblEditarServico = new JLabel(new
		// ImageIcon("Img/Serviço/Editar.png"));
		// lblEditarServico.setOpaque(true);
		// lblEditarServico.setBackground(Color.GREEN);
		// lblEditarServico.setBounds(lblSalvarServico.getX(),
		// lblSalvarServico.getY() + 65, 61, 52);
		// lblEditarServico.addMouseListener(this);
		// add(lblEditarServico);

		lblExcluirServico = new JLabel(new ImageIcon("Img/Serviço/Remover.png"));
		// lblExcluirServico.setOpaque(true);
		// lblExcluirServico.setBackground(Color.GREEN);
		lblExcluirServico.setBounds(lblEditarServico.getX(),
				lblEditarServico.getY() + 65, 61, 52);
		lblExcluirServico.setToolTipText("Excluir");
		lblExcluirServico.addMouseListener(this);
		lblExcluirServico.setEnabled(false);
		add(lblExcluirServico);

		lblCancelar = new JLabel(new ImageIcon("Img/Cancelar.png"));
		// lblCancelar.setOpaque(true);
		// lblCancelar.setBackground(Color.GREEN);
		lblCancelar.setBounds(lblExcluirServico.getX() - 3,
				lblExcluirServico.getY() + 62, 61, 52);
		lblCancelar.setToolTipText("Cancelar");
		lblCancelar.addMouseListener(this);
		add(lblCancelar);

		lblRelatorioServico = new JLabel(new ImageIcon("Img/Relatorio.png"));
		lblRelatorioServico.setBounds(lblExcluirServico.getX() + 5,
				lblCancelar.getY() + 65, 44, 50);
		lblRelatorioServico.setToolTipText("Relatório");
		lblRelatorioServico.addMouseListener(this);
		add(lblRelatorioServico);

		btnPrimLinha = new JButton("<<");
		btnPrimLinha.setBackground(corOriginalBtn);
		btnPrimLinha.setForeground(Color.WHITE);
		btnPrimLinha.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
		btnPrimLinha.setBounds(310, 380, 52, 40);
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
		// lblMinimize.setOpaque(true);
		// lblMinimize.setBackground(Color.GREEN);
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
		verificaRelatorio();
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
		scroll.setBounds(35, 430, 750, 130);
		// scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scroll);

		modelT.addColumn("Nome");
		modelT.addColumn("Descrição");
		modelT.addColumn("Valor (R$)");

		atualizaTabela(modelT);

		tblListar.getColumnModel().getColumn(0).setPreferredWidth(250);
		tblListar.getColumnModel().getColumn(1).setPreferredWidth(400);
		tblListar.getColumnModel().getColumn(2).setPreferredWidth(100);

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

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(lblMinimize)) {
			this.setExtendedState(JFrame.ICONIFIED);
		}

		else if (e.getSource().equals(lblClose)) {
			this.dispose();
		}

		if (e.getSource() == lblNovoServico && lblNovoServico.isEnabled()) {
			setEnableCamposTrue();
			lblSalvarServico.setEnabled(true);
			lblNovoServico.setEnabled(false);
			lblEditarServico.setEnabled(false);
			lblExcluirServico.setEnabled(false);

			txtBtn = "Novo";
			tblListar.setEnabled(false);
			if (txtNomeServico.getText().length() < 1)
				txtNomeServico.grabFocus();
		}

		if (e.getSource() == lblSalvarServico && lblSalvarServico.isEnabled()) {
			Servico s = new Servico();
			try {
				if (lblInfoNome.getText().length() < 2) {
					if (verificaCamposPreenchidos()) {
						s.setNomeServico(txtNomeServico.getText());
						s.setDescricaoServico(txtDescricaoServico.getText());
						s.setValorServico(Double.parseDouble(txtValorServico
								.getText().substring(3).replace(",", ".")));

						if (txtBtn.equals("Novo")) {
							ServicoController.cadastrarServico(s);
							JOptionPane.showMessageDialog(this,
									"Serviço cadastrado com sucesso");
						} else {
							ServicoController.editarServico(s, cod);
							JOptionPane.showMessageDialog(this,
									"Serviço editado com sucesso");
						}

						txtBtn = "";
						limpaCampos();
						setEnableCamposFalse();
						lblNovoServico.setEnabled(true);
						lblSalvarServico.setEnabled(false);
						tblListar.getSelectionModel().clearSelection();
						tblListar.setEnabled(true);
						atualizaTabela(modelT);
						txtNomeServico.grabFocus();

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
		if (e.getSource() == lblEditarServico && lblEditarServico.isEnabled()) {
			// setando todos os campos com enable = true
			setEnableCamposTrue();
			// setando enable = true no botao salvar
			lblSalvarServico.setEnabled(true);
			// setando enable = false nos botoes novo, editar e excluir
			lblNovoServico.setEnabled(false);
			lblEditarServico.setEnabled(false);
			lblExcluirServico.setEnabled(false);

			txtBtn = "Editar";
			tblListar.getSelectionModel().clearSelection();
			tblListar.setEnabled(false);
			// deixando a txtNome focada
			txtNomeServico.grabFocus();

		}

		// Função botão excluir
		if (e.getSource() == lblExcluirServico && lblExcluirServico.isEnabled()) {
			if (tblListar.getSelectedRow() != -1) {
				int cod = ServicoController.getServico(
						listaAtualTabela.get(tblListar.getSelectedRow())
								.getCodServico()).getCodServico();
				int resposta = JOptionPane.showConfirmDialog(this,
						"Deseja exluir o serviço '"
								+ ServicoController.getServico(cod)
										.getNomeServico() + "'", "",
						JOptionPane.YES_NO_OPTION);
				if (resposta == JOptionPane.YES_OPTION) {
					ServicoController.excluirServico(cod);
					JOptionPane.showMessageDialog(this, "Serviço excluído");
					// atualizando a tabela
					atualizaTabela(modelT);
					txtNomeServico.grabFocus();
					lblEditarServico.setEnabled(false);
					lblExcluirServico.setEnabled(false);
					lblNovoServico.setEnabled(true);
					limpaCampos();
				}
			}
		}

		if (e.getSource() == lblCancelar && lblCancelar.isEnabled()) {
			limpaCampos();
			setEnableCamposFalse();
			lblEditarServico.setEnabled(false);
			lblExcluirServico.setEnabled(false);
			lblNovoServico.setEnabled(true);
			lblSalvarServico.setEnabled(false);
			atualizaTabela(modelT);
			tblListar.setEnabled(true);
			txtBtn = "";
			txtNomeServico.grabFocus();
		}
		if (e.getSource().equals(lblRelatorioServico)
				&& lblRelatorioServico.isEnabled()) {
			if (tblListar.getSelectedRow() != -1) {
				GerarRelatorioServico gru = new GerarRelatorioServico();
				ArrayList<RelatorioServicos> listaDeServicos = new ArrayList<RelatorioServicos>();
				RelatorioServicos rs = new RelatorioServicos();
				rs.setDescricao(tblListar.getValueAt(
						tblListar.getSelectedRow(), 1).toString());
				rs.setValor(tblListar.getValueAt(tblListar.getSelectedRow(), 2)
						.toString());
				rs.setNome(tblListar.getValueAt(tblListar.getSelectedRow(), 0)
						.toString());
				rs.setData("");
				rs.setDataCompleta("");
				listaDeServicos.add(rs);
				gru.gerarServicos(listaDeServicos);
			} else if (tblListar.getRowCount() > 0) {
				GerarRelatorioServico gru = new GerarRelatorioServico();
				ArrayList<RelatorioServicos> listaDeServicos = new ArrayList<RelatorioServicos>();
				for (int i = 0; i < tblListar.getRowCount(); i++) {
					RelatorioServicos rs = new RelatorioServicos();
					rs.setDescricao(tblListar.getValueAt(i, 1).toString());
					rs.setValor(tblListar.getValueAt(i, 2).toString());
					rs.setNome(tblListar.getValueAt(i, 0).toString());
					rs.setData("");
					rs.setDataCompleta("");
					listaDeServicos.add(rs);
				}
				gru.gerarServicos(listaDeServicos);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource().equals(lblNovoServico) && lblNovoServico.isEnabled()) {
			lblNovoServico.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblSalvarServico)
				&& lblSalvarServico.isEnabled()) {
			lblSalvarServico.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblEditarServico)
				&& lblEditarServico.isEnabled()) {
			lblEditarServico.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblExcluirServico)
				&& lblExcluirServico.isEnabled()) {
			lblExcluirServico.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblCancelar) && lblCancelar.isEnabled()) {
			lblCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblRelatorioServico)
				&& lblRelatorioServico.isEnabled()) {
			lblRelatorioServico.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblClose)
				&& lblClose.isEnabled()) {
			lblClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblMinimize)
				&& lblMinimize.isEnabled()) {
			lblMinimize.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
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
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (tblListar.getSelectedRow() != -1) {
			lblNovoServico.setEnabled(false);
			lblEditarServico.setEnabled(true);
			lblExcluirServico.setEnabled(true);
			setCamposDadosServicos(ServicoController
					.getServico(ServicoController.getServico(
							listaAtualTabela.get(tblListar.getSelectedRow())
									.getCodServico()).getCodServico()));
		}

	}

	@Override
	public void keyPressed(KeyEvent arg0) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (tblListar.isEnabled() && tblListar.getSelectedRow() > -1) {
			if (e.getKeyCode() == e.VK_DOWN || e.getKeyCode() == e.VK_RIGHT)
				setCamposDadosServicos(ServicoController
						.getServico(ServicoController.getServico(
								listaAtualTabela
										.get(tblListar.getSelectedRow())
										.getCodServico()).getCodServico()));
			if (e.getKeyCode() == e.VK_UP || e.getKeyCode() == e.VK_LEFT)
				setCamposDadosServicos(ServicoController
						.getServico(ServicoController.getServico(
								listaAtualTabela
										.get(tblListar.getSelectedRow())
										.getCodServico()).getCodServico()));
		}
		if (e.getSource() == txtNomeServico) {
			pesquisa();

			if (ServicoController.verificaDuplicidadeNome(
					txtNomeServico.getText(), cod)) {
				lblInfoNome
						.setText("Este nome já foi cadastrado, insira outro");
			} else {
				lblInfoNome.setText("");
			}
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
			lblEditarServico.setEnabled(true);
			lblExcluirServico.setEnabled(true);
			Servico s = ServicoController.getServico(listaAtualTabela.get(
					tblListar.getSelectedRow()).getCodServico());
			setCamposDadosServicos(s);
			lblNovoServico.setEnabled(false);
			tblListar.grabFocus();

		}

		if (e.getSource() == btnUltLinha) {
			tblListar.setRowSelectionInterval(tblListar.getModel()
					.getRowCount() - 1, tblListar.getModel().getRowCount() - 1);
			lblEditarServico.setEnabled(true);
			lblExcluirServico.setEnabled(true);
			Servico s = ServicoController.getServico(listaAtualTabela.get(
					tblListar.getSelectedRow()).getCodServico());
			setCamposDadosServicos(s);
			lblNovoServico.setEnabled(false);
			tblListar.grabFocus();

		}

		if (e.getSource() == btnAvancar) {
			if (tblListar.getSelectedRow() < tblListar.getModel().getRowCount() - 1) {
				tblListar.setRowSelectionInterval(
						tblListar.getSelectedRow() + 1,
						tblListar.getSelectedRow() + 1);
				lblEditarServico.setEnabled(true);
				lblExcluirServico.setEnabled(true);
				Servico s = ServicoController.getServico(listaAtualTabela.get(
						tblListar.getSelectedRow()).getCodServico());
				setCamposDadosServicos(s);
				tblListar.grabFocus();

				lblNovoServico.setEnabled(false);
			}
		}

		if (e.getSource() == btnVoltar) {
			if (tblListar.getSelectedRow() > 0) {
				tblListar.setRowSelectionInterval(
						tblListar.getSelectedRow() - 1,
						tblListar.getSelectedRow() - 1);

				// tblListar.setSelectionMode(0);

				lblEditarServico.setEnabled(true);
				lblExcluirServico.setEnabled(true);
				lblNovoServico.setEnabled(false);
				tblListar.grabFocus();

				Servico s = ServicoController.getServico(listaAtualTabela.get(
						tblListar.getSelectedRow()).getCodServico());
				setCamposDadosServicos(s);
				tblListar.grabFocus();
			} else {
				tblListar.clearSelection();
				lblEditarServico.setEnabled(false);
				lblExcluirServico.setEnabled(false);
				limpaCampos();
				lblNovoServico.setEnabled(true);
			}
		}

	}

	private void setCamposDadosServicos(Servico s) {
		if (s.getCodServico() > 0)
			cod = s.getCodServico();
		txtNomeServico.setText(s.getNomeServico());
		txtDescricaoServico.setText(s.getDescricaoServico());
		txtValorServico.setText(new DecimalFormat("0.00").format(s
				.getValorServico()));
	}

	private int divideNomeServico(String nome) {
		try {
			if (nome.indexOf(" ", 25) < 30)
				return nome.indexOf(" ", 25);
			else
				return nome.indexOf(" ", 15);
		} catch (Exception ex) {
			return 0;
		}
	}

	private void atualizaTabelaPesquisa(DefaultTableModel modelo) {
		modelo.setNumRows(0);
		int linha = 0;
		if (ServicoController.getServicos(txtNomeServico.getText()) != null) {
			listaAtualTabela = ServicoController.getServicos(txtNomeServico
					.getText());
			for (Servico s : ServicoController.getServicos(txtNomeServico
					.getText())) {
				linha = setDadosTabela(modelo, linha, s);
			}
		}
	}

	private int setDadosTabela(DefaultTableModel modelo, int linha, Servico s) {
		if (s.getNomeServico().length() < 30
				|| divideNomeServico(s.getNomeServico()) < 0)
			modelo.addRow(new Object[] { s.getNomeServico(),
					s.getDescricaoServico(),
					(s.getValorServico() + "").replace(".", ",") });
		else {
			modelo.addRow(new Object[] {
					"<HTML><CENTER>"
							+ s.getNomeServico().substring(0,
									divideNomeServico(s.getNomeServico()))
							+ "<BR>"
							+ s.getNomeServico().substring(
									divideNomeServico(s.getNomeServico()),
									s.getNomeServico().length()),
					s.getDescricaoServico(),
					(s.getValorServico() + "").replace(".", ",") });
			tblListar.setRowHeight(linha, 30);
		}
		linha++;
		return linha;
	}

	private void atualizaTabela(DefaultTableModel modelo) {
		int linha = 0;
		modelo.setNumRows(0);
		if (ServicoController.getServicos() != null) {
			listaAtualTabela = ServicoController.getServicos();
			for (Servico s : ServicoController.getServicos())
				linha = setDadosTabela(modelo, linha, s);
		}
	}

	private void setEnableCamposFalse() {
		txtDescricaoServico.setEnabled(false);
		txtValorServico.setEnabled(false);
		btnPrimLinha.setEnabled(true);
		btnUltLinha.setEnabled(true);
		btnVoltar.setEnabled(true);
		btnAvancar.setEnabled(true);
		verificaRelatorio();
	}

	private void limpaCampos() {
		txtDescricaoServico.setText("");
		txtValorServico.setText("");
		txtNomeServico.setText("");
		lblInfoNome.setText("");
		cod = 0;
		txtBtn = "";
		pesquisa();

	}

	private void pesquisa() {
		if (txtNomeServico.getText().length() > 0) {
			atualizaTabelaPesquisa(modelT);
		} else
			atualizaTabela(modelT);
	}

	private boolean verificaCamposPreenchidos() {
		try {
			if (txtNomeServico.getText().length() > 0
					&& txtDescricaoServico.getText().length() > 0
					&& txtValorServico.getText().length() > 0
					&& lblInfoNome.getText().length() < 1)
				return true;
		} catch (Exception ex) {
			return false;
		}
		return false;
	}

	private void setEnableCamposTrue() {
		txtDescricaoServico.setEnabled(true);
		txtValorServico.setEnabled(true);
		btnPrimLinha.setEnabled(false);
		btnUltLinha.setEnabled(false);
		btnVoltar.setEnabled(false);
		btnAvancar.setEnabled(false);
		lblRelatorioServico.setEnabled(false);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		setLocation(getLocation().x + e.getX() - posX,
				getLocation().y + e.getY() - posY);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		if (jh != null)
			jh.setVisible(true);
		else if (jc != null) {
			jc.setVisible(true);
			jc.atualizaNovoServico(jc.getModelTServico());
		} else if (ja != null) {
			ja.setVisible(true);
			ja.preencherCombos();
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {

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

	public boolean verificaRelatorio() {
		if (tblListar.getRowCount() == 0) {
			lblRelatorioServico.setEnabled(false);
			return false;
		}
		lblRelatorioServico.setEnabled(true);
		return true;
	}

}
