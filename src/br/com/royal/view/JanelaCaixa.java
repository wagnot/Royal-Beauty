package br.com.royal.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import br.com.royal.controller.CaixaController;
import br.com.royal.model.Caixa;

public class JanelaCaixa extends JDialog implements MouseListener,
		KeyListener, ActionListener {

	private JLabel lblIconeEntrada, lblValor, lblFundo, lblInfo;
	private JNumberFormatField txtValor;
	private JButton btnConfirma, btnSair;
	private JanelaHome home;

	private Date data = new Date();
	private SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public JanelaCaixa(JFrame home) {
		super(home, true);
		this.home = (JanelaHome) home;
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}
		
		setTitle("Gerenciamento de Tarefas");
		setSize(500, 300);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setUndecorated(true);
		inicializaComponentes();
		addMouseListener(this);
		
		setVisible(true);
	}


	private void inicializaComponentes() {
		lblIconeEntrada = new JLabel(new ImageIcon("Img/Caixa/Abrir Caixa.png"));
		lblIconeEntrada.setBounds(230, 15, 54, 54);
		add(lblIconeEntrada);

		lblValor = new JLabel("Insira o valor inicial do caixa:");
		lblValor.setBounds(80,
				lblIconeEntrada.getY() + lblIconeEntrada.getHeight() + 20, 600,
				35);
		lblValor.setFont(new Font("Century Gothic", Font.PLAIN, 24));
		lblValor.setForeground(new Color(158, 0, 57));
		add(lblValor);

		txtValor = new JNumberFormatField();
		txtValor.setLimit(7);
		txtValor.setHorizontalAlignment(SwingConstants.CENTER);
		txtValor.setBounds(100, lblValor.getY() + 50, 300, 40);
		txtValor.setFont(new Font("Century Gothic", Font.PLAIN, 24));
		txtValor.setForeground(new Color(202, 40, 89));
		txtValor.addKeyListener(this);
		add(txtValor);

		lblInfo = new JLabel();
		lblInfo.setBounds(txtValor.getX(), txtValor.getY() + 35, 260, 30);
		lblInfo.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		lblInfo.setForeground(Color.RED);
		add(lblInfo);

		btnConfirma = new JButton("Confirmar");
		btnConfirma.setBounds(65, txtValor.getY() + 80, 170, 45);
		btnConfirma.setFont(new Font("Century Gothic", Font.PLAIN, 28));
		btnConfirma.setForeground(new Color(255, 255, 255));
		btnConfirma.setBackground(new Color(202, 40, 89));
		btnConfirma.addActionListener(this);
		btnConfirma.addKeyListener(this);
		add(btnConfirma);

		btnSair = new JButton("Sair");
		btnSair.setBounds(270, btnConfirma.getY(), btnConfirma.getWidth(), 45);
		btnSair.setFont(new Font("Century Gothic", Font.PLAIN, 28));
		btnSair.setForeground(new Color(255, 255, 255));
		btnSair.setBackground(new Color(202, 40, 89));
		btnSair.addActionListener(this);
		btnSair.addActionListener(this);
		add(btnSair);

		lblFundo = new JLabel(new ImageIcon(
				"Img/Fundo da Janela de Abertura de Caixa.png"));
		lblFundo.setBounds(0, 0, getWidth(), getHeight());
		add(lblFundo);
		this.addKeyListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnConfirma) {
			confirmar();
		}

		if (e.getSource() == btnSair) {
			sair();
		}
	}

	private void sair() {
		this.dispose();
		home.dispose();
	}

	private void confirmar() {
		double valor = 0; 
		try {
			valor = Double.parseDouble(txtValor.getText().substring(3).replace(",", "."));
			if (txtValor.getText().length() > 0 && valor > 0) {
				Caixa caixa = new Caixa();
				caixa.setDataCaixa(formatador.format(data));
				caixa.setValorInicial(valor);
				caixa.setValorAtual(valor);
				
				CaixaController cc = new CaixaController();
				cc.cadastrarCaixa(caixa);
				this.dispose();

			} else
				JOptionPane.showMessageDialog(this, "Insira um valor!");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Insira um valor válido!");
			System.out.println("Quebro no confirmar:\n"+ex.getMessage());
		}
	}

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
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == e.VK_ENTER) {
			confirmar();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (!txtValor.getText().equals("")) {
			double irineu = 0;
			try {
				irineu = Double.parseDouble(txtValor.getText().substring(3).replace(",", "."));
				if (irineu>=0) {
					lblInfo.setText("");
				} else
					lblInfo.setText("Insira um valor positivo!");
			} catch (Exception ex) {
				lblInfo.setText("Insira um valor válido!");
			}
		} else
			lblInfo.setText("");

	}

}
