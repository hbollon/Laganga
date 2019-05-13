package controller;

import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import model.Database;
import model.LocalUser;
import view.SplashWin;

public abstract class Main {
	public static void main(String[] args) throws Exception {
		// Désactivation de la décoration des fenêtres par Swing
		JFrame.setDefaultLookAndFeelDecorated(false);
		
		// Affichage du splash screen
		new SplashWin();
		
		// Initialisation de la connexion à la base de données
		TimeUnit.SECONDS.sleep(1);
		Database.database.connect();
		
		/*
		Exemple insertion d'event
		
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("name", "Gazage des juifs");
		values.put("type", "Autodafé");
		values.put("priority", 6000000);
		values.put("begin", new GregorianCalendar());
		values.put("end", new GregorianCalendar());
		values.put("location", Location.factory.getByID(2));
		
		Event event2 = (Event) Event.factory.insert(values);
		System.out.println(event2);
		*/
		
		// Bypass de la connexion pour le debugging (connexion de l'utilisateur 1)
		LocalUser.localUser.login("simon.bernoud@gmail.com", "bernoud");
	}
}