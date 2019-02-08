package br.com.royal.view;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FTable extends DefaultTableModel {

	public boolean isCellEditable(int row, int column) {  
	        return false;  
	    }}
