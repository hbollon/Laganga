/*
 * Fichier : Event.java
 * Auteur : Julien Valverdé
 */
package model;

import java.sql.*;


/*
 * User
 * Une instance du modèle. Une instance du modèle représente une table de la base de données.
 */
public class Event extends Model {
	// Propriétés du modèle
	public static final String TABLE = "events";
	public static final String SINGLE = "event";
	public static final ModelFactory[] RELATIONS = {ModelFactory.users};
	
	
	// Champs de la table
	public String name;
	private User user;
	
	public User getUser() {
		return user;
	}
	
	
	/*
	 * Constructeur
	 */
	public Event(ModelFactory factory) {
		super(factory);
	}
	
	/*
	 * save
	 * Sauvegarde les champs de la table issus du ResultSet
	 */
	public void save(ResultSet res) throws Exception {
		super.save(res);
		
		name = res.getString(getPrefix()+"name");
		user = (User) ModelFactory.users.getObjectFromResultSet(res);
	}
	
	/*
	 * update
	 */
	protected void update() throws SQLException {
		super.update();
		
		res.updateString(getPrefix()+"name", name);
		user.updateDatabase();
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
		return getID()+"/"+name+"/"+user;
	}
}