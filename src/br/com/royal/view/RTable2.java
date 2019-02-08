package br.com.royal.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class RTable2 extends DefaultTableCellRenderer {
	public RTable2() {
		setOpaque(true);
	}

	public Component getTableCellRendererComponent(JTable minhaTable,
			Object valor, boolean selecionada, boolean focada, int linha,
			int coluna) {
		super.getTableCellRendererComponent(minhaTable, valor, selecionada,
				focada, linha, coluna);
		setBackground(new Color(164, 6, 69));
		setForeground(Color.WHITE);
		setBorder(noFocusBorder);
		return this;
	}

}
