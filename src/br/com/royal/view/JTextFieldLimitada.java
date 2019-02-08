package br.com.royal.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

public class JTextFieldLimitada extends JTextField implements KeyListener {
	private byte maxLength = 0;
	private boolean backspace = false;

	public JTextFieldLimitada(int maxLength) {
		super();
		this.maxLength = (byte) maxLength;
		this.addKeyListener(this);
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = (byte) maxLength;
		update();
	}

	private void update() {
		if (getText().length() > maxLength) {
			try {
				setText(getText().substring(0, maxLength));
				setCaretPosition(maxLength);
			} catch (Exception eex) {
			}
		}
	}

	public void setText(String arg0) {
		super.setText(arg0);
		update();
	}

	public void paste() {
		super.paste();
		update();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (!backspace && getText().length() > maxLength - 1) {
			e.consume();
		}
	}
}
