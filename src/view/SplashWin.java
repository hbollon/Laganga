package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Le SplashWindow est le splash screen affiché au démarrage pendant la connexion à MySQL.
 * 
 * @author Julien Valverdé
 */
public class SplashWin extends JFrame {
	private static final long serialVersionUID = -4244014342067339792L;
	
	public SplashWin() throws Exception {
		JFrame.setDefaultLookAndFeelDecorated(true);
		
		setTitle("Laganga");
		setLocationRelativeTo(null);
		setSize(400, 300);
		setResizable(false);
		getContentPane().setBackground(Color.WHITE);
		
		setLayout(new BorderLayout());
		
		// Image du splash
		JLabel splash;
		
		BufferedImage splashImage = ImageIO.read(new File("./res/splash.png"));
		splash = new JLabel(new ImageIcon(splashImage));
		
		add(splash, BorderLayout.CENTER);
		
		// Texte de statut
		add(new JLabel("Connexion...", JLabel.CENTER), BorderLayout.SOUTH);
		
		setVisible(true);
	}
}