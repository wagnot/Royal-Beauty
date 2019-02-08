package br.com.royal.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class RTableAgendamento extends DefaultTableCellRenderer{
	public RTableAgendamento (){
		setOpaque(true);
	}
	public Component getTableCellRendererComponent(JTable minhaTable, Object valor, boolean selecionada,
			boolean focada, int linha, int coluna){
		super.getTableCellRendererComponent(minhaTable, valor, selecionada, focada, linha, coluna);
		for (int i=0; i<minhaTable.getRowCount(); i++){
			minhaTable.setRowHeight(i, 40);
		}
		setBackground(new Color (164, 6, 69));
		setForeground(Color.WHITE);
		setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color (48, 49, 50)));
		return this;
	}
	
	
}