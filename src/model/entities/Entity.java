package model.entities;

import java.sql.ResultSet;
import java.util.ArrayList;

public abstract class Entity {
	// Objet usine
	private EntityFactory factoryObject;
	
	// Propriétés du type d'entité
	private String table;
	private String single;
	private String prefix;
	
	public EntityFactory getFactoryObject() {
		return factoryObject;
	}
	public String getTable() {
		return table;
	}
	public String getSingle() {
		return single;
	}
	public String getPrefix() {
		return prefix;
	}
	
	// Attributs de l'entité
	private int id;
	
	public int getID() {
		return id;
	}
	
	/*
	 * Constructeur
	 * Sauvegarde les propriétés du modèle à partir de l'usine
	 */
	public Entity(EntityFactory factoryObject) throws Exception {
		this.factoryObject = factoryObject;
		
		// Stockage des propriétés de l'entité
		table = factoryObject.getTable();
		single = factoryObject.getSingle();
		prefix = factoryObject.getPrefix();
	}
	
	public void save(ResultSet res) throws Exception {
		id = res.getInt(getPrefix()+"id");
	}
	
	// Création de la liste des valeurs des attributs
	public ArrayList<Object> getFieldsValues() {
		return new ArrayList<Object>();
	}
	
	/*
	 * Opérations sur la base de données
	 */
	
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
	
	/**
	 * Supprime l'entrée de la base de données.
	 */
	public void delete() {
	}
	
	
	/*
	 * Listes jointes
	 */
	
	static protected ArrayList<Entity> toAdd(ArrayList<Entity> oldList, ArrayList<Entity> updatedList) {
		ArrayList<Entity> toAddList = new ArrayList<Entity>();
		
		for (int i = 0; i < updatedList.size(); i++) {
				Entity ent = updatedList.get(i);
				
				if (!oldList.contains(ent))
					toAddList.add(ent);
		}
		
		return toAddList;
	}
	
	static protected ArrayList<Entity> toRemove(ArrayList<Entity> oldList, ArrayList<Entity> updatedList) {
		ArrayList<Entity> toRemoveList = new ArrayList<Entity>();
		
		for (int i = 0; i < oldList.size(); i++) {
				Entity ent = oldList.get(i);
				
				if (!updatedList.contains(ent))
					toRemoveList.add(ent);
		}
		
		return toRemoveList;
	}
}