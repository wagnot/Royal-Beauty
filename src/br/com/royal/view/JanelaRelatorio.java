package br.com.royal.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import br.com.royal.controller.ClienteController;
import br.com.royal.controller.ComandaServicoController;
import br.com.royal.controller.ProdutoController;
import br.com.royal.controller.ServicoController;
import br.com.royal.controller.Validacoes;
import br.com.royal.controller.VendaController;
import br.com.royal.controllerReports.GerarRelatorioComandapCliente;
import br.com.royal.model.Cliente;
import br.com.royal.model.ComandaServico;
import br.com.royal.model.Produto;
import br.com.royal.model.Servico;
import br.com.royal.model.VendaServico;
import br.com.royal.modelReports.RelatorioCaixa;
import br.com.royal.modelReports.RelatorioComandapCliente;

public class JanelaRelatorio extends JFrame
		implements ActionListener, WindowListener, MouseListener, KeyListener, MouseMotionListener, FocusListener {

	private JanelaHome jh;
	private ArrayList<Servico> servicos = new ServicoController().getServicos();
	private ArrayList<Cliente> clientes = new ClienteController().getClientes();
	private List<Produto> produtos = new ProdutoController().listar();

	private JTextField txtPesquisaCliente, txtPesquisaProduto, txtPesquisaServico;
	private JLabel lblNavbar, lblFundo, lblMinimize, lblClose, lblDataInicial, lblDataFinal, lblTipoRelatorio,
			lblRelatorios, lblRelatorioComandapProduto, lblRelatorioVendapProduto, lblRelatorioComandapCliente,
			lblRelatorioComandapServico, lblRelatorioVendaGeral, lblRelatorioCaixaGeral, lblRelatorioEntradaProduto,
			lblRelatorioSaidaProduto, lblNomeCliente, lblNomeProduto, lblNomeServico, lblImgInfoDataInicial,
			lblImgInfoDataFinal, lblInfoDataFinal;
	private JTable tblCliente, tblProduto, tblServico;
	private Color corOriginalBtn = new Color(164, 6, 69);
	private FTable modeloCliente, modeloProduto, modeloServico;
	private ArrayList<Cliente> listaAtualTabela = new ArrayList<Cliente>();
	private ArrayList<Produto> listaAtualProduto = new ArrayList<Produto>();

	private JFormattedTextField txtDataInicial, txtDataFinal;
	private MaskFormatter mascara;
	private int posX, posY;
	private JScrollPane scrollCliente, scrollProduto, scrollServico;
	private JButton btnGerar, btnCancelar;
	private RelatorioCaixa rc = new RelatorioCaixa();
	private ArrayList<RelatorioCaixa> listaCaixa = new ArrayList<RelatorioCaixa>();
	private ProdutoController pc = new ProdutoController();

	private String btnTxt = "";

	private SimpleDateFormat simple = new SimpleDateFormat("dd/MM/yyyy");
	private Date dataInicial = new Date();

	private Date dataFinal = new Date();

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnGerar)) {
			if (verificaDatas()) {
				if (tblCliente.getSelectedRow() > -1) {
					ArrayList<RelatorioComandapCliente> relatorioComandaPCliente = new ArrayList<RelatorioComandapCliente>();
					ArrayList<ComandaServico> comandasEncontradas = new ArrayList<ComandaServico>();
					for (VendaServico vss : new VendaController().listarVendasServico()) {
						VendaServico vs = new VendaServico();
						ComandaServico cs = new ComandaServico();
						vs.setCodCliente(clientes.get(tblCliente.getSelectedRow()).getCod());
						if (vss.getCodCliente() == vs.getCodCliente()) {
							cs.setComandaServico(vss.getCodComandaServico());
							for (ComandaServico css : ComandaServicoController.getComandasServico()) {
								if (cs.getComandaServico() == css.getComandaServico()) {
									comandasEncontradas.add(css);
								}

							}
						}
					}
					for (ComandaServico cs : comandasEncontradas) {
						RelatorioComandapCliente rcpe = new RelatorioComandapCliente();
						rcpe.setNomeCliente(clientes.get(tblCliente.getSelectedRow()).getNome());
						rcpe.setDataComanda(cs.getDataComanda());
						rcpe.setObservacao(cs.getObservacao());
						rcpe.setValorServico(cs.getSubTotalComanda() + "");
						rcpe.setDataCompleta("");
						ArrayList<Servico> servicos = ComandaServicoController.getServicosPComanda(cs);
						for (Servico s : servicos) {
							s = ServicoController.getServico(s.getCodServico());
							if (servicos.size() > 1) {
								if (rcpe.getServicos() != null)
									rcpe.setServicos(rcpe.getServicos() + s.getNomeServico() + ", ");
								else
									rcpe.setServicos(s.getNomeServico() + ", ");
							} else if (servicos.size() == 1)
								rcpe.setServicos(s.getNomeServico());
							else
								rcpe.setServicos("");
								
						}
						if (servicos.size() > 1)
							rcpe.setServicos(rcpe.getServicos().substring(0, rcpe.getServicos().length() - 2));
						relatorioComandaPCliente.add(rcpe);
					}

					new GerarRelatorioComandapCliente().gerarEntradaComandapCliente(relatorioComandaPCliente);
				} else
					JOptionPane.showMessageDialog(this, "Selecione um cliente");

			} else {
				JOptionPane.showMessageDialog(this, "Insira datas validas");
			}
		}

		if (e.getSource() == btnCancelar) {
			setEnableTrue();
			setVisibleFalse();

			txtDataFinal.setText("");
			txtDataInicial.setText("");
		}
	}

	private boolean verificaDatas() {
		if (verificaDataFinal() && verificaDataFinalMaiorAnterior(dataFinal) && verificaDataInicial())
			return true;
		return false;
	}

	public JanelaRelatorio(JanelaHome jh) {
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}
		this.jh = jh;
		setTitle("Gerenciamento de Relatórios");
		setSize(1080, 700);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		addWindowListener(this);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setUndecorated(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Img/Rosa.png"));

		ic();
		this.setVisible(true);
	}

	public void ic() {
		lblDataInicial = new JLabel("* Data Inicial:");
		lblDataInicial.setBounds(280, 130, 220, 30);
		lblDataInicial.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));

		add(lblDataInicial);

		txtDataInicial = new JFormattedTextField();
		txtDataInicial.setBounds(lblDataInicial.getX() + 120, lblDataInicial.getY(), 80, 30);
		add(txtDataInicial);
		txtDataInicial.addFocusListener(this);
		txtDataInicial.addKeyListener(this);

		lblImgInfoDataInicial = new JLabel(new ImageIcon("Img/error.png"));
		lblImgInfoDataInicial.setBounds(txtDataInicial.getX() + 84, txtDataInicial.getY() + 5, 20, 20);
		add(lblImgInfoDataInicial);
		lblImgInfoDataInicial.setVisible(false);

		try {
			mascara = new MaskFormatter("##/##/####");
			mascara.setPlaceholderCharacter('_');
			mascara.install(txtDataInicial);
		} catch (Exception ex) {
		}

		lblDataFinal = new JLabel("* Data Final:");
		lblDataFinal.setBounds(txtDataInicial.getX() + 120, lblDataInicial.getY(), 130, 30);
		lblDataFinal.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		add(lblDataFinal);

		txtDataFinal = new JFormattedTextField();
		txtDataFinal.setBounds(lblDataFinal.getX() + 100, lblDataFinal.getY(), 80, 30);
		add(txtDataFinal);
		txtDataFinal.addFocusListener(this);
		txtDataFinal.addKeyListener(this);

		lblInfoDataFinal = new JLabel("Não é possivel inserir uma data final anterior a da data inicial");
		lblInfoDataFinal.setBounds(lblDataInicial.getX() + 30, txtDataFinal.getY() + 25, 450, 30);
		lblInfoDataFinal.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		lblInfoDataFinal.setForeground(Color.RED);
		add(lblInfoDataFinal);
		lblInfoDataFinal.setVisible(false);

		lblImgInfoDataFinal = new JLabel(new ImageIcon("Img/error.png"));
		lblImgInfoDataFinal.setBounds(txtDataFinal.getX() + 84, txtDataFinal.getY() + 5, 20, 20);
		add(lblImgInfoDataFinal);
		lblImgInfoDataFinal.setVisible(false);

		btnGerar = new JButton("Gerar");
		btnGerar.setBounds(420, 655, 100, 30);
		btnGerar.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnGerar.setForeground(Color.WHITE);
		btnGerar.setBackground(corOriginalBtn);
		btnGerar.addActionListener(this);
		btnGerar.setVisible(false);
		add(btnGerar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(btnGerar.getX() + 120, btnGerar.getY(), btnGerar.getWidth(), btnGerar.getHeight());
		btnCancelar.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setBackground(corOriginalBtn);
		btnCancelar.addActionListener(this);
		btnCancelar.setVisible(false);
		add(btnCancelar);

		try {
			mascara = new MaskFormatter("##/##/####");
			mascara.setPlaceholderCharacter('_');
			mascara.install(txtDataFinal);
		} catch (Exception ex) {
		}

		lblRelatorios = new JLabel("Relatórios:");
		lblRelatorios.setBounds(870, 80, 150, 50);
		lblRelatorios.setFont(new Font("Century Gothic", Font.PLAIN, 20));

		add(lblRelatorios);

		lblRelatorioComandapCliente = new JLabel(new ImageIcon("Img/Relatórios/Comanda por Cliente.png"));
		lblRelatorioComandapCliente.setBounds(lblRelatorios.getX() - 15, lblRelatorios.getY() + 60, 50, 61);
		lblRelatorioComandapCliente.setToolTipText("Comanda por Cliente");
		lblRelatorioComandapCliente.addMouseListener(this);
		add(lblRelatorioComandapCliente);

		lblRelatorioComandapProduto = new JLabel(new ImageIcon("Img/Relatórios/Comanda por Produto.png"));
		lblRelatorioComandapProduto.setBounds(lblRelatorioComandapCliente.getX() + 80, lblRelatorios.getY() + 60, 50,
				61);
		lblRelatorioComandapProduto.setToolTipText("Comanda por Produto");
		lblRelatorioComandapProduto.addMouseListener(this);
		add(lblRelatorioComandapProduto);

		lblRelatorioComandapServico = new JLabel(new ImageIcon("Img/Relatórios/Comanda por Servico.png"));
		lblRelatorioComandapServico.setBounds(lblRelatorioComandapCliente.getX(),
				lblRelatorioComandapProduto.getY() + 70, 50, 61);
		lblRelatorioComandapServico.setToolTipText("Comanda por Serviço");
		lblRelatorioComandapServico.addMouseListener(this);
		add(lblRelatorioComandapServico);

		lblRelatorioVendapProduto = new JLabel(new ImageIcon("Img/Relatórios/Venda por Produto.png"));
		lblRelatorioVendapProduto.setBounds(lblRelatorioComandapServico.getX() + 75,
				lblRelatorioComandapProduto.getY() + 70, 77, 61);
		lblRelatorioVendapProduto.setToolTipText("Venda por Produto");
		lblRelatorioVendapProduto.addMouseListener(this);
		add(lblRelatorioVendapProduto);

		lblRelatorioVendaGeral = new JLabel(new ImageIcon("Img/Relatórios/Venda Geral.png"));
		lblRelatorioVendaGeral.setBounds(lblRelatorioComandapServico.getX() - 5,
				lblRelatorioComandapServico.getY() + 70, 55, 61);
		lblRelatorioVendaGeral.setToolTipText("Venda Geral");
		lblRelatorioVendaGeral.addMouseListener(this);
		add(lblRelatorioVendaGeral);

		lblRelatorioCaixaGeral = new JLabel(new ImageIcon("Img/Relatórios/Caixa Geral.png"));
		lblRelatorioCaixaGeral.setBounds(lblRelatorioVendaGeral.getX() + 80, lblRelatorioComandapServico.getY() + 70,
				55, 61);
		lblRelatorioCaixaGeral.setToolTipText("Caixa Geral");
		lblRelatorioCaixaGeral.addMouseListener(this);
		add(lblRelatorioCaixaGeral);

		lblRelatorioEntradaProduto = new JLabel(new ImageIcon("Img/Relatórios/Entrada Produto Geral.png"));
		lblRelatorioEntradaProduto.setBounds(lblRelatorioVendaGeral.getX() - 2, lblRelatorioVendaGeral.getY() + 70, 61,
				61);
		lblRelatorioEntradaProduto.setToolTipText("Entrada de Produto Geral");
		lblRelatorioEntradaProduto.addMouseListener(this);
		add(lblRelatorioEntradaProduto);

		lblRelatorioSaidaProduto = new JLabel(new ImageIcon("Img/Relatórios/Saida Produto Geral.png"));
		lblRelatorioSaidaProduto.setBounds(lblRelatorioEntradaProduto.getX() + 80, lblRelatorioVendaGeral.getY() + 70,
				61, 61);
		lblRelatorioSaidaProduto.setToolTipText("Saída de Produto Geral");
		lblRelatorioSaidaProduto.addMouseListener(this);
		add(lblRelatorioSaidaProduto);

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

		criaTabelaComandaCliente();
		criaTabelaComandaProduto();
		criaTabelaComandaServico();

		lblFundo = new JLabel(new ImageIcon("Img/Gerenciamento de Relatórios.png"));
		lblFundo.setBounds(0, 0, getWidth(), getHeight());
		add(lblFundo);

	}

	private void criaTabelaComandaCliente() {
		modeloCliente = new FTable();
		lblNomeCliente = new JLabel("Digite o nome do Cliente");
		lblNomeCliente.setBounds(435, 380, 500, 30);
		lblNomeCliente.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		lblNomeCliente.setVisible(false);
		add(lblNomeCliente);

		txtPesquisaCliente = new JTextField();
		txtPesquisaCliente.setBounds(lblNomeCliente.getX() - 100, lblNomeCliente.getY() + 30, 400, 30);
		txtPesquisaCliente.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		txtPesquisaCliente.setVisible(false);
		txtPesquisaCliente.addKeyListener(this);
		add(txtPesquisaCliente);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
		}

		tblCliente = new JTable(modeloCliente);
		tblCliente.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblCliente.getTableHeader().setResizingAllowed(false);
		tblCliente.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		tblCliente.setRowSelectionAllowed(true);
		tblCliente.setSelectionBackground(new Color(222, 54, 121));
		tblCliente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblCliente.getTableHeader().setDefaultRenderer(new RTable());
		tblCliente.addKeyListener(this);
		scrollCliente = new JScrollPane(tblCliente);
		scrollCliente.setBounds(txtPesquisaCliente.getX() - 100, txtPesquisaCliente.getY() + 35, 600, 200);
		scrollCliente.setVisible(false);

		modeloCliente.addColumn("Nome do Cliente");
		tblCliente.getColumnModel().getColumn(0).setPreferredWidth(600);
		tblCliente.getTableHeader().setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		((DefaultTableCellRenderer) tblCliente.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);

		for (int i = 0; i < tblCliente.getColumnCount(); i++) {
			DefaultTableCellRenderer cellRender = new DefaultTableCellRenderer();
			cellRender.setHorizontalAlignment(SwingConstants.CENTER);
			tblCliente.getColumnModel().getColumn(i).setCellRenderer(cellRender);
		}
		add(scrollCliente);
		atualizaTabelaComandaCliente(modeloCliente);
		// scroll.setHorizontalScrollBarPolicy(JScrollPane
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}

	}

	private void criaTabelaComandaServico() {
		modeloServico = new FTable();
		lblNomeServico = new JLabel("Digite o nome do Serviço");
		lblNomeServico.setBounds(435, 380, 500, 30);
		lblNomeServico.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		lblNomeServico.setVisible(false);
		add(lblNomeServico);

		txtPesquisaServico = new JTextField();
		txtPesquisaServico.setBounds(lblNomeServico.getX() - 100, lblNomeServico.getY() + 30, 400, 30);
		txtPesquisaServico.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		txtPesquisaServico.setVisible(false);
		txtPesquisaServico.addKeyListener(this);
		add(txtPesquisaServico);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
		}

		tblServico = new JTable(modeloServico);
		tblServico.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblServico.getTableHeader().setResizingAllowed(false);
		tblServico.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		tblServico.setRowSelectionAllowed(true);
		tblServico.setSelectionBackground(new Color(222, 54, 121));
		tblServico.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblServico.getTableHeader().setDefaultRenderer(new RTable());
		tblServico.addKeyListener(this);
		scrollServico = new JScrollPane(tblServico);
		scrollServico.setBounds(txtPesquisaServico.getX() - 100, txtPesquisaServico.getY() + 35, 600, 200);
		scrollServico.setVisible(false);

		modeloServico.addColumn("Nome do Serviço");
		tblServico.getColumnModel().getColumn(0).setPreferredWidth(600);
		tblServico.getTableHeader().setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		((DefaultTableCellRenderer) tblServico.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);

		for (int i = 0; i < tblServico.getColumnCount(); i++) {
			DefaultTableCellRenderer cellRender = new DefaultTableCellRenderer();
			cellRender.setHorizontalAlignment(SwingConstants.CENTER);
			tblServico.getColumnModel().getColumn(i).setCellRenderer(cellRender);
		}
		add(scrollServico);
		atualizaTabelaComandaServico(modeloServico);
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}
	}

	public void atualizaTabelaPesquisaCliente(DefaultTableModel modelo) {
		modelo.setNumRows(0);
		if (ClienteController.getClientes(txtPesquisaCliente.getText()) != null) {
			clientes = ClienteController.getClientes(txtPesquisaCliente.getText());
			for (Cliente c : ClienteController.getClientes(txtPesquisaCliente.getText())) {
				modelo.addRow(new Object[] { c.getNome() });
			}
		}
	}

	private void criaTabelaComandaProduto() {
		modeloProduto = new FTable();
		lblNomeProduto = new JLabel("Digite o nome do Produto");
		lblNomeProduto.setBounds(435, 380, 500, 30);
		lblNomeProduto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		lblNomeProduto.setVisible(false);
		add(lblNomeProduto);

		txtPesquisaProduto = new JTextField();
		txtPesquisaProduto.setBounds(lblNomeProduto.getX() - 100, lblNomeProduto.getY() + 30, 400, 30);
		txtPesquisaProduto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		txtPesquisaProduto.setVisible(false);
		txtPesquisaProduto.addKeyListener(this);
		add(txtPesquisaProduto);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
		}

		tblProduto = new JTable(modeloProduto);
		tblProduto.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblProduto.getTableHeader().setResizingAllowed(false);
		tblProduto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		tblProduto.setRowSelectionAllowed(true);
		tblProduto.setSelectionBackground(new Color(222, 54, 121));
		tblProduto.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblProduto.getTableHeader().setDefaultRenderer(new RTable());
		tblProduto.addKeyListener(this);
		scrollProduto = new JScrollPane(tblProduto);
		// scroll.add(tblListar);
		scrollProduto.setBounds(txtPesquisaProduto.getX() - 100, txtPesquisaProduto.getY() + 35, 600, 200);
		scrollProduto.setVisible(false);

		modeloProduto.addColumn("Nome do Produto:");
		tblProduto.getColumnModel().getColumn(0).setPreferredWidth(600);
		tblProduto.getTableHeader().setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		((DefaultTableCellRenderer) tblProduto.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);

		for (int i = 0; i < tblProduto.getColumnCount(); i++) {
			DefaultTableCellRenderer cellRender = new DefaultTableCellRenderer();
			cellRender.setHorizontalAlignment(SwingConstants.CENTER);
			tblProduto.getColumnModel().getColumn(i).setCellRenderer(cellRender);
		}
		add(scrollProduto);
		atualizaTabelaComandaProduto(modeloProduto);
		// scroll.setHorizontalScrollBarPolicy(JScrollPane
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}
	}

	public void atualizaTabelaComandaCliente(DefaultTableModel modelo) {
		modelo.setRowCount(0);
		for (Cliente c : clientes) {
			modelo.addRow(new Object[] { c.getNome() });

		}
	}

	public void atualizaTabelaComandaProduto(DefaultTableModel modelo) {
		modelo.setRowCount(0);
		clientes = ClienteController.getClientes();
		for (Produto p : produtos) {
			modelo.addRow(new Object[] { p.getNomeProduto() });

		}
	}

	public void atualizaTabelaComandaServico(DefaultTableModel modelo) {
		modelo.setRowCount(0);
		for (Servico s : servicos) {
			modelo.addRow(new Object[] { s.getNomeServico() });
		}
	}

	private void camposInvisiveis() {
		lblNomeCliente.setVisible(false);
		txtPesquisaCliente.setVisible(false);
		scrollCliente.setVisible(false);
		lblNomeProduto.setVisible(false);
		txtPesquisaProduto.setVisible(false);
		scrollProduto.setVisible(false);
		lblNomeServico.setVisible(false);
		txtPesquisaServico.setVisible(false);
		scrollServico.setVisible(false);
		btnGerar.setVisible(false);
	}

	public void atualizaTabelaPesquisaProduto(DefaultTableModel modelo) {

	}

	private void pesquisaNomeProduto() {
		if (txtPesquisaProduto.getText().length() > 0) {
			atualizaTabelaPesquisaProduto(modeloProduto);
		} else
			atualizaTabelaComandaProduto(modeloProduto);
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		if (jh != null)
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

	private void setEnableFalse() {
		lblRelatorioComandapCliente.setEnabled(false);
		lblRelatorioCaixaGeral.setEnabled(false);
		lblRelatorioComandapProduto.setEnabled(false);
		lblRelatorioComandapServico.setEnabled(false);
		lblRelatorioSaidaProduto.setEnabled(false);
		lblRelatorioVendaGeral.setEnabled(false);
		lblRelatorioVendapProduto.setEnabled(false);
		lblRelatorioEntradaProduto.setEnabled(false);

		btnGerar.setVisible(true);
		btnCancelar.setVisible(true);
	}

	private void setEnableTrue() {
		lblRelatorioComandapCliente.setEnabled(true);
		lblRelatorioCaixaGeral.setEnabled(true);
		lblRelatorioComandapProduto.setEnabled(true);
		lblRelatorioComandapServico.setEnabled(true);
		lblRelatorioSaidaProduto.setEnabled(true);
		lblRelatorioVendaGeral.setEnabled(true);
		lblRelatorioVendapProduto.setEnabled(true);
		lblRelatorioEntradaProduto.setEnabled(true);

		btnGerar.setVisible(false);
		btnCancelar.setVisible(false);

		btnTxt = "";
	}

	private void setVisibleFalse() {
		lblNomeCliente.setVisible(false);
		txtPesquisaCliente.setVisible(false);
		scrollCliente.setVisible(false);

		lblNomeProduto.setVisible(false);
		txtPesquisaProduto.setVisible(false);
		scrollProduto.setVisible(false);

		lblNomeServico.setVisible(false);
		txtPesquisaServico.setVisible(false);
		scrollServico.setVisible(false);

		tblCliente.clearSelection();
		tblProduto.clearSelection();
		tblServico.clearSelection();

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(lblMinimize)) {
			this.setExtendedState(JFrame.ICONIFIED);
		}

		if (e.getSource().equals(lblClose)) {
			this.dispose();
		}
		if (e.getSource().equals(lblRelatorioComandapCliente) && lblRelatorioComandapCliente.isEnabled()) {
			camposInvisiveis();
			lblNomeCliente.setVisible(true);
			txtPesquisaCliente.setVisible(true);
			scrollCliente.setVisible(true);
			btnTxt = "ComandaCliente";
			setEnableFalse();
		}
		if (e.getSource().equals(lblRelatorioComandapProduto) && lblRelatorioComandapProduto.isEnabled()) {
			camposInvisiveis();
			lblNomeProduto.setVisible(true);
			txtPesquisaProduto.setVisible(true);
			scrollProduto.setVisible(true);
			btnTxt = "ComandaProduto";
			setEnableFalse();
		}

		if (e.getSource() == lblRelatorioComandapServico && lblRelatorioComandapServico.isEnabled()) {
			camposInvisiveis();
			lblNomeServico.setVisible(true);
			txtPesquisaServico.setVisible(true);
			scrollServico.setVisible(true);
			btnTxt = "ComandaServico";
			setEnableFalse();
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource().equals(lblRelatorioComandapCliente) && lblRelatorioComandapCliente.isEnabled()) {
			lblRelatorioComandapCliente.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblRelatorioComandapProduto) && lblRelatorioComandapProduto.isEnabled()) {
			lblRelatorioComandapProduto.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblRelatorioComandapServico) && lblRelatorioComandapServico.isEnabled()) {
			lblRelatorioComandapServico.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblRelatorioVendapProduto) && lblRelatorioVendapProduto.isEnabled()) {
			lblRelatorioVendapProduto.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblRelatorioVendaGeral) && lblRelatorioVendaGeral.isEnabled()) {
			lblRelatorioVendaGeral.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblRelatorioCaixaGeral) && lblRelatorioCaixaGeral.isEnabled()) {
			lblRelatorioCaixaGeral.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblRelatorioEntradaProduto) && lblRelatorioEntradaProduto.isEnabled()) {
			lblRelatorioEntradaProduto.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblRelatorioSaidaProduto) && lblRelatorioSaidaProduto.isEnabled()) {
			lblRelatorioSaidaProduto.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource().equals(lblRelatorioComandapCliente) && lblRelatorioComandapCliente.isEnabled()) {
			lblRelatorioComandapCliente.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource().equals(lblRelatorioComandapProduto) && lblRelatorioComandapProduto.isEnabled()) {
			lblRelatorioComandapProduto.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource().equals(lblRelatorioComandapServico) && lblRelatorioComandapServico.isEnabled()) {
			lblRelatorioComandapServico.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource().equals(lblRelatorioVendapProduto) && lblRelatorioVendapProduto.isEnabled()) {
			lblRelatorioVendapProduto.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource().equals(lblRelatorioVendaGeral) && lblRelatorioVendaGeral.isEnabled()) {
			lblRelatorioVendaGeral.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource().equals(lblRelatorioCaixaGeral) && lblRelatorioCaixaGeral.isEnabled()) {
			lblRelatorioCaixaGeral.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource().equals(lblRelatorioEntradaProduto) && lblRelatorioEntradaProduto.isEnabled()) {
			lblRelatorioEntradaProduto.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if (e.getSource().equals(lblRelatorioSaidaProduto) && lblRelatorioSaidaProduto.isEnabled()) {
			lblRelatorioSaidaProduto.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	private void pesquisa() {
		if (txtPesquisaCliente.getText().length() > 0) {
			atualizaTabelaPesquisaCliente(modeloCliente);
		} else
			atualizaTabelaComandaCliente(modeloCliente);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (txtPesquisaCliente.hasFocus()) {
			pesquisa();
		}

		if (txtPesquisaProduto.hasFocus()) {
			pesquisaNomeProduto();
		}

		if (txtDataFinal.hasFocus()) {
			verificaDataFinal();
			verificaDataFinalMaiorAnterior(dataFinal);
		}

		if (txtDataInicial.hasFocus()) {
			verificaDataInicial();
		}

	}

	private boolean verificaDataInicial() {
		if (txtDataInicial.getText().replaceAll("[/_]", "").length() == 8) {

			simple.setLenient(false);

			try {
				dataInicial = simple.parse(txtDataInicial.getText());
				lblImgInfoDataInicial.setVisible(false);
				return true;
			} catch (Exception ex) {
				lblImgInfoDataInicial.setVisible(true);
			}
		} else {
			lblImgInfoDataInicial.setVisible(false);
		}
		return false;
	}

	private boolean verificaDataFinal() {
		if (txtDataFinal.getText().replaceAll("[/_]", "").length() == 8) {

			simple.setLenient(false);
			try {
				dataFinal = simple.parse(txtDataFinal.getText());
				lblImgInfoDataFinal.setVisible(false);
				return true;
			} catch (Exception ex) {
				lblImgInfoDataFinal.setVisible(true);
			}
		} else {
			lblInfoDataFinal.setVisible(false);
			lblImgInfoDataFinal.setVisible(false);
		}
		return false;
	}

	private boolean verificaDataFinalMaiorAnterior(Date dataFinal) {
		if (txtDataFinal.getText().replaceAll("[/_]", "").length() == 8)
			if (dataFinal.before(dataInicial)) {
				lblInfoDataFinal.setVisible(true);
				return false;
			} else {
				lblInfoDataFinal.setVisible(false);
			}
		return true;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		setLocation(getLocation().x + e.getX() - posX, getLocation().y + e.getY() - posY);
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void focusGained(FocusEvent e) {
		if (e.getSource() == txtDataFinal)
			txtDataFinal.setCaretPosition(new Validacoes().getCaretPositionData(txtDataFinal.getText()));

		if (e.getSource() == txtDataInicial)
			txtDataInicial.setCaretPosition(new Validacoes().getCaretPositionData(txtDataInicial.getText()));

	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub

	}

}
