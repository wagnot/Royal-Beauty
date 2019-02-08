package br.com.royal.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultCaret;

import br.com.royal.controller.ComandaController;
import br.com.royal.controller.ComandaProdutoController;
import br.com.royal.controller.ComandaServicoController;
import br.com.royal.controller.EstoqueController;
import br.com.royal.model.Cliente;
import br.com.royal.model.ComandaProduto;
import br.com.royal.model.ComandaServico;
import br.com.royal.model.Estoque;
import br.com.royal.model.Produto;
import br.com.royal.model.Servico;
import br.com.royal.model.ServicoComanda;
import br.com.royal.model.Telefones;

public class JanelaComanda extends JFrame implements ActionListener,
		WindowListener, KeyListener, MouseListener, MouseMotionListener,
		FocusListener {
	private JanelaHome jh;
	private Color corOriginalBtn = new Color(164, 6, 69);

	private JLabel fotoCliente, lblFotoCliente, lblInfoFoto, lblNomeCliente,
			lblServico, lblProduto, lblObservacao, lblValorServico,
			lblQuantidadeProduto, lblSubTotalServico, lblSubTotalProduto,
			lblTotal, lblTxtSubTotalServico, lblTxtSubTotalProduto,
			lblTxtTotal, lblFundo, lblNavbar, lblMinimize, lblClose,
			lblTxtDescricaoServico, lblTxtNomeProduto,lblDesconto,
			lblInfoDescricaoServicoCompleta, lblInfoNomeProdutoCompleto;
	private int posX, posY, linhaTblServico = 0, linhaTblProduto = 0;
	private JTextField txtNomeCliente, txtProduto,
			txtServico;
	private JIntNumberFormatField txtQuantidadeProduto;
	private JNumberFormatField txtValorServico,txtDesconto;
	private JList<Servico> listaServicos;
	private JButton btnAddServico, btnAddProduto, btnRemServico, btnRemProduto,
			btnPagamentoComanda, btnNovoServico, btnNovoProduto,
			btnCancelarComanda;
	private JTable tblListar, tblServicos, tblProdutos;
	private FTable modelTCliente = new FTable(), modelTServico = new FTable(),
			modelTProduto = new FTable();
	private int linhaSelecionadaServico = -1, linhaSelecionadaProduto = -1;
	private Cliente clienteSelecionado;
	
	public FTable getModelTServico() {
		return modelTServico;
	}

	private JTextArea txtObservacao;

	private ArrayList<ServicoComanda> servicosAux = new ArrayList<ServicoComanda>();
	private ArrayList<Servico> servicos;
	private ArrayList<Cliente> listaAtualTabela = new ArrayList<Cliente>();
	private ArrayList<ServicoComanda> listaAtualTabelaS = new ArrayList<ServicoComanda>();
	private ArrayList<Produto> listaAtualTabelaP = new ArrayList<Produto>();
	private EstoqueController eControlle = new EstoqueController();
	public String localFoto = "";
	private double subTotalServico = 0, total = 0, subTotalProduto;
	private EstoqueController eController = new EstoqueController();
	

	JScrollPane scrollServico;

	private JanelaPagamento janelaPagamento;

	public JanelaPagamento getJanelaPagamento() {
		return janelaPagamento;
	}

	public void setJanelaPagamento(JanelaPagamento janelaPagamento) {
		this.janelaPagamento = janelaPagamento;
	}

	private static final String COMMIT_ACTION = "commit";

	public JanelaComanda(JanelaHome jh) {
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}
		this.jh = jh;
		setTitle("Cadastro de Cliente");
		setSize(1080, 700);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		addWindowListener(this);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setUndecorated(true);
		IC();
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Img/Rosa.png"));

		setVisible(true);
	}

	private void criaTabela() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
		}

		tblListar = new JTable(modelTCliente);
		tblListar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblListar.getTableHeader().setResizingAllowed(false);
		tblListar.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		tblListar.setRowSelectionAllowed(true);
		tblListar.setSelectionBackground(new Color(222, 54, 121));
		tblListar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblListar.getTableHeader().setDefaultRenderer(new RTable());
		tblListar.addKeyListener(this);
		JScrollPane scrollCliente = new JScrollPane(tblListar);
		// scroll.add(tblListar);

		scrollCliente.setBounds(130, 145, 570, 128);

		modelTCliente.addColumn("Nome");
		modelTCliente.addColumn("Telefone");
		modelTCliente.addColumn("Email");

		tblListar.getColumnModel().getColumn(0).setPreferredWidth(220);
		tblListar.getColumnModel().getColumn(1).setPreferredWidth(110);
		tblListar.getColumnModel().getColumn(2).setPreferredWidth(220);
		tblListar.getColumnModel().getColumn(2).setPreferredWidth(220);

		tblListar.getTableHeader().setReorderingAllowed(false);
		atualizaTabelaClientes(modelTCliente);

		add(scrollCliente);
		scrollCliente
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

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

		// -------------

		tblServicos = new JTable(modelTServico);
		tblServicos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblServicos.getTableHeader().setResizingAllowed(false);
		tblServicos.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		tblServicos.setRowSelectionAllowed(true);
		tblServicos.setSelectionBackground(new Color(222, 54, 121));
		tblServicos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblServicos.getTableHeader().setDefaultRenderer(new RTable2());
		tblServicos.addKeyListener(this);
		scrollServico = new JScrollPane(tblServicos);
		// scroll.add(tblListar);
		scrollServico.setBounds(130, scrollCliente.getY() + 285, 275, 128);
		scrollServico
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		modelTServico.addColumn("Serviço");
		modelTServico.addColumn("Preço");

		tblServicos.getColumnModel().getColumn(0).setPreferredWidth(150);
		tblServicos.getColumnModel().getColumn(1).setPreferredWidth(105);
		tblServicos.getTableHeader().setReorderingAllowed(false);

		tblServicos.setShowHorizontalLines(false);
		tblServicos.setShowVerticalLines(false);

		tblServicos.addMouseListener(this);
		tblServicos.getTableHeader().setFont(
				new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		((DefaultTableCellRenderer) tblServicos.getTableHeader()
				.getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);

		for (int i = 0; i < tblServicos.getColumnCount(); i++) {
			DefaultTableCellRenderer cellRender = new DefaultTableCellRenderer();
			cellRender.setHorizontalAlignment(SwingConstants.CENTER);
			tblServicos.getColumnModel().getColumn(i)
					.setCellRenderer(cellRender);
		}

		// -------------

		tblProdutos = new JTable(modelTProduto);
		tblProdutos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblProdutos.getTableHeader().setResizingAllowed(false);
		tblProdutos.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		tblProdutos.setRowSelectionAllowed(true);
		tblProdutos.setSelectionBackground(new Color(222, 54, 121));
		tblProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblProdutos.getTableHeader().setDefaultRenderer(new RTable2());
		tblProdutos.addKeyListener(this);
		JScrollPane scrollProduto = new JScrollPane(tblProdutos);
		// scroll.add(tblListar);
		scrollProduto.setBounds(430, scrollServico.getY(), 275, 128);
		scrollProduto
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		modelTProduto.addColumn("Produto");
		modelTProduto.addColumn("Quant.");

		tblProdutos.getColumnModel().getColumn(0).setPreferredWidth(150);
		tblProdutos.getColumnModel().getColumn(1).setPreferredWidth(105);
		tblProdutos.getTableHeader().setReorderingAllowed(false);
		add(scrollProduto);

		tblProdutos.setShowHorizontalLines(false);
		tblProdutos.setShowVerticalLines(false);

		tblProdutos.addMouseListener(this);
		tblProdutos.getTableHeader().setFont(
				new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		((DefaultTableCellRenderer) tblProdutos.getTableHeader()
				.getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);

		for (int i = 0; i < tblProdutos.getColumnCount(); i++) {
			DefaultTableCellRenderer cellRender = new DefaultTableCellRenderer();
			cellRender.setHorizontalAlignment(SwingConstants.CENTER);
			tblProdutos.getColumnModel().getColumn(i)
					.setCellRenderer(cellRender);
		}
	}

	private void IC() {
		criaTabela();

		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}

		lblNomeCliente = new JLabel("Cliente:");
		lblNomeCliente.setBounds(130, 100, 200, 30);
		lblNomeCliente.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		add(lblNomeCliente);

		txtNomeCliente = new JTextField();
		txtNomeCliente.setBounds(lblNomeCliente.getX() + 70,
				lblNomeCliente.getY(), 500, 30);
		txtNomeCliente.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		txtNomeCliente.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				12));
		add(txtNomeCliente);
		txtNomeCliente.addKeyListener(this);

		lblFotoCliente = new JLabel("Foto:");
		lblFotoCliente.setBounds(lblNomeCliente.getX() + 600,
				lblNomeCliente.getY(), 220, 30);
		lblFotoCliente.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		add(lblFotoCliente);

		fotoCliente = new JLabel();
		fotoCliente.setBounds(lblFotoCliente.getX(),
				lblFotoCliente.getY() + 45, 170, 128);
		add(fotoCliente);
		fotoCliente.setBorder(LineBorder.createBlackLineBorder());
		fotoCliente.addMouseListener(this);
		fotoCliente.addKeyListener(this);

		lblInfoFoto = new JLabel();
		lblInfoFoto.setBounds(fotoCliente.getX(), fotoCliente.getY()
				+ fotoCliente.getHeight() - 10, 263, 30);
		lblInfoFoto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		lblInfoFoto.setForeground(Color.RED);
		add(lblInfoFoto);

		lblServico = new JLabel("Serviço:");
		lblServico.setBounds(lblNomeCliente.getX(),
				txtNomeCliente.getY() + 190, 100, 30);
		lblServico.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(lblServico);

		btnNovoServico = new JButton("+");
		btnNovoServico.setBackground(corOriginalBtn);
		btnNovoServico.setForeground(Color.WHITE);
		btnNovoServico.setFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
		btnNovoServico.setBounds(lblServico.getX() + 60, lblServico.getY(), 45,
				27);
		btnNovoServico.setToolTipText("Cadastrar serviço");
		add(btnNovoServico);
		btnNovoServico.addActionListener(this);

		txtServico = new JTextField();
		txtServico
				.setBounds(lblServico.getX(), lblServico.getY() + 30, 220, 30);
		txtServico.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		add(txtServico);
		txtServico.addKeyListener(this);
		txtServico.addFocusListener(this);

		listaServicos = new JList<Servico>();
		listaServicos.setBounds(txtServico.getX() + 2, txtServico.getY() + 29,
				txtServico.getWidth() - 4, 100);
		listaServicos
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		listaServicos.setBorder(new LineBorder(new Color(200, 200, 200)));
		listaServicos.setVisible(false);
		listaServicos.addKeyListener(this);
		listaServicos.addMouseListener(this);
		listaServicos.addFocusListener(this);
		add(listaServicos);
		add(scrollServico);

		btnAddServico = new JButton("+");
		btnAddServico.setBackground(corOriginalBtn);
		btnAddServico.setForeground(Color.WHITE);
		btnAddServico.setFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
		btnAddServico.setBounds(txtServico.getX() + txtServico.getWidth() + 10,
				txtServico.getY(), 45, 27);
		btnAddServico.setToolTipText("Adicionar serviço");
		add(btnAddServico);
		btnAddServico.addActionListener(this);
		// btnAddServico.setEnabled(false);

		btnRemServico = new JButton("-");
		btnRemServico.setBackground(corOriginalBtn);
		btnRemServico.setForeground(Color.WHITE);
		btnRemServico.setFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
		btnRemServico.setBounds(txtServico.getX() + txtServico.getWidth() + 10,
				btnAddServico.getY() + 40, 45, 27);
		btnRemServico.setToolTipText("Remover Servico");
		add(btnRemServico);
		btnRemServico.addActionListener(this);
		// btnRemServico.setEnabled(false);

		lblValorServico = new JLabel("Valor:");
		lblValorServico.setBounds(lblServico.getX(), lblServico.getY() + 70,
				100, 30);
		lblValorServico.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		add(lblValorServico);

		txtValorServico = new JNumberFormatField();
		txtValorServico.setLimit(7);
		txtValorServico.setBounds(lblValorServico.getX() + 50,
				lblValorServico.getY(), 150, 30);
		txtValorServico.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				12));
		add(txtValorServico);
		txtValorServico.addKeyListener(this);

		lblTxtDescricaoServico = new JLabel();
		lblTxtDescricaoServico.setBounds(lblServico.getX(),
				lblValorServico.getY() + 30, 250, 30);
		lblTxtDescricaoServico.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblTxtDescricaoServico);

		lblInfoDescricaoServicoCompleta = new JLabel();
		lblInfoDescricaoServicoCompleta.setBounds(lblTxtDescricaoServico.getX()
				+ lblTxtDescricaoServico.getWidth(),
				lblTxtDescricaoServico.getY(), 150, 30);
		lblInfoDescricaoServicoCompleta.setIcon(new ImageIcon("Img/info.png"));
		lblInfoDescricaoServicoCompleta.setVisible(false);
		add(lblInfoDescricaoServicoCompleta);

		lblSubTotalServico = new JLabel("Subtotal Serviço:");
		lblSubTotalServico.setBounds(lblServico.getX(),
				lblNomeCliente.getY() + 460, 150, 30);
		lblSubTotalServico.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 17));
		add(lblSubTotalServico);

		lblTxtSubTotalServico = new JLabel("R$ 0,00");
		lblTxtSubTotalServico.setBounds(lblSubTotalServico.getX() + 142,
				lblSubTotalServico.getY(), 500, 30);
		lblTxtSubTotalServico.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 16));
		add(lblTxtSubTotalServico);

		lblProduto = new JLabel("Produto:");
		lblProduto.setBounds(lblServico.getX() + 300, lblServico.getY(), 100,
				30);
		lblProduto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(lblProduto);

		txtProduto = new JTextField();
		txtProduto.setBounds(lblProduto.getX(), lblProduto.getY() + 30,
				txtServico.getWidth(), 30);
		txtProduto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(txtProduto);
		txtProduto.addKeyListener(this);

		btnAddProduto = new JButton("+");
		btnAddProduto.setBackground(corOriginalBtn);
		btnAddProduto.setForeground(Color.WHITE);
		btnAddProduto.setFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
		btnAddProduto.setBounds(txtProduto.getX() + txtProduto.getWidth() + 10,
				txtProduto.getY(), btnAddServico.getWidth(), 27);
		btnAddProduto.setToolTipText("Adicionar produto");
		add(btnAddProduto);
		btnAddProduto.addActionListener(this);

		btnRemProduto = new JButton("-");
		btnRemProduto.setBackground(corOriginalBtn);
		btnRemProduto.setForeground(Color.WHITE);
		btnRemProduto.setFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
		btnRemProduto.setBounds(txtProduto.getX() + txtProduto.getWidth() + 10,
				txtProduto.getY() + 40, btnAddServico.getWidth(), 27);
		btnRemProduto.setToolTipText("Remover produto");
		add(btnRemProduto);
		btnRemProduto.addActionListener(this);

		lblQuantidadeProduto = new JLabel("Quantidade:");
		lblQuantidadeProduto.setBounds(lblProduto.getX(),
				lblProduto.getY() + 70, 100, 30);
		lblQuantidadeProduto.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblQuantidadeProduto);

		txtQuantidadeProduto = new JIntNumberFormatField();
		txtQuantidadeProduto.setLimit(7);
		txtQuantidadeProduto.setBounds(lblQuantidadeProduto.getX() + 95,
				lblQuantidadeProduto.getY(), 123, 30);
		txtQuantidadeProduto.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		txtQuantidadeProduto.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 12));
		add(txtQuantidadeProduto);
		txtQuantidadeProduto.addKeyListener(this);

		btnNovoProduto = new JButton("+");
		btnNovoProduto.setBackground(corOriginalBtn);
		btnNovoProduto.setForeground(Color.WHITE);
		btnNovoProduto.setFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
		btnNovoProduto.setBounds(lblProduto.getX() + 63, lblProduto.getY(), 45,
				27);
		btnNovoProduto.setToolTipText("Cadastrar produto");
		add(btnNovoProduto);
		btnNovoProduto.addActionListener(this);

		lblTxtNomeProduto = new JLabel("Estoque:");
		lblTxtNomeProduto.setBounds(lblQuantidadeProduto.getX(),
				txtQuantidadeProduto.getY() + 30, 60, 30);
		lblTxtNomeProduto.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		lblTxtNomeProduto.setVisible(false);

		add(lblTxtNomeProduto);

		lblInfoNomeProdutoCompleto = new JLabel();
		lblInfoNomeProdutoCompleto.setBounds(lblTxtNomeProduto.getX()
				+ lblTxtNomeProduto.getWidth() + 6,
				lblTxtNomeProduto.getY() + 0, 150, 30);

		add(lblInfoNomeProdutoCompleto);

		lblSubTotalProduto = new JLabel("Subtotal Produto:");
		lblSubTotalProduto.setBounds(lblProduto.getX(),
				lblSubTotalServico.getY(), 150, 30);
		lblSubTotalProduto.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 17));
		add(lblSubTotalProduto);

		lblTxtSubTotalProduto = new JLabel("R$ 0,00");
		lblTxtSubTotalProduto.setBounds(lblSubTotalProduto.getX() + 148,
				lblSubTotalProduto.getY(), 500, 30);
		lblTxtSubTotalProduto.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 16));
		add(lblTxtSubTotalProduto);

		lblObservacao = new JLabel("Observação:");
		lblObservacao.setBounds(lblFotoCliente.getX(), lblServico.getY(), 100,
				30);
		lblObservacao
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(lblObservacao);

		txtObservacao = new JTextArea();
		txtObservacao
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		DefaultCaret caret = (DefaultCaret) txtObservacao.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		txtObservacao.setLineWrap(true);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		JScrollPane scrollOb = new JScrollPane(txtObservacao);
		scrollOb.setBounds(lblObservacao.getX(), lblObservacao.getY() + 30,
				230, 240);
		add(scrollOb);
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		lblTotal = new JLabel("Total:");
		lblTotal.setBounds(lblSubTotalServico.getX(), lblNomeCliente.getY() + 515, 150,
				30);
		lblTotal.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 32));
		add(lblTotal);

		lblTxtTotal = new JLabel("R$ 0,00");
		lblTxtTotal.setBounds(lblTotal.getX() + 95, lblTotal.getY() + 1, 150,
				30);
		lblTxtTotal.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 28));
		add(lblTxtTotal);
		
		lblDesconto = new JLabel("Desconto:");
		lblDesconto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 19));
		lblDesconto.setBounds(lblSubTotalProduto.getX(),
				lblTotal.getY(), 250, 30);
		add(lblDesconto);

		txtDesconto = new JNumberFormatField();
		txtDesconto.setLimit(7);
		txtDesconto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 18));
		txtDesconto.setBounds(lblDesconto.getX() + 100, lblDesconto.getY() + 1,
				105, 30);
		add(txtDesconto);

		btnPagamentoComanda = new JButton("Pagar");
		btnPagamentoComanda.setBackground(corOriginalBtn);
		btnPagamentoComanda.setForeground(Color.WHITE);
		btnPagamentoComanda.setFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
		btnPagamentoComanda.setBounds(lblFotoCliente.getX(),
				lblNomeCliente.getY() + 480, 110, 35);
		add(btnPagamentoComanda);
		btnPagamentoComanda.addActionListener(this);

		btnCancelarComanda = new JButton("Cancelar");
		btnCancelarComanda.setBackground(corOriginalBtn);
		btnCancelarComanda.setForeground(Color.WHITE);
		btnCancelarComanda.setFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
		btnCancelarComanda.setBounds(btnPagamentoComanda.getX() + 120,
				btnPagamentoComanda.getY(), btnPagamentoComanda.getWidth(), 35);
		add(btnCancelarComanda);
		btnCancelarComanda.addActionListener(this);

		// btne.setEnabled(false);

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

		lblFundo = new JLabel(new ImageIcon("Img/Janela de Comanda.png"));
		lblFundo.setBounds(0, 0, getWidth(), getHeight());
		add(lblFundo);

		addKeyListener(this);
		btnNovoProduto.addKeyListener(this);
		btnNovoServico.addKeyListener(this);
		btnAddProduto.addKeyListener(this);
		btnAddServico.addKeyListener(this);
		btnRemProduto.addKeyListener(this);
		btnRemServico.addKeyListener(this);
		txtObservacao.addKeyListener(this);
		txtValorServico.addKeyListener(this);
		txtQuantidadeProduto.addKeyListener(this);
		btnPagamentoComanda.addKeyListener(this);
		btnCancelarComanda.addKeyListener(this);

	}

	public void atualizaTabelaClientes(DefaultTableModel modelo) {
		modelo.setNumRows(0);
		int linha = 0;
		if (ComandaController.getClientes() != null) {
			listaAtualTabela = ComandaController.getClientes();
			Collections.sort(listaAtualTabela);
			for (Cliente cc : listaAtualTabela) {
				Cliente c = sobreescreveTextoNulo(cc);
				if (c.getNome().length() < 20
						|| divideNomeCliente(c.getNome()) < 0) {
					modelo.addRow(new Object[] { c.getNome(),
							c.getLstTelefones().get(0).getTelefone(),
							c.getEmail() });
				} else {
					modelo.addRow(new Object[] {
							"<HTML><CENTER>"
									+ c.getNome().substring(0,
											divideNomeCliente(c.getNome()))
									+ "<BR>"
									+ c.getNome().substring(
											divideNomeCliente(c.getNome()),
											c.getNome().length()),
							c.getLstTelefones().get(0).getTelefone(),
							c.getEmail() });
					tblListar.setRowHeight(linha, 30);
				}
				linha++;
			}
		}
	}

	private int divideNomeCliente(String nome) {
		try {
			if (nome.indexOf(" ", 25) < 30)
				return nome.indexOf(" ", 25);
			else
				return nome.indexOf(" ", 15);
		} catch (Exception e) {
			return -1;
		}
	}

	private int divideNomeServicoProduto(String nome) {
		try {
			if (nome.indexOf(" ", 15) < 20 && nome.indexOf(" ", 15) >0)
				return nome.indexOf(" ", 15);
			else if (nome.indexOf(" ", 8)>0)
				return nome.indexOf(" ", 8);
		} catch (Exception ex) {
			return 0;
		}
		return 0;
	}

	public void atualizaTabelaPesquisaClientes(DefaultTableModel modelo) {
		modelo.setNumRows(0);
		int linha = 0;
		if (ComandaController.getClientes(txtNomeCliente.getText()) != null) {
			listaAtualTabela = ComandaController.getClientes(txtNomeCliente
					.getText());
			for (Cliente cc : ComandaController.getClientes(txtNomeCliente
					.getText())) {
				Cliente c = sobreescreveTextoNulo(cc);
				if (c.getNome().length() < 20
						|| divideNomeCliente(c.getNome()) < 0) {
					modelo.addRow(new Object[] { c.getNome(),
							c.getLstTelefones().get(0).getTelefone(),
							c.getEmail() });
				} else {
					modelo.addRow(new Object[] {
							"<HTML><CENTER>"
									+ c.getNome().substring(0,
											divideNomeCliente(c.getNome()))
									+ "<BR>"
									+ c.getNome().substring(
											divideNomeCliente(c.getNome()),
											c.getNome().length()),

							c.getLstTelefones().get(0).getTelefone(),
							c.getEmail() });
					tblListar.setRowHeight(linha, 30);
				}
				linha++;
			}
		}
	}

	private Cliente sobreescreveTextoNulo(Cliente cc) {
		Cliente c = cc;
		String tracos = "----------------------";
		if (c.getLstTelefones().size() < 1) {
			Telefones tc = new Telefones();
			tc.setTelefone(tracos);
			c.getLstTelefones().add(tc);
		}
		if (c.getNome().equals(""))
			c.setNome(tracos);
		if (c.getDataNasc().replaceAll("[/_]", "").equals(""))
			c.setDataNasc("------------------");
		if (c.getRg().equals(""))
			c.setRg(tracos);
		if (c.getCpf().replaceAll("[/._-]", "").equals(""))
			c.setCpf(tracos);
		if (c.getSexo().equals(""))
			c.setSexo("-----");
		if (c.getLogradouro().equals(""))
			c.setLogradouro(tracos);
		if (c.getNumCliente().equals(""))
			c.setNumCliente("---------");
		if (c.getCep().replaceAll("[/_-]", "").equals(""))
			c.setCep("---------------");
		if (c.getBairro().equals(""))
			c.setBairro(tracos);
		if (c.getCidade().equals(""))
			c.setCidade(tracos);
		if (c.getComplemento().equals(""))
			c.setComplemento(tracos);
		if (c.getEstado().equals(""))
			c.setEstado("------");
		if (c.getEmail().equals(""))
			c.setEmail(tracos);
		if (c.getFotoCliente().equals(""))
			c.setFotoCliente(tracos);

		return c;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (((KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,
				java.awt.event.InputEvent.ALT_DOWN_MASK)) != null)
				&& e.getKeyCode() == KeyEvent.VK_F4) {
			mensagemFechar();
		}

	}

	private void pesquisa() {
		if (txtNomeCliente.getText().length() > 0) {
			atualizaTabelaPesquisaClientes(modelTCliente);
		} else
			atualizaTabelaClientes(modelTCliente);
	}

	String txtCmb = "";

	@Override
	public void keyReleased(KeyEvent e) {
		if (txtNomeCliente.hasFocus()) {
			fotoCliente.setIcon(null);
			pesquisa();
		}

		if (tblListar.isEnabled() && tblListar.getSelectedRow() > -1
				&& tblListar.hasFocus()) {
			setDadosCliente();
		}
		if (txtProduto.hasFocus()) {
			int estoqueAux=0;
			// txtProduto.hasFocus()
			if (txtProduto.getText().trim().length() > 0) {
				Produto p = ComandaProdutoController.getProduto(txtProduto
						.getText().trim());
				
				for (Produto produto: listaAtualTabelaP){
					if (p.getId() == produto.getId()){
						estoqueAux = produto.getQuantidadeProdutoComanda();
					}
					
				}
				
				// lblTxtNomeProduto.setText(p.getNomeProduto());
				// lblTxtQuantidadeProdutoEstoque.setText("");
				Estoque estoque = new Estoque();

			
				estoque.setCodProduto(p.getId());
				if (estoque.getCodProduto() != 0) {
					
					if (eControlle.conferirEstoque(estoque)) {

						lblInfoNomeProdutoCompleto.setText((eControlle
								.pegarEstoque(estoque)-estoqueAux) + "");
						lblTxtNomeProduto.setVisible(true);
						lblInfoNomeProdutoCompleto.setVisible(true);
						
					} else {
						estoque.setQuantidadeEstoqueProduto(0);
						eControlle.acaoEstoque(estoque);

						lblInfoNomeProdutoCompleto.setText(estoque
								.getQuantidadeEstoqueProduto() + "");
						lblTxtNomeProduto.setVisible(true);
					}
				} else {
					lblInfoNomeProdutoCompleto.setText("");
					lblTxtNomeProduto.setVisible(false);
				}

			} else {

				lblInfoNomeProdutoCompleto.setText("");
				lblTxtNomeProduto.setVisible(false);
			}

		}

		if (ComandaServicoController.getServico(txtServico.getText())
				.getDescricaoServico() == null) {
			lblTxtDescricaoServico.setText("");
			lblInfoDescricaoServicoCompleta.setVisible(false);
			txtValorServico.setText("");
		}

		if (txtServico.hasFocus()) {
			if (txtServico.getText().length() >= 2) {
				if (e.getKeyCode() != e.VK_DOWN) {
					pesquisaServico();
				} else {
					listaServicos.setSelectedIndex(0);
					listaServicos.grabFocus();
				}
			} else if (listaServicos.isVisible()) {
				listaServicos.setVisible(false);
			}
		} else
			listaServicos.setVisible(false);

		if (listaServicos.hasFocus()) {
			if (e.getKeyCode() == e.VK_DOWN) {
				if (listaServicos.getSelectedIndex() < servicos.size()) {
					listaServicos.setSelectedIndex(1);
				}

			} else if (e.getKeyCode() == e.VK_UP) {
				try {
					listaServicos.setSelectedIndex(listaServicos
							.getSelectedIndex() - 1);
				} catch (Exception ex) {
				}
			}
			listaServicos.grabFocus();
		}

		if (tblServicos.hasFocus()) {
			if (e.VK_DOWN == e.getKeyCode() || e.VK_UP == e.getKeyCode())
				verificaLinhaSelecionadaServico();
		}

		if (tblProdutos.hasFocus()) {
			if (e.VK_DOWN == e.getKeyCode() || e.VK_UP == e.getKeyCode())
				verificaLinhaSelecionadaProduto();
		}

	}

	private void pesquisaServico() {
		DefaultListModel<Servico> lstServico = new DefaultListModel<Servico>();
		servicos = ComandaServicoController.getServicos(txtServico.getText(),
				listaAtualTabelaS);
		int heigthLst = 0;
		for (Servico s : servicos) {
			lstServico.addElement(s);
			heigthLst = heigthLst + 20;
		}
		listaServicos.setSize(listaServicos.getWidth(), heigthLst);
		listaServicos.setModel(lstServico);
		listaServicos.setVisible(true);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		jh.atualizaVisorCaixa();
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

	public void addLinhaTabelaServicos(DefaultTableModel modelo, String valor,
			Servico ser) {
		Servico ss = ser;

		ServicoComanda s = new ServicoComanda();
		s.setCod(ss.getCodServico());
		s.setNome(ss.getNomeServico());
		s.setDescricao(ss.getDescricaoServico());
		s.setValor(valor);
		listaAtualTabelaS.add(s);

		if (s.getNome().length() < 18 || divideNomeServicoProduto(s.getNome())==0)
			modelo.addRow(new Object[] { s.getNome(), s.getValor() });
		else {
			modelo.addRow(new Object[] {
					"<HTML><CENTER>"
							+ s.getNome().substring(0,
									divideNomeServicoProduto(s.getNome()))
							+ "<BR>"
							+ s.getNome().substring(
									divideNomeServicoProduto(s.getNome()),
									s.getNome().length()),
					s.getValor() });
			tblServicos.setRowHeight(linhaTblServico, 30);
		}
		linhaTblServico++;

	}

	public void remLinhaTabelaServicos(DefaultTableModel modelo,
			int linhaSelecionada) {
		String v = tblServicos.getValueAt(linhaSelecionada, 1).toString()
				.replace(",", ".");
		double valor = Double.parseDouble(v.substring(3, v.length()));
		subTotalServico -= valor;
		lblTxtSubTotalServico.setText("R$ "
				+ String.format("%.2f", subTotalServico));
		setTotal(valor, 0);

		String nome = tblServicos.getValueAt(linhaSelecionada, 0).toString();

		if (nome.length() > 10)
			if (nome.substring(0, 6).equals("<HTML>")) {
				nome = tblServicos.getValueAt(linhaSelecionada, 0).toString()
						.substring(14, 29)
						+ tblServicos.getValueAt(linhaSelecionada, 0)
								.toString().substring(33, 43);
			}

		modelo.removeRow(linhaSelecionada);

		for (ServicoComanda s : listaAtualTabelaS) {
			if (s.getNome().equals(
					ComandaServicoController.getServico(nome).getNomeServico())) {
				listaAtualTabelaS.remove(s);
				break;
			}
		}
		linhaTblServico--;

		lblTxtDescricaoServico.setText("");
		lblInfoDescricaoServicoCompleta.setVisible(false);
		linhaSelecionadaServico = -1;
	}

	public void atualizaNovoServico(DefaultTableModel modelo) {
		modelo.setNumRows(0);
		linhaTblServico = 0;
		for (Servico ss : ComandaServicoController.getServicos()) {
			for (ServicoComanda s : servicosAux) {
				if (ss.getCodServico() == s.getCod()) {
					if (ss.getNomeServico().length() < 18)
						modelo.addRow(new Object[] { ss.getNomeServico(),
								s.getValor() });
					else {
						modelo.addRow(new Object[] {
								"<HTML><center>"
										+ ss.getNomeServico().substring(
												0,
												divideNomeServicoProduto(s
														.getNome()))
										+ "<br>"
										+ ss.getNomeServico().substring(
												divideNomeServicoProduto(s
														.getNome()),
												s.getNome().length())
										+ "</HTML>", s.getValor() });
						tblServicos.setRowHeight(linhaTblServico, 30);
					}
					linhaTblServico++;

				}
			}
		}
		linhaSelecionadaServico = -1;
	}
	
	
	
	public void atualizaProduto(DefaultTableModel modelo, Produto p, Produto produtoExcluir) {
		modelo.setNumRows(0);
		listaAtualTabelaP.remove(produtoExcluir);
		listaAtualTabelaP.add(p);
		Collections.sort(listaAtualTabelaP);
		
			for (Produto produto: listaAtualTabelaP){
				if (produto.getNomeProduto().length() < 18)
					modelo.addRow(new Object[] { produto.getNomeProduto(), produto.getQuantidadeProdutoComanda() });
				else {
					if (divideNomeServicoProduto(produto.getNomeProduto()) > 0) {
						modelo.addRow(new Object[] {
								"<HTML><center>"
										+ produto.getNomeProduto().substring(
												0,
												divideNomeServicoProduto(produto.getNomeProduto()))
										+ "<br>"
										+ produto.getNomeProduto().substring(
												divideNomeServicoProduto(produto.getNomeProduto()),
												produto.getNomeProduto().length())
										+ "</HTML>", produto.getQuantidadeProdutoComanda() });
						tblProdutos.setRowHeight(linhaTblProduto, 30);
					} else {
						modelo.addRow(new Object[] {produto.getNomeProduto(), produto.getQuantidadeProdutoComanda() });
					}
				}
			}
				//linhaTblProduto++;
				linhaSelecionadaProduto = -1;
				
		
		
		
	}

	

	public void addLinhaTabelaProdutos(DefaultTableModel modelo, Produto p,
			int quantidade) {
		// verificando se a linha da tabela devera ser dividida em duas
		// em relação ao nome do Produto
		listaAtualTabelaP.add(p);
		if (p.getNomeProduto().length() < 18)
			modelo.addRow(new Object[] { p.getNomeProduto(), quantidade });
		else {
			if (divideNomeServicoProduto(p.getNomeProduto()) > 0) {
				modelo.addRow(new Object[] {
						"<HTML><center>"
								+ p.getNomeProduto().substring(
										0,
										divideNomeServicoProduto(p
												.getNomeProduto()))
								+ "<br>"
								+ p.getNomeProduto().substring(
										divideNomeServicoProduto(p
												.getNomeProduto()),
										p.getNomeProduto().length())
								+ "</HTML>", quantidade });
				tblProdutos.setRowHeight(linhaTblProduto, 30);
			} else {
				modelo.addRow(new Object[] { p.getNomeProduto(), quantidade });
			}
		}
		linhaTblProduto++;
		linhaSelecionadaProduto = -1;
	}
	
	
	
	
	
	

	public void remLinhaTabelaProdutos(DefaultTableModel modelo,
			int linhaSelecionadaTabelaProd) {

		Produto aux = listaAtualTabelaP.get(linhaSelecionadaTabelaProd);

		String p = tblProdutos.getValueAt(linhaSelecionadaTabelaProd, 1)
				.toString();
		int quantidade = Integer.parseInt(p);
		double valor = aux.getValor() * quantidade;
		subTotalProduto -= valor;
		lblTxtSubTotalProduto.setText("R$ "
				+ String.format("%.2f", subTotalProduto));

		setTotal(valor, 0);

		String nome = tblProdutos.getValueAt(linhaSelecionadaTabelaProd, 0)
				.toString();

		if (nome.length() > 10)
			if (nome.substring(0, 6).equals("<HTML>")) {
				nome = tblProdutos.getValueAt(linhaSelecionadaTabelaProd, 0)
						.toString().substring(14, 29)
						+ tblProdutos.getValueAt(linhaSelecionadaTabelaProd, 0)
								.toString().substring(33, 43);
			}

		for (Produto s : listaAtualTabelaP) {
			if (aux.getCodBarras().equals(s.getCodBarras())) {
				listaAtualTabelaP.remove(s);
				break;
			}
		}

		// removendo linha selecionada
		modelo.removeRow(linhaSelecionadaTabelaProd);
		linhaTblProduto--;

		lblTxtNomeProduto.setVisible(false);
		lblInfoNomeProdutoCompleto.setVisible(false);
		linhaSelecionadaProduto = -1;
	}

	private void setTotal(double subTotal, int tipo) {
		if (tipo == 1)
			total += subTotal;
		else
			total -= subTotal;
		lblTxtTotal.setText("R$ " + String.format("%.2f", total));
	}

	private ArrayList<Servico> converteListas(ArrayList<ServicoComanda> sc) {
		ArrayList<Servico> servicos = new ArrayList<Servico>();
		for (ServicoComanda ss : sc) {
			Servico s = new Servico();
			s.setCodServico(ss.getCod());
			s.setDescricaoServico(ss.getDescricao());
			s.setNomeServico(ss.getNome());
			s.setValorServico(Double.parseDouble(ss.getValor()
					.substring(3, ss.getValor().length()).replace(",", ".")));
			servicos.add(s);
		}
		return servicos;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnPagamentoComanda) {
			if (total > 0) {
				Date data = new Date();
				SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

				if (tblListar.getSelectedRow() != -1) {
					ComandaServico s = new ComandaServico();
					s.setObservacao(txtObservacao.getText());
					s.setSubTotalComanda(subTotalServico);
					s.setServicos(converteListas(listaAtualTabelaS));
					s.setDataComanda(formatador.format(data));

					ComandaProduto p = new ComandaProduto();
					p.setProdutos(listaAtualTabelaP);
					p.setSubTotalProduto(subTotalProduto);
					p.setDataComanda(formatador.format(data));

					new JanelaPagamento(this, s, p, total, clienteSelecionado, Double.parseDouble(txtDesconto.getText().substring(3).replace(",", ".")));
				} else {
					JOptionPane.showMessageDialog(this, "Selecione um cliente");
				}
			} else {
				JOptionPane.showMessageDialog(this,
						"Adicione um serviço ou produto");
			}
		}

		if (e.getSource() == btnCancelarComanda) {
			mensagemFechar();
		}

		if (e.getSource() == btnAddServico) {
			if (ComandaServicoController.getServico(txtServico.getText())
					.getNomeServico() != null)
				try {
					Servico servico = ComandaServicoController
							.getServico(txtServico.getText());
					double valor = Double.parseDouble(txtValorServico.getText()
							.substring(3).replace(",", "."));
					if (valor > 0) {
						subTotalServico += valor;

						lblTxtSubTotalServico.setText("R$ "
								+ String.format("%.2f", subTotalServico));
						addLinhaTabelaServicos(modelTServico,
								"R$ " + String.format("%.2f", valor), servico);
						txtValorServico.setText("");
						txtServico.setText("");
						lblTxtDescricaoServico.setText("");
						lblInfoDescricaoServicoCompleta.setVisible(false);
						setTotal(valor, 1);
					} else {
						JOptionPane.showMessageDialog(this,
								"Insira um valor válido");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this,
							"Insira um valor válido");
					ex.printStackTrace();
				}
			else
				JOptionPane.showMessageDialog(this,
						"Insira um serviço cadastrado");
		}

		if (e.getSource() == btnRemServico) {
			if (tblServicos.getSelectedRow() > -1) {

				remLinhaTabelaServicos(modelTServico,
						tblServicos.getSelectedRow());

			} else {
				JOptionPane.showMessageDialog(this,
						"Selecione um serviço da lista");
			}
		}

		if (e.getSource() == btnNovoServico) {
			servicosAux = listaAtualTabelaS;
			new JanelaServico(null, this,null);
			this.setVisible(false);
		}

		if (e.getSource() == btnNovoProduto) {
			new JanelaProduto(null, null, this);
			this.setVisible(false);

		}
			
		if (e.getSource() == btnAddProduto) {
			if (ComandaProdutoController.getProduto(txtProduto.getText().trim())
					.getCodBarras() != null){
				Produto p = ComandaProdutoController.getProduto(txtProduto
						.getText().trim());
				Produto p1 = null;
				try{
				  for (Produto produto: listaAtualTabelaP){
					  if (produto.getId()== p.getId()){
						  
						  p1 = produto;
						  if (txtQuantidadeProduto.getText().trim().length() > 0) {
								int quantidade = Integer.parseInt(txtQuantidadeProduto
										.getText().trim());
								Estoque estoque = new Estoque();
								estoque.setCodProduto(p.getId());
								estoque.setQuantidadeEstoqueProduto(eControlle
										.pegarEstoque(estoque));
								if (quantidade>0){
									if ((quantidade+produto.getQuantidadeProdutoComanda())
											<= estoque.getQuantidadeEstoqueProduto()){
										
										subTotalProduto += p.getValor() * quantidade;
										p.setQuantidadeProdutoComanda(
												produto.getQuantidadeProdutoComanda()+quantidade);
										lblTxtSubTotalProduto.setText("R$ "+ String.format("%.2f",
														subTotalProduto));
										//listaAtualTabelaP.remove(produto);
									
										//addLinhaTabelaProdutos(modelTProduto, p,
											//	quantidade);
										
										atualizaProduto(modelTProduto,p,produto);
										
										txtQuantidadeProduto.setText("");
										txtProduto.setText("");
										lblTxtNomeProduto.setVisible(false);
										lblInfoNomeProdutoCompleto.setVisible(false);

										setTotal(subTotalProduto, 1);
										
										
										
										
										
										
									}else{
										JOptionPane.showMessageDialog(null, "Estoque insuficiente");
									}
									
									
									
									
								}else{
									JOptionPane.showMessageDialog(null, "Insira uma quantidade válida");
								}
						  }else{
							  JOptionPane.showMessageDialog(null, "Insira uma quantidade válida");
						  }
						
						  
					  }
				  }
				}catch(Exception e1){
					
				}
						  
						
					  
				  if(p1==null)
				try {
					if (txtQuantidadeProduto.getText().length() > 0) {
						int quantidade = Integer.parseInt(txtQuantidadeProduto
								.getText().replace(".", ""));
						Estoque estoque = new Estoque();
						estoque.setCodProduto(p.getId());
						estoque.setQuantidadeEstoqueProduto(eControlle
								.pegarEstoque(estoque));
						if (quantidade > 0) {
							if (quantidade <= estoque
									.getQuantidadeEstoqueProduto()) {
								subTotalProduto += p.getValor() * quantidade;
								p.setQuantidadeProdutoComanda(quantidade);

								lblTxtSubTotalProduto
										.setText("R$ "
												+ String.format("%.2f",
														subTotalProduto));

								addLinhaTabelaProdutos(modelTProduto, p,
										quantidade);

								txtQuantidadeProduto.setText("");
								txtProduto.setText("");
								lblTxtNomeProduto.setVisible(false);
								lblInfoNomeProdutoCompleto.setVisible(false);

								setTotal(subTotalProduto, 1);
							} else {
								JOptionPane.showMessageDialog(null,
										"Estoque insuficiente");
							}
						} else {
							JOptionPane.showMessageDialog(this,
									"Insira uma quantidade válido");
						}
					} else {
						JOptionPane.showMessageDialog(null,
								"Insira uma quantidade para o produto");
					}
				} catch (Exception ex) {
					JOptionPane
							.showMessageDialog(null,
									"Somente caracteres númericos serão aceitos no campo quantidade");
				}
				
			
		}else{
				JOptionPane.showMessageDialog(null,
						"Insira um produto cadastrado");
		}
			
			
		}

		if (e.getSource().equals(btnRemProduto)) {

			if (tblProdutos.getSelectedRow() > -1) {

				remLinhaTabelaProdutos(modelTProduto,
						tblProdutos.getSelectedRow());

			} else {
				JOptionPane.showMessageDialog(this,
						"Selecione um produto da lista");
			}

		}

	}

	public double getSubTotalServico() {
		return subTotalServico;
	}

	public void setSubTotalServico(double subTotalServico) {
		this.subTotalServico = subTotalServico;
	}

	public double getSubTotalProduto() {
		return subTotalProduto;
	}

	public void setSubTotalProduto(double subTotalProduto) {
		this.subTotalProduto = subTotalProduto;
	}

	private void mensagemFechar() {
		if (JOptionPane.showConfirmDialog(this, "Deseja cancelar a comanda?\n"
				+ "Todas as informações serão perdidas.") == JOptionPane.YES_OPTION)
			this.dispose();
	}

	private void verificaLinhaSelecionadaProduto() {
		if (linhaSelecionadaProduto != tblProdutos.getSelectedRow()) {
			linhaSelecionadaProduto = tblProdutos.getSelectedRow();
			Produto p = listaAtualTabelaP.get(tblProdutos.getSelectedRow());
			
			Estoque e = new Estoque();
			e.setCodProduto(p.getId());
		lblTxtNomeProduto.setVisible(true);
				lblInfoNomeProdutoCompleto.setVisible(true);
			int estoque = eController.pegarEstoque(e) - p.getQuantidadeProdutoComanda();
			lblInfoNomeProdutoCompleto.setText(estoque + "");
		
		} else {
			tblProdutos.getSelectionModel().clearSelection();
			lblTxtNomeProduto.setVisible(false);
			lblInfoNomeProdutoCompleto.setVisible(false);
			linhaSelecionadaProduto = -1;
		}
	}

	private boolean verificaLinhaSelecionadaServico() {
		if (linhaSelecionadaServico != tblServicos.getSelectedRow()) {
			linhaSelecionadaServico = tblServicos.getSelectedRow();
			String nome = tblServicos.getValueAt(linhaSelecionadaServico, 0)
					.toString();
			if (nome.length() > 10)
				if (nome.substring(0, 6).equals("<HTML>")) {
					nome = tblServicos
							.getValueAt(tblServicos.getSelectedRow(), 0)
							.toString().replace("<HTML><CENTER>", "").replace("<BR>", "");
				}
			lblTxtDescricaoServico.setText(ComandaServicoController.getServico(
					nome).getDescricaoServico());
			if (ComandaServicoController.getServico(nome).getDescricaoServico()
					.length() > 18) {
				lblInfoDescricaoServicoCompleta.setVisible(true);
				lblInfoDescricaoServicoCompleta
						.setToolTipText(lblTxtDescricaoServico.getText());
			} else {
				lblInfoDescricaoServicoCompleta.setVisible(false);
			}
			return true;
		} else {
			tblServicos.getSelectionModel().clearSelection();
			lblTxtDescricaoServico.setText("");
			lblInfoDescricaoServicoCompleta.setVisible(false);
			linhaSelecionadaServico = -1;
			return false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(lblMinimize)) {
			this.setExtendedState(JFrame.ICONIFIED);
		}

		else if (e.getSource().equals(lblClose)) {
			mensagemFechar();
		}

		if (listaServicos.hasFocus()) {
			txtServico.setText(listaServicos.getSelectedValue()
					.getNomeServico());
			listaServicos.setVisible(false);
			txtValorServico.grabFocus();
			lblTxtDescricaoServico.setText(ComandaServicoController.getServico(
					txtServico.getText()).getDescricaoServico());
			txtValorServico.setText(String.format("%.2f",(ComandaServicoController.getServico(
					txtServico.getText()).getValorServico())));
			if (ComandaServicoController.getServico(txtServico.getText())
					.getDescricaoServico().length() > 30) {
				lblInfoDescricaoServicoCompleta
						.setToolTipText(lblTxtDescricaoServico.getText());
				lblInfoDescricaoServicoCompleta.setVisible(true);
			}
		} else {
			listaServicos.setVisible(false);
		}

		if (tblServicos.hasFocus()) {
			if (tblServicos.getSelectedRow() > -1) {
				verificaLinhaSelecionadaServico();

			}
		}

		if (tblProdutos.hasFocus() && tblProdutos.getSelectedRow() > -1) {
			verificaLinhaSelecionadaProduto();

		}

	}

	boolean verifica = true;

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource().equals(scrollServico) && listaServicos.isVisible()) {
			listaServicos.setVisible(false);
			listaServicos.setVisible(true);
		}

		if (e.getSource().equals(listaServicos)) {
			verifica = false;
		}

	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource().equals(scrollServico) && listaServicos.isVisible()) {
			listaServicos.setVisible(false);
			listaServicos.setVisible(true);
		}

		if (e.getSource().equals(listaServicos)) {
			verifica = true;
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
		if (tblListar.hasFocus()) {
			setDadosCliente();
		}

		if (e.getSource() == fotoCliente && !localFoto.equals("")) {
			new JanelaVisualizarImagem(this);
		}

		if (tblServicos.getSelectedRow() > -1) {
			tblServicos.setShowHorizontalLines(false);
			tblServicos.setShowVerticalLines(false);
		}
	}

	private void setDadosCliente(){
		txtNomeCliente.setText(tblListar.getValueAt(
				tblListar.getSelectedRow(), 0).toString().replace("<HTML><CENTER>", "").replace("<BR>", ""));
		setFoto();

		tblListar.grabFocus();
		clienteSelecionado =ComandaController.getCliente(
				listaAtualTabela.get(tblListar.getSelectedRow()).getCod());
	}
	
	private void setFoto() {
		if (tblListar.getSelectedRow() != -1 && tblListar.hasFocus()) {
			lblInfoFoto.setText("");
			fotoCliente.setIcon(null);
			localFoto = ComandaController.getCliente(
					listaAtualTabela.get(tblListar.getSelectedRow()).getCod())
					.getFotoCliente();
			if (localFoto.length() > 30) {
				BufferedImage buff = setImagemDimensao(localFoto,
						fotoCliente.getWidth(), fotoCliente.getHeight());
				fotoCliente.setIcon(new ImageIcon(buff));
			} else {
				fotoCliente.setIcon(null);
				localFoto = "";
			}
		}
	}

	public BufferedImage setImagemDimensao(String caminhoImg,
			Integer imgLargura, Integer imgAltura) {
		Double novaImgLargura = null;
		Double novaImgAltura = null;
		Double imgProporcao = null;
		Graphics2D g2d = null;
		BufferedImage imagem = null, novaImagem = null;
		try {
			// pegando a imagem que vai ser redimensionada
			imagem = ImageIO.read(new File(caminhoImg));
		} catch (IOException ex) {
			System.err.println("Quebro na setImgDimensao:" + ex.getMessage());
			if (caminhoImg.length() > 30){
				lblInfoFoto.setText("Não foi possivel encontrar a imagem");
			}
			else
				lblInfoFoto.setText("");
			Logger.getLogger(JanelaProduto.class.getName()).log(Level.SEVERE,
					null, ex);
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

			novaImagem = new BufferedImage(novaImgLargura.intValue(),
					novaImgAltura.intValue(), BufferedImage.TYPE_INT_RGB);
			g2d = novaImagem.createGraphics();
			g2d.drawImage(imagem, 0, 0, novaImgLargura.intValue(),
					novaImgAltura.intValue(), null);

			return novaImagem;
		} catch (Exception ex) {
			System.err
					.println("Quebro na setImgDimensao(2):" + ex.getMessage());
		}
		return null;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		setLocation(getLocation().x + e.getX() - posX,
				getLocation().y + e.getY() - posY);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (e.getSource().equals(scrollServico) && listaServicos.isVisible()) {
			listaServicos.setVisible(false);
			listaServicos.setVisible(true);
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		if (e.getSource() == txtServico && txtServico.getText().length() >= 3)
			pesquisaServico();

	}
	
	public void remover (Produto p){
		listaAtualTabelaP.remove(p);
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (verifica) {
			if (e.getSource() == txtServico) {
				listaServicos.setVisible(false);
			}
		}
	}

}
