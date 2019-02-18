package model;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public abstract class Entity {
	public static final EntityFields FIELDS = new EntityFields();
	static {
		FIELDS.addField("id", "int");
	}
	
	
	// Propriétés du modèle
	private EntityModel model;
	private EntityFields fields;
	
	public EntityModel getModel() {
		return model;
	}
	public EntityFields getFields() {
		return fields;
	}
	
	
	/*
	 * Constructeur
	 * Sauvegarde les propriétés du modèle à partir de l'usine
	 */
	public Entity(EntityModel model, ResultSet res) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, SQLException {
		this.model = model;
		
		fields = new EntityFields(model.getFields());
		fields.save(model, res);
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