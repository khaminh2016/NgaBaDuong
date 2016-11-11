/*
 * 
 * 
 * 
 * 
 */
package ponggame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * 
 * @author Invisible Man 2
 *
 */
public class MainGameUI extends JFrame{
	private static final int _HEIGHT = 500;
	private static final int _WIDTH = 500;
	private PongPanel pongPanel;
	
	public MainGameUI(){
		setTitle("Pong Game - K21T Ltd.");
		pongPanel = new PongPanel();
		setPreferredSize(new Dimension(_WIDTH, _HEIGHT));
		setLayout(new BorderLayout());
		getContentPane().add(pongPanel, BorderLayout.CENTER);
		setLocationRelativeTo(null);
		this.setResizable(false);
		pack();
	}

    public static void main(String[] args) {
       MainGameUI mainFrame = new MainGameUI();
       mainFrame.setVisible(true);
       mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}