package br.com.royal.view;

import java.awt.Color;
import java.awt.Cursor;
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
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import br.com.royal.controller.EstoqueController;
import br.com.royal.model.Produto;

public class JanelaProdutosEmFalta extends JFrame implements ActionListener,
		MouseListener, MouseMotionListener, KeyListener, WindowListener, ItemListener {

	private JLabel lblPesquisa, lblEntradaEstoque,lblSaidaEstoque, lblFundo, lblNavbar,
	lblMinimize, lblClose;
	private JTextField txtPesquisa;
	
	private JTable tabela;
	private int posX, posY;
	private JScrollPane scrollTabela;
	private FTable modelo = new FTable();
	private JanelaHome jh;
	private JRadioButton rdbEstoqueGeral, rdbEstoqueEmFalta;
	private ButtonGroup radioGrupo;
	private EstoqueController ce = new EstoqueController();
	private ArrayList<Produto> produto = new ArrayList<Produto>();
	
	public JanelaProdutosEmFalta(JanelaHome jh) {
		this.jh=jh;
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		setLayout(null);
		setSize(602, 420);
		setTitle("Estoque");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setUndecorated(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Img/Rosa.png"));
		inicializarComponentes();
		setVisible(true);
		addWindowListener(this);
	}

	private void criaTabela() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		tabela = new JTable(modelo);
		tabela.addKeyListener(this);
		tabela.addMouseListener(this);
		tabela.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabela.setSelectionBackground(new Color(222, 54, 121));
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.getTableHeader().setDefaultRenderer(new RTable());
		scrollTabela = new JScrollPane(tabela);

		// scrollTabela.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		scrollTabela.setBounds(60, 190, 480, 210);
		tabela.getTableHeader().setResizingAllowed(false);

		add(scrollTabela);

		modelo.addColumn("Produto");
		
		modelo.addColumn("Estoque");
		modelo.addColumn("Custo");
		modelo.addColumn("Lucro");

		preencherJTable(modelo);

		tabela.getColumnModel().getColumn(0).setPreferredWidth(180);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(98);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(100);
		tabela.getColumnModel().getColumn(3).setPreferredWidth(100);
		
		tabela.getTableHeader().setFont(
				new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		((DefaultTableCellRenderer) tabela.getTableHeader()
				.getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);
		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		for (int i = 0; i < tabela.getColumnCount(); i++) {
			DefaultTableCellRenderer cellRender = new DefaultTableCellRenderer();
			cellRender.setHorizontalAlignment(SwingConstants.CENTER);
			tabela.getColumnModel().getColumn(i).setCellRenderer(cellRender);
		}

		tabela.getTableHeader().setReorderingAllowed(false);
		
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void preencherJTable(DefaultTableModel modelo) {
		modelo.setRowCount(0);
		if (rdbEstoqueEmFalta.isSelected()){
			produto = ce.listarProdutosEmFalta();
		for (Produto p: ce.listarProdutosEmFalta()){
			
			modelo.addRow(new Object[] { p.getNomeProduto(),
					 p.getQuantidadeProdutoEstoque(),
			
					p.getCustoProduto(),p.getLucroProduto() });
		}
		}else{
			produto = ce.listarEstoque();
			for (Produto p: ce.listarEstoque()){
				modelo.addRow(new Object[] { p.getNomeProduto(),
						 p.getQuantidadeProdutoEstoque(),
				
						p.getCustoProduto(),p.getLucroProduto() });
			
			
			}
			
		}

	}
		
	public void pesquisaJTable(DefaultTableModel modelo) {
		modelo.setRowCount(0);
		if (rdbEstoqueEmFalta.isSelected()){
			produto = ce.pesquisarProdutosEmfalta(txtPesquisa.getText());
		for (Produto p: ce.pesquisarProdutosEmfalta(txtPesquisa.getText())){
			modelo.addRow(new Object[] { p.getNomeProduto(),
					 p.getQuantidadeProdutoEstoque(),
			
					p.getCustoProduto(),p.getLucroProduto() });
		}
		}else{
			produto = ce.pesquisarEstoqueGeral(txtPesquisa.getText());
			for(Produto p: ce.pesquisarEstoqueGeral(txtPesquisa.getText())){
			modelo.addRow(new Object[] { p.getNomeProduto(),
					 p.getQuantidadeProdutoEstoque(),
			
			p.getCustoProduto(),p.getLucroProduto() });
			
		}
		}

	}
	

	private void inicializarComponentes() {
	

		lblPesquisa = new JLabel("Pesquisa:");
		lblPesquisa.setForeground(Color.BLACK);
		// lblPesquisa.setOpaque(true);
		// lblPesquisa.setBackground(Color.GREEN);
		lblPesquisa.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 19));
		lblPesquisa.setBounds(95, 90, 105, 30);
		add(lblPesquisa);

		txtPesquisa = new JFormattedTextField();
		txtPesquisa
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		txtPesquisa.setBounds(lblPesquisa.getX() + 95, lblPesquisa.getY(),
				260, 30);
		txtPesquisa.addKeyListener(this);
		add(txtPesquisa);
		
		rdbEstoqueGeral = new JRadioButton("Estoque Geral", true);
		rdbEstoqueGeral.setBounds(txtPesquisa.getX()+40,txtPesquisa.getY()+txtPesquisa.getHeight()+10,
				 150, 20);
		rdbEstoqueGeral.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));

		add(rdbEstoqueGeral);
		rdbEstoqueGeral.addItemListener(this);

		rdbEstoqueEmFalta = new JRadioButton("Produtos em falta", false);
		rdbEstoqueEmFalta.setBounds(rdbEstoqueGeral.getX(),
		rdbEstoqueGeral.getY() + rdbEstoqueGeral.getHeight()+5, 190, 20);
		rdbEstoqueEmFalta.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				14));
		add(rdbEstoqueEmFalta);
		rdbEstoqueEmFalta.addItemListener(this);

		radioGrupo = new ButtonGroup();
		radioGrupo.add(rdbEstoqueGeral);
		radioGrupo.add(rdbEstoqueEmFalta);
		criaTabela();
		
		lblEntradaEstoque = new JLabel(new ImageIcon("Img/Pagamentos Pendentes/Nova Entrada de Produto.png"));
		lblEntradaEstoque.setForeground(Color.WHITE);
		lblEntradaEstoque.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		lblEntradaEstoque.setBounds(460, lblPesquisa.getX()-8, 40, 43);
		lblEntradaEstoque.setToolTipText("Nova entrada de estoque");
		lblEntradaEstoque.addMouseListener(this);
		add(lblEntradaEstoque);
		
		lblSaidaEstoque = new JLabel(new ImageIcon("Img/Pagamentos Pendentes/Nova Saída de Produto.png"));
		lblSaidaEstoque.setForeground(Color.WHITE);
		lblSaidaEstoque.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		lblSaidaEstoque.setBounds(lblEntradaEstoque.getX()+lblEntradaEstoque.getWidth()+5, lblEntradaEstoque.getY(), 40, 43);
		lblSaidaEstoque.setToolTipText("Nova saída de estoque");
		lblSaidaEstoque.addMouseListener(this);
		add(lblSaidaEstoque);
		
		lblMinimize = new JLabel();
//		lblMinimize.setOpaque(true);
//		lblMinimize.setBackground(Color.GREEN);
		lblMinimize.setBounds(547, 7, 22, 10);
		lblMinimize.addMouseListener(this);
		add(lblMinimize);
		
		lblClose = new JLabel();
		// lblClose.setOpaque(true);
		// lblClose.setBackground(Color.GREEN);
		lblClose.setBounds(577, 3, 22, 20);
		lblClose.addMouseListener(this);
		add(lblClose);

		lblNavbar = new JLabel();
		// lblNavbar.setOpaque(true);
		// lblNavbar.setBackground(Color.GREEN);
		lblNavbar.setBounds(0, 0, getWidth(), 33);
		lblNavbar.addMouseListener(this);
		lblNavbar.addMouseMotionListener(this);
		add(lblNavbar);

		lblFundo = new JLabel(new ImageIcon("Img/Gerenciamento de Estoque.png"));
		lblFundo.setBounds(0, 0, getWidth(), getHeight());
		add(lblFundo);

	}
	public void pesquisa() {
		if (txtPesquisa.getText().trim().length() > 0 ) {
			pesquisaJTable(modelo);
		} else {
			preencherJTable(modelo);

		}
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
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
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
	public void keyPressed(KeyEvent e) {
		if (((KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,
				java.awt.event.InputEvent.ALT_DOWN_MASK)) != null)
				&& e.getKeyCode() == KeyEvent.VK_F4) {
			this.dispose();
		}	

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (txtPesquisa.hasFocus() ) {
			pesquisa();
		}
		

	
			
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		setLocation(getLocation().x + e.getX() - posX, getLocation().y + e.getY() - posY);

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(lblMinimize)) {
			this.setExtendedState(JFrame.ICONIFIED);
		}
		if (e.getSource().equals(lblClose)) {
			this.dispose();
		}
		
		
		if (e.getSource().equals(lblEntradaEstoque)
				&& lblEntradaEstoque.isEnabled()) {
			
			
			
			if (tabela.getSelectedRow() != -1) {
					
				String codigoBarras = produto.get(tabela.getSelectedRow()).getCodBarras();
				new JanelaEntradaProduto(this, null, codigoBarras);
			} else {
				String codigoBarras = "";
				new JanelaEntradaProduto(this, null, codigoBarras);
			}

			this.setVisible(false);
			
		}
		
		
		if (e.getSource().equals(lblSaidaEstoque)
			&& lblEntradaEstoque.isEnabled()) {
			
			
			
			if (tabela.getSelectedRow() != -1) {
					
				String codigoBarras = produto.get(tabela.getSelectedRow()).getCodBarras();
				new JanelaSaidaProduto(this,  codigoBarras);
			} else {
				String codigoBarras = "";
				new JanelaSaidaProduto(this, codigoBarras);
			}

		this.setVisible(false);
			
	}
		
		

		

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource().equals(lblEntradaEstoque)){
			lblEntradaEstoque.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		
		if (e.getSource().equals(lblSaidaEstoque)){
			lblSaidaEstoque.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource().equals(lblEntradaEstoque)){
			lblEntradaEstoque.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource().equals(lblSaidaEstoque)){
			lblSaidaEstoque.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (rdbEstoqueEmFalta.isSelected()){
			preencherJTable(modelo);
		}else{
			preencherJTable(modelo);
		}
		
	}

}
