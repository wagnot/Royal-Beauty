package br.com.royal.controller;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

public class ExecutaCamera implements Runnable {
	static JLabel label;
	
	static private ExecutaCamera myThread = null;
	static VideoCapture  webSource = null;

	
	static Mat frame = new Mat();
	static MatOfByte mem = new MatOfByte();

		public void reproduzir(JLabel label){
			this.label=label;
			webSource = new VideoCapture(0);

			myThread = new ExecutaCamera();
			Thread t = new Thread(myThread);
			t.setDaemon(true);
			myThread.runnable = true;

			t.start();
		}

		public Mat capturar() {
			myThread.runnable = false;
			webSource.release();
			return frame;
		}

		
		public void salvar() {
			 Imgcodecs.imwrite("C:/Royal/src/br/com/royal/imagemCamera/cliente.jpg", frame);
		}

		public void encerrar() {
			webSource.release();
		}
		
		protected volatile boolean runnable = false;

		@Override
		public void run() {
			synchronized (this) {
				while (runnable) {
					if (webSource.grab()) {
						try {
							webSource.retrieve(frame);
							Imgcodecs.imencode(".bmp", frame, mem);
							Image im = ImageIO.read(new ByteArrayInputStream(
									mem.toArray()));

							BufferedImage buff = (BufferedImage) im;
							Graphics g = label.getGraphics();

							if (g.drawImage(buff, 0, 0, label.getWidth(),
									label.getHeight(), 0, 0, buff.getWidth(),
									buff.getHeight(), null))

								if (runnable == false) {
									this.wait();
								}
						} catch (Exception ex) {
							System.out.println("Error");
						}
					}
				}
			}
		}
	
//	private ExecutaCamera executaCamera = null;
//	static VideoCapture video = null;
//
//	Mat frame = new Mat();
//	MatOfByte mem = new MatOfByte();
//	static JLabel label = new JLabel();
//	private volatile boolean runnable = false;
//
//	public void reproduzir(JLabel label) {
//		this.label = label;
//		video = new VideoCapture(0);
//
//		executaCamera = new ExecutaCamera();
//		Thread t = new Thread(executaCamera);
//		t.setDaemon(true);
//		executaCamera.runnable = true;
//
//		t.start();
//	}
//
//	public Mat capturar() {
//		runnable = false;
//		video.release();
//
//		video.read(frame);
//		
//		return frame;
//	}
//
//	public void encerrar() {
//		video.release();
//	}
//
//	@Override
//	public void run() {
//		synchronized (this) {
//			while (runnable) {
//				if (video.grab()) {
//					try {
//						video.retrieve(frame);
//						Imgcodecs.imencode(".bmp", frame, mem);
//						Image im = ImageIO.read(new ByteArrayInputStream(
//								mem.toArray()));
//
//						BufferedImage buff = (BufferedImage) im;
//						Graphics g = label.getGraphics();
//
//						if (g.drawImage(buff, 0, 0, label.getWidth(),
//								label.getHeight(), 0, 0, buff.getWidth(),
//								buff.getHeight(), null))
//
//							if (runnable == false) {
//								this.wait();
//							}
//					} catch (Exception ex) {
//						System.out.println("Error");
//					}
//				}
//			}
//		}
//
//	}
}
