package br.com.royal.view;

import java.awt.Color;
import java.awt.Font;
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
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.text.MaskFormatter;

import br.com.royal.controller.CaixaController;
import br.com.royal.controller.EstoqueController;
import br.com.royal.controller.FormaDePagamentoController;
import br.com.royal.controller.MotivoSaidaController;
import br.com.royal.controller.PagamentoController;
import br.com.royal.controller.SaidaProdutoController;
import br.com.royal.controller.VendaController;
import br.com.royal.controllerReports.GerarComanda;
import br.com.royal.model.Caixa;
import br.com.royal.model.Cartao;
import br.com.royal.model.Cheque;
import br.com.royal.model.Cliente;
import br.com.royal.model.ComandaProduto;
import br.com.royal.model.ComandaServico;
import br.com.royal.model.Estoque;
import br.com.royal.model.Fiado;
import br.com.royal.model.FormaDePagamento;
import br.com.royal.model.MotivoSaida;
import br.com.royal.model.Pagamento;
import br.com.royal.model.Parcela;
import br.com.royal.model.Produto;
import br.com.royal.model.QuantidadeParcela;
import br.com.royal.model.SaidaProduto;
import br.com.royal.model.Servico;
import br.com.royal.modelReports.ReciboComanda;

public class JanelaPagamento extends JDialog implements ActionListener,
		MouseListener, MouseMotionListener, WindowListener, KeyListener,
		ItemListener {
	private Color corOriginalBtn = new Color(164, 6, 69);
	private int posX, posY;

	private JNumberFormatField txtValor;
	private JTextField txtNumeroCheque, txtNomeBancoCheque;
	private JLabel lblFundo, lblFormaPagamento, lblParcelas, lblValor,
			lblTotal, lblNumeroCheque, lblNomeBancoCheque, lblDataAbate,
			lblTrocoDeve, lblTxtTrocoDeve, lblInfoParcela;
	private JFormattedTextField txtDataAbate;
	private MaskFormatter mascaraData;
	private JComboBox<QuantidadeParcela> cmbParcelas;
	private JComboBox<Object> cmbFormaPagamentos;
	private JButton btnPagar, btnVoltar, btnDebitar;
	private JList<Servico> lstServicos;
	private JList<Produto> lstProdutos;
	private DefaultListModel<Servico> modeloServico = new DefaultListModel<Servico>();
	private DefaultListModel<Produto> modeloProduto = new DefaultListModel<Produto>();
	private JRadioButton rbtDebito, rbtCredito;
	private ButtonGroup grupoButtons;

	// inserir pagamento antes da parcela
	private ArrayList<Cartao> listaCartao = new ArrayList<Cartao>();
	private ArrayList<Cheque> listaCheque = new ArrayList<Cheque>();
	private ArrayList<Fiado> listaFiado = new ArrayList<Fiado>();

	private ArrayList<Pagamento> listaPagamento = new ArrayList<Pagamento>();
	private ArrayList<Parcela> parcelas = new ArrayList<Parcela>();

	private double total = 0, deve = 0, valorDinheiro = 0, desconto = 0;
	private ComandaProduto comandaProduto;
	private ComandaServico comandaServico;
	private Cliente cliente;
	private JanelaComanda jc;
	private String dataAbate = "";


	public JanelaPagamento(JanelaComanda jc, ComandaServico s,
			ComandaProduto p, double total, Cliente cliente, double desconto) {
		super(jc, true);
		this.jc = jc;
		this.total = total-desconto;
		this.deve = this.total;

		this.comandaProduto = p;
		this.comandaServico = s;
		this.cliente = cliente;

		this.desconto = desconto;

		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
		}
		setTitle("Pagamento de Comanda");
		setSize(780, 550);
		setResizable(false);
		setLayout(null);
		setUndecorated(true);
		setLocationRelativeTo(null);
		addWindowListener(this);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		IC();

		setVisible(true);
	}

	private void IC() {
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		lblTotal = new JLabel("Total: R$" + String.format("%.2f", total),
				JLabel.CENTER);
		lblTotal.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 32));
		lblTotal.setBounds(0, 50, getWidth(), 30);
		add(lblTotal);

		lblFormaPagamento = new JLabel("Forma de pagamento");
		lblFormaPagamento.setFont(new Font("Century Gothic",
				Font.TRUETYPE_FONT, 16));
		lblFormaPagamento.setBounds(180, 110, 220, 30);
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

		btnDebitar = new JButton("Debitar");
		btnDebitar.setBackground(corOriginalBtn);
		btnDebitar.setForeground(Color.WHITE);
		btnDebitar.setFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
		btnDebitar.setBounds(txtValor.getX() + txtValor.getWidth() + 30,
				txtValor.getY(), 100, 30);
		add(btnDebitar);
		btnDebitar.addActionListener(this);

		// Cartao
		lblParcelas = new JLabel("Parcelas:");
		lblParcelas.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 16));
		lblParcelas.setBounds(lblFormaPagamento.getX() + 70,
				cmbFormaPagamentos.getY() + 80, 140, 30);
		lblParcelas.setVisible(false);
		add(lblParcelas);

		cmbParcelas = new JComboBox<QuantidadeParcela>();
		cmbParcelas.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 15));
		cmbParcelas.setBounds(lblParcelas.getX() + 75, lblParcelas.getY(), 80,
				30);
		cmbParcelas.setVisible(false);

		for (QuantidadeParcela pp : PagamentoController.getQuantidadeParcelas()) {

			cmbParcelas.addItem(pp);
		}
		add(cmbParcelas);

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
				cmbFormaPagamentos.getY() + 80, 140, 30);
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
		lblDataAbate = new JLabel("Data Abate:");
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

		txtDataAbate.addKeyListener(this);
		try {
			mascaraData = new MaskFormatter("##/##/####");
			mascaraData.setPlaceholderCharacter('_');
			mascaraData.install(txtDataAbate);
		} catch (Exception ex) {
		}

		// ---

		lblTrocoDeve = new JLabel("Deve: R$");
		lblTrocoDeve
				.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 25));
		lblTrocoDeve.setBounds(310, 400, 120, 30);
		add(lblTrocoDeve);

		lblTxtTrocoDeve = new JLabel(String.format("%.2f", total));
		lblTxtTrocoDeve.setFont(new Font("Century Gothic", Font.TRUETYPE_FONT,
				25));
		lblTxtTrocoDeve.setBounds(lblTrocoDeve.getX() + 113,
				lblTrocoDeve.getY(), 120, 30);
		add(lblTxtTrocoDeve);

		btnVoltar = new JButton("Voltar");
		btnVoltar.setBackground(corOriginalBtn);
		btnVoltar.setForeground(Color.WHITE);
		btnVoltar.setFont(new Font("Arial", Font.TRUETYPE_FONT, 20));
		btnVoltar.setBounds(240, lblTrocoDeve.getY() + 60, 120, 32);
		add(btnVoltar);
		btnVoltar.addActionListener(this);

		btnPagar = new JButton("Finalizar");
		btnPagar.setBackground(corOriginalBtn);
		btnPagar.setForeground(Color.WHITE);
		btnPagar.setFont(new Font("Arial", Font.TRUETYPE_FONT, 20));
		btnPagar.setBounds(btnVoltar.getX() + 180, btnVoltar.getY(), 120, 32);
		add(btnPagar);
		btnPagar.addActionListener(this);
		btnPagar.setEnabled(false);

		lblFundo = new JLabel(new ImageIcon("Img/Janela de Pagamento.png"));
		lblFundo.setBounds(0, 0, getWidth(), getHeight());
		add(lblFundo);

		setComponentesVisibleFalse();

		this.addKeyListener(this);
		cmbFormaPagamentos.addKeyListener(this);
		txtValor.addKeyListener(this);
		btnDebitar.addKeyListener(this);
		txtNomeBancoCheque.addKeyListener(this);
		txtNumeroCheque.addKeyListener(this);
		cmbParcelas.addKeyListener(this);
		rbtCredito.addKeyListener(this);
		rbtDebito.addKeyListener(this);
		txtDataAbate.addKeyListener(this);
		btnVoltar.addKeyListener(this);
		btnPagar.addKeyListener(this);
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
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

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
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
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

		if (e.getSource() == btnDebitar) {
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

		if (e.getSource() == btnPagar) {
			Parcela parcela = new Parcela();
			parcela.setValorParcela(valorDinheiro);
			parcela.setCodQuantidadeParcela(1);
			parcela.setCodFormaDePagamento(3);

			parcelas.add(parcela);

			Pagamento pagamento = new Pagamento();
			if (listaFiado.size() > 0)
				pagamento.setDataPagamento(dataAbate);
			else
				pagamento.setDataPagamento(comandaProduto.getDataComanda());

			pagamento.setCodCliente(cliente.getCod());
			pagamento.setDesconto(desconto);
			pagamento.setTotalPagamento(total);

			VendaController vc = new VendaController();
			vc.cadastrosVenda(pagamento, comandaServico, comandaProduto,
					parcelas, listaCartao, listaCheque, listaFiado);
			
			ArrayList<ReciboComanda> relatorioComanda = new ArrayList<ReciboComanda>();
			ReciboComanda rc = new ReciboComanda();
			GerarComanda grc = new GerarComanda();
			rc.setNomecliente(cliente.getNome());
			rc.setObservacoes(comandaServico.getObservacao());
			double subTotalProdutos=0, subTotalServicos=0;
			for (Produto p : comandaProduto.getProdutos()){
				if (comandaProduto.getProdutos().size()>1){
					if (rc.getProdutos()!=null){
						rc.setProdutos(rc.getProdutos()+"Nome: "+p.getNomeProduto()+", Quantidade: "
								+p.getQuantidadeProdutoComanda()+", Valor: R$ "+p.getValor()+", Subtotal: "+
								(p.getValor()*p.getQuantidadeProdutoComanda()+"\n"));
					}else{
						rc.setProdutos("Nome: "+p.getNomeProduto()+", Quantidade: "
							+p.getQuantidadeProdutoComanda()+", Valor: R$ "+p.getValor()+
							", Subtotal: "+(p.getValor()*p.getQuantidadeProdutoComanda()+"\n"));
					}
				}else if (comandaProduto.getProdutos().size()==1){
					rc.setProdutos("Nome: "+p.getNomeProduto()+", Quantidade: "+
					p.getQuantidadeProdutoComanda()+", Valor: R$ "+p.getValor()+", Subtotal: "+
					(p.getValor()*p.getQuantidadeProdutoComanda()+"\n"));
				}else{
					rc.setProdutos("");
				}
			}
			for (Servico s: comandaServico.getServicos()){
				if (comandaServico.getServicos().size()>1){
					if (rc.getServicos()!=null){
						rc.setServicos(rc.getServicos()+"Nome: "+s.getNomeServico()+
								"Valor cobrado: R$ "+s.getValorServico()+"\n");
					}else{
						rc.setServicos("Nome: "+s.getNomeServico()+
								"Valor cobrado: R$ "+s.getValorServico()+"\n");
					}
					
				}else if (comandaServico.getServicos().size()==1){
					rc.setServicos("Nome: "+s.getNomeServico()+
							"Valor cobrado: R$ "+s.getValorServico());
				}else{
					rc.setServicos("");
				}
			}
			subTotalProdutos=jc.getSubTotalProduto();
			subTotalServicos=jc.getSubTotalServico();
			rc.setSubtotalprodutos(subTotalProdutos+"");
			rc.setSubtotalservicos(subTotalServicos+"");
			rc.setTotal(pagamento.getTotalPagamento()+"");
			rc.setDesconto(desconto+"");
			rc.setDataCompleta("");
			relatorioComanda.add(rc);
			grc.gerarComanda(relatorioComanda);
			
			
			MotivoSaida ms = new MotivoSaida();
			MotivoSaidaController msc = new MotivoSaidaController();
			EstoqueController eController = new EstoqueController();
			SaidaProdutoController spController = new SaidaProdutoController();
			ms = msc.pegarMotivo("Venda");

			for (Produto p : comandaProduto.getProdutos()) {
				SaidaProduto sp = new SaidaProduto();
				Estoque estoque = new Estoque();

				sp.setQuantidadeSaidaProduto((p.getQuantidadeProdutoComanda()));
				sp.setCodMotivoSaidaProduto(ms.getCodMotivoSaida());
				sp.setDataSaidaProduto(comandaProduto.getDataComanda().substring(0, 10));

				sp.setMotivo(ms.getDescricaoMotivoSaida());

				sp.setCodProduto(p.getId());

				estoque.setCodProduto(p.getId());

				estoque.setQuantidadeEstoqueProduto(eController
						.pegarEstoque(estoque) - sp.getQuantidadeSaidaProduto());
				spController.cadastrar(sp);
				eController.acaoEstoque(estoque);
			}

			CaixaController cc = new CaixaController();
			Caixa caixa = new Caixa();
			caixa.setValorAtual(valorDinheiro);
			caixa.setDataCaixa(comandaProduto.getDataComanda());
			cc.cadastrarVendaEntradaCaixa(caixa);
			
			JOptionPane.showMessageDialog(this,
					"Pagamento concluído com sucesso");

			this.dispose();
			jc.dispose();
		}

		if (e.getSource() == btnVoltar) {
			fechar();
		}
	}

	private void fechar() {
		if (JOptionPane.showConfirmDialog(this,
				"Você ira perder todos os dados ao voltar para a comanda.\n"
						+ "Deseja continuar?") == JOptionPane.YES_OPTION) {
			this.dispose();
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

	private double verificaValorParcela(double valor, Parcela p, int indexCombo) {
		if (valor > deve)
			valor = valor - (valor - deve);
		p.setValorParcela(valor / p.getCodQuantidadeParcela());
		if (indexCombo != 3)
			parcelas.add(p);
		return valor;
	}

	private void calculaDeveTroco(double valor) {
		if (valor >= deve) {
			deve = valor - deve;
			lblTrocoDeve.setText("Troco: R$");
			setTextoFormatadoDeveTroco(deve);
			btnPagar.setEnabled(true);
			btnDebitar.setEnabled(false);
		} else {
			deve -= valor;
			setTextoFormatadoDeveTroco(deve);
		}

	}

	private void setTextoFormatadoDeveTroco(double valor) {
		lblTxtTrocoDeve.setText(String.format("%.2f", valor));
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

	@Override
	public void keyPressed(KeyEvent e) {
		if (((KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,
				java.awt.event.InputEvent.ALT_DOWN_MASK)) != null)
				&& e.getKeyCode() == KeyEvent.VK_F4)
			fechar();

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (rbtDebito.isSelected()) {
			cmbParcelas.setEnabled(false);
			cmbParcelas.setSelectedIndex(0);
		} else
			cmbParcelas.setEnabled(true);
	}

}
