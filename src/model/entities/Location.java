package model.entities;

import model.FieldsList;

/**
 * Un Location représente un lieu dans lequel un évènement peut avoir lieu.
 * 
 * @author Julien Valverdé
 */
public class Location extends Entity {
	/*
	 * Objet usine
	 */
	public static EntityFactory factory = new EntityFactory();
	static {
		factory.setClassName("model.entities.Location");
		factory.setTable("locations");
		factory.setSingle("location");
		factory.setParent(Entity.factory);
		
		// Champs
		FieldsList fields = new FieldsList();
		fields.add("name", "String");
		fields.add("description", "String");
		
		factory.setFieldsList(fields);
	}
	
	// Getteurs des attributs
	public String getName() {
		return (String) getFieldsValues().get("name");
	}
	public String getDescription() {
		return (String) getFieldsValues().get("description");
	}
	
	// Setteurs des attributs
	public void setName(String name) {
		getFieldsValues().put("name", name);
	}
	public void setDescription(String description) {
		getFieldsValues().put("description", description);
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