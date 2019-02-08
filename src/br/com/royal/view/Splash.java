package br.com.royal.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class Splash extends JFrame implements WindowListener {
	private JanelaLogin janelaLogin;
	private JLabel lblSeguradorDaBarra, lblBarra, lblParteBaixoBarra,
			lblFlowersDireita, lblFlowersEsquerda, lblPorcentagem, lblFundo,
			lblSeguradorFonteLogo, lblFonteLogoSplash, lblSeguradorFlowerLogo,
			lblFlowerLogoSplash;
	private Timer timerBarra, timerEsquerda, timerDireita,
			timerLimpaQuandoCarrega, timerFonteLogo, timerAuxiliarFonteLogo,
			timerFlowerLogo, timerAuxiliarFlowerLogo, timerAuxiliarLimpeza,
			timerAuxiliarFinaliza, timerFinaliza;
	private int larguraBarra = 0, porcentagem = 0, alturaEsquerda = 320,
			alturaDireita = -204, larguraParteBaixoBarra = 450,
			larguraFonteLogo = 0, alturaFlowerLogo = 0, larguraTela = 500,
			alturaTela = 320;;

	public Splash() {
		setSize(500, 320);
		setResizable(false);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// setBackground(new Color (0, 0, 0, 0));
		addWindowListener(this);
		inicializaComponentes();
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Img/Rosa.png"));
		setVisible(true);
	}

	public void inicializaComponentes() {
		janelaLogin = new JanelaLogin();
		janelaLogin.setVisible(false);
		timerBarra = new Timer(15, acoesTimerBarra);
		timerEsquerda = new Timer(30, acoesTimerEsquerda);
		timerDireita = new Timer(32, acoesTimerDireita);
		timerLimpaQuandoCarrega = new Timer(1, acoesTimerLimpador);
		timerAuxiliarLimpeza = new Timer(1000, acoesTimerAuxiliarLimpeza);
		timerFonteLogo = new Timer(5, acoesTimerFonteLogo);
		timerAuxiliarFonteLogo = new Timer(5, acoesTimerAuxiliarFonteLogo);
		timerFlowerLogo = new Timer(5, acoesFlowerLogo);
		timerAuxiliarFlowerLogo = new Timer(500, acoesTimerAuxiliarFlowerLogo);
		timerFinaliza = new Timer(1, acoesTimerFinaliza);
		timerAuxiliarFinaliza = new Timer(1000, acoesTimerAuxiliarFinaliza);

		lblSeguradorDaBarra = new JLabel();
		// lblSeguradorDaBarra.setOpaque(true);
		// lblSeguradorDaBarra.setBackground(Color.GREEN);
		lblSeguradorDaBarra.setLayout(null);
		lblSeguradorDaBarra.setBounds(20, 140, 460, 23);

		lblBarra = new JLabel(new ImageIcon("Img/barra de carregamento.png"));
		// lblBarra.setOpaque(true);
		// lblBarra.setBackground(Color.GREEN);
		lblBarra.setBounds(5, 5, 450, 13);
		lblSeguradorDaBarra.add(lblBarra);
		add(lblSeguradorDaBarra);

		lblParteBaixoBarra = new JLabel(new ImageIcon(
				"Img/Complemento de Baixo da Barra.png"));
		// lblParteBaixoBarra.setOpaque(true);
		// lblParteBaixoBarra.setBackground(Color.GREEN);
		lblParteBaixoBarra.setBounds(28, lblSeguradorDaBarra.getY() + 13, 450,
				40);
		add(lblParteBaixoBarra);

		lblPorcentagem = new JLabel();
		lblPorcentagem.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblPorcentagem.setForeground(new Color(164, 6, 69));
		// lblPorcentagem.setOpaque(true);
		// lblPorcentagem.setBackground(Color.GREEN);
		lblPorcentagem.setAlignmentX(CENTER_ALIGNMENT);

		lblPorcentagem.setBounds(235, 120, 50, 25);
		add(lblPorcentagem);

		lblFlowersEsquerda = new JLabel(new ImageIcon(
				"Img/Complemento de Tela da Barra - Esquerda.png"));
		// lblFlowersEsquerda.setOpaque(true);
		// lblFlowersEsquerda.setBackground(Color.GREEN);
		lblFlowersEsquerda.setBounds(5, 320, 110, 216);
		add(lblFlowersEsquerda);

		lblFlowersDireita = new JLabel(new ImageIcon(
				"Img/Complemento de Tela da Barra - Direita.png"));
		// lblFlowersDireita.setOpaque(true);
		// lblFlowersDireita.setBackground(Color.GREEN);
		lblFlowersDireita.setBounds(413, -204, 84, 204);
		add(lblFlowersDireita);

		lblSeguradorFlowerLogo = new JLabel();
		// lblSeguradorFlowerLogo.setOpaque(true);
		// lblSeguradorFlowerLogo.setBackground(Color.GREEN);
		lblSeguradorFlowerLogo.setBounds(153, 100, 73, 0);
		lblSeguradorFlowerLogo.setLayout(null);

		lblFlowerLogoSplash = new JLabel(new ImageIcon("Img/Flower Royal.png"));
		// lblFlowerLogoSplash.setOpaque(true);
		// lblFlowerLogoSplash.setBackground(Color.GREEN);
		lblFlowerLogoSplash.setBounds(0, 5, 73, 73);
		lblSeguradorFlowerLogo.add(lblFlowerLogoSplash);
		add(lblSeguradorFlowerLogo);

		lblSeguradorFonteLogo = new JLabel();
		// lblSeguradorFonteLogo.setOpaque(true);
		// lblSeguradorFonteLogo.setBackground(Color.GREEN);
		lblSeguradorFonteLogo.setBounds(85, 105, 0, 109);
		lblSeguradorDaBarra.setLayout(null);

		lblFonteLogoSplash = new JLabel(new ImageIcon("Img/Fonte Royal.png"));
		// lblLogoSplash.setOpaque(true);
		// lblLogoSplash.setBackground(Color.GREEN);
		lblFonteLogoSplash.setBounds(5, 0, 306, 109);
		lblSeguradorFonteLogo.add(lblFonteLogoSplash);
		add(lblSeguradorFonteLogo);

		lblFundo = new JLabel(new ImageIcon("Img/fundoSplash.png"));
		lblFundo.setBounds(0, 0, getWidth(), getHeight());
		add(lblFundo);

		timerBarra.start();
	}

	ActionListener acoesTimerBarra = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (larguraBarra < 460)
				carregaBarra();
			else {
				timerBarra.stop();
				porcentagem = 100;
				lblPorcentagem.setText(porcentagem + "%");
				lblPorcentagem.setBounds(220, lblPorcentagem.getY(),
						lblPorcentagem.getWidth(), lblPorcentagem.getHeight());
				timerAuxiliarLimpeza.start();
			}

		}
	};

	ActionListener acoesTimerEsquerda = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			mostraTexturaEsquerda();

			if (alturaEsquerda == 100) {
				timerEsquerda.stop();
			}

		}
	};

	ActionListener acoesTimerDireita = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			mostraTexturaDireita();

			if (alturaDireita == 3) {
				timerDireita.stop();
			}
		}
	};

	ActionListener acoesTimerLimpador = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (larguraBarra > 0 && larguraParteBaixoBarra > 0)
				limpaComponentesDaBarraDeCarregamento();
			else {
				lblBarra.setVisible(false);
				lblParteBaixoBarra.setVisible(false);
				timerLimpaQuandoCarrega.stop();
			}
		}
	};

	ActionListener acoesTimerFonteLogo = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (larguraFonteLogo < 306)
				mostraFonteLogo();
			else {
				timerFonteLogo.stop();
				timerAuxiliarFinaliza.start();
			}
		}
	};

	ActionListener acoesTimerAuxiliarFonteLogo = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			timerFonteLogo.start();
			timerAuxiliarFonteLogo.stop();
		}
	};

	ActionListener acoesTimerAuxiliarLimpeza = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			timerLimpaQuandoCarrega.start();
			timerAuxiliarFlowerLogo.start();
			timerAuxiliarLimpeza.stop();
		}
	};

	ActionListener acoesFlowerLogo = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (alturaFlowerLogo < 83)
				abreFlowerLogo();
			else {
				timerFlowerLogo.stop();
				timerAuxiliarFonteLogo.start();
			}
		}
	};

	ActionListener acoesTimerAuxiliarFlowerLogo = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			timerFlowerLogo.start();
			timerAuxiliarFlowerLogo.stop();
		}
	};

	ActionListener acoesTimerAuxiliarFinaliza = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			timerFinaliza.start();
			timerAuxiliarFinaliza.stop();
		}
	};

	ActionListener acoesTimerFinaliza = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (alturaTela > 0 && larguraTela > 0)
				finalizaSplash();
			else {
				timerFinaliza.stop();
				dispose();
			}
		}
	};

	public void carregaBarra() {
		larguraBarra++;
		if (larguraBarra % 5 == 0)
			porcentagem++;
		lblPorcentagem.setText(porcentagem + "%");
		lblSeguradorDaBarra.setBounds(lblSeguradorDaBarra.getX(),
				lblSeguradorDaBarra.getY(), larguraBarra,
				lblSeguradorDaBarra.getHeight());
		if (lblPorcentagem.getText().length() == 3) {
			lblPorcentagem.setBounds(230, lblPorcentagem.getY(),
					lblPorcentagem.getWidth(), lblPorcentagem.getHeight());
		}
		if (porcentagem == 12) {
			timerEsquerda.start();
			timerDireita.start();
		}
	}

	public void mostraTexturaEsquerda() {
		alturaEsquerda--;
		lblFlowersEsquerda.setBounds(lblFlowersEsquerda.getX(), alturaEsquerda,
				lblFlowersEsquerda.getWidth(), lblFlowersEsquerda.getHeight());
	}

	public void mostraTexturaDireita() {
		alturaDireita++;
		lblFlowersDireita.setBounds(lblFlowersDireita.getX(), alturaDireita,
				lblFlowersDireita.getWidth(), lblFlowersDireita.getHeight());
	}

	public void limpaComponentesDaBarraDeCarregamento() {
		lblPorcentagem.setVisible(false);
		larguraBarra -= 20;
		larguraParteBaixoBarra -= 18;
		lblBarra.setBounds(lblBarra.getX(), lblBarra.getY(), larguraBarra,
				lblBarra.getHeight());
		lblParteBaixoBarra.setBounds(lblParteBaixoBarra.getX(),
				lblParteBaixoBarra.getY(), larguraParteBaixoBarra,
				lblParteBaixoBarra.getHeight());
	}

	public void mostraFonteLogo() {
		larguraFonteLogo++;
		lblSeguradorFonteLogo.setBounds(lblSeguradorFonteLogo.getX(),
				lblSeguradorFonteLogo.getY(), larguraFonteLogo,
				lblSeguradorFonteLogo.getHeight());
	}

	public void abreFlowerLogo() {
		alturaFlowerLogo++;
		lblSeguradorFlowerLogo.setBounds(lblSeguradorFlowerLogo.getX(),
				lblSeguradorFlowerLogo.getY(),
				lblSeguradorFlowerLogo.getWidth(), alturaFlowerLogo);
	}

	public void finalizaSplash() {
		alturaTela -= 10;
		larguraTela -= 20;
		setSize(larguraTela, alturaTela);
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		janelaLogin.setVisible(true);

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
}
