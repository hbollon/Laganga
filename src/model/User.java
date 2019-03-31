package model;

import java.sql.ResultSet;

/**
 * Une Entity est une entrée de table.
 * 
 * @author Julien Valverdé
 */
public class User extends Entity {
	// Propriétés du type d'entité
	public static final String TABLE = "users";
	public static final String SINGLE = "user";
	
	public static EntityFields fields;
	static {
		String[] names = {"firstName", "lastName", "email", "birth"};
		String[] types = {"String", "String", "String", "Date"};
		
		fields = new EntityFields(Entity.fields, names, types);
	}
	
	/*
	 * Constructeur
	 */
	public User(EntityFactory factory, ResultSet res) throws Exception {
		super(factory, res);
	}
	
	/*
	 * toString
	 * Affiche l'instance sous forme textuelle
	 */
	public String toString() {
		String str = "User no. "+get("id")+":\n";
		str += "\t- firstName: "+get("firstName")+"\n";
		str += "\t- lastName: "+get("lastName")+"\n";
		str += "\t- email: "+get("email")+"\n";
		str += "\t- birth: "+get("birth")+"\n";
		
		return str;
	}
}