package Window;

import java.awt.Image;

import javax.swing.JFrame;

public class main {

	public static void main(String[] args) {
		JFrame fenetre = new JFrame();
		Image img = new ImageIcon("");

	    fenetre.setTitle("Laganga");
	    fenetre.setSize(1920, 1080);
	    fenetre.setLocationRelativeTo(null);
	    fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    fenetre.setVisible(true);
	  }       
	}

}