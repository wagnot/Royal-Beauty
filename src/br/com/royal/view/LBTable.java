package br.com.royal.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class LBTable extends JPanel implements MouseListener {
	private JLabel lblCelulas[][];
	private int dimensaoX = 0, dimensaoY = 0, lblX = 0, lblY = 0;
	private ArrayList linhas, colunas;
	private Color corOriginalLBLPar = new Color(242, 250, 223), corBusy = new Color(250, 208, 213),
			corOriginalLBLImpar = new Color(248, 250, 244), corHoverLBL = new Color(250, 208, 212);
	private boolean ocupado;
	private JanelaAgendamento ja;

	public JLabel[][] getLblCelulas() {
		return lblCelulas;
	}

	public void setLblCelulas(JLabel[][] lblCelulas) {
		this.lblCelulas = lblCelulas;
	}

	public int getLblY() {
		return lblY;
	}

	public void setLblY(int lblY) {
		this.lblY = lblY;
	}

	public ArrayList getLinhas() {
		return linhas;
	}

	public void setLinhas(ArrayList linhas) {
		this.linhas = linhas;
	}

	public ArrayList getColunas() {
		return colunas;
	}

	public void setColunas(ArrayList colunas) {
		this.colunas = colunas;
	}

	public Color getCorOriginalLBLPar() {
		return corOriginalLBLPar;
	}

	public void setCorOriginalLBLPar(Color corOriginalLBLPar) {
		this.corOriginalLBLPar = corOriginalLBLPar;
	}

	public Color getCorBusy() {
		return corBusy;
	}

	public void setCorBusy(Color corBusy) {
		this.corBusy = corBusy;
	}

	public Color getCorOriginalLBLImpar() {
		return corOriginalLBLImpar;
	}

	public void setCorOriginalLBLImpar(Color corOriginalLBLImpar) {
		this.corOriginalLBLImpar = corOriginalLBLImpar;
	}

	public Color getCorHoverLBL() {
		return corHoverLBL;
	}

	public void setCorHoverLBL(Color corHoverLBL) {
		this.corHoverLBL = corHoverLBL;
	}

	public boolean isOcupado() {
		return ocupado;
	}

	public void setOcupado(boolean ocupado) {
		this.ocupado = ocupado;
	}

	public int getLblX() {
		return lblX;
	}

	public void setLblX(int lblX) {
		this.lblX = lblX;
	}

	public int getDimensaoX() {
		return dimensaoX;
	}

	public void setDimensaoX(int dimensaoX) {
		this.dimensaoX = dimensaoX;
	}

	public int getDimensaoY() {
		return dimensaoY;
	}

	public void setDimensaoY(int dimensaoY) {
		this.dimensaoY = dimensaoY;
	}

	public LBTable(JanelaAgendamento ja, ArrayList linhas, ArrayList colunas) {
		this.ja = ja;
		this.linhas = linhas;
		this.colunas = colunas;
		setLayout(null);
		inicializaComponentes();
		setVisible(true);
	}

	private void inicializaComponentes() {
		lblCelulas = new JLabel[linhas.size() + 1][colunas.size() + 1];
		setaCelulas(linhas, colunas);
		calculaDimensaoX();
		calculaDimensaoY();
		populaFuncionarios();
		populaHorarios(linhas);
		populaActions();
	}

	public void setaCelulas(ArrayList linhas, ArrayList colunas) {
		for (int i = 0; i < linhas.size() + 1; i++) {
			for (int j = 0; j < colunas.size() + 1; j++) {
				lblCelulas[i][j] = new JLabel();
				lblCelulas[i][j].setOpaque(true);
				if (i > 0 && i % 2 == 1) {
					lblCelulas[i][j].setBackground(corOriginalLBLImpar);
				} else if (i > 0 && i % 2 == 0) {
					lblCelulas[i][j].setBackground(corOriginalLBLPar);
				}
				lblCelulas[i][j].setFont(new Font("Century Gothic", Font.TRUETYPE_FONT, 14));
				lblCelulas[i][j].setBounds(lblX, lblY, 200, 40);
				lblCelulas[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				lblCelulas[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				add(lblCelulas[i][j]);
				lblX += lblCelulas[i][j].getWidth();
			}
			lblX = 0;
			lblY += lblCelulas[0][0].getHeight();
		}
	}

	private void calculaDimensaoX() {
		for (int i = 0; i <= colunas.size(); i++) {
			dimensaoX += lblCelulas[0][i].getWidth();
		}
	}

	private void calculaDimensaoY() {
		for (int i = 0; i <= linhas.size(); i++) {
			dimensaoY += lblCelulas[i][0].getHeight();
		}
	}

	public void populaFuncionarios() {
		lblCelulas[0][0].setText("Horários");
		lblCelulas[0][0].setBackground(new Color(164, 6, 69));
		lblCelulas[0][0].setForeground(Color.WHITE);
		for (int i = 1; i < colunas.size() + 1; i++) {
			lblCelulas[0][i].setText(colunas.get(i - 1).toString());
			lblCelulas[0][i].setBackground(new Color(164, 6, 69));
			lblCelulas[0][i].setForeground(Color.WHITE);
		}
	}

	public void populaHorarios(ArrayList linhas) {
		for (int i = 1; i < linhas.size() + 1; i++) {
			lblCelulas[i][0].setText(linhas.get(i - 1).toString());
		}
	}

	public void populaActions() {
		for (int i = 1; i < linhas.size() + 1; i++) {
			for (int j = 1; j < colunas.size() + 1; j++) {
				lblCelulas[i][j].addMouseListener(this);
			}

		}
	}

	public void preencheOcupados(int x, int y) {
		lblCelulas[x][y].setBackground(corBusy);
		lblCelulas[x][y].removeMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JLabel lbl = (JLabel) e.getSource();
		if (e.getClickCount() == 2) {
			ja.getCalendario().setEnabled(true);
			lbl.setBackground(corBusy);
			// lbl.setText("<html> <center>Luan Lucas <br> Escova
			// </center></html>");
			lbl.removeMouseListener(this);
			lbl.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			lbl.grabFocus();
			JOptionPane.showMessageDialog(null, "Você clicou duas vezes em um label!");
			
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JLabel lbl = (JLabel) e.getSource();
		lbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lbl.setBackground(corHoverLBL);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JLabel lbl = (JLabel) e.getSource();
		lbl.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		for (int i = 1; i < linhas.size() + 1; i++) {
			for (int j = 1; j < colunas.size() + 1; j++) {
				if (lblCelulas[i][j].getBackground().getRed() == lbl.getBackground().getRed()
						&& lblCelulas[i][j].getBackground().getGreen() == lbl.getBackground().getGreen()
						&& lblCelulas[i][j].getBackground().getBlue() == lbl.getBackground().getBlue()) {
					if (i % 2 == 1) {
						lbl.setBackground(corOriginalLBLImpar);
					} else if (i % 2 == 0) {
						lbl.setBackground(corOriginalLBLPar);
					}

				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
