package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Un Location représente un lieu dans lequel un évènement peut avoir lieu.
 * 
 * @author Julien Valverdé
 */
public class Location extends Entity {
	// Objet usine
	public static EntityFactory factory;
	static {
		try {
			// Champs
			ArrayList<String> fields = new ArrayList<String>();
			
			fields.add("name");
			fields.add("description");
			
			// Création de l'objet
			factory = new EntityFactory(
					"model.Location",
					"locations",
					"location",
					Entity.factory,
					fields);
		} catch (Exception e) {}
	}
	
	// Attributs de l'entité
	private String name;
	private String description;
	
	// Getteurs des attributs
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	
	// Setteurs des attributs
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	// Constructeur
	public Location(EntityFactory factory) throws Exception {
		super(factory);
	}
	
	public void save(ResultSet res) throws Exception {
		super.save(res);
		
		name = res.getString(getPrefix()+"name");
		description = res.getString(getPrefix()+"description");
	}
	
	protected int bindUpdateFields(PreparedStatement st) throws Exception {
		int i = super.bindUpdateFields(st);
		
		st.setString(i, name); i++;
		st.setString(i, description); i++;
		
		return i;
	}
	
	/**
	 * Renvoie la liste des champs à mettre à jour lors d'une opération update.
	 */
	/*
	public String getUpdateFields() {
		String fields = super.getUpdateFields();
		
		fields += "`"+getPrefix()+"name` = ? ";
		fields += "`"+getPrefix()+"description` = ?";
		
		return fields;
	}
	*/
	
	/**
	 * Retourne une représentation textuelle de l'évènement.
	 */
	public String toString() {
		String str = "Location no. "+getID()+":\n";
		str += "\t- name: "+getName()+"\n";
		str += "\t- description: "+getDescription()+"\n";
		
		return str;
	}
}