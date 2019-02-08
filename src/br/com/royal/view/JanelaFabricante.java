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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.hssf.record.formula.TblPtg;

import br.com.royal.controller.FabricanteController;
import br.com.royal.controllerReports.GerarRelatorioFabricante;
import br.com.royal.model.Fabricante;
import br.com.royal.modelReports.RelatorioFabricante;

public class JanelaFabricante extends JDialog
		implements ActionListener, MouseListener, MouseMotionListener, KeyListener, WindowListener {
	private JanelaProduto jp;
	private JLabel lblNome, lblFundo, lblNavbar, lblClose, lblNovoFabricante, lblSalvarFabricante, lblEditarFabricante,
			lblCancelar, lblnRelatorioFabricante;
	private JTextField txtNome;

	private FabricanteController fController = new FabricanteController();
	private Color corOriginalBtn = new Color(164, 6, 69);
	private JTable tabela;

	private JScrollPane scrollTabela;
	private FTable modelo = new FTable();
	private String txtBtn = "";
	private JButton btnPrimLinha, btnVoltar, btnAvancar, btnUltLinha;
	private String nomeFabricante = "";

	private int posX, posY;

	public JanelaFabricante(JanelaProduto jp) {
		super(jp, true);
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		this.jp = jp;

		setLayout(null);
		setSize(602, 420);
		setTitle("Fabricante");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setUndecorated(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Img/Rosa.png"));
		inicializarComponentes();
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

		lblNome = new JLabel("Fabricante:");
		lblNome.setForeground(Color.BLACK);
		// lblNome.setOpaque(true);
		// lblNome.setBackground(Color.GREEN);
		lblNome.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 19));
		lblNome.setBounds(50, 110, 105, 20);
		add(lblNome);

		txtNome = new JTextField();
		txtNome.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
		txtNome.setBounds(lblNome.getX() + 110, lblNome.getY() - 5, 270, 30);
		txtNome.addKeyListener(this);
		add(txtNome);

		lblNovoFabricante = new JLabel(new ImageIcon("Img/Fabricante/Novo.png"));
		lblNovoFabricante.setBackground(corOriginalBtn);
		lblNovoFabricante.setForeground(Color.WHITE);
		lblNovoFabricante.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		lblNovoFabricante.setBounds(480, 32, 41, 39);
		lblNovoFabricante.setToolTipText("Novo");
		lblNovoFabricante.addMouseListener(this);
		add(lblNovoFabricante);

		lblSalvarFabricante = new JLabel(new ImageIcon("Img/Fabricante/Salvar.png"));
		lblSalvarFabricante.setBackground(corOriginalBtn);
		lblSalvarFabricante.setForeground(Color.WHITE);
		lblSalvarFabricante.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		lblSalvarFabricante.setBounds(lblNovoFabricante.getX(), lblNovoFabricante.getY() + 50, 41, 39);
		lblSalvarFabricante.setToolTipText("Salvar");
		lblSalvarFabricante.addMouseListener(this);
		add(lblSalvarFabricante);

		lblEditarFabricante = new JLabel(new ImageIcon("Img/Fabricante/Editar.png"));
		lblEditarFabricante.setBackground(corOriginalBtn);
		lblEditarFabricante.setForeground(Color.WHITE);
		lblEditarFabricante.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		lblEditarFabricante.setBounds(lblNovoFabricante.getX(), lblSalvarFabricante.getY() + 50, 41, 39);
		lblEditarFabricante.setToolTipText("Editar");
		lblEditarFabricante.addMouseListener(this);
		add(lblEditarFabricante);

		lblCancelar = new JLabel(new ImageIcon("Img/CancelarMenor.png"));
		lblCancelar.setBackground(corOriginalBtn);
		lblCancelar.setForeground(Color.WHITE);
		lblCancelar.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		lblCancelar.setBounds(lblNovoFabricante.getX(), lblEditarFabricante.getY() + 50, 33, 33);
		lblCancelar.setToolTipText("Cancelar");
		lblCancelar.addMouseListener(this);
		add(lblCancelar);

		lblnRelatorioFabricante = new JLabel(new ImageIcon("Img/RelatorioMenorzin.png"));
		lblnRelatorioFabricante.setBackground(corOriginalBtn);
		lblnRelatorioFabricante.setForeground(Color.WHITE);
		lblnRelatorioFabricante.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		lblnRelatorioFabricante.setBounds(lblCancelar.getX() + 2, lblCancelar.getY() + 47, 29, 32);
		lblnRelatorioFabricante.setToolTipText("Relatório");
		lblnRelatorioFabricante.addMouseListener(this);
		add(lblnRelatorioFabricante);

		btnPrimLinha = new JButton("<<");
		btnPrimLinha.setBackground(corOriginalBtn);
		btnPrimLinha.setForeground(Color.WHITE);
		btnPrimLinha.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
		btnPrimLinha.setToolTipText("Primeira linha");
		btnPrimLinha.setBounds(180, 225, 53, 40);
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

		lblEditarFabricante.setEnabled(false);

		lblNovoFabricante.setEnabled(true);

		lblSalvarFabricante.setEnabled(false);

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

		lblFundo = new JLabel(new ImageIcon("Img/Gerenciamento de Fabricantes.png"));
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
		tabela.setFont(new Font ("Century Gothic", Font.TRUETYPE_FONT, 12));
		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabela.setSelectionBackground(new Color (222, 54, 121));
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.getTableHeader().setDefaultRenderer(new RTable());
		scrollTabela = new JScrollPane(tabela);

		//scrollTabela.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		scrollTabela.setBounds(60, 270, 480, 130);
		tabela.getTableHeader().setResizingAllowed(false);

		add(scrollTabela);

		modelo.addColumn("Nome do Fabricante");

		preencherJtable(modelo);

		tabela.getColumnModel().getColumn(0).setPreferredWidth(483);
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

		for (Fabricante f : fController.listar()) {
			modelo.addRow(new Object[] { f.getNomeFabricante() });

		}

	}

	public void pesquisaJtable(DefaultTableModel modelo) {
		modelo.setNumRows(0);
		if (fController.achou(txtNome.getText()) != null) {
			for (Fabricante e : fController.achou(txtNome.getText())) {

				modelo.addRow(new Object[] { e.getNomeFabricante() });

			}
		}
	}

	public boolean inserir() {
		try {
			if (txtNome.getText().length() > 0) {

				Fabricante f = new Fabricante();
				f.setNomeFabricante(txtNome.getText());

				if (fController.inserir(f)) {
					// pai.motivo(f);

					JOptionPane.showMessageDialog(null, "Fabricante inserido com sucesso");
					preencherJtable(modelo);
					jp.getJcComboFabricante().removeAllItems();
					jp.getJcComboFabricante().addItem(" ");
					jp.preencher();
					return true;

				} else {
					JOptionPane.showMessageDialog(null, "Fabricante já cadastrado, insira um fabricante válido");
					txtNome.setText("");
					return false;
				}

			} else {
				JOptionPane.showMessageDialog(null, "O campo fabricante é obrigatório");
				return false;
			}
		} catch (Exception e) {
			System.out.println("Erroooow:" + e.getMessage());
			return false;
		}

	}

	public boolean editar() {
		try {
			if (txtNome.getText().length() > 0) {

				Fabricante f = new Fabricante();
				f.setNomeFabricante(txtNome.getText());

				if (fController.editarFabricante(f, nomeFabricante)) {
					// pai.motivo(f);
					JOptionPane.showMessageDialog(null, "Fabricante editado com sucesso");
					preencherJtable(modelo);
					if (jp!=null){
						jp.getJcComboFabricante().removeAllItems();
						jp.getJcComboFabricante().addItem(" ");
						jp.preencher();
					}
					return true;

				} else {
					JOptionPane.showMessageDialog(null, "Fabricante já cadastrado, insira um fabricante válido");
					txtNome.setText("");
					return false;
				}

			} else {
				JOptionPane.showMessageDialog(null, "O campo fabricante é obrigatório");
				return false;
			}
		} catch (Exception e) {
			System.out.println("Erroooow:" + e.getMessage());
			return false;
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnPrimLinha) {
			lblNovoFabricante.setEnabled(false);
			tabela.setRowSelectionInterval(0, 0);
			lblEditarFabricante.setEnabled(true);
			campoFalse();

			dadosTabela();
			tabela.grabFocus();
			
		}
		if (e.getSource() == btnUltLinha) {
			lblNovoFabricante.setEnabled(false);
			tabela.setRowSelectionInterval(tabela.getModel().getRowCount() - 1, tabela.getModel().getRowCount() - 1);
			lblEditarFabricante.setEnabled(true);
			campoFalse();

			dadosTabela();
			tabela.grabFocus();
			
		}
		if (e.getSource() == btnAvancar) {
			if (tabela.getSelectedRow() < tabela.getModel().getRowCount() - 1) {
				lblNovoFabricante.setEnabled(false);
				tabela.setRowSelectionInterval(tabela.getSelectedRow() + 1, tabela.getSelectedRow() + 1);
				lblEditarFabricante.setEnabled(true);
				campoFalse();
				dadosTabela();
				tabela.grabFocus();
				
			}
		}
		if (e.getSource() == btnVoltar) {
			lblNovoFabricante.setEnabled(false);
			if (tabela.getSelectedRow() > 0) {
				tabela.setRowSelectionInterval(tabela.getSelectedRow() - 1, tabela.getSelectedRow() - 1);
				lblEditarFabricante.setEnabled(true);
				campoFalse();
				dadosTabela();
				tabela.grabFocus();
				
			} else {
				tabela.clearSelection();
				lblEditarFabricante.setEnabled(false);
				lblNovoFabricante.setEnabled(true);
				txtNome.setEnabled(true);
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
			lblEditarFabricante.setEnabled(false);
			lblNovoFabricante.setEnabled(true);
			lblSalvarFabricante.setEnabled(false);
			verificaRelatorio();
			txtBtn = "";
			pesquisa();
			tabela.setEnabled(true);
			txtNome.setEnabled(true);
			txtNome.grabFocus();

		}
		if (e.getSource().equals(lblnRelatorioFabricante) && lblnRelatorioFabricante.isEnabled()){
			if (tabela.getSelectedRow()!=-1){
				GerarRelatorioFabricante gru = new GerarRelatorioFabricante();
				ArrayList<RelatorioFabricante> relatorioFabricantes = new ArrayList<RelatorioFabricante>();
				RelatorioFabricante rf = new RelatorioFabricante();
				rf.setNome(tabela.getValueAt(tabela.getSelectedRow(), 0).toString());
				rf.setData("");
				rf.setDataCompleta("");
				relatorioFabricantes.add(rf);
				gru.gerarServicos(relatorioFabricantes);
			}else if (tabela.getRowCount()>0){
				GerarRelatorioFabricante gru = new GerarRelatorioFabricante();
				ArrayList<RelatorioFabricante> relatorioFabricantes = new ArrayList<RelatorioFabricante>();
				for (int i=0; i<tabela.getRowCount(); i++){
					RelatorioFabricante rf = new RelatorioFabricante();
					rf.setNome(tabela.getValueAt(i, 0).toString());
					rf.setData("");
					rf.setDataCompleta("");
					relatorioFabricantes.add(rf);
				}
				gru.gerarServicos(relatorioFabricantes);
			}
		}
		if (e.getSource().equals(lblNovoFabricante) && lblNovoFabricante.isEnabled()) {
			btnPrimLinha.setEnabled(false);
			btnVoltar.setEnabled(false);
			btnAvancar.setEnabled(false);
			btnUltLinha.setEnabled(false);
			lblnRelatorioFabricante.setEnabled(false);
			tabela.clearSelection();
			txtNome.setEnabled(true);
			lblSalvarFabricante.setEnabled(true);
			lblEditarFabricante.setEnabled(false);
			lblNovoFabricante.setEnabled(false);
			txtBtn = "Novo";
			tabela.setEnabled(false);
			if (txtNome.getText().length() < 1) {
				txtNome.grabFocus();
			}
		}

		if (e.getSource().equals(lblSalvarFabricante) && lblSalvarFabricante.isEnabled()) {

			if (txtBtn.equals("Novo")) {
				if (inserir()) {
					txtBtn = "";
					limpaCampos();

					lblEditarFabricante.setEnabled(false);

					lblNovoFabricante.setEnabled(true);
					lblSalvarFabricante.setEnabled(false);
					tabela.getSelectionModel().clearSelection();
					tabela.setEnabled(true);
					// preencherJtable(modelo);
					txtNome.grabFocus();
					txtNome.setEnabled(true);
					btnPrimLinha.setEnabled(true);
					btnVoltar.setEnabled(true);
					btnAvancar.setEnabled(true);
					btnUltLinha.setEnabled(true);
					verificaRelatorio();
					tabela.setEnabled(true);
				} else {

				}
			} else {
				if (editar()) {
					txtBtn = "";
					limpaCampos();

					lblEditarFabricante.setEnabled(false);

					lblNovoFabricante.setEnabled(true);

					lblSalvarFabricante.setEnabled(false);
					tabela.getSelectionModel().clearSelection();
					tabela.setEnabled(true);
					txtNome.setEnabled(true);
					// preencherJtable(modelo);
					txtNome.grabFocus();
					verificaRelatorio();
					tabela.setEnabled(true);

				}
			}
		}
		if (e.getSource().equals(lblEditarFabricante) && lblEditarFabricante.isEnabled()) {
			btnPrimLinha.setEnabled(false);
			btnVoltar.setEnabled(false);
			btnAvancar.setEnabled(false);
			btnUltLinha.setEnabled(false);
			if (tabela.getSelectedRow() != -1) {
				txtNome.setEnabled(true);

				lblSalvarFabricante.setEnabled(true);
				// setando enable = false nos botoes novo, editar e excluir
				lblNovoFabricante.setEnabled(false);
				lblEditarFabricante.setEnabled(false);
				nomeFabricante = (String) tabela.getValueAt(tabela.getSelectedRow(), 0);
				lblnRelatorioFabricante.setEnabled(false);
				dadosTabela();

				txtBtn = "Editar";

				tabela.setEnabled(false);
				tabela.getSelectionModel().clearSelection();

				txtNome.grabFocus();

			} else {
				JOptionPane.showMessageDialog(null, "Selecione um Fabricante para editar");
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
			lblNovoFabricante.setEnabled(false);
			lblEditarFabricante.setEnabled(true);

		} else {
			lblEditarFabricante.setEnabled(false);

		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void limpaCampos() {
		txtNome.setText("");
	}

	public void dadosTabela() {
		txtNome.setText(tabela.getValueAt(tabela.getSelectedRow(), 0).toString());
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (tabela.isEnabled() && tabela.getSelectedRow() > -1) {
			if (e.getKeyCode() == e.VK_DOWN || e.getKeyCode() == e.VK_RIGHT)
				dadosTabela();
			if (e.getKeyCode() == e.VK_UP || e.getKeyCode() == e.VK_LEFT)
				dadosTabela();
		}	
		
		if (txtNome.hasFocus() && txtBtn.equals("")) {
			pesquisa();
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
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		
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
	
	public boolean verificaRelatorio() {
		if (tabela.getRowCount() == 0) {
			lblnRelatorioFabricante.setEnabled(false);
			return false;
		}
		lblnRelatorioFabricante.setEnabled(true);
		return true;
	}

}
