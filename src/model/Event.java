package model;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class Event extends Entity {
	// Propriétés du type d'entité
	public static final String TABLE = "events";
	public static final String SINGLE = "event";
	public static final EntityFields FIELDS = new EntityFields(Entity.FIELDS);
	static {
		FIELDS.addField("name", "String");
		FIELDS.addField("user", "User");
	}
	public static final EntityModel[] SINGLE_JOIN = {User.model};
	
	// Modèle du type d'entité
	public static EntityModel model;
	static {
		try {
			model = new EntityModel("model.Event");
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException
				| SecurityException e) {
			e.printStackTrace();
		}
	}
	
	
	/*
	 * Constructeur
	 */
	public Event(ResultSet res) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException, ClassNotFoundException, SQLException {
		super(model, res);
	}
	
	/*
	 * toString
	 * Affiche l'instance sous forme textuelle
	 */
	public String toString() {
		return get("id")+"/"+get("name")+"/"+get("user");
	}
}