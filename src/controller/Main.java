package controller;

import java.sql.*;
import model.Database;

public abstract class Main {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// Tentative de connexion à la base de données
		try {
			System.out.println("Connexion en cours...");
			Database.database = new Database("os-vps418.infomaniak.ch:3306", "l2_gr2", "l2_gr2", "5KUavzaM");
			System.out.println("Connexion réussie !");
		}
		catch (Exception e) {
			// Actions à effectuer si la connexion a échoué
			System.out.println("Connexion à MySQL impossible !");
			e.printStackTrace();
		}
	}
}