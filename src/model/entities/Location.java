package model.entities;

import java.sql.ResultSet;

import model.FieldsList;

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
			FieldsList fields = new FieldsList();
			fields.add("name", "String");
			fields.add("description", "String");
			
			// Création de l'objet
			factory = new EntityFactory(
					"model.entities.Location",
					"locations",
					"location",
					fields);
		} catch (Exception e) {
			System.out.println("Initialisation de Location impossible : "+e);
		}
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