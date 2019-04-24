package controller;

import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import model.*;
import view.*;

public abstract class Main {
	public static void main(String[] args) throws Exception {
		// Splash screen
		SplashWin splash = new SplashWin();
		
		TimeUnit.SECONDS.sleep(1);
		try {
			// Connexion à la base de données
			Database.database.connect();
		}
		catch (Exception e) {
			// Actions à effectuer si la connexion a échoué
			new FatalErrorWin(e.getMessage());
			throw e;
		}
		finally {
			splash.dispose();
		}
		
		// Initialisation de l'utilisateur local
		LocalUser localUser = new LocalUser();
		
		// Désactivation de la décoration des fenêtres par Swing
		JFrame.setDefaultLookAndFeelDecorated(false);
		
		// Affichage de la fenêtre de connexion
		//LoginWin loginWindow = new LoginWin(localUser);
		
		// Bypass de la connexion pour le debugging (connexion de l'utilisateur 1)
		localUser.login("julien.valverde@netc.fr", "issou");
		
		//System.out.println(User.factory.getByID(1));
		Group group1 = (Group) Group.factory.getByID(1);
		System.out.println(group1.getOwner());
		
		// Fenêtre principale
		MainWin fenetre;
		fenetre = new MainWin();
		fenetre.setVisible(true);
		
		// Calendrier de la fenêtre principale
		MainWinCalendar calendar = new MainWinCalendar(fenetre);
	}
}