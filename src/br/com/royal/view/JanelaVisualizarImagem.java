package br.com.royal.view;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class JanelaVisualizarImagem extends JDialog implements WindowListener,
		MouseListener, MouseMotionListener {

	public JLabel lblFoto;
	private JLabel lblFundo, lblClose, lblNavbar;
	private int posX, posY;
	JanelaCliente janela;
	JanelaComanda janelaComanda;
	public JLabel getLblFundo() {
		return lblFundo;
	}

	public void setLblFundo(JLabel lblFundo) {
		this.lblFundo = lblFundo;
	}

	JanelaEntradaProduto janelaEntradaProduto;
	JanelaSaidaProduto saida;
	
	
	public JanelaVisualizarImagem(JanelaSaidaProduto saida) {
		super(saida, true);
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}

		this.saida = saida;
		setTitle("Visualizar");
		setSize(700, 470);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		addWindowListener(this);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setUndecorated(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Img/Rosa.png"));
		IC();
		
		lblFundo = new JLabel(new ImageIcon("Img/Visualização de Produto.png"));
		lblFundo.setBounds(0, 0, getWidth(), getHeight());
		add(lblFundo);
		
		lblFoto.setIcon(new ImageIcon(janela.setImagemDimensao(
				janela.localFoto, lblFoto.getWidth(),
				lblFoto.getWidth())));

		setVisible(true);
	}
	
	
	
	

	public JanelaVisualizarImagem(JanelaEntradaProduto janela) {
		super(janela, true);
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}

		this.janelaEntradaProduto = janela;
		setTitle("Visualizar");
		setSize(700, 470);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		addWindowListener(this);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setUndecorated(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Img/Rosa.png"));
		IC();
		
		lblFundo = new JLabel(new ImageIcon("Img/Visualização de Produto.png"));
		lblFundo.setBounds(0, 0, getWidth(), getHeight());
		add(lblFundo);
		
		lblFoto.setIcon(new ImageIcon(janela.setImagemDimensao(
				janela.localFoto, lblFoto.getWidth(),
				lblFoto.getWidth())));

		setVisible(true);
	}
	
	public JanelaVisualizarImagem(JanelaCliente janela) {
		super(janela, true);
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}

		this.janela = janela;
		setTitle("Visualizar");
		setSize(700, 470);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		addWindowListener(this);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setUndecorated(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Img/Rosa.png"));
		IC();
		
		lblFundo = new JLabel(new ImageIcon("Img/Visualização de Cliente.png"));
		lblFundo.setBounds(0, 0, getWidth(), getHeight());
		add(lblFundo);
		
		lblFoto.setIcon(new ImageIcon(janela.setImagemDimensao(
				janela.localFoto, lblFoto.getWidth(),
				lblFoto.getWidth())));

		setVisible(true);
	}

	public JanelaVisualizarImagem(JanelaComanda janela) {
		super(janela, true);
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}

		this.janelaComanda = janela;
		setTitle("Visualizar");
		setSize(700, 470);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		addWindowListener(this);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setUndecorated(true);
		IC();
		
		lblFundo = new JLabel(new ImageIcon("Img/Visualização de Cliente.png"));
		lblFundo.setBounds(0, 0, getWidth(), getHeight());
		lblFundo.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblFundo);
		
		lblFoto.setIcon(new ImageIcon(janela.setImagemDimensao(
				janela.localFoto, lblFoto.getWidth(),
				lblFoto.getWidth())));

		setVisible(true);
	}

	private void IC() {
		lblFoto = new JLabel();
		// 20% menor q a camera padrao
		lblFoto.setBounds(100, 55, 512, 384);
		lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
		lblFoto.setBorder(BorderFactory.createLineBorder(new Color(161, 0, 64)));
		add(lblFoto);

		lblClose = new JLabel();
		// lblClose.setOpaque(true);
		// lblClose.setBackground(Color.GREEN);
		lblClose.setBounds(672, 3, 25, 22);
		lblClose.addMouseListener(this);
		add(lblClose);

		lblNavbar = new JLabel();
		// lblNavbar.setOpaque(true);
		// lblNavbar.setBackground(Color.GREEN);
		lblNavbar.setBounds(0, 0, getWidth(), 28);
		lblNavbar.addMouseListener(this);
		lblNavbar.addMouseMotionListener(this);
		add(lblNavbar);
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
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

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(lblClose)) {
			this.dispose();
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
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

	@Override
	public void mouseDragged(MouseEvent e) {
		setLocation(getLocation().x + e.getX() - posX,
				getLocation().y + e.getY() - posY);

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
