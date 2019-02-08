package br.com.royal.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableCellRenderer;

import br.com.royal.controllerReports.GerarRelatorioCaixa;
import br.com.royal.controllerReports.GerarRelatorioCliente;

import br.com.royal.model.Caixa;
import br.com.royal.modelReports.RelatorioCaixa;
import br.com.royal.modelReports.RelatorioClientes;

public class JanelaRelatorioCampos extends JDialog
		implements WindowListener, ActionListener, MouseListener, ItemListener, KeyListener {

	private JanelaRelatorio jr;
	private JLabel lblFundo, lblNomeRelatorio, lblPrinter, nomeCliente, nomeProduto;
	private JTextField txtNomeCliente, txtNomeProduto;
	private JRadioButton radioUltimaVisita, radioTodasAsVisitas;
	private JTable tabela;
	private FTable modelT = new FTable();

	// JCHECKBOX COMANDA POR CLIENTE
	private JCheckBox  cbUltimaVisita, cbTodos;
	
	//JCHECKBOX CAIXA POR DATA
//	private JCheckBox cbTodosCaixa, cbValorInicial, cbDataCaixa, cbValorAtualFinal, cbStatus;

	private JCheckBox[] checks = new JCheckBox[20];

	public JanelaRelatorioCampos(JanelaRelatorio jr, String tipoJanela) {
		super(jr, true);
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		this.jr = jr;

		setLayout(null);
		setSize(602, 420);
		setTitle("Relatórios");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setUndecorated(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Img/Rosa.png"));
		validaJanela(tipoJanela);

		iC();

		setVisible(true);
		addWindowListener(this);
	}

	private void validaJanela(String tipoJanela) {

	
		 if (tipoJanela.equals("comandacliente"))
			TelaComandaCliente();

		else if (tipoJanela.equals("produto"))
			TelaProduto();
			

}
	private void Tabela() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
		}

		tabela = new JTable(modelT);
		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabela.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		tabela.setSelectionBackground(new Color(222, 54, 121));
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.getTableHeader().setDefaultRenderer(new RTable());
		tabela.getTableHeader().setResizingAllowed(false);
		tabela.addKeyListener(this);
		JScrollPane scroll = new JScrollPane(tabela);
		scroll.setBounds(20, 480, 1040, 200);
		// scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scroll);
		tabela.addMouseListener(this);

	}

	public void iC() {
		

		lblNomeRelatorio = new JLabel("Selecione como queira o relatório: ");
		lblNomeRelatorio.setBounds(110, 100, 500, 30);
		lblNomeRelatorio.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(lblNomeRelatorio);

		lblPrinter = new JLabel(new ImageIcon("Img/printer-.png"));
		lblPrinter.setBounds(500, 350, 61, 52);
		lblPrinter.addMouseListener(this);
		add(lblPrinter);

		lblFundo = new JLabel(new ImageIcon("Img/Gerenciamento de Fabricantes.png"));
		lblFundo.setBounds(0, 0, getWidth(), getHeight());
		add(lblFundo);
	}

	public void TelaComandaCliente() {
		
		nomeCliente = new JLabel("Cliente: ");
		nomeCliente.setBounds(60, 70, 400, 30);
		nomeCliente.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(nomeCliente);
		
		txtNomeCliente = new JTextField("");
		txtNomeCliente.setBounds(120, 70, 400, 30);
		add(txtNomeCliente);
		
		radioUltimaVisita = new JRadioButton("Comanda da última visita");
		radioUltimaVisita.setBounds(130, 130, 200, 30);
		radioUltimaVisita.addItemListener(this);
		add(radioUltimaVisita);
		
		radioTodasAsVisitas = new JRadioButton("Comanda de todas as visitas");
		radioTodasAsVisitas.setBounds(130, 160, 200, 30);
		radioTodasAsVisitas.addItemListener(this);
		add(radioTodasAsVisitas);
		
		


	}
	
	public void TelaProduto(){
		txtNomeProduto = new JTextField("");
		txtNomeProduto.setBounds(120, 70, 400, 30);
		txtNomeProduto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(txtNomeProduto);
		
		nomeProduto = new JLabel();
		nomeProduto.setBounds(50, 70, 300, 30);
		nomeProduto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		add(nomeProduto);
	}



	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {

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
	public void itemStateChanged(ItemEvent e) {
		
		}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
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

	


