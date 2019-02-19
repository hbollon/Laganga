package model;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

/**
 * Une Entity est une entrée de table.
 * 
 * @author Julien Valverdé
 */
public class User extends Entity {
	// Propriétés du type d'entité
	public static final String TABLE = "users";
	public static final String SINGLE = "user";
	public static final EntityFields FIELDS = new EntityFields(Entity.FIELDS);
	static {
		FIELDS.addField("firstName", "String");
		FIELDS.addField("lastName", "String");
		FIELDS.addField("email", "String");
		FIELDS.addField("birth", "Date");
	}
	
	// Modèle du type d'entité
	public static EntityModel model;
	static {
		try {
			model = new EntityModel("model.User");
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException
				| SecurityException e) {
			e.printStackTrace();
		}
	}
	
	
	/*
	 * Constructeur
	 */
	public User(ResultSet res) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException, ClassNotFoundException, SQLException {
		super(model, res);
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