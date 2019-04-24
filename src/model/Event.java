package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;

public class Event extends Entity {
	// Propriétés du type d'entité
	public static final String TABLE = "events";
	public static final String SINGLE = "event";
	
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
	private Date date;
	private Time begin;
	private Time end;
	//private Agenda agenda;
	//private Location location;
	
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
	public Date getDate() {
		return date;
	}
	public Time getBegin() {
		return begin;
	}
	public Time getEnd() {
		return end;
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
	public void setDate(Date date) {
		this.date = date;
	}
	public void setBegin(Time begin) {
		this.begin = begin;
	}
	public void setEnd(Time end) {
		this.end = end;
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
		date = res.getDate(getPrefix()+"date");
		begin = res.getTime(getPrefix()+"begin");
		end = res.getTime(getPrefix()+"end");
	}
	
	protected int bindUpdateFields(PreparedStatement st) throws Exception {
		int i = super.bindUpdateFields(st);
		
		st.setString(i, name); i++;
		st.setString(i, type); i++;
		st.setInt(i, priority); i++;
		st.setDate(i, date); i++;
		st.setTime(i, begin); i++;
		st.setTime(i, end); i++;
		
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
		fields += "`"+getPrefix()+"date` = ?, ";
		fields += "`"+getPrefix()+"begin` = ?, ";
		fields += "`"+getPrefix()+"end` = ?";
		
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
		str += "\t- date: "+getDate()+"\n";
		str += "\t- begin: "+getBegin()+"\n";
		str += "\t- end: "+getEnd()+"\n";
		
		return str;
	}
}