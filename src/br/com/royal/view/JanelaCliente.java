package br.com.royal.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.ScrollPane;
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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import org.opencv.core.Mat;

import br.com.royal.controller.BuscaCep;
import br.com.royal.controller.ClienteController;
import br.com.royal.controller.Validacoes;
import br.com.royal.controllerReports.GerarRelatorioCliente;
import br.com.royal.model.Cliente;
import br.com.royal.model.Telefones;
import br.com.royal.modelReports.RelatorioClientes;

public class JanelaCliente extends JFrame implements ActionListener,
		KeyListener, MouseListener, MouseMotionListener, WindowListener,
		FocusListener {
	private JanelaHome jh;
	private JanelaAgendamento ja;
	public JTextField txtNomeCliente, txtRgCliente, txtLogradouroCliente,
			txtBairroCliente, txtCidadeCliente, txtComplementoCliente,
			txtNumCliente, txtEmailCliente;
	private JFormattedTextField txtDataNascCliente, txtCpfCliente,
			txtCepCliente, txtTelefone;
	private MaskFormatter mascaraData, mascaraCpf, mascaraCep, mascaraTel;
	private JComboBox<String> cmbSexoCliente, cmbEstadoCliente;
	private int posX, posY;
	private JLabel lblFundo, lblNavbar, lblMinimize, lblClose, lblNomeCliente,
			lblDataNascCliente, lblRgCliente, lblCpfCliente, lblSexoCliente,
			lblLogradouroCliente, lblNumCliente, lblCepCliente,
			lblBairroCliente, lblCidadeCliente, lblComplementoCliente,
			lblEstadoCliente, lblTelefone, lblInfoCamposObrigatorios,
			lblInfoCpf, lblImgInfoCpf, lblImgInfoData, lblNovoCliente,
			lblSalvarCliente, lblEditarCliente, lblExcluirCliente, lblCancelar,
			lblEmailCliente, lblImgInfoRg, lblImgInfoEmail, lblInfoEmail,
			lblRelatorioCliente, lblFotoCliente, lblInfoFoto;
	public JLabel fotoCliente, lblCamera, lblProcuraImagem, lblRemoveImagem;

	public String localFoto = "";
	private JButton btnAddTel, btnRemTel, btnAvancar, btnVoltar, btnPrimLinha,
			btnUltLinha;
	private JList<String> lstTelefone;
	private JTable tblListar;
	private FTable modelT = new FTable();
	private Validacoes validacao = new Validacoes();
	private DefaultListModel<String> model = new DefaultListModel<String>();
	private Color corOriginalBtn = new Color(164, 6, 69);
	private String txtBtn = "", aux = "", caminho = "", localImagemEdicao = "",
			arquivo = "";
	private int cod = 0;
	private ArrayList<Cliente> listaAtualTabela = new ArrayList<Cliente>();
	public BufferedImage imagem;
	public Mat mat;

	public JanelaCliente(JanelaHome jh, JanelaAgendamento ja) {
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}
		this.ja = ja;
		this.jh = jh;
		setTitle("Cadastro de Cliente");
		setSize(1080, 700);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		addWindowListener(this);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setUndecorated(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Img/Rosa.png"));
		IC();

		setVisible(true);
	}

	private void IC() {
		criaTabela();
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}
		lblNomeCliente = new JLabel("* Nome:");
		lblNomeCliente.setBounds(140, 75, 100, 30);
		lblNomeCliente.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		add(lblNomeCliente);

		txtNomeCliente = new JTextFieldLimitada(70);
		txtNomeCliente.setBounds(lblNomeCliente.getX(),
				lblNomeCliente.getY() + 30, 400, 30);
		txtNomeCliente.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		txtNomeCliente.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				12));
		add(txtNomeCliente);
		txtNomeCliente.addKeyListener(this);

		lblCpfCliente = new JLabel("CPF:");
		lblCpfCliente.setBounds(lblNomeCliente.getX(),
				txtNomeCliente.getY() + 40, 100, 30);
		lblCpfCliente
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(lblCpfCliente);

		txtCpfCliente = new JFormattedTextField();
		txtCpfCliente.setBounds(lblCpfCliente.getX(),
				lblCpfCliente.getY() + 30, 100, 30);
		txtCpfCliente.addKeyListener(this);
		txtCpfCliente.setEnabled(false);
		txtCpfCliente
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		add(txtCpfCliente);
		txtCpfCliente.addFocusListener(this);
		try {
			mascaraCpf = new MaskFormatter("###.###.###-##");
			mascaraCpf.setPlaceholderCharacter('_');
			mascaraCpf.install(txtCpfCliente);
		} catch (Exception ex) {
		}

		lblImgInfoCpf = new JLabel();
		lblImgInfoCpf.setBounds(txtCpfCliente.getX() + 105,
				txtCpfCliente.getY() + 5, 20, 20);
		add(lblImgInfoCpf);

		lblInfoCpf = new JLabel();
		lblInfoCpf.setBounds(txtCpfCliente.getX(), txtCpfCliente.getY() + 25,
				260, 30);
		lblInfoCpf.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		lblInfoCpf.setForeground(Color.RED);
		add(lblInfoCpf);

		lblRgCliente = new JLabel("RG:");
		lblRgCliente.setBounds(lblCpfCliente.getX() + 130,
				lblCpfCliente.getY(), 100, 30);
		lblRgCliente
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(lblRgCliente);

		txtRgCliente = new JTextFieldLimitada(20);
		txtRgCliente.setBounds(lblRgCliente.getX(), lblRgCliente.getY() + 30,
				100, 30);
		txtRgCliente.setEnabled(false);
		txtRgCliente
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		add(txtRgCliente);
		txtRgCliente.addKeyListener(this);

		lblImgInfoRg = new JLabel();
		lblImgInfoRg.setBounds(txtRgCliente.getX() + 104,
				txtRgCliente.getY() + 5, 20, 20);
		add(lblImgInfoRg);

		lblDataNascCliente = new JLabel("Nascimento");
		lblDataNascCliente.setBounds(lblRgCliente.getX() + 120,
				lblCpfCliente.getY(), 150, 30);
		lblDataNascCliente.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblDataNascCliente);

		txtDataNascCliente = new JFormattedTextField();
		txtDataNascCliente.setBounds(lblDataNascCliente.getX(),
				lblDataNascCliente.getY() + 30, 100, 30);
		txtDataNascCliente.setEnabled(false);
		txtDataNascCliente.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 12));
		add(txtDataNascCliente);
		txtDataNascCliente.addKeyListener(this);
		txtDataNascCliente.addFocusListener(this);
		try {
			mascaraData = new MaskFormatter("##/##/####");
			mascaraData.setPlaceholderCharacter('_');
			mascaraData.install(txtDataNascCliente);
		} catch (Exception ex) {
		}

		lblImgInfoData = new JLabel();
		lblImgInfoData.setBounds(txtDataNascCliente.getX() + 105,
				txtDataNascCliente.getY() + 5, 20, 20);
		add(lblImgInfoData);

		lblSexoCliente = new JLabel("Sexo:");
		lblSexoCliente.setBounds(520, lblCpfCliente.getY(), 100, 30);
		lblSexoCliente.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		add(lblSexoCliente);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
		}

		cmbSexoCliente = new JComboBox<String>();
		cmbSexoCliente.setBounds(lblSexoCliente.getX(),
				lblSexoCliente.getY() + 30, 100, 30);
		cmbSexoCliente.addItem("");
		cmbSexoCliente.addItem("Masculino");
		cmbSexoCliente.addItem("Feminino");
		cmbSexoCliente.setEnabled(false);
		cmbSexoCliente.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				12));
		add(cmbSexoCliente);

		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}

		lblEmailCliente = new JLabel("Email:");
		lblEmailCliente.setBounds(lblNomeCliente.getX(),
				txtCpfCliente.getY() + 40, 100, 30);
		lblEmailCliente.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		add(lblEmailCliente);

		txtEmailCliente = new JTextFieldLimitada(100);
		txtEmailCliente.setBounds(lblEmailCliente.getX(),
				lblEmailCliente.getY() + 30, 350, 30);
		txtEmailCliente.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				12));
		add(txtEmailCliente);
		txtEmailCliente.addKeyListener(this);
		txtEmailCliente.setEnabled(false);

		lblImgInfoEmail = new JLabel();
		lblImgInfoEmail.setBounds(
				txtEmailCliente.getX() + txtEmailCliente.getWidth() + 5,
				txtEmailCliente.getY() + 5, 20, 20);
		add(lblImgInfoEmail);

		lblInfoEmail = new JLabel();
		lblInfoEmail.setBounds(txtEmailCliente.getX(),
				txtEmailCliente.getY() + 25, 260, 30);
		lblInfoEmail
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		lblInfoEmail.setForeground(Color.RED);
		add(lblInfoEmail);

		lblCepCliente = new JLabel("CEP:");
		lblCepCliente.setBounds(lblSexoCliente.getX(), lblEmailCliente.getY(),
				80, 30);
		lblCepCliente
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(lblCepCliente);

		txtCepCliente = new JFormattedTextField();
		txtCepCliente.setBounds(lblCepCliente.getX(),
				lblCepCliente.getY() + 30, 90, 30);
		txtCepCliente
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		add(txtCepCliente);
		txtCepCliente.setEnabled(false);
		txtCepCliente.addKeyListener(this);
		txtCepCliente.addFocusListener(this);
		try {
			mascaraCep = new MaskFormatter("#####-###");
			mascaraCep.setPlaceholderCharacter('_');
			mascaraCep.install(txtCepCliente);
		} catch (Exception ex) {
		}

		lblLogradouroCliente = new JLabel("Logradouro:");
		lblLogradouroCliente.setBounds(lblNomeCliente.getX(),
				txtEmailCliente.getY() + 40, 100, 30);
		lblLogradouroCliente.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblLogradouroCliente);

		txtLogradouroCliente = new JTextFieldLimitada(30);
		txtLogradouroCliente.setBounds(lblLogradouroCliente.getX(),
				lblLogradouroCliente.getY() + 30, 250, 30);
		txtLogradouroCliente.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 12));
		add(txtLogradouroCliente);
		txtLogradouroCliente.setEnabled(false);

		lblNumCliente = new JLabel("Número:");
		lblNumCliente.setBounds(lblLogradouroCliente.getX()
				+ txtLogradouroCliente.getWidth() + 10,
				lblLogradouroCliente.getY(), 100, 30);
		lblNumCliente
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(lblNumCliente);

		txtNumCliente = new JTextFieldLimitada(20);
		txtNumCliente.setBounds(lblNumCliente.getX(),
				lblNumCliente.getY() + 30, 100, 30);
		txtNumCliente
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		add(txtNumCliente);
		txtNumCliente.setEnabled(false);

		lblBairroCliente = new JLabel("Bairro:");
		lblBairroCliente.setBounds(lblNumCliente.getX() + 115,
				lblLogradouroCliente.getY(), 80, 30);
		lblBairroCliente.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		add(lblBairroCliente);

		txtBairroCliente = new JTextField();
		txtBairroCliente.setBounds(lblBairroCliente.getX(),
				lblBairroCliente.getY() + 30, 150, 30);
		txtBairroCliente.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				12));
		add(txtBairroCliente);
		txtBairroCliente.setEnabled(false);

		lblCidadeCliente = new JLabel("Cidade:");
		lblCidadeCliente.setBounds(lblNomeCliente.getX(),
				txtLogradouroCliente.getY() + 40, 80, 30);
		lblCidadeCliente.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		add(lblCidadeCliente);

		txtCidadeCliente = new JTextField();
		txtCidadeCliente.setBounds(lblCidadeCliente.getX(),
				lblCidadeCliente.getY() + 30, txtLogradouroCliente.getWidth(),
				30);
		txtCidadeCliente.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				12));
		add(txtCidadeCliente);
		txtCidadeCliente.setEnabled(false);

		lblEstadoCliente = new JLabel("Estado:");
		lblEstadoCliente.setBounds(lblNumCliente.getX(),
				lblCidadeCliente.getY(), 80, 30);
		lblEstadoCliente.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		add(lblEstadoCliente);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
		}

		cmbEstadoCliente = new JComboBox<String>();
		cmbEstadoCliente.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				12));
		cmbEstadoCliente.setBounds(txtNumCliente.getX(),
				lblEstadoCliente.getY() + 30, txtNumCliente.getWidth(), 30);
		cmbEstadoCliente.addItem("");
		cmbEstadoCliente.addItem("AC");
		cmbEstadoCliente.addItem("AL");
		cmbEstadoCliente.addItem("AP");
		cmbEstadoCliente.addItem("AM");
		cmbEstadoCliente.addItem("BA");
		cmbEstadoCliente.addItem("CE");
		cmbEstadoCliente.addItem("DF");
		cmbEstadoCliente.addItem("ES");
		cmbEstadoCliente.addItem("GO");
		cmbEstadoCliente.addItem("MA");
		cmbEstadoCliente.addItem("MT");
		cmbEstadoCliente.addItem("MS");
		cmbEstadoCliente.addItem("MG");
		cmbEstadoCliente.addItem("PA");
		cmbEstadoCliente.addItem("PB");
		cmbEstadoCliente.addItem("PR");
		cmbEstadoCliente.addItem("PE");
		cmbEstadoCliente.addItem("PI");
		cmbEstadoCliente.addItem("RJ");
		cmbEstadoCliente.addItem("RN");
		cmbEstadoCliente.addItem("RO");
		cmbEstadoCliente.addItem("RR");
		cmbEstadoCliente.addItem("SC");
		cmbEstadoCliente.addItem("SP");
		cmbEstadoCliente.addItem("SE");
		cmbEstadoCliente.addItem("TO");
		cmbEstadoCliente.setEnabled(false);

		add(cmbEstadoCliente);

		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}

		lblComplementoCliente = new JLabel("Complemento:");
		lblComplementoCliente.setBounds(lblBairroCliente.getX(),
				lblEstadoCliente.getY(), 115, 30);
		lblComplementoCliente.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblComplementoCliente);

		txtComplementoCliente = new JTextField();
		txtComplementoCliente.setBounds(lblComplementoCliente.getX(),
				lblComplementoCliente.getY() + 30, txtBairroCliente.getWidth(),
				30);
		txtComplementoCliente.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 12));
		add(txtComplementoCliente);
		txtComplementoCliente.setEnabled(false);

		lblInfoCamposObrigatorios = new JLabel("* Campos Obrigatórios");
		lblInfoCamposObrigatorios.setBounds(110, 440, 160, 30);
		lblInfoCamposObrigatorios.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblInfoCamposObrigatorios);

		lblFotoCliente = new JLabel("Foto:");
		lblFotoCliente.setBounds(640, lblNomeCliente.getY(), 220, 30);
		lblFotoCliente.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		add(lblFotoCliente);

		fotoCliente = new JLabel();
		fotoCliente.setBounds(lblFotoCliente.getX(),
				lblFotoCliente.getY() + 30, 170, 128);
		add(fotoCliente);
		fotoCliente.setBorder(LineBorder.createBlackLineBorder());
		fotoCliente.addMouseListener(this);
		fotoCliente.addMouseMotionListener(this);
		
		lblInfoFoto = new JLabel();
		lblInfoFoto.setBounds(fotoCliente.getX(), fotoCliente.getY()
				+ fotoCliente.getHeight() - 8, 260, 30);
		lblInfoFoto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		lblInfoFoto.setForeground(Color.RED);
		add(lblInfoFoto);

		lblCamera = new JLabel(new ImageIcon(
				"Img/Foto do Cliente/Nova Foto de Cliente.png"));
		lblCamera.setBackground(corOriginalBtn);
		lblCamera.setForeground(Color.WHITE);
		lblCamera.setToolTipText("Tirar uma nova foto");
		lblCamera.setBounds(820, fotoCliente.getY() + 30, 34, 28);
		add(lblCamera);
		lblCamera.addMouseListener(this);
		lblCamera.setEnabled(false);

		lblProcuraImagem = new JLabel(new ImageIcon(
				"Img/Foto do Cliente/Selecionar Foto de Cliente.png"));
		lblProcuraImagem.setBackground(corOriginalBtn);
		lblProcuraImagem.setForeground(Color.WHITE);
		lblProcuraImagem.setToolTipText("Buscar uma foto existente");
		lblProcuraImagem.setBounds(820, lblCamera.getY() + 36, 28, 28);
		add(lblProcuraImagem);
		lblProcuraImagem.addMouseListener(this);
		lblProcuraImagem.setEnabled(false);

		lblRemoveImagem = new JLabel(new ImageIcon(
				"Img/Foto do Cliente/Remover Foto de Cliente.png"));
		lblRemoveImagem.setBackground(corOriginalBtn);
		lblRemoveImagem.setForeground(Color.WHITE);
		lblRemoveImagem.setToolTipText("Remover foto");
		lblRemoveImagem.setBounds(820, lblProcuraImagem.getY() + 36, 29, 29);
		add(lblRemoveImagem);
		lblRemoveImagem.addMouseListener(this);
		lblRemoveImagem.setEnabled(false);

		lblTelefone = new JLabel("* Telefone:");
		lblTelefone.setBounds(690, txtCepCliente.getY(), 100, 30);
		lblTelefone.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(lblTelefone);

		txtTelefone = new JFormattedTextField();
		txtTelefone.setBounds(lblTelefone.getX(), lblTelefone.getY() + 30, 130,
				30);
		txtTelefone.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		add(txtTelefone);
		txtTelefone.setEnabled(false);
		txtTelefone.addKeyListener(this);
		txtTelefone.addFocusListener(this);
		try {
			mascaraTel = new MaskFormatter("(##) ####-#####");
			mascaraTel.setPlaceholderCharacter('_');
			mascaraTel.install(txtTelefone);
		} catch (Exception ex) {
		}

		lstTelefone = new JList<String>();
		lstTelefone.setFocusable(false);
		ScrollPane scrollLst = new ScrollPane();
		scrollLst.add(lstTelefone);
		scrollLst.setBounds(txtTelefone.getX(), txtTelefone.getY() + 40, 130,
				100);
		add(scrollLst);

		btnAddTel = new JButton("+");
		btnAddTel.setBackground(corOriginalBtn);
		btnAddTel.setForeground(Color.WHITE);
		btnAddTel.setFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
		btnAddTel.setBounds(820, scrollLst.getY() + 44, 50, 27);
		btnAddTel.setToolTipText("Adicionar telefone");
		add(btnAddTel);
		btnAddTel.addActionListener(this);
		btnAddTel.setEnabled(false);
		btnAddTel.setFocusable(false);

		btnRemTel = new JButton("-");
		btnRemTel.setBackground(corOriginalBtn);
		btnRemTel.setForeground(Color.WHITE);
		btnRemTel.setFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
		btnRemTel.setBounds(btnAddTel.getX(), btnAddTel.getY() + 29, 50, 27);
		btnRemTel.setToolTipText("Remover telefone");
		btnRemTel.setFocusable(false);
		add(btnRemTel);
		btnRemTel.addActionListener(this);
		btnRemTel.setEnabled(false);

		btnPrimLinha = new JButton("<<");
		btnPrimLinha.setBackground(corOriginalBtn);
		btnPrimLinha.setForeground(Color.WHITE);
		btnPrimLinha.setFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
		btnPrimLinha.setBounds(420, 430, 52, 40);
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

		final JTextArea text = new JTextArea();
		text.setBounds(fotoCliente.getX(), fotoCliente.getY(),
				fotoCliente.getWidth(), fotoCliente.getHeight());
		text.setOpaque(false);
		text.setBackground(new Color(0, 0, 0, 0));
		text.setEditable(false);
		text.setFocusable(false);

		new FileDrop(System.out, text, new FileDrop.Listener() {
			public void filesDropped(java.io.File[] files) {
				for (int i = 0; i < files.length; i++) {
					try {
						aux = files[i].getName();
						String path = files[i].getCanonicalPath() + "\n";
						String teste = path.trim();
						if (teste.substring(teste.length() - 3).equals("jpg")
								|| teste.substring(teste.length() - 3).equals(
										"png")
								|| teste.substring(teste.length() - 3).equals(
										"pgm")) {

							if (lblNovoCliente.isEnabled() == false
									&& lblEditarCliente.isEnabled() == false) {
								fotoCliente.setIcon(new ImageIcon(teste));
								int j = 1;
								boolean bool = false;
								do {
									bool = false;
									for (Cliente c : ClienteController
											.getClientes()) {
										if (c.getFotoCliente().length() > 42) {
											if (c.getFotoCliente()
													.substring(42).equals(aux)) {
												String extensao = aux
														.substring(aux.length() - 4);
												String aux2 = "";
												if (j == 1)
													aux2 = aux.substring(0,
															aux.length() - 4);
												else
													aux2 = aux.substring(0,
															aux.length() - 5);

												aux2 += j + "";
												aux2 += extensao;
												aux = aux2;
												bool = true;
												j++;
												break;
											}
										}
									}
								} while (bool);

								try {
									arquivo = teste;
									localFoto = teste;
									imagem = setImagemDimensao(teste,
											fotoCliente.getWidth(),
											fotoCliente.getHeight());
									fotoCliente.setText("");
									fotoCliente.setIcon(new ImageIcon(imagem));

								} catch (Exception ex) {
									System.err
											.println("Quebrou no fileDropped: "
													+ ex.getMessage());
								}
								// jooj
							}

						}
					} // end try
					catch (java.io.IOException e) {
					}

				}
			}
		});
		add(text);

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

		lblNovoCliente = new JLabel(new ImageIcon("Img/Cliente/Novo.png"));
		// lblNovoCliente.setOpaque(true);
		// lblNovoCliente.setBackground(Color.GREEN);
		lblNovoCliente.setBounds(890, 75, 61, 52);
		lblNovoCliente.setToolTipText("Novo");
		lblNovoCliente.addMouseListener(this);
		add(lblNovoCliente);

		lblSalvarCliente = new JLabel(new ImageIcon("Img/Cliente/Salvar.png"));
		// lblSalvarCliente.setOpaque(true);
		// lblSalvarCliente.setBackground(Color.GREEN);

		lblSalvarCliente.setBounds(lblNovoCliente.getX(),
				lblNovoCliente.getY() + 65, 61, 52);
		lblSalvarCliente.setToolTipText("Salvar");
		lblSalvarCliente.addMouseListener(this);
		lblSalvarCliente.setEnabled(false);
		add(lblSalvarCliente);

		lblEditarCliente = new JLabel(new ImageIcon("Img/Cliente/Editar.png"));
		// lblEditarCliente.setOpaque(true);
		// lblEditarCliente.setBackground(Color.GREEN);
		lblEditarCliente.setBounds(lblSalvarCliente.getX(),
				lblSalvarCliente.getY() + 65, 61, 52);
		lblEditarCliente.setToolTipText("Editar");
		lblEditarCliente.addMouseListener(this);
		lblEditarCliente.setEnabled(false);
		add(lblEditarCliente);

		lblExcluirCliente = new JLabel(new ImageIcon("Img/Cliente/Remover.png"));
		// lblExcluirCliente.setOpaque(true);
		// lblExcluirCliente.setBackground(Color.GREEN);
		lblExcluirCliente.setBounds(lblEditarCliente.getX(),
				lblEditarCliente.getY() + 65, 61, 52);
		lblExcluirCliente.setToolTipText("Excluir");
		lblExcluirCliente.addMouseListener(this);
		lblExcluirCliente.setEnabled(false);
		add(lblExcluirCliente);

		lblCancelar = new JLabel(new ImageIcon("Img/Cancelar.png"));
		// lblCancelar.setOpaque(true);
		// lblCancelar.setBackground(Color.GREEN);
		lblCancelar.setBounds(lblSalvarCliente.getX() + 7,
				lblExcluirCliente.getY() + 65, 51, 51);
		lblCancelar.setToolTipText("Cancelar");
		lblCancelar.addMouseListener(this);
		add(lblCancelar);

		lblRelatorioCliente = new JLabel(new ImageIcon("Img/Relatorio.png"));
		lblRelatorioCliente.setBounds(lblCancelar.getX() + 5,
				lblCancelar.getY() + 70, 44, 50);
		lblRelatorioCliente.setToolTipText("Relatório");
		lblRelatorioCliente.addMouseListener(this);
		add(lblRelatorioCliente);

		lblNavbar = new JLabel();
		// lblNavbar.setOpaque(true);
		// lblNavbar.setBackground(Color.GREEN);
		lblNavbar.setBounds(0, 0, getWidth(), 41);
		lblNavbar.addMouseListener(this);
		lblNavbar.addMouseMotionListener(this);
		add(lblNavbar);

		lblFundo = new JLabel(
				new ImageIcon("Img/Gerenciamento de Clientes.png"));
		lblFundo.setBounds(0, 0, getWidth(), getHeight());
		add(lblFundo);
	}

	private void criaTabela() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
		}

		tblListar = new JTable(modelT);
		tblListar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblListar.getTableHeader().setResizingAllowed(false);
		tblListar.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		tblListar.setRowSelectionAllowed(true);
		tblListar.setSelectionBackground(new Color(222, 54, 121));
		tblListar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblListar.getTableHeader().setDefaultRenderer(new RTable());
		tblListar.addKeyListener(this);
		JScrollPane scroll = new JScrollPane(tblListar);
		// scroll.add(tblListar);
		scroll.setBounds(20, 480, 1040, 200);
		// scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		modelT.addColumn("Nome");
		modelT.addColumn("CPF");
		modelT.addColumn("RG");
		modelT.addColumn("Email");
		modelT.addColumn("Data de nascimento");
		modelT.addColumn("Sexo");
		modelT.addColumn("Logradouro");
		modelT.addColumn("Número");
		modelT.addColumn("CEP");
		modelT.addColumn("Bairro");
		modelT.addColumn("Cidade");
		modelT.addColumn("Estado");
		modelT.addColumn("Complemento");
		modelT.addColumn("Telefone");
		modelT.addColumn("Foto");
		atualizaTabela(modelT);

		tblListar.getColumnModel().getColumn(0).setPreferredWidth(250);
		tblListar.getColumnModel().getColumn(1).setPreferredWidth(105);
		tblListar.getColumnModel().getColumn(2).setPreferredWidth(100);
		tblListar.getColumnModel().getColumn(3).setPreferredWidth(210);
		tblListar.getColumnModel().getColumn(4).setPreferredWidth(150);
		tblListar.getColumnModel().getColumn(5).setPreferredWidth(60);
		tblListar.getColumnModel().getColumn(6).setPreferredWidth(250);
		tblListar.getColumnModel().getColumn(7).setPreferredWidth(80);
		tblListar.getColumnModel().getColumn(8).setPreferredWidth(80);
		tblListar.getColumnModel().getColumn(9).setPreferredWidth(100);
		tblListar.getColumnModel().getColumn(10).setPreferredWidth(100);
		tblListar.getColumnModel().getColumn(11).setPreferredWidth(60);
		tblListar.getColumnModel().getColumn(12).setPreferredWidth(100);
		tblListar.getColumnModel().getColumn(13).setPreferredWidth(120);
		tblListar.getColumnModel().getColumn(14).setPreferredWidth(200);
		tblListar.getTableHeader().setReorderingAllowed(false);
		add(scroll);
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
			if (caminhoImg.length() > 30)
				lblInfoFoto.setText("Não foi possivel encontrar a imagem");
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
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAddTel) {
			addTelefone();
		}

		if (e.getSource() == btnRemTel) {
			if (lstTelefone.getSelectedIndex() != -1) {
				model.remove(lstTelefone.getSelectedIndex());
				lstTelefone.setModel(model);
			} else
				JOptionPane.showMessageDialog(this, "Selecione um telefone");

		}

		if (e.getSource() == btnPrimLinha) {
			lblNovoCliente.setEnabled(false);
			tblListar.setRowSelectionInterval(0, 0);
			lblEditarCliente.setEnabled(true);
			lblExcluirCliente.setEnabled(true);
			Cliente c = ClienteController.getCliente(listaAtualTabela.get(
					tblListar.getSelectedRow()).getCod());
			tblListar.grabFocus();
			setCamposDadosClientes(c);
		}

		if (e.getSource() == btnUltLinha) {
			lblNovoCliente.setEnabled(false);
			tblListar.setRowSelectionInterval(tblListar.getModel()
					.getRowCount() - 1, tblListar.getModel().getRowCount() - 1);
			lblEditarCliente.setEnabled(true);
			lblExcluirCliente.setEnabled(true);
			Cliente c = ClienteController.getCliente(listaAtualTabela.get(
					tblListar.getSelectedRow()).getCod());
			tblListar.grabFocus();
			setCamposDadosClientes(c);
		}

		if (e.getSource() == btnAvancar) {
			tblListar.grabFocus();
			avancarLinhaSelecionada();
		}

		if (e.getSource() == btnVoltar) {
			tblListar.grabFocus();
			voltarLinhaSelecionada();
		}

	}

	private void addTelefone() {
		Validacoes v = new Validacoes();
		if (txtTelefone.getText().replaceAll("[(_ -)]", "").length() > 10
				&& validacao.verificaDDITelefone(txtTelefone.getText())) {

			if (model.size() > 0) {
				if (!v.verificaTelefoneDuplicado(model,
						txtTelefone.getText())) {
					model.addElement(txtTelefone.getText().replace("_", ""));
					lstTelefone.setModel(model);
					txtTelefone.setText("");
				} else{
					JOptionPane.showMessageDialog(this,
							"Este telefone já foi cadastrado");
					txtTelefone.setText("");
					txtTelefone.grabFocus();
				}
			} else {
				model.addElement(txtTelefone.getText().replace("_", ""));
				lstTelefone.setModel(model);
				txtTelefone.setText("");
			}
		} else
			JOptionPane.showMessageDialog(this, "Telefone inválido");
	}

	private void avancarLinhaSelecionada() {
		if (tblListar.getSelectedRow() < tblListar.getModel().getRowCount() - 1) {
			lblNovoCliente.setEnabled(false);
			tblListar.setRowSelectionInterval(tblListar.getSelectedRow() + 1,
					tblListar.getSelectedRow() + 1);
			lblEditarCliente.setEnabled(true);
			lblExcluirCliente.setEnabled(true);
			Cliente c = ClienteController.getCliente(listaAtualTabela.get(
					tblListar.getSelectedRow()).getCod());
			setCamposDadosClientes(c);
		}
	}

	private void voltarLinhaSelecionada() {
		if (tblListar.getSelectedRow() > 0) {
			lblNovoCliente.setEnabled(false);
			tblListar.setRowSelectionInterval(tblListar.getSelectedRow() - 1,
					tblListar.getSelectedRow() - 1);
			lblEditarCliente.setEnabled(true);
			lblExcluirCliente.setEnabled(true);
			Cliente c = ClienteController.getCliente(listaAtualTabela.get(
					tblListar.getSelectedRow()).getCod());
			setCamposDadosClientes(c);
		} else {
			lblNovoCliente.setEnabled(true);
			tblListar.clearSelection();
			lblEditarCliente.setEnabled(false);
			lblExcluirCliente.setEnabled(false);
			limpaCampos();
		}

	}

	private void setCamposDadosClientes(Cliente c) {
		limpaCampos();
		if (c.getCod() > 0)
			cod = c.getCod();

		txtNomeCliente.setText(c.getNome());
		txtCpfCliente.setText(c.getCpf());
		txtRgCliente.setText(c.getRg());
		txtDataNascCliente.setText(c.getDataNasc());
		txtLogradouroCliente.setText(c.getLogradouro());
		txtNumCliente.setText(c.getNumCliente());
		txtCepCliente.setText(c.getCep());
		txtBairroCliente.setText(c.getBairro());
		txtCidadeCliente.setText(c.getCidade());
		txtComplementoCliente.setText(c.getComplemento());
		txtEmailCliente.setText(c.getEmail());
		lblInfoFoto.setText("");

		try {
			if (c.getFotoCliente().length() > 30) {
				fotoCliente.setIcon(new ImageIcon(setImagemDimensao(
						c.getFotoCliente(), fotoCliente.getWidth(),
						fotoCliente.getWidth())));
				localFoto = c.getFotoCliente();
			}
		} catch (Exception ex) {
		}
		if (c.getSexo().equals("M"))
			cmbSexoCliente.setSelectedIndex(1);
		else if (c.getSexo().equals("F"))
			cmbSexoCliente.setSelectedIndex(2);
		cmbEstadoCliente.setSelectedItem(c.getEstado());

		model.clear();
		// setando tambem todos os telefones do cliente na lista
		for (Telefones cc : c.getLstTelefones()) {
			model.addElement(cc.getTelefone());
		}
		lstTelefone.setModel(model);
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

	private int divideNomeCliente(String nome) {
		try {
			if (nome.indexOf(" ", 25) < 30)
				return nome.indexOf(" ", 25);
			else
				return nome.indexOf(" ", 15);
		} catch (Exception ex) {
			return 0;
		}
	}

	public void atualizaTabela(DefaultTableModel modelo) {
		modelo.setNumRows(0);
		int linha = 0;
		if (ClienteController.getClientes() != null) {
			listaAtualTabela = ClienteController.getClientes();
			for (Cliente cc : ClienteController.getClientes()) {
				Cliente c = sobreescreveTextoNulo(cc);
				linha = setDadosTabela(modelo, linha, c);
			}
		}
	}

	private int setDadosTabela(DefaultTableModel modelo, int linha, Cliente c) {
		if (c.getNome().length() < 35 || divideNomeCliente(c.getNome()) < 0) {
			modelo.addRow(new Object[] { c.getNome(), c.getCpf(), c.getRg(),
					c.getEmail(), c.getDataNasc(), c.getSexo(),
					c.getLogradouro(), c.getNumCliente(), c.getCep(),
					c.getBairro(), c.getCidade(), c.getEstado(),
					c.getComplemento(),
					c.getLstTelefones().get(0).getTelefone(),
					c.getFotoCliente() });
		} else {
			modelo.addRow(new Object[] {
					"<html><center>"
							+ c.getNome().substring(0,
									divideNomeCliente(c.getNome()))
							+ "<br>"
							+ c.getNome().substring(
									divideNomeCliente(c.getNome()),
									c.getNome().length()) + "</html>",
					c.getCpf(), c.getRg(), c.getEmail(), c.getDataNasc(),
					c.getSexo(), c.getLogradouro(), c.getNumCliente(),
					c.getCep(), c.getBairro(), c.getCidade(), c.getEstado(),
					c.getComplemento(),
					c.getLstTelefones().get(0).getTelefone(),
					c.getFotoCliente() });
			tblListar.setRowHeight(linha, 30);
		}
		linha++;
		return linha;
	}

	public void atualizaTabelaPesquisa(DefaultTableModel modelo) {
		modelo.setNumRows(0);
		int linha = 0;
		if (ClienteController.getClientes(txtNomeCliente.getText()) != null) {
			listaAtualTabela = ClienteController.getClientes(txtNomeCliente
					.getText());
			for (Cliente cc : ClienteController.getClientes(txtNomeCliente
					.getText())) {
				Cliente c = sobreescreveTextoNulo(cc);
				linha = setDadosTabela(modelo, linha, c);
			}
		}
	}

	private void setEnableCamposFalse() {
		txtCpfCliente.setEnabled(false);
		txtRgCliente.setEnabled(false);
		txtDataNascCliente.setEnabled(false);
		cmbSexoCliente.setEnabled(false);
		txtLogradouroCliente.setEnabled(false);
		txtNumCliente.setEnabled(false);
		txtCepCliente.setEnabled(false);
		txtBairroCliente.setEnabled(false);
		txtCidadeCliente.setEnabled(false);
		cmbEstadoCliente.setEnabled(false);
		txtComplementoCliente.setEnabled(false);
		btnAddTel.setEnabled(false);
		btnRemTel.setEnabled(false);
		lblProcuraImagem.setEnabled(false);
		lblCamera.setEnabled(false);
		lblRemoveImagem.setEnabled(false);
		txtEmailCliente.setEnabled(false);
		txtTelefone.setEnabled(false);
		btnPrimLinha.setEnabled(true);
		btnUltLinha.setEnabled(true);
		btnVoltar.setEnabled(true);
		btnAvancar.setEnabled(true);

		verificaRelatorio();
	}

	private boolean verificaCamposPreenchidos() {
		if (!txtNomeCliente.getText().equals("")
				&& lstTelefone.getModel().getSize() > 0) {
			if ((txtCpfCliente.getText().replaceAll("[_. -]", "").length() == 11 || txtCpfCliente
					.getText().replaceAll("[_. -]", "").length() == 0))
				if (txtCepCliente.getText().replaceAll("[- ._]", "").length() == 8
						|| (txtCepCliente.getText().replaceAll("[- ._]", "")
								.length() == 0))
					if ((txtEmailCliente.getText().equals(""))
							|| (validacao
									.validaEmail(txtEmailCliente.getText())))

						if ((txtDataNascCliente.getText()
								.replaceAll("[_/]", "").length() == 8 && validacao
								.validaData(txtDataNascCliente.getText()
										.replaceAll("[_/]", "")))
								|| (txtDataNascCliente.getText()
										.replaceAll("[_/]", "").length() == 0))
							return true;
						else
							JOptionPane.showMessageDialog(this,
									"Data de nascimento inválida");
					else
						JOptionPane.showMessageDialog(this, "Email inválido");
				else
					JOptionPane.showMessageDialog(this, "Cep inválido");
			else
				JOptionPane.showMessageDialog(this, "Cpf inválido");
		} else
			JOptionPane.showMessageDialog(this,
					"Preencha todos os campos obrigatórios");
		return false;
	}

	private void setEnableCamposTrue() {
		txtCpfCliente.setEnabled(true);
		txtRgCliente.setEnabled(true);
		txtDataNascCliente.setEnabled(true);
		cmbSexoCliente.setEnabled(true);
		txtLogradouroCliente.setEnabled(true);
		txtNumCliente.setEnabled(true);
		txtCepCliente.setEnabled(true);
		txtBairroCliente.setEnabled(true);
		txtCidadeCliente.setEnabled(true);
		cmbEstadoCliente.setEnabled(true);
		txtComplementoCliente.setEnabled(true);
		txtEmailCliente.setEnabled(true);
		fotoCliente.setEnabled(true);
		btnAddTel.setEnabled(true);
		btnRemTel.setEnabled(true);
		lblProcuraImagem.setEnabled(true);
		lblCamera.setEnabled(true);
		lblRemoveImagem.setEnabled(true);
		txtTelefone.setEnabled(true);
		btnPrimLinha.setEnabled(false);
		btnUltLinha.setEnabled(false);
		btnVoltar.setEnabled(false);
		btnAvancar.setEnabled(false);
		lblRelatorioCliente.setEnabled(false);
	}

	private void limpaCampos() {
		txtNomeCliente.setText("");
		txtCpfCliente.setText("");
		txtRgCliente.setText("");
		txtDataNascCliente.setText("");
		txtTelefone.setText("");
		txtLogradouroCliente.setText("");
		txtEmailCliente.setText("");
		txtNumCliente.setText("");
		txtCepCliente.setText("");
		txtBairroCliente.setText("");
		txtCidadeCliente.setText("");
		txtComplementoCliente.setText("");
		fotoCliente.setIcon(null);
		cmbSexoCliente.setSelectedIndex(0);
		cmbEstadoCliente.setSelectedIndex(0);
		model.clear();
		lstTelefone.setModel(model);
		lblImgInfoCpf.setIcon(null);
		lblImgInfoData.setIcon(null);
		lblImgInfoEmail.setIcon(null);
		lblImgInfoRg.setIcon(null);
		lblInfoCpf.setText("");
		cod = 0;
		txtBtn = "";
		lblInfoEmail.setText("");
		lblInfoFoto.setText("");
		mat = null;
		localFoto = "";
		ClienteController
				.deletaImagem("C:/Royal/src/br/com/royal/imagemCamera/cliente.jpg");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (txtNomeCliente.hasFocus()) {
			pesquisa();
		}

		if (txtCpfCliente.hasFocus()) {
			if (txtCpfCliente.getText().replaceAll("[/._-]", "").length() == 11) {

				if (!ClienteController.verificaDuplicidadeCpf(
						txtCpfCliente.getText(), cod)) {
					if (validacao.validaCpf(txtCpfCliente.getText())) {
						lblImgInfoCpf.setIcon(new ImageIcon("Img/success.png"));
						lblInfoCpf.setText("");
					} else {
						lblImgInfoCpf.setIcon(new ImageIcon("Img/error.png"));
						lblInfoCpf.setText("");
					}
				} else {
					lblImgInfoCpf.setIcon(new ImageIcon("Img/error.png"));
					lblInfoCpf.setText("CPF já foi cadastrado, insira outro");
				}
			} else if (txtCpfCliente.getText().replaceAll("[/._-]", "")
					.length() == 0) {
				lblImgInfoCpf.setIcon(new ImageIcon(""));
				lblInfoCpf.setText("");
			} else {
				lblImgInfoCpf.setIcon(new ImageIcon("Img/error.png"));
				lblInfoCpf.setText("");
			}
		}

		if (txtDataNascCliente.hasFocus()) {
			if (txtDataNascCliente.getText().replaceAll("[_/]", "").length() == 8)
				if (!validacao.validaData(txtDataNascCliente.getText()
						.replaceAll("[_/]", ""))) {
					lblImgInfoData.setIcon(new ImageIcon("Img/error.png"));
				} else {
					lblImgInfoData.setIcon(new ImageIcon(""));
				}
			else
				lblImgInfoData.setIcon(new ImageIcon(""));
		}

		if (txtEmailCliente.hasFocus()) {
			if (txtEmailCliente.getText().length() > 0) {
				if (!ClienteController.verificaDuplicidadeEmail(
						txtEmailCliente.getText(), cod)) {
					if (!validacao.validaEmail(txtEmailCliente.getText())) {
						lblImgInfoEmail.setIcon(new ImageIcon("Img/error.png"));
						lblInfoEmail.setText("");
					} else {
						lblImgInfoEmail
								.setIcon(new ImageIcon("Img/success.png"));
						lblInfoEmail.setText("");
					}
				} else {
					lblImgInfoEmail.setIcon(new ImageIcon("Img/error.png"));
					lblInfoEmail
							.setText("Email já foi cadastrado, insira outro");
				}
			} else {
				lblImgInfoEmail.setIcon(new ImageIcon(""));
				lblInfoEmail.setText("");
			}
		}

		if (txtCepCliente.getText().replaceAll("[_ -]", "").length() == 8
				&& txtCepCliente.hasFocus()) {
			BuscaCep busca = BuscaCep.searchCep(txtCepCliente.getText());
			if (busca.wasSuccessful()) {
				txtLogradouroCliente.setText(busca.getLogradouro());
				txtBairroCliente.setText(busca.getBairro());
				txtCidadeCliente.setText(busca.getCidade());
				cmbEstadoCliente.setSelectedItem(busca.getUf());

				txtNumCliente.grabFocus();
			}
		}

		if (tblListar.isEnabled() && tblListar.getSelectedRow() > -1) {
			if (e.getKeyCode() == e.VK_DOWN || e.getKeyCode() == e.VK_ENTER)
				setCamposDadosClientes(ClienteController
						.getCliente(listaAtualTabela.get(
								tblListar.getSelectedRow()).getCod()));
			if (e.getKeyCode() == e.VK_UP)
				setCamposDadosClientes(ClienteController
						.getCliente(listaAtualTabela.get(
								tblListar.getSelectedRow()).getCod()));
			tblListar.grabFocus();
		}
		

	}

	private void pesquisa() {
		if (txtNomeCliente.getText().length() > 0) {
			atualizaTabelaPesquisa(modelT);
		} else
			atualizaTabela(modelT);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (txtTelefone.hasFocus()) {
			if (e.VK_ENTER == e.getKeyCode()) {
				addTelefone();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == fotoCliente && !localFoto.equals("")) {
			new JanelaVisualizarImagem(this);
		}

		if (e.getSource().equals(lblMinimize)) {
			this.setExtendedState(JFrame.ICONIFIED);
		}

		if (e.getSource().equals(lblClose)) {
			this.dispose();
		}

		// Função botão novo
		if (e.getSource() == lblNovoCliente && lblNovoCliente.isEnabled()) {
			// setandos todos os campos como Enable = true
			setEnableCamposTrue();
			// setando Enable = true no botao salvar
			lblSalvarCliente.setEnabled(true);
			// setando enable =false nos botoes novo, editar e excluir
			lblNovoCliente.setEnabled(false);
			lblEditarCliente.setEnabled(false);
			lblExcluirCliente.setEnabled(false);

			// preenchendo a variavel 'txtBtn' para poder informar que o
			// usuário solicita cadastrar um novo Cliente
			txtBtn = "Novo";
			tblListar.setEnabled(false);
			// se o campo nome não tiver algo digitado nao recebera foco
			// fiz isso so pra deixar um pouco mais dinamico
			if (txtNomeCliente.getText().length() < 1)
				txtNomeCliente.grabFocus();
		}
		// Função botão salvar
		if (e.getSource() == lblSalvarCliente && lblSalvarCliente.isEnabled()) {
			// verificando se todos os campos obrigatorios estao preenchidos
			if (verificaCamposPreenchidos()) {

				/*
				 * se os campos estiverem preenchidos corretamente os valores
				 * inseridos nos campos serão setados em uma variavel cliente
				 */
				Cliente c = new Cliente();
				c.setCod(this.cod);
				c.setNome(txtNomeCliente.getText());
				c.setDataNasc(txtDataNascCliente.getText());
				c.setRg(txtRgCliente.getText());
				c.setCpf(txtCpfCliente.getText());
				c.setLogradouro(txtLogradouroCliente.getText());
				c.setNumCliente(txtNumCliente.getText());
				c.setCep(txtCepCliente.getText());
				c.setBairro(txtBairroCliente.getText());
				c.setCidade(txtCidadeCliente.getText());
				c.setComplemento(txtComplementoCliente.getText());
				c.setEstado(cmbEstadoCliente.getSelectedItem().toString());
				c.setTelefone(model.toArray());
				c.setEmail(txtEmailCliente.getText().toString());

				if (cmbSexoCliente.getSelectedIndex() == 1)
					c.setSexo("M");
				else if (cmbSexoCliente.getSelectedIndex() == 2)
					c.setSexo("F");
				else
					c.setSexo("");

				if (mat != null) {
					aux = txtNomeCliente.getText() + ".jpg";
					int i = 1;
					boolean bool = false;
					do {
						bool = false;
						for (Cliente cliente : ClienteController.getClientes()) {
							if (cliente.getFotoCliente().length() > 42) {
								if (cliente.getFotoCliente().substring(42)
										.equals(aux)) {
									String extensao = ".jpg";
									String aux2 = "";
									if (i == 1)
										aux2 = aux.substring(0,
												aux.length() - 4);
									else
										aux2 = aux.substring(0,
												aux.length() - 5);

									aux2 += i + "";
									aux2 += extensao;
									aux = aux2;
									bool = true;
									i++;
									break;
								}
							}
						}
					} while (bool);

					File outputfile = new File(
							"C:/Royal/src/br/com/royal/imagensClientes/" + aux);
					caminho = outputfile.toString();
					try {
						imagem = setImagemDimensao(
								"C:/Royal/src/br/com/royal/imagemCamera/cliente.jpg",
								520, 400);
						ImageIO.write(imagem, "jpg", outputfile);
					} catch (Exception e2) {
					}
					mat = null;
					ClienteController
							.deletaImagem("C:/Royal/src/br/com/royal/imagemCamera/cliente.jpg");
				} else if (aux.equals("")) {
					caminho = "-----------";
				} else {
					try {
						File outputfile = new File(
								"C:/Royal/src/br/com/royal/imagensClientes/"
										+ aux);
						caminho = outputfile.toString();

						imagem = setImagemDimensao(arquivo, 520, 400);
						ImageIO.write(imagem, "jpg", outputfile);

					} catch (Exception ex) {
					}
				}
				c.setFotoCliente(caminho);

				/*
				 * Verificando se o usuario solicitou cadastrar um clinte novo
				 * ou as edições feitas em um usuário ja cadastrado
				 */
				// Obs: variavel 'txtBtn' foi declarada e comentada na linha 58
				if (txtBtn.equals("Novo")) {
					/*
					 * Se for para cadastrar um cliente novo o método
					 * 'cadastrarCliente' será solicitado e terá como parametro
					 * de entrada a variavel cliente que contem todos os dados
					 * inseridos para assim cadastra-lo
					 */
					ClienteController.cadastrarCliente(c);
					// avisando o usuario que o cadastro foi um sucesso :D
					JOptionPane.showMessageDialog(this,
							"Cliente cadastrado com sucesso");
				} else {
					/*
					 * Se for para salvar as edições feitas em um cliente o
					 * método 'editarCliente' será solicitado e terá como
					 * parametro de entrada a variavel cliente que contém todos
					 * os dados inseridos e o cpf que não sofreu nenhuma
					 * alteração apos a ultima edição feita
					 */
					ClienteController.deletaImagem(localImagemEdicao);
					ClienteController.editarCliente(c);
					// Avisando o usuario que a edição foi um sucesso u.u
					JOptionPane.showMessageDialog(this,
							"Cliente editado com sucesso");
				}
				// limpando a váriavel txtBtn
				txtBtn = "";
				// chamando o método para limpas todos os campos
				limpaCampos();
				// setando Enable = false em todos os campos
				setEnableCamposFalse();

				// setando Enable = false no botao novo
				lblNovoCliente.setEnabled(true);
				// setando Enable = false no botao salvar
				lblSalvarCliente.setEnabled(false);

				tblListar.getSelectionModel().clearSelection();

				tblListar.setEnabled(true);
				// Atualizando a tabela
				atualizaTabela(modelT);
				// focando a txtNomeCliente
				txtNomeCliente.grabFocus();
			}
		}
		// Função botão editar
		if (e.getSource() == lblEditarCliente && lblEditarCliente.isEnabled()) {
			// setando todos os campos com enable = true
			setEnableCamposTrue();
			// setando enable = true no botao salvar
			lblSalvarCliente.setEnabled(true);
			// setando enable = false nos botoes novo, editar e excluir
			lblNovoCliente.setEnabled(false);
			lblEditarCliente.setEnabled(false);
			lblExcluirCliente.setEnabled(false);
			// Cliente c = ClienteController.getCliente(listaAtualTabela.get(
			// tblListar.getSelectedRow()).getCod());
			//
			// setCamposDadosClientes(c);

			txtBtn = "Editar";
			localImagemEdicao = ClienteController.getCliente(
					listaAtualTabela.get(tblListar.getSelectedRow()).getCod())
					.getFotoCliente();
			tblListar.getSelectionModel().clearSelection();
			tblListar.setEnabled(false);
			// deixando a txtNome focada
			txtNomeCliente.grabFocus();
		}
		// Função botão excluir
		if (e.getSource() == lblExcluirCliente && lblExcluirCliente.isEnabled()) {
			// verificando se o usuario selecionou algum cliente na tabela
			if (tblListar.getSelectedRow() != -1) {
				// guardando o cpf do cliente antes q ele sofra alguma
				// modificação
				// para por usa-lo na consulta do banco de dados(ja q nao pode
				// usar o ip ¬¬)
				int cod = ClienteController.getCliente(
						listaAtualTabela.get(tblListar.getSelectedRow())
								.getCod()).getCod();
				// apresentando uma mensagem para o usuário para saber se ele
				// tem certeza
				// se quer excluir o cliente
				int resposta = JOptionPane.showConfirmDialog(this,
						"Deseja excluir o(a) cliente '"
								+ ClienteController.getCliente(cod).getNome()
								+ "'", "", JOptionPane.YES_NO_OPTION);
				if (resposta == JOptionPane.YES_OPTION) {
					/*
					 * Se ele confirmar a mesagem será chamado o método
					 * excluirCliente que terá como parametro de entrada o cpf
					 * do cliente selecionado
					 */
					ClienteController.deletaImagem(ClienteController
							.getCliente(cod).getFotoCliente());
					ClienteController.excluirCliente(cod);
					JOptionPane.showMessageDialog(this, "Cliente excluído");
					// atualizando a tabela
					atualizaTabela(modelT);
					// deixando a txtNomeCliente em foco
					txtNomeCliente.grabFocus();
					limpaCampos();
					lblExcluirCliente.setEnabled(false);
					lblEditarCliente.setEnabled(false);
					lblNovoCliente.setEnabled(true);
				}
			} else {
				// se ele nao selecionar um cliente será solicitado que ele o
				// faça
				JOptionPane.showMessageDialog(this, "Selecione um cliente");
			}
		}

		// Função botão cancelar
		if (e.getSource().equals(lblCancelar) && lblCancelar.isEnabled()) {
			// limpando todos os campos
			limpaCampos();
			// setando todos os campos com Enable
			setEnableCamposFalse();
			// setando os botoes editar, excluir e novo com enable = true
			lblEditarCliente.setEnabled(false);
			lblExcluirCliente.setEnabled(false);
			lblNovoCliente.setEnabled(true);
			// setando enable = false no botao salvar
			lblSalvarCliente.setEnabled(false);
			// limpando a variavel txtBtn
			txtBtn = "";
			// atualizando a tabela
			pesquisa();
			tblListar.setEnabled(true);
			// focando a txtCliente
			txtNomeCliente.grabFocus();
		}

		if (e.getSource().equals(lblRelatorioCliente)
				&& lblRelatorioCliente.isEnabled()) {
			GerarRelatorioCliente gru = new GerarRelatorioCliente();
			if (tblListar.getSelectedRow() != -1) {
				ArrayList<RelatorioClientes> relatorioClientes = new ArrayList<RelatorioClientes>();
				RelatorioClientes rc = new RelatorioClientes();
				rc.setNome(tblListar.getValueAt(tblListar.getSelectedRow(), 0)
						.toString());
				rc.setDataNasc(tblListar.getValueAt(tblListar.getSelectedRow(),
						4).toString());
				rc.setRg(tblListar.getValueAt(tblListar.getSelectedRow(), 2)
						.toString());
				rc.setCpf(tblListar.getValueAt(tblListar.getSelectedRow(), 1)
						.toString());
				rc.setSexo(tblListar.getValueAt(tblListar.getSelectedRow(), 5)
						.toString());
				rc.setEndereco(tblListar.getValueAt(tblListar.getSelectedRow(),
						6).toString());
				rc.setCep(tblListar.getValueAt(tblListar.getSelectedRow(), 8)
						.toString());
				rc.setNumero(tblListar
						.getValueAt(tblListar.getSelectedRow(), 7).toString());
				rc.setBairro(tblListar
						.getValueAt(tblListar.getSelectedRow(), 9).toString());
				ArrayList<String> telefones = new ArrayList<String>();
				for (int i = 0; i < lstTelefone.getModel().getSize(); i++) {
					telefones.add(lstTelefone.getModel().getElementAt(i)
							.toString());
				}
				rc.setTelefone(telefones);
				rc.setCidade(tblListar.getValueAt(tblListar.getSelectedRow(),
						10).toString());
				rc.setEstado(tblListar.getValueAt(tblListar.getSelectedRow(),
						11).toString());
				rc.setComplemento(tblListar.getValueAt(
						tblListar.getSelectedRow(), 12).toString());
				rc.setEmail(tblListar.getValueAt(tblListar.getSelectedRow(), 3)
						.toString());
				if (!tblListar.getValueAt(tblListar.getSelectedRow(), 14)
						.toString().equals("-----------"))
					rc.setFoto(tblListar.getValueAt(tblListar.getSelectedRow(),
							14).toString());
				rc.setData("");
				rc.setDataCompleta("");
				relatorioClientes.add(rc);
				gru.gerarClienteEspecifico(relatorioClientes);
			} else if (tblListar.getRowCount() > 0) {
				ArrayList<RelatorioClientes> relatorioClientes = new ArrayList<RelatorioClientes>();
				for (int i = 0; i < tblListar.getRowCount(); i++) {
					RelatorioClientes rc = new RelatorioClientes();
					rc.setNome(tblListar.getValueAt(i, 0).toString());
					rc.setDataNasc(tblListar.getValueAt(i, 4).toString());
					rc.setRg(tblListar.getValueAt(i, 2).toString());
					rc.setCpf(tblListar.getValueAt(i, 1).toString());
					rc.setSexo(tblListar.getValueAt(i, 5).toString());
					rc.setData("");
					rc.setDataCompleta("");
					relatorioClientes.add(rc);
				}
				gru.gerarClienteGeral(relatorioClientes);
			}

		}

		if (e.getSource() == lblProcuraImagem && lblProcuraImagem.isEnabled()) {
			try {
				FileDialog file = new FileDialog(new Dialog(this),
						"Abrir imagem", FileDialog.LOAD);
				file.setFile("*.png;*.jpg;*.jpeg");
				file.setVisible(true);
				arquivo = file.getDirectory() + file.getFile();
				localFoto = arquivo;
				aux = file.getFile();
				int i = 1;
				boolean bool = false;
				if (arquivo != null && aux != null) {
					do {
						bool = false;
						for (Cliente c : ClienteController.getClientes()) {
							if (c.getFotoCliente().length() > 42) {
								if (c.getFotoCliente().substring(42)
										.equals(aux)) {
									String extensao = arquivo.substring(arquivo
											.length() - 4);
									String aux2 = "";
									if (i == 1)
										aux2 = aux.substring(0,
												aux.length() - 4);
									else
										aux2 = aux.substring(0,
												aux.length() - 5);

									aux2 += i + "";
									aux2 += extensao;
									aux = aux2;
									bool = true;
									i++;
									break;
								}
							}
						}
					} while (bool);

					imagem = setImagemDimensao(arquivo, fotoCliente.getWidth(),
							fotoCliente.getHeight());
					fotoCliente.setText("");

					fotoCliente.setIcon(new ImageIcon(imagem));

				}

			} catch (Exception ex) {
				System.out.println("Quebro: " + ex.getMessage());
			}
		}

		if (e.getSource() == lblRemoveImagem && lblRemoveImagem.isEnabled()) {
			fotoCliente.setIcon(null);
			mat = null;
		}

		if (e.getSource() == lblCamera && lblCamera.isEnabled()) {
			new JanelaCameraCliente(this);
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource().equals(fotoCliente) && fotoCliente.getIcon()!=null){
			fotoCliente.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		
		if (e.getSource().equals(lblCamera) && lblCamera.isEnabled()) {
			lblCamera.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblProcuraImagem)
				&& lblProcuraImagem.isEnabled()) {
			lblProcuraImagem.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblRemoveImagem)
				&& lblRemoveImagem.isEnabled()) {
			lblRemoveImagem.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		if (e.getSource().equals(lblNovoCliente) && lblNovoCliente.isEnabled()) {
			lblNovoCliente.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblSalvarCliente)
				&& lblSalvarCliente.isEnabled()) {
			lblSalvarCliente.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblEditarCliente)
				&& lblEditarCliente.isEnabled()) {
			lblEditarCliente.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblExcluirCliente)
				&& lblExcluirCliente.isEnabled()) {
			lblExcluirCliente.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblCancelar) && lblCancelar.isEnabled()) {
			lblCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblRelatorioCliente)
				&& lblRelatorioCliente.isEnabled()) {
			lblRelatorioCliente.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
		if (e.getSource().equals(fotoCliente)) {
			fotoCliente.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		
		if (e.getSource().equals(lblCamera) && lblCamera.isEnabled()) {
			lblCamera.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource().equals(lblProcuraImagem)
				&& lblProcuraImagem.isEnabled()) {
			lblProcuraImagem.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource().equals(lblRemoveImagem)
				&& lblRemoveImagem.isEnabled()) {
			lblRemoveImagem.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource().equals(lblNovoCliente) && lblNovoCliente.isEnabled()) {
			lblNovoCliente.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource().equals(lblSalvarCliente)
				&& lblSalvarCliente.isEnabled()) {
			lblSalvarCliente.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource().equals(lblEditarCliente)
				&& lblEditarCliente.isEnabled()) {
			lblEditarCliente.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource().equals(lblExcluirCliente)
				&& lblExcluirCliente.isEnabled()) {
			lblExcluirCliente.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource().equals(lblCancelar) && lblCancelar.isEnabled()) {
			lblCancelar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource().equals(lblRelatorioCliente)
				&& lblRelatorioCliente.isEnabled()) {
			lblRelatorioCliente.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
		if (tblListar.getSelectedRow() != -1 && tblListar.hasFocus()) {
			lblEditarCliente.setEnabled(true);
			lblExcluirCliente.setEnabled(true);
			lblNovoCliente.setEnabled(false);
			setCamposDadosClientes(ClienteController
					.getCliente(listaAtualTabela
							.get(tblListar.getSelectedRow()).getCod()));
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
		else if (ja != null) {
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
			lblRelatorioCliente.setEnabled(false);
			return false;
		}
		lblRelatorioCliente.setEnabled(true);
		return true;
	}

	@Override
	public void focusGained(FocusEvent e) {
		if (e.getSource() == txtTelefone)
			txtTelefone.setCaretPosition(new Validacoes()
					.getCaretPositionTelefone(txtTelefone.getText()));

		if (e.getSource() == txtCepCliente)
			txtCepCliente.setCaretPosition(new Validacoes()
					.getCaretPositionCep(txtCepCliente.getText()));

		if (e.getSource() == txtCpfCliente) {
			txtCpfCliente.setCaretPosition(new Validacoes()
					.getCaretPositionCpf(txtCpfCliente.getText()));
		}

		if (e.getSource() == txtDataNascCliente) {
			txtDataNascCliente.setCaretPosition(new Validacoes()
					.getCaretPositionData(txtDataNascCliente.getText()));
		}
	}

	@Override
	public void focusLost(FocusEvent arg0) {
	}
}
