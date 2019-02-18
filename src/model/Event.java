package model;

import java.sql.*;

public class Event extends Entity {
	// Propriétés du modèle
	public static final String TABLE = "events";
	public static final String SINGLE = "event";
	public static final EntityFields FIELDS = new EntityFields(Entity.FIELDS);
	static {
		FIELDS.addField("name", "String");
		FIELDS.addField("user", "User");
	}
	public static final EntityModel[] RELATIONS = {EntityModel.users};
	
	/*
	 * Constructeur
	 */
	public Event(EntityModel model) {
		super(model);
	}
	
	/*
	 * toString
	 * Affiche l'instance sous forme textuelle
	 */
	public String toString() {
		return get("id")+"/"+get("name")+"/"+get("user");
	}
}