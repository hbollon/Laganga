package model.entities;

import java.util.Calendar;
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
		fields.add("begin", "DateTime");
		fields.add("end", "DateTime");
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
	public Calendar getBegin() {
		return (Calendar) getFieldsValues().get("begin");
	}
	public Calendar getEnd() {
		return (Calendar) getFieldsValues().get("end");
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
	public void setBegin(Calendar begin) {
		getFieldsValues().put("begin", begin);
	}
	public void setEnd(Calendar end) {
		getFieldsValues().put("end", end);
	}
	public void setLocation(Location location) {
		getFieldsValues().put("location", location);
	}
}