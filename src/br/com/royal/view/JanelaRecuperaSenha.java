package br.com.royal.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import br.com.royal.controller.BancoController;
import br.com.royal.model.Login;

public class JanelaRecuperaSenha extends JFrame implements ActionListener,
		KeyListener, MouseListener {
	private JPanel pnTeste;
	private JLabel lblFundo, lblTeste, lblNav, lblPergunta, lblUser,
			lblPerguntaSeg, lblResposta;
	private ImageIcon imgFundoPanel;
	private JButton btnVoltar, btnOk;
	private JTextField txtLogin, txtUser, txtResposta;
	private int contador=0;
	
	public JanelaRecuperaSenha() {
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}
		this.setSize(704, 446);
		this.setTitle("Olá! Recupere sua senha!");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setLayout(null);
		this.setUndecorated(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Img/Rosa.png"));
		init();
		this.setVisible(true);
	}

	private void init() {

		imgFundoPanel = new ImageIcon("Img/fundoLogin.png");
		pnTeste = new JPanel();
		pnTeste.setOpaque(true);
		pnTeste.setBackground(new Color(0, 55, 83));
		pnTeste.setBounds(0, 0, getWidth(), getHeight());
		lblTeste = new JLabel();
		lblTeste.setOpaque(true);
		// lblTeste.setIcon(imgFundoPanel);
		lblTeste.setBackground(new Color(200, 191, 231));
		lblTeste.setBounds(0, 100, pnTeste.getWidth(), pnTeste.getHeight());
		lblNav = new JLabel();
		lblNav.setOpaque(true);
		lblNav.setBackground(new Color(242, 250, 223));
		lblNav.setBounds(0, 0, pnTeste.getWidth(), pnTeste.getHeight());
		pnTeste.setLayout(null);
		pnTeste.add(lblNav);
		pnTeste.add(lblTeste);

		lblUser = new JLabel("Digite seu usuário: ");
		lblUser.setBounds(260, 30, 300, 30);
		lblUser.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		add(lblUser);

		txtUser = new JTextField("");
		txtUser.setBounds(160, lblUser.getY() + 50, 400, 40);
		txtUser.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		add(txtUser);
		txtUser.addKeyListener(this);

		lblPergunta = new JLabel("Sua pergunta de segurança é:", JLabel.CENTER);
		lblPergunta.setBounds(0, txtUser.getY() + 50, getWidth(), 40);
		lblPergunta.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		add(lblPergunta);

		lblPerguntaSeg = new JLabel("", JLabel.CENTER);
		lblPerguntaSeg.setBounds(0, lblPergunta.getY() + 50, getWidth(), 40);
		lblPerguntaSeg.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		add(lblPerguntaSeg);
		lblPerguntaSeg.setForeground(Color.red);

		lblResposta = new JLabel("Sua resposta é: ");
		lblResposta.setBounds(280, lblPerguntaSeg.getY() + 50, 500, 40);
		lblResposta.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		lblResposta.setVisible(true);
		add(lblResposta);

		txtResposta = new JTextField();
		txtResposta.setBounds(160, lblResposta.getY() + 50, 400, 40);
		txtResposta.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		txtResposta.setVisible(true);
		add(txtResposta);
		txtResposta.setEnabled(false);
		txtResposta.addKeyListener(this);

		btnOk = new JButton("Confirma");
		btnOk.setBounds(190, 350, 150, 40);
		btnOk.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		btnOk.setForeground(new Color(255, 255, 255));
		btnOk.setBackground(new Color(202, 40, 89));
		btnOk.addActionListener(this);
		btnOk.addKeyListener(this);
		add(btnOk);

		// btnLogin = new JButton("Login");
		// btnLogin.setBounds(150, 250, 150, 40);
		// btnLogin.setFont(new Font("Century Gothic", Font.PLAIN, 28));
		// btnLogin.setForeground(new Color(255,255,255));
		// btnLogin.setBackground(new Color(202, 40, 89));
		// btnLogin.addActionListener(this);
		// add(btnLogin);

		btnVoltar = new JButton("Voltar");
		btnVoltar.setBounds(380, 350, btnOk.getWidth(), 40);
		btnVoltar.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		btnVoltar.setForeground(new Color(255, 255, 255));
		btnVoltar.setBackground(new Color(202, 40, 89));
		btnVoltar.addActionListener(this);
		btnVoltar.addKeyListener(this);
		add(btnVoltar);

		lblFundo = new JLabel(new ImageIcon(
				"Img/Fundo Recuperação de Senha.png"));
		lblFundo.setBounds(0, 0, getWidth(), getHeight());
		add(lblFundo);

		this.add(pnTeste);

	}

	private BancoController bc = new BancoController();

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnVoltar)
			this.dispose();
		if (e.getSource() == btnOk) {
			verificaResposta();
			
		}

	}

	private void verificaResposta() {
		Login l = bc.getUsers(txtUser.getText());
		if (txtResposta.getText().equals(l.getResposta())) {
			JanelaNovaSenha ns = new JanelaNovaSenha(this,
					txtUser.getText());
			this.dispose();
		}else if(!txtResposta.isEnabled()){
			JOptionPane.showMessageDialog(this, "Insira um usuário ja cadastrado");
			
		}	else if(contador==5){
			JOptionPane.showMessageDialog(this, "Você errou 5 vezes\n"
					+ "Tente novamente mais tarde");
			this.dispose();
		}else{
			contador++;
			JOptionPane.showMessageDialog(this, "Resposta incorreta");
			txtResposta.grabFocus();
		}
	}

	public void Recuperar() {
		Login l = new Login();
		try {
			if (txtUser.getText().length() > 0) {
				l.setResposta(txtResposta.getText());
				if (l.equals("respostaSecreta"))
					bc.recoveryUser(l);
			}
			if (txtResposta.equals(""))
				JOptionPane.showMessageDialog(this, "Digite a resposta! ");
			else {
				JOptionPane.showMessageDialog(this, "Ok");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==e.VK_ENTER){
			if(btnVoltar.hasFocus())
				this.dispose();
			else
				verificaResposta();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		BancoController bc = new BancoController();
		if (txtUser.hasFocus()) {
			if (bc.verificaLogin(txtUser.getText())) {
				Login l = bc.getUsers(txtUser.getText());
				lblPerguntaSeg.setText(l.getPergunta());
				txtUser.setBorder(new LineBorder(Color.GREEN));
				txtResposta.setEnabled(true);

			} else {
				txtResposta.setText("");
				lblPerguntaSeg.setText("");
				txtResposta.setEnabled(false);
				txtUser.setBorder(new LineBorder(Color.red));
			}
		}
		
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
