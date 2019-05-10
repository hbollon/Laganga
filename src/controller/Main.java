package controller;

import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import model.*;
import model.entities.Event;
import view.*;

public abstract class Main {
	public static void main(String[] args) throws Exception {
		// Désactivation de la décoration des fenêtres par Swing
		JFrame.setDefaultLookAndFeelDecorated(false);
		
		// Affichage du splash screen
		new SplashWin();
		
		// Initialisation de la connexion à la base de données
		TimeUnit.SECONDS.sleep(1);
		Database.database.connect();
		
		// Bypass de la connexion pour le debugging (connexion de l'utilisateur 1)
		LocalUser.localUser.login("julien.valverde@netc.fr", "issou");
		
		/*
		System.out.println(User.factory.getByID(1));
		Group group1 = (Group) Group.factory.getByID(1);
		System.out.println(group1.getOwner());
		*/
		Event event = (Event) Event.factory.getByID(1);
		System.out.println(event);
	}
}