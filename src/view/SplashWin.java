package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Le SplashWin est le splash screen affiché au démarrage pendant la connexion à MySQL.
 * 
 * @author Julien Valverdé
 */
public class SplashWin extends JFrame {
	private static final long serialVersionUID = -4244014342067339792L;
	
	public SplashWin() throws Exception {
		setTitle("Laganga");
		setLocationRelativeTo(null);
		setSize(400, 300);
		setResizable(false);
		
		Container content = getContentPane();
		content.setLayout(new BorderLayout());
		content.setBackground(Color.WHITE);
		
		// Image du splash
		JLabel image = new JLabel(new ImageIcon(new ImageIcon("./res/splash.png").getImage().getScaledInstance(400, 300, Image.SCALE_DEFAULT)), JLabel.CENTER);
		
		content.add(image, BorderLayout.CENTER);
		//System.out.println(image.getWidth()+" "+image.getHeight());
		
		// Texte de statut
		content.add(new JLabel("Connexion...", JLabel.CENTER), BorderLayout.SOUTH);
		
		setVisible(true);
	}
}