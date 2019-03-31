package model;

import java.sql.ResultSet;

public class Event extends Entity {
	// Propriétés du type d'entité
	public static final String TABLE = "events";
	public static final String SINGLE = "event";
	
	// Liste et type des champs
	public static EntityFields fields;
	static {
		String[] names = {"name", "type", "date", "begin", "end", "agenda", "location"};
		String[] types = {"String", "String", "Date", "Time", "Time", "Agenda", "Location"};
		
		fields = new EntityFields(Entity.fields, names, types);
	}
	
	// Usine
	public static EntityFactory factory;
	static {
		try {
			factory = new EntityFactory("model.Event");
		} catch (Exception e) {}
	}
	
	public Event(ResultSet res) throws Exception {
		super(factory, res);
	}
	
	/*
	 * toString
	 * Affiche l'instance sous forme textuelle
	 */
	public String toString() {
		return get("id")+"/"+get("name")+"/"+get("user");
	}
}