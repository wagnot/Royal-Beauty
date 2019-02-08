

package br.com.royal.view;

// x é para direita/esquerda Y é cima/baixo

// height e altura e width e largura

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Graphics2D;
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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import br.com.royal.controller.EstoqueController;
import br.com.royal.controller.FabricanteController;
import br.com.royal.controller.ProdutoController;
import br.com.royal.controllerReports.GerarRelatorioProduto;
import br.com.royal.dao.Conexao;
import br.com.royal.model.Estoque;
import br.com.royal.model.Fabricante;
import br.com.royal.model.Produto;
import br.com.royal.modelReports.RelatorioProdutos;

public class JanelaProduto extends JFrame implements ActionListener,
		MouseListener, KeyListener, MouseMotionListener, WindowListener {

	private JComboBox<Object> jcComboFabricante;
	private JLabel lblFundo, lblNavbar, lblMinimize, lblClose, lblNome,
			lblDescricao, lblValor, lblQuantidade, lblImg, lblFabricante,
			lblInfoCamposObrigatorios, lblNovoProduto, lblSalvarProduto,
			lblEditarProduto, lblExcluirProduto, lblCancelar,
			lblEntradaProduto, lblRelatorioProduto, lblCodigoBarras,lblCusto,lblLucro,lblDuplicidade;
	private int posX, posY;
	private Color corOriginalBtn = new Color(164, 6, 69);
	private JTextField txtNome;
	private JTextField txtDescricao;
	private JNumberFormatField txtValor,txtCusto,txtLucro;
	private JIntNumberFormatField txtQuantidade;
	private JTextField txtCodigoBarras;
	private List<Produto> produto = new ArrayList<Produto>();

	private JButton btnImg, btnRetirarImg, btnAdicionarFabricante, btnAvancar,
			btnVoltar, btnPrimLinha, btnUltLinha;

	String nomeProduto = "", descricaoProduto = "", barrasAntiga = "";
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	PreparedStatement pst1 = null;
	ResultSet rs1 = null;
	BufferedImage imagem;
	BufferedImage imagemAux;
	String aux2 = "";
	String aux = "";
	String caminho = "";
	String imagemAuxiliar = "";
	private String txtBtn = "", localFoto = "";
	private ProdutoController pController = new ProdutoController();
	private FabricanteController fController = new FabricanteController();
	private JTable tabela;
	private JScrollPane scrollTabela;
	private FTable modelo = new FTable();
	private JanelaHome jh;
	private JanelaEntradaProduto jep;
	private JanelaComanda jc;
	
	
	public JanelaProduto(JanelaHome jh, JanelaEntradaProduto jep,
			JanelaComanda jc) {
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		this.jh = jh;
		this.jep = jep;
		this.jc = jc;

		con = Conexao.getConexao();
		setLayout(null);
		setSize(1080, 700);
		setTitle("Cadastro de produtos");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Img/Rosa.png"));
		setResizable(false);
		setUndecorated(true);
		addWindowListener(this);
		inicializarComponentes();
		setVisible(true);

	}

	public void remover(Fabricante f) {
		jcComboFabricante.removeItem(f);
	}

	public void motivo(Fabricante f) {

		fController.inserirJcombo(f);
		jcComboFabricante.addItem(f);

	}

	public void preencher() {
		jcComboFabricante.removeAllItems();
		jcComboFabricante.addItem(" ");
		for (Fabricante f : fController.listar()) {
			jcComboFabricante.addItem(f);
		}

	}

	public JanelaHome getJh() {
		return jh;
	}

	public void setJh(JanelaHome jh) {
		this.jh = jh;
	}

	public FTable getModelo() {
		return modelo;
	}

	public void setModelo(FTable modelo) {
		this.modelo = modelo;
	}

	public JComboBox<Object> getJcComboFabricante() {
		return jcComboFabricante;
	}

	public void setJcComboFabricante(JComboBox<Object> jcComboFabricante) {
		this.jcComboFabricante = jcComboFabricante;
	}

	public void inicializarComponentes() {
		criaTabela();
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		lblCodigoBarras = new JLabel("* Codigo de barras");
		lblCodigoBarras.setBounds(120, 90,
				170, 30);
		lblCodigoBarras.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		add(lblCodigoBarras);

		txtCodigoBarras = new JTextFieldLimitada(100);
		txtCodigoBarras.setBounds(lblCodigoBarras.getX(),
				lblCodigoBarras.getY() + 30, 200, 30);
		txtCodigoBarras.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				12));
		add(txtCodigoBarras);
		txtCodigoBarras.addKeyListener(this);
		
		lblDuplicidade =  new JLabel("");
		lblDuplicidade.setBounds(lblCodigoBarras.getX(), txtCodigoBarras.getY()+ txtCodigoBarras.getHeight()-5, 200, 30);
		lblDuplicidade.setFont(new Font ("Century Gothic", Font.TRUETYPE_FONT,12));
		lblDuplicidade.setForeground(Color.RED);
		add (lblDuplicidade);
		
		lblNome = new JLabel("* Nome:");
		lblNome.setBounds(lblCodigoBarras.getX()+230, lblCodigoBarras.getY(), 100, 30);
		lblNome.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(lblNome);

		txtNome = new JTextFieldLimitada(50);
		txtNome.setBounds(lblNome.getX(), lblNome.getY() + 30, 190, 30);
		txtNome.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		add(txtNome);

		lblDescricao = new JLabel("* Descricão:");
		lblDescricao.setBounds(lblCodigoBarras.getX(), txtNome.getY() + 44, 300, 30);
		lblDescricao
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(lblDescricao);

		txtDescricao = new JTextFieldLimitada(50);
		txtDescricao.setBounds(lblDescricao.getX(), lblDescricao.getY() + 30,
				420, 30);
		txtDescricao
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		add(txtDescricao);

		lblValor = new JLabel("* Valor do Produto:");
		lblValor.setBounds(txtDescricao.getX(), txtDescricao.getY()
				+ txtDescricao.getHeight() + 10, 200, 30);
		lblValor.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(lblValor);

		txtValor = new JNumberFormatField();
		txtValor.setLimit(7);
		txtValor.setBounds(lblValor.getX(), lblValor.getY() + 30, 170, 30);
		txtValor.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		add(txtValor);

		lblQuantidade = new JLabel("* Quantidade mínima:");
		lblQuantidade
				.setBounds(lblNome.getX(), txtDescricao.getY() + 120, 300, 30);
		lblQuantidade
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(lblQuantidade);

		txtQuantidade = new JIntNumberFormatField();
		txtQuantidade.setLimit(10);
		txtQuantidade.setBounds(lblQuantidade.getX(),
				lblQuantidade.getY() + 30, 190, 30);
		txtQuantidade
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		add(txtQuantidade);

		lblFabricante = new JLabel("* Fabricante");
		lblFabricante.setBounds(lblCodigoBarras.getX(), txtDescricao.getY() + 120, 100,
				30);
		lblFabricante
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(lblFabricante);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		jcComboFabricante = new JComboBox();
		jcComboFabricante.setBounds(lblFabricante.getX(),
				lblFabricante.getY() + 30, 170, 30);
		jcComboFabricante.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 12));
		add(jcComboFabricante);
		jcComboFabricante.addItem(" ");

		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		lblCusto = new JLabel("* Custo Produto");		
		lblCusto.setBounds(lblNome.getX(),lblValor.getY(),120,30);
		lblCusto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(lblCusto);
		
		txtCusto= new JNumberFormatField();
		txtCusto.setLimit(7);
		txtCusto.setBounds(lblCusto.getX(), lblCusto.getY() + 30, 190, 30);
		txtCusto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		add(txtCusto);
		
		btnAdicionarFabricante = new JButton("+");
		btnAdicionarFabricante.setBackground(corOriginalBtn);
		btnAdicionarFabricante.setForeground(Color.WHITE);
		btnAdicionarFabricante.setFont(new Font("Arial",
				Font.TRUETYPE_FONT, 17));
		btnAdicionarFabricante.setBounds(jcComboFabricante.getX()
				+ jcComboFabricante.getHeight() + 143,
				jcComboFabricante.getY(), 40, 30);
		btnAdicionarFabricante.addActionListener(this);
		btnAdicionarFabricante.setToolTipText("Adicionar Novo Fabricante");
		add(btnAdicionarFabricante);

		//lblLucro = new JLabel("* Lucro Produto");		
		//lblLucro.setBounds(lblCodigoBarras.getX(),txtCodigoBarras.getY()+txtCodigoBarras.getHeight()+5,120,30);
		//lblLucro.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		//add(lblLucro);
		//txtValor.addKeyListener(this);
		//txtCusto.addKeyListener(this);
		
		
		//txtLucro= new JNumberFormatField();
		//txtLucro.setLimit(7);
		//txtLucro.setBounds(lblLucro.getX(), lblLucro.getY() + 30, 170, 30);
		//txtLucro.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		//add(txtLucro);

		lblImg = new JLabel("");
		lblImg.setBounds(570, 120, 265, 265);
		lblImg.setHorizontalAlignment(SwingConstants.CENTER);

		lblImg.setBorder(BorderFactory.createLineBorder(new Color(161, 0, 64)));
		add(lblImg);

		final JTextArea text = new JTextArea();
		text.setFocusable(false);
		text.setBounds(570, lblImg.getY(), lblImg.getWidth(), lblImg.getHeight());
		text.setOpaque(false);
		text.setBackground(new Color(0, 0, 0, 0));
		text.setEditable(false);

		new FileDrop(System.out, text, new FileDrop.Listener() {
			public void filesDropped(java.io.File[] files) {
				for (int i = 0; i < files.length; i++) {
					try {
						aux = "";
						String conferir = "";
						String nois = files[i].getName();
						String path = files[i].getCanonicalPath() + "\n";
						String teste = path.trim();
						if (teste.substring(teste.length() - 3).equals("jpg")
								|| teste.substring(teste.length() - 3).equals(
										"png")
								|| teste.substring(teste.length() - 3).equals(
										"pgm")) {

							if (lblNovoProduto.isEnabled() == false
									&& lblEditarProduto.isEnabled() == false) {
								lblImg.setIcon(new ImageIcon(teste));
								try {
									if (tabela.getSelectedRow() != -1)

										conferir = (String) tabela.getValueAt(
												tabela.getSelectedRow(), 5);
									if (conferir.equals("-----------")) {

									} else {

										for (Produto p : pController.listar()) {
											if (p.getFotoProduto()
													.substring(34).equals(nois)) {
												String extensao = nois
														.substring(nois
																.length() - 4);

												nois = nois.substring(0,
														nois.length() - 4);
												nois += "1";
												nois += extensao;
											}
										}
									}
								} catch (Exception e) {

								}
								try {
									aux = nois;

									imagem = setImagemDimensao(teste, 265, 265);
									lblImg.setText("");
									
									lblImg.setIcon(new ImageIcon(imagem));
									//localFoto = teste;

								} catch (Exception ex) {

								}

							}

						}
					} // end try
					catch (java.io.IOException e) {
					}
				}

			}
		});
		add(text);

		btnImg = new JButton("Adicionar imagem");
		btnImg.setBackground(corOriginalBtn);
		btnImg.setForeground(Color.WHITE);
		btnImg.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 11));
		btnImg.setBounds(570, 405, 130, 30);
		btnImg.addActionListener(this);
		add(btnImg);

		btnRetirarImg = new JButton("Remover imagem");
		btnRetirarImg.setBackground(corOriginalBtn);
		btnRetirarImg.setForeground(Color.WHITE);
		btnRetirarImg
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 11));
		btnRetirarImg.setBounds(btnImg.getX() + btnImg.getWidth() + 5,
				btnImg.getY(), btnImg.getWidth(), 30);
		btnRetirarImg.addActionListener(this);
		add(btnRetirarImg);

		btnPrimLinha = new JButton("<<");
		btnPrimLinha.setBackground(corOriginalBtn);
		btnPrimLinha.setForeground(Color.WHITE);
		btnPrimLinha.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
		btnPrimLinha.setBounds(420, 460, 52, 40);
		add(btnPrimLinha);
		btnPrimLinha.addActionListener(this);

		btnVoltar = new JButton("<");
		btnVoltar.setBackground(corOriginalBtn);
		btnVoltar.setForeground(Color.WHITE);
		btnVoltar.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
		btnVoltar.setBounds(btnPrimLinha.getX() + 55, btnPrimLinha.getY(),
				btnPrimLinha.getWidth() - 5, btnPrimLinha.getHeight());
		add(btnVoltar);
		btnVoltar.addActionListener(this);

		btnAvancar = new JButton(">");
		btnAvancar.setBackground(corOriginalBtn);
		btnAvancar.setForeground(Color.WHITE);
		btnAvancar.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
		btnAvancar.setBounds(btnVoltar.getX() + 50, btnVoltar.getY(),
				btnVoltar.getWidth(), btnVoltar.getHeight());
		add(btnAvancar);
		btnAvancar.addActionListener(this);

		btnUltLinha = new JButton(">>");
		btnUltLinha.setBackground(corOriginalBtn);
		btnUltLinha.setForeground(Color.WHITE);
		btnUltLinha.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
		btnUltLinha.setBounds(btnAvancar.getX() + 50, btnAvancar.getY(),
				btnPrimLinha.getWidth(), btnPrimLinha.getHeight());
		add(btnUltLinha);
		btnUltLinha.addActionListener(this);

		lblNovoProduto = new JLabel(new ImageIcon("Img/Produto/Novo.png"));
		// lblNovoProduto.setOpaque(true);
		// lblNovoProduto.setBackground(Color.GREEN);
		lblNovoProduto.setBounds(890, 65, 61, 52);
		lblNovoProduto.setToolTipText("Novo");
		lblNovoProduto.addMouseListener(this);
		add(lblNovoProduto);

		lblSalvarProduto = new JLabel(new ImageIcon("Img/Produto/Salvar.png"));
		// lblSalvarProduto.setOpaque(true);
		// lblSalvarProduto.setBackground(Color.GREEN);
		lblSalvarProduto.setBounds(lblNovoProduto.getX(),
				lblNovoProduto.getY() + 60, 61, 52);
		lblSalvarProduto.setToolTipText("Salvar");
		lblSalvarProduto.addMouseListener(this);
		lblSalvarProduto.setEnabled(false);
		add(lblSalvarProduto);

		lblEditarProduto = new JLabel(new ImageIcon("Img/Produto/Editar.png"));
		// lblEditarProduto.setOpaque(true);
		// lblEditarProduto.setBackground(Color.GREEN);
		lblEditarProduto.setBounds(lblSalvarProduto.getX(),
				lblSalvarProduto.getY() + 60, 61, 52);
		lblEditarProduto.setToolTipText("Editar");
		lblEditarProduto.addMouseListener(this);
		lblEditarProduto.setEnabled(false);
		add(lblEditarProduto);

		lblExcluirProduto = new JLabel(new ImageIcon("Img/Produto/Remover.png"));
		// lblExcluirProduto.setOpaque(true);
		// lblExcluirProduto.setBackground(Color.GREEN);
		lblExcluirProduto.setBounds(lblEditarProduto.getX(),
				lblEditarProduto.getY() + 60, 61, 52);
		lblExcluirProduto.setToolTipText("Excluir");
		lblExcluirProduto.addMouseListener(this);
		lblExcluirProduto.setEnabled(false);
		add(lblExcluirProduto);

		lblCancelar = new JLabel(new ImageIcon("Img/Cancelar.png"));
		// lblCancelar.setOpaque(true);
		// lblCancelar.setBackground(Color.GREEN);
		lblCancelar.setBounds(lblExcluirProduto.getX() + 2,
				lblExcluirProduto.getY() + 60, 51, 51);
		lblCancelar.setToolTipText("Cancelar");
		lblCancelar.addMouseListener(this);
		add(lblCancelar);

		lblEntradaProduto = new JLabel(new ImageIcon(
				"Img/Entrada de Produto/Nova.png"));
		lblEntradaProduto.setBounds(lblNovoProduto.getX() + 3,
				lblCancelar.getY() + 65, 53, 57);
		lblEntradaProduto.addMouseListener(this);
		lblEntradaProduto.setToolTipText("Nova Entrada");
		add(lblEntradaProduto);

		lblRelatorioProduto = new JLabel(new ImageIcon("Img/Relatorio.png"));
		lblRelatorioProduto.setBounds(lblEntradaProduto.getX() - 5,
				lblEntradaProduto.getY() + 65, 61, 52);
		lblRelatorioProduto.addMouseListener(this);
		lblRelatorioProduto.setToolTipText("Relatório");
		add(lblRelatorioProduto);

		lblInfoCamposObrigatorios = new JLabel("* Campos Obrigatórios");
		lblInfoCamposObrigatorios.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		lblInfoCamposObrigatorios.setBounds(100, 470, 170, 30);
		add(lblInfoCamposObrigatorios);

		txtNome.addKeyListener(this);

		camposFalse();

		lblEditarProduto.setEnabled(false);

		btnImg.setEnabled(false);

		preencher();

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

		lblFundo = new JLabel(
				new ImageIcon("Img/Gerenciamento de Produtos.png"));
		lblFundo.setBounds(0, 0, getWidth(), getHeight());
		add(lblFundo);

		lblFundo = new JLabel(
				new ImageIcon("Img/Gerenciamento de Produtos.png"));
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
		tabela = new JTable(modelo);
		tabela.addMouseListener(this);
		tabela.addKeyListener(this);
		tabela.setSelectionBackground(new Color(222, 54, 121));
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.getTableHeader().setDefaultRenderer(new RTable());
		scrollTabela = new JScrollPane(tabela);
		// scrollTabela.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		tabela.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		scrollTabela.setBounds(45, 510, 990, 170);
		tabela.getTableHeader().setResizingAllowed(false);
		
		add(scrollTabela);

		modelo.addColumn("Nome");
		modelo.addColumn("Descrição");
		modelo.addColumn("Valor");
		modelo.addColumn("Quantidade Mínima");
		modelo.addColumn("Fabricante");
		modelo.addColumn("Foto");
		modelo.addColumn("Codigo de barras");
		modelo.addColumn("Custo");
		modelo.addColumn("Lucro");
		preencherJtable(modelo);
		tabela.getColumnModel().getColumn(0).setPreferredWidth(220);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(220);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(130);
		tabela.getColumnModel().getColumn(3).setPreferredWidth(130);
		tabela.getColumnModel().getColumn(4).setPreferredWidth(110);
		tabela.getColumnModel().getColumn(5).setPreferredWidth(185);
		tabela.getColumnModel().getColumn(6).setPreferredWidth(130);
		tabela.getColumnModel().getColumn(7).setPreferredWidth(130);
		tabela.getColumnModel().getColumn(8).setPreferredWidth(130);
		tabela.getTableHeader().setReorderingAllowed(false);
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
	}

	private int divideNomeProduto(String nome) {
		try {
			if (nome.indexOf(" ", 25) < 30)
				return nome.indexOf(" ", 25);
			else
				return nome.indexOf(" ", 15);
		} catch (Exception ex) {
			return 0;
		}
	}

	public void preencherJtable(DefaultTableModel modelo) {
		modelo.setNumRows(0);
		int linha = 0;
		if (pController.listar() != null)
			for (Produto c : pController.listar()) {
				linha = setDadosTabela(modelo, linha, c);
			}
	}

	private int setDadosTabela(DefaultTableModel modelo, int linha, Produto c) {
		if (c.getNomeProduto().length() < 30
				|| divideNomeProduto(c.getNomeProduto()) < 0)
			modelo.addRow(new Object[] { c.getNomeProduto(),
					c.getDescricaoProduto(), c.getValor(), c.getQuantidade(),
					c.getNomeFabricante(), c.getFotoProduto(),
					c.getCodBarras(),c.getCustoProduto(),c.getLucroProduto() });
		else {
			modelo.addRow(new Object[] {
					"<HTML><CENTER>"
							+ c.getNomeProduto().substring(0,
									divideNomeProduto(c.getNomeProduto()))
							+ "<BR>"
							+ c.getNomeProduto().substring(
									divideNomeProduto(c.getNomeProduto()),
									c.getNomeProduto().length()),
					c.getDescricaoProduto(), c.getValor(), c.getQuantidade(),
					c.getNomeFabricante(), c.getFotoProduto(),
					c.getCodBarras(),c.getCustoProduto(),c.getLucroProduto() });
			tabela.setRowHeight(linha, 30);
		}
		linha++;
		return linha;
	}
	

	

	public void pesquisaJtable(DefaultTableModel modelo) {
		modelo.setNumRows(0);
		int linha = 0;
	if (txtNome.getText().trim().length()>0){
		if (pController.achou(txtNome.getText()) != null) {
			for (Produto c : pController.achou(txtNome.getText())) {
				linha = setDadosTabela(modelo, linha, c);
			}
			linha++;
		}
	}else{
			
				if (pController.achou(txtCodigoBarras.getText()) != null) {
					for (Produto c : pController.achouBarras(txtCodigoBarras.getText().trim())) {
						linha = setDadosTabela(modelo, linha, c);
					}
					linha++;
				}
				
	}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(btnImg)) {
			String conferir = "";
			FileDialog fc = new FileDialog(new Dialog(this),
					"Selecione uma imagem...", FileDialog.LOAD);
			fc.setFile("*.jpg;*.jpeg; *.png");
			fc.setVisible(true);
			String diretorio = fc.getDirectory();
			String arquivo = fc.getFile();
			if (diretorio != null && arquivo != null) {
				try {
					aux = arquivo;
					try {
						if (tabela.getSelectedRow() != -1)
							conferir = (String) tabela.getValueAt(
									tabela.getSelectedRow(), 5);
						if (conferir.equals("-----------")) {

						} else {
							for (Produto p : pController.listar()) {
								if (p.getFotoProduto().substring(34)
										.equals(aux)) {
									String extensao = aux.substring(aux
											.length() - 4);

									aux = aux.substring(0, aux.length() - 4);
									aux += "1";
									aux += extensao;
								}
							}
						}
					} catch (Exception e1) {

					}
					localFoto = diretorio + arquivo;
					imagem = setImagemDimensao(localFoto, 265, 265);
					lblImg.setText("");
					lblImg.setIcon(new ImageIcon(imagem));

				} catch (Exception ex) {

				}

			}
		}
		if (e.getSource().equals(btnAdicionarFabricante)) {
			new JanelaFabricante(this);
		}

		if (e.getSource() == btnPrimLinha) {
			lblNovoProduto.setEnabled(false);
			tabela.setRowSelectionInterval(0, 0);
			lblEditarProduto.setEnabled(true);
			lblExcluirProduto.setEnabled(true);
			dadosTabela();
			tabela.grabFocus();
		}
		if (e.getSource() == btnUltLinha) {
			lblNovoProduto.setEnabled(false);
			tabela.setRowSelectionInterval(tabela.getModel().getRowCount() - 1,
					tabela.getModel().getRowCount() - 1);
			lblEditarProduto.setEnabled(true);
			lblExcluirProduto.setEnabled(true);

			dadosTabela();
			tabela.grabFocus();
		}
		if (e.getSource() == btnAvancar) {
			if (tabela.getSelectedRow() < tabela.getModel().getRowCount() - 1) {
				lblNovoProduto.setEnabled(false);
				tabela.setRowSelectionInterval(tabela.getSelectedRow() + 1,
						tabela.getSelectedRow() + 1);
				lblEditarProduto.setEnabled(true);
				lblExcluirProduto.setEnabled(true);
				dadosTabela();

				tabela.grabFocus();
			}
		}
		if (e.getSource() == btnVoltar) {
			lblNovoProduto.setEnabled(false);
			if (tabela.getSelectedRow() > 0) {
				tabela.setRowSelectionInterval(tabela.getSelectedRow() - 1,
						tabela.getSelectedRow() - 1);
				lblEditarProduto.setEnabled(true);
				lblExcluirProduto.setEnabled(true);
				dadosTabela();
				tabela.grabFocus();
			} else {
				tabela.clearSelection();
				lblNovoProduto.setEnabled(true);
				lblEditarProduto.setEnabled(false);
				lblExcluirProduto.setEnabled(false);
				limpaCampos();
			}
		}
		if (e.getSource() == btnRetirarImg) {
			lblImg.setIcon(null);
		}

	}

	public boolean cadastrar() {
		try {
			if (txtNome.getText().trim().length() < 1
					|| txtValor.getText().length() < 1
					|| txtQuantidade.getText().length() < 1
					|| jcComboFabricante.getSelectedIndex() <= 0
					|| txtCusto.getText().length() < 1
					|| txtCodigoBarras.getText().trim().length() <1
					|| !verificarCustoValor(Double.parseDouble(txtValor.getText().replace(",",".").substring(3)),Double.parseDouble(txtCusto.getText().replace(",",".").substring(3)))) {

				JOptionPane.showMessageDialog(null,
						"Preencha os campos corretamente");
				return false;
			} else {
				Fabricante f = (Fabricante) jcComboFabricante.getSelectedItem();
				Produto produto = new Produto();
				if (aux.equals("")) {
					caminho = "-----------";
				} else {
					try {
						File outputfile = new File(
								"C:/Royal/src/br/com/royal/imagens/" + aux);

						caminho = outputfile.toString();
						
						System.out.println("caminho: " + caminho);
						System.out.println("localFoto: " + localFoto);
						ImageIO.write(imagem, "jpg", outputfile);

					} catch (Exception ex) {
						System.out.println("quebro");
					}
					aux = "";
				}
				produto.setNomeProduto(txtNome.getText());
				produto.setDescricaoProduto(txtDescricao.getText());
				produto.setValor(Double.parseDouble(txtValor.getText().replace(
						",", ".").substring(3)));
				produto.setQuantidade(Integer.parseInt(txtQuantidade.getText().replace(".", "")));
				produto.setCodFabricante(f.getCodFabricante());
				produto.setFotoProduto(caminho);
				produto.setNomeFabricante(f.getNomeFabricante());
				produto.setCodBarras(txtCodigoBarras.getText().trim());
				produto.setCustoProduto(Double.parseDouble(txtCusto.getText().replace(",",".").substring(3)));
				produto.setLucroProduto(produto.getValor() - produto.getCustoProduto());
				
				if (pController.conferirBarras(produto.getCodBarras()))
					if (pController.cadastrar(produto)) {
						preencherJtable(modelo);
						JOptionPane.showMessageDialog(null,
								"Produto Cadastrado com sucesso \n");
						Produto p = new Produto();
						p.setCodBarras(produto.getCodBarras());
						p = pController.codigoBarras(p);
						Estoque estoque = new Estoque();
						estoque.setCodProduto(p.getId());
						EstoqueController e = new EstoqueController();
						e.acaoEstoque(estoque);
						
						return true;
					} else {
						JOptionPane.showMessageDialog(null,
								"Digite um nome ou descrição válido ");
						return false;
					}
				else
					JOptionPane.showMessageDialog(null,
							"Codigo de barras ja cadastrado");
				return false;
			}
		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(null,
							"Preencha os campos corretamente, textos não são aceitos em campos numéricos");
			return false;
		}

	}

	public static BufferedImage setImagemDimensao(String caminhoImg,
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
			System.out.println(ex.getMessage());
			Logger.getLogger(JanelaProduto.class.getName()).log(Level.SEVERE,
					null, ex);
		}

		// pegando a largura da img
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

			// --- se altura for maior do que o parÃ¢metro imgAltura, diminui-se
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
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(lblRelatorioProduto)
				&& lblRelatorioProduto.isEnabled()) {

		}
		if (e.getSource().equals(lblMinimize)) {
			this.setExtendedState(JFrame.ICONIFIED);
		}

		else if (e.getSource().equals(lblClose)) {
			this.dispose();
		}
		if (e.getSource().equals(lblEntradaProduto)
				&& lblEntradaProduto.isEnabled()) {
			if (tabela.getSelectedRow() != -1) {
				String codigoBarras = tabela.getValueAt(
						tabela.getSelectedRow(), 6).toString();
				new JanelaEntradaProduto(null, this, codigoBarras);
			} else {
				String codigoBarras = "";
				new JanelaEntradaProduto(null, this, codigoBarras);
			}

			this.setVisible(false);
		}
		if (e.getSource().equals(lblNovoProduto) && lblNovoProduto.isEnabled()) {
			camposTrue();
			txtNome.setEnabled(true);
			txtCodigoBarras.setEnabled(true);
			tabela.clearSelection();
			lblSalvarProduto.setEnabled(true);
			btnImg.setEnabled(true);
			lblExcluirProduto.setEnabled(false);
			lblEditarProduto.setEnabled(false);
			lblNovoProduto.setEnabled(false);
			txtBtn = "Novo";
			tabela.setEnabled(false);
			if (txtCodigoBarras.isEnabled())
				txtCodigoBarras.grabFocus();
			else
				txtNome.grabFocus();

		}

		if (e.getSource().equals(lblEditarProduto)
				&& lblEditarProduto.isEnabled()) {
			if (tabela.getSelectedRow() != -1) {
				txtNome.setEnabled(true);
				txtCodigoBarras.setEnabled(true);
				camposTrue();
				btnImg.setEnabled(true);
				lblSalvarProduto.setEnabled(true);
				// setando enable = false nos botoes novo, editar e excluir
				lblNovoProduto.setEnabled(false);
				lblEditarProduto.setEnabled(false);
				lblExcluirProduto.setEnabled(false);
				imagemAuxiliar = (String) tabela.getValueAt(
						tabela.getSelectedRow(), 5);
				nomeProduto = tabela.getValueAt(tabela.getSelectedRow(), 0)
						.toString();
				descricaoProduto = tabela
						.getValueAt(tabela.getSelectedRow(), 1).toString();
				txtNome.setText(tabela.getValueAt(tabela.getSelectedRow(), 0)
						.toString());
				txtDescricao.setText(tabela.getValueAt(tabela.getSelectedRow(),
						1).toString());
				txtValor.setText(tabela.getValueAt(tabela.getSelectedRow(), 2)
						.toString());
				txtQuantidade.setText(tabela.getValueAt(
						tabela.getSelectedRow(), 3).toString());

				txtCodigoBarras.setText(tabela.getValueAt(
						tabela.getSelectedRow(), 6).toString());
				
				barrasAntiga = tabela.getValueAt(tabela.getSelectedRow(), 6).toString();

				String fab = (String) tabela.getValueAt(
						tabela.getSelectedRow(), 4);

				for (int i = 0; i < jcComboFabricante.getItemCount(); i++) {
					String l = jcComboFabricante.getItemAt(i).toString();
					if (fab.equals(l)) {

						Fabricante m = (Fabricante) jcComboFabricante
								.getItemAt(i);

						jcComboFabricante.setSelectedItem(m);

					}
				}
				lblImg.setIcon(new ImageIcon((String) tabela.getValueAt(
						tabela.getSelectedRow(), 5)));
				imagemAuxiliar = (String) tabela.getValueAt(
						tabela.getSelectedRow(), 5);

				txtBtn = "Editar";
				// tabela.getSelectionModel().clearSelection();
				tabela.setEnabled(false);
				tabela.getSelectionModel().clearSelection();

				txtNome.grabFocus();

			} else {
				JOptionPane.showMessageDialog(null, "Selecione um Produto");
			}
		}
		if (e.getSource().equals(lblExcluirProduto)
				&& lblExcluirProduto.isEnabled()) {
			if (tabela.getSelectedRow() != -1) {
				Produto produto = new Produto();
				produto.setNomeProduto(tabela.getValueAt(
						tabela.getSelectedRow(), 0).toString());
				produto.setDescricaoProduto(tabela.getValueAt(
						tabela.getSelectedRow(), 1).toString());
				produto.setFotoProduto((String) tabela.getValueAt(
						tabela.getSelectedRow(), 5));

				File o = new File(produto.getFotoProduto());
				int resposta = JOptionPane.showConfirmDialog(null,
						"Deseja excluir o produto ? ");
				if (resposta == JOptionPane.YES_OPTION) {

					if (o.delete()) {
						JOptionPane.showMessageDialog(null,
								"Produto Excluido com sucesso");
						pController.remover(produto);
						aux = "";
						// epController.remover(produto);
					} else {
						JOptionPane.showMessageDialog(null,
								"Produto Excluido com sucesso");
						pController.remover(produto);
						aux="";
						// epController.remover(produto);
					}
				}

				lblEditarProduto.setEnabled(false);
				lblExcluirProduto.setEnabled(false);
				lblNovoProduto.setEnabled(true);
				btnImg.setEnabled(false);
				lblSalvarProduto.setEnabled(false);
				tabela.getSelectionModel().clearSelection();
				tabela.setEnabled(true);
				limpaCampos();

				lblImg.setIcon(null);
				preencherJtable(modelo);
				txtNome.grabFocus();

			} else {
				JOptionPane.showMessageDialog(null,
						"Selecione um produto para exluir");
			}
		}

		if (e.getSource().equals(lblSalvarProduto)
				&& lblSalvarProduto.isEnabled()) {

			if (txtBtn.equals("Novo")) {
				if (cadastrar()) {
					txtBtn = "";
					limpaCampos();
					camposFalse();
					lblEditarProduto.setEnabled(false);
					lblExcluirProduto.setEnabled(false);
					lblNovoProduto.setEnabled(true);
					btnImg.setEnabled(false);
					lblSalvarProduto.setEnabled(false);
					tabela.getSelectionModel().clearSelection();
					tabela.setEnabled(true);
					preencherJtable(modelo);
					txtNome.grabFocus();
					tabela.setEnabled(true);
				} else {

				}
			} else {
				if (editar()) {
					txtBtn = "";
					limpaCampos();
					camposFalse();
					lblEditarProduto.setEnabled(false);
					lblExcluirProduto.setEnabled(false);
					lblNovoProduto.setEnabled(true);
					btnImg.setEnabled(false);
					lblSalvarProduto.setEnabled(false);
					tabela.getSelectionModel().clearSelection();
					tabela.setEnabled(true);
					preencherJtable(modelo);
					txtNome.grabFocus();
					lblImg.setIcon(null);
					tabela.setEnabled(true);

				}
			}
		}

		if (e.getSource().equals(lblCancelar) && lblCancelar.isEnabled()) {

			limpaCampos();

			camposFalse();
			btnImg.setEnabled(false);
			lblImg.setIcon(null);
			lblEditarProduto.setEnabled(false);
			lblExcluirProduto.setEnabled(false);
			lblNovoProduto.setEnabled(true);

			lblSalvarProduto.setEnabled(false);
			txtBtn = "";

			//pesquisa();
			tabela.setEnabled(true);
			preencherJtable(modelo);
			txtCodigoBarras.grabFocus();

		}

		if (e.getSource().equals(lblRelatorioProduto)
				&& lblRelatorioProduto.isEnabled()) {
			if (tabela.getSelectedRow() != -1) {
				GerarRelatorioProduto gru = new GerarRelatorioProduto();
				ArrayList<RelatorioProdutos> relatorioProdutos = new ArrayList<RelatorioProdutos>();
				RelatorioProdutos rp = new RelatorioProdutos();
				rp.setNome(tabela.getValueAt(tabela.getSelectedRow(), 0)
						.toString());
				rp.setDescricao(tabela.getValueAt(tabela.getSelectedRow(), 1)
						.toString());
				rp.setValor(tabela.getValueAt(tabela.getSelectedRow(), 2)
						.toString());
				rp.setQuantidade(tabela.getValueAt(tabela.getSelectedRow(), 3)
						.toString());
				rp.setFabricante(tabela.getValueAt(tabela.getSelectedRow(), 4)
						.toString());
				rp.setData("");
				rp.setDataCompleta("");
				relatorioProdutos.add(rp);
				gru.gerarProduto(relatorioProdutos);
			} else if (tabela.getRowCount() > 0) {
				GerarRelatorioProduto gru = new GerarRelatorioProduto();
				ArrayList<RelatorioProdutos> relatorioProdutos = new ArrayList<RelatorioProdutos>();
				for (int i = 0; i < tabela.getRowCount(); i++) {
					RelatorioProdutos rp = new RelatorioProdutos();
					rp.setNome(tabela.getValueAt(i, 0).toString());
					rp.setDescricao(tabela.getValueAt(i, 1).toString());
					rp.setValor(tabela.getValueAt(i, 2).toString());
					rp.setQuantidade(tabela.getValueAt(i, 3).toString());
					rp.setFabricante(tabela.getValueAt(i, 4).toString());
					rp.setData("");
					rp.setDataCompleta("");
					relatorioProdutos.add(rp);
				}
				gru.gerarProduto(relatorioProdutos);
			}
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource().equals(lblNovoProduto) && lblNovoProduto.isEnabled()) {
			lblNovoProduto.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblSalvarProduto)
				&& lblSalvarProduto.isEnabled()) {
			lblSalvarProduto.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblEditarProduto)
				&& lblEditarProduto.isEnabled()) {
			lblEditarProduto.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblExcluirProduto)
				&& lblExcluirProduto.isEnabled()) {
			lblExcluirProduto.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblCancelar) && lblCancelar.isEnabled()) {
			lblCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblRelatorioProduto)
				&& lblRelatorioProduto.isEnabled()) {
			lblRelatorioProduto.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblClose)
				&& lblClose.isEnabled()) {
			lblClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblMinimize)
				&& lblMinimize.isEnabled()) {
			lblMinimize.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		
		if (e.getSource().equals(lblEntradaProduto)
				&& lblEntradaProduto.isEnabled()) {
			lblEntradaProduto.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
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
	public void mouseReleased(MouseEvent arg0) {
		if (tabela.getSelectedRow() != -1) {
			dadosTabela();
			lblNovoProduto.setEnabled(false);
			lblEditarProduto.setEnabled(true);
			lblExcluirProduto.setEnabled(true);
		} else {
			lblEditarProduto.setEnabled(false);
			lblExcluirProduto.setEnabled(false);
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (txtNome.hasFocus() && txtBtn.equals("")){
			
			pesquisa();
		}
		
		else if (txtCodigoBarras.hasFocus() && txtBtn.equals("")){
			
			pesquisaBarras();
		}else{
			txtNome.setEnabled(true);
			txtCodigoBarras.setEnabled(true);
			
		}
		
		
		
		if (txtCodigoBarras.getText().trim().length()>0 && txtBtn.equals("Novo")){
			
		 if(!pController.conferirBarras(txtCodigoBarras.getText().trim())){
				
			 lblDuplicidade.setText("Código já cadastrado");
			System.out.println("morro do dende");
			
			}else{
				lblDuplicidade.setText("");
			}
		
		}else if (txtCodigoBarras.getText().trim().length()>0 && txtBtn.equals("Editar")){
		if (txtCodigoBarras.getText().trim().equals(barrasAntiga)){
			lblDuplicidade.setText("");
		}else{
			
			
			if(!pController.conferirBarras(txtCodigoBarras.getText().trim())){
				lblDuplicidade.setText("Código já cadastrado");
			
			}else{
				lblDuplicidade.setText("");
			}
		}
			
		}else{
			lblDuplicidade.setText("");
		}
		
		
		//if(txtValor.getText().length() > 0 && txtCusto.getText().length() > 0 ){
		//double l=Double.parseDouble(txtValor.getText().replace(
		//			",", ".").substring(3));
	//	double j =Double.parseDouble(txtCusto.getText().replace(
		//		",", ".").substring(3));
		//if (l>j){
		//	txtLucro.setText(l-j+"");
		//}else{
		//	txtLucro.setText("0");
	//	}
			
		//}

		if (tabela.isEnabled() && tabela.getSelectedRow() > -1) {
			if (e.getKeyCode() == e.VK_DOWN || e.getKeyCode() == e.VK_RIGHT)
				dadosTabela();
			if (e.getKeyCode() == e.VK_UP || e.getKeyCode() == e.VK_LEFT)
				dadosTabela();
		}

	}
	public boolean verificarCustoValor(double l,double j){
		if (l>j){
			return true;
		}else if (l==j){
			return true;
		}else{
			return false;
		}
	}

	public void pesquisa() {
		if (txtNome.getText().length() > 0 ) {
			pesquisaJtable(modelo);
			txtCodigoBarras.setEnabled(false);
		} else {
			preencherJtable(modelo);
				txtCodigoBarras.setEnabled(true);
		}
	}
	
	public void pesquisaBarras(){
		if (txtCodigoBarras.getText().trim().length()>0){
			pesquisaJtable(modelo);
			txtNome.setEnabled(false);		
			}else{
			preencherJtable(modelo);
			txtNome.setEnabled(true);
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	private void camposTrue() {
		txtNome.setEnabled(true);
		//txtLucro.setEnabled(false);
		txtDescricao.setEnabled(true);
		txtValor.setEnabled(true);
		txtQuantidade.setEnabled(true);
		txtCusto.setEnabled(true);
		jcComboFabricante.setEnabled(true);
		btnAdicionarFabricante.setEnabled(true);
		btnRetirarImg.setEnabled(true);
		btnPrimLinha.setEnabled(false);
		btnUltLinha.setEnabled(false);
		btnVoltar.setEnabled(false);
		btnAvancar.setEnabled(false);
		lblRelatorioProduto.setEnabled(false);
	}

	private void camposFalse() {
		//txtLucro.setEnabled(false);
		txtCusto.setEnabled(false);
		txtNome.setEnabled(true);
		txtDescricao.setEnabled(false);
		txtValor.setEnabled(false);
		txtQuantidade.setEnabled(false);
		jcComboFabricante.setEnabled(false);
		btnAdicionarFabricante.setEnabled(false);
		btnRetirarImg.setEnabled(false);
		btnPrimLinha.setEnabled(true);
		btnUltLinha.setEnabled(true);
		btnVoltar.setEnabled(true);
		btnAvancar.setEnabled(true);
		verificaRelatorio();
		lblImg.setIcon(null);
	}

	public boolean editar() {
		try {
			if (txtNome.getText().trim().length() < 1
					|| txtValor.getText().length() < 1
					|| txtQuantidade.getText().length() < 1
					|| jcComboFabricante.getSelectedIndex() <= 0
					|| txtCusto.getText().length()<1
					|| txtCodigoBarras.getText().trim().length() < 1
					|| !verificarCustoValor(Double.parseDouble(txtValor.getText().replace(",",".").substring(3)),Double.parseDouble(txtCusto.getText().replace(",",".").substring(3)))) {
				JOptionPane.showMessageDialog(null,
						"Preencha os campos corretamente");
				lblNovoProduto.setEnabled(true);
				return false;
			} else {
				Fabricante f = (Fabricante) jcComboFabricante.getSelectedItem();
				Produto produto = new Produto();
				try {
					if (imagemAuxiliar.equals("-----------") && aux.equals("")) {

						caminho = "-----------";
					} else if (imagemAuxiliar.equals("-----------")
							&& !aux.equals("")) {

						File outputfile = new File(
								"C:/Royal/src/br/com/royal/imagens/" + aux);
						caminho = outputfile.toString();
						ImageIO.write(imagem, "jpg", outputfile);
						aux = "";

					} else {
						if (aux.equals("") && lblImg.getIcon() != null) {

							File outputfile = new File(imagemAuxiliar);

							caminho = outputfile.toString();
							ImageIO.write(imagem, "jpg", outputfile);

						} else if (aux.equals("") && lblImg.getIcon() == null) {

							File o1 = new File(imagemAuxiliar);
							if (o1.delete()) {

								caminho = "-----------";
							} else {

							}
						}

						else if (!aux.equals("") && lblImg.getIcon() != null) {

							File o = new File(imagemAuxiliar);

							if (o.delete()) {

								File outputfile = new File(
										"C:/Royal/src/br/com/royal/imagens/"
												+ aux);
								caminho = outputfile.toString();
								

								ImageIO.write(imagem, "jpg", outputfile);
							}
							aux = "";
						}
					}

				} catch (Exception ex) {

				}

				produto.setNomeProduto(txtNome.getText());
				produto.setDescricaoProduto(txtDescricao.getText());
				produto.setValor(Double.parseDouble(txtValor.getText().replace(
						",", ".").substring(3)));
				produto.setQuantidade(Integer.parseInt(txtQuantidade.getText().replace(".", "")));
				produto.setCodFabricante(f.getCodFabricante());
				produto.setFotoProduto(caminho);
				produto.setNomeFabricante(f.getNomeFabricante());
				produto.setCodBarras(txtCodigoBarras.getText().trim());
				produto.setCustoProduto(Double.parseDouble(txtCusto.getText().replace(
						",", ".").substring(3)));
				produto.setLucroProduto(produto.getValor()-produto.getCustoProduto());
				
				if (pController.atualizar(produto, nomeProduto,
						descricaoProduto)) {

					preencherJtable(modelo);
					JOptionPane.showMessageDialog(null,
							"Produto Editado com sucesso \n");
					lblNovoProduto.setEnabled(true);
					return true;

				} else {
					JOptionPane.showMessageDialog(null,
							"Digite um nome ou descrição válidos");
					return false;
				}
			}
		} catch (Exception e2) {
			JOptionPane
					.showMessageDialog(null,
							"Preencha os campos corretamente, textos não são aceitos em campos numéricos");
			return false;
		}

	}

	private void limpaCampos() {
		txtNome.setText("");
		txtDescricao.setText("");
		txtValor.setText("");
		txtCusto.setText("");
		//txtLucro.setText("");
		txtQuantidade.setText("");
		jcComboFabricante.setSelectedIndex(0);
		lblImg.setIcon(null);
		txtCodigoBarras.setText("");
		aux="";
		lblDuplicidade.setText("");
	}

	public void dadosTabela() {
		if (tabela.getSelectedRow() != -1) {
			limpaCampos();
			nomeProduto = tabela.getValueAt(tabela.getSelectedRow(), 0)
					.toString().replace("<HTML><CENTER>", "")
					.replace("<BR>", "");
			descricaoProduto = tabela.getValueAt(tabela.getSelectedRow(), 1)
					.toString();
			txtNome.setText(nomeProduto);
			txtDescricao.setText(tabela.getValueAt(tabela.getSelectedRow(), 1)
					.toString());
			txtValor.setText(tabela.getValueAt(tabela.getSelectedRow(), 2)
					.toString());
			txtCusto.setText(tabela.getValueAt(tabela.getSelectedRow(), 7).toString());
			txtQuantidade.setText(tabela.getValueAt(tabela.getSelectedRow(), 3)
					.toString());
			if (tabela.getValueAt(tabela.getSelectedRow(), 5).toString()
					.length() > 25){
				//lblImg.setIcon(new ImageIcon(tabela.getValueAt(tabela.getSelectedRow(), 5).toString()));
			
			imagemAux = setImagemDimensao(tabela.getValueAt(tabela.getSelectedRow(), 5).toString(), 265, 265);
			lblImg.setText("");
			lblImg.setIcon(new ImageIcon(imagemAux));
			}

			txtCodigoBarras.setText(tabela.getValueAt(tabela.getSelectedRow(),
					6).toString());
			String fab = (String) tabela.getValueAt(tabela.getSelectedRow(), 4);

			for (int i = 0; i < jcComboFabricante.getItemCount(); i++) {
				String l = jcComboFabricante.getItemAt(i).toString();
				if (fab.equals(l)) {

					Fabricante m = (Fabricante) jcComboFabricante.getItemAt(i);

					jcComboFabricante.setSelectedItem(m);

				}
			}
		}
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
		else if (jep != null)
			jep.setVisible(true);
		else if (jc != null)
			jc.setVisible(true);
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
		if (tabela.getRowCount() == 0) {
			lblRelatorioProduto.setEnabled(false);
			return false;
		}
		lblRelatorioProduto.setEnabled(true);
		return true;
	}

}
