package game.engine;

import game.Runner;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	public JPanel contentPane;
	protected final Runner runner;
	public MainCanvas mainCanvas;
	
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame(new Runner());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/
	
	public MainFrame(Runner runner) {
		this.runner = runner;
		setTitle("Game engine 1.0 beta");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainCanvas = new MainCanvas(runner);
		mainCanvas.setPreferredSize(new Dimension((int) Settings.World.width, (int) Settings.World.height));
		add(mainCanvas);
		setResizable(false);
		pack();
		setVisible(true);
	}

}
