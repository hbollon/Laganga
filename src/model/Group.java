package model;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

/**
 * 
 * @author Julien Valverdé
 */
public class Group extends Entity {
	// Propriétés du type d'entité
	public static final String TABLE = "groups";
	public static final String SINGLE = "group";
	public static final EntityFields FIELDS = new EntityFields(Entity.FIELDS);
	static {
		FIELDS.addField("name", "String");
	}
	
	// Modèle du type d'entité
	public static EntityModel model;
	static {
		try {
			model = new EntityModel("model.Group");
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException
				| SecurityException e) {
			e.printStackTrace();
		}
	}
	
	
	/*
	 * Constructeur
	 */
	public Group(ResultSet res) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException, ClassNotFoundException, SQLException {
		super(model, res);
	}
	
	/*
	 * toString
	 * Affiche l'instance sous forme textuelle
	 */
	public String toString() {
		return "(Group) "+get("id");
	}
}