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
	public String email;
	public Date birth;
	
	
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
		
		firstName = res.getString(getPrefix()+"firstName");
		lastName = res.getString(getPrefix()+"lastName");
		email = res.getString(getPrefix()+"email");
		birth = res.getDate(getPrefix()+"birth");
	}
	
	/*
	 * update
	 */
	protected void update() throws SQLException {
		super.update();
		
		res.updateString(getPrefix()+"firstName", firstName);
		res.updateString(getPrefix()+"lastName", lastName);
		res.updateString(getPrefix()+"email", email);
		res.updateDate(getPrefix()+"birth", birth);
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
		String str = "User no. "+getID()+":\n";
		str += "\t- firstName: "+firstName+"\n";
		str += "\t- lastName: "+lastName+"\n";
		str += "\t- email: "+email+"\n";
		str += "\t- birth: "+birth+"\n";
		
		return str;
	}
}