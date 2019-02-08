package br.com.royal.view;

import org.opencv.core.Core;

public class App {
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		new JanelaLogin();
	}
}