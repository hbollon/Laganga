package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;

public class Event extends Entity {
	// Objet usine
	public static EntityFactory factory;
	static {
		try {
			// Champs
			ArrayList<String> fields = new ArrayList<String>();
			
			fields.add("name");
			fields.add("type");
			fields.add("priority");
			fields.add("begin");
			fields.add("end");
			fields.add("location");
			
			// Entités jointes
			ArrayList<EntityFactory> joinedEntities = new ArrayList<EntityFactory>();
			ArrayList<String> joinedFields = new ArrayList<String>();
			
			joinedEntities.add(Location.factory); joinedFields.add("location");
			
			// Création de l'objet
			factory = new EntityFactory(
					"model.Event",
					"events",
					"event",
					Entity.factory,
					fields,
					joinedEntities,
					joinedFields);
		} catch (Exception e) {}
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
		return begin;
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
	
	public static int bind(PreparedStatement st) throws Exception {
		//int i = super.bindUpdateFields(st);
		
		st.setString(i, name); i++;
		st.setString(i, type); i++;
		st.setInt(i, priority); i++;
		st.setDate(i, begin); i++;
		st.setDate(i, end); i++;
		st.setInt(i, location.getID()); i++;
		
		return i;
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