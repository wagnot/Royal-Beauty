package br.com.royal.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
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
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

import br.com.royal.controller.BuscaCep;
import br.com.royal.controller.FuncionarioController;
import br.com.royal.controller.UsuarioController;
import br.com.royal.controller.Validacoes;
import br.com.royal.controllerReports.GerarRelatorioFuncionario;
import br.com.royal.model.Funcionario;
import br.com.royal.model.ServicoFuncionario;
import br.com.royal.model.Telefones;
import br.com.royal.modelReports.RelatorioFuncionarios;

public class JanelaFuncionario extends JFrame implements ActionListener,
		KeyListener, MouseListener, MouseMotionListener, WindowListener, FocusListener {
	private Color corOriginalBtn = new Color(164, 6, 69);
	private JTextField txtNomeFuncionario, txtRgFuncionario,
			txtLogradouroFuncionario, txtBairroFuncionario,
			txtCidadeFuncionario, txtComplementoFuncionario, txtNumFuncionario,
			txtEmailFuncionario;
	private JFormattedTextField txtDataNascFuncionario,
			txtDataAdmissaoFuncionario, txtCpfFuncionario, txtCepFuncionario,
			txtTelefone;
	private JNumberFormatField txtSalarioFuncionario;
	private MaskFormatter mascaraData, mascaraCpf, mascaraCep, mascaraTel;
	private JComboBox<String> cmbSexoFuncionario, cmbEstadoFuncionario;
	private JLabel lblFundo, lblMinimize, lblClose, lblNavbar,
			lblNomeFuncionario, lblDataNascFuncionario, lblRgFuncionario,
			lblServicos, lblCpfFuncionario, lblSexoFuncionario,
			lblLogradouroFuncionario, lblNumFuncionario, lblCepFuncionario,
			lblBairroFuncionario, lblCidadeFuncionario,
			lblComplementoFuncionario, lblEstadoFuncionario, lblTelefone,
			lblInfoCamposObrigatorios, lblSalarioFuncionario,
			lblDataAdmissaoFuncionario, lblImgInfoCpf, lblImgInfoDataNasc,
			lblImgInfoDataAdm, lblInfoCpf, lblNovoFuncionario,
			lblEditarFuncionario, lblExcluirFuncionario, lblSalvarFuncionario,
			lblCancelar, lblEmailFuncionario, lblInfoEmail,
			lblImgEmail, lblRelatorioFuncionario;
	private JButton btnAddTel, btnRemTel, btnNovo, btnVoltar, btnAvancar,
			btnPrimLinha, btnUltLinha;
	private int posX, posY;
	private JList<String> lstTelefone;
	private JTable tblListar;
	private FTable modelT = new FTable();
	private JCheckBox[] checks;
	private JPanel pnlServicos;
	JScrollPane scrollLst = new JScrollPane();
	private DefaultListModel<String> model = new DefaultListModel<String>();

	private ArrayList<Funcionario> listaAtualTabela = new ArrayList<Funcionario>();
	private int cod = 0;
	private String nomePesquisa = "";
	private String txtBtn = "";// serve para salver se o usuario clicou no botao
								// "editar" ou no botao "novo", mudando assim a
								// função do botao "salvar"
	private JanelaHome jh;

	public JanelaFuncionario(JanelaHome jh) {
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}
		this.jh = jh;
		setTitle("Cadastro Funcionario");
		setSize(1080, 700);
		setResizable(false);
		setLayout(null);
		setUndecorated(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Img/Rosa.png"));
		addWindowListener(this);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		IC();

		setVisible(true);
	}

	private void IC() {
		criaTabela();

		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}

		checks = new JCheckBox[FuncionarioController.getServicos().size()];
		lblNomeFuncionario = new JLabel("* Nome:");
		lblNomeFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		lblNomeFuncionario.setBounds(130, 50, 100, 30);
		add(lblNomeFuncionario);

		txtNomeFuncionario = new JTextField();
		txtNomeFuncionario.setBounds(lblNomeFuncionario.getX(),
				lblNomeFuncionario.getY() + 30, 400, 30);
		txtNomeFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 12));
		add(txtNomeFuncionario);
		txtNomeFuncionario.addKeyListener(this);

		lblCpfFuncionario = new JLabel("* CPF:");
		lblCpfFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		lblCpfFuncionario.setBounds(lblNomeFuncionario.getX(),
				txtNomeFuncionario.getY() + 50, 100, 30);
		add(lblCpfFuncionario);

		txtCpfFuncionario = new JFormattedTextField();
		txtCpfFuncionario.setBounds(lblCpfFuncionario.getX(),
				lblCpfFuncionario.getY() + 30, 100, 30);
		txtCpfFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 12));
		txtCpfFuncionario.addKeyListener(this);
		txtCpfFuncionario.addFocusListener(this);
		txtCpfFuncionario.setEnabled(false);
		add(txtCpfFuncionario);
		try {
			mascaraCpf = new MaskFormatter("###.###.###-##");
			mascaraCpf.setPlaceholderCharacter('_');
			mascaraCpf.install(txtCpfFuncionario);
		} catch (Exception ex) {
		}
		lblImgInfoCpf = new JLabel();
		lblImgInfoCpf.setBounds(txtCpfFuncionario.getX() + 105,
				txtCpfFuncionario.getY() + 5, 20, 20);
		add(lblImgInfoCpf);

		lblInfoCpf = new JLabel();
		lblInfoCpf.setBounds(txtCpfFuncionario.getX(),
				txtCpfFuncionario.getY() + 25, 240, 30);
		lblInfoCpf.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(lblInfoCpf);

		lblRgFuncionario = new JLabel("RG:");
		lblRgFuncionario.setBounds(lblNomeFuncionario.getX() + 125,
				lblCpfFuncionario.getY(), 100, 30);
		lblRgFuncionario.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		add(lblRgFuncionario);

		txtRgFuncionario = new JTextField();
		txtRgFuncionario.setBounds(lblRgFuncionario.getX(),
				lblRgFuncionario.getY() + 30, 100, 30);
		txtRgFuncionario.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				12));
		txtRgFuncionario.setEnabled(false);
		add(txtRgFuncionario);
		txtRgFuncionario.addKeyListener(this);

		lblDataNascFuncionario = new JLabel("* Nascimento");
		lblDataNascFuncionario.setBounds(lblNomeFuncionario.getX() + 255,
				lblCpfFuncionario.getY(), 150, 30);
		lblDataNascFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 13));
		add(lblDataNascFuncionario);

		txtDataNascFuncionario = new JFormattedTextField();
		txtDataNascFuncionario.setBounds(lblDataNascFuncionario.getX() + 5,
				lblDataNascFuncionario.getY() + 30, 100, 30);
		txtDataNascFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 12));
		add(txtDataNascFuncionario);
		txtDataNascFuncionario.addFocusListener(this);
		txtDataNascFuncionario.setEnabled(false);
		txtDataNascFuncionario.addKeyListener(this);
		try {
			mascaraData = new MaskFormatter("##/##/####");
			mascaraData.setPlaceholderCharacter('_');
			mascaraData.install(txtDataNascFuncionario);
		} catch (Exception ex) {
		}

		lblImgInfoDataNasc = new JLabel();
		lblImgInfoDataNasc.setBounds(txtDataNascFuncionario.getX() + 105,
				txtDataNascFuncionario.getY() + 5, 20, 20);
		add(lblImgInfoDataNasc);

		lblDataAdmissaoFuncionario = new JLabel("* Data Admissão");
		lblDataAdmissaoFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 13));
		lblDataAdmissaoFuncionario.setBounds(lblNomeFuncionario.getX() + 390,
				lblCpfFuncionario.getY(), 110, 30);
		add(lblDataAdmissaoFuncionario);

		txtDataAdmissaoFuncionario = new JFormattedTextField();
		txtDataAdmissaoFuncionario.setBounds(lblNomeFuncionario.getX() + 395,
				lblDataAdmissaoFuncionario.getY() + 30, 100, 30);
		txtDataAdmissaoFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 12));
		add(txtDataAdmissaoFuncionario);
		txtDataAdmissaoFuncionario.setEnabled(false);
		txtDataAdmissaoFuncionario.addKeyListener(this);
		txtDataAdmissaoFuncionario.addFocusListener(this);
		try {
			mascaraData = new MaskFormatter("##/##/####");
			mascaraData.setPlaceholderCharacter('_');
			mascaraData.install(txtDataAdmissaoFuncionario);
		} catch (Exception ex) {
		}

		lblImgInfoDataAdm = new JLabel();
		lblImgInfoDataAdm.setBounds(txtDataAdmissaoFuncionario.getX() + 105,
				txtDataAdmissaoFuncionario.getY() + 5, 20, 20);
		add(lblImgInfoDataAdm);

		lblEmailFuncionario = new JLabel("Email:");
		lblEmailFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 13));
		lblEmailFuncionario.setBounds(lblNomeFuncionario.getX(),
				txtCpfFuncionario.getY() + 40, 110, 30);
		add(lblEmailFuncionario);

		txtEmailFuncionario = new JTextField();
		txtEmailFuncionario.setBounds(lblEmailFuncionario.getX(),
				lblEmailFuncionario.getY() + 30, 250, 30);
		txtEmailFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 12));
		add(txtEmailFuncionario);
		txtEmailFuncionario.setEnabled(false);
		txtEmailFuncionario.addKeyListener(this);

		lblImgEmail = new JLabel();
		lblImgEmail
				.setBounds(
						txtEmailFuncionario.getX()
								+ txtEmailFuncionario.getWidth() + 5,
						txtEmailFuncionario.getY() + 5, 20, 20);
		add(lblImgEmail);

		lblInfoEmail = new JLabel();
		lblInfoEmail.setBounds(txtEmailFuncionario.getX(),
				txtEmailFuncionario.getY() + 25, 260, 30);
		lblInfoEmail
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(lblInfoEmail);

		lblSalarioFuncionario = new JLabel("Salário:");
		lblSalarioFuncionario.setBounds(lblNomeFuncionario.getX() + 280,
				lblEmailFuncionario.getY(), 100, 30);
		lblSalarioFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblSalarioFuncionario);

		txtSalarioFuncionario = new JNumberFormatField();
		txtSalarioFuncionario.setLimit(7);
		txtSalarioFuncionario.setBounds(lblSalarioFuncionario.getX(),
				lblSalarioFuncionario.getY() + 30, 100, 30);
		txtSalarioFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 12));
		txtSalarioFuncionario.setEnabled(false);
		add(txtSalarioFuncionario);

		lblSexoFuncionario = new JLabel("Sexo:");
		lblSexoFuncionario.setBounds(lblNomeFuncionario.getX() + 400,
				lblSalarioFuncionario.getY(), 100, 30);
		lblSexoFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblSexoFuncionario);

		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
		cmbSexoFuncionario = new JComboBox<String>();
		cmbSexoFuncionario.setBounds(lblSexoFuncionario.getX(),
				lblSexoFuncionario.getY() + 30, 100, 30);
		cmbSexoFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 12));
		cmbSexoFuncionario.addItem("");
		cmbSexoFuncionario.addItem("Masculino");
		cmbSexoFuncionario.addItem("Feminino");
		cmbSexoFuncionario.setEnabled(false);
		add(cmbSexoFuncionario);
		
		try{
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
		lblCepFuncionario = new JLabel("CEP:");
		lblCepFuncionario.setBounds(lblNomeFuncionario.getX(), 275, 80, 30);
		lblCepFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblCepFuncionario);

		txtCepFuncionario = new JFormattedTextField();
		txtCepFuncionario.setBounds(lblCepFuncionario.getX(),
				lblCepFuncionario.getY() + 30, 80, 30);
		txtCepFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 12));
		add(txtCepFuncionario);
		txtCepFuncionario.setEnabled(false);
		txtCepFuncionario.addKeyListener(this);
		txtCepFuncionario.addFocusListener(this);
		try {
			mascaraCep = new MaskFormatter("#####-###");
			mascaraCep.setPlaceholderCharacter('_');
			mascaraCep.install(txtCepFuncionario);
		} catch (Exception ex) {
		}

		lblLogradouroFuncionario = new JLabel("* Logradouro:");
		lblLogradouroFuncionario.setBounds(lblNomeFuncionario.getX() + 110,
				275, 100, 30);
		lblLogradouroFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblLogradouroFuncionario);

		txtLogradouroFuncionario = new JTextField();
		txtLogradouroFuncionario.setBounds(lblLogradouroFuncionario.getX(),
				lblLogradouroFuncionario.getY() + 30, 250, 30);
		txtLogradouroFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 12));
		add(txtLogradouroFuncionario);
		txtLogradouroFuncionario.setEnabled(false);

		lblNumFuncionario = new JLabel("* Número:");
		lblNumFuncionario.setBounds(lblNomeFuncionario.getX() + 390,
				lblLogradouroFuncionario.getY(), 100, 30);
		lblNumFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblNumFuncionario);

		txtNumFuncionario = new JTextField();
		txtNumFuncionario.setBounds(lblNumFuncionario.getX(),
				lblNumFuncionario.getY() + 30, 100, 30);
		txtNumFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 12));
		add(txtNumFuncionario);
		txtNumFuncionario.setEnabled(false);

		lblBairroFuncionario = new JLabel("* Bairro:");
		lblBairroFuncionario.setBounds(lblNomeFuncionario.getX(), 350, 80, 30);
		lblBairroFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblBairroFuncionario);

		txtBairroFuncionario = new JTextField();
		txtBairroFuncionario.setBounds(lblBairroFuncionario.getX(),
				lblBairroFuncionario.getY() + 30, 100, 30);
		txtBairroFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 12));
		add(txtBairroFuncionario);
		txtBairroFuncionario.setEnabled(false);

		lblCidadeFuncionario = new JLabel("* Cidade:");
		lblCidadeFuncionario.setBounds(lblNomeFuncionario.getX() + 135,
				lblBairroFuncionario.getY(), 80, 30);
		lblCidadeFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblCidadeFuncionario);

		txtCidadeFuncionario = new JTextField();
		txtCidadeFuncionario.setBounds(lblCidadeFuncionario.getX(),
				lblCidadeFuncionario.getY() + 30, 100, 30);
		txtCidadeFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 12));
		add(txtCidadeFuncionario);
		txtCidadeFuncionario.setEnabled(false);

		lblEstadoFuncionario = new JLabel("* Estado:");
		lblEstadoFuncionario.setBounds(lblNomeFuncionario.getX() + 265,
				lblBairroFuncionario.getY(), 80, 30);
		lblEstadoFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblEstadoFuncionario);
		
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
		cmbEstadoFuncionario = new JComboBox<String>();
		cmbEstadoFuncionario.setBounds(lblEstadoFuncionario.getX(),
				lblEstadoFuncionario.getY() + 30, 100, 30);
		cmbEstadoFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 12));
		cmbEstadoFuncionario.addItem("");
		cmbEstadoFuncionario.addItem("AC");
		cmbEstadoFuncionario.addItem("AL");
		cmbEstadoFuncionario.addItem("AP");
		cmbEstadoFuncionario.addItem("AM");
		cmbEstadoFuncionario.addItem("BA");
		cmbEstadoFuncionario.addItem("CE");
		cmbEstadoFuncionario.addItem("DF");
		cmbEstadoFuncionario.addItem("ES");
		cmbEstadoFuncionario.addItem("GO");
		cmbEstadoFuncionario.addItem("MA");
		cmbEstadoFuncionario.addItem("MT");
		cmbEstadoFuncionario.addItem("MS");
		cmbEstadoFuncionario.addItem("MG");
		cmbEstadoFuncionario.addItem("PA");
		cmbEstadoFuncionario.addItem("PB");
		cmbEstadoFuncionario.addItem("PR");
		cmbEstadoFuncionario.addItem("PE");
		cmbEstadoFuncionario.addItem("PI");
		cmbEstadoFuncionario.addItem("RJ");
		cmbEstadoFuncionario.addItem("RN");
		cmbEstadoFuncionario.addItem("RO");
		cmbEstadoFuncionario.addItem("RR");
		cmbEstadoFuncionario.addItem("SC");
		cmbEstadoFuncionario.addItem("SP");
		cmbEstadoFuncionario.addItem("SE");
		cmbEstadoFuncionario.addItem("TO");
		cmbEstadoFuncionario.setEnabled(false);
		add(cmbEstadoFuncionario);
		
		try{
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
		lblComplementoFuncionario = new JLabel("Complemento:");
		lblComplementoFuncionario.setBounds(lblNomeFuncionario.getX() + 390,
				lblBairroFuncionario.getY(), 220, 30);
		lblComplementoFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblComplementoFuncionario);

		txtComplementoFuncionario = new JTextField();
		txtComplementoFuncionario.setBounds(lblComplementoFuncionario.getX(),
				lblComplementoFuncionario.getY() + 30, 110, 30);
		txtComplementoFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 12));
		add(txtComplementoFuncionario);
		txtComplementoFuncionario.setEnabled(false);

		lblInfoCamposObrigatorios = new JLabel("* Campos Obrigatórios");
		lblInfoCamposObrigatorios.setBounds(lblNomeFuncionario.getX(), 430,
				180, 30);
		lblInfoCamposObrigatorios.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblInfoCamposObrigatorios);

		lblTelefone = new JLabel("* Telefone:");
		lblTelefone.setBounds(lblNomeFuncionario.getX() + 530,
				lblNomeFuncionario.getY(), 100, 30);
		lblTelefone.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(lblTelefone);

		txtTelefone = new JFormattedTextField();
		txtTelefone.setBounds(lblTelefone.getX(), lblTelefone.getY() + 30, 140,
				30);
		txtTelefone.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		add(txtTelefone);
		txtTelefone.setEnabled(false);
		txtTelefone.addFocusListener(this);
		txtTelefone.addKeyListener(this);
		try {
			mascaraTel = new MaskFormatter("(##) ####-#####");
			mascaraTel.setPlaceholderCharacter('_');
			mascaraTel.install(txtTelefone);
		} catch (Exception ex) {
		}

		lstTelefone = new JList<String>();
		lstTelefone.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		scrollLst.setViewportView(lstTelefone);
		scrollLst.setBounds(txtTelefone.getX() + 1, txtTelefone.getY() + 30,
				140, 130);
		lstTelefone.setFocusable(false);
		add(scrollLst);

		btnAddTel = new JButton("+");
		btnAddTel.setBackground(corOriginalBtn);
		btnAddTel.setForeground(Color.WHITE);
		btnAddTel.setFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
		btnAddTel.setBounds(lblNomeFuncionario.getX() + 675,
				scrollLst.getY() + 60, 50, 30);
		btnAddTel.setToolTipText("Adicionar telefone");
		add(btnAddTel);
		btnAddTel.setFocusable(false);
		btnAddTel.addActionListener(this);
		btnAddTel.setEnabled(false);

		btnRemTel = new JButton("-");
		btnRemTel.setBackground(corOriginalBtn);
		btnRemTel.setForeground(Color.WHITE);
		btnRemTel.setFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
		btnRemTel.setBounds(btnAddTel.getX(), btnAddTel.getY() + 40, 50, 30);
		add(btnRemTel);
		btnRemTel.setFocusable(false);
		btnRemTel.setToolTipText("Remover telefone");
		btnRemTel.addActionListener(this);
		btnRemTel.setEnabled(false);

		lblServicos = new JLabel("* Serviços:");
		lblServicos
				.setBounds(scrollLst.getX(), scrollLst.getY() + 85, 130, 130);
		lblServicos.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(lblServicos);
		
		pnlServicos = new JPanel();
		pnlServicos.setLayout(null);
		pnlServicos.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		pnlServicos.setEnabled(false);

		int altura = 0;

		int y = 0;
		for (int i = 0; i < FuncionarioController.getServicos().size(); i++) {
			checks[i] = new JCheckBox();
			checks[i].setBounds(0, y, 200, 30);
			checks[i].setEnabled(false);
			if (FuncionarioController.getServicos().get(i).getNomeServico()
					.length() > 14)
				checks[i].setText(FuncionarioController.getServicos().get(i)
						.getNomeServico().substring(0, 14)
						+ "...");
			else
				checks[i].setText(FuncionarioController.getServicos().get(i)
						.getNomeServico());

			checks[i]
					.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
			pnlServicos.add(checks[i]);
			checks[i].addMouseListener(this);
			checks[i].setFocusable(false);
			y += 20;
			altura += 22;
		}

		if (altura > 140) {
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}catch (Exception ex){
				ex.printStackTrace();
			}
			JScrollPane scrollPnl = new JScrollPane(pnlServicos);
			scrollPnl.setBounds(scrollLst.getX(), scrollLst.getY() + 160, 140,
					140);
			scrollPnl
					.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			add(scrollPnl);
			try{
				UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
			}catch (Exception ex){
				ex.printStackTrace();
			}
			
			pnlServicos.setPreferredSize(new Dimension(100, altura));
		} else {
			pnlServicos.setBounds(scrollLst.getX(), scrollLst.getY() + 160,
					140, 140);
			add(pnlServicos);
		}
		lblNovoFuncionario = new JLabel(new ImageIcon(
				"Img/Funcionário/Novo.png"));
		// lblNovoFuncionario.setOpaque(true);
		// lblNovoFuncionario.setBackground(Color.GREEN);
		lblNovoFuncionario.setBounds(885, 88, 61, 52);
		lblNovoFuncionario.setToolTipText("Novo");
		lblNovoFuncionario.addMouseListener(this);
		add(lblNovoFuncionario);

		btnNovo = new JButton("Novo");
		btnNovo.setBounds(870, 90, 90, 50);
		// add(btnNovo);
		btnNovo.addActionListener(this);

		lblSalvarFuncionario = new JLabel(new ImageIcon(
				"Img/Funcionário/Salvar.png"));
		// lblSalvarFuncionario.setOpaque(true);
		// lblSalvarFuncionario.setBackground(Color.GREEN);
		lblSalvarFuncionario.setBounds(lblNovoFuncionario.getX(),
				lblNovoFuncionario.getY() + 61, 61, 52);
		lblSalvarFuncionario.setToolTipText("Salvar");
		lblSalvarFuncionario.addMouseListener(this);
		lblSalvarFuncionario.setEnabled(false);
		add(lblSalvarFuncionario);

		lblEditarFuncionario = new JLabel(new ImageIcon(
				"Img/Funcionário/Editar.png"));
		// lblEditarFuncionario.setOpaque(true);
		// lblEditarFuncionario.setBackground(Color.GREEN);
		lblEditarFuncionario.setBounds(lblSalvarFuncionario.getX(),
				lblSalvarFuncionario.getY() + 61, 61, 52);
		lblEditarFuncionario.setToolTipText("Editar");
		lblEditarFuncionario.addMouseListener(this);
		lblEditarFuncionario.setEnabled(false);
		add(lblEditarFuncionario);

		lblExcluirFuncionario = new JLabel(new ImageIcon(
				"Img/Funcionário/Remover.png"));
		// lblExcluirFuncionario.setOpaque(true);
		// lblExcluirFuncionario.setBackground(Color.GREEN);
		lblExcluirFuncionario.setBounds(lblEditarFuncionario.getX(),
				lblEditarFuncionario.getY() + 61, 61, 52);
		lblExcluirFuncionario.setToolTipText("Excluir");
		lblExcluirFuncionario.addMouseListener(this);
		lblExcluirFuncionario.setEnabled(false);
		add(lblExcluirFuncionario);

		lblCancelar = new JLabel(new ImageIcon("Img/Cancelar.png"));
		// lblCancelar.setOpaque(true);
		// lblCancelar.setBackground(Color.GREEN);
		lblCancelar.setBounds(lblExcluirFuncionario.getX(),
				lblExcluirFuncionario.getY() + 61, 51, 51);
		lblCancelar.setToolTipText("Cancelar");
		lblCancelar.addMouseListener(this);
		add(lblCancelar);

		lblRelatorioFuncionario = new JLabel(new ImageIcon("Img/Relatorio.png"));
		lblRelatorioFuncionario.setBounds(lblCancelar.getX() + 5,
				lblCancelar.getY() + 70, 44, 50);
		lblRelatorioFuncionario.setToolTipText("Relatório");
		lblRelatorioFuncionario.addMouseListener(this);
		add(lblRelatorioFuncionario);

		btnPrimLinha = new JButton("<<");
		btnPrimLinha.setBackground(corOriginalBtn);
		btnPrimLinha.setForeground(Color.WHITE);
		btnPrimLinha.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
		btnPrimLinha.setToolTipText("Primeira linha");
		btnPrimLinha.setBounds(410, 425, 53, 40);
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
		btnAvancar.setBounds(btnVoltar.getX() + 50, btnVoltar.getY(),
				btnVoltar.getWidth(), btnVoltar.getHeight());
		btnAvancar.setToolTipText("Avançar linha");
		add(btnAvancar);
		btnAvancar.addActionListener(this);

		btnUltLinha = new JButton(">>");
		btnUltLinha.setBackground(corOriginalBtn);
		btnUltLinha.setForeground(Color.WHITE);
		btnUltLinha.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
		btnUltLinha.setBounds(btnAvancar.getX() + 45, btnAvancar.getY(),
				btnPrimLinha.getWidth(), btnPrimLinha.getHeight());

		btnUltLinha.setToolTipText("Ultima linha");
		add(btnUltLinha);
		btnUltLinha.addActionListener(this);

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

		lblFundo = new JLabel(new ImageIcon(
				"Img/Gerenciamento de Funcionários.png"));
		lblFundo.setBounds(0, 0, getWidth(), getHeight());
		add(lblFundo);
		verificaRelatorio();
	}

	private void criaTabela() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
		}

		tblListar = new JTable(modelT);
		tblListar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblListar.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		tblListar.setSelectionBackground(new Color(222, 54, 121));
		tblListar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblListar.getTableHeader().setDefaultRenderer(new RTable());
		tblListar.getTableHeader().setResizingAllowed(false);
		tblListar.addKeyListener(this);
		JScrollPane scroll = new JScrollPane(tblListar);
		scroll.setBounds(20, 480, 1040, 200);
		// scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scroll);
		tblListar.addMouseListener(this);
		modelT.addColumn("Nome");
		modelT.addColumn("CPF");
		modelT.addColumn("RG");
		modelT.addColumn("Data de nascimento");
		modelT.addColumn("Data Admissão");
		modelT.addColumn("Email");
		modelT.addColumn("Salário");
		modelT.addColumn("Sexo");
		modelT.addColumn("Logradouro");
		modelT.addColumn("Número");
		modelT.addColumn("CEP");
		modelT.addColumn("Bairro");
		modelT.addColumn("Cidade");
		modelT.addColumn("Estado");
		modelT.addColumn("Complemento");
		modelT.addColumn("Telefone");
		modelT.addColumn("Serviço");
		atualizaTabela(modelT);

		tblListar.getColumnModel().getColumn(0).setPreferredWidth(250);
		tblListar.getColumnModel().getColumn(1).setPreferredWidth(100);
		tblListar.getColumnModel().getColumn(2).setPreferredWidth(100);
		tblListar.getColumnModel().getColumn(3).setPreferredWidth(170);
		tblListar.getColumnModel().getColumn(4).setPreferredWidth(100);
		tblListar.getColumnModel().getColumn(5).setPreferredWidth(220);
		tblListar.getColumnModel().getColumn(6).setPreferredWidth(90);
		tblListar.getColumnModel().getColumn(7).setPreferredWidth(50);
		tblListar.getColumnModel().getColumn(8).setPreferredWidth(250);
		tblListar.getColumnModel().getColumn(9).setPreferredWidth(80);
		tblListar.getColumnModel().getColumn(10).setPreferredWidth(80);
		tblListar.getColumnModel().getColumn(11).setPreferredWidth(100);
		tblListar.getColumnModel().getColumn(12).setPreferredWidth(100);
		tblListar.getColumnModel().getColumn(13).setPreferredWidth(60);
		tblListar.getColumnModel().getColumn(14).setPreferredWidth(100);
		tblListar.getColumnModel().getColumn(15).setPreferredWidth(110);
		tblListar.getColumnModel().getColumn(16).setPreferredWidth(140);

		tblListar.getTableHeader().setReorderingAllowed(false);
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

	private int divideNomeFuncionario(String nome) {
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
		int linha=0;
		if (FuncionarioController.getFuncionarios() != null) {
			listaAtualTabela = FuncionarioController.getFuncionarios();
			for (Funcionario ff : FuncionarioController.getFuncionarios()) {
				Funcionario f = sobreescreveCamposNulos(ff);
				linha = setDadosTabela(modelo, linha, f);
			}
		}
	}

	private int setDadosTabela(DefaultTableModel modelo, int linha,
			Funcionario f) {
		if (f.getLstTelefones().size() < 1) {
			Telefones tc = new Telefones();
			tc.setTelefone("--------------------");
			f.getLstTelefones().add(tc);
		}
		String salario = "";
		if (f.getSalarioFuncionario() < 1)
			salario = "------------";
		else
			salario = new DecimalFormat("0.00").format(f.getSalarioFuncionario());

		if (f.getNomeFuncionario().length() < 35
				|| divideNomeFuncionario(f.getNomeFuncionario()) < 0)
			modelo.addRow(new Object[] { f.getNomeFuncionario(),
					f.getCpfFuncionario(), f.getRgFuncionario(),
					f.getDataNascFuncionario(),
					f.getDataAdmissaoFuncionario(),
					f.getEmailFuncionario(), salario,
					f.getSexoFuncionario(),
					f.getLogradouroFuncionario(),
					f.getNumFuncionario(), f.getCepFuncionario(),
					f.getBairroFuncionario(), f.getCidadeFuncionario(),
					f.getEstadoFuncionario(),
					f.getComplementoFuncionario(),
					f.getLstTelefones().get(0).getTelefone(),
					f.getLstServicoFuncionario().get(0).getServico() });
		else {
			modelo.addRow(new Object[] {
					"<html><center>"
							+ f.getNomeFuncionario().substring(
									0,
									divideNomeFuncionario(f
											.getNomeFuncionario()))
							+ "<br>"
							+ f.getNomeFuncionario().substring(
									divideNomeFuncionario(f
											.getNomeFuncionario()),
									f.getNomeFuncionario().length()),
					f.getCpfFuncionario(), f.getRgFuncionario(),
					f.getDataNascFuncionario(),
					f.getDataAdmissaoFuncionario(),
					f.getEmailFuncionario(), salario,
					f.getSexoFuncionario(),
					f.getLogradouroFuncionario(),
					f.getNumFuncionario(), f.getCepFuncionario(),
					f.getBairroFuncionario(), f.getCidadeFuncionario(),
					f.getEstadoFuncionario(),
					f.getComplementoFuncionario(),
					f.getLstTelefones().get(0).getTelefone(),
					f.getLstServicoFuncionario().get(0).getServico() });
			tblListar.setRowHeight(linha, 30);
		}
linha++;
		return linha;
	}

	private void atualizaTabelaPesquisa(DefaultTableModel modelo) {
		modelo.setNumRows(0);
		int linha=0;
		if (FuncionarioController.getFuncionarios(txtNomeFuncionario.getText()) != null) {
			listaAtualTabela = FuncionarioController
					.getFuncionarios(txtNomeFuncionario.getText());
			for (Funcionario ff : FuncionarioController
					.getFuncionarios(txtNomeFuncionario.getText())) {
				Funcionario f = sobreescreveCamposNulos(ff);

				linha = setDadosTabela(modelo, linha, f);
			}
		}
	}

	private Funcionario sobreescreveCamposNulos(Funcionario ff) {
		Funcionario f = ff;
		String tracos = "----------------------";
		if (f.getRgFuncionario().equals(""))
			f.setRgFuncionario(tracos);
		if (f.getEmailFuncionario().equals(""))
			f.setEmailFuncionario("-------------------------------------------------");
		if (f.getSexoFuncionario().equals(""))
			f.setSexoFuncionario("-----");
		if (f.getCepFuncionario().replaceAll("[-_]", "").equals(""))
			f.setCepFuncionario(tracos);
		return f;
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
				txtTelefone.grabFocus();
			} else
				JOptionPane.showMessageDialog(this, "Selecione um telefone");

		}

		if (e.getSource() == btnPrimLinha) {
			tblListar.grabFocus();
			tblListar.setRowSelectionInterval(0, 0);
			lblEditarFuncionario.setEnabled(true);
			if (listaAtualTabela.get(tblListar.getSelectedRow())
					.getCodFuncionario() != UsuarioController
					.getUsuarioLogado().getFuncionario().getCodFuncionario()) {
				lblExcluirFuncionario.setEnabled(true);
				tblListar.grabFocus();
			} else
				lblExcluirFuncionario.setEnabled(false);
			Funcionario f = FuncionarioController
					.getFuncionario(listaAtualTabela.get(
							tblListar.getSelectedRow()).getCodFuncionario());
			setCamposDadosFuncionarios(f);
			btnNovo.setEnabled(false);
		}

		if (e.getSource() == btnUltLinha) {
			tblListar.setRowSelectionInterval(tblListar.getModel()
					.getRowCount() - 1, tblListar.getModel().getRowCount() - 1);
			lblEditarFuncionario.setEnabled(true);
			if (listaAtualTabela.get(tblListar.getSelectedRow())
					.getCodFuncionario() != UsuarioController
					.getUsuarioLogado().getFuncionario().getCodFuncionario()) {
				lblExcluirFuncionario.setEnabled(true);
				tblListar.grabFocus();
			} else
				lblExcluirFuncionario.setEnabled(false);
			Funcionario f = FuncionarioController
					.getFuncionario(listaAtualTabela.get(
							tblListar.getSelectedRow()).getCodFuncionario());
			setCamposDadosFuncionarios(f);
			lblNovoFuncionario.setEnabled(false);
		}

		if (e.getSource() == btnAvancar) {
			if (tblListar.getSelectedRow() < tblListar.getModel().getRowCount() - 1) {

				tblListar.setRowSelectionInterval(
						tblListar.getSelectedRow() + 1,
						tblListar.getSelectedRow() + 1);
				lblEditarFuncionario.setEnabled(true);
				if (listaAtualTabela.get(tblListar.getSelectedRow())
						.getCodFuncionario() != UsuarioController
						.getUsuarioLogado().getFuncionario()
						.getCodFuncionario()) {
					lblExcluirFuncionario.setEnabled(true);
					tblListar.grabFocus();
				} else
					lblExcluirFuncionario.setEnabled(false);
				Funcionario f = FuncionarioController
						.getFuncionario(listaAtualTabela.get(
								tblListar.getSelectedRow()).getCodFuncionario());
				setCamposDadosFuncionarios(f);

				lblNovoFuncionario.setEnabled(false);
			}
		}

		if (e.getSource() == btnVoltar) {
			if (tblListar.getSelectedRow() > 0) {
				tblListar.setRowSelectionInterval(
						tblListar.getSelectedRow() - 1,
						tblListar.getSelectedRow() - 1);
				lblEditarFuncionario.setEnabled(true);
				if (listaAtualTabela.get(tblListar.getSelectedRow())
						.getCodFuncionario() != UsuarioController
						.getUsuarioLogado().getFuncionario()
						.getCodFuncionario()) {
					lblExcluirFuncionario.setEnabled(true);
					tblListar.grabFocus();
				} else
					lblExcluirFuncionario.setEnabled(false);
				lblNovoFuncionario.setEnabled(false);
				Funcionario f = FuncionarioController
						.getFuncionario(listaAtualTabela.get(
								tblListar.getSelectedRow()).getCodFuncionario());
				setCamposDadosFuncionarios(f);
			} else {
				tblListar.clearSelection();
				lblEditarFuncionario.setEnabled(false);
				lblExcluirFuncionario.setEnabled(false);
				limpaCampos();
				lblNovoFuncionario.setEnabled(true);
			}
		}
	}

	private void addTelefone() {
		Validacoes v = new Validacoes();
		if (txtTelefone.getText().replaceAll("[(_ -)]", "").length() > 10
				&& Validacoes.verificaDDITelefone(txtTelefone.getText())) {

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
				}
			} else {
				model.addElement(txtTelefone.getText().replace("_", ""));
				lstTelefone.setModel(model);
				txtTelefone.setText("");
			}
		} else
			JOptionPane.showMessageDialog(this, "Telefone inválido");
		txtTelefone.grabFocus();
	}

	private void setCamposDadosFuncionarios(Funcionario f) {
		if (f.getCodFuncionario() > 0)
			cod = f.getCodFuncionario();
		txtNomeFuncionario.setText(f.getNomeFuncionario());
		txtCpfFuncionario.setText(f.getCpfFuncionario());
		txtRgFuncionario.setText((f.getRgFuncionario() + "").replace(".", ","));
		txtDataNascFuncionario.setText(f.getDataNascFuncionario());
		txtDataAdmissaoFuncionario.setText(f.getDataAdmissaoFuncionario());
		if (f.getSalarioFuncionario() > 0)
			txtSalarioFuncionario.setText(new DecimalFormat("0.00").format(f.getSalarioFuncionario()));
		else
			txtSalarioFuncionario.setText("0.00");
		if (f.getSexoFuncionario().equals("M"))
			cmbSexoFuncionario.setSelectedIndex(1);
		else if (f.getSexoFuncionario().equals("F"))
			cmbSexoFuncionario.setSelectedIndex(2);
		else
			cmbSexoFuncionario.setSelectedIndex(0);

		txtLogradouroFuncionario.setText(f.getLogradouroFuncionario());
		txtNumFuncionario.setText(f.getNumFuncionario());
		txtCepFuncionario.setText(f.getCepFuncionario());
		txtBairroFuncionario.setText(f.getBairroFuncionario());
		txtCidadeFuncionario.setText(f.getCidadeFuncionario());
		txtComplementoFuncionario.setText(f.getComplementoFuncionario());
		txtEmailFuncionario.setText(f.getEmailFuncionario());
		cmbEstadoFuncionario.setSelectedItem(f.getEstadoFuncionario());
		for (int i = 0; i < checks.length; i++)
			checks[i].setSelected(false);
		for (int i = 0; i < checks.length; i++) {
			for (ServicoFuncionario sf : f.getLstServicoFuncionario()) {
				if (checks[i].getText().equals(sf.getServico())) {
					checks[i].setSelected(true);
				}
			}
		}

		model.clear();
		for (Telefones ff : f.getLstTelefones()) {
			model.addElement(ff.getTelefone());
		}
		lstTelefone.setModel(model);
	}

	private boolean verificaCamposPreenchidos() {
		if (!txtNomeFuncionario.getText().equals("")
				&& !txtCpfFuncionario.getText().equals("")
				&& cmbEstadoFuncionario.getSelectedIndex() > 0
				&& !txtLogradouroFuncionario.getText().equals("")
				&& !txtNumFuncionario.getText().equals("")
				&& !txtBairroFuncionario.getText().equals("")
				&& !txtCidadeFuncionario.getText().equals("")
				&& !txtCpfFuncionario.getText().replaceAll("[_.-]", "")
						.equals("")
				&& !txtDataAdmissaoFuncionario.getText().replaceAll("[_/]", "")
						.equals("")
				&& !txtDataNascFuncionario.getText().replaceAll("[_/]", "")
						.equals("")
				&& (txtCepFuncionario.getText().replaceAll("[-_]", "")
						.equals("") || txtCepFuncionario.getText()
						.replaceAll("[-_]", "").length() == 8)
				&& ((txtEmailFuncionario.getText().length() > 0 && Validacoes
						.validaEmail(txtEmailFuncionario.getText())) || txtEmailFuncionario
						.getText().length() == 0)
				&& lstTelefone.getModel().getSize() > 0)
			if (verificaCheks())
				return true;
		return false;
	}

	private boolean verificaCheks() {
		int j = 0;
		for (int i = 0; i < checks.length; i++)
			if (checks[i].isSelected())
				j++;
		if (j > 0)
			return true;
		return false;
	}

	private void setEnableCamposFalse() {
		txtCpfFuncionario.setEnabled(false);
		txtRgFuncionario.setEnabled(false);
		txtDataNascFuncionario.setEnabled(false);
		cmbSexoFuncionario.setEnabled(false);
		txtLogradouroFuncionario.setEnabled(false);
		txtNumFuncionario.setEnabled(false);
		txtCepFuncionario.setEnabled(false);
		txtBairroFuncionario.setEnabled(false);
		txtCidadeFuncionario.setEnabled(false);
		cmbEstadoFuncionario.setEnabled(false);
		txtComplementoFuncionario.setEnabled(false);
		btnAddTel.setEnabled(false);
		btnRemTel.setEnabled(false);
		txtTelefone.setEnabled(false);
		txtDataAdmissaoFuncionario.setEnabled(false);
		txtSalarioFuncionario.setEnabled(false);
		txtEmailFuncionario.setEnabled(false);
		for (int i = 0; i < checks.length; i++)
			checks[i].setEnabled(false);

		btnVoltar.setEnabled(true);
		btnAvancar.setEnabled(true);
		btnPrimLinha.setEnabled(true);
		btnUltLinha.setEnabled(true);
		verificaRelatorio();
	}

	private void setEnableCamposTrue() {
		txtEmailFuncionario.setEnabled(true);
		txtCpfFuncionario.setEnabled(true);
		txtRgFuncionario.setEnabled(true);
		txtDataNascFuncionario.setEnabled(true);
		cmbSexoFuncionario.setEnabled(true);
		txtLogradouroFuncionario.setEnabled(true);
		txtNumFuncionario.setEnabled(true);
		txtCepFuncionario.setEnabled(true);
		txtBairroFuncionario.setEnabled(true);
		txtCidadeFuncionario.setEnabled(true);
		cmbEstadoFuncionario.setEnabled(true);
		txtComplementoFuncionario.setEnabled(true);
		btnAddTel.setEnabled(true);
		btnRemTel.setEnabled(true);
		txtTelefone.setEnabled(true);
		txtDataAdmissaoFuncionario.setEnabled(true);
		txtSalarioFuncionario.setEnabled(true);
		for (int i = 0; i < checks.length; i++)
			checks[i].setEnabled(true);

		btnVoltar.setEnabled(false);
		btnAvancar.setEnabled(false);
		btnPrimLinha.setEnabled(false);
		btnUltLinha.setEnabled(false);
		lblRelatorioFuncionario.setEnabled(false);
	}

	private void limpaCampos() {
		txtNomeFuncionario.setText("");
		txtCpfFuncionario.setText("");
		txtRgFuncionario.setText("");
		txtDataNascFuncionario.setText("");
		txtTelefone.setText("");
		txtLogradouroFuncionario.setText("");
		txtNumFuncionario.setText("");
		txtCepFuncionario.setText("");
		txtBairroFuncionario.setText("");
		txtCidadeFuncionario.setText("");
		txtComplementoFuncionario.setText("");
		txtSalarioFuncionario.setText("");
		txtDataAdmissaoFuncionario.setText("");
		txtEmailFuncionario.setText("");
		lblImgEmail.setIcon(null);
		lblInfoEmail.setText("");
		cmbSexoFuncionario.setSelectedIndex(0);
		cmbEstadoFuncionario.setSelectedIndex(0);
		lblImgInfoCpf.setIcon(null);
		lblImgInfoDataAdm.setIcon(null);
		lblImgInfoDataNasc.setIcon(null);
		lblInfoCpf.setText("");

		model.clear();
		lstTelefone.setModel(model);
		for (int i = 0; i < checks.length; i++)
			checks[i].setSelected(false);
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
	public void keyReleased(KeyEvent e) {
		if (tblListar.isEnabled() && tblListar.getSelectedRow() > -1
				&& tblListar.hasFocus()) {
			if (e.getKeyCode() == e.VK_DOWN)
				setCamposDadosFuncionarios(FuncionarioController
						.getFuncionario(listaAtualTabela.get(
								tblListar.getSelectedRow()).getCodFuncionario()));
			if (e.getKeyCode() == e.VK_UP)
				setCamposDadosFuncionarios(FuncionarioController
						.getFuncionario(listaAtualTabela.get(
								tblListar.getSelectedRow()).getCodFuncionario()));

			if (listaAtualTabela.get(tblListar.getSelectedRow())
					.getCodFuncionario() == UsuarioController
					.getUsuarioLogado().getFuncionario().getCodFuncionario())
				lblExcluirFuncionario.setEnabled(false);
			else
				lblExcluirFuncionario.setEnabled(true);
		}

		if (tblListar.getSelectedRow() != -1
				&& !nomePesquisa.equals(txtNomeFuncionario.getText())) {
			nomePesquisa = txtNomeFuncionario.getText();
			// limpaCampos();
			txtNomeFuncionario.setText(nomePesquisa);
			btnNovo.setEnabled(true);
		}

		if (txtNomeFuncionario.hasFocus()) {
			nomePesquisa = txtNomeFuncionario.getText();
			pesquisa();
		}

		if (txtCpfFuncionario.hasFocus()) {
			if (txtCpfFuncionario.getText().replaceAll("[/._-]", "").length() == 11) {

				if (!FuncionarioController.verificaDuplicidadeCpf(
						txtCpfFuncionario.getText(), cod)) {
					if (FuncionarioController.validaCpf(txtCpfFuncionario
							.getText())) {
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
			} else if (txtCpfFuncionario.getText().replaceAll("[/._-]", "")
					.length() == 0) {
				lblImgInfoCpf.setIcon(new ImageIcon(""));
				lblInfoCpf.setText("");
			} else {
				lblImgInfoCpf.setIcon(new ImageIcon("Img/error.png"));
				lblInfoCpf.setText("");
			}
		}

		if (txtDataNascFuncionario.hasFocus()) {
			if (txtDataNascFuncionario.getText().replaceAll("[_/]", "")
					.length() == 8)
				if (!FuncionarioController.validaData(txtDataNascFuncionario
						.getText().replaceAll("[_/]", ""))) {
					lblImgInfoDataNasc.setIcon(new ImageIcon("Img/error.png"));
				} else {
					lblImgInfoDataNasc.setIcon(new ImageIcon(""));
				}
			else
				lblImgInfoDataNasc.setIcon(new ImageIcon(""));
		}

		if (txtDataAdmissaoFuncionario.hasFocus()) {
			if (txtDataAdmissaoFuncionario.getText().replaceAll("[_/]", "")
					.length() == 8)
				if (!FuncionarioController
						.validaData(txtDataAdmissaoFuncionario.getText()
								.replaceAll("[_/]", ""))) {
					lblImgInfoDataAdm.setIcon(new ImageIcon("Img/error.png"));
				} else {
					lblImgInfoDataAdm.setIcon(new ImageIcon(""));
				}
			else
				lblImgInfoDataAdm.setIcon(new ImageIcon(""));
		}

		if (txtDataNascFuncionario.hasFocus()) {
			if (txtDataNascFuncionario.getText().replaceAll("[_/]", "")
					.length() == 8)
				if (!FuncionarioController.validaData(txtDataNascFuncionario
						.getText().replaceAll("[_/]", ""))) {
					lblImgInfoDataNasc.setIcon(new ImageIcon("Img/error.png"));
				} else {
					lblImgInfoDataNasc.setIcon(new ImageIcon(""));
				}
			else
				lblImgInfoDataNasc.setIcon(new ImageIcon(""));
		}

		if (txtDataAdmissaoFuncionario.hasFocus()) {
			if (txtDataAdmissaoFuncionario.getText().replaceAll("[_/]", "")
					.length() == 8)
				if (!FuncionarioController
						.validaData(txtDataAdmissaoFuncionario.getText()
								.replaceAll("[_/]", ""))) {
					lblImgInfoDataAdm.setIcon(new ImageIcon("Img/error.png"));
				} else {
					lblImgInfoDataAdm.setIcon(new ImageIcon(""));
				}
			else
				lblImgInfoDataAdm.setIcon(new ImageIcon(""));
		}

		if (txtEmailFuncionario.hasFocus()) {
			if (txtEmailFuncionario.getText().length() > 0)
				if (!Validacoes.validaEmail(txtEmailFuncionario.getText())) {
					lblInfoEmail.setText("");
					lblImgEmail.setIcon(new ImageIcon("Img/error.png"));
				} else if (FuncionarioController.verificaDuplicidadeEmail(
						txtEmailFuncionario.getText(), cod)) {
					lblInfoEmail
							.setText("Email já foi cadastrado, insira outro");
					lblImgEmail.setIcon(new ImageIcon("Img/error.png"));
				} else {
					lblInfoEmail.setText("");
					lblImgEmail.setIcon(new ImageIcon("Img/success.png"));
				}
			else {
				lblImgEmail.setIcon(null);
				lblInfoEmail.setText("");
			}
		}

		if (txtCepFuncionario.getText().replaceAll("[_ -]", "").length() == 8
				&& txtCepFuncionario.hasFocus()) {
			BuscaCep busca = BuscaCep.searchCep(txtCepFuncionario.getText());
			if (busca.wasSuccessful()) {
				txtLogradouroFuncionario.setText(busca.getLogradouro());
				txtBairroFuncionario.setText(busca.getBairro());
				txtCidadeFuncionario.setText(busca.getCidade());
				cmbEstadoFuncionario.setSelectedItem(busca.getUf());

				txtNumFuncionario.grabFocus();
			} else {
				txtLogradouroFuncionario.setText("");
				txtBairroFuncionario.setText("");
				txtCidadeFuncionario.setText("");
				cmbEstadoFuncionario.setSelectedIndex(0);
			}

		}
	}

	private void pesquisa() {
		if (txtNomeFuncionario.getText().length() > 0) {
			atualizaTabelaPesquisa(modelT);
		} else
			atualizaTabela(modelT);

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(lblMinimize)) {
			this.setExtendedState(JFrame.ICONIFIED);
		}

		if (e.getSource().equals(lblClose)) {
			this.dispose();
		}

		if (e.getSource() == lblSalvarFuncionario
				&& lblSalvarFuncionario.isEnabled()) {
			try {
				Funcionario f = new Funcionario();

				if (verificaCamposPreenchidos()) {
					f.setNomeFuncionario(txtNomeFuncionario.getText());
					f.setRgFuncionario(txtRgFuncionario.getText());
					f.setCpfFuncionario(txtCpfFuncionario.getText());
					f.setDataNascFuncionario(txtDataNascFuncionario.getText());
					f.setDataAdmissaoFuncionario(txtDataAdmissaoFuncionario
							.getText());
					if (!txtSalarioFuncionario.getText().substring(3).equals("0,00"))
						f.setSalarioFuncionario(Double
								.parseDouble(txtSalarioFuncionario.getText().substring(3)
										.replace(",", ".")));

					f.setLogradouroFuncionario(txtLogradouroFuncionario
							.getText());
					f.setCepFuncionario(txtCepFuncionario.getText());
					f.setBairroFuncionario(txtBairroFuncionario.getText());
					f.setCidadeFuncionario(txtCidadeFuncionario.getText());
					f.setNumFuncionario(txtNumFuncionario.getText());
					f.setComplementoFuncionario(txtComplementoFuncionario
							.getText());
					f.setEstadoFuncionario(cmbEstadoFuncionario
							.getSelectedItem().toString());
					f.setEmailFuncionario(txtEmailFuncionario.getText());
					if (cmbSexoFuncionario.getSelectedIndex() == 1)
						f.setSexoFuncionario("M");
					else if (cmbSexoFuncionario.getSelectedIndex() == 1)
						f.setSexoFuncionario("F");
					else
						f.setSexoFuncionario("");
					ArrayList<ServicoFuncionario> serFunc = new ArrayList<ServicoFuncionario>();
					for (int i = 0; i < checks.length; i++) {
						ServicoFuncionario sf = new ServicoFuncionario();
						if (checks[i].isSelected()) {
							sf.setFuncionario(txtCpfFuncionario.getText());
							sf.setServico(checks[i].getText());
							serFunc.add(sf);
						}
					}
					f.setLstServicoFuncionario(serFunc);
					f.setTelefone(model.toArray());

					if (txtBtn.equals("Novo")) {
						FuncionarioController.cadastrarFuncionario(f);
						JOptionPane.showMessageDialog(this,
								"Funcionario cadastrado com sucesso");
					} else {
						FuncionarioController.editarFuncionario(f, cod);
						JOptionPane.showMessageDialog(this,
								"Funcionário editado com sucesso");
					}

					txtBtn = "";
					limpaCampos();
					setEnableCamposFalse();

					lblEditarFuncionario.setEnabled(false);
					lblExcluirFuncionario.setEnabled(false);
					lblNovoFuncionario.setEnabled(true);
					lblSalvarFuncionario.setEnabled(false);

					tblListar.getSelectionModel().clearSelection();

					tblListar.setEnabled(true);
					atualizaTabela(modelT);
					txtNomeFuncionario.grabFocus();
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

		if (e.getSource() == lblNovoFuncionario
				&& lblNovoFuncionario.isEnabled()) {
			// setandos todos os campos como Enable = true
			setEnableCamposTrue();
			// setando Enable = true no botao salvar
			lblSalvarFuncionario.setEnabled(true);
			// setando enable =false nos botoes novo, editar e excluir
			lblNovoFuncionario.setEnabled(false);
			lblEditarFuncionario.setEnabled(false);
			lblExcluirFuncionario.setEnabled(false);

			// preenchendo a variavel 'txtBtn' para poder informar que o
			// usuário solicita cadastrar um novo Funcionario
			txtBtn = "Novo";
			tblListar.setEnabled(false);
			// se o campo nome não tiver algo digitado nao recebera foco
			// fiz isso so pra deixar um pouco mais dinamico
			if (txtNomeFuncionario.getText().length() < 1)
				txtNomeFuncionario.grabFocus();
		}

		// Função botão editar
		if (e.getSource() == lblEditarFuncionario
				&& lblEditarFuncionario.isEnabled()) {
			setEnableCamposTrue();
			// setando enable = true no botao salvar
			lblSalvarFuncionario.setEnabled(true);
			// setando enable = false nos botoes novo, editar e excluir
			lblNovoFuncionario.setEnabled(false);
			lblEditarFuncionario.setEnabled(false);
			lblExcluirFuncionario.setEnabled(false);
			// guardando o cpf do Funcionario antes q ele sofra alguma
			// modificação
			// para por usa-lo na consulta do banco de dados(ja q nao pode
			// usar o ip ¬¬)
			// cod = FuncionarioController.getFuncionario(
			// listaAtualTabela.get(tblListar.getSelectedRow())
			// .getCodFuncionario()).getCodFuncionario();
			// Declarando uma variavel Funcionario e guardando nela os dados
			// do Funcionario que será editado
			Funcionario f = FuncionarioController.getFuncionario(cod);
			setCamposDadosFuncionarios(f);

			// preenchendo a variavel 'txtBtn' para poder informar que o
			// usuário solicita editar um Funcionario
			txtBtn = "Editar";
			tblListar.getSelectionModel().clearSelection();
			tblListar.setEnabled(false);
			// deixando a txtNome focada
			txtNomeFuncionario.grabFocus();

		}

		// Função botão excluir
		if (e.getSource() == lblExcluirFuncionario) {
			// verificando se o usuario selecionou algum Funcionario na tabela
			if (tblListar.getSelectedRow() != -1) {
				// guardando o cpf do Funcionario antes q ele sofra alguma
				// modificação
				// para por usa-lo na consulta do banco de dados(ja q nao pode
				// usar o ip ¬¬)
				cod = FuncionarioController.getFuncionario(
						listaAtualTabela.get(tblListar.getSelectedRow())
								.getCodFuncionario()).getCodFuncionario();
				// apresentando uma mensagem para o usuário para saber se ele
				// tem certeza
				// se quer excluir o Funcionario

				if ((FuncionarioController.verificaUsuarioExistente(cod) != null && JOptionPane
						.showConfirmDialog(
								this,
								"Deseja exlcuir o funcionário '"
										+ listaAtualTabela.get(
												tblListar.getSelectedRow())
												.getNomeFuncionario()
										+ "' e o seu login '"
										+ FuncionarioController
												.verificaUsuarioExistente(cod)
												.getLogin() + "'?", "",
								JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
						|| (FuncionarioController.verificaUsuarioExistente(cod) == null && JOptionPane
								.showConfirmDialog(this,
										"Deseja exluir o(a) funcionário '"
												+ FuncionarioController
														.getFuncionario(cod)
														.getNomeFuncionario()
												+ "'?", "",
										JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION)) {

					/*
					 * Se ele confirmar a mesagem será chamado o método
					 * excluirFuncionario que terá como parametro de entrada o
					 * cpf do Funcionario selecionado
					 */
					FuncionarioController.excluirFuncionario(cod);
					if (FuncionarioController.verificaUsuarioExistente(cod) != null) {
						UsuarioController.excluirUsuario(FuncionarioController
								.verificaUsuarioExistente(cod).getCod());

						JOptionPane.showMessageDialog(this,
								"Funcionário e usuário excluídos");
					} else
						JOptionPane.showMessageDialog(this,
								"Funcionário excluído");
					// atualizando a tabela
					atualizaTabela(modelT);
					// deixando a txtNomeFuncionario em foco
					txtNomeFuncionario.grabFocus();
					lblEditarFuncionario.setEnabled(false);
					lblExcluirFuncionario.setEnabled(false);
					lblNovoFuncionario.setEnabled(true);
					limpaCampos();
				}
			} else {
				// se ele nao selecionar um Funcionario será solicitado que ele
				// o
				// faça
				JOptionPane.showMessageDialog(this, "Selecione um funcionário");

			}
		}

		if (e.getSource() == lblCancelar && lblCancelar.isEnabled()) {
			limpaCampos();
			setEnableCamposFalse();
			lblEditarFuncionario.setEnabled(false);
			lblExcluirFuncionario.setEnabled(false);
			lblNovoFuncionario.setEnabled(true);
			lblSalvarFuncionario.setEnabled(false);
			atualizaTabela(modelT);
			tblListar.setEnabled(true);
			txtBtn = "";
			txtNomeFuncionario.grabFocus();
		}

		if (e.getSource().equals(lblRelatorioFuncionario)
				&& lblRelatorioFuncionario.isEnabled()) {
			if (tblListar.getSelectedRow() != -1) {
				GerarRelatorioFuncionario gru = new GerarRelatorioFuncionario();
				ArrayList<RelatorioFuncionarios> relatorioFuncionarios = new ArrayList<RelatorioFuncionarios>();
				RelatorioFuncionarios rf = new RelatorioFuncionarios();
				rf.setNome(tblListar.getValueAt(tblListar.getSelectedRow(), 0)
						.toString());
				rf.setDataNasc(tblListar.getValueAt(tblListar.getSelectedRow(),
						3).toString());
				rf.setRg(tblListar.getValueAt(tblListar.getSelectedRow(), 2)
						.toString());
				rf.setCpf(tblListar.getValueAt(tblListar.getSelectedRow(), 1)
						.toString());
				rf.setLogradouro(tblListar.getValueAt(
						tblListar.getSelectedRow(), 8).toString());
				rf.setCep(tblListar.getValueAt(tblListar.getSelectedRow(), 10)
						.toString());
				rf.setNumero(tblListar
						.getValueAt(tblListar.getSelectedRow(), 9).toString());
				rf.setBairro(tblListar.getValueAt(tblListar.getSelectedRow(),
						11).toString());
				ArrayList<String> telefones = new ArrayList<String>();
				for (int i = 0; i < lstTelefone.getModel().getSize(); i++) {
					telefones.add(lstTelefone.getModel().getElementAt(i)
							.toString());
				}
				rf.setTelefone(telefones);
				rf.setCidade(tblListar.getValueAt(tblListar.getSelectedRow(),
						12).toString());
				rf.setEstado(tblListar.getValueAt(tblListar.getSelectedRow(),
						13).toString());
				rf.setComplemento(tblListar.getValueAt(
						tblListar.getSelectedRow(), 14).toString());
				rf.setSalario(tblListar.getValueAt(tblListar.getSelectedRow(),
						6).toString());
				rf.setDataAdmissao(tblListar.getValueAt(
						tblListar.getSelectedRow(), 4).toString());
				rf.setEmail(tblListar.getValueAt(tblListar.getSelectedRow(), 5)
						.toString());
				rf.setData("");
				rf.setDataCompleta("");
				relatorioFuncionarios.add(rf);
				gru.gerarFuncionarioEspecifico(relatorioFuncionarios);
			} else if (tblListar.getRowCount() > 0) {
				GerarRelatorioFuncionario gru = new GerarRelatorioFuncionario();
				ArrayList<RelatorioFuncionarios> relatorioFuncionarios = new ArrayList<RelatorioFuncionarios>();
				for (int i = 0; i < tblListar.getRowCount(); i++) {
					RelatorioFuncionarios rf = new RelatorioFuncionarios();
					rf.setNome(tblListar.getValueAt(i, 0).toString());
					rf.setCpf(tblListar.getValueAt(i, 1).toString());
					rf.setRg(tblListar.getValueAt(i, 2).toString());
					rf.setDataNasc(tblListar.getValueAt(i, 3).toString());
					ArrayList<String> telefone = new ArrayList<String>();
					telefone.add(tblListar.getValueAt(i, 15).toString());
					rf.setTelefone(telefone);
					rf.setData("");
					rf.setDataCompleta("");
					relatorioFuncionarios.add(rf);
				}
				gru.gerarFuncionarioGeral(relatorioFuncionarios);

			}

		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource().equals(lblNovoFuncionario) && lblNovoFuncionario.isEnabled()) {
			lblNovoFuncionario.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblSalvarFuncionario)
				&& lblSalvarFuncionario.isEnabled()) {
			lblSalvarFuncionario.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblEditarFuncionario)
				&& lblEditarFuncionario.isEnabled()) {
			lblEditarFuncionario.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblExcluirFuncionario)
				&& lblExcluirFuncionario.isEnabled()) {
			lblExcluirFuncionario.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblCancelar) && lblCancelar.isEnabled()) {
			lblCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblRelatorioFuncionario)
				&& lblRelatorioFuncionario.isEnabled()) {
			lblRelatorioFuncionario.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
		if (tblListar.getSelectedRow() != -1 && tblListar.isFocusable()) {
			lblNovoFuncionario.setEnabled(false);
			lblEditarFuncionario.setEnabled(true);
			if (listaAtualTabela.get(tblListar.getSelectedRow())
					.getCodFuncionario() != UsuarioController
					.getUsuarioLogado().getFuncionario().getCodFuncionario())
				lblExcluirFuncionario.setEnabled(true);
			else
				lblExcluirFuncionario.setEnabled(false);
			setCamposDadosFuncionarios(FuncionarioController
					.getFuncionario(listaAtualTabela.get(
							tblListar.getSelectedRow()).getCodFuncionario()));
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
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

	@Override
	public void mouseDragged(MouseEvent e) {
		setLocation(getLocation().x + e.getX() - posX,
				getLocation().y + e.getY() - posY);

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public boolean verificaRelatorio() {
		if (tblListar.getRowCount() == 0) {
			lblRelatorioFuncionario.setEnabled(false);
			return false;
		}
		lblRelatorioFuncionario.setEnabled(true);
		return true;
	}

	@Override
	public void focusGained(FocusEvent e) {
		if(e.getSource()==txtTelefone){
			txtTelefone.setCaretPosition(new Validacoes().getCaretPositionTelefone(txtTelefone.getText()));
		}
		
		if(e.getSource()==txtCepFuncionario){
			txtCepFuncionario.setCaretPosition(new Validacoes().getCaretPositionCep(txtCepFuncionario.getText()));
		}
		
		if(e.getSource()==txtDataAdmissaoFuncionario){
			txtDataAdmissaoFuncionario.setCaretPosition(new Validacoes().getCaretPositionData(txtDataAdmissaoFuncionario.getText()));
		}
		
		if(e.getSource()==txtDataNascFuncionario)
			txtDataNascFuncionario.setCaretPosition(new Validacoes().getCaretPositionData(txtDataNascFuncionario.getText()));
		
		if(e.getSource()==txtCpfFuncionario){
			txtCpfFuncionario.setCaretPosition(new Validacoes().getCaretPositionCpf(txtCpfFuncionario.getText()));
		}
		
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}