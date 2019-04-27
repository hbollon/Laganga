package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import model.Database;
import model.LocalUser;

/**
 * Le SplashWin est le splash screen affiché au démarrage pendant la connexion à MySQL.
 * 
 * @author Julien Valverdé
 */
@SuppressWarnings("deprecation")
public class SplashWin extends JFrame implements Observer {
	private static final long serialVersionUID = -4244014342067339792L;
	
	public SplashWin() throws Exception {
		super();
		
		setTitle("Laganga");
		setLocationRelativeTo(null);
		setSize(400, 300);
		setResizable(false);
		
		Container content = getContentPane();
		content.setLayout(new BorderLayout());
		content.setBackground(Color.WHITE);
		
		// Image du splash
		JLabel image = new JLabel(new ImageIcon(new ImageIcon("./res/loading.gif").getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT)), JLabel.CENTER);
		
		content.add(image, BorderLayout.CENTER);
		//System.out.println(image.getWidth()+" "+image.getHeight());
		
		// Texte de statut
		content.add(new JLabel("Chargement...", JLabel.CENTER), BorderLayout.SOUTH);
		
		// Observer les états de la base de données
		Database.database.addObserver(this);
		
		// Permet de changer le curseur lors d'une opération avec la BDD
		new CursorChanger(this);
		
		setVisible(true);
	}

	@Override
	public void update(Observable database, Object status) {
		switch ((int) status) {
			// Connexion réussie, afficher la fenêtre de connexion
			case Database.CONNECTION_SUCCESS:
				new LoginWin();
				break;
			
			// Connexion échouée, afficher une fenêtre d'erreur
			case Database.CONNECTION_FAILURE:
				new FatalErrorWin("Connexion au serveur de base de données impossible. Veuillez consulter les logs pour plus d'informations.");
				break;
		}
		
		dispose();
	}
}