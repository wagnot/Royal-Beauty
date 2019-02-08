package br.com.royal.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import org.opencv.core.Mat;

import br.com.royal.controller.ExecutaCamera;

public class JanelaCameraCliente extends JDialog implements WindowListener,
		MouseListener, MouseMotionListener {
	
	public JLabel lblFoto;
	private JLabel lblFundo, lblClose, lblNavbar, lblConfirmar, lblCaptura, lblCancelar;
	private ExecutaCamera camera = new ExecutaCamera();
	private String captura = "Capturar", nova = "Nova", tipoBtn = captura;
	private Mat mat;
	private int posX, posY;
	JanelaCliente janela;

	public JanelaCameraCliente(JanelaCliente janela) {
		super(janela, true);
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}
		
		this.janela = janela;
		setTitle("Câmera");
		setSize(700, 470);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		addWindowListener(this);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setUndecorated(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Img/Rosa.png"));
		IC();

		camera.reproduzir(lblFoto);
		setVisible(true);
		
//		Timer tempoPartida = new Timer();
//        tempoPartida.schedule(new TimerTask() {
//        	int i = 0;
//            @Override
//            public void run() {
//            	if(i==1){
//            		setVisible(true);
//            		tempoPartida.cancel();
//            	}else
//            		i++;
//            	System.out.println(isVisible());
//            }
//            
//        }, 1000, 1000);
        
	}

	private void IC() {
		lblFoto = new JLabel();
		// 20% menor q a camera padrao
		lblFoto.setBounds(30, 55, 512, 384);
		lblFoto.setBorder(BorderFactory.createLineBorder(new Color (161, 0, 64)));
		add(lblFoto);

		lblConfirmar = new JLabel(new ImageIcon ("Img/Câmera de Foto do Cliente/Salvar Foto.png"));
		lblConfirmar.setBounds(570, 120, 55, 42);
		lblConfirmar.setToolTipText("Salvar imagem capturada");
		add(lblConfirmar);
		lblConfirmar.addMouseListener(this);
		lblConfirmar.setEnabled(false);
		
		lblCaptura = new JLabel(new ImageIcon ("Img/Câmera de Foto do Cliente/Nova Foto de Cliente.png"));
		lblCaptura.setBounds(lblConfirmar.getX(), lblConfirmar.getY()+100, 53, 43);
		lblCaptura.setToolTipText("Capturar imagem");
		add(lblCaptura);
		lblCaptura.addMouseListener(this);

		lblCancelar = new JLabel(new ImageIcon ("Img/Câmera de Foto do Cliente/Cancelar.png"));
		lblCancelar.setBounds(lblCaptura.getX()+2, lblCaptura.getY() + 100, 51, 51);
		lblCancelar.setToolTipText("Cancelar");
		lblCancelar.addMouseListener(this);
		add(lblCancelar);
		
		lblClose = new JLabel();
//		lblClose.setOpaque(true);
//		lblClose.setBackground(Color.GREEN);
		lblClose.setBounds(672, 3, 25, 22);
		lblClose.addMouseListener(this);
		add(lblClose);
		
		lblNavbar = new JLabel();
//		lblNavbar.setOpaque(true);
//		lblNavbar.setBackground(Color.GREEN);
		lblNavbar.setBounds(0, 0, getWidth(), 28);
		lblNavbar.addMouseListener(this);
		lblNavbar.addMouseMotionListener(this);
		add(lblNavbar);
		
		lblFundo = new JLabel(new ImageIcon ("Img/Gerenciamento de Câmera.png"));
		lblFundo.setBounds(0, 0, getWidth(), getHeight());
		add(lblFundo);
	}


	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		if(!tipoBtn.equals("Confirmar"))
			camera.encerrar();
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
		if (e.getSource() == lblCancelar&& lblCancelar.isEnabled()) {
			this.dispose();
			janela.mat = null;
		}

		if (e.getSource() == lblCaptura && lblCaptura.isEnabled()) {
			if (tipoBtn.equals(captura)) {
				mat=camera.capturar();
				tipoBtn = nova;
				lblConfirmar.setEnabled(true);
			} else {
				camera.reproduzir(lblFoto);
				tipoBtn = captura;
				lblConfirmar.setEnabled(false);
			}
		}
		
		if(e.getSource() == lblConfirmar&& lblConfirmar.isEnabled()){
			janela.mat = mat;
			tipoBtn="Confirmar";
			camera.salvar();
			janela.imagem=janela.setImagemDimensao("C:/Royal/src/br/com/royal/imagemCamera/cliente.jpg", janela.fotoCliente.getWidth(), janela.fotoCliente.getHeight());
			janela.fotoCliente.setIcon(new ImageIcon(janela.imagem));
			janela.localFoto="C:/Royal/src/br/com/royal/imagemCamera/cliente.jpg";
			camera.encerrar();
			this.dispose();
		}
		
		if (e.getSource().equals(lblClose)) {
			this.dispose();
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource().equals(lblConfirmar) && lblConfirmar.isEnabled()){
			lblConfirmar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblCaptura) && lblCaptura.isEnabled()){
			lblCaptura.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblCancelar) ){
			lblCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource().equals(lblConfirmar) && lblConfirmar.isEnabled()){
			lblConfirmar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource().equals(lblCaptura) && lblCaptura.isEnabled()){
			lblCaptura.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource().equals(lblCancelar) && lblCancelar.isEnabled()){
			lblCancelar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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

	@Override
	public void mouseDragged(MouseEvent e) {
		setLocation(getLocation().x + e.getX() - posX, getLocation().y + e.getY() - posY);
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
