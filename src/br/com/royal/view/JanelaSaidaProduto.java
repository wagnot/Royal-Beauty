package br.com.royal.view;

import java.awt.Color;
import java.awt.Cursor;
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

import br.com.royal.controller.EstoqueController;
import br.com.royal.controller.MotivoSaidaController;
import br.com.royal.controller.ProdutoController;
import br.com.royal.controller.SaidaProdutoController;
import br.com.royal.controllerReports.GerarRelatorioEntradaProduto;
import br.com.royal.model.Estoque;
import br.com.royal.model.MotivoEntrada;
import br.com.royal.model.MotivoSaida;
import br.com.royal.model.Produto;
import br.com.royal.model.SaidaProduto;
import br.com.royal.modelReports.RelatorioEntradaProduto;

public class JanelaSaidaProduto extends JFrame implements ActionListener,
		MouseMotionListener, MouseListener, KeyListener, WindowListener {
	private JLabel lblFundo, lblQuantidadeSaida, lblMotivoSaida, lblDataSaida,
			lblInfoCamposObrigatorios, lblProduto, lblImgInfoData1, lblNavbar,
			lblClose, lblMinimize, lblNovaSaidaProduto, lblSalvarSaidaProduto,
			lblEditarSaidaProduto, lblCancelar, lblRelatorioSaidaProduto,
			lblTxtFoto, lblFoto, lblNomeProduto, lblTxtNomeProduto,
			lblDescricaoProduto, lblTxtDescricaoProduto,
			lblQuantidadeMinimaProduto, lblTxtQuantidadeMinimaProduto,
			lblFabricanteProduto, lblTxtFabricanteProduto, lblTxtValorProduto,
			lblValorProduto;
	private JTextField txtQuantidadeSaida, txtProduto;
	private JComboBox<Object> jcComboMotivo;
	private JFormattedTextField mskDataSaida;
	private MaskFormatter mascaraData = null;
	private JTable tabela;
	private Color corOriginalBtn = new Color(164, 6, 69);
	private JScrollPane scrollTabela;
	private FTable modelo = new FTable();
	private String txtBtn = "";
	private JButton btnPrimLinha, btnVoltar, btnAvancar, btnUltLinha,
			btnAddFornecedor, btnAddMotivo, btnAddProduto;
	private int posX, posY;
	private ProdutoController pController = new ProdutoController();

	private MotivoSaidaController motivoController = new MotivoSaidaController();
	private SaidaProdutoController spController = new SaidaProdutoController();
	private EstoqueController eController = new EstoqueController();
	private List<SaidaProduto> saida = new ArrayList<SaidaProduto>();
	private String codigoBarras = "";
	private JanelaProdutosEmFalta jpef;
	private JanelaProduto jp;
	private int codigo;
	private int quantidadeSaida;
	public String localFoto="";

	private Produto produto = new Produto();
	private JLabel lblInfo;

	public JanelaSaidaProduto(JanelaProdutosEmFalta jpef,String codigoBarras) {
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		this.jpef = jpef;

		setLayout(null);
		setSize(902, 620);
		setTitle("Entrada");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Img/Rosa.png"));
		setResizable(false);
		setUndecorated(true);
		inicializaComponentes();
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
		addWindowListener(this);

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
	
	
	

	public void remover(MotivoEntrada me) {
		jcComboMotivo.removeItem(me);
	}

	public void preencher() {
		jcComboMotivo.removeAllItems();
		jcComboMotivo.addItem(" ");
		for (MotivoSaida m : motivoController.listar()) {
			jcComboMotivo.addItem(m);
		}

	}

	public void pegarCodigo() {
		codigo = saida.get(tabela.getSelectedRow()).getCodSaidaProduto();
		quantidadeSaida = saida.get(tabela.getSelectedRow())
				.getQuantidadeSaidaProduto();

		// .getCodFuncionario()).getCodFuncionario();
	}

	public void inicializaComponentes() {
		criaTabela();
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		lblFundo = new JLabel(new ImageIcon("Img/Saída de Produto.png"));
		lblFundo.setBounds(0, 0, getWidth(), getHeight());
		
		
		
		
		lblProduto = new JLabel("* Produto:");
		lblProduto.setBounds(120, 100,
				99, 30);
		lblProduto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		add(lblProduto);

		txtProduto = new JTextField();
		txtProduto.setBounds(lblProduto.getX() + lblProduto.getWidth() -6,
				lblProduto.getY(), 120, 30);
		txtProduto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		add(txtProduto);
		txtProduto.addKeyListener(this);
		
		lblQuantidadeSaida = new JLabel("* Quantidade de Saída:");
		lblQuantidadeSaida.setBounds(400, 100, 190, 30);
		lblQuantidadeSaida.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 16));
		add(lblQuantidadeSaida);
		
		lblMotivoSaida = new JLabel("* Motivo:");
		lblMotivoSaida.setBounds(lblProduto.getX(),
				lblQuantidadeSaida.getY() + 50, 90, 30);
		lblMotivoSaida.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				16));
		add(lblMotivoSaida);
		
		
		
		lblDataSaida = new JLabel("* Data de Saida:");
		lblDataSaida.setBounds(lblQuantidadeSaida.getX(), lblMotivoSaida.getY(), 140, 30);
		lblDataSaida
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		add(lblDataSaida);
		
		

		mskDataSaida = new JFormattedTextField();
		mskDataSaida.setBounds(lblDataSaida.getX() + lblDataSaida.getWidth(),
				lblDataSaida.getY(), 120, 30);
		mskDataSaida
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		mskDataSaida.addKeyListener(this);
		
		add(mskDataSaida);

		try {
			mascaraData = new MaskFormatter("##/##/####");
			mascaraData.setPlaceholderCharacter('_');
			mascaraData.install(mskDataSaida);
		} catch (Exception ex) {
		}

		

		lblImgInfoData1 = new JLabel();
		lblImgInfoData1.setBounds(mskDataSaida.getX() + 125,
				mskDataSaida.getY() + 5, 20, 20);
		add(lblImgInfoData1);

		

		txtQuantidadeSaida = new JTextField();
		txtQuantidadeSaida.setBounds(lblQuantidadeSaida.getX()
				+ lblQuantidadeSaida.getWidth(), lblQuantidadeSaida.getY(),
				100, 30);
		txtQuantidadeSaida.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 16));
		add(txtQuantidadeSaida);

		

		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch (Exception ex){
			ex.printStackTrace();
		}
		jcComboMotivo = new JComboBox<Object>();
		jcComboMotivo
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		jcComboMotivo.setBounds(
				lblMotivoSaida.getX() + lblMotivoSaida.getWidth() + 5,
				lblMotivoSaida.getY(), 115, 30);
		add(jcComboMotivo);
		jcComboMotivo.addItem(" ");
		
		try{
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		}catch (Exception ex){
			ex.printStackTrace();
		}

		btnAddMotivo = new JButton("+");
		btnAddMotivo.setBackground(corOriginalBtn);
		btnAddMotivo.setForeground(Color.WHITE);
		btnAddMotivo.setFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
		btnAddMotivo.setBounds(jcComboMotivo.getX() + jcComboMotivo.getWidth()
				+ 5, jcComboMotivo.getY(), 45, 30);
		btnAddMotivo.addActionListener(this);
		btnAddMotivo.setToolTipText("Adicionar um novo motivo de Saida");
		btnAddMotivo.setEnabled(false);
		add(btnAddMotivo);
			
		
		
		
		

		lblInfo = new JLabel("<html><u>Mais informações</u></html>");
		lblInfo.setBounds(txtProduto.getX(),
				txtProduto.getY() + txtProduto.getHeight(), 130, 30);
		lblInfo.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		lblInfo.addMouseListener(this);
		add(lblInfo);
		lblInfo.setVisible(false);

		btnAddProduto = new JButton("+");
		btnAddProduto.setBackground(corOriginalBtn);
		btnAddProduto.setForeground(Color.WHITE);
		btnAddProduto.setFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
		btnAddProduto.setBounds(txtProduto.getX() + txtProduto.getWidth() + 5,
				lblProduto.getY(), 45, 30);
		btnAddProduto.addActionListener(this);
		btnAddProduto.setToolTipText("Adicionar um novo produto");
		btnAddProduto.setEnabled(false);
		add(btnAddProduto);

		lblTxtFoto = new JLabel("Foto:");
		lblTxtFoto.setBounds(lblProduto.getX(), lblDataSaida.getY() + 60, 230,
				30);
		lblTxtFoto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(lblTxtFoto);

		lblFoto = new JLabel("");
		lblFoto.setBounds(lblTxtFoto.getX(), lblTxtFoto.getY() + 23, 140, 100);
		lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
		lblFoto.setBorder(BorderFactory.createLineBorder(new Color(161, 0, 64)));
		lblFoto.addMouseListener(this);
		add(lblFoto);

		lblNomeProduto = new JLabel("Nome:");
		lblNomeProduto.setBounds(lblFoto.getX() + 150, lblFoto.getY(), 230, 30);
		lblNomeProduto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		add(lblNomeProduto);

		lblTxtNomeProduto = new JLabel();
		lblTxtNomeProduto.setBounds(lblNomeProduto.getX() + 60,
				lblNomeProduto.getY(), 150, 30);
		lblTxtNomeProduto.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblTxtNomeProduto);

		lblDescricaoProduto = new JLabel("Descrição:");
		lblDescricaoProduto.setBounds(lblNomeProduto.getX(),
				lblNomeProduto.getY() + 30, 230, 30);
		lblDescricaoProduto.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblDescricaoProduto);

		lblTxtDescricaoProduto = new JLabel();
		lblTxtDescricaoProduto.setBounds(lblDescricaoProduto.getX() + 80,
				lblNomeProduto.getY() + 30, 130, 30);
		lblTxtDescricaoProduto.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblTxtDescricaoProduto);

		lblFabricanteProduto = new JLabel("Fabricante:");
		lblFabricanteProduto.setBounds(lblNomeProduto.getX(),
				lblDescricaoProduto.getY() + 30, 230, 30);
		lblFabricanteProduto.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblFabricanteProduto);

		lblTxtFabricanteProduto = new JLabel();
		lblTxtFabricanteProduto.setBounds(lblFabricanteProduto.getX() + 83,
				lblDescricaoProduto.getY() + 30, 140, 30);
		lblTxtFabricanteProduto.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblTxtFabricanteProduto);

		lblQuantidadeMinimaProduto = new JLabel("Quantidade Minima:");
		lblQuantidadeMinimaProduto.setBounds(490, lblNomeProduto.getY(), 230,
				30);
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
		lblValorProduto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		add(lblValorProduto);

		lblTxtValorProduto = new JLabel();
		lblTxtValorProduto.setBounds(540, lblValorProduto.getY(), 150, 30);
		lblTxtValorProduto.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblTxtValorProduto);

		lblInfoCamposObrigatorios = new JLabel("* Campos Obrigatórios");
		lblInfoCamposObrigatorios.setBounds(160, 375, 160, 30);
		lblInfoCamposObrigatorios.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblInfoCamposObrigatorios);

		lblNovaSaidaProduto = new JLabel(new ImageIcon(
				"Img/Saída de Produto/Nova.png"));
		// lblNovaEntradaProduto.setOpaque(true);
		// lblNovaEntradaProduto.setBackground(Color.GREEN);
		lblNovaSaidaProduto.setBounds(738, 52, 53, 57);
		lblNovaSaidaProduto.addMouseListener(this);
		lblNovaSaidaProduto.setToolTipText("Nova");
		add(lblNovaSaidaProduto);

		lblSalvarSaidaProduto = new JLabel(new ImageIcon(
				"Img/Saída de Produto/Salvar.png"));
		// lblSalvarEntradaProduto.setOpaque(true);
		// lblSalvarEntradaProduto.setBackground(Color.GREEN);
		lblSalvarSaidaProduto.setBounds(lblNovaSaidaProduto.getX(),
				lblNovaSaidaProduto.getY() + 70, 53, 57);
		lblSalvarSaidaProduto.addMouseListener(this);
		lblSalvarSaidaProduto.setToolTipText("Salvar");
		lblSalvarSaidaProduto.setEnabled(false);
		add(lblSalvarSaidaProduto);

		lblEditarSaidaProduto = new JLabel(new ImageIcon(
				"Img/Saída de Produto/Editar.png"));
		// lblEditarEntradaProduto.setOpaque(true);
		// lblEditarEntradaProduto.setBackground(Color.GREEN);
		lblEditarSaidaProduto.setBounds(lblSalvarSaidaProduto.getX(),
				lblSalvarSaidaProduto.getY() + 70, 53, 57);
		lblEditarSaidaProduto.addMouseListener(this);
		lblEditarSaidaProduto.setToolTipText("Editar");
		lblEditarSaidaProduto.setEnabled(false);
		add(lblEditarSaidaProduto);

		lblCancelar = new JLabel(new ImageIcon("Img/Cancelar.png"));
		// lblCancelar.setOpaque(true);
		// lblCancelar.setBackground(Color.GREEN);
		lblCancelar.setBounds(lblEditarSaidaProduto.getX() - 5,
				lblEditarSaidaProduto.getY() + 70, 61, 52);
		lblCancelar.addMouseListener(this);
		lblCancelar.setToolTipText("Cancelar");
		add(lblCancelar);

		lblRelatorioSaidaProduto = new JLabel(
				new ImageIcon("Img/Relatorio.png"));
		// lblRelatorioEntradaProduto.setOpaque(true);
		// lblRelatorioEntradaProduto.setBackground(Color.GREEN);
		lblRelatorioSaidaProduto.setBounds(lblCancelar.getX() + 8,
				lblCancelar.getY() + 70, 44, 50);
		lblRelatorioSaidaProduto.addMouseListener(this);
		lblRelatorioSaidaProduto.setToolTipText("Relatório");
		add(lblRelatorioSaidaProduto);

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
		btnPrimLinha.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
		btnPrimLinha.setToolTipText("Primeira linha");
		btnPrimLinha.setBounds(340, 370, 53, 40);
		add(btnPrimLinha);
		btnPrimLinha.addActionListener(this);

		btnVoltar = new JButton("<");
		btnVoltar.setBackground(corOriginalBtn);
		btnVoltar.setForeground(Color.WHITE);
		btnVoltar.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
		btnVoltar.setBounds(btnPrimLinha.getX() + 55, btnPrimLinha.getY(),
				btnPrimLinha.getWidth() - 10, btnPrimLinha.getHeight());
		btnVoltar.setToolTipText("Voltar uma linha");
		add(btnVoltar);
		btnVoltar.addActionListener(this);

		btnAvancar = new JButton(">");
		btnAvancar.setBackground(corOriginalBtn);
		btnAvancar.setForeground(Color.WHITE);
		btnAvancar.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
		btnAvancar.setBounds(btnVoltar.getX() + 55, btnVoltar.getY(),
				btnVoltar.getWidth(), btnVoltar.getHeight());
		btnAvancar.setToolTipText("Avançar linha");
		add(btnAvancar);
		btnAvancar.addActionListener(this);

		btnUltLinha = new JButton(">>");
		btnUltLinha.setBackground(corOriginalBtn);
		btnUltLinha.setForeground(Color.WHITE);
		btnUltLinha.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
		btnUltLinha.setBounds(btnAvancar.getX() + 45, btnAvancar.getY(),
				btnPrimLinha.getWidth(), btnAvancar.getHeight());

		btnUltLinha.setToolTipText("Ultima linha");
		add(btnUltLinha);
		btnUltLinha.addActionListener(this);

		camposFalse();
		
		mskDataSaida.addKeyListener(this);
		lblEditarSaidaProduto.setEnabled(false);
		// btnExcluir.setEnabled(false);
		lblNovaSaidaProduto.setEnabled(true);

		lblSalvarSaidaProduto.setEnabled(false);
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

		modelo.addColumn("Data");
		modelo.addColumn("Quantidade");
		modelo.addColumn("Motivo");
		modelo.addColumn("Produto");

		preencherJtable(modelo);

		tabela.getColumnModel().getColumn(0).setPreferredWidth(140);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(140);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(250);
		tabela.getColumnModel().getColumn(3).setPreferredWidth(250);
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

	public JComboBox<Object> getJcComboMotivo() {
		return jcComboMotivo;
	}

	public void setJcComboMotivo(JComboBox<Object> jcComboMotivo) {
		this.jcComboMotivo = jcComboMotivo;
	}

	public void preencherJtable(DefaultTableModel modelo) {
		modelo.setNumRows(0);

		for (SaidaProduto s : spController.listar()) {
			modelo.addRow(new Object[] { s.getDataSaidaProduto(),
					s.getQuantidadeSaidaProduto(), s.getMotivo(),
					s.getNomeProduto(), });

		}

	}

	public void pesquisaJtable(DefaultTableModel modelo) {
		modelo.setNumRows(0);
		if (pController.achou(txtProduto.getText().trim()) != null) {
			for (SaidaProduto s : spController.achou(txtProduto.getText().trim())) {
				modelo.addRow(new Object[] { s.getDataSaidaProduto(),
						s.getQuantidadeSaidaProduto(), s.getMotivo(),
						s.getNomeProduto(), });

			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(btnAddMotivo)) {
			new JanelaMotivoSaidaProduto(this);

		}
		if (e.getSource().equals(btnAddProduto)) {
			this.setVisible(false);
			// new JanelaProduto(null, this);
		}

		if (e.getSource() == btnPrimLinha) {
			lblNovaSaidaProduto.setEnabled(false);
			tabela.setRowSelectionInterval(0, 0);
			lblEditarSaidaProduto.setEnabled(true);
			// btnExcluir.setEnabled(true);
			dadosTabela();
			tabela.grabFocus();
		}
		if (e.getSource() == btnUltLinha) {
			lblNovaSaidaProduto.setEnabled(false);
			tabela.setRowSelectionInterval(tabela.getModel().getRowCount() - 1,
					tabela.getModel().getRowCount() - 1);
			lblEditarSaidaProduto.setEnabled(true);
			// btnExcluir.setEnabled(true);

			dadosTabela();
			tabela.grabFocus();
		}
		if (e.getSource() == btnAvancar) {
			if (tabela.getSelectedRow() < tabela.getModel().getRowCount() - 1) {
				lblNovaSaidaProduto.setEnabled(false);
				tabela.setRowSelectionInterval(tabela.getSelectedRow() + 1,
						tabela.getSelectedRow() + 1);
				lblEditarSaidaProduto.setEnabled(true);
				// btnExcluir.setEnabled(true);
				dadosTabela();
				tabela.grabFocus();
			}
		}
		if (e.getSource() == btnVoltar) {
			lblNovaSaidaProduto.setEnabled(false);
			if (tabela.getSelectedRow() > 0) {
				tabela.setRowSelectionInterval(tabela.getSelectedRow() - 1,
						tabela.getSelectedRow() - 1);
				lblEditarSaidaProduto.setEnabled(true);
				// btnExcluir.setEnabled(true);
				dadosTabela();
				tabela.grabFocus();
			} else {
				tabela.clearSelection();
				lblEditarSaidaProduto.setEnabled(false);
				// btnExcluir.setEnabled(false);
				limpaCampos();
			}
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

		if (e.getSource().equals(lblCancelar) && lblCancelar.isEnabled()) {

			limpaCampos();

			camposFalse();

			lblEditarSaidaProduto.setEnabled(false);
			// btnExcluir.setEnabled(false);
			lblNovaSaidaProduto.setEnabled(true);

			lblSalvarSaidaProduto.setEnabled(false);
			txtBtn = "";

			pesquisa();
			tabela.setEnabled(true);
			preencherJtable(modelo);

			txtProduto.grabFocus();
		}

		if (e.getSource().equals(lblNovaSaidaProduto)
				&& lblNovaSaidaProduto.isEnabled()) {
			if(e.getSource()==lblFoto && lblFoto.isEnabled() && !localFoto.equals("")){
				new JanelaVisualizarImagem(this);
			}
			
			camposTrue();
			tabela.clearSelection();
			lblSalvarSaidaProduto.setEnabled(true);

			// btnExcluir.setEnabled(false);
			lblEditarSaidaProduto.setEnabled(false);
			lblNovaSaidaProduto.setEnabled(false);
			txtBtn = "Novo";
			tabela.setEnabled(false);
			if (txtProduto.getText().trim().length() < 1)
				txtProduto.grabFocus();
		}

		if (e.getSource().equals(lblSalvarSaidaProduto)
				&& lblSalvarSaidaProduto.isEnabled()) {

			if (txtBtn.equals("Novo")) {
				if (cadastrar()) {
					txtBtn = "";
					limpaCampos();
					camposFalse();
					lblEditarSaidaProduto.setEnabled(false);
					// btnExcluir.setEnabled(false);
					lblNovaSaidaProduto.setEnabled(true);
					lblSalvarSaidaProduto.setEnabled(false);
					tabela.getSelectionModel().clearSelection();
					tabela.setEnabled(true);
					preencherJtable(modelo);
					txtProduto.grabFocus();
					tabela.setEnabled(true);
				} else {

				}
			} else {
				if (editar()) {
					txtBtn = "";
					limpaCampos();
					camposFalse();
					lblEditarSaidaProduto.setEnabled(false);
					// btnExcluir.setEnabled(false);
					lblNovaSaidaProduto.setEnabled(true);

					lblSalvarSaidaProduto.setEnabled(false);
					tabela.getSelectionModel().clearSelection();
					tabela.setEnabled(true);
					preencherJtable(modelo);
					txtProduto.grabFocus();

					tabela.setEnabled(true);

				}

			}
		}
		if (e.getSource().equals(lblEditarSaidaProduto)
				&& lblEditarSaidaProduto.isEnabled()) {
			definirValor();
			if (tabela.getSelectedRow() != -1) {
				camposTrue();

				lblSalvarSaidaProduto.setEnabled(true);
				// setando enable = false nos botoes novo, editar e excluir
				lblNovaSaidaProduto.setEnabled(false);
				lblEditarSaidaProduto.setEnabled(false);
				// btnExcluir.setEnabled(false);

				pegarCodigo();
				dadosTabela();

				produto = pController.codigoBarras(produto);
				if (produto.getId() != 0 && produto.getAtividadeProduto() == 1) {
					lblInfo.setVisible(true);
				}
				txtBtn = "Editar";

				tabela.setEnabled(false);
				tabela.getSelectionModel().clearSelection();

				txtProduto.grabFocus();

			} else {
				JOptionPane.showMessageDialog(null, "Selecione uma Saída");
			}
		}
		if (e.getSource().equals(lblRelatorioSaidaProduto)
				&& lblRelatorioSaidaProduto.isEnabled()) {

		}
		if (e.getSource().equals(lblInfo)
				&& !lblInfo.getText().equals("Produto não cadastrado")) {
			setCamposDadosProduto();
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource().equals(lblNovaSaidaProduto) && lblNovaSaidaProduto.isEnabled()){
			lblNovaSaidaProduto.setCursor(new Cursor (Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblEditarSaidaProduto) && lblEditarSaidaProduto.isEnabled()){
			lblEditarSaidaProduto.setCursor(new Cursor (Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblSalvarSaidaProduto) && lblSalvarSaidaProduto.isEnabled()){
			lblSalvarSaidaProduto.setCursor(new Cursor (Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblCancelar) && lblCancelar.isEnabled()){
			lblCancelar.setCursor(new Cursor (Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblRelatorioSaidaProduto) && lblRelatorioSaidaProduto.
				isEnabled()){
			lblRelatorioSaidaProduto.setCursor(new Cursor (Cursor.HAND_CURSOR));
		}

	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource().equals(lblNovaSaidaProduto) && lblNovaSaidaProduto.isEnabled()){
			lblNovaSaidaProduto.setCursor(new Cursor (Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource().equals(lblEditarSaidaProduto) && lblEditarSaidaProduto.isEnabled()){
			lblEditarSaidaProduto.setCursor(new Cursor (Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource().equals(lblSalvarSaidaProduto) && lblSalvarSaidaProduto.isEnabled()){
			lblSalvarSaidaProduto.setCursor(new Cursor (Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource().equals(lblCancelar) && lblCancelar.isEnabled()){
			lblCancelar.setCursor(new Cursor (Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource().equals(lblRelatorioSaidaProduto) && lblRelatorioSaidaProduto.
				isEnabled()){
			lblRelatorioSaidaProduto.setCursor(new Cursor (Cursor.DEFAULT_CURSOR));
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
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (tabela.getSelectedRow() != -1) {
			dadosTabela();
			lblNovaSaidaProduto.setEnabled(false);
			lblEditarSaidaProduto.setEnabled(true);
			// btnExcluir.setEnabled(true);
		} else {
			lblEditarSaidaProduto.setEnabled(false);
			// btnExcluir.setEnabled(false);
		}
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
		if (mskDataSaida.hasFocus()
				&& mskDataSaida.getText().replace("_", "").length() == 10) {
			if (validaDataEntrada(mskDataSaida.getText())) {
				lblImgInfoData1.setIcon(new ImageIcon("Img/success.png"));
			} else {
				lblImgInfoData1.setIcon(new ImageIcon("Img/error.png"));
			}
		} else {
			lblImgInfoData1.setIcon(null);
		}

		if (txtProduto.hasFocus() && txtProduto.getText().trim().length() > 0) {
			produto.setCodBarras(txtProduto.getText());
			produto = pController.codigoBarras(produto);
			if (produto.getId() != 0 && produto.getAtividadeProduto() == 1) {

				System.out.println(produto.getAtividadeProduto() + " "
						+ produto.getId());
				setCamposDadosProduto();
			} else {
				limpaCamposProduto();
			}

		} else {
			limpaCamposProduto();

			
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	private void limpaCampos() {
		txtQuantidadeSaida.setText("");

		mskDataSaida.setText("");
		jcComboMotivo.setSelectedIndex(0);

		txtProduto.setText("");
		lblImgInfoData1.setIcon(null);

		lblInfo.setVisible(false);
		limpaCamposProduto();

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

	private void camposTrue() {
		txtQuantidadeSaida.setEnabled(true);

		jcComboMotivo.setEnabled(true);

		txtProduto.setEnabled(true);
		mskDataSaida.setEnabled(true);
		btnAddMotivo.setEnabled(true);
		btnAddProduto.setEnabled(true);
		btnPrimLinha.setEnabled(false);
		btnVoltar.setEnabled(false);
		btnAvancar.setEnabled(false);
		btnUltLinha.setEnabled(false);
		lblRelatorioSaidaProduto.setEnabled(false);
	}

	private void camposFalse() {
		txtQuantidadeSaida.setEnabled(false);
		mskDataSaida.setEnabled(false);
		
		jcComboMotivo.setEnabled(false);
		txtProduto.setEnabled(true);
		btnAddMotivo.setEnabled(false);
		btnAddProduto.setEnabled(false);
		btnPrimLinha.setEnabled(true);
		btnVoltar.setEnabled(true);
		btnAvancar.setEnabled(true);
		btnUltLinha.setEnabled(true);
		verificaRelatorio();
	}

	public void dadosTabela() {
		if (tabela.getSelectedRow() != -1) {

			String motivo = (String) tabela.getValueAt(tabela.getSelectedRow(),
					2);
			for (int i = 0; i < jcComboMotivo.getItemCount(); i++) {
				String l = jcComboMotivo.getItemAt(i).toString();
				if (motivo.equals(l)) {
					MotivoSaida m = (MotivoSaida) jcComboMotivo.getItemAt(i);
					jcComboMotivo.setSelectedItem(m);
				}
			}
			txtQuantidadeSaida.setText(tabela.getValueAt(
					tabela.getSelectedRow(), 1).toString());

			mskDataSaida.setText(tabela.getValueAt(tabela.getSelectedRow(), 0)
					.toString());

			txtProduto.setText(tabela.getValueAt(tabela.getSelectedRow(), 3)
					.toString());
			produto.setCodBarras(tabela.getValueAt(tabela.getSelectedRow(), 3)
					.toString());
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
			dataEntrada = formataData.parse(mskDataSaida.getText());
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
			if (!validaDataEntrada(mskDataSaida.getText())
					|| txtQuantidadeSaida.getText().length() < 1

					|| txtProduto.getText().trim().length() <= 0
					|| jcComboMotivo.getSelectedIndex() <= 0

					|| mskDataSaida.getText().replaceAll("[_ /]", "").length() != 8
					|| txtQuantidadeSaida.getText().length() < 1) {
				JOptionPane.showMessageDialog(null,
						"Preencha os campos corretamente");

				lblNovaSaidaProduto.setEnabled(true);
				return false;
			} else {
				if (produto.getId() != 0) {

					MotivoSaida m = (MotivoSaida) jcComboMotivo
							.getSelectedItem();

					SaidaProduto sp = new SaidaProduto();
					Estoque e = new Estoque();

					sp.setQuantidadeSaidaProduto(Integer
							.parseInt(txtQuantidadeSaida.getText().trim()));
					sp.setCodMotivoSaidaProduto(m.getCodMotivoSaida());
					sp.setDataSaidaProduto(mskDataSaida.getText());

					sp.setMotivo(m.getDescricaoMotivoSaida());

					sp.setCodProduto(produto.getId());

					e.setCodProduto(sp.getCodProduto());

					if (eController.conferirEstoque(e)) {
						if (eController.pegarEstoque(e) < sp
								.getQuantidadeSaidaProduto()) {
							e.setQuantidadeEstoqueProduto(0);
							sp.setQuantidadeSaidaProduto(eController
									.pegarEstoque(e));
						} else {
							e.setQuantidadeEstoqueProduto(eController
									.pegarEstoque(e)
									- sp.getQuantidadeSaidaProduto());
						}

					} else {

						JOptionPane
								.showMessageDialog(null,
										"Você precisa fazer uma entrada deste produto, anter de efetuar uma saida");
						return false;
					}
					if (spController.cadastrar(sp)) {
						preencherJtable(modelo);
						JOptionPane.showMessageDialog(null,
								"Saida Cadastrada com sucesso \n");
						eController.acaoEstoque(e);
						produto.setId(0);
						lblInfo.setVisible(false);
						return true;
					} else {
						JOptionPane.showMessageDialog(null, "deu ruim ");
						lblNovaSaidaProduto.setEnabled(true);
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
			lblNovaSaidaProduto.setEnabled(true);
			return false;
		}
	}

	public boolean editar() {
		try {
			if (!validaDataEntrada(mskDataSaida.getText())
					

					|| txtProduto.getText().trim().length() <= 0
					|| jcComboMotivo.getSelectedIndex() <= 0

					|| mskDataSaida.getText().replaceAll("[_ /]", "").length() != 8
					|| txtQuantidadeSaida.getText().trim().length() < 1) {
				JOptionPane.showMessageDialog(null,
						"Preencha os campos corretamente");

				lblNovaSaidaProduto.setEnabled(true);
				return false;
			} else {
				if (produto.getId() != 0 && produto.getAtividadeProduto() == 1) {

					MotivoSaida m = (MotivoSaida) jcComboMotivo
							.getSelectedItem();

					SaidaProduto ep = new SaidaProduto();
					Estoque e = new Estoque();
					ep.setQuantidadeSaidaProduto(Integer
							.parseInt(txtQuantidadeSaida.getText().trim()));
					ep.setCodMotivoSaidaProduto(m.getCodMotivoSaida());
					ep.setDataSaidaProduto(mskDataSaida.getText());

					ep.setMotivo(m.getDescricaoMotivoSaida());

					ep.setCodProduto(produto.getId());
					e.setCodProduto(ep.getCodProduto());

					// if (eController.conferirEstoque(e)){
					// e.setQuantidadeEstoqueProduto(eController.pegarEstoque(e)+ep.getQuantidadeEntradaProduto());
					// }else{
					//
					// e.setQuantidadeEstoqueProduto(ep.getQuantidadeEntradaProduto());
					//
					// }
					e.setQuantidadeEstoqueProduto(eController.pegarEstoque(e)
							- ep.getQuantidadeSaidaProduto());
					SaidaProduto aux = spController.find(codigo);

					if (e.getCodProduto() == aux.getCodProduto()) {

						if (quantidadeSaida > ep.getQuantidadeSaidaProduto()) {
							System.out.println("Entrou no primeiro if");

							int resto = quantidadeSaida
									- ep.getQuantidadeSaidaProduto();
							e.setQuantidadeEstoqueProduto(eController
									.pegarEstoque(e) + resto);
							// esse resto e o que preciso subtrair do estoque

							// eController.acaoEstoque(e);

							eController.acaoEstoque(e);
						}

						if (quantidadeSaida < ep.getQuantidadeSaidaProduto()) {
							System.out.println("Entrou no segundo if");
							e.setQuantidadeEstoqueProduto(eController
									.pegarEstoque(e)
									- ep.getQuantidadeSaidaProduto()
									+ quantidadeSaida);

							if (eController.pegarEstoque(e) < ep
									.getQuantidadeSaidaProduto()) {
								System.out.println("Entrou no terceiro if");
								ep.setQuantidadeSaidaProduto(eController
										.pegarEstoque(e));
								e.setQuantidadeEstoqueProduto(0);
							}

							eController.acaoEstoque(e);

						}

					} else {

						if (eController.conferirEstoque(e)) {

							Estoque auxEstoque = new Estoque();
							auxEstoque.setCodProduto(aux.getCodProduto());
							if (eController.pegarEstoque(auxEstoque) < ep
									.getQuantidadeSaidaProduto()) {
								auxEstoque.setQuantidadeEstoqueProduto(0);
							} else {

								auxEstoque
										.setQuantidadeEstoqueProduto(eController
												.pegarEstoque(auxEstoque)
												- ep.getQuantidadeSaidaProduto());
							}

							eController.acaoEstoque(auxEstoque);
							// }else{

						} else {
							JOptionPane
									.showMessageDialog(null,
											"Você precisa fazer uma entrada deste produto, anter de efetuar uma saida");
							return false;
						}
					}

					if (spController.atualizar(ep, codigo)) {
						preencherJtable(modelo);
						JOptionPane.showMessageDialog(null,
								"Saida Editada com sucesso \n");

						return true;
					} else {
						JOptionPane.showMessageDialog(null, "deu ruim ");
						lblNovaSaidaProduto.setEnabled(true);
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
			lblNovaSaidaProduto.setEnabled(true);
			return false;
		}
	}

	public void pesquisa() {
		if (txtProduto.getText().trim().length()>0) {

			pesquisaJtable(modelo);
			saida = spController.achou(txtProduto.getText().trim());

		} else {
			preencherJtable(modelo);
			saida = spController.listar();

		}
	}

	public void definirValor() {
		saida = spController.listar();

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
			lblRelatorioSaidaProduto.setEnabled(false);
			return false;
		}
		lblRelatorioSaidaProduto.setEnabled(true);
		return true;
	}

}
