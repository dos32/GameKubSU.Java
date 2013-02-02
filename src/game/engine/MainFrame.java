package game.engine;

import game.Runner;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Graphics2D mainGraphics;
	protected final Runner runner;
	
	public void paint(Graphics g) {
		runner.renderer.render(mainGraphics);
	}
	
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
		setIgnoreRepaint(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, Settings.Frame.width, Settings.Frame.height);
		setResizable(false);
		setTitle("Game engine 1.0 beta");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setVisible(true);
		mainGraphics = (Graphics2D) contentPane.getGraphics();
		setIgnoreRepaint(false);
	}

}
