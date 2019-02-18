package model;

import java.sql.*;

public abstract class Entity {
	public static final EntityFields FIELDS = new EntityFields();
	static {
		FIELDS.addField("id", "int");
	}
	
	
	// Propriétés du modèle
	private EntityModel model;
	private String table;
	private String prefix;
	private EntityFields fields;
	
	public String getTable() {
		return table;
	}
	public String getPrefix() {
		return prefix;
	}
	public EntityFields getFields() {
		return fields;
	}
	
	
	/*
	 * Constructeur
	 * Sauvegarde les propriétés du modèle à partir de l'usine
	 */
	public Entity(EntityModel model) {
		this.model = model;
		
		table = model.getTable();
		prefix = model.getPrefix();
		fields = new EntityFields(model.getFields());
	}
	
	public Object get(String name) {
		return fields.get(name);
	}
	public void put(String name, Object value) {
		fields.put(name, value);
	}
	
	/*
	 * save
	 * Sauvegarde les champs de la table issus du ResultSet
	 */
	public void save(ResultSet res) {
	}
	
	/*
	 * update
	 */
	protected void update() {
	}
	
	/*
	 * delete
	 */
	public void delete() {
	}
}