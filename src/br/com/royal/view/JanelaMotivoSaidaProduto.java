package br.com.royal.view;

import java.awt.Color;
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
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
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

import br.com.royal.controller.MotivoSaidaController;
import br.com.royal.controllerReports.GerarMotivoSaidaProduto;
import br.com.royal.model.MotivoSaida;
import br.com.royal.modelReports.RelatorioMotivoSaidaProduto;

public class JanelaMotivoSaidaProduto extends JDialog
		implements ActionListener, MouseListener, MouseMotionListener, KeyListener, WindowListener {
	private JanelaSaidaProduto jep;
	private JLabel lblMinimize, lblNome, lblFundo, lblNavbar, lblClose, lblNovoMotivoSaidaProduto,
	lblSalvarMotivoSaidaProduto, lblEditarMotivoSaidaProduto, lblCancelar, lblRelatorioMotivoSaidaProduto;
	private JTextField txtNome;
	private JButton btnPrimLinha, btnVoltar, btnAvancar, btnUltLinha;
	private MotivoSaidaController msController = new MotivoSaidaController();
	private Color corOriginalBtn = new Color(164, 6, 69);
	private int posX, posY;
	private JTable tabela;
	private JScrollPane scrollTabela;
	private FTable modelo = new FTable();
	private String txtBtn = "";
	private String motivo = "";

	public JanelaMotivoSaidaProduto(JanelaSaidaProduto jc) {
		super(jc, true);
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		this.jep = jc;
		setLayout(null);
		setTitle("Motivo");
		setSize(602, 420);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Img/Rosa.png"));
		setResizable(false);
		setUndecorated(true);
		inicializarComponentes();
		// Ultima linha
		setVisible(true);
		addWindowListener(this);
	}

	public void inicializarComponentes() {
		criaTabela();
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		
		lblFundo = new JLabel();
				lblNome = new JLabel("Motivo:");
		lblNome.setForeground(Color.BLACK);
		// lblNome.setOpaque(true);
		// lblNome.setBackground(Color.GREEN);
		lblNome.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 19));
		lblNome.setBounds(65, 110, 105, 20);
		add(lblNome);

		txtNome = new JTextField();
		txtNome.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		txtNome.setBounds(lblNome.getX() + 80, lblNome.getY() - 5, 280, 30);
		txtNome.addKeyListener(this);
		add(txtNome);

		lblNovoMotivoSaidaProduto = new JLabel(new ImageIcon("Img/Motivo de Entrada de Produto/Novo.png"));
		lblNovoMotivoSaidaProduto.setBackground(corOriginalBtn);
		lblNovoMotivoSaidaProduto.setForeground(Color.WHITE);
		lblNovoMotivoSaidaProduto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		lblNovoMotivoSaidaProduto.setBounds(480, 35, 41, 39);
		lblNovoMotivoSaidaProduto.setToolTipText("Novo");
		lblNovoMotivoSaidaProduto.addMouseListener(this);
		add(lblNovoMotivoSaidaProduto);

		lblSalvarMotivoSaidaProduto = new JLabel(new ImageIcon("Img/Motivo de Entrada de Produto/Salvar.png"));
		lblSalvarMotivoSaidaProduto.setBackground(corOriginalBtn);
		lblSalvarMotivoSaidaProduto.setForeground(Color.WHITE);
		lblSalvarMotivoSaidaProduto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		lblSalvarMotivoSaidaProduto.setBounds(lblNovoMotivoSaidaProduto.getX(),
				lblNovoMotivoSaidaProduto.getY() + 50, 41, 39);
		lblSalvarMotivoSaidaProduto.setToolTipText("Salvar");
		lblSalvarMotivoSaidaProduto.addMouseListener(this);
		add(lblSalvarMotivoSaidaProduto);

		lblEditarMotivoSaidaProduto = new JLabel(new ImageIcon("Img/Motivo de Entrada de Produto/Editar.png"));
		lblEditarMotivoSaidaProduto.setBackground(corOriginalBtn);
		lblEditarMotivoSaidaProduto.setForeground(Color.WHITE);
		lblEditarMotivoSaidaProduto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		lblEditarMotivoSaidaProduto.setBounds(lblNovoMotivoSaidaProduto.getX(),
				lblSalvarMotivoSaidaProduto.getY() + 50, 41, 39);
		lblEditarMotivoSaidaProduto.setToolTipText("Editar");
		lblEditarMotivoSaidaProduto.addMouseListener(this);
		add(lblEditarMotivoSaidaProduto);

		lblCancelar = new JLabel(new ImageIcon("Img/CancelarMenor.png"));
		lblCancelar.setBackground(corOriginalBtn);
		lblCancelar.setForeground(Color.WHITE);
		lblCancelar.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		lblCancelar.setBounds(lblNovoMotivoSaidaProduto.getX() + 2, lblEditarMotivoSaidaProduto.getY() + 50, 38, 33);
		lblCancelar.setToolTipText("Cancelar");
		lblCancelar.addMouseListener(this);
		add(lblCancelar);

		lblRelatorioMotivoSaidaProduto = new JLabel(new ImageIcon("Img/RelatorioMenorzin.png"));
		lblRelatorioMotivoSaidaProduto.setBackground(corOriginalBtn);
		lblRelatorioMotivoSaidaProduto.setForeground(Color.WHITE);
		lblRelatorioMotivoSaidaProduto.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		lblRelatorioMotivoSaidaProduto.setBounds(lblCancelar.getX() + 5, lblCancelar.getY() + 50, 29, 32);
		lblRelatorioMotivoSaidaProduto.setToolTipText("Relatório");
		lblRelatorioMotivoSaidaProduto.addMouseListener(this);
		add(lblRelatorioMotivoSaidaProduto);

		btnPrimLinha = new JButton("<<");
		btnPrimLinha.setBackground(corOriginalBtn);
		btnPrimLinha.setForeground(Color.WHITE);
		btnPrimLinha.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
		btnPrimLinha.setToolTipText("Primeira linha");
		btnPrimLinha.setBounds(180, 240, 53, 40);
		add(btnPrimLinha);
		btnPrimLinha.addActionListener(this);

		btnVoltar = new JButton("<");
		btnVoltar.setBackground(corOriginalBtn);
		btnVoltar.setForeground(Color.WHITE);
		btnVoltar.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
		btnVoltar.setBounds(btnPrimLinha.getX() + 55, btnPrimLinha.getY(), btnPrimLinha.getWidth() - 10,
				btnPrimLinha.getHeight());
		btnVoltar.setToolTipText("Voltar uma linha");
		add(btnVoltar);
		btnVoltar.addActionListener(this);

		btnAvancar = new JButton(">");
		btnAvancar.setBackground(corOriginalBtn);
		btnAvancar.setForeground(Color.WHITE);
		btnAvancar.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
		btnAvancar.setBounds(btnVoltar.getX() + 55, btnVoltar.getY(), btnVoltar.getWidth(), btnVoltar.getHeight());
		btnAvancar.setToolTipText("Avançar linha");
		add(btnAvancar);
		btnAvancar.addActionListener(this);

		btnUltLinha = new JButton(">>");
		btnUltLinha.setBackground(corOriginalBtn);
		btnUltLinha.setForeground(Color.WHITE);
		btnUltLinha.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
		btnUltLinha.setBounds(btnAvancar.getX() + 45, btnAvancar.getY(), btnPrimLinha.getWidth(),
				btnAvancar.getHeight());

		btnUltLinha.setToolTipText("Ultima linha");
		add(btnUltLinha);
		btnUltLinha.addActionListener(this);

		lblEditarMotivoSaidaProduto.setEnabled(false);

		lblNovoMotivoSaidaProduto.setEnabled(true);

		lblSalvarMotivoSaidaProduto.setEnabled(false);

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

		lblFundo = new JLabel(new ImageIcon("Img/Motivo de Saída do Produto.png "));
		lblFundo.setBounds(0, 0, getWidth(), getHeight());
		add(lblFundo);
		verificaRelatorio();
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
		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabela.setFont(new Font ("Century Gothic", Font.TRUETYPE_FONT, 12));
		tabela.setSelectionBackground(new Color (222, 54, 121));
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.getTableHeader().setDefaultRenderer(new RTable());
		scrollTabela = new JScrollPane(tabela);

		// scrollTabela
		// .setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		scrollTabela.setBounds(80, 285, 445, 110);
		tabela.getTableHeader().setResizingAllowed(false);

		add(scrollTabela);

		modelo.addColumn("Descrição");

		preencherJtable(modelo);

		tabela.getColumnModel().getColumn(0).setPreferredWidth(443);
		tabela.getTableHeader().setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 12));

		((DefaultTableCellRenderer) tabela.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);
		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		for (int i = 0; i < tabela.getColumnCount(); i++) {
			DefaultTableCellRenderer cellRender = new DefaultTableCellRenderer();
			cellRender.setHorizontalAlignment(SwingConstants.CENTER);
			tabela.getColumnModel().getColumn(i).setCellRenderer(cellRender);
		}

		tabela.getTableHeader().setReorderingAllowed(false);
	}

	public void preencherJtable(DefaultTableModel modelo) {

		modelo.setNumRows(0);

		for (MotivoSaida e : msController.listar()) {
			modelo.addRow(new Object[] { e.getDescricaoMotivoSaida() });

		}

	}

	public void pesquisaJtable(DefaultTableModel modelo) {
		modelo.setNumRows(0);
		if (msController.achou(txtNome.getText()) != null) {
			for (MotivoSaida e : msController.achou(txtNome.getText())) {

				modelo.addRow(new Object[] { e.getDescricaoMotivoSaida() });

			}
		}
	}

	public boolean inserir() {
		try {
			if (txtNome.getText().length() > 0) {

				MotivoSaida ms = new MotivoSaida();
				ms.setDescricaoMotivoSaida(txtNome.getText());

				if (msController.inserir(ms)) {

					JOptionPane.showMessageDialog(null, "Motivo inserido com sucesso");
					preencherJtable(modelo);
					//jep.preencher();
					return true;
				} else {
					JOptionPane.showMessageDialog(null, "Motivo já cadastrado, insira um motivo válido");
					txtNome.setText("");
					return false;
				}

			} else {
				JOptionPane.showMessageDialog(null, "O campo motivo é obrigatório");
				return false;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Preencha o campo corretamente");
			return false;
		}

	}

	public boolean editar() {
		try {
			if (txtNome.getText().length() > 0) {

				MotivoSaida ms = new MotivoSaida();
				ms.setDescricaoMotivoSaida(txtNome.getText());

				if (msController.editarMotivo(ms, motivo)) {

					JOptionPane.showMessageDialog(null, "Motivo editado com sucesso");
					preencherJtable(modelo);
					jep.preencher();
					return true;
				} else {
					JOptionPane.showMessageDialog(null, "Motivo já cadastrado, insira um motivo válido");
					txtNome.setText("");
					return false;
				}

			} else {
				JOptionPane.showMessageDialog(null, "O campo motivo é obrigatório");
				return false;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Preencha o campo corretamente");
			return false;
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnPrimLinha) {
			lblNovoMotivoSaidaProduto.setEnabled(false);
			tabela.setRowSelectionInterval(0, 0);
			lblEditarMotivoSaidaProduto.setEnabled(true);
			campoFalse();

			dadosTabela();
			tabela.grabFocus();
		}
		if (e.getSource() == btnUltLinha) {
			lblNovoMotivoSaidaProduto.setEnabled(false);
			tabela.setRowSelectionInterval(tabela.getModel().getRowCount() - 1, tabela.getModel().getRowCount() - 1);
			lblEditarMotivoSaidaProduto.setEnabled(true);
			campoFalse();

			dadosTabela();
			tabela.grabFocus();
		}
		if (e.getSource() == btnAvancar) {
			if (tabela.getSelectedRow() < tabela.getModel().getRowCount() - 1) {
				lblNovoMotivoSaidaProduto.setEnabled(false);
				tabela.setRowSelectionInterval(tabela.getSelectedRow() + 1, tabela.getSelectedRow() + 1);
				lblEditarMotivoSaidaProduto.setEnabled(true);
				campoFalse();
				dadosTabela();
				tabela.grabFocus();
			}
		}
		if (e.getSource() == btnVoltar) {
			lblNovoMotivoSaidaProduto.setEnabled(false);
			if (tabela.getSelectedRow() > 0) {
				tabela.setRowSelectionInterval(tabela.getSelectedRow() - 1, tabela.getSelectedRow() - 1);
				lblEditarMotivoSaidaProduto.setEnabled(true);
				campoFalse();
				dadosTabela();
				tabela.grabFocus();
			} else {
				tabela.clearSelection();
				lblEditarMotivoSaidaProduto.setEnabled(false);

				limpaCampos();
			}
		}

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		setLocation(getLocation().x + e.getX() - posX, getLocation().y + e.getY() - posY);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(lblClose)) {
			this.dispose();
		}
		if (e.getSource().equals(lblCancelar) && lblCancelar.isEnabled()) {
			limpaCampos();
			btnPrimLinha.setEnabled(true);
			btnVoltar.setEnabled(true);
			btnAvancar.setEnabled(true);
			btnUltLinha.setEnabled(true);
			verificaRelatorio();
			lblEditarMotivoSaidaProduto.setEnabled(false);
			lblNovoMotivoSaidaProduto.setEnabled(true);
			lblSalvarMotivoSaidaProduto.setEnabled(false);
			txtBtn = "";
			pesquisa();
			tabela.setEnabled(true);
			txtNome.setEnabled(true);
			txtNome.grabFocus();

		}

		if (e.getSource().equals(lblNovoMotivoSaidaProduto) && lblNovoMotivoSaidaProduto.isEnabled()) {
			btnPrimLinha.setEnabled(false);
			btnVoltar.setEnabled(false);
			btnAvancar.setEnabled(false);
			btnUltLinha.setEnabled(false);
			lblRelatorioMotivoSaidaProduto.setEnabled(false);
			tabela.clearSelection();
			txtNome.setEnabled(true);
			lblSalvarMotivoSaidaProduto.setEnabled(true);
			lblEditarMotivoSaidaProduto.setEnabled(false);
			lblNovoMotivoSaidaProduto.setEnabled(false);
			txtBtn = "Novo";
			tabela.setEnabled(false);
			if (txtNome.getText().length() < 1) {
				txtNome.grabFocus();
			}
		}

		if (e.getSource().equals(lblSalvarMotivoSaidaProduto) && lblSalvarMotivoSaidaProduto.isEnabled()) {

			if (txtBtn.equals("Novo")) {
				if (inserir()) {
					txtBtn = "";
					limpaCampos();

					lblEditarMotivoSaidaProduto.setEnabled(false);
					btnPrimLinha.setEnabled(true);
					btnVoltar.setEnabled(true);
					btnAvancar.setEnabled(true);
					btnUltLinha.setEnabled(true);
					verificaRelatorio();
					lblNovoMotivoSaidaProduto.setEnabled(true);
					lblSalvarMotivoSaidaProduto.setEnabled(false);
					tabela.getSelectionModel().clearSelection();
					tabela.setEnabled(true);
					// preencherJtable(modelo);
					txtNome.grabFocus();
					txtNome.setEnabled(true);

					tabela.setEnabled(true);
				} else {

				}
			} else {
				if (editar()) {
					txtBtn = "";
					limpaCampos();

					lblEditarMotivoSaidaProduto.setEnabled(false);

					lblNovoMotivoSaidaProduto.setEnabled(true);

					lblSalvarMotivoSaidaProduto.setEnabled(false);
					tabela.getSelectionModel().clearSelection();
					tabela.setEnabled(true);
					txtNome.setEnabled(true);
					// preencherJtable(modelo);
					txtNome.grabFocus();

					tabela.setEnabled(true);

				}
			}
		}

		if (e.getSource().equals(lblEditarMotivoSaidaProduto) && lblEditarMotivoSaidaProduto.isEnabled()) {
			btnPrimLinha.setEnabled(false);
			btnVoltar.setEnabled(false);
			btnAvancar.setEnabled(false);
			btnUltLinha.setEnabled(false);
			lblRelatorioMotivoSaidaProduto.setEnabled(false);
			if (tabela.getSelectedRow() != -1) {
				txtNome.setEnabled(true);

				lblSalvarMotivoSaidaProduto.setEnabled(true);
				// setando enable = false nos botoes novo, editar e excluir
				lblNovoMotivoSaidaProduto.setEnabled(false);
				lblEditarMotivoSaidaProduto.setEnabled(false);
				motivo = (String) tabela.getValueAt(tabela.getSelectedRow(), 0);

				dadosTabela();

				txtBtn = "Editar";

				tabela.setEnabled(false);
				tabela.getSelectionModel().clearSelection();

				txtNome.grabFocus();

			} else {
				JOptionPane.showMessageDialog(null, "Selecione um motivo para editar");
			}

		}
		if (e.getSource().equals(lblRelatorioMotivoSaidaProduto) && lblRelatorioMotivoSaidaProduto.isEnabled()){
			if (tabela.getSelectedRow()!=-1){
				GerarMotivoSaidaProduto gru = new GerarMotivoSaidaProduto();
				ArrayList<RelatorioMotivoSaidaProduto> relatorioMotivosSaidaProduto = new ArrayList<RelatorioMotivoSaidaProduto>();
				RelatorioMotivoSaidaProduto rm = new RelatorioMotivoSaidaProduto();
				rm.setDescricao(tabela.getValueAt(tabela.getSelectedRow(), 0).toString());
				rm.setData("");
				rm.setDataCompleta("");
				relatorioMotivosSaidaProduto.add(rm);
				gru.gerarMotivoSaidaProduto(relatorioMotivosSaidaProduto);
			}else if (tabela.getRowCount()>0){
				GerarMotivoSaidaProduto gru = new GerarMotivoSaidaProduto();
				ArrayList<RelatorioMotivoSaidaProduto> relatorioMotivosSaidaProduto = new ArrayList<RelatorioMotivoSaidaProduto>();
				for (int i=0; i<tabela.getRowCount(); i++){
					RelatorioMotivoSaidaProduto rm = new RelatorioMotivoSaidaProduto();
					rm.setDescricao(tabela.getValueAt(i, 0).toString());
					rm.setData("");
					rm.setDataCompleta("");
					relatorioMotivosSaidaProduto.add(rm);
				}
				gru.gerarMotivoSaidaProduto(relatorioMotivosSaidaProduto);
			}
		}

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
		if (e.getSource().equals(lblNavbar)) {
			posX = e.getX();
			posY = e.getY();
		} else if (tabela.getSelectedRow() != -1) {
			dadosTabela();
			campoFalse();
			lblNovoMotivoSaidaProduto.setEnabled(false);
			lblEditarMotivoSaidaProduto.setEnabled(true);

		} else {
			lblEditarMotivoSaidaProduto.setEnabled(false);

		}
	}

	public void limpaCampos() {
		txtNome.setText("");
	}

	public void dadosTabela() {
		txtNome.setText(tabela.getValueAt(tabela.getSelectedRow(), 0).toString());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (txtNome.hasFocus() && txtBtn.equals("")) {
			pesquisa();
		}
		
		if (tabela.isEnabled() && tabela.getSelectedRow() > -1) {
			if (e.getKeyCode() == e.VK_DOWN || e.getKeyCode() == e.VK_RIGHT)
				dadosTabela();
			if (e.getKeyCode() == e.VK_UP || e.getKeyCode() == e.VK_LEFT)
				dadosTabela();
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void pesquisa() {
		if (txtNome.getText().length() > 0) {
			pesquisaJtable(modelo);

		} else {
			preencherJtable(modelo);
		}

	}

	public void campoFalse() {
		txtNome.setEnabled(false);
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

	public boolean verificaRelatorio() {
		if (tabela.getRowCount() == 0) {
			lblRelatorioMotivoSaidaProduto.setEnabled(false);
			return false;
		}
		lblRelatorioMotivoSaidaProduto.setEnabled(true);
		return true;
	}
}
