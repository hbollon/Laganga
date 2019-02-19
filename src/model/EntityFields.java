package model;

import java.util.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

/**
 * Un EntityFields est une structure associée à une entité contenant :
 * - Le nom des champs du dit modèle.
 * - Les données de la ligne courante.
 * 
 * @author Julien Valverdé
 */
public class EntityFields {
	private ArrayList<String> names = new ArrayList<String>();
	private Map<String, String> types = new HashMap<String, String>();
	private Map<String, Object> data = new HashMap<String, Object>();
	
	// Getteurs
	public ArrayList<String> getNames() {
		return names;
	}
	public Map<String, String> getTypes() {
		return types;
	}
	
	public EntityFields() {
	}
	public EntityFields(EntityFields original) {
		addFields(original.getNames(), original.getTypes());
	}
	
	public void addField(String name, String type) {
		names.add(name);
		types.put(name, type);
	}
	public void addFields(ArrayList<String> newNames, Map<String, String> newTypes) {
		for (int i = 0; i < newNames.size(); i++)
			addField(newNames.get(i), newTypes.get(newNames.get(i)));
	}
	
	/**
	 * Sauvegarde les données du ResultSet dans les champs qui leurs sont associées.
	 * 
	 * @param res Le ResultSet.
	 * 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void save(EntityModel model, ResultSet res) throws SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException, ClassNotFoundException {
		for (int i = 0; i < names.size(); i++) {
			String name = names.get(i);
			String prefixedName = model.getPrefix()+name;
			String type = getFieldType(name);
			
			switch (type) {
				// Types SQL
				case "int":    data.put(name, res.getInt(prefixedName)); break;
				case "float":  data.put(name, res.getFloat(prefixedName)); break;
				case "String": data.put(name, res.getString(prefixedName)); break;
				case "Time":   data.put(name, res.getTime(prefixedName)); break;
				case "Date":   data.put(name, res.getDate(prefixedName)); break;
				
				// Types du modèle
				default:       ((EntityModel) Class.forName(type).getDeclaredField("model").get(null)).getEntityFromResultSet(res);
			}
		}
	}
	
	
	public String getFieldType(String name) {
		return types.get(name);
	}
	public Object get(String name) {
		return data.get(name);
	}
	public void put(String name, Object value) {
		data.put(name, value);
	}
}