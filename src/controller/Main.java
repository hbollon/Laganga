package controller;

import java.util.*;
import model.*;
import view.*;

public abstract class Main {
	public static void main(String[] args) throws Exception {
		// Connexion à la base de données
		try {
			System.out.println("Connexion en cours...");
			Database.database = new Database("localhost", "l2_gr2", "l2_gr2", "5KUavzaM");
			System.out.println("Connexion réussie !");
		}
		catch (Exception e) {
			// Actions à effectuer si la connexion a échoué
			System.out.println("Connexion à MySQL impossible !");
			//throw e;
		}
		
		// Création d'une usine d'entités
		EntityFactory users = new EntityFactory("model.User");
		
		// Affichage de tous les utilisateurs
		ArrayList<Entity> usersList = users.getAll();
		
		for (int i = 0; i < usersList.size(); i++)
			System.out.println(usersList.get(i));
		
		// Fenêtre principale
		//MainWin fenetre = new MainWin();
		//fenetre.setVisible(true);
		
		// Calendrier de la fenêtre principale
		//MainWinCalendar calendar = new MainWinCalendar(fenetre);
	}
}