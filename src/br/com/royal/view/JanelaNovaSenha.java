package br.com.royal.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import br.com.royal.controller.BancoController;
import br.com.royal.model.Login;

public class JanelaNovaSenha extends JDialog implements ActionListener, KeyListener {

	private JPanel pnTeste;
	private JLabel lblFundo, lblCorDeFundo, lblNovaSenha, lblConfirmaSenha,
			lblInfo;
	private JPasswordField txtPassword, txtNewPassword;
	private JButton btnConfirma, btnCancelar;
	private String nomeUsuario = "";

	public JanelaNovaSenha(JFrame jframe, String nomeUsuario) {
		super(jframe, true);
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}
		this.nomeUsuario = nomeUsuario;
		System.out.println(nomeUsuario);
		this.setSize(504, 346);
		this.setTitle("Nova Senha!");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setUndecorated(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Img/Rosa.png"));
		init();
		this.setVisible(true);
	}

	private void init() {

		lblNovaSenha = new JLabel("Digite sua nova senha:");
		lblNovaSenha.setBounds(140, 30, 300, 30);
		lblNovaSenha.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		add(lblNovaSenha);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(90, lblNovaSenha.getY() + 40, 330, 40);
		txtPassword.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		add(txtPassword);
		txtPassword.addKeyListener(this);

		lblConfirmaSenha = new JLabel("Confirme sua nova senha:");
		lblConfirmaSenha.setBounds(120, 150, 300, 30);
		lblConfirmaSenha.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		add(lblConfirmaSenha);

		txtNewPassword = new JPasswordField();
		txtNewPassword.setBounds(90, lblConfirmaSenha.getY() + 40, 330, 40);
		txtNewPassword.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		add(txtNewPassword);
		txtNewPassword.addKeyListener(this);

		lblInfo = new JLabel();
		lblInfo.setBounds(90, txtNewPassword.getY() + 30, 330, 40);
		lblInfo.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		add(lblInfo);

		btnConfirma = new JButton("Ok");
		btnConfirma.setBounds(90, txtNewPassword.getY() + 80, 150, 40);
		btnConfirma.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		btnConfirma.setForeground(new Color(255, 255, 255));
		btnConfirma.setBackground(new Color(202, 40, 89));
		btnConfirma.addActionListener(this);
		add(btnConfirma);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(270, txtNewPassword.getY() + 80, 150, 40);
		btnCancelar.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		btnCancelar.setForeground(new Color(255, 255, 255));
		btnCancelar.setBackground(new Color(202, 40, 89));
		btnCancelar.addActionListener(this);
		add(btnCancelar);

		lblCorDeFundo = new JLabel();
		lblCorDeFundo.setBounds(0, 0, getWidth(), getHeight());
		lblCorDeFundo.setOpaque(true);
		lblCorDeFundo.setBackground(new Color(217, 210, 238));

		lblFundo = new JLabel(new ImageIcon("Img/Fundo Tela Nova Senha.png"));
		lblFundo.setBounds(0, 0, getWidth(), getHeight());
		add(lblFundo);
		add(lblCorDeFundo);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnConfirma) {
			if (txtPassword.getText().length() >= 6
					&& txtPassword.getText().equals(txtNewPassword.getText())) {
				Login l = new Login();
				try {
					l.setSenhaCriptografada(txtPassword.getText());
				} catch (Exception ex) {}
				l.setNome(nomeUsuario);
				BancoController.editarUsuario(l);
				;

				JOptionPane.showMessageDialog(this,
						"Senha alterada com sucesso!");
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this,
						"Preencha todos os campos corretamente");
			}
		}

		if (e.getSource() == btnCancelar) {
			if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this,
					"Tem certeza?")) {
				this.dispose();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (txtPassword.hasFocus()) {
			if (txtPassword.getText().length() < 6) {
				lblInfo.setText("A senha deve conter pelo menos 6 digitos");
			} else
				lblInfo.setText("");
		}

		if (txtNewPassword.hasFocus() && txtPassword.getText().length() >= 6) {
			if (!txtPassword.getText().equals(txtNewPassword.getText())) {
				lblInfo.setText("As senhas não coincidem");
			} else
				lblInfo.setText("");
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
