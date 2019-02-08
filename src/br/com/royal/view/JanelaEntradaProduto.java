package br.com.royal.view;

import java.awt.Color;
import java.awt.Cursor;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
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
import javax.swing.text.MaskFormatter;

import br.com.royal.controller.EntradaProdutoController;
import br.com.royal.controller.EstoqueController;
import br.com.royal.controller.FornecedorController;
import br.com.royal.controller.MotivoEntradaController;
import br.com.royal.controller.ProdutoController;
import br.com.royal.controller.Validacoes;
import br.com.royal.controllerReports.GerarRelatorioEntradaProduto;
import br.com.royal.model.EntradaProduto;
import br.com.royal.model.Estoque;
import br.com.royal.model.Fornecedor;
import br.com.royal.model.MotivoEntrada;
import br.com.royal.model.Produto;
import br.com.royal.modelReports.RelatorioEntradaProduto;

public class JanelaEntradaProduto extends JFrame implements ActionListener,
		MouseMotionListener, MouseListener, KeyListener, WindowListener, FocusListener{
	private JLabel lblFundo, lblQuantidadeEntrada, lblMotivoEntrada,
			lblDataValidadeLote, lblDataEntrada, lblLote, lblFornecedor,
			lblInfoCamposObrigatorios, lblProduto, lblImgInfoData1,
			lblImgInfoData2, lblNavbar, lblClose, lblMinimize,
			lblNovaEntradaProduto, lblSalvarEntradaProduto,
			lblEditarEntradaProduto, lblCancelar, lblRelatorioEntradaProduto,
			lblNomeProduto, lblTxtNomeProduto, lblDescricaoProduto,
			lblTxtDescricaoProduto, lblValorProduto, lblTxtValorProduto,
			lblQuantidadeMinimaProduto, lblTxtQuantidadeMinimaProduto,
			lblFabricanteProduto, lblTxtFabricanteProduto, lblTxtFoto, lblFoto;
	private JTextField txtLote, txtProduto;
	private JIntNumberFormatField txtQuantidadeEntrada;
	private JComboBox<Object> jcComboMotivo, jcComboFornecedor;
	private JFormattedTextField mskDataValidadeLote, mskDataEntrada;
	private MaskFormatter mascaraData = null, mascara2 = null;
	private JTable tabela;
	private Color corOriginalBtn = new Color(164, 6, 69);
	private JScrollPane scrollTabela;
	private FTable modelo = new FTable();
	private String txtBtn = "";
	private JButton btnPrimLinha, btnVoltar, btnAvancar, btnUltLinha,
			btnAddFornecedor, btnAddMotivo, btnAddProduto;
	private int posX, posY;
	private ProdutoController pController = new ProdutoController();
	private FornecedorController fController = new FornecedorController();
	private MotivoEntradaController motivoController = new MotivoEntradaController();
	private EntradaProdutoController epController = new EntradaProdutoController();
	private EstoqueController eController = new EstoqueController();
	private List<EntradaProduto> entrada = new ArrayList<EntradaProduto>();
	private String codigoBarras = "";
	private JanelaProdutosEmFalta jpef;
	private JanelaProduto jp;
	private int codigo;
	private int quantidade;
	public String localFoto="";
	
	private Produto produto = new Produto();

	public JanelaEntradaProduto(JanelaProdutosEmFalta jpef, JanelaProduto jp,
			String codigoBarras) {
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		this.jpef = jpef;
		this.jp = jp;
		this.codigoBarras = codigoBarras;
		setLayout(null);
		setSize(902, 620);
		setTitle("Entrada");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Img/Rosa.png"));
		setResizable(false);
		setUndecorated(true);
		inicializaComponentes();
		
		addWindowListener(this);
		produto.setCodBarras(codigoBarras);
		produto = pController.codigoBarras(produto);
		txtProduto.setText(codigoBarras);
		try {
			if (codigoBarras.equals("")) {

			} else {
				txtProduto.setText(codigoBarras);
				codigoBarras = "";
				if (produto.getId() != 0) {
					setCamposDadosProduto();
				}

			}
		} catch (Exception ex) {
		}
		setVisible(true);
	}


	public void remover(MotivoEntrada me) {
		jcComboMotivo.removeItem(me);
	}

	public void preencher() {
		jcComboMotivo.removeAllItems();
		jcComboMotivo.addItem(" ");
		for (MotivoEntrada m : motivoController.listar()) {
			jcComboMotivo.addItem(m);
		}
		jcComboFornecedor.removeAllItems();
		jcComboFornecedor.addItem(" ");
		for (Fornecedor f : fController.listar()) {
			jcComboFornecedor.addItem(f);
		}

	}

	public void pegarCodigo() {
		codigo = entrada.get(tabela.getSelectedRow()).getIdEntradaProduto();
		quantidade = entrada.get(tabela.getSelectedRow())
				.getQuantidadeEntradaProduto();

	}

	public void inicializaComponentes() {
		criaTabela();
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		lblFundo = new JLabel(new ImageIcon("Img/Entrada de Produtos.png"));
		lblFundo.setBounds(0, 0, getWidth(), getHeight());

		lblLote = new JLabel("Lote:");
		lblLote.setBounds(130, 105, 80, 30);
		lblLote.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		// lblNomeForm.setForeground(new Color(211, 23, 164));
		add(lblLote);

		txtLote = new JTextField();
		txtLote.setBounds(lblLote.getX()+60, lblLote.getY(), 202, 30);
		txtLote.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		txtLote.addKeyListener(this);
		// txtNomeForm.setForeground(new Color(211, 23, 164));
		add(txtLote);

		lblDataEntrada = new JLabel("* Data de Entrada:");
		lblDataEntrada.setBounds(120, txtLote.getY() + 50, 200, 30);
		lblDataEntrada.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		add(lblDataEntrada);

		mskDataEntrada = new JFormattedTextField();
		mskDataEntrada.setBounds(275, lblDataEntrada.getY(), 117, 30);
		mskDataEntrada.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		mskDataEntrada.addKeyListener(this);
		mskDataEntrada.setEnabled(false);
		add(mskDataEntrada);
		mskDataEntrada.addFocusListener(this);

		try {
			mascaraData = new MaskFormatter("##/##/####");
			mascaraData.setPlaceholderCharacter('_');
			mascaraData.install(mskDataEntrada);
		} catch (Exception ex) {
		}

		lblImgInfoData1 = new JLabel();
		lblImgInfoData1.setBounds(mskDataEntrada.getX() + 125,
				mskDataEntrada.getY() + 5, 20, 16);
		add(lblImgInfoData1);

		lblDataValidadeLote = new JLabel("* Data de Validade:");
		lblDataValidadeLote.setBounds(410, lblDataEntrada.getY(), 210, 30);
		lblDataValidadeLote.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblDataValidadeLote);

		mskDataValidadeLote = new JFormattedTextField();
		mskDataValidadeLote.setBounds(570, lblDataValidadeLote.getY(), 120, 30);
		mskDataValidadeLote.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		mskDataValidadeLote.addKeyListener(this);
		add(mskDataValidadeLote);
		mskDataValidadeLote.addFocusListener(this);

		try {
			mascara2 = new MaskFormatter("##/##/####");
			mascara2.setPlaceholderCharacter('_');
			mascara2.install(mskDataValidadeLote);
		} catch (Exception e) {

		}
		lblImgInfoData2 = new JLabel();
		lblImgInfoData2.setBounds(mskDataValidadeLote.getX() + 125,
				mskDataValidadeLote.getY() + 5, 20, 20);
		add(lblImgInfoData2);

		lblMotivoEntrada = new JLabel("* Motivo:");
		lblMotivoEntrada.setBounds(lblDataEntrada.getX(), mskDataEntrada.getY() + 50,
				70, 30);
		lblMotivoEntrada.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		add(lblMotivoEntrada);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		jcComboMotivo = new JComboBox<Object>();
		jcComboMotivo
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		jcComboMotivo.setBounds(
				lblMotivoEntrada.getX() + lblMotivoEntrada.getWidth() + 5,
				lblMotivoEntrada.getY(), 150, 30);
		add(jcComboMotivo);
		jcComboMotivo.addItem(" ");

		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		btnAddMotivo = new JButton("+");
		btnAddMotivo.setBackground(corOriginalBtn);
		btnAddMotivo.setForeground(Color.WHITE);
		btnAddMotivo.setFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
		btnAddMotivo.setBounds(jcComboMotivo.getX() + jcComboMotivo.getWidth()
				+ 5, jcComboMotivo.getY(), 45, 30);
		btnAddMotivo.addActionListener(this);
		btnAddMotivo.setToolTipText("Adicionar um novo motivo de entrada");
		add(btnAddMotivo);

		lblFornecedor = new JLabel("* Fornecedor:");
		lblFornecedor.setBounds(lblDataValidadeLote.getX(),
				lblMotivoEntrada.getY(), 120, 30);
		lblFornecedor
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(lblFornecedor);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		jcComboFornecedor = new JComboBox<Object>();
		jcComboFornecedor.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 12));
		jcComboFornecedor.setBounds(lblFornecedor.getX() + 100,
				jcComboMotivo.getY(), 130, 30);
		add(jcComboFornecedor);
		jcComboFornecedor.addItem(" ");

		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		btnAddFornecedor = new JButton("+");
		btnAddFornecedor.setBackground(corOriginalBtn);
		btnAddFornecedor.setForeground(Color.WHITE);
		btnAddFornecedor.setFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
		btnAddFornecedor.setBounds(
				jcComboFornecedor.getX() + jcComboFornecedor.getWidth() + 5,
				jcComboFornecedor.getY(), 45, 30);
		btnAddFornecedor.addActionListener(this);
		btnAddFornecedor.setToolTipText("Adicionar um novo fornecedor");
		add(btnAddFornecedor);

		lblProduto = new JLabel("* Produto:");
		lblProduto.setBounds(lblDataEntrada.getX(), 55, 79,
				30);
		lblProduto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(lblProduto);

		txtProduto = new JTextField();
		txtProduto.setBounds(lblProduto.getX() + lblProduto.getWidth() - 5,
				lblProduto.getY(), 150, 30);
		txtProduto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(txtProduto);
		txtProduto.addKeyListener(this);

		btnAddProduto = new JButton("+");
		btnAddProduto.setBackground(corOriginalBtn);
		btnAddProduto.setForeground(Color.WHITE);
		btnAddProduto.setFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
		btnAddProduto.setBounds(txtProduto.getX() + txtProduto.getWidth() + 5,
				lblProduto.getY(), 45, 30);
		btnAddProduto.addActionListener(this);
		btnAddProduto.setToolTipText("Adicionar um novo produto");
		add(btnAddProduto);

		lblQuantidadeEntrada = new JLabel("* Quantidade Entrada:");
		lblQuantidadeEntrada.setBounds(410,
				lblLote.getY(), 230, 30);
		lblQuantidadeEntrada.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblQuantidadeEntrada);

		txtQuantidadeEntrada = new JIntNumberFormatField();
		txtQuantidadeEntrada.setLimit(10);
		txtQuantidadeEntrada.setBounds(lblQuantidadeEntrada.getX() + 170,
				lblQuantidadeEntrada.getY(), 110, 30);
		txtQuantidadeEntrada.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(txtQuantidadeEntrada);

		lblTxtFoto= new JLabel("Foto:");
		lblTxtFoto.setBounds(lblDataEntrada.getX(), lblMotivoEntrada.getY() + 40, 230,
				30);
		lblTxtFoto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		add(lblTxtFoto);
	
		lblFoto= new JLabel("");
		lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
		lblFoto.setBounds(lblProduto.getX(), lblTxtFoto.getY() + 23, 140,
				100);
		lblFoto.setBorder(BorderFactory.createLineBorder(new Color(161, 0, 64)));
		lblFoto.addMouseListener(this);
		add(lblFoto);

		lblNomeProduto = new JLabel("Nome:");
		lblNomeProduto.setBounds(lblFoto.getX()+150, lblMotivoEntrada.getY() + 60, 230,
				30);
		lblNomeProduto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		add(lblNomeProduto);

		lblTxtNomeProduto = new JLabel();
		lblTxtNomeProduto.setBounds(lblNomeProduto.getX()+60, lblNomeProduto.getY(), 150,
				30);
		lblTxtNomeProduto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		add(lblTxtNomeProduto);
		
		lblDescricaoProduto = new JLabel("Descrição:");
		lblDescricaoProduto.setBounds(lblNomeProduto.getX(),
				lblNomeProduto.getY() + 30, 230, 30);
		lblDescricaoProduto.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblDescricaoProduto);
		
		lblTxtDescricaoProduto = new JLabel();
		lblTxtDescricaoProduto.setBounds(lblDescricaoProduto.getX()+80,
				lblNomeProduto.getY() + 30, 130, 30);
		lblTxtDescricaoProduto.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblTxtDescricaoProduto);
		
		lblFabricanteProduto= new JLabel("Fabricante:");
		lblFabricanteProduto.setBounds(lblNomeProduto.getX(),
				lblDescricaoProduto.getY() + 30, 230, 30);
		lblFabricanteProduto.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblFabricanteProduto);

		lblTxtFabricanteProduto= new JLabel();
		lblTxtFabricanteProduto.setBounds(lblFabricanteProduto.getX()+83,
				lblDescricaoProduto.getY() + 30, 140, 30);
		lblTxtFabricanteProduto.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblTxtFabricanteProduto);

		lblQuantidadeMinimaProduto = new JLabel("Quantidade Minima:");
		lblQuantidadeMinimaProduto.setBounds(490,
				lblNomeProduto.getY(), 230, 30);
		lblQuantidadeMinimaProduto.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblQuantidadeMinimaProduto);

		lblTxtQuantidadeMinimaProduto = new JLabel();
		lblTxtQuantidadeMinimaProduto.setBounds(640,
				lblQuantidadeMinimaProduto.getY(), 50, 30);
		lblTxtQuantidadeMinimaProduto.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblTxtQuantidadeMinimaProduto);
		
		lblValorProduto = new JLabel("Valor:");
		lblValorProduto.setBounds(lblQuantidadeMinimaProduto.getX(),
				lblDescricaoProduto.getY(), 230, 30);
		lblValorProduto.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblValorProduto);

		lblTxtValorProduto = new JLabel();
		lblTxtValorProduto.setBounds(540,
				lblValorProduto.getY(), 150, 30);
		lblTxtValorProduto.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblTxtValorProduto);

		lblInfoCamposObrigatorios = new JLabel("* Campos Obrigatórios");
		lblInfoCamposObrigatorios.setBounds(160, 375, 160, 30);
		lblInfoCamposObrigatorios.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblInfoCamposObrigatorios);

		lblNovaEntradaProduto = new JLabel(new ImageIcon(
				"Img/Entrada de Produto/Nova.png"));
		// lblNovaEntradaProduto.setOpaque(true);
		// lblNovaEntradaProduto.setBackground(Color.GREEN);
		lblNovaEntradaProduto.setBounds(738, 52, 53, 57);
		lblNovaEntradaProduto.addMouseListener(this);
		lblNovaEntradaProduto.setToolTipText("Nova");
		add(lblNovaEntradaProduto);

		lblSalvarEntradaProduto = new JLabel(new ImageIcon(
				"Img/Entrada de Produto/Salvar.png"));
		// lblSalvarEntradaProduto.setOpaque(true);
		// lblSalvarEntradaProduto.setBackground(Color.GREEN);
		lblSalvarEntradaProduto.setBounds(lblNovaEntradaProduto.getX(),
				lblNovaEntradaProduto.getY() + 70, 53, 57);
		lblSalvarEntradaProduto.addMouseListener(this);
		lblSalvarEntradaProduto.setToolTipText("Salvar");
		lblSalvarEntradaProduto.setEnabled(false);
		add(lblSalvarEntradaProduto);

		lblEditarEntradaProduto = new JLabel(new ImageIcon(
				"Img/Entrada de Produto/Editar.png"));
		// lblEditarEntradaProduto.setOpaque(true);
		// lblEditarEntradaProduto.setBackground(Color.GREEN);
		lblEditarEntradaProduto.setBounds(lblSalvarEntradaProduto.getX(),
				lblSalvarEntradaProduto.getY() + 70, 53, 57);
		lblEditarEntradaProduto.addMouseListener(this);
		lblEditarEntradaProduto.setToolTipText("Editar");
		lblEditarEntradaProduto.setEnabled(false);
		add(lblEditarEntradaProduto);

		lblCancelar = new JLabel(new ImageIcon("Img/Cancelar.png"));
		// lblCancelar.setOpaque(true);
		// lblCancelar.setBackground(Color.GREEN);
		lblCancelar.setBounds(lblEditarEntradaProduto.getX() - 5,
				lblEditarEntradaProduto.getY() + 70, 61, 52);
		lblCancelar.addMouseListener(this);
		lblCancelar.setToolTipText("Cancelar");
		add(lblCancelar);

		lblRelatorioEntradaProduto = new JLabel(new ImageIcon(
				"Img/Relatorio.png"));
		// lblRelatorioEntradaProduto.setOpaque(true);
		// lblRelatorioEntradaProduto.setBackground(Color.GREEN);
		lblRelatorioEntradaProduto.setBounds(lblCancelar.getX() + 8,
				lblCancelar.getY() + 70, 44, 50);
		lblRelatorioEntradaProduto.addMouseListener(this);
		lblRelatorioEntradaProduto.setToolTipText("Relatório");
		add(lblRelatorioEntradaProduto);

		lblMinimize = new JLabel();
		// lblMinimize.setOpaque(true);
		// lblMinimize.setBackground(Color.GREEN);
		lblMinimize.setBounds(825, 10, 32, 20);
		lblMinimize.addMouseListener(this);
		add(lblMinimize);

		lblClose = new JLabel();
		// lblClose.setOpaque(true);
		// lblClose.setBackground(Color.GREEN);
		lblClose.setBounds(865, 6, 33, 28);
		lblClose.addMouseListener(this);
		add(lblClose);

		lblNavbar = new JLabel();
		// lblNavbar.setOpaque(true);
		// lblNavbar.setBackground(Color.GREEN);
		lblNavbar.setBounds(0, 0, getWidth(), 37);
		lblNavbar.addMouseListener(this);
		lblNavbar.addMouseMotionListener(this);
		add(lblNavbar);

		btnPrimLinha = new JButton("<<");
		btnPrimLinha.setBackground(corOriginalBtn);
		btnPrimLinha.setForeground(Color.WHITE);
		btnPrimLinha.setFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
		btnPrimLinha.setBounds(340, 370, 52, 40);
		btnPrimLinha.setToolTipText("Primeira linha");
		add(btnPrimLinha);
		btnPrimLinha.addActionListener(this);

		btnVoltar = new JButton("<");
		btnVoltar.setBackground(corOriginalBtn);
		btnVoltar.setForeground(Color.WHITE);
		btnVoltar.setFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
		btnVoltar.setBounds(btnPrimLinha.getX() + 55, btnPrimLinha.getY(),
				btnPrimLinha.getWidth(), btnPrimLinha.getHeight());
		btnVoltar.setToolTipText("Voltar uma linha");
		add(btnVoltar);
		btnVoltar.addActionListener(this);

		btnAvancar = new JButton(">");
		btnAvancar.setBackground(corOriginalBtn);
		btnAvancar.setForeground(Color.WHITE);
		btnAvancar.setFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
		btnAvancar.setBounds(btnVoltar.getX() + 55, btnVoltar.getY(),
				btnVoltar.getWidth(), btnVoltar.getHeight());
		btnAvancar.setToolTipText("Avançar uma linha");
		add(btnAvancar);
		btnAvancar.addActionListener(this);

		btnUltLinha = new JButton(">>");
		btnUltLinha.setBackground(corOriginalBtn);
		btnUltLinha.setForeground(Color.WHITE);
		btnUltLinha.setFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
		btnUltLinha.setBounds(btnAvancar.getX() + 55, btnAvancar.getY(),
				btnAvancar.getWidth(), btnAvancar.getHeight());
		btnUltLinha.setToolTipText("Ultima linha");
		add(btnUltLinha);
		btnUltLinha.addActionListener(this);

		camposFalse();

		lblEditarEntradaProduto.setEnabled(false);
		// btnExcluir.setEnabled(false);
		lblNovaEntradaProduto.setEnabled(true);

		lblSalvarEntradaProduto.setEnabled(false);
		preencher();
		add(lblFundo);
		verificaRelatorio();

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
		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabela.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		tabela.setSelectionBackground(new Color(222, 54, 121));
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.getTableHeader().setDefaultRenderer(new RTable());
		scrollTabela = new JScrollPane(tabela);

		// scrollTabela.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		scrollTabela.setBounds(60, 415, 780, 180);
		tabela.getTableHeader().setResizingAllowed(false);

		add(scrollTabela);

		modelo.addColumn("Lote");
		modelo.addColumn("Quantidade");
		modelo.addColumn("Data Validade");
		modelo.addColumn("Data Entrada");
		modelo.addColumn("Motivo");
		modelo.addColumn("Fornecedor");
		modelo.addColumn("Produto");

		preencherJtable(modelo);

		tabela.getColumnModel().getColumn(0).setPreferredWidth(100);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(85);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(100);
		tabela.getColumnModel().getColumn(3).setPreferredWidth(90);
		tabela.getColumnModel().getColumn(4).setPreferredWidth(120);
		tabela.getColumnModel().getColumn(5).setPreferredWidth(140);
		tabela.getColumnModel().getColumn(6).setPreferredWidth(143);
		tabela.getTableHeader().setFont(
				new Font("Century Gothic", Font.TRUETYPE_FONT, 12));

		((DefaultTableCellRenderer) tabela.getTableHeader()
				.getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);
		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		for (int i = 0; i < tabela.getColumnCount(); i++) {
			DefaultTableCellRenderer cellRender = new DefaultTableCellRenderer();
			cellRender.setHorizontalAlignment(SwingConstants.CENTER);
			tabela.getColumnModel().getColumn(i).setCellRenderer(cellRender);
		}

		tabela.getTableHeader().setReorderingAllowed(false);
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
	
	public JComboBox<Object> getJcComboMotivo() {
		return jcComboMotivo;
	}

	public void setJcComboMotivo(JComboBox<Object> jcComboMotivo) {
		this.jcComboMotivo = jcComboMotivo;
	}

	public JComboBox<Object> getJcComboFornecedor() {
		return jcComboFornecedor;
	}

	public void setJcComboFornecedor(JComboBox<Object> jcComboFornecedor) {
		this.jcComboFornecedor = jcComboFornecedor;
	}

	public void preencherJtable(DefaultTableModel modelo) {

		modelo.setNumRows(0);

		for (EntradaProduto e : epController.listar()) {
			modelo.addRow(new Object[] { e.getLoteProduto(),
					e.getQuantidadeEntradaProduto(), e.getDataValidadeLote(),
					e.getDataEntradaProduto(), e.getMotivo(),
					e.getNomeFornecedor(), e.getNomeProduto() });

		}

	}

	public void pesquisaJtable(DefaultTableModel modelo) {
		modelo.setNumRows(0);
		if (pController.achou(txtLote.getText()) != null) {
			for (EntradaProduto e : epController.achou(txtProduto.getText())) {

				modelo.addRow(new Object[] { e.getLoteProduto(),
						e.getQuantidadeEntradaProduto(),
						e.getDataValidadeLote(), e.getDataEntradaProduto(),
						e.getMotivo(), e.getNomeFornecedor(),
						e.getNomeProduto() });

			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnAddFornecedor)) {
			new JanelaFornecedor(null, this);
			this.setVisible(false);
		}
		if (e.getSource().equals(btnAddMotivo)) {
			new JanelaMotivoEntradaProduto(this);

		}
		if (e.getSource().equals(btnAddProduto)) {
			this.setVisible(false);
			new JanelaProduto(null, this, null);
		}

		if (e.getSource() == btnPrimLinha) {
			lblNovaEntradaProduto.setEnabled(false);
			tabela.setRowSelectionInterval(0, 0);
			lblEditarEntradaProduto.setEnabled(true);
			// btnExcluir.setEnabled(true);
			dadosTabela();
			tabela.grabFocus();
		}
		if (e.getSource() == btnUltLinha) {
			lblNovaEntradaProduto.setEnabled(false);
			tabela.setRowSelectionInterval(tabela.getModel().getRowCount() - 1,
					tabela.getModel().getRowCount() - 1);
			lblEditarEntradaProduto.setEnabled(true);
			// btnExcluir.setEnabled(true);

			dadosTabela();
			tabela.grabFocus();
		}
		if (e.getSource() == btnAvancar) {
			if (tabela.getSelectedRow() < tabela.getModel().getRowCount() - 1) {
				lblNovaEntradaProduto.setEnabled(false);
				tabela.setRowSelectionInterval(tabela.getSelectedRow() + 1,
						tabela.getSelectedRow() + 1);
				lblEditarEntradaProduto.setEnabled(true);
				// btnExcluir.setEnabled(true);
				dadosTabela();
				tabela.grabFocus();
			}
		}
		if (e.getSource() == btnVoltar) {
			lblNovaEntradaProduto.setEnabled(false);
			if (tabela.getSelectedRow() > 0) {
				tabela.setRowSelectionInterval(tabela.getSelectedRow() - 1,
						tabela.getSelectedRow() - 1);
				lblEditarEntradaProduto.setEnabled(true);
				// btnExcluir.setEnabled(true);
				dadosTabela();
				tabela.grabFocus();
			} else {
				tabela.clearSelection();
				lblEditarEntradaProduto.setEnabled(false);
				// btnExcluir.setEnabled(false);
				limpaCampos();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==lblFoto && lblFoto.isEnabled() && !localFoto.equals("")){
			new JanelaVisualizarImagem(this);
		}
		
		if (e.getSource().equals(lblMinimize)) {
			this.setExtendedState(JFrame.ICONIFIED);
		}

		else if (e.getSource().equals(lblClose)) {
			this.dispose();
		}

		if (e.getSource().equals(lblCancelar) && lblCancelar.isEnabled()) {

			limpaCampos();

			camposFalse();

			lblEditarEntradaProduto.setEnabled(false);
			// btnExcluir.setEnabled(false);
			lblNovaEntradaProduto.setEnabled(true);

			lblSalvarEntradaProduto.setEnabled(false);
			txtBtn = "";

			pesquisa();
			tabela.setEnabled(true);
			preencherJtable(modelo);

			txtProduto.grabFocus();
		}

		if (e.getSource().equals(lblNovaEntradaProduto)
				&& lblNovaEntradaProduto.isEnabled()) {
			
			camposTrue();
			tabela.clearSelection();
			lblSalvarEntradaProduto.setEnabled(true);

			// btnExcluir.setEnabled(false);
			lblEditarEntradaProduto.setEnabled(false);
			lblNovaEntradaProduto.setEnabled(false);
			txtBtn = "Novo";
			tabela.setEnabled(false);
			if (txtProduto.getText().length() < 1)
				txtProduto.grabFocus();
		}

		if (e.getSource().equals(lblSalvarEntradaProduto)
				&& lblSalvarEntradaProduto.isEnabled()) {

			if (txtBtn.equals("Novo")) {
				if (cadastrar()) {
					txtBtn = "";
					limpaCampos();
					camposFalse();
					lblEditarEntradaProduto.setEnabled(false);
					// btnExcluir.setEnabled(false);
					lblNovaEntradaProduto.setEnabled(true);
					lblSalvarEntradaProduto.setEnabled(false);
					tabela.getSelectionModel().clearSelection();
					tabela.setEnabled(true);
					// preencherJtable(modelo);
					txtLote.grabFocus();
					tabela.setEnabled(true);
				} else {

				}
			} else {
				if (editar()) {
					txtBtn = "";
					limpaCampos();
					camposFalse();
					lblEditarEntradaProduto.setEnabled(false);
					// btnExcluir.setEnabled(false);
					lblNovaEntradaProduto.setEnabled(true);

					lblSalvarEntradaProduto.setEnabled(false);
					tabela.getSelectionModel().clearSelection();
					tabela.setEnabled(true);
					// preencherJtable(modelo);
					txtLote.grabFocus();

					tabela.setEnabled(true);

				}

			}
		}
		if (e.getSource().equals(lblEditarEntradaProduto)
				&& lblEditarEntradaProduto.isEnabled()) {
			definirValor();
			if (tabela.getSelectedRow() != -1) {
				camposTrue();

				lblSalvarEntradaProduto.setEnabled(true);
				// setando enable = false nos botoes novo, editar e excluir
				lblNovaEntradaProduto.setEnabled(false);
				lblEditarEntradaProduto.setEnabled(false);
				// btnExcluir.setEnabled(false);

				pegarCodigo();
				dadosTabela();

				produto = pController.codigoBarras(produto);
				if (produto.getId() != 0 && produto.getAtividadeProduto() == 1) {
					setCamposDadosProduto();
				}
				txtBtn = "Editar";

				tabela.setEnabled(false);
				tabela.getSelectionModel().clearSelection();

				txtLote.grabFocus();

			} else {
				JOptionPane.showMessageDialog(null, "Selecione uma Entrada");
			}
		}
		if (e.getSource().equals(lblRelatorioEntradaProduto)
				&& lblRelatorioEntradaProduto.isEnabled()) {
			if (tabela.getSelectedRow() != -1) {
				GerarRelatorioEntradaProduto gru = new GerarRelatorioEntradaProduto();
				ArrayList<RelatorioEntradaProduto> relatoriosEntradaProduto = new ArrayList<RelatorioEntradaProduto>();
				RelatorioEntradaProduto rep = new RelatorioEntradaProduto();
				rep.setQuantidade(tabela.getValueAt(tabela.getSelectedRow(), 1)
						.toString());
				rep.setDataEntrada(tabela
						.getValueAt(tabela.getSelectedRow(), 2).toString());
				rep.setMotivo(tabela.getValueAt(tabela.getSelectedRow(), 4)
						.toString());
				rep.setLote(tabela.getValueAt(tabela.getSelectedRow(), 0)
						.toString());
				rep.setValidade(tabela.getValueAt(tabela.getSelectedRow(), 2)
						.toString());
				rep.setFornecedor(tabela.getValueAt(tabela.getSelectedRow(), 5)
						.toString());
				rep.setProduto(tabela.getValueAt(tabela.getSelectedRow(), 6)
						.toString());
				rep.setData("");
				rep.setDataCompleta("");
				relatoriosEntradaProduto.add(rep);
				gru.gerarClienteEspecifico(relatoriosEntradaProduto);
			} else if (tabela.getRowCount() > 0) {
				GerarRelatorioEntradaProduto gru = new GerarRelatorioEntradaProduto();
				ArrayList<RelatorioEntradaProduto> relatoriosEntradaProduto = new ArrayList<RelatorioEntradaProduto>();
				for (int i = 0; i < tabela.getRowCount(); i++) {
					RelatorioEntradaProduto rep = new RelatorioEntradaProduto();
					rep.setQuantidade(tabela.getValueAt(i, 1).toString());
					rep.setDataEntrada(tabela.getValueAt(i, 2).toString());
					rep.setMotivo(tabela.getValueAt(i, 4).toString());
					rep.setLote(tabela.getValueAt(i, 0).toString());
					rep.setValidade(tabela.getValueAt(i, 2).toString());
					rep.setFornecedor(tabela.getValueAt(i, 5).toString());
					rep.setProduto(tabela.getValueAt(i, 6).toString());
					rep.setData("");
					rep.setDataCompleta("");
					relatoriosEntradaProduto.add(rep);
				}
				gru.gerarClienteEspecifico(relatoriosEntradaProduto);
			}
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource()==lblFoto && lblFoto.getIcon()!=null){
			lblFoto.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		
		if (e.getSource().equals(lblNovaEntradaProduto) && lblNovaEntradaProduto.isEnabled()) {
			lblNovaEntradaProduto.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblSalvarEntradaProduto)
				&& lblSalvarEntradaProduto.isEnabled()) {
			lblSalvarEntradaProduto.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblEditarEntradaProduto)
				&& lblEditarEntradaProduto.isEnabled()) {
			lblEditarEntradaProduto.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		
		if (e.getSource().equals(lblCancelar) && lblCancelar.isEnabled()) {
			lblCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblRelatorioEntradaProduto)
				&& lblRelatorioEntradaProduto.isEnabled()) {
			lblRelatorioEntradaProduto.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource()==lblFoto && lblFoto.getIcon()!=null){
			lblFoto.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		
		if (e.getSource().equals(lblNovaEntradaProduto) && lblNovaEntradaProduto.isEnabled()) {
			lblNovaEntradaProduto.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource().equals(lblSalvarEntradaProduto)
				&& lblSalvarEntradaProduto.isEnabled()) {
			lblSalvarEntradaProduto.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource().equals(lblEditarEntradaProduto)
				&& lblEditarEntradaProduto.isEnabled()) {
			lblEditarEntradaProduto.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		
		if (e.getSource().equals(lblCancelar) && lblCancelar.isEnabled()) {
			lblCancelar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource().equals(lblRelatorioEntradaProduto)
				&& lblRelatorioEntradaProduto.isEnabled()) {
			lblRelatorioEntradaProduto.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource().equals(lblNavbar)) {
			posX = e.getX();
			posY = e.getY();
		}
		if (tabela.getSelectedRow() != -1) {
			dadosTabela();
			lblNovaEntradaProduto.setEnabled(false);
			lblEditarEntradaProduto.setEnabled(true);
			// btnExcluir.setEnabled(true);
		} else {
			lblEditarEntradaProduto.setEnabled(false);
			// btnExcluir.setEnabled(false);
		}

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

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

		if (txtProduto.hasFocus() && txtBtn.equals("")) {
			pesquisa();
		}
		if (mskDataEntrada.hasFocus()
				&& mskDataEntrada.getText().replace("_", "").length() == 10) {
			if (validaDataEntrada(mskDataEntrada.getText())) {
				lblImgInfoData1.setIcon(new ImageIcon("Img/success.png"));
			} else {
				lblImgInfoData1.setIcon(new ImageIcon("Img/error.png"));
			}
		} else {
			lblImgInfoData1.setIcon(null);
		}

		if (mskDataValidadeLote.hasFocus()
				&& mskDataValidadeLote.getText().replace("_", "").length() == 10) {
			if (validaDataValidade(mskDataValidadeLote.getText())) {
				lblImgInfoData2.setIcon(new ImageIcon("Img/success.png"));
			} else {
				lblImgInfoData2.setIcon(new ImageIcon("Img/error.png"));
			}

		} else {
			lblImgInfoData2.setIcon(null);
		}

		if ( txtProduto.getText().trim().length() > 0) {
			pesquisa();
			produto.setCodBarras(txtProduto.getText().trim());
			produto = pController.codigoBarras(produto);
			if (produto.getId() != 0 && produto.getAtividadeProduto() == 1) {
				System.out.println(produto.getId());
				setCamposDadosProduto();
			} else {
				limpaCamposProduto();
			}

		} else {
			limpaCamposProduto();
		}

	}

	private void setCamposDadosProduto(){
		lblTxtNomeProduto.setText(produto.getNomeProduto());
		lblTxtDescricaoProduto.setText(produto.getDescricaoProduto());
		lblTxtFabricanteProduto.setText(produto.getNomeFabricante());
		lblTxtQuantidadeMinimaProduto.setText(produto.getQuantidade()+"");
		lblTxtValorProduto.setText(produto.getValor()+"");
		localFoto = produto.getFotoProduto();
		if (localFoto.equals("-----------")){
			
		}else{
			try{
			lblFoto.setIcon(new ImageIcon(setImagemDimensao(localFoto, lblFoto.getWidth(), lblFoto.getHeight())));
			}catch(Exception e){
				
			}
		}
		
		
	}
	

	private void limpaCamposProduto(){
		lblTxtNomeProduto.setText("");
		lblTxtDescricaoProduto.setText("");
		lblTxtFabricanteProduto.setText("");
		lblTxtQuantidadeMinimaProduto.setText("");
		lblTxtValorProduto.setText("");
		localFoto = "";
		lblFoto.setIcon(null);
	}

	
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	private void limpaCampos() {
		txtQuantidadeEntrada.setText("");
		txtLote.setText("");
		mskDataValidadeLote.setText("");
		mskDataEntrada.setText("");
		jcComboMotivo.setSelectedIndex(0);
		jcComboFornecedor.setSelectedIndex(0);
		txtProduto.setText("");
		lblImgInfoData1.setIcon(null);
		lblImgInfoData2.setIcon(null);
		limpaCamposProduto();
		produto.setCodBarras("");

	}

	private void camposTrue() {
		txtQuantidadeEntrada.setEnabled(true);
		txtLote.setEnabled(true);
		mskDataValidadeLote.setEnabled(true);
		mskDataEntrada.setEnabled(true);
		jcComboMotivo.setEnabled(true);
		jcComboFornecedor.setEnabled(true);
		txtProduto.setEnabled(true);
		btnAddFornecedor.setEnabled(true);
		btnAddMotivo.setEnabled(true);
		btnAddProduto.setEnabled(true);
		btnPrimLinha.setEnabled(false);
		btnVoltar.setEnabled(false);
		btnAvancar.setEnabled(false);
		btnUltLinha.setEnabled(false);
		lblRelatorioEntradaProduto.setEnabled(false);
	}

	private void camposFalse() {
		txtQuantidadeEntrada.setEnabled(false);
		txtLote.setEnabled(false);
		mskDataValidadeLote.setEnabled(false);
		mskDataEntrada.setEnabled(false);
		jcComboMotivo.setEnabled(false);
		jcComboFornecedor.setEnabled(false);
		btnPrimLinha.setEnabled(true);
		btnVoltar.setEnabled(true);
		btnAvancar.setEnabled(true);
		btnUltLinha.setEnabled(true);
		verificaRelatorio();
	}

	public void dadosTabela() {
		lblFoto.setIcon(null);
		if (tabela.getSelectedRow() != -1) {

			String motivo = (String) tabela.getValueAt(tabela.getSelectedRow(),
					4);
			for (int i = 0; i < jcComboMotivo.getItemCount(); i++) {
				String l = jcComboMotivo.getItemAt(i).toString();
				if (motivo.equals(l)) {
					MotivoEntrada m = (MotivoEntrada) jcComboMotivo
							.getItemAt(i);
					jcComboMotivo.setSelectedItem(m);
				}
			}
			txtQuantidadeEntrada.setText(tabela.getValueAt(
					tabela.getSelectedRow(), 1).toString());
			mskDataValidadeLote.setText(tabela.getValueAt(
					tabela.getSelectedRow(), 2).toString());
			mskDataEntrada.setText(tabela
					.getValueAt(tabela.getSelectedRow(), 3).toString());
			txtLote.setText(tabela.getValueAt(tabela.getSelectedRow(), 0)
					.toString());
			txtProduto.setText(tabela.getValueAt(tabela.getSelectedRow(), 6)
					.toString());
			produto.setCodBarras(tabela.getValueAt(tabela.getSelectedRow(), 6)
					.toString());
			String fornecedor = (String) tabela.getValueAt(
					tabela.getSelectedRow(), 5);
			for (int i = 0; i < jcComboFornecedor.getItemCount(); i++) {
				String l = jcComboFornecedor.getItemAt(i).toString();
				if (fornecedor.equals(l)) {
					Fornecedor m = (Fornecedor) jcComboFornecedor.getItemAt(i);
					jcComboFornecedor.setSelectedItem(m);
				}
			}
			produto = pController.codigoBarras(produto);
			setCamposDadosProduto();

		}

	}

	public boolean validaDataEntrada(String dataDigitada) {
		Date dataAtual = new Date();
		DateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
		Date data;
		formataData.setLenient(false);
		try {
			data = formataData.parse(dataDigitada);
			if (data.after(dataAtual)) {
				// lblImgInfoData1.setIcon(new ImageIcon("Img/error.png"));

				// System.out.println("Data inválida.");
				return false;
			} else {

				// System.out.println("Data válida.");
				return true;
			}

		} catch (ParseException e) {
			System.out.println("Data inválida.");
			return false;
		}

	}

	public boolean validaDataValidade(String dataDigitada) {
		Date dataAtual = new Date();
		DateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
		Date data, dataEntrada;
		formataData.setLenient(false);
		try {
			dataEntrada = formataData.parse(mskDataEntrada.getText());
			data = formataData.parse(dataDigitada);
			if (data.before(dataEntrada)) {
				System.out.println("Data inválida.");
				return false;
			} else {
				return true;
			}

		} catch (ParseException e) {
			System.out.println("Data inválida.");
			return false;
		}

	}

	public boolean cadastrar() {
		try {
			if (!validaDataEntrada(mskDataEntrada.getText())
					|| !validaDataValidade(mskDataValidadeLote.getText())
					|| txtProduto.getText().trim().length() <= 0
					|| jcComboFornecedor.getSelectedIndex() <= 0
					|| jcComboMotivo.getSelectedIndex() <= 0
					|| mskDataValidadeLote.getText().replaceAll("[_ /]", "")
							.length() != 8
					|| mskDataEntrada.getText().replaceAll("[_ /]", "")
							.length() != 8
					|| txtQuantidadeEntrada.getText().length() < 1
					) {
				JOptionPane.showMessageDialog(null,
						"Preencha os campos corretamente");

				lblNovaEntradaProduto.setEnabled(true);
				return false;
			} else {
				if (produto.getId() != 0) {
					Fornecedor f = (Fornecedor) jcComboFornecedor
							.getSelectedItem();

					MotivoEntrada m = (MotivoEntrada) jcComboMotivo
							.getSelectedItem();

					EntradaProduto ep = new EntradaProduto();
					Estoque e = new Estoque();

					ep.setQuantidadeEntradaProduto(Integer
							.parseInt(txtQuantidadeEntrada.getText().replace(".", "")));
					ep.setCodMotivoEntradaProduto(m.getCodMotivoEntrada());
					ep.setDataEntradaProduto(mskDataEntrada.getText());
					ep.setDataValidadeLote(mskDataValidadeLote.getText());
					ep.setLoteProduto(txtLote.getText());
					ep.setMotivo(m.getDescricao());
					ep.setCodFornecedor(f.getCodFornecedor());
					ep.setCodProduto(produto.getId());

					e.setCodProduto(ep.getCodProduto());
					if (eController.conferirEstoque(e)) {
						e.setQuantidadeEstoqueProduto(eController
								.pegarEstoque(e)
								+ ep.getQuantidadeEntradaProduto());
					} else {

						e.setQuantidadeEstoqueProduto(ep
								.getQuantidadeEntradaProduto());

					}
					if (epController.cadastrar(ep)) {
						preencherJtable(modelo);
						JOptionPane.showMessageDialog(null,
								"Entrada Cadastrada com sucesso \n");
						eController.acaoEstoque(e);
						produto.setId(0);
						limpaCamposProduto();
						return true;
					} else {
						JOptionPane.showMessageDialog(null, "deu ruim ");
						lblNovaEntradaProduto.setEnabled(true);
						return false;
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Insira um codigo de barras cadastrado");
					return false;
				}
			}
		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(null,
							"Preencha os campos corretamente, textos não são aceitos em campos numéricos");
			lblNovaEntradaProduto.setEnabled(true);
			return false;
		}
	}

	public boolean editar() {
		try {
			if (!validaDataEntrada(mskDataEntrada.getText())
					|| !validaDataValidade(mskDataValidadeLote.getText())

					|| txtProduto.getText().trim().length() <= 0
					|| jcComboFornecedor.getSelectedIndex() <= 0
					|| jcComboMotivo.getSelectedIndex() <= 0
					|| mskDataValidadeLote.getText().replaceAll("[_ /]", "")
							.length() != 8
					|| mskDataEntrada.getText().replaceAll("[_ /]", "")
							.length() != 8
					|| txtQuantidadeEntrada.getText().length() < 1
				) {
				JOptionPane.showMessageDialog(null,
						"Preencha os campos corretamente");

				lblNovaEntradaProduto.setEnabled(true);
				return false;
			} else {
				if (produto.getId() != 0 && produto.getAtividadeProduto() == 1) {
					Fornecedor f = (Fornecedor) jcComboFornecedor
							.getSelectedItem();

					MotivoEntrada m = (MotivoEntrada) jcComboMotivo
							.getSelectedItem();

					EntradaProduto ep = new EntradaProduto();
					Estoque e = new Estoque();
					ep.setQuantidadeEntradaProduto(Integer
							.parseInt(txtQuantidadeEntrada.getText().replace(".", "")));
					ep.setCodMotivoEntradaProduto(m.getCodMotivoEntrada());
					ep.setDataEntradaProduto(mskDataEntrada.getText());
					ep.setDataValidadeLote(mskDataValidadeLote.getText());
					ep.setLoteProduto(txtLote.getText());
					ep.setMotivo(m.getDescricao());
					ep.setCodFornecedor(f.getCodFornecedor());
					ep.setCodProduto(produto.getId());
					e.setCodProduto(ep.getCodProduto());
					e.setQuantidadeEstoqueProduto(ep
							.getQuantidadeEntradaProduto());
					// if (eController.conferirEstoque(e)){
					// e.setQuantidadeEstoqueProduto(eController.pegarEstoque(e)+ep.getQuantidadeEntradaProduto());
					// }else{
					//
					// e.setQuantidadeEstoqueProduto(ep.getQuantidadeEntradaProduto());
					//
					// }

					EntradaProduto aux = epController.find(codigo);

					if (epController.atualizar(ep, codigo)) {
						preencherJtable(modelo);
						JOptionPane.showMessageDialog(null,
								"Entrada Editada com sucesso \n");

						if (e.getCodProduto() == aux.getCodProduto()) {

							if (quantidade > ep.getQuantidadeEntradaProduto()) {
								e.setQuantidadeEstoqueProduto(eController
										.pegarEstoque(e));
								int resto = quantidade
										- ep.getQuantidadeEntradaProduto();
								e.setQuantidadeEstoqueProduto(e
										.getQuantidadeEstoqueProduto() - resto);
								// esse resto e o que preciso subtrair do
								// estoque

								// eController.acaoEstoque(e);

							} else if (quantidade < ep
									.getQuantidadeEntradaProduto()) {
								// você esta certo grazadeus
								e.setQuantidadeEstoqueProduto(eController
										.pegarEstoque(e)
										+ ep.getQuantidadeEntradaProduto()
										- quantidade);

							} else {
								e.setQuantidadeEstoqueProduto(eController
										.pegarEstoque(e));
							}
							eController.acaoEstoque(e);
						} else {

							// if (eController.conferirEstoque(e)){

							Estoque auxEstoque = new Estoque();
							auxEstoque.setCodProduto(aux.getCodProduto());

							auxEstoque.setQuantidadeEstoqueProduto(eController
									.pegarEstoque(auxEstoque) - quantidade);
							System.out.println(auxEstoque
									.getQuantidadeEstoqueProduto());
							eController.acaoEstoque(auxEstoque);
							// }else{

							eController.acaoEstoque(e);

							// }

						}

						return true;
					} else {
						JOptionPane.showMessageDialog(null, "deu ruim ");
						lblNovaEntradaProduto.setEnabled(true);
						return false;
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Insira um codigo de barras cadastrado");
					return false;
				}
			}

		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(null,
							"Preencha os campos corretamente, textos não são aceitos em campos numéricos");
			System.out.println(e.getMessage());
			lblNovaEntradaProduto.setEnabled(true);
			return false;
		}
	}

	public void pesquisa() {
		if (txtProduto.getText().trim().length() > 0) {

			pesquisaJtable(modelo);
			entrada = epController.achou(txtProduto.getText().trim());

		} else {
			preencherJtable(modelo);
			entrada = epController.listar();

		}
	}

	public void definirValor() {
		entrada = epController.listar();

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
		if (jp != null) {
			jp.setVisible(true);
			jp.pesquisaJtable(jp.getModelo());
		} else if (jpef != null)
			jpef.setVisible(true);
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

	public boolean verificaRelatorio() {
		if (tabela.getRowCount() == 0) {
			lblRelatorioEntradaProduto.setEnabled(false);
			return false;
		}
		lblRelatorioEntradaProduto.setEnabled(true);
		return true;
	}


	@Override
	public void focusGained(FocusEvent e) {
		if(e.getSource()==mskDataEntrada){
			mskDataEntrada.setCaretPosition(new Validacoes().getCaretPositionData(mskDataEntrada.getText()));
		}
		
		if(e.getSource()==mskDataValidadeLote){
			mskDataValidadeLote.setCaretPosition(new Validacoes().getCaretPositionData(mskDataValidadeLote.getText()));
		}
		
	}


	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
