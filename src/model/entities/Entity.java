package model.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Database;
import model.FieldsList;

public abstract class Entity {
	/*
	 * Objet usine
	 */
	public static EntityFactory factory = new EntityFactory();
	static {
		factory.setClassName("model.entities.Entity");
		
		// Champs
		FieldsList fields = new FieldsList();
		fields.add("id", "int");
		
		factory.setFieldsList(fields);
	}
	
	// Objet usine de l'instance
	private EntityFactory factoryObject;
	
	// Valeurs des champs
	private Map<String, Object> fieldsValues = new HashMap<String, Object>();
	
	// Getteurs
	public EntityFactory getFactory() {
		return factoryObject;
	}
	protected Map<String, Object> getFieldsValues() {
		return fieldsValues;
	}
	public int getID() {
		return (int) fieldsValues.get("id");
	}
	
	// Setteurs
	public void setFactory(EntityFactory factoryObject) {
		this.factoryObject = factoryObject;
	}
	
	// Sauvegarder les valeurs depuis un ResultSet
	public void save(ResultSet res) throws SQLException, Exception {
		fieldsValues = getFactory().getFieldsList().save(res, getFactory());
	}
	
	
	/*
	 * Opérations sur la base de données
	 */
	
	// Update
	public void update() throws SQLException, Exception {
		FieldsList fields = getFactory().getFieldsList().clone();
		
		String query = getFactory().getUpdateQuery(fields);
		
		fields.add("id", "int"); // On binde un champ ID à la fin pour la clause WHERE
		Database.database.prepareUpdateAndExecute(query, fields, fieldsValues);
	}
	
	// Delete
	private static FieldsList deleteQueryFields = new FieldsList();
	static {
		deleteQueryFields.add("id", "int");
	}
	
	public void delete() throws SQLException, Exception {
		Database.database.prepareUpdateAndExecute(getFactory().getDeleteQuery(), deleteQueryFields, fieldsValues);
	}
	
	/**
	 * Met à jour les attributs de l'objet depuis la base de données.
	 */
	public void refresh() {
		
	}
	
	/**
	 * Met à jour la base de données depuis les attributs de l'objet.
	 */
	/*
	public void update() throws Exception {
		PreparedStatement st = Database.database.getPreparedQuery(factory.getUpdateQuery(this));
		
		int i = factory.bindUpdateFields(this, st);
		
		Database.database.executePreparedQuery(st);
	}
	
	/*
	 * Listes jointes
	 */
	
	static protected List<Entity> toAdd(List<Entity> oldList, List<Entity> updatedList) {
		List<Entity> toAddList = new ArrayList<Entity>();
		
		for (int i = 0; i < updatedList.size(); i++) {
				Entity ent = updatedList.get(i);
				
				if (!oldList.contains(ent))
					toAddList.add(ent);
		}
		
		return toAddList;
	}
	
	static protected List<Entity> toRemove(List<Entity> oldList, List<Entity> updatedList) {
		List<Entity> toRemoveList = new ArrayList<Entity>();
		
		for (int i = 0; i < oldList.size(); i++) {
				Entity ent = oldList.get(i);
				
				if (!updatedList.contains(ent))
					toRemoveList.add(ent);
		}
		
		return toRemoveList;
	}
	
	/*
	 * Représentation sous forme de chaine de caractères
	 */
	public String toString() {
		return getFactory().getClassName()+": "+getFactory().getFieldsList().valuesToString(fieldsValues);
	}
}