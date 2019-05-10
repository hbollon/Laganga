package model.entities;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.FieldsList;

public class Event extends Entity {
	// Objet usine
	public static EntityFactory factory;
	static {
		try {
			// Champs
			FieldsList fields = new FieldsList();
			fields.add("name", "String");
			fields.add("type", "String");
			fields.add("priority", "int");
			fields.add("begin", "String");
			fields.add("end", "String");
			fields.add("location", "Location");
			
			// Entités jointes
			ArrayList<EntityFactory> joinedEntities = new ArrayList<EntityFactory>();
			ArrayList<String> joinedFields = new ArrayList<String>();
			
			joinedEntities.add(Location.factory); joinedFields.add("location");
			
			// Création de l'objet
			factory = new EntityFactory(
					"model.entities.Event",
					"events",
					"event",
					fields,
					joinedEntities,
					joinedFields);
		} catch (Exception e) {
			System.out.println("Initialisation de Event impossible : "+e);
		}
	}
	
	// Attributs de l'entité
	private String name;
	private String type;
	private int priority;
	private Date begin;
	private Date end;
	private Location location;
	
	// Getteurs des attributs
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	public int getPriority() {
		return priority;
	}
	public Date getBegin() {
		return begin;
	}
	public Date getEnd() {
		return end;
	}
	public Location getLocation() {
		return location;
	}
	
	// Setteurs des attributs
	public void setName(String name) {
		this.name = name;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public void setBegin(Date begin) {
		this.begin = begin;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	
	// Constructeur
	public Event(EntityFactory factory) throws Exception {
		super(factory);
	}
	
	public void save(ResultSet res) throws Exception {
		super.save(res);
		
		name = res.getString(getPrefix()+"name");
		type = res.getString(getPrefix()+"type");
		priority = res.getInt(getPrefix()+"priority");
		begin = res.getDate(getPrefix()+"begin");
		end = res.getDate(getPrefix()+"end");
		location = (Location) Location.factory.getFromResultSet(res);
	}
	
	/**
	 * Retourne une représentation textuelle de l'évènement.
	 */
	public String toString() {
		String str = "Event no. "+getID()+":\n";
		str += "\t- name: "+getName()+"\n";
		str += "\t- type: "+getType()+"\n";
		str += "\t- priority: "+getPriority()+"\n";
		str += "\t- begin: "+getBegin()+"\n";
		str += "\t- end: "+getEnd()+"\n";
		str += "\t- location: "+getLocation()+"\n";
		
		return str;
	}
}