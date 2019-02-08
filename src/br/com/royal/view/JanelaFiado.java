package br.com.royal.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.MaskFormatter;

import org.apache.poi.hssf.record.formula.TblPtg;

import br.com.royal.controller.FiadoController;
import br.com.royal.controller.FormaDePagamentoController;
import br.com.royal.controller.HorarioController;
import br.com.royal.controller.PagamentoController;
import br.com.royal.controller.Validacoes;
import br.com.royal.controller.VendaController;
import br.com.royal.model.Cartao;
import br.com.royal.model.Cheque;
import br.com.royal.model.Fiado;
import br.com.royal.model.FormaDePagamento;
import br.com.royal.model.Pagamento;
import br.com.royal.model.Parcela;
import br.com.royal.model.QuantidadeParcela;

public class JanelaFiado extends JDialog implements ActionListener,
		MouseListener, MouseMotionListener, KeyListener, WindowListener,
		ItemListener, FocusListener {

	private JLabel lblPesquisaData, lblFundo, lblNavbar, lblClose,
			lblFormaPagamento, lblParcelas, lblValor, lblTotal,
			lblNumeroCheque, lblNomeBancoCheque, lblDataAbate, lblTrocoDeve,
			lblTxtTrocoDeve, lblInfoParcela, lblDesconto, lblInfoDesconto;
	private JTextField txtNumeroCheque, txtNomeBancoCheque;
	private JFormattedTextField txtPesquisaData, txtDataAbate;
	private JComboBox<QuantidadeParcela> cmbParcelas;
	private JComboBox<Object> cmbFormaPagamentos;
	private Color corOriginalBtn = new Color(164, 6, 69);
	private JTable tabela;
	private JScrollPane scrollTabela;
	private FTable modelo = new FTable();
	private String txtBtn = "";
	private JButton btnPrimLinha, btnCancelar, btnAvancar, btnUltLinha,
			btnPagar, btnReceber, btnDesconto;
	private String horarioInicio = "", retornoHoraInicio = "", dataAbate = "";
	private int posX, posY;
	private MaskFormatter mascaraData;
	private JRadioButton rbtFiadoHoje, rbtTodosFiados, rbtDebito, rbtCredito;;
	private ButtonGroup buttonGroup, grupoButtons;
	private JNumberFormatField txtValor, txtDesconto;

	private ArrayList<Parcela> parcelas = new ArrayList<Parcela>();

	private ArrayList<Cartao> listaCartao = new ArrayList<Cartao>();
	private ArrayList<Cheque> listaCheque = new ArrayList<Cheque>();
	private ArrayList<Fiado> listaFiado = new ArrayList<Fiado>();
	private ArrayList<Fiado> fiados = new ArrayList<Fiado>();
	private FiadoController fController = new FiadoController();
	

	private double deve = 0, valorDinheiro = 0, desconto = 0, parcela = 0;

	private Date data = new Date();
	private SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

	public JanelaFiado() {
		// super(ja, true);
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		setLayout(null);
		setSize(850, 570);
		setTitle("Fabricante");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setUndecorated(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Img/Rosa.png"));
		inicializarComponentes();
		preencherJTable(modelo);
		setEnableFalse();
		setComponentesVisibleFalse();

		setVisible(true);
		addWindowListener(this);
	}

	private void inicializarComponentes() {

		lblPesquisaData = new JLabel("Pesquisa:");
		lblPesquisaData.setForeground(Color.BLACK);
		lblPesquisaData.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				19));
		lblPesquisaData.setBounds(210, 90, 105, 30);
		add(lblPesquisaData);

		txtPesquisaData = new JFormattedTextField();
		txtPesquisaData.setBounds(lblPesquisaData.getX() + 100,
				lblPesquisaData.getY(), 100, 30);
		txtPesquisaData.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				12));
		add(txtPesquisaData);
		txtPesquisaData.addKeyListener(this);
		txtPesquisaData.addFocusListener(this);
		try {
			mascaraData = new MaskFormatter("##/##/####");
			mascaraData.setPlaceholderCharacter('_');
			mascaraData.install(txtPesquisaData);
		} catch (Exception ex) {
		}

		rbtTodosFiados = new JRadioButton("Todos", true);
		rbtTodosFiados.setBounds(lblPesquisaData.getX() + 230,
				txtPesquisaData.getY() - 1, 100, 30);
		rbtTodosFiados.addItemListener(this);
		rbtTodosFiados.setForeground(Color.BLACK);
		rbtTodosFiados.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				19));
		add(rbtTodosFiados);

		rbtFiadoHoje = new JRadioButton("Hoje");
		rbtFiadoHoje.addItemListener(this);
		rbtFiadoHoje.setBounds(rbtTodosFiados.getX() + 90,
				rbtTodosFiados.getY(), 100, 30);
		rbtFiadoHoje.setForeground(Color.BLACK);
		rbtFiadoHoje
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 19));
		add(rbtFiadoHoje);

		buttonGroup = new ButtonGroup();
		buttonGroup.add(rbtFiadoHoje);
		buttonGroup.add(rbtTodosFiados);
		// rbtTodosFiados.setSelected(true);

		// "Total: R$" + String.format("%.2f", total),JLabel.CENTER
		lblTotal = new JLabel("Parcela: R$ 10,00", JLabel.CENTER);
		lblTotal.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 26));
		lblTotal.setBounds(0, 275, getWidth(), 30);
		lblTotal.setVisible(false);
		add(lblTotal);

		lblFormaPagamento = new JLabel("Forma de pagamento");
		lblFormaPagamento.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 16));
		lblFormaPagamento.setBounds(90, 320, 220, 30);
		add(lblFormaPagamento);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		cmbFormaPagamentos = new JComboBox<Object>();
		cmbFormaPagamentos.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		cmbFormaPagamentos.setBounds(lblFormaPagamento.getX() + 20,
				lblFormaPagamento.getY() + 40, 120, 30);
		cmbFormaPagamentos.addItem("");
		for (FormaDePagamento f : FormaDePagamentoController
				.getFormasDePagamento())
			cmbFormaPagamentos.addItem(f);
		add(cmbFormaPagamentos);
		cmbFormaPagamentos.addMouseListener(this);
		cmbFormaPagamentos.addActionListener(this);

		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		lblValor = new JLabel("Valor");
		lblValor.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		lblValor.setBounds(
				lblFormaPagamento.getX() + lblFormaPagamento.getWidth(),
				lblFormaPagamento.getY(), 250, 30);
		add(lblValor);

		txtValor = new JNumberFormatField();
		txtValor.setLimit(7);
		txtValor.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		txtValor.setBounds(lblValor.getX() - 30, cmbFormaPagamentos.getY(),
				105, 30);
		add(txtValor);

		btnReceber = new JButton("Receber");
		btnReceber.setBackground(corOriginalBtn);
		btnReceber.setForeground(Color.WHITE);
		btnReceber.setFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
		btnReceber.setBounds(txtValor.getX() + txtValor.getWidth() + 30,
				txtValor.getY() - 1, 100, 30);
		add(btnReceber);
		btnReceber.addActionListener(this);

		// Cartao
		lblParcelas = new JLabel("Parcelas:");
		lblParcelas.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		lblParcelas.setBounds(lblFormaPagamento.getX() + 70,
				cmbFormaPagamentos.getY() + 70, 140, 30);
		lblParcelas.setVisible(false);
		add(lblParcelas);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		cmbParcelas = new JComboBox<QuantidadeParcela>();
		cmbParcelas.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 15));
		cmbParcelas.setBounds(lblParcelas.getX() + 75, lblParcelas.getY(), 80,
				30);
		cmbParcelas.setVisible(false);

		for (QuantidadeParcela pp : PagamentoController.getQuantidadeParcelas()) {

			cmbParcelas.addItem(pp);
		}
		add(cmbParcelas);

		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		lblInfoParcela = new JLabel("MAX " + (cmbParcelas.getItemCount()) + "X");
		lblInfoParcela.setBounds(cmbParcelas.getX() + 8,
				cmbParcelas.getY() + 25, 260, 30);
		lblInfoParcela.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				13));
		lblInfoParcela.setForeground(Color.RED);
		add(lblInfoParcela);

		rbtDebito = new JRadioButton("Débito");
		rbtDebito.setBounds(lblParcelas.getX() + 200, lblParcelas.getY(), 140,
				30);
		rbtDebito.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		add(rbtDebito);
		rbtDebito.addItemListener(this);

		rbtCredito = new JRadioButton("Crédito");
		rbtCredito.setBounds(rbtDebito.getX(), rbtDebito.getY() + 30, 140, 30);
		rbtCredito.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		add(rbtCredito);
		rbtCredito.addItemListener(this);

		grupoButtons = new ButtonGroup();
		grupoButtons.add(rbtCredito);
		grupoButtons.add(rbtDebito);

		// Cheque
		lblNumeroCheque = new JLabel("Número Cheque:");
		lblNumeroCheque.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				16));
		lblNumeroCheque.setBounds(lblFormaPagamento.getX() + 50,
				cmbFormaPagamentos.getY() + 60, 140, 30);
		add(lblNumeroCheque);

		txtNumeroCheque = new JTextField();
		txtNumeroCheque.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				16));
		txtNumeroCheque.setBounds(
				lblNumeroCheque.getX() + lblNumeroCheque.getWidth(),
				lblNumeroCheque.getY(), 200, 30);
		add(txtNumeroCheque);

		lblNomeBancoCheque = new JLabel("Nome Banco:");
		lblNomeBancoCheque.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 16));
		lblNomeBancoCheque.setBounds(lblNumeroCheque.getX(),
				lblNumeroCheque.getY() + 50, 140, 30);
		add(lblNomeBancoCheque);

		txtNomeBancoCheque = new JTextField();
		txtNomeBancoCheque.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 16));
		txtNomeBancoCheque.setBounds(txtNumeroCheque.getX(),
				lblNomeBancoCheque.getY(), 200, 30);
		add(txtNomeBancoCheque);

		// Fiado
		lblDataAbate = new JLabel("Data acerto:");
		lblDataAbate
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		lblDataAbate.setBounds(cmbFormaPagamentos.getX() + 90,
				cmbFormaPagamentos.getY() + 80, 120, 30);
		add(lblDataAbate);

		txtDataAbate = new JFormattedTextField();
		txtDataAbate.setBounds(lblDataAbate.getX() + lblDataAbate.getWidth()
				- 10, lblDataAbate.getY(), 100, 30);
		txtDataAbate
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		add(txtDataAbate);
		txtDataAbate.addFocusListener(this);
		txtDataAbate.addKeyListener(this);
		try {
			mascaraData = new MaskFormatter("##/##/####");
			mascaraData.setPlaceholderCharacter('_');
			mascaraData.install(txtDataAbate);
		} catch (Exception ex) {
		}

		lblDesconto = new JLabel("Desconto:");
		lblDesconto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 18));
		lblDesconto.setBounds(580, 410, 250, 30);
		add(lblDesconto);

		txtDesconto = new JNumberFormatField();
		txtDesconto.setLimit(7);
		txtDesconto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		txtDesconto.setBounds(lblDesconto.getX() + 100, lblDesconto.getY() + 1,
				105, 30);
		txtDesconto.addKeyListener(this);
		add(txtDesconto);
		

		lblInfoDesconto = new JLabel();
		lblInfoDesconto.setBounds(lblDesconto.getX()-30,
				txtDesconto.getY() + 28, 300, 30);
		lblInfoDesconto
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		lblInfoDesconto.setForeground(Color.RED);
		add(lblInfoDesconto);


		// btnDesconto = new JButton("Descontar");
		// btnDesconto.setBackground(corOriginalBtn);
		// btnDesconto.setForeground(Color.WHITE);
		// btnDesconto.setFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
		// btnDesconto.setBounds(txtDesconto.getX() + 120, txtDesconto.getY(),
		// 110, 30);
		// add(btnDesconto);
		// btnDesconto.addActionListener(this);

		lblTrocoDeve = new JLabel("Deve: R$");
		lblTrocoDeve
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 25));
		lblTrocoDeve.setBounds(580, 350, 120, 30);
		add(lblTrocoDeve);

		lblTxtTrocoDeve = new JLabel("10,00");
		lblTxtTrocoDeve.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				24));
		lblTxtTrocoDeve.setBounds(lblTrocoDeve.getX() + 110,
				lblTrocoDeve.getY(), 120, 30);
		add(lblTxtTrocoDeve);

		btnPagar = new JButton("Finalizar");
		btnPagar.setBackground(corOriginalBtn);
		btnPagar.setForeground(Color.WHITE);
		btnPagar.setFont(new Font("Arial", Font.TRUETYPE_FONT, 20));
		btnPagar.setBounds(550, lblTrocoDeve.getY() + 130, 120, 32);
		add(btnPagar);
		btnPagar.addActionListener(this);
		btnPagar.setEnabled(false);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBackground(corOriginalBtn);
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("Arial", Font.TRUETYPE_FONT, 20));
		btnCancelar.setBounds(btnPagar.getX() + 150, btnPagar.getY(), 120, 32);
		add(btnCancelar);
		btnCancelar.setEnabled(false);
		btnCancelar.addActionListener(this);

		lblClose = new JLabel();
		// lblClose.setOpaque(true);
		// lblClose.setBackground(Color.GREEN);
		lblClose.setBounds(813, 3, 33, 28);
		lblClose.addMouseListener(this);
		add(lblClose);

		lblNavbar = new JLabel();
		// lblNavbar.setOpaque(true);
		// lblNavbar.setBackground(Color.GREEN);
		lblNavbar.setBounds(0, 0, getWidth(), 34);
		lblNavbar.addMouseListener(this);
		lblNavbar.addMouseMotionListener(this);
		add(lblNavbar);

		criaTabela();

		lblFundo = new JLabel(new ImageIcon(
				"Img/Gerenciamento de Pendencias.png"));
		lblFundo.setBounds(0, 0, getWidth(), getHeight());
		add(lblFundo);
	}

	private void setComponentesVisibleFalse() {
		lblNomeBancoCheque.setVisible(false);
		lblNumeroCheque.setVisible(false);
		txtNomeBancoCheque.setVisible(false);
		txtNumeroCheque.setVisible(false);
		lblParcelas.setVisible(false);
		cmbParcelas.setVisible(false);
		rbtCredito.setVisible(false);
		rbtDebito.setVisible(false);
		lblDataAbate.setVisible(false);
		txtDataAbate.setVisible(false);
		lblInfoParcela.setVisible(false);

		rbtCredito.setSelected(false);
		rbtDebito.setSelected(false);
		cmbParcelas.setSelectedIndex(0);
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

		scrollTabela.setBounds(170, 130, 473, 130);
		tabela.getTableHeader().setResizingAllowed(false);

		add(scrollTabela);

		modelo.addColumn("Cliente");
		modelo.addColumn("Valor");
		modelo.addColumn("Data abate");

		tabela.getColumnModel().getColumn(0).setPreferredWidth(241);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(115);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(115);

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

	private void preencherJTable(FTable modelo) {
		modelo.setRowCount(0);

		if (rbtTodosFiados.isSelected()){
			fiados = fController.getTodosFiados();
			
			for (Fiado f : new FiadoController().getTodosFiados())
				modelo.addRow(new Object[] { f.getNomeCliente(),
						f.getValorParcela(), f.getDataAbate() });
		}else{
			fiados = fController.getTodosFiadosDia(txtPesquisaData.getText());

			for (Fiado f : new FiadoController()
					.getTodosFiadosDia(txtPesquisaData.getText()))
				modelo.addRow(new Object[] { f.getNomeCliente(),
						f.getValorParcela(), f.getDataAbate() });
		}
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
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getSource() == txtPesquisaData) {
			if (txtPesquisaData.getText().replaceAll("[/_]", "").length() > 0) {
				rbtTodosFiados.setSelected(false);
				rbtFiadoHoje.setSelected(false);
				preencherJTable(modelo);

			} else {
				rbtTodosFiados.setSelected(true);
			}
		}

		if (e.getSource() == txtDesconto) {
			desconto = Double.parseDouble(txtDesconto.getText().substring(3)
					.replace(",", "."));
			if (parcela - desconto >=0){
				deve=parcela - desconto;
				setTextoFormatadoDeveTroco(deve);
				lblInfoDesconto.setText("");
			
			}else{
				setTextoFormatadoDeveTroco(0);
				lblInfoDesconto.setText("O desconto não pode ser maior que a parcela");
			}
		}
		
	
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		setLocation(getLocation().x + e.getX() - posX,
				getLocation().y + e.getY() - posY);
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(lblClose)) {
			this.dispose();
		}

		if (e.getSource().equals(tabela)) {
			if (tabela.getSelectedRow() > -1) {
				setEnableTrue();
				
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

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
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==btnPagar){
			Fiado f = new Fiado();
			f.setCodFiado(fiados.get(tabela.getSelectedRow()).getCodFiado());
			
		Parcela parcela = new Parcela();
			parcela.setValorParcela(valorDinheiro);
			parcela.setCodQuantidadeParcela(1);
			parcela.setCodFormaDePagamento(3);

			parcelas.add(parcela);
			Date data = new Date();
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			

			

		Pagamento pagamento = new Pagamento();
		if (listaFiado.size() > 0)
			pagamento.setDataPagamento(dataAbate);
		else
				pagamento.setDataPagamento((formatador.format(data)));

			pagamento.setCodCliente(fiados.get(tabela.getSelectedRow()).getCodCliente());
			pagamento.setDesconto(desconto);
			pagamento.setTotalPagamento(Double.parseDouble(tabela.getValueAt(tabela.getSelectedRow(), 1)+""));

			int codComandaProduto = fiados.get(tabela.getSelectedRow()).getCodComandaProduto();
			int codComandaServico = fiados.get(tabela.getSelectedRow()).getCodComandaServico();
			int codVendaProduto = fiados.get(tabela.getSelectedRow()).getCodVendaProduto();
			int codVendaServico = fiados.get(tabela.getSelectedRow()).getCodVendaServico();
			pagamento.setCodComandaProduto(codComandaProduto);
			
			pagamento.setCodComandaServico(codComandaServico);
			
			
			System.out.println(codComandaProduto);
			System.out.println(codComandaServico);
			System.out.println(codVendaProduto);
			System.out.println(codVendaServico);
			VendaController vc = new VendaController();
		//	vc.cadastrosVenda(pagamento, comandaServico, comandaProduto,
			//		parcelas, listaCartao, listaCheque, listaFiado);
	vc.pagarFiado(f,pagamento,codComandaProduto,codComandaServico, codVendaProduto,codVendaServico,parcelas, listaCartao, listaCheque, listaFiado);
			//(fiado,pagamento)
			
		}
		
		if (e.getSource() == cmbFormaPagamentos) {
			setComponentesVisibleFalse();

			// Cheque
			if (cmbFormaPagamentos.getSelectedIndex() == 1) {
				lblNomeBancoCheque.setVisible(true);
				lblNumeroCheque.setVisible(true);
				txtNomeBancoCheque.setVisible(true);
				txtNumeroCheque.setVisible(true);
			}
			// Cartão
			if (cmbFormaPagamentos.getSelectedIndex() == 2) {
				lblParcelas.setVisible(true);
				cmbParcelas.setVisible(true);
				rbtCredito.setVisible(true);
				rbtDebito.setVisible(true);
				lblInfoParcela.setVisible(true);
			}
			// Dinheiro
			if (cmbFormaPagamentos.getSelectedIndex() == 3) {

			}
			// Fiado
			if (cmbFormaPagamentos.getSelectedIndex() == 4) {
				lblDataAbate.setVisible(true);
				txtDataAbate.setVisible(true);
			}
		}

		if (btnCancelar == e.getSource()) {
			tabela.clearSelection();
			setEnableFalse();
		}

		if (btnReceber == e.getSource()) {
			if (verificaCamposPreenchidosDebito()) {

				double valor = Double.parseDouble(txtValor.getText()
						.replace(",", ".").substring(3));
				Parcela p = new Parcela();
				if (cmbParcelas.getSelectedIndex() > 0)
					p.setCodQuantidadeParcela(cmbParcelas.getSelectedIndex() + 1);
				else
					p.setCodQuantidadeParcela(1);
				p.setCodFormaDePagamento(((FormaDePagamento) cmbFormaPagamentos
						.getSelectedItem()).getCodFormaDePagamento());

				// Cheque
				if (cmbFormaPagamentos.getSelectedIndex() == 1) {
					if (txtNomeBancoCheque.getText().length() > 0
							&& txtNumeroCheque.getText().length() > 0) {
						Cheque cheque = new Cheque();
						cheque.setNomeBanco(txtNomeBancoCheque.getText());
						cheque.setNumeroCheque(txtNumeroCheque.getText());
						listaCheque.add(cheque);

						setComponentesVisibleFalse();
						cmbFormaPagamentos.grabFocus();
						verificaValorParcela(valor, p,
								cmbFormaPagamentos.getSelectedIndex());
						calculaDeveTroco(valor);

						limpaCamposDebitar();
						tabela.setEnabled(false);
					} else {
						JOptionPane.showMessageDialog(this,
								"Insira o nome do banco e o número do cheque");
						if (txtNumeroCheque.getText().length() == 0)
							txtNumeroCheque.grabFocus();
						else
							txtNomeBancoCheque.grabFocus();
					}
				}
				// Cartão
				else if (cmbFormaPagamentos.getSelectedIndex() == 2) {
					Cartao cartao = new Cartao();
					if (rbtCredito.isSelected() || rbtDebito.isSelected()) {
						if (rbtCredito.isSelected()) {
							cartao.setCodTipo(1);
						} else {
							cartao.setCodTipo(2);
						}
						listaCartao.add(cartao);
						tabela.setEnabled(false);

						setComponentesVisibleFalse();
						cmbFormaPagamentos.grabFocus();
						verificaValorParcela(valor, p,
								cmbFormaPagamentos.getSelectedIndex());
						calculaDeveTroco(valor);

						limpaCamposDebitar();
					} else {
						JOptionPane.showMessageDialog(this,
								"Escolha um tipo de cartão");
					}
				}
				// Dinheiro
				else if (cmbFormaPagamentos.getSelectedIndex() == 3) {
					setComponentesVisibleFalse();
					cmbFormaPagamentos.grabFocus();
					double v = verificaValorParcela(valor, p,
							cmbFormaPagamentos.getSelectedIndex());
					calculaDeveTroco(valor);
					tabela.setEnabled(false);
					valorDinheiro += v;
					limpaCamposDebitar();
				}
				// Fiado
				else if (cmbFormaPagamentos.getSelectedIndex() == 4) {
					if (txtDataAbate.getText().replaceAll("[_ /]", "").length() > 0) {
						Fiado fiado = new Fiado();
						fiado.setDataAbate(txtDataAbate.getText());
						dataAbate = txtDataAbate.getText();
						listaFiado.add(fiado);
						tabela.setEnabled(false);

						setComponentesVisibleFalse();
						cmbFormaPagamentos.grabFocus();
						verificaValorParcela(valor, p,
								cmbFormaPagamentos.getSelectedIndex());
						calculaDeveTroco(valor);

						limpaCamposDebitar();
					} else {
						JOptionPane.showMessageDialog(this,
								"Insira a data de abate");
						txtDataAbate.grabFocus();
					}
				}
				
				
				
			}
		}
	}

	private void limpaCamposDebitar() {
		cmbFormaPagamentos.setSelectedIndex(0);
		txtValor.setText("");
		txtDataAbate.setText("");
		rbtCredito.setSelected(false);
		rbtDebito.setSelected(false);
		txtNumeroCheque.setText("");
		txtNomeBancoCheque.setText("");
	}

	private void setTextoFormatadoDeveTroco(double valor) {
		lblTxtTrocoDeve.setText(String.format("%.2f", valor));
	}

	private void calculaDeveTroco(double valor) {
		if (valor >= deve ) {
			deve = valor - deve;
			lblTrocoDeve.setText("Troco: R$");
			setTextoFormatadoDeveTroco(deve);
			btnPagar.setEnabled(true);

			// btnReceber.setEnabled(false);
		} else {
			deve -= valor;
			setTextoFormatadoDeveTroco(deve);
		}
		parcela = deve;

	}

	private double verificaValorParcela(double valor, Parcela p, int indexCombo) {
		if (valor > deve)
			valor = valor - (valor - deve);
		p.setValorParcela(valor / p.getCodQuantidadeParcela());
		if (indexCombo != 3)
			parcelas.add(p);
		return valor;
	}

	private boolean verificaCamposPreenchidosDebito() {
		if (cmbFormaPagamentos.getSelectedIndex() > 0) {
			if (txtValor.getText().length() > 0) {
				try {
					if (Double.parseDouble(txtValor.getText().replace(",", ".")
							.substring(3)) > 0)
						return true;
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this,
							"Insira um valor válido");
					System.out.println("VerificaCamposPreenchidosDebito:\n"
							+ ex.getMessage());
				}
			} else
				JOptionPane.showMessageDialog(this, "Insira um valor");
		} else
			JOptionPane.showMessageDialog(this,
					"Selecione uma forma de pagamento");
		return false;
	}

	private void setEnableTrue() {
		lblTrocoDeve.setVisible(true);
		lblTxtTrocoDeve.setVisible(true);

		cmbFormaPagamentos.setEnabled(true);
		txtDesconto.setEnabled(true);
		txtValor.setEnabled(true);
		btnReceber.setEnabled(true);

		btnCancelar.setEnabled(true);

		lblTotal.setVisible(true);
		lblTxtTrocoDeve.setText(String.format("%.2f",
				tabela.getValueAt(tabela.getSelectedRow(), 1))
				.replace(".", ","));
		lblTotal.setText(("Parcela: R$ " + String.format("%.2f",
				tabela.getValueAt(tabela.getSelectedRow(), 1))
				.replace(".", ",")));
		deve = Double.parseDouble(tabela.getValueAt(tabela.getSelectedRow(), 1)
				.toString().replace("<", "."));
		parcela = deve;
	}

	private void setEnableFalse() {
		lblTotal.setVisible(false);
		lblTrocoDeve.setVisible(false);
		lblTxtTrocoDeve.setVisible(false);

		cmbFormaPagamentos.setEnabled(false);
		txtDesconto.setEnabled(false);
		txtValor.setEnabled(false);
		btnReceber.setEnabled(false);

		btnCancelar.setEnabled(false);

		cmbFormaPagamentos.setSelectedIndex(0);
		tabela.setEnabled(true);

		deve = 0;
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		if (rbtDebito.isSelected()) {
			cmbParcelas.setEnabled(false);
			cmbParcelas.setSelectedIndex(0);
		} if (rbtCredito.isSelected()){
			cmbParcelas.setEnabled(true);
		}
		if ((rbtFiadoHoje.isSelected() || rbtTodosFiados.isSelected()) && !cmbFormaPagamentos.isEnabled()) {
			if (rbtFiadoHoje.isSelected())
				txtPesquisaData.setText(formatador.format(data));
			else
				txtPesquisaData.setText("");
			preencherJTable(modelo);
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		if (e.getSource() == txtPesquisaData) {
			txtPesquisaData.setCaretPosition(new Validacoes()
					.getCaretPositionData(txtPesquisaData.getText()));
		}

		if (e.getSource() == txtDataAbate) {
			txtDataAbate.setCaretPosition(new Validacoes()
					.getCaretPositionData(txtDataAbate.getText()));
		}
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub

	}

}
