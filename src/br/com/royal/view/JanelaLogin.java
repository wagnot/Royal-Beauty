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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import br.com.royal.controller.BancoController;
import br.com.royal.controller.UsuarioController;
import br.com.royal.model.Login;
import br.com.royal.model.Usuario;

public class JanelaLogin extends JFrame implements ActionListener,
		MouseListener, KeyListener {

	private BancoController bc = new BancoController();

	private JPanel pnTeste;
	private JLabel lblFundo, lblNav, lblNome, lblLogin, lblPassword,
			lblEsqueceuSenha, lblImage;
	private ImageIcon imgFundoPanel, botaoRoyal;
	private JButton btnLogin, btnSair;
	private JTextField txtLogin, txtNome;
	private JPasswordField txtSenha;

	// criando um objeto privado do tipo ImageIcon
	public JanelaLogin() {
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}
		this.setSize(704, 446);
		this.setTitle("Bem vindo ao Royal Beauty!");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setLayout(null);
		this.setUndecorated(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Img/Rosa.png"));
		inicializaComponentes();
		this.setVisible(true);
	}

	public void inicializaComponentes() {
		// instanciando o objeto ImageIcon e setando uma imagem que está no
		// mesmo pacote que ESTA classe

		
		lblFundo = new JLabel(new ImageIcon("Img/fundoLogin.png"));
		lblFundo.setBounds(0, 0, getWidth(), getHeight());
		lblLogin = new JLabel("Usuário: ");
		lblLogin.setBounds(160, 136, 200, 30);
		lblLogin.setFont(new Font("Century Gothic", Font.PLAIN, 24));
		lblLogin.setForeground(new Color(158, 0, 57));
		add(lblLogin);

		txtLogin = new JTextField();
		txtLogin.setBounds(180, 170, 350, 40);
		txtLogin.setFont(new Font("Century Gothic", Font.PLAIN, 24));
		txtLogin.setForeground(new Color(202, 40, 89));
		add(txtLogin);

		lblPassword = new JLabel("Senha: ");
		lblPassword.setBounds(160, lblLogin.getY() + 90, 200, 30);
		lblPassword.setFont(new Font("Century Gothic", Font.PLAIN, 24));
		lblPassword.setForeground(new Color(158, 0, 57));

		add(lblPassword);

		txtSenha = new JPasswordField();
		txtSenha.setBounds(180, 260, 350, 40);
		txtSenha.setFont(new Font("Century Gothic", Font.PLAIN, 24));
		txtSenha.setForeground(new Color(158, 0, 57));
		add(txtSenha);

		lblEsqueceuSenha = new JLabel("           ");
		lblEsqueceuSenha.setBounds(363, txtSenha.getY() + 24, 210, 50);
		lblEsqueceuSenha.setForeground(new Color(178, 82, 104));
		lblEsqueceuSenha.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblEsqueceuSenha.addMouseListener(this);
		add(lblEsqueceuSenha);

		btnLogin = new JButton("Login");
		btnLogin.setBounds(210, lblEsqueceuSenha.getY() + 60, 122, 40);
		btnLogin.setFont(new Font("Century Gothic", Font.PLAIN, 28));
		btnLogin.setForeground(new Color(255, 255, 255));
		btnLogin.setBackground(new Color(202, 40, 89));
		btnLogin.addActionListener(this);
		add(btnLogin);

		btnSair = new JButton("Sair");
		btnSair.setBounds(400, lblEsqueceuSenha.getY() + 60, 122, 40);
		btnSair.setFont(new Font("Century Gothic", Font.PLAIN, 28));
		btnSair.setForeground(new Color(255, 255, 255));
		btnSair.setBackground(new Color(202, 40, 89));
		btnSair.addActionListener(this);
		add(btnSair);

		txtLogin.addKeyListener(this);
		txtSenha.addKeyListener(this);
		btnLogin.addKeyListener(this);
		btnSair.addKeyListener(this);
		this.addKeyListener(this);

		this.add(lblFundo);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		BancoController bc = new BancoController();

		if (arg0.getSource() == btnLogin) {
			if (btnLogin.getText().equals("Login")) {
				Logar();
			}
		}
		if (arg0.getSource() == btnSair) {

			this.dispose();

		}

	}

	public void Logar() {
		Login l = new Login();
		try {
			if (txtLogin.getText().length() > 0
					&& txtSenha.getText().length() > 0) {
				// l.setNome(txtNome.getText());
				l.setUser(txtLogin.getText());
				l.setSenhaCriptografada(txtSenha.getText());
				if (bc.loginUser(l)) {
					UsuarioController.setUsuarioLogado(UsuarioController
							.getUsuario(txtLogin.getText()));
					dispose();
					new JanelaHome();
				} else
					JOptionPane.showMessageDialog(this,
							"Login ou senha incorretos");
			} else if (txtLogin.getText().equals("")) {
				JOptionPane.showMessageDialog(this,
						"Você esqueceu de preencher o campo: Login!");
			} else if (txtSenha.getText().equals("")) {
				JOptionPane.showMessageDialog(this,
						"Você esqueceu de preencher o campo: Senha!");
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Login ou senha incorretos");
		}

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		new JanelaRecuperaSenha();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == e.VK_ENTER)
			if (e.getSource() == btnSair)
				this.dispose();
			else
				Logar();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode()==e.VK_ESCAPE)
			this.dispose();
	}	
	
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
