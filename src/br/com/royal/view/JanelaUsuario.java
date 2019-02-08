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
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableCellRenderer;

import br.com.royal.controller.UsuarioController;
import br.com.royal.controllerReports.GerarRelatorioUsuario;
import br.com.royal.model.Funcionario;
import br.com.royal.model.Usuario;
import br.com.royal.modelReports.RelatorioUsuarios;

//Arrumar a segurança dessa budega
public class JanelaUsuario extends JFrame implements ActionListener,
		KeyListener, MouseListener, MouseMotionListener, WindowListener {

	private JTextField txtLoginUsuario, txtRespostaUsuario, txtPerguntaUsuario;
	private JPasswordField txtSenha, txtConfirmaSenha;
	private JComboBox<Funcionario> cmbNomeFuncionario;
	private JComboBox<String> cmbTipoUsuario, cmbPerguntaUsuario;
	private JLabel lblFundo, lblNavbar, lblMinimize, lblClose, lblLoginUsuario,
			lblTipoUsuario, lblNomeFuncionario, lblRespostaUsuario,
			lblSenhaUsuario, lblInfoCamposObrigatorios, lblPerguntaUsuario,
			lblConfirmaSenha, lblInfoSenha, lblInfoLogin, lblNovoUsuario,
			lblSalvarUsuario, lblEditarUsuario, lblCancelar,
			lblRelatorioUsuario;
	private JButton btnCancelarPergunta, btnVoltar, btnAvancar, btnPrimLinha,
			btnUltLinha;
	private Color corOriginalBtn = new Color(164, 6, 69);
	private JTable tblListar;
	private FTable modelT = new FTable();
	private int cod = 0;
	private int posX, posY;
	private String clickNovo = "Novo", clickEditar = "Editar";
	private String txtBtn = "";// serve para salver se o usuario clicou no botao
								// "editar" ou no botao "novo", mudando assim a
								// função do botao "salvar"
	private JanelaHome jh;
	private Usuario UsuarioSeguranca = new Usuario();
	private ArrayList<Usuario> listaAtualTabela = new ArrayList<Usuario>();
	private String adm = "Administrador", fun = "Funcionário";

	public JanelaUsuario(JanelaHome jh) {
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}
		this.jh = jh;
		setTitle("Cadastro Usuario");
		setSize(1080, 700);
		setResizable(false);
		setLayout(null);
		setUndecorated(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Img/Rosa.png"));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(this);
		IC();
		
		setVisible(true);
		
		if (UsuarioController.getUsuarioLogado().getTipo().equals(fun)) {
			limitacoesFuncionario();
			lblEditarUsuario.setEnabled(true);
		}
		
	}

	private void IC() {

		lblLoginUsuario = new JLabel("* Login:");
		lblLoginUsuario.setBounds(190, 70, 100, 30);
		lblLoginUsuario.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		add(lblLoginUsuario);

		txtLoginUsuario = new JTextField();
		txtLoginUsuario.setBounds(lblLoginUsuario.getX(),
				lblLoginUsuario.getY() + 30, 400, 30);
		txtLoginUsuario.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				12));
		add(txtLoginUsuario);
		txtLoginUsuario.addKeyListener(this);
		
		lblInfoLogin = new JLabel();
		lblInfoLogin.setBounds(txtLoginUsuario.getX(),
				txtLoginUsuario.getY() + 25, 300, 30);
		lblInfoLogin
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(lblInfoLogin);

		lblSenhaUsuario = new JLabel("* Senha:");
		lblSenhaUsuario.setBounds(lblLoginUsuario.getX(),
				txtLoginUsuario.getY() + 50, 100, 30);
		lblSenhaUsuario.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		add(lblSenhaUsuario);

		txtSenha = new JPasswordField();
		txtSenha.setBounds(lblSenhaUsuario.getX(), lblSenhaUsuario.getY() + 30,
				150, 30);
		txtSenha.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		txtSenha.setEnabled(false);
		add(txtSenha);
		txtSenha.addKeyListener(this);

		lblInfoSenha = new JLabel();
		lblInfoSenha.setBounds(txtSenha.getX(), txtSenha.getY() + 25, 300, 30);
		lblInfoSenha
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(lblInfoSenha);

		lblConfirmaSenha = new JLabel("* Confirme a senha:");
		lblConfirmaSenha.setBounds(lblLoginUsuario.getX() + 170,
				lblSenhaUsuario.getY(), 200, 30);
		lblConfirmaSenha.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		add(lblConfirmaSenha);

		txtConfirmaSenha = new JPasswordField();
		txtConfirmaSenha.setBounds(lblConfirmaSenha.getX(),
				lblConfirmaSenha.getY() + 30, 150, 30);
		txtConfirmaSenha.setEnabled(false);
		txtConfirmaSenha.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				12));
		add(txtConfirmaSenha);
		txtConfirmaSenha.addKeyListener(this);

		lblNomeFuncionario = new JLabel("* Funcionários");
		lblNomeFuncionario.setBounds(lblLoginUsuario.getX() + 350,
				lblSenhaUsuario.getY(), 120, 30);
		lblNomeFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		lblNomeFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblNomeFuncionario);

		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
		cmbNomeFuncionario = new JComboBox<Funcionario>();
		cmbNomeFuncionario.setBounds(lblNomeFuncionario.getX(),
				lblNomeFuncionario.getY() + 30, 120, 30);
		cmbNomeFuncionario.setEnabled(false);
		cmbNomeFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 12));
		atualizaCombo();
		cmbNomeFuncionario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 12));
		add(cmbNomeFuncionario);
		
		try{
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
		lblTipoUsuario = new JLabel("* Tipo:");
		lblTipoUsuario.setBounds(lblLoginUsuario.getX(), txtSenha.getY() + 40,
				120, 30);
		lblTipoUsuario.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		lblTipoUsuario.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		add(lblTipoUsuario);
		
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
		cmbTipoUsuario = new JComboBox<String>();
		cmbTipoUsuario.setBounds(lblTipoUsuario.getX(),
				lblTipoUsuario.getY() + 30, 120, 30);
		cmbTipoUsuario.addItem("");
		cmbTipoUsuario.addItem(adm);
		cmbTipoUsuario.addItem(fun);
		cmbTipoUsuario.setEnabled(false);
		cmbTipoUsuario.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				12));
		add(cmbTipoUsuario);
		
		try{
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
		lblPerguntaUsuario = new JLabel("* Pergunta:");
		lblPerguntaUsuario.setBounds(lblLoginUsuario.getX() + 159,
				lblTipoUsuario.getY(), 120, 30);
		lblPerguntaUsuario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		lblPerguntaUsuario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblPerguntaUsuario);
		
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
		cmbPerguntaUsuario = new JComboBox<String>();
		cmbPerguntaUsuario.setBounds(lblPerguntaUsuario.getX(),
				lblPerguntaUsuario.getY() + 30, 310, 30);
		cmbPerguntaUsuario.addItem("");
		cmbPerguntaUsuario.addItem("Qual o nome do seu primeiro cachorro?");
		cmbPerguntaUsuario
				.addItem("Qual foi o destino da sua primeira viagem?");
		cmbPerguntaUsuario.addItem("Qual é a sua comida predileta?");
		cmbPerguntaUsuario.addItem("Adicionar uma pergunta");
		cmbPerguntaUsuario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 12));
		cmbPerguntaUsuario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 12));
		add(cmbPerguntaUsuario);
		cmbPerguntaUsuario.setEnabled(false);
		cmbPerguntaUsuario.addActionListener(this);
		
		try{
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
		txtPerguntaUsuario = new JTextField();
		txtPerguntaUsuario.setBounds(lblPerguntaUsuario.getX(),
				lblPerguntaUsuario.getY() + 30, 310, 30);
		txtPerguntaUsuario.setVisible(false);
		txtPerguntaUsuario.setEnabled(false);
		txtPerguntaUsuario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 12));
		add(txtPerguntaUsuario);

		btnCancelarPergunta = new JButton("Cancelar");
		btnCancelarPergunta.setBounds(cmbPerguntaUsuario.getX() + 320,
				lblPerguntaUsuario.getY() + 30, 90, 30);
		btnCancelarPergunta.addActionListener(this);
		btnCancelarPergunta.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 12));
		add(btnCancelarPergunta);
		btnCancelarPergunta.setVisible(false);
		btnCancelarPergunta.setEnabled(false);

		lblRespostaUsuario = new JLabel("* Resposta:");
		lblRespostaUsuario.setBounds(lblLoginUsuario.getX(),
				cmbTipoUsuario.getY() + 40, 100, 30);
		lblRespostaUsuario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblRespostaUsuario);

		txtRespostaUsuario = new JTextField();
		txtRespostaUsuario.setBounds(lblRespostaUsuario.getX(),
				lblRespostaUsuario.getY() + 30, 280, 30);
		txtRespostaUsuario.setEnabled(false);
		txtRespostaUsuario.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 12));
		add(txtRespostaUsuario);
		
		criaTabela();

		lblInfoCamposObrigatorios = new JLabel("* Campos Obrigatórios");
		lblInfoCamposObrigatorios.setBounds(lblLoginUsuario.getX(), 435, 180,
				30);
		lblInfoCamposObrigatorios.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblInfoCamposObrigatorios);

		btnPrimLinha = new JButton("<<");
		btnPrimLinha.setBackground(corOriginalBtn);
		btnPrimLinha.setForeground(Color.WHITE);
		btnPrimLinha.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
		btnPrimLinha.setBounds(lblLoginUsuario.getX() + 250, 435, 52, 40);
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

		lblNovoUsuario = new JLabel(new ImageIcon("Img/Usuário/Novo.png"));
		// lblNovoUsuario.setOpaque(true);
		// .setBackground(Color.GREEN);
		lblNovoUsuario.setBounds(lblLoginUsuario.getX() + 695, 95, 61, 52);
		lblNovoUsuario.setToolTipText("Novo");
		lblNovoUsuario.addMouseListener(this);
		add(lblNovoUsuario);

		lblSalvarUsuario = new JLabel(new ImageIcon("Img/Usuário/Salvar.png"));
		// lblSalvarUsuario.setOpaque(true);
		// lblSalvarUsuario.setBackground(Color.GREEN);
		lblSalvarUsuario.setBounds(lblNovoUsuario.getX(),
				lblNovoUsuario.getY() + 70, 61, 52);
		lblSalvarUsuario.setToolTipText("Salvar");
		lblSalvarUsuario.addMouseListener(this);
		lblSalvarUsuario.setEnabled(false);
		add(lblSalvarUsuario);

		lblEditarUsuario = new JLabel(new ImageIcon("Img/Usuário/Editar.png"));
		// lblEditarUsuario.setOpaque(true);
		// lblEditarUsuario.setBackground(Color.GREEN);
		lblEditarUsuario.setBounds(lblSalvarUsuario.getX(),
				lblSalvarUsuario.getY() + 70, 61, 52);
		lblEditarUsuario.setToolTipText("Editar");
		lblEditarUsuario.addMouseListener(this);
		lblEditarUsuario.setEnabled(false);
		add(lblEditarUsuario);

		lblCancelar = new JLabel(new ImageIcon("Img/Cancelar.png"));
		// lblCancelar.setOpaque(true);
		// lblCancelar.setBackground(Color.GREEN);
		lblCancelar.setBounds(lblEditarUsuario.getX() + 7,
				lblEditarUsuario.getY() + 70, 51, 51);
		lblCancelar.setToolTipText("Cancelar");
		lblCancelar.addMouseListener(this);
		add(lblCancelar);

		lblRelatorioUsuario = new JLabel(new ImageIcon("Img/Relatorio.png"));
		lblRelatorioUsuario.setBounds(lblEditarUsuario.getX() + 15,
				lblCancelar.getY() + 70, 44, 50);
		lblRelatorioUsuario.setToolTipText("Relatório");
		lblRelatorioUsuario.addMouseListener(this);
		add(lblRelatorioUsuario);

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
				new ImageIcon("Img/Gerenciamento de Usuários.png"));
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
		scroll.setBounds(20, 480, 1040, 200);
		// scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scroll);

		modelT.addColumn("Login");
		modelT.addColumn("Funcionário");
		modelT.addColumn("Tipo");
		modelT.addColumn("Pergunta");
		if (UsuarioController.getUsuarioLogado().getTipo().equals(adm))
			atualizaTabela(modelT);
		else {
			atualizaTabelaUsuarioLogado(modelT);
			setCamposDadosUsuario(UsuarioController
					.getUsuario(UsuarioController.getUsuarioLogado().getCod()));
		}

		tblListar.getColumnModel().getColumn(0).setPreferredWidth(300);
		tblListar.getColumnModel().getColumn(1).setPreferredWidth(300);
		tblListar.getColumnModel().getColumn(2).setPreferredWidth(150);
		tblListar.getColumnModel().getColumn(3).setPreferredWidth(300);
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
		

		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void atualizaCombo() {
		cmbNomeFuncionario.removeAllItems();
		Funcionario ff = new Funcionario();
		ff.setNomeFuncionario("");
		cmbNomeFuncionario.addItem(ff);
		for (Funcionario f : UsuarioController.getAlgunsFuncionarios()) {
			cmbNomeFuncionario.addItem(f);
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
		if (e.getSource() == lblNovoUsuario && lblNovoUsuario.isEnabled()) {
			txtBtn = clickNovo;
			setEnableCamposTrue();
			lblSalvarUsuario.setEnabled(true);
			lblNovoUsuario.setEnabled(false);
			lblEditarUsuario.setEnabled(false);

			tblListar.setEnabled(false);

			if (txtLoginUsuario.getText().length() < 1)
				txtLoginUsuario.grabFocus();
		}

		// Função botão salvar
		if (e.getSource() == lblSalvarUsuario && lblSalvarUsuario.isEnabled()) {
			// verificando se todos os campos obrigatorios estao preenchidos
			if (verificaCamposPreenchidos()) {
				/*
				 * se os campos estiverem preenchidos corretamente os valores
				 * inseridos nos campos serão setados em uma variavel Usuario
				 */
				Usuario u = new Usuario();
				u.setLogin(txtLoginUsuario.getText());
				if (txtSenha.getText().equals(""))
					u.setSenha(UsuarioSeguranca.getSenha());
				else {
					try {
						u.setSenhaCriptografada(txtSenha.getText());
					} catch (Exception ex) {
					}
				}

				u.setTipo(cmbTipoUsuario.getSelectedItem().toString());
				if (txtBtn.equals(clickNovo)) {
					u.setFuncionario((Funcionario) cmbNomeFuncionario
							.getSelectedItem());
					u.setResposta(txtRespostaUsuario.getText());
				} else {
					u.setFuncionario(UsuarioSeguranca.getFuncionario());
					u.setResposta(UsuarioSeguranca.getResposta());
				}

				if (!txtPerguntaUsuario.getText().equals(""))
					u.setPergunta(txtPerguntaUsuario.getText());
				else
					u.setPergunta(cmbPerguntaUsuario.getSelectedItem()
							.toString());

				if (txtBtn.equals(clickNovo)) {
					UsuarioController.cadastrarUsuario(u);
					JOptionPane.showMessageDialog(this,
							"Usuário cadastrado com sucesso");
				} else {
					UsuarioController.editarUsuario(u, cod);
					JOptionPane.showMessageDialog(this,
							"Usuário editado com sucesso");
				}

				// limapando a váriavel txtBtn
				txtBtn = "";
				// chamando o método para limpas todos os campos

				lblSalvarUsuario.setEnabled(false);
				setEnableCamposFalse();
				pesquisa();
				if (UsuarioController.getUsuarioLogado().getTipo().equals(adm)) {
					limpaCampos();
					// setando Enable = false em todos os campos
					atualizaCombo();
					// setando Enable = true nos botoes editar, excluir e novo
					lblEditarUsuario.setEnabled(false);
					lblNovoUsuario.setEnabled(true);
					// setando Enable = false no botao salvar
					tblListar.getSelectionModel().clearSelection();

					tblListar.setEnabled(true);

				} else {
					limitacoesFuncionario();
					lblEditarUsuario.setEnabled(true);
					txtSenha.setText("");
					txtConfirmaSenha.setText("");
					txtRespostaUsuario.setText("");
				}

			} else
				JOptionPane.showMessageDialog(this,
						"Preencha todos os campos obrigatórios corretamente");

		}

		if (e.getSource() == lblEditarUsuario && lblEditarUsuario.isEnabled()) {
			lblEditarUsuario.setEnabled(false);
			setEnableCamposTrue();
			cmbNomeFuncionario.setEnabled(false);

			cod = UsuarioController.getUsuario(
					listaAtualTabela.get(tblListar.getSelectedRow()).getCod())
					.getCod();
			UsuarioSeguranca = UsuarioController.getUsuario(cod);

			if (cmbPerguntaUsuario.getSelectedIndex() < 1) {
				btnCancelarPergunta.setVisible(true);
				btnCancelarPergunta.setEnabled(true);
			}

			if (UsuarioController.getUsuarioLogado().getTipo().equals(adm)) {
				tblListar.getSelectionModel().clearSelection();
				tblListar.setEnabled(false);
				atualizaCombo();
			} else
				limitacoesFuncionario();

			txtBtn = clickEditar;
			txtLoginUsuario.grabFocus();
			lblSalvarUsuario.setEnabled(true);
			lblNovoUsuario.setEnabled(false);

		}

		if (e.getSource() == lblCancelar && lblCancelar.isEnabled()) {
			lblEditarUsuario.setEnabled(false);
			lblNovoUsuario.setEnabled(true);
			lblSalvarUsuario.setEnabled(false);

			// limpando a variavel txtBtn
			txtBtn = "";

			if (UsuarioController.getUsuarioLogado().getTipo().equals(adm)) {
				limpaCampos();
				pesquisa();
			} else {
				limitacoesFuncionario();
				lblEditarUsuario.setEnabled(true);
				txtSenha.setText("");
				txtConfirmaSenha.setText("");
				txtRespostaUsuario.setText("");
				lblInfoSenha.setText("");
				txtLoginUsuario.setText(tblListar.getValueAt(
						tblListar.getSelectedRow(), 0)
						+ "");
				if (cmbPerguntaUsuario.getSelectedIndex() > 0
						&& cmbPerguntaUsuario.getSelectedIndex() != cmbPerguntaUsuario
								.getItemCount() - 1) {
					txtPerguntaUsuario.setVisible(false);
					btnCancelarPergunta.setVisible(false);
					cmbPerguntaUsuario.setVisible(true);
					cmbPerguntaUsuario.setSelectedItem(UsuarioController
							.getUsuario(
									UsuarioController.getUsuarioLogado()
											.getCod()).getPergunta());
				} else {
					txtPerguntaUsuario.setVisible(true);
					btnCancelarPergunta.setVisible(true);
					cmbPerguntaUsuario.setVisible(false);
					txtPerguntaUsuario.setText(UsuarioController.getUsuario(
							UsuarioController.getUsuarioLogado().getCod())
							.getPergunta());
				}
			}
			setEnableCamposFalse();
			tblListar.setEnabled(true);
			txtLoginUsuario.grabFocus();
			atualizaCombo();
		}

		if (e.getSource().equals(lblRelatorioUsuario)
				&& lblRelatorioUsuario.isEnabled()) {
			if (tblListar.getSelectedRow() != -1) {
				GerarRelatorioUsuario gru = new GerarRelatorioUsuario();
				ArrayList<RelatorioUsuarios> relatorioUsuarios = new ArrayList<RelatorioUsuarios>();
				RelatorioUsuarios ru = new RelatorioUsuarios();
				ru.setLogin(tblListar.getValueAt(tblListar.getSelectedRow(), 0)
						.toString());
				ru.setFuncionario(tblListar.getValueAt(
						tblListar.getSelectedRow(), 1).toString());
				ru.setTipo(tblListar.getValueAt(tblListar.getSelectedRow(), 2)
						.toString());
				ru.setPergunta(tblListar.getValueAt(tblListar.getSelectedRow(),
						3).toString());
				ru.setData("");
				ru.setDataCompleta("");
				relatorioUsuarios.add(ru);
				gru.gerarUsuarios(relatorioUsuarios);
			} else if (tblListar.getRowCount() > 0) {
				GerarRelatorioUsuario gru = new GerarRelatorioUsuario();
				ArrayList<RelatorioUsuarios> relatorioUsuarios = new ArrayList<RelatorioUsuarios>();
				for (int i = 0; i < tblListar.getRowCount(); i++) {
					RelatorioUsuarios ru = new RelatorioUsuarios();
					ru.setLogin(tblListar.getValueAt(i, 0).toString());
					ru.setFuncionario(tblListar.getValueAt(i, 1).toString());
					ru.setTipo(tblListar.getValueAt(i, 2).toString());
					ru.setPergunta(tblListar.getValueAt(i, 3).toString());
					ru.setData("");
					ru.setDataCompleta("");
					relatorioUsuarios.add(ru);
				}
				gru.gerarUsuarios(relatorioUsuarios);
			}
		}
	}

	private void limitacoesFuncionario() {
		btnAvancar.setVisible(false);
		btnVoltar.setVisible(false);
		btnUltLinha.setVisible(false);
		btnPrimLinha.setVisible(false);
		lblNovoUsuario.setEnabled(false);

		if (tblListar.getSelectedRow() < 0)
			tblListar.setRowSelectionInterval(tblListar.getSelectedRow() + 1,
					tblListar.getSelectedRow() + 1);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource().equals(lblNovoUsuario) && lblNovoUsuario.isEnabled()) {
			lblNovoUsuario.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblSalvarUsuario)
				&& lblSalvarUsuario.isEnabled()) {
			lblSalvarUsuario.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblEditarUsuario)
				&& lblEditarUsuario.isEnabled()) {
			lblEditarUsuario.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblCancelar) && lblCancelar.isEnabled()) {
			lblCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblRelatorioUsuario)
				&& lblRelatorioUsuario.isEnabled()) {
			lblRelatorioUsuario.setCursor(new Cursor(Cursor.HAND_CURSOR));
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

	private void setCamposDadosUsuario(Usuario u) {
		if (u.getCod() > 0)
			cod = u.getCod();
		txtLoginUsuario.setText(u.getLogin());
		cmbTipoUsuario.setSelectedItem(u.getTipo());
		cmbPerguntaUsuario.setSelectedIndex(0);
		cmbPerguntaUsuario.setSelectedItem(u.getPergunta());
		cmbPerguntaUsuario.setVisible(true);
		txtPerguntaUsuario.setVisible(false);
		if (cmbPerguntaUsuario.getSelectedIndex() < 1) {
			txtPerguntaUsuario.setText(u.getPergunta());
			cmbPerguntaUsuario.setVisible(false);
			txtPerguntaUsuario.setVisible(true);
			btnCancelarPergunta.setVisible(true);
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (tblListar.getSelectedRow() != -1
				&& !UsuarioController.getUsuarioLogado().getTipo().equals(fun)) {
			setEnableBtnsLista();
			lblNovoUsuario.setEnabled(false);
			setCamposDadosUsuario(UsuarioController.getUsuario(listaAtualTabela
					.get(tblListar.getSelectedRow()).getCod()));
		}

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (tblListar.isEnabled() && tblListar.getSelectedRow() > -1) {
			if (e.getKeyCode() == e.VK_DOWN || e.getKeyCode() == e.VK_RIGHT) {
				setCamposDadosUsuario(UsuarioController
						.getUsuario(listaAtualTabela.get(
								tblListar.getSelectedRow()).getCod()));
				setEnableBtnsLista();
			}
			if (e.getKeyCode() == e.VK_UP || e.getKeyCode() == e.VK_LEFT) {
				setCamposDadosUsuario(UsuarioController
						.getUsuario(listaAtualTabela.get(
								tblListar.getSelectedRow()).getCod()));
				setEnableBtnsLista();
			}
		}

		if (txtLoginUsuario.hasFocus()) {
			if (UsuarioController.getUsuarioLogado().getTipo().equals(adm))
				pesquisa();
			
			if(e.getKeyChar()==e.VK_SPACE || txtLoginUsuario.getText().lastIndexOf(' ')>0)
				lblInfoLogin
				.setText("Insira um login sem espaços");
			else if (UsuarioController.verificaDuplicidadeLogin(
					txtLoginUsuario.getText(), cod)
					&& txtBtn.length() > 2)
				lblInfoLogin
						.setText("Este login já foi cadastrado, insira outro");
			else{
				lblInfoLogin.setText("");
			}
		}

		if (txtSenha.hasFocus() || txtConfirmaSenha.hasFocus()) {
			if (txtSenha.getText().length() < 6) {
				lblInfoSenha.setText("A senha deve conter no mínimo 6 letras");
			} else {
				if (!verificaSenhasCoincidem()) {
					lblInfoSenha.setText("As senhas não coincidem");
				} else {
					lblInfoSenha.setText("");
				}
			}
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Funções dos botões:

		if (e.getSource() == btnPrimLinha) {
			lblNovoUsuario.setEnabled(false);
			tblListar.setRowSelectionInterval(0, 0);
			setEnableBtnsLista();

			setCamposDadosUsuario(UsuarioController.getUsuario(listaAtualTabela
					.get(tblListar.getSelectedRow()).getCod()));
			tblListar.grabFocus();
		}

		if (e.getSource() == btnUltLinha) {
			lblNovoUsuario.setEnabled(false);
			tblListar.setRowSelectionInterval(tblListar.getModel()
					.getRowCount() - 1, tblListar.getModel().getRowCount() - 1);
			setEnableBtnsLista();

			setCamposDadosUsuario(UsuarioController.getUsuario(listaAtualTabela
					.get(tblListar.getSelectedRow()).getCod()));
			tblListar.grabFocus();
		}

		if (e.getSource() == btnAvancar) {
			if (tblListar.getSelectedRow() < tblListar.getModel().getRowCount() - 1) {
				lblNovoUsuario.setEnabled(false);
				tblListar.setRowSelectionInterval(
						tblListar.getSelectedRow() + 1,
						tblListar.getSelectedRow() + 1);
				setEnableBtnsLista();

				setCamposDadosUsuario(UsuarioController
						.getUsuario(listaAtualTabela.get(
								tblListar.getSelectedRow()).getCod()));
				tblListar.grabFocus();
			}
		}

		if (e.getSource() == btnVoltar) {
			if (tblListar.getSelectedRow() > 0) {
				lblNovoUsuario.setEnabled(false);
				tblListar.setRowSelectionInterval(
						tblListar.getSelectedRow() - 1,
						tblListar.getSelectedRow() - 1);
				setEnableBtnsLista();

				setCamposDadosUsuario(UsuarioController
						.getUsuario(listaAtualTabela.get(
								tblListar.getSelectedRow()).getCod()));
				tblListar.grabFocus();
			} else {
				txtLoginUsuario.grabFocus();
				lblNovoUsuario.setEnabled(true);
				tblListar.clearSelection();
				lblEditarUsuario.setEnabled(false);
				limpaCampos();
			}
		}

		if (e.getSource() == cmbPerguntaUsuario) {
			if (cmbPerguntaUsuario.getSelectedItem().toString()
					.equals("Adicionar uma pergunta")) {
				txtPerguntaUsuario.setVisible(true);
				cmbPerguntaUsuario.setVisible(false);
				btnCancelarPergunta.setVisible(true);
				txtPerguntaUsuario.grabFocus();
			}
		}

		if (e.getSource() == btnCancelarPergunta) {
			txtPerguntaUsuario.setVisible(false);
			cmbPerguntaUsuario.setVisible(true);
			btnCancelarPergunta.setVisible(false);
			cmbPerguntaUsuario.setSelectedIndex(0);
			txtPerguntaUsuario.setText("");
		}
	}

	private void setEnableBtnsLista() {
		if (!UsuarioController.getUsuarioLogado().getTipo().equals(fun)
				&& tblListar.getSelectedRow() == 0)
			lblEditarUsuario.setEnabled(true);
		else
			lblEditarUsuario.setEnabled(false);
	}

	private void pesquisa() {
		if (txtLoginUsuario.getText().length() > 0) {
			atualizaTabelaPesquisa(modelT);
		} else
			atualizaTabela(modelT);
	}

	private void atualizaTabelaPesquisa(FTable modelo) {
		modelo.setNumRows(0);
		if (UsuarioController.getUsuarios(txtLoginUsuario.getText()) != null) {
			atualizaTabelaUsuarioLogado(modelo);
			listaAtualTabela = UsuarioController.getUsuarios(txtLoginUsuario
					.getText());
			for (Usuario u : UsuarioController.getUsuarios(txtLoginUsuario
					.getText())) {
				if (u.getCod() != UsuarioController.getUsuarioLogado().getCod())
					modelo.addRow(new Object[] { u.getLogin(),
							u.getFuncionario(), u.getTipo(), u.getPergunta() });
			}
		}
	}

	private void atualizaTabela(FTable modelo) {
		modelo.setNumRows(0);
		if (UsuarioController.getUsuarios() != null) {
			atualizaTabelaUsuarioLogado(modelo);
			listaAtualTabela = UsuarioController.getUsuarios();
			for (Usuario u : UsuarioController.getUsuarios()) {
				if (u.getCod() != UsuarioController.getUsuarioLogado().getCod())
					modelo.addRow(new Object[] { u.getLogin(),
							u.getFuncionario(), u.getTipo(), u.getPergunta() });
			}
		}
	}

	private void atualizaTabelaUsuarioLogado(FTable modelo) {
		modelo.setNumRows(0);
		Usuario u = UsuarioController.getUsuario(UsuarioController
				.getUsuarioLogado().getCod());

		listaAtualTabela.add(u);
		modelo.addRow(new Object[] { u.getLogin(), u.getFuncionario(),
				u.getTipo(), u.getPergunta() });

	}

	private void setEnableCamposTrue() {
		txtSenha.setEnabled(true);
		txtConfirmaSenha.setEnabled(true);
		if (UsuarioController.getUsuarioLogado().getTipo().equals(adm))
			cmbNomeFuncionario.setEnabled(true);
		cmbPerguntaUsuario.setEnabled(true);
		if (txtBtn.equals(clickNovo)
				&& UsuarioController.getUsuarioLogado().getTipo().equals(adm))
			cmbTipoUsuario.setEnabled(true);
		txtPerguntaUsuario.setEnabled(true);
		txtRespostaUsuario.setEnabled(true);
		btnCancelarPergunta.setEnabled(true);
		btnPrimLinha.setEnabled(false);
		btnUltLinha.setEnabled(false);
		btnVoltar.setEnabled(false);
		btnAvancar.setEnabled(false);
		lblRelatorioUsuario.setEnabled(false);
	}

	private void setEnableCamposFalse() {
		txtSenha.setEnabled(false);
		txtConfirmaSenha.setEnabled(false);
		cmbNomeFuncionario.setEnabled(false);
		cmbPerguntaUsuario.setEnabled(false);
		cmbTipoUsuario.setEnabled(false);
		btnCancelarPergunta.setEnabled(false);
		txtPerguntaUsuario.setEnabled(false);
		txtRespostaUsuario.setEnabled(false);
		btnPrimLinha.setEnabled(true);
		btnUltLinha.setEnabled(true);
		btnVoltar.setEnabled(true);
		btnAvancar.setEnabled(true);
		verificaRelatorio();
	}

	private void limpaCampos() {
		txtLoginUsuario.setText("");
		txtSenha.setText("");
		txtConfirmaSenha.setText("");
		txtPerguntaUsuario.setText("");
		txtRespostaUsuario.setText("");
		cmbNomeFuncionario.setSelectedIndex(0);
		cmbPerguntaUsuario.setSelectedIndex(0);
		cmbTipoUsuario.setSelectedIndex(0);
		lblInfoSenha.setText("");
		btnCancelarPergunta.setVisible(false);
		txtPerguntaUsuario.setVisible(false);
		cmbPerguntaUsuario.setVisible(true);
		lblInfoLogin.setText("");
		cod = 0;
	}

	private boolean verificaCamposPreenchidos() {
		if (cmbTipoUsuario.getSelectedIndex() > 0
				&& txtLoginUsuario.getText().length() > 0
				&& lblInfoSenha.getText().equals("")
				&& ((cmbPerguntaUsuario.getSelectedIndex() > 0 && cmbPerguntaUsuario
						.getSelectedIndex() != cmbPerguntaUsuario
						.getItemCount() - 1) || txtPerguntaUsuario.getText()
						.length() > 0) && lblInfoLogin.getText().length() < 1)
			if (txtBtn.equals(clickEditar)
					&& (txtSenha.getText().length() >= 6 || txtSenha.getText()
							.length() == 0)
					&& (txtConfirmaSenha.getText().length() >= 6 || txtConfirmaSenha
							.getText().length() == 0))
				return true;
			else if (cmbNomeFuncionario.getSelectedIndex() > 0
					&& txtRespostaUsuario.getText().length() > 0
					&& txtSenha.getText().length() >= 6
					&& txtConfirmaSenha.getText().length() >= 6)
				return true;
		return false;
	}

	private boolean verificaSenhasCoincidem() {
		if (txtSenha.getText().equals(txtConfirmaSenha.getText()))
			return true;
		return false;
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
		jh.setVisible(true);

	}

	@Override
	public void windowClosing(WindowEvent e) {

	}

	@Override
	public void windowDeactivated(WindowEvent e) {

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
			lblRelatorioUsuario.setEnabled(false);
			return false;
		}
		lblRelatorioUsuario.setEnabled(true);
		return true;
	}

}
