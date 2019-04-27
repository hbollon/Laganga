package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;

public class Event extends Entity {
	// Propriétés du type d'entité
	public static final String TABLE = "events";
	public static final String SINGLE = "event";
	public static final EntityFactory[] JOINED_ENTITIES = {Location.factory};
	public static final String[] JOINED_IDS = {"location"};
	
	// Objet usine
	public static EntityFactory factory;
	static {
		try {
			factory = new EntityFactory("model.Event");
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
	
	protected int bindUpdateFields(PreparedStatement st) throws Exception {
		int i = super.bindUpdateFields(st);
		
		st.setString(i, name); i++;
		st.setString(i, type); i++;
		st.setInt(i, priority); i++;
		st.setDate(i, begin); i++;
		st.setDate(i, end); i++;
		st.setInt(i, location.getID()); i++;
		
		return i;
	}
	
	/**
	 * Renvoie la liste des champs à mettre à jour lors d'une opération update.
	 */
	public String getUpdateFields() {
		String fields = super.getUpdateFields();
		
		fields += "`"+getPrefix()+"name` = ?, ";
		fields += "`"+getPrefix()+"type` = ?, ";
		fields += "`"+getPrefix()+"priority` = ?, ";
		fields += "`"+getPrefix()+"begin` = ?, ";
		fields += "`"+getPrefix()+"end` = ?, ";
		fields += "`"+getPrefix()+"location` = ?";
		
		return fields;
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