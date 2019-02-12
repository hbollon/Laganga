/*
 * Fichier : User.java
 * Auteur : Julien Valverdé
 */
package model;

import java.sql.*;


/*
 * User
 * Une instance du modèle. Une instance du modèle représente une table de la base de données.
 */
public class User extends Model {
	// Propriétés du modèle
	public static final String TABLE = "users";
	public static final String SINGLE = "user";
	
	
	// Champs de la table
	public String firstName;
	public String lastName;
	
	
	/*
	 * Constructeur
	 */
	public User(ModelFactory factory) {
		super(factory);
	}
	
	/*
	 * save
	 * Sauvegarde les champs de la table issus du ResultSet
	 */
	public void save(ResultSet res) throws Exception {
		super.save(res);
		
		firstName = res.getString(getPrefix()+"firstname");
		lastName = res.getString(getPrefix()+"lastname");
	}
	
	/*
	 * update
	 */
	protected void update() throws SQLException {
		super.update();
		
		res.updateString(getPrefix()+"firstname", firstName);
		res.updateString(getPrefix()+"lastname", lastName);
	}
	public void updateDatabase() throws SQLException {
		update();
		
		res.updateRow();
	}
	
	/*
	 * toString
	 * Affiche l'instance sous forme textuelle
	 */
	public String toString() {
		return getID()+"/"+firstName+"/"+lastName;
	}
}