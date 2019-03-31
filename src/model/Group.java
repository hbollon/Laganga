package model;

import java.sql.ResultSet;

/**
 * 
 * @author Julien Valverdé
 */
public class Group extends Entity {
	// Propriétés du type d'entité
	public static final String TABLE = "groups";
	public static final String SINGLE = "group";
	
	// Liste et type des champs
	public static EntityFields fields;
	static {
		String[] names = {"name"};
		String[] types = {"String"};
		
		fields = new EntityFields(Entity.fields, names, types);
	}
	
	// Usine
	public static EntityFactory factory;
	static {
		try {
			factory = new EntityFactory("model.Group");
		} catch (Exception e) {}
	}
	
	public Group(ResultSet res) throws Exception {
		super(factory, res);
	}
	
	/*
	 * toString
	 * Affiche l'instance sous forme textuelle
	 */
	public String toString() {
		return "(Group) "+get("id");
	}
}