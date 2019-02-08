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
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import br.com.royal.controller.CaixaController;
import br.com.royal.model.Caixa;

public class JanelaHome extends JFrame
		implements MouseListener, MouseMotionListener, ActionListener, WindowListener, KeyListener {
	private JLabel lblFundo, lblNavbar, lblMenu, lblDivAgendamento, lblDivCliente, lblDivFuncionario, lblDivFornecedor,
			lblDivProduto, lblDivEstoque, lblDivServico, lblDivUsuario, lblDivComanda, lblDivRelatorio, lblShow,
			lblHide, lblClose, lblMinimize, lblSairCaixa, lblValorCaixa;
	private Timer timerAbre, timerFecha;
	private int larguraMenuAbrindo = 0, larguraMenuFechando = 0, posX, posY;
	private JLabel lblFecharCaixa;
	private Color corOriginalDivs = new Color(168, 56, 101), corHoverDivs = new Color(160, 36, 85);

	private Date data = new Date();
	private SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

	CaixaController caixaController = new CaixaController();

	public JanelaHome() {
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}
		setTitle("Gerenciamento de Tarefas");
		setSize(1080, 700);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setUndecorated(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Img/Rosa.png"));
		addWindowListener(this);
		inicializaComponentes();
		addMouseListener(this);
		setVisible(true);
		lblFecharCaixa.setVisible(false);

		if (new CaixaController().verificaUltimoCaixa()) {
			if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this,
					"O Caixa do dia " + new CaixaController().getUltimoCaixa().getDataCaixa() + " não foi fechado.\n "
							+ "Deseja fecha-lo para continuar?")) {
				new CaixaController().fecharCaixa(new CaixaController().getUltimoCaixa().getDataCaixa());
				new JanelaCaixa(this);
			} else {
				this.dispose();
			}
		} else if (caixaController.verificaCaixaAberto(formatador.format(data))) {
			if (!caixaController.verificaCaixaAbertoStatus(formatador.format(data))) {
				new CaixaController().ativarCaixa(formatador.format(data));
				new JanelaCaixa(this);
			}

		} else
			new JanelaCaixa(this);

		lblFecharCaixa.setVisible(true);
		atualizaVisorCaixa();
	}

	public void atualizaVisorCaixa() {
		lblValorCaixa.setText(
				"Valor atual do caixa: R$ " + String.format("%.2f", caixaController.getCaixaAtual().getValorAtual()));
	}

	public void inicializaComponentes() {
		timerAbre = new Timer(1, acoesDoTimerAbre);
		timerFecha = new Timer(1, acoesDoTimerFecha);

		lblShow = new JLabel(new ImageIcon("Img/Show.png"));
		lblShow.setBounds(5, 320, 29, 75);
		lblShow.addMouseListener(this);
		add(lblShow);

		lblHide = new JLabel(new ImageIcon("Img/Hide.png"));
		lblHide.setVisible(false);
		lblHide.addMouseListener(this);
		add(lblHide);

		lblMenu = new JLabel();
		lblMenu.setOpaque(true);
		lblMenu.setBackground(new Color(161, 17, 74));
		lblMenu.setBounds(5, 41, 0, 654);
		lblMenu.setLayout(null);
		lblMenu.setVisible(false);
		lblMenu.addMouseListener(this);
		add(lblMenu);

		lblDivAgendamento = new JLabel(new ImageIcon("Img/Divisórias/divAgendamento.png"));
		lblDivAgendamento.setOpaque(true);
		lblDivAgendamento.setBackground(corOriginalDivs);
		lblDivAgendamento.setBounds(0, 0, 236, 65);
		lblDivAgendamento.addMouseListener(this);
		lblMenu.add(lblDivAgendamento);

		lblDivCliente = new JLabel(new ImageIcon("Img/Divisórias/divCliente.png"));
		lblDivCliente.setOpaque(true);
		lblDivCliente.setBackground(corOriginalDivs);
		lblDivCliente.setBounds(0, lblDivAgendamento.getY() + 65, 236, 65);
		lblDivCliente.addMouseListener(this);
		lblMenu.add(lblDivCliente);

		lblDivFuncionario = new JLabel(new ImageIcon("Img/Divisórias/divFuncionario.png"));
		lblDivFuncionario.setOpaque(true);
		lblDivFuncionario.setBackground(corOriginalDivs);
		lblDivFuncionario.setBounds(0, lblDivCliente.getY() + 65, 236, 65);
		lblDivFuncionario.addMouseListener(this);
		lblMenu.add(lblDivFuncionario);

		lblDivFornecedor = new JLabel(new ImageIcon("Img/Divisórias/divFornecedor.png"));
		lblDivFornecedor.setOpaque(true);
		lblDivFornecedor.setBackground(corOriginalDivs);
		lblDivFornecedor.setBounds(0, lblDivFuncionario.getY() + 65, 236, 65);
		lblDivFornecedor.addMouseListener(this);
		lblMenu.add(lblDivFornecedor);

		lblDivProduto = new JLabel(new ImageIcon("Img/Divisórias/divProduto.png"));
		lblDivProduto.setOpaque(true);
		lblDivProduto.setBackground(corOriginalDivs);
		lblDivProduto.setBounds(0, lblDivFornecedor.getY() + 65, 236, 65);
		lblDivProduto.addMouseListener(this);
		lblMenu.add(lblDivProduto);

		lblDivEstoque = new JLabel(new ImageIcon("Img/Divisórias/divEstoque.png"));
		lblDivEstoque.setOpaque(true);
		lblDivEstoque.setBackground(corOriginalDivs);
		lblDivEstoque.setBounds(0, lblDivProduto.getY() + 65, 236, 65);
		lblDivEstoque.addMouseListener(this);
		lblMenu.add(lblDivEstoque);

		lblDivServico = new JLabel(new ImageIcon("Img/Divisórias/divServico.png"));
		lblDivServico.setOpaque(true);
		lblDivServico.setBackground(corOriginalDivs);
		lblDivServico.setBounds(0, lblDivEstoque.getY() + 65, 236, 66);
		lblDivServico.addMouseListener(this);
		lblMenu.add(lblDivServico);

		lblDivUsuario = new JLabel(new ImageIcon("Img/Divisórias/divUsuario.png"));
		lblDivUsuario.setOpaque(true);
		lblDivUsuario.setBackground(corOriginalDivs);
		lblDivUsuario.setBounds(0, lblDivServico.getY() + 66, 236, 66);
		lblDivUsuario.addMouseListener(this);
		lblMenu.add(lblDivUsuario);

		lblDivComanda = new JLabel(new ImageIcon("Img/Divisórias/divComanda.png"));
		lblDivComanda.setOpaque(true);
		lblDivComanda.setBackground(corOriginalDivs);
		lblDivComanda.setBounds(0, lblDivUsuario.getY() + 66, 236, 66);
		lblDivComanda.addMouseListener(this);
		lblMenu.add(lblDivComanda);

		lblDivRelatorio = new JLabel(new ImageIcon("Img/Divisórias/divRelatorio.png"));
		lblDivRelatorio.setOpaque(true);
		lblDivRelatorio.setBackground(corOriginalDivs);
		lblDivRelatorio.setBounds(0, lblDivComanda.getY() + 66, 236, 66);
		lblDivRelatorio.addMouseListener(this);
		lblMenu.add(lblDivRelatorio);

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

		lblFecharCaixa = new JLabel(new ImageIcon("img/Caixa/Fechar Caixa.png"));
		lblFecharCaixa.setBackground(new Color(164, 6, 69));
		lblFecharCaixa.setForeground(Color.WHITE);
		lblFecharCaixa.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		lblFecharCaixa.setToolTipText("Fechar Caixa");
		lblFecharCaixa.setBounds(995, 54, 54, 54);
		lblFecharCaixa.addMouseListener(this);
		add(lblFecharCaixa);

		lblValorCaixa = new JLabel("", JLabel.RIGHT);
		lblValorCaixa.setBounds(-30, lblFecharCaixa.getY() + 5, getWidth() - (lblFecharCaixa.getWidth() + 10), 35);
		lblValorCaixa.setFont(new Font("Century Gothic", Font.PLAIN, 24));
		lblValorCaixa.setForeground(new Color(158, 0, 57));
		add(lblValorCaixa);

		lblFundo = new JLabel(new ImageIcon("Img/Gerenciamento de Tarefas.png"));
		lblFundo.setBounds(0, 0, getWidth(), getHeight());
		add(lblFundo);

		this.addKeyListener(this);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(lblShow)) {
			lblShow.setVisible(false);
			lblHide.setVisible(true);
			timerAbre.start();
			lblMenu.setVisible(true);
		}

		if (e.getSource().equals(lblHide)) {
			if (larguraMenuFechando < 240) {
				timerAbre.stop();
				larguraMenuFechando = larguraMenuAbrindo;
				larguraMenuAbrindo = 0;
				timerFecha.start();
			} else {
				timerFecha.start();
			}
		}

		if (e.getSource().equals(lblMinimize)) {
			this.setExtendedState(JFrame.ICONIFIED);
		}

		if (e.getSource().equals(lblClose)) {
			deslogar();
		}

		if (e.getSource() == lblFecharCaixa) {
			if (JOptionPane.showConfirmDialog(this,
					"Tem certeza que deseja fechar o caixa e sair?") == JOptionPane.YES_OPTION) {
				caixaController.fecharCaixa(formatador.format(data));
				this.dispose();
			}
		}
		if (e.getSource().equals(this)) {
			if (larguraMenuFechando < 240) {
				timerAbre.stop();
				larguraMenuFechando = larguraMenuAbrindo;
				larguraMenuAbrindo = 0;
				timerFecha.start();
			} else {
				timerFecha.start();
			}

		}
		if (e.getSource().equals(lblMenu)) {
		}

		if (e.getSource().equals(lblDivAgendamento)) {
			new JanelaAgendamento(this);
			setVisible(false);
		}

		if (e.getSource().equals(lblDivCliente)) {
			new JanelaCliente(this, null);
			setVisible(false);
		}
		if (e.getSource().equals(lblDivFuncionario)) {
			new JanelaFuncionario(this);
			setVisible(false);
		}
		if (e.getSource().equals(lblDivFornecedor)) {
			new JanelaFornecedor(this, null);
			setVisible(false);
		}
		if (e.getSource().equals(lblDivProduto)) {
			new JanelaProduto(this, null, null);
			setVisible(false);
		}

		if (e.getSource().equals(lblDivEstoque)) {
			new JanelaProdutosEmFalta(this);
			setVisible(false);
		}

		if (e.getSource().equals(lblDivServico)) {
			new JanelaServico(this, null, null);
			setVisible(false);
		}

		if (e.getSource().equals(lblDivUsuario)) {
			new JanelaUsuario(this);
			setVisible(false);
		}

		if (e.getSource().equals(lblDivComanda)) {
			new JanelaComanda(this);
			setVisible(false);
		}
		
		if(e.getSource().equals(lblDivRelatorio)){
			new JanelaRelatorio(this);
			setVisible(false);
		}
	}

	private void deslogar() {
		if (JOptionPane.showConfirmDialog(this, "Tem certeza que deseja sair?") == JOptionPane.YES_OPTION) {
			this.dispose();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

		if (e.getSource().equals(lblDivAgendamento)) {
			lblDivAgendamento.setBackground(corHoverDivs);
		}
		if (e.getSource().equals(lblDivCliente)) {
			lblDivCliente.setBackground(corHoverDivs);
		}
		if (e.getSource().equals(lblDivFuncionario)) {
			lblDivFuncionario.setBackground(corHoverDivs);
		}
		if (e.getSource().equals(lblDivFornecedor)) {
			lblDivFornecedor.setBackground(corHoverDivs);
		}
		if (e.getSource().equals(lblDivProduto)) {
			lblDivProduto.setBackground(corHoverDivs);
		}
		if (e.getSource().equals(lblDivEstoque)) {
			lblDivEstoque.setBackground(corHoverDivs);
		}
		if (e.getSource().equals(lblDivServico)) {
			lblDivServico.setBackground(corHoverDivs);
		}
		if (e.getSource().equals(lblDivUsuario)) {
			lblDivUsuario.setBackground(corHoverDivs);
		}
		if (e.getSource().equals(lblDivComanda)) {
			lblDivComanda.setBackground(corHoverDivs);
		}
		if (e.getSource().equals(lblDivRelatorio)) {
			lblDivRelatorio.setBackground(corHoverDivs);
		}
		if (e.getSource().equals(lblFecharCaixa)) {
			lblFecharCaixa.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource().equals(lblDivAgendamento)) {
			lblDivAgendamento.setBackground(corOriginalDivs);
		}
		if (e.getSource().equals(lblDivCliente)) {
			lblDivCliente.setBackground(corOriginalDivs);
		}
		if (e.getSource().equals(lblDivFuncionario)) {
			lblDivFuncionario.setBackground(corOriginalDivs);
		}
		if (e.getSource().equals(lblDivFornecedor)) {
			lblDivFornecedor.setBackground(corOriginalDivs);
		}
		if (e.getSource().equals(lblDivProduto)) {
			lblDivProduto.setBackground(corOriginalDivs);
		}
		if (e.getSource().equals(lblDivEstoque)) {
			lblDivEstoque.setBackground(corOriginalDivs);
		}
		if (e.getSource().equals(lblDivServico)) {
			lblDivServico.setBackground(corOriginalDivs);
		}
		if (e.getSource().equals(lblDivComanda)) {
			lblDivComanda.setBackground(corOriginalDivs);
		}
		if (e.getSource().equals(lblDivUsuario)) {
			lblDivUsuario.setBackground(corOriginalDivs);
		}
		if (e.getSource().equals(lblDivRelatorio)) {
			lblDivRelatorio.setBackground(corOriginalDivs);
		}
		if (e.getSource().equals(lblFecharCaixa)) {
			lblFecharCaixa.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
		// TODO Auto-generated method stub

	}

	ActionListener acoesDoTimerAbre = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (larguraMenuAbrindo < 240)
				abreMenu();
			else {
				larguraMenuFechando = larguraMenuAbrindo;
				larguraMenuAbrindo = 0;
				timerAbre.stop();
			}
		}
	};

	ActionListener acoesDoTimerFecha = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (larguraMenuFechando > 0)
				fechaMenu();
			else {
				larguraMenuFechando = 0;
				timerFecha.stop();
				lblHide.setVisible(false);
				lblMenu.setVisible(false);
				lblShow.setVisible(true);
			}
		}
	};

	public void abreMenu() {
		larguraMenuAbrindo += 10;
		lblMenu.setBounds(lblMenu.getX(), lblMenu.getY(), larguraMenuAbrindo, lblMenu.getHeight());
		lblHide.setBounds(lblMenu.getWidth() + 1, lblShow.getY(), 29, 75);
	}

	public void fechaMenu() {
		larguraMenuFechando -= 10;
		lblMenu.setBounds(lblMenu.getX(), lblMenu.getY(), larguraMenuFechando, lblMenu.getHeight());
		lblHide.setBounds(lblMenu.getWidth() + 1, lblShow.getY(), 29, 75);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		setLocation(getLocation().x + e.getX() - posX, getLocation().y + e.getY() - posY);
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		new JanelaLogin();

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
	public void keyPressed(KeyEvent e) {
		if (((KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, java.awt.event.InputEvent.ALT_DOWN_MASK)) != null)
				&& e.getKeyCode() == KeyEvent.VK_F4) {
			deslogar();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}