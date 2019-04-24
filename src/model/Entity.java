package model;

import java.sql.ResultSet;
import java.util.HashMap;

public abstract class Entity {
	// Propriétés de l'entité
	public static final String TABLE = "entities";
	public static final String SINGLE = "entity";
	
	// Champs de l'entité
	public static EntityFields fields;
	static {
		String[] names = {"id"};
		String[] types = {"int"};
		
		fields = new EntityFields(names, types);
	}
	
	private EntityFactory factory; // Objet usine
	private HashMap<String, Object> data = new HashMap<String, Object>(); // Valeur des champs
	
	/*
	 * Constructeur
	 * Sauvegarde les propriétés du modèle à partir de l'usine
	 */
	public Entity(EntityFactory factory, ResultSet res) throws Exception {
		this.factory = factory;
		
		saveDataFromResultSet(res);
	}
	
	private void saveDataFromResultSet(ResultSet res) throws Exception {
		EntityFields fields = factory.getFields();
		
		for (int i = 0; i < fields.size(); i++) {
			String name = fields.get(i);
			String prefixedName = factory.getPrefix()+name;
			String type = fields.getType(i);
			
			switch (type) {
				// Types SQL
				case "int":    put(name, res.getInt(prefixedName)); break;
				case "float":  put(name, res.getFloat(prefixedName)); break;
				case "String": put(name, res.getString(prefixedName)); break;
				case "Time":   put(name, res.getTime(prefixedName)); break;
				case "Date":   put(name, res.getDate(prefixedName)); break;
				
				// Types du modèle
				//default:       ((EntityFactory) Class.forName(type).getDeclaredField("factory").get(null)).getEntityFromResultSet(res);
			}
		}
	}
	
	public Object get(String key) {
		return data.get(key);
	}
	public void put(String key, Object value) {
		data.put(key, value);
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