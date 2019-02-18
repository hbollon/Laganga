package model;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

/*
 * User
 * Une instance du modèle. Une instance du modèle représente une table de la base de données.
 */
public class User extends Entity {
	// Propriétés du modèle
	public static final String TABLE = "users";
	public static final String SINGLE = "user";
	public static final EntityFields FIELDS = new EntityFields(Entity.FIELDS);
	static {
		FIELDS.addField("firstName", "String");
		FIELDS.addField("lastName", "String");
		FIELDS.addField("email", "String");
		FIELDS.addField("birth", "Date");
	}
	
	/*
	 * Constructeur
	 */
	public User(EntityModel model, ResultSet res) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, SQLException {
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