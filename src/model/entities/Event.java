package model.entities;

import java.util.HashMap;
import java.util.Map;

import model.FieldsList;

public class Event extends Entity {
	/*
	 * Objet usine
	 */
	public static EntityFactory factory = new EntityFactory();
	static {
		factory.setClassName("model.entities.Event");
		factory.setTable("events");
		factory.setSingle("event");
		factory.setParent(Entity.factory);
		
		// Champs
		FieldsList fields = new FieldsList();
		fields.add("name", "String");
		fields.add("type", "String");
		fields.add("priority", "int");
		fields.add("begin", "String");
		fields.add("end", "String");
		fields.add("location", "Location");
		
		factory.setFieldsList(fields);
		
		// Entités jointes
		Map<String, EntityFactory> joinedEntities = new HashMap<String, EntityFactory>();
		joinedEntities.put("location", Location.factory);
		
		factory.setJoinedEntities(joinedEntities);
	}
	
	// Getteurs des attributs
	public String getName() {
		return (String) getFieldsValues().get("name");
	}
	public String getType() {
		return (String) getFieldsValues().get("type");
	}
	public int getPriority() {
		return (int) getFieldsValues().get("priority");
	}
	public String getBegin() {
		return (String) getFieldsValues().get("begin");
	}
	public String getEnd() {
		return (String) getFieldsValues().get("end");
	}
	public Location getLocation() {
		return (Location) getFieldsValues().get("location");
	}
	
	// Setteurs des attributs
	public void setName(String name) {
		getFieldsValues().put("name", name);
	}
	public void setType(String type) {
		getFieldsValues().put("type", type);
	}
	public void setPriority(int priority) {
		getFieldsValues().put("priority", priority);
	}
	public void setBegin(String begin) {
		getFieldsValues().put("begin", begin);
	}
	public void setEnd(String end) {
		getFieldsValues().put("end", end);
	}
	public void setLocation(Location location) {
		getFieldsValues().put("location", location);
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