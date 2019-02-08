package br.com.royal.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.ScrollPane;
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
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import br.com.royal.controller.FornecedorController;
import br.com.royal.controller.Validacoes;
import br.com.royal.controllerReports.GerarRelatorioFornecedor;
import br.com.royal.dao.FornecedorDao;
import br.com.royal.model.Fornecedor;
import br.com.royal.model.TelFornecedor;
import br.com.royal.modelReports.RelatorioFornecedores;

public class JanelaFornecedor extends JFrame implements ActionListener,
		KeyListener, MouseListener, ItemListener, MouseMotionListener,
		WindowListener, FocusListener {

	private JLabel lblFundo, lblNavbar, lblMinimize, lblClose, lblNomeForm,
			lblCnpjForn, lblContatoForn, lblTelefoneForn,
			lblCpf, lblImgInfoCpf, lblImgInfoCnpj, lblInfoCpf, lblPessoaFisica,
			lblPessoaJuridica, lblNovoFornecedor, lblEditarFornecedor,
			lblExcluirFornecedor, lblSalvarFornecedor, lblCancelar,
			lblInfoObrigatorias, lblRelatorioFornecedor;
	private int posX, posY;
	private JTextField txtNome, txtContato;
	private Color corOriginalBtn = new Color(164, 6, 69);
	private MaskFormatter mascaraCNPJ, mascaraTelefone, mascaraCpf;
	private JButton btnAddTel, btnRemTel, btnPrimLinha, btnVoltar, btnAvancar,
			btnUltLinha;
	private JRadioButton rdbPessoaFisica, rdbPessoaJuridica;
	private JFormattedTextField txtCnpj, txtTelefone, txtCpf;
	private JList<String> lstTelefone;
	private FTable modelo = new FTable();
	private JTable tblListar;
	private String txtBtn = "";
	private Validacoes validacao = new Validacoes();
	private DefaultListModel<String> model = new DefaultListModel<String>();
	private JScrollPane scrollTabela;
	private FornecedorController fController = new FornecedorController();
	private ButtonGroup radioGrupo;
	private String cpf = "", cnpj = "";
	private JanelaHome jh;
	private JanelaEntradaProduto jep;

	public JanelaFornecedor(JanelaHome jh, JanelaEntradaProduto jep) {
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}
		this.jh = jh;
		this.jep = jep;
		setTitle("Cadastro de Fornecedor");
		setSize(902, 620);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		addWindowListener(this);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setUndecorated(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Img/Rosa.png"));
		IC();
		setEnableCamposFalse();


		setVisible(true);
	}

	private void IC() {
		criaTabela();
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}

		lblFundo = new JLabel(new ImageIcon(
				"Img/Gerenciamento de Fornecedores.png"));
		lblFundo.setBounds(0, 0, getWidth(), getHeight());

		lblNomeForm = new JLabel("* Nome:");
		lblNomeForm.setBounds(110, 70, 80, 30);
		lblNomeForm.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 18));
		// lblNomeForm.setForeground(new Color(211, 23, 164));
		add(lblNomeForm);

		txtNome = new JTextField();
		txtNome.setBounds(220, lblNomeForm.getY(), 410, 30);
		txtNome.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 15));
		txtNome.addKeyListener(this);
		// txtNomeForm.setForeground(new Color(211, 23, 164));
		add(txtNome);

		lblCpf = new JLabel("* CPF:");
		lblCpf.setBounds(lblNomeForm.getX(), 123, 80, 30);
		lblCpf.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 18));
		add(lblCpf);

		txtCpf = new JFormattedTextField();
		txtCpf.setBounds(220, lblCpf.getY(), 130, 35);
		txtCpf.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 15));
		txtCpf.addKeyListener(this);
		txtCpf.addFocusListener(this);
		txtCpf.setEnabled(false);
		add(txtCpf);

		try {
			mascaraCpf = new MaskFormatter("###.###.###-##");
			mascaraCpf.setPlaceholderCharacter('_');
			mascaraCpf.install(txtCpf);
		} catch (Exception ex) {
		}

		lblCnpjForn = new JLabel("* CNPJ:");
		lblCnpjForn.setBounds(lblNomeForm.getX(), lblCpf.getY(), 80, 30);
		lblCnpjForn.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 18));

		add(lblCnpjForn);
		lblCnpjForn.setVisible(false);

		txtCnpj = new JFormattedTextField();
		txtCnpj.setBounds(220, lblCnpjForn.getY(), 160, 35);
		txtCnpj.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 15));
		txtCnpj.addFocusListener(this);
		txtCnpj.setBackground(txtNome.getBackground());
		add(txtCnpj);
		txtCnpj.addKeyListener(this);
		try {
			mascaraCNPJ = new MaskFormatter("##.###.###/####-##");
			mascaraCNPJ.setPlaceholderCharacter('_');
			mascaraCNPJ.install(txtCnpj);
		} catch (Exception ex) {
		}
		txtCnpj.setEnabled(false);
		txtCnpj.setVisible(false);

		lblImgInfoCpf = new JLabel();
		lblImgInfoCpf.setBounds(355, 134, 20, 20);
		add(lblImgInfoCpf);

		lblImgInfoCnpj = new JLabel();
		lblImgInfoCnpj.setBounds(385, lblImgInfoCpf.getY(),
				lblImgInfoCpf.getWidth(), lblImgInfoCpf.getHeight());
		add(lblImgInfoCnpj);

		lblInfoCpf = new JLabel();
		lblInfoCpf.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		lblInfoCpf.setBounds(txtCpf.getX(), txtCpf.getY() + 25, 250, 14);
		add(lblInfoCpf);

		lblContatoForn = new JLabel("* Contato:");
		lblContatoForn.setBounds(lblCnpjForn.getX(), lblCnpjForn.getY() + 53,
				120, 30);
		lblContatoForn.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				18));
		// lblContatoForn.setForeground(new Color(211, 23, 164));
		add(lblContatoForn);

		txtContato = new JTextField();
		txtContato.setBounds(txtCnpj.getX(), lblContatoForn.getY(),
				txtCnpj.getWidth(), 35);
		txtContato.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 15));
		// txtContatoForm.setForeground(new Color(211, 23, 164));
		txtContato.setBackground(txtNome.getBackground());
		add(txtContato);
		txtContato.setEnabled(false);

		lblTelefoneForn = new JLabel("Telefone:");
		lblTelefoneForn.setBounds(lblContatoForn.getX()+6,
				lblContatoForn.getY() + 53, txtCnpj.getWidth(), 30);
		lblTelefoneForn.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				20));
		// lblTelefoneForn.setForeground(new Color(211, 23, 164));
		lblTelefoneForn.setBackground(txtNome.getBackground());
		add(lblTelefoneForn);

		txtTelefone = new JFormattedTextField();
		txtTelefone.setBounds(txtCnpj.getX(), lblTelefoneForn.getY(),
				txtCnpj.getWidth(), 35);
		txtTelefone.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 15));
		txtTelefone.addFocusListener(this);
		txtTelefone.setBackground(txtNome.getBackground());
		add(txtTelefone);
		txtTelefone.addKeyListener(this);
		txtTelefone.setEnabled(false);
		try {
			mascaraTelefone = new MaskFormatter("(##) ####-####");
			mascaraTelefone.setPlaceholderCharacter('_');
			mascaraTelefone.install(txtTelefone);
		} catch (Exception ex) {
		}

		btnAddTel = new JButton("+");
		btnAddTel.setBackground(corOriginalBtn);
		btnAddTel.setForeground(Color.WHITE);
		btnAddTel.setFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
		btnAddTel.setBounds(txtTelefone.getX() + 226, 209, 50, 27);
		add(btnAddTel);
		btnAddTel.addActionListener(this);
		btnAddTel.setFocusable(false);
		
		btnRemTel = new JButton("-");
		btnRemTel.setBackground(corOriginalBtn);
		btnRemTel.setForeground(Color.WHITE);
		btnRemTel.setFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
		btnRemTel.setBounds(btnAddTel.getX(), btnAddTel.getY() + 30,
				btnAddTel.getWidth(), btnAddTel.getHeight());
		add(btnRemTel);
		btnRemTel.addActionListener(this);
		btnRemTel.setFocusable(false);
		
		lstTelefone = new JList();
		ScrollPane scroll = new ScrollPane();
		scroll.add(lstTelefone);
		scroll.setBounds(btnAddTel.getX() + 55, txtCnpj.getY(), 130, 140);
		lstTelefone.setFocusable(false);
		add(scroll);

		lblMinimize = new JLabel();
		// lblMinimize.setOpaque(true);
		// lblMinimize.setBackground(Color.GREEN);
		lblMinimize.setBounds(822, 10, 37, 15);
		lblMinimize.addMouseListener(this);
		add(lblMinimize);

		lblClose = new JLabel();
		// lblClose.setOpaque(true);
		// lblClose.setBackground(Color.GREEN);
		lblClose.setBounds(862, 6, 35, 23);
		lblClose.addMouseListener(this);
		add(lblClose);

		lblNovoFornecedor = new JLabel(new ImageIcon("Img/Fornecedor/Novo.png"));
		// lblNovoFornecedor.setOpaque(true);
		// lblNovoFornecedor.setBackground(Color.GREEN);
		lblNovoFornecedor.setBounds(718, 47, 61, 52);
		lblNovoFornecedor.setToolTipText("Novo");
		lblNovoFornecedor.addMouseListener(this);
		add(lblNovoFornecedor);

		lblSalvarFornecedor = new JLabel(new ImageIcon(
				"Img/Fornecedor/Salvar.png"));
		// lblSalvarFornecedor.setOpaque(true);
		// lblSalvarFornecedor.setBackground(Color.GREEN);
		lblSalvarFornecedor.setBounds(lblNovoFornecedor.getX(),
				lblNovoFornecedor.getY() + 60, 61, 52);
		lblSalvarFornecedor.setToolTipText("Salvar");
		lblSalvarFornecedor.addMouseListener(this);
		lblSalvarFornecedor.setEnabled(false);
		add(lblSalvarFornecedor);

		lblEditarFornecedor = new JLabel(new ImageIcon(
				"Img/Fornecedor/Editar.png"));
		// lblEditarFornecedor.setOpaque(true);
		// lblEditarFornecedor.setBackground(Color.GREEN);
		lblEditarFornecedor.setBounds(lblSalvarFornecedor.getX(),
				lblSalvarFornecedor.getY() + 60, 61, 52);
		lblEditarFornecedor.setToolTipText("Editar");
		lblEditarFornecedor.addMouseListener(this);
		lblEditarFornecedor.setEnabled(false);
		add(lblEditarFornecedor);

		lblExcluirFornecedor = new JLabel(new ImageIcon(
				"Img/Fornecedor/Remover.png"));
		// lblExcluirFornecedor.setOpaque(true);
		// lblExcluirFornecedor.setBackground(Color.GREEN);
		lblExcluirFornecedor.setBounds(lblEditarFornecedor.getX(),
				lblEditarFornecedor.getY() + 60, 61, 52);
		lblExcluirFornecedor.addMouseListener(this);
		lblExcluirFornecedor.setToolTipText("Excluir");
		lblExcluirFornecedor.setEnabled(false);
		add(lblExcluirFornecedor);

		lblCancelar = new JLabel(new ImageIcon("Img/Fornecedor/Cancelar.png"));
		// lblCancelar.setOpaque(true);
		// lblCancelar.setBackground(Color.GREEN);
		lblCancelar.setBounds(lblExcluirFornecedor.getX() + 13,
				lblExcluirFornecedor.getY() + 57, 43, 43);
		lblCancelar.setToolTipText("Cancelar");
		lblCancelar.addMouseListener(this);
		add(lblCancelar);

		lblRelatorioFornecedor = new JLabel(new ImageIcon(
				"Img/RelatorioMenor.png"));
		// lblRelatorioFornecedor.setOpaque(true);
		// lblRelatorioFornecedor.setBackground(Color.GREEN);
		lblRelatorioFornecedor.setBounds(lblCancelar.getX() + 5,
				lblCancelar.getY() + 68, 35, 40);
		lblRelatorioFornecedor.setToolTipText("Relatório");
		lblRelatorioFornecedor.addMouseListener(this);
		add(lblRelatorioFornecedor);

		lblPessoaFisica = new JLabel("* Pessoa física:");
		lblPessoaFisica.setBounds(lblTelefoneForn.getX(), 295, 130, 20);
		// lblPessoaFisica.setOpaque(true);
		// lblPessoaFisica.setBackground(Color.GREEN);
		lblPessoaFisica.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				16));
		// lblCnpjForn.setForeground(new Color(231, 23, 164));
		add(lblPessoaFisica);

		lblPessoaJuridica = new JLabel("* Pessoa jurídica:");
		lblPessoaJuridica.setBounds(lblPessoaFisica.getX(), lblPessoaFisica.getY() + 30, 140, 20);
		// lblPessoaJuridica.setOpaque(true);
		// lblPessoaJuridica.setBackground(Color.GREEN);
		lblPessoaJuridica.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 16));

		add(lblPessoaJuridica);

		rdbPessoaJuridica = new JRadioButton("", false);
		rdbPessoaJuridica.setBounds(lblPessoaFisica.getX() + 132,
				lblPessoaFisica.getY() + 30, 100, 20);
		rdbPessoaJuridica.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 18));

		add(rdbPessoaJuridica);
		rdbPessoaJuridica.addItemListener(this);

		rdbPessoaFisica = new JRadioButton("", true);
		rdbPessoaFisica.setBounds(rdbPessoaJuridica.getX(),
				lblPessoaFisica.getY() + 1, 100, 20);
		rdbPessoaFisica.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				18));
		add(rdbPessoaFisica);
		rdbPessoaFisica.addItemListener(this);

		radioGrupo = new ButtonGroup();
		radioGrupo.add(rdbPessoaFisica);
		radioGrupo.add(rdbPessoaJuridica);

		btnPrimLinha = new JButton("<<");
		btnPrimLinha.setBackground(corOriginalBtn);
		btnPrimLinha.setForeground(Color.WHITE);
		btnPrimLinha.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
		btnPrimLinha.setToolTipText("Primeira linha");
		btnPrimLinha.setBounds(rdbPessoaFisica.getX() + 90, 370, 53, 40);
		add(btnPrimLinha);
		btnPrimLinha.addActionListener(this);

		btnVoltar = new JButton("<");
		btnVoltar.setBackground(corOriginalBtn);
		btnVoltar.setForeground(Color.WHITE);
		btnVoltar.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
		btnVoltar.setBounds(btnPrimLinha.getX() + 55, btnPrimLinha.getY(),
				btnPrimLinha.getWidth() - 10, btnPrimLinha.getHeight());
		btnVoltar.setToolTipText("Voltar uma linha");
		add(btnVoltar);
		btnVoltar.addActionListener(this);

		btnAvancar = new JButton(">");
		btnAvancar.setBackground(corOriginalBtn);
		btnAvancar.setForeground(Color.WHITE);
		btnAvancar.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
		btnAvancar.setBounds(btnVoltar.getX() + 55, btnVoltar.getY(),
				btnVoltar.getWidth(), btnVoltar.getHeight());
		btnAvancar.setToolTipText("Avançar linha");
		add(btnAvancar);
		btnAvancar.addActionListener(this);

		btnUltLinha = new JButton(">>");
		btnUltLinha.setBackground(corOriginalBtn);
		btnUltLinha.setForeground(Color.WHITE);
		btnUltLinha.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
		btnUltLinha.setBounds(btnAvancar.getX() + 45, btnAvancar.getY(),
				btnPrimLinha.getWidth(), btnAvancar.getHeight());

		btnUltLinha.setToolTipText("Ultima linha");
		add(btnUltLinha);
		btnUltLinha.addActionListener(this);

		lblNavbar = new JLabel();
		// lblNavbar.setOpaque(true);
		// lblNavbar.setBackground(Color.GREEN);
		lblNavbar.setBounds(0, 0, getWidth(), 37);
		lblNavbar.addMouseListener(this);
		lblNavbar.addMouseMotionListener(this);
		add(lblNavbar);

		lblInfoObrigatorias = new JLabel("* Campos Obrigatórios");
		lblInfoObrigatorias.setBounds(120, 384, 190, 30);
		lblInfoObrigatorias.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 14));
		add(lblInfoObrigatorias);

		add(lblFundo);
		verificaRelatorio();

	}

	private void criaTabela() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
		}

		tblListar = new JTable(modelo);
		tblListar.addKeyListener(this);
		tblListar.addMouseListener(this);
		tblListar.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		tblListar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblListar.setSelectionBackground(new Color(222, 54, 121));
		tblListar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblListar.getTableHeader().setDefaultRenderer(new RTable());
		scrollTabela = new JScrollPane(tblListar);

		// scrollTabela.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		scrollTabela.setBounds(110, 420, 670, 180);
		tblListar.getTableHeader().setResizingAllowed(false);

		add(scrollTabela);

		modelo.addColumn("Nome");
		modelo.addColumn("Contato");
		modelo.addColumn("CNPJ");
		modelo.addColumn("CPF");
		modelo.addColumn("Telefone");

		atualizaTabela(modelo);

		tblListar.getColumnModel().getColumn(0).setPreferredWidth(180);
		tblListar.getColumnModel().getColumn(1).setPreferredWidth(150);
		tblListar.getColumnModel().getColumn(2).setPreferredWidth(130);
		tblListar.getColumnModel().getColumn(3).setPreferredWidth(100);
		tblListar.getColumnModel().getColumn(4).setPreferredWidth(110);
		tblListar.getTableHeader().setReorderingAllowed(false);

		tblListar.getTableHeader().setFont(
				new Font("Century Gothic", Font.TRUETYPE_FONT, 12));
		((DefaultTableCellRenderer) tblListar.getTableHeader()
				.getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);
		tblListar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		for (int i = 0; i < tblListar.getColumnCount(); i++) {
			DefaultTableCellRenderer cellRender = new DefaultTableCellRenderer();
			cellRender.setHorizontalAlignment(SwingConstants.CENTER);
			tblListar.getColumnModel().getColumn(i).setCellRenderer(cellRender);
		}
	}

	private int divideNomeFornecedor(String nome) {
			if (nome.indexOf(" ", 18) < 23 && nome.indexOf(" ",18)>0)
				return nome.indexOf(" ", 18);
			else
				return nome.indexOf(" ", 10);
	}

	public void atualizaTabela(DefaultTableModel modelo) {
		modelo.setNumRows(0);
		int linha = 0;
		if (fController.listar() != null)
			for (Fornecedor f : fController.listar()) {
				linha = setDadosTabela(modelo, linha, f);
			}
	}

	private int setDadosTabela(DefaultTableModel modelo, int linha, Fornecedor f) {
		if (f.getLstTelefones().size() < 1) {
			TelFornecedor tc = new TelFornecedor();
			tc.setTelFornecedor("--------------------");
			f.getLstTelefones().add(tc);
		}
		if (f.getNomeFornecedor().length() < 23
				|| divideNomeFornecedor(f.getNomeFornecedor()) <= 0)
			modelo.addRow(new Object[] { f.getNomeFornecedor(),
					f.getContatoFornecedor(), f.getCnpjFornecedor(),
					f.getCpfFornecedor(),
					f.getLstTelefones().get(0).getTelFornecedor() });
		else {
			modelo.addRow(new Object[] {
					"<HTML><CENTER>"
							+ f.getNomeFornecedor()
									.substring(
											0,
											divideNomeFornecedor(f
													.getNomeFornecedor()))
							+ "<BR>"
							+ f.getNomeFornecedor()
									.substring(
											divideNomeFornecedor(f
													.getNomeFornecedor()),
											f.getNomeFornecedor().length()),
					f.getContatoFornecedor(), f.getCnpjFornecedor(),
					f.getCpfFornecedor(),
					f.getLstTelefones().get(0).getTelFornecedor() });
			tblListar.setRowHeight(linha, 30);
		}
		linha++;

		return linha;
	}

	public void atualizaTabelaPesquisa(DefaultTableModel modelo) {
		modelo.setNumRows(0);
		int linha = 0;
		if (fController.listar(txtNome.getText()) != null)
			for (Fornecedor f : fController.listar(txtNome.getText())) {
				linha = setDadosTabela(modelo, linha,f);
			}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnAddTel) {
			addTelefone();
		}

		if (e.getSource() == btnRemTel) {
			if (e.getSource() == btnRemTel) {
				if (lstTelefone.getSelectedIndex() != -1) {
					model.remove(lstTelefone.getSelectedIndex());
					lstTelefone.setModel(model);
				} else
					JOptionPane
							.showMessageDialog(this, "Selecione um telefone");

			}
		}

		if (e.getSource().equals(btnPrimLinha)) {
			tblListar.setRowSelectionInterval(0, 0);
			lblEditarFornecedor.setEnabled(true);
			lblExcluirFornecedor.setEnabled(true);
			preencherCampos();
			setEnableCamposFalse();
			lblNovoFornecedor.setEnabled(false);

			tblListar.grabFocus();
		}
		if (e.getSource().equals(btnVoltar)) {
			lblNovoFornecedor.setEnabled(false);
			if (tblListar.getSelectedRow() > 0) {

				tblListar.setRowSelectionInterval(
						tblListar.getSelectedRow() - 1,
						tblListar.getSelectedRow() - 1);

				lblEditarFornecedor.setEnabled(true);
				lblExcluirFornecedor.setEnabled(true);
				preencherCampos();
				tblListar.grabFocus();
			} else {
				tblListar.clearSelection();
				lblEditarFornecedor.setEnabled(false);
				lblExcluirFornecedor.setEnabled(false);
				limpaCampos();
				lblNovoFornecedor.setEnabled(true);

			}

		}
		if (e.getSource().equals(btnUltLinha)) {
			tblListar.setRowSelectionInterval(tblListar.getModel()
					.getRowCount() - 1, tblListar.getModel().getRowCount() - 1);
			lblEditarFornecedor.setEnabled(true);
			lblExcluirFornecedor.setEnabled(true);
			preencherCampos();
			tblListar.grabFocus();
			lblNovoFornecedor.setEnabled(false);

		}
		if (e.getSource().equals(btnAvancar)) {
			if (tblListar.getSelectedRow() < tblListar.getModel().getRowCount() - 1) {
				tblListar.setRowSelectionInterval(
						tblListar.getSelectedRow() + 1,
						tblListar.getSelectedRow() + 1);
				lblEditarFornecedor.setEnabled(true);
				lblExcluirFornecedor.setEnabled(true);
				preencherCampos();
				lblNovoFornecedor.setEnabled(false);
				tblListar.grabFocus();
			}
		}

	}

	private void addTelefone() {
		Validacoes v = new Validacoes();

		if (txtTelefone.getText().replaceAll("[(_ -)]", "").length() > 10
				&& validacao.verificaDDITelefone(txtTelefone.getText())) {

			if (model.size() > 0) {
				if (!v.verificaTelefoneDuplicado(model,
						txtTelefone.getText())) {
					model.addElement(txtTelefone.getText().replace("_", ""));
					lstTelefone.setModel(model);
					txtTelefone.setText("");
				} else{
					JOptionPane.showMessageDialog(this,
							"Este telefone já foi cadastrado");
					txtTelefone.setText("");
				}
			} else {
				model.addElement(txtTelefone.getText().replace("_", ""));
				lstTelefone.setModel(model);
				txtTelefone.setText("");
			}

		} else
			JOptionPane.showMessageDialog(this, "Telefone inválido");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(txtTelefone.hasFocus()){
			if(e.VK_ENTER==e.getKeyCode()){
				addTelefone();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (txtNome.hasFocus()) {
			pesquisa();
		}

		if (tblListar.isEnabled() && tblListar.getSelectedRow() > -1
				&& tblListar.hasFocus()) {
			if (e.getKeyCode() == e.VK_DOWN || e.getKeyCode() == e.VK_RIGHT)
				preencherCampos();
			if (e.getKeyCode() == e.VK_UP || e.getKeyCode() == e.VK_LEFT)
				preencherCampos();
			tblListar.grabFocus();
		}

		if (txtCnpj.hasFocus()) {
			if (txtCnpj.getText().replaceAll("[/._-]", "").length() == 14) {
				Validacoes validacao = new Validacoes();
				if (fController.conferirCnpj(txtCnpj.getText(), cnpj)) {
					if (validacao.validaCnpj(txtCnpj.getText())) {

						lblImgInfoCnpj
								.setIcon(new ImageIcon("Img/success.png"));
						lblInfoCpf.setText("");
					} else {

						lblImgInfoCnpj.setIcon(new ImageIcon("Img/error.png"));
						lblInfoCpf.setText("");

					}
				} else {
					lblImgInfoCnpj.setIcon(new ImageIcon("Img/error.png"));
					lblInfoCpf.setVisible(true);
					lblInfoCpf.setText("Cnpj já foi cadastrado. Insira outro");

				}

			} else {
				lblInfoCpf.setText("");
				lblImgInfoCnpj.setIcon(null);
				lblImgInfoCpf.setIcon(null);
			}
		}

		if (txtCpf.hasFocus()) {
			if (txtCpf.getText().replaceAll("[/._-]", "").length() == 11) {
				Validacoes validacao = new Validacoes();
				if (fController.conferirCpf(txtCpf.getText(), cpf)) {
					if (validacao.validaCpf(txtCpf.getText())) {

						lblImgInfoCpf.setIcon(new ImageIcon("Img/success.png"));
						lblInfoCpf.setText("");
					} else {

						lblImgInfoCpf.setIcon(new ImageIcon("Img/error.png"));
						lblInfoCpf.setText("");

					}
				} else {

					lblImgInfoCpf.setIcon(new ImageIcon("Img/error.png"));
					lblInfoCpf.setText("Cpf já foi cadastrado, insira outro");

				}

			} else if (txtCpf.getText().replaceAll("[/._-]", "").length() == 0) {

				lblImgInfoCpf.setIcon(new ImageIcon(""));
				lblInfoCpf.setText("");
			} else {

				lblImgInfoCpf.setIcon(null);
				lblInfoCpf.setText("");
			}
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
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

		if (e.getSource() == lblNovoFornecedor && lblNovoFornecedor.isEnabled()) {
			setEnableCamposTrue();
			lblSalvarFornecedor.setEnabled(true);
			lblNovoFornecedor.setEnabled(false);
			lblEditarFornecedor.setEnabled(false);
			lblExcluirFornecedor.setEnabled(false);
			btnPrimLinha.setEnabled(false);
			btnVoltar.setEnabled(false);
			btnAvancar.setEnabled(false);
			btnUltLinha.setEnabled(false);
			txtBtn = "Novo";
			tblListar.clearSelection();
			tblListar.setEnabled(false);
			if (txtNome.getText().length() < 1)
				txtNome.grabFocus();
		}
		if (e.getSource() == lblEditarFornecedor
				&& lblEditarFornecedor.isEnabled()) {

			if (tblListar.getSelectedRow() != -1) {
				setEnableCamposTrue();

				lblSalvarFornecedor.setEnabled(true);
				lblNovoFornecedor.setEnabled(false);
				lblEditarFornecedor.setEnabled(false);
				lblExcluirFornecedor.setEnabled(false);
				btnPrimLinha.setEnabled(false);
				btnVoltar.setEnabled(false);
				btnAvancar.setEnabled(false);
				btnUltLinha.setEnabled(false);
				cpf = tblListar.getValueAt(tblListar.getSelectedRow(), 3)
						.toString();
				cnpj = tblListar.getValueAt(tblListar.getSelectedRow(), 2)
						.toString();

				lstTelefone.setModel(model);
				txtBtn = "Editar";
				tblListar.getSelectionModel().clearSelection();
				tblListar.setEnabled(false);
				txtNome.grabFocus();
			} else {

				JOptionPane.showMessageDialog(this, "Selecione um Fornecedor");
			}

		}
		if (e.getSource() == lblSalvarFornecedor
				&& lblSalvarFornecedor.isEnabled()) {

			if (verificarCampos()) {
				Fornecedor f = new Fornecedor();
				try {

					f.setNomeFornecedor(txtNome.getText());
					f.setContatoFornecedor(txtContato.getText());
					if (rdbPessoaJuridica.isSelected()) {

						if (validacao.validaCnpj(txtCnpj.getText())) {
							f.setCnpjFornecedor(txtCnpj.getText());
							f.setCpfFornecedor("--------------");
						}

					} else {
						if (validacao.validaCpf(txtCpf.getText())) {
							f.setCnpjFornecedor("------------------");
							f.setCpfFornecedor(txtCpf.getText());

						}

					}
					f.setTelefone(model.toArray());

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(this,
							"Preencha os campos corretamente");
				}

				if (txtBtn.equals("Novo")) {
					if (validacao.validaCpf(txtCpf.getText())
							|| validacao.validaCnpj(txtCnpj.getText())) {
						if (fController.inserir(f, cnpj, cpf)) {

							atualizaTabela(modelo);

							JOptionPane.showMessageDialog(this,
									"Fornecedor cadastrado com sucesso");
							txtBtn = "";

							limpaCampos();

							setEnableCamposFalse();

							lblEditarFornecedor.setEnabled(false);
							lblExcluirFornecedor.setEnabled(false);
							lblNovoFornecedor.setEnabled(true);

							lblSalvarFornecedor.setEnabled(false);

							tblListar.getSelectionModel().clearSelection();

							tblListar.setEnabled(true);

							atualizaTabela(modelo);
							if (jep != null) {
								jep.preencher();
							}
							txtNome.grabFocus();

						} else {
							JOptionPane.showMessageDialog(null,
									"Cpf ou cnpj já cadastrado");
						}

					} else {
						JOptionPane.showMessageDialog(null,
								"Insira um cpf ou cnpj valido");
					}

				} else {
					if (validacao.validaCpf(txtCpf.getText())
							|| validacao.validaCnpj(txtCnpj.getText())) {
						if (fController.editar(f, cpf, cnpj)) {

							atualizaTabela(modelo);

							JOptionPane.showMessageDialog(this,
									"Fornecedor editado com sucesso");
							txtBtn = "";

							limpaCampos();

							setEnableCamposFalse();

							lblEditarFornecedor.setEnabled(false);
							lblExcluirFornecedor.setEnabled(false);
							lblNovoFornecedor.setEnabled(true);

							lblSalvarFornecedor.setEnabled(false);

							tblListar.getSelectionModel().clearSelection();

							tblListar.setEnabled(true);

							atualizaTabela(modelo);
							if (jep != null) {
								jep.preencher();
							}
							txtNome.grabFocus();

						} else {
							JOptionPane.showMessageDialog(null,
									"CPF ou CNPJ já cadastrado");
						}
					} else {
						JOptionPane.showMessageDialog(null,
								"Insira um cpf ou cnpj valido");
					}

				}

			} else {
				JOptionPane.showMessageDialog(null,
						"Preencha os campos Corretamente");
			}

		}
		if (e.getSource() == lblExcluirFornecedor
				&& lblExcluirFornecedor.isEnabled()) {
			if (tblListar.getSelectedRow() != -1) {
				

				String cpf = tblListar
						.getValueAt(tblListar.getSelectedRow(), 3).toString();

				String cnpj = tblListar.getValueAt(tblListar.getSelectedRow(),
						2).toString();

				int resposta = JOptionPane.showConfirmDialog(this,
						"Deseja exluir o(a) Fornecedor '"
								+ FornecedorDao.getFornecedor(cpf, cnpj)
										.getNomeFornecedor() + "'", "",
						JOptionPane.YES_NO_OPTION);
				if (resposta == JOptionPane.YES_OPTION) {

					fController.excluirFornecedor(cpf, cnpj);
					JOptionPane.showMessageDialog(this, "Fornecedor excluído");
					limpaCampos();
					atualizaTabela(modelo);
					txtNome.grabFocus();
					setEnableCamposFalse();
					
					lblEditarFornecedor.setEnabled(false);
					lblExcluirFornecedor.setEnabled(false);
					lblNovoFornecedor.setEnabled(true);
				}
			}
		}

		if (e.getSource() == lblCancelar && lblCancelar.isEnabled()) {
			limpaCampos();
			setEnableCamposFalse();
			lblEditarFornecedor.setEnabled(false);
			lblExcluirFornecedor.setEnabled(false);
			lblNovoFornecedor.setEnabled(true);
			lblSalvarFornecedor.setEnabled(false);
			btnPrimLinha.setEnabled(true);
			btnVoltar.setEnabled(true);
			btnAvancar.setEnabled(true);
			btnUltLinha.setEnabled(true);
			txtBtn = "";
			pesquisa();
			tblListar.setEnabled(true);
			tblListar.clearSelection();
			txtNome.grabFocus();

		}

		if (e.getSource().equals(lblRelatorioFornecedor)
				&& lblRelatorioFornecedor.isEnabled()) {
			if (tblListar.getSelectedRow() != -1) {
				GerarRelatorioFornecedor gru = new GerarRelatorioFornecedor();
				ArrayList<RelatorioFornecedores> relatorioFornecedor = new ArrayList<RelatorioFornecedores>();
				RelatorioFornecedores rf = new RelatorioFornecedores();
				rf.setNome(tblListar.getValueAt(tblListar.getSelectedRow(), 0)
						.toString());
				rf.setContato(tblListar.getValueAt(tblListar.getSelectedRow(),
						1).toString());
				rf.setCnpj(tblListar.getValueAt(tblListar.getSelectedRow(), 2)
						.toString());
				rf.setCpf(tblListar.getValueAt(tblListar.getSelectedRow(), 3)
						.toString());
				rf.setTelefone(tblListar.getValueAt(tblListar.getSelectedRow(),
						4).toString());
				rf.setData("");
				rf.setDataCompleta("");
				relatorioFornecedor.add(rf);
				gru.gerarFornecedor(relatorioFornecedor);
			} else {
				if (tblListar.getRowCount() > 0) {
					GerarRelatorioFornecedor gru = new GerarRelatorioFornecedor();
					ArrayList<RelatorioFornecedores> relatorioFornecedor = new ArrayList<RelatorioFornecedores>();
					for (int i = 0; i < tblListar.getRowCount(); i++) {
						RelatorioFornecedores rf = new RelatorioFornecedores();
						rf.setNome(tblListar.getValueAt(i, 0).toString());
						rf.setContato(tblListar.getValueAt(i, 1).toString());
						rf.setCnpj(tblListar.getValueAt(i, 2).toString());
						rf.setCpf(tblListar.getValueAt(i, 3).toString());
						rf.setTelefone(tblListar.getValueAt(i, 4).toString());
						rf.setData("");
						rf.setDataCompleta("");
						relatorioFornecedor.add(rf);
					}
					gru.gerarFornecedor(relatorioFornecedor);

				}

			}

		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource().equals(lblNovoFornecedor) && lblNovoFornecedor.isEnabled()) {
			lblNovoFornecedor.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblSalvarFornecedor)
				&& lblSalvarFornecedor.isEnabled()) {
			lblSalvarFornecedor.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblEditarFornecedor)
				&& lblEditarFornecedor.isEnabled()) {
			lblEditarFornecedor.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblExcluirFornecedor)
				&& lblExcluirFornecedor.isEnabled()) {
			lblExcluirFornecedor.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblCancelar) && lblCancelar.isEnabled()) {
			lblCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblRelatorioFornecedor)
				&& lblRelatorioFornecedor.isEnabled()) {
			lblRelatorioFornecedor.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblClose)
				&& lblClose.isEnabled()) {
			lblClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if (e.getSource().equals(lblMinimize)
				&& lblMinimize.isEnabled()) {
			lblMinimize.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
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
		if (tblListar.getSelectedRow() != -1) {
			cpf = tblListar.getValueAt(tblListar.getSelectedRow(), 3)
					.toString();
			cnpj = tblListar.getValueAt(tblListar.getSelectedRow(), 2)
					.toString();

			Fornecedor f = FornecedorDao.getFornecedor(cpf, cnpj);
			txtNome.setText(f.getNomeFornecedor());
			txtContato.setText(f.getContatoFornecedor());
			if (f.getCpfFornecedor().equals("--------------")) {
				txtCnpj.setVisible(true);
				txtCnpj.setText(f.getCnpjFornecedor());
				rdbPessoaJuridica.setSelected(true);

			} else {
				txtCpf.setVisible(true);
				txtCpf.setText(f.getCpfFornecedor());

				rdbPessoaFisica.setSelected(true);

			}
			model.clear();
			for (TelFornecedor cc : f.getLstTelefones()) {
				model.addElement(cc.getTelFornecedor());
			}
			lstTelefone.setModel(model);
			txtBtn = "Editar";

			lblSalvarFornecedor.setEnabled(false);
			lblNovoFornecedor.setEnabled(false);
			lblEditarFornecedor.setEnabled(true);
			lblExcluirFornecedor.setEnabled(true);
		}
	}

	private void limpaCampos() {
		txtNome.setText("");
		txtContato.setText("");
		txtCnpj.setText("");
		txtCpf.setText("");
		txtTelefone.setText("");
		model.clear();
		lstTelefone.setModel(model);
		lblImgInfoCpf.setIcon(null);
		lblInfoCpf.setText("");
		lblImgInfoCnpj.setIcon(null);
		lblImgInfoCpf.setIcon(null);
		cnpj = "";
		cpf = "";
	}

	private void setEnableCamposFalse() {

		txtContato.setEnabled(false);
		txtCnpj.setEnabled(false);
		txtCpf.setEnabled(false);
		txtTelefone.setEnabled(false);

		btnAddTel.setEnabled(false);
		btnRemTel.setEnabled(false);
		txtTelefone.setEnabled(false);
		
		btnVoltar.setEnabled(true);
		btnAvancar.setEnabled(true);
		btnPrimLinha.setEnabled(true);
		btnUltLinha.setEnabled(true);
		verificaRelatorio();
	}

	private void setEnableCamposTrue() {

		txtContato.setEnabled(true);
		txtCnpj.setEnabled(true);
		txtCpf.setEnabled(true);
		txtTelefone.setEnabled(true);

		btnAddTel.setEnabled(true);
		btnRemTel.setEnabled(true);
		txtTelefone.setEnabled(true);
		
		
		btnVoltar.setEnabled(false);
		btnAvancar.setEnabled(false);
		btnPrimLinha.setEnabled(false);
		btnUltLinha.setEnabled(false);
		
		lblRelatorioFornecedor.setEnabled(false);
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		if (rdbPessoaFisica.isSelected()) {
			lblCpf.setVisible(true);
			lblCnpjForn.setVisible(false);
			txtCpf.setVisible(true);
			txtCnpj.setVisible(false);
			txtCnpj.setText("");
			lblImgInfoCnpj.setIcon(null);

		} else {
			txtCnpj.setVisible(true);
			txtCpf.setVisible(false);
			lblCpf.setVisible(false);
			lblCnpjForn.setVisible(true);
			txtCpf.setText("");
			lblImgInfoCpf.setIcon(null);
		}

	}

	private void pesquisa() {
		if (txtNome.getText().length() > 0) {
			atualizaTabelaPesquisa(modelo);
		} else
			atualizaTabela(modelo);
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

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		if (jh != null)
			jh.setVisible(true);
		else if (jep != null)
			jep.setVisible(true);
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

	public void preencherCampos() {
		if (tblListar.getSelectedRow() != -1) {
			setEnableCamposFalse();
			lblSalvarFornecedor.setEnabled(false);
			lblNovoFornecedor.setEnabled(false);
			lblEditarFornecedor.setEnabled(true);
			lblExcluirFornecedor.setEnabled(true);
			cpf = tblListar.getValueAt(tblListar.getSelectedRow(), 3)
					.toString();
			cnpj = tblListar.getValueAt(tblListar.getSelectedRow(), 2)
					.toString();

			Fornecedor f = FornecedorDao.getFornecedor(cpf, cnpj);
			txtNome.setText(f.getNomeFornecedor());
			txtContato.setText(f.getContatoFornecedor());
			if (f.getCpfFornecedor().equals("--------------")) {
				txtCnpj.setVisible(true);
				txtCnpj.setText(f.getCnpjFornecedor());
				rdbPessoaJuridica.setSelected(true);

			} else {
				txtCpf.setVisible(true);
				txtCpf.setText(f.getCpfFornecedor());

				rdbPessoaFisica.setSelected(true);

			}
			model.clear();
			for (TelFornecedor cc : f.getLstTelefones()) {
				model.addElement(cc.getTelFornecedor());
			}
			lstTelefone.setModel(model);
			txtBtn = "Editar";

		} else {

			JOptionPane.showMessageDialog(this, "Selecione um Fornecedor");
		}
	}

	public boolean verificarCampos() {
		if (txtNome.getText().length() > 0 && txtContato.getText().length() > 0
				&& txtCpf.getText().replaceAll("[/._-]", "").length() == 11
				|| txtCnpj.getText().replaceAll("[/._-]", "").length() == 14) {
			return true;

		} else {
			return false;
		}
	}

	public boolean verificaRelatorio() {
		if (tblListar.getRowCount() == 0) {
			lblRelatorioFornecedor.setEnabled(false);
			return false;
		}
		lblRelatorioFornecedor.setEnabled(true);
		return true;
	}

	@Override
	public void focusGained(FocusEvent e) {
		if(e.getSource()==txtCpf){
			txtCpf.setCaretPosition(new Validacoes().getCaretPositionCpf(txtCpf.getText()));
		}
		
		if(e.getSource()==txtTelefone){
			txtTelefone.setCaretPosition(new Validacoes().getCaretPositionTelefone(txtTelefone.getText()));
		}
		
		if(e.getSource()==txtCnpj){
			
			txtCnpj.setCaretPosition(new Validacoes().getCaretPositionCnpj(txtCnpj.getText()));
		}
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
