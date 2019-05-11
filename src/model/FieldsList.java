package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.entities.Entity;
import model.entities.EntityFactory;

public class FieldsList {
	// Noms et types
	private List<String> names = new ArrayList<String>();
	private Map<String, String> types = new HashMap<String, String>();
	
	// Récupération d'un champ
	public String getName(int index) {
		return names.get(index);
	}
	
	// Récupération du type d'un champ
	public String getType(String name) {
		return types.get(name);
	}
	public String getType(int index) {
		return getType(getName(index));
	}
	
	// Ajout d'un champ
	public void add(String name, String type) {
		names.add(name);
		types.put(name, type);
	}
	
	// Ajout de multiple champs
	public void addAll(FieldsList list) {
		for (int i = 0; i < list.size(); i++)
			add(list.getName(i), list.getType(i));
	}
	
	// Suppression d'un champ
	public void remove(String name) {
		names.remove(name);
		types.remove(name);
	}
	public void remove(int index) {
		remove(getName(index));
	}
	
	// Taille de la liste
	public int size() {
		return names.size();
	}
	
	// Cloner la liste
	public FieldsList clone() {
		FieldsList clone = new FieldsList();
		clone.addAll(this);
		
		return clone;
	}
	
	// Binder les valeurs des champs à une requête préparée
	public void bind(PreparedStatement st, Map<String, Object> values) throws Exception {
		for (int i = 0; i < size(); i++) {
			Object value = values.get(getName(i));
			
			switch (getType(i)) {
				case "int":
					st.setInt(i + 1, (int) value);
					break;
				case "float":
					st.setFloat(i + 1, (float) value);
					break;
				case "boolean":
					st.setBoolean(i + 1, (boolean) value);
					break;
				case "String":
					st.setString(i + 1, (String) value);
					break;
				case "Calendar":
					st.setString(i + 1, (String) value);
					break;
				default:
					if (value instanceof Entity)
						st.setInt(i + 1, ((Entity) value).getID());
					else
						throw new Exception("Le type enregistré pour le champ "+getName(i)+" n'existe pas.");
			}
		}
	}
	
	// Sauvegarder les valeurs des champs à partir du résultat d'une requête
	public Map<String, Object> save(ResultSet res, EntityFactory factory) throws SQLException, Exception {
		Map<String, Object> values = new HashMap<String, Object>();
		
		for (int i = 0; i < size(); i++) {
			String name = getName(i);
			String field = factory.getTable()+"."+factory.getPrefix()+name; // Nom complet du champ à récupérer
			
			switch (getType(i)) {
				case "int":
					values.put(name, res.getInt(field));
					break;
				case "float":
					values.put(name, res.getFloat(field));
					break;
				case "boolean":
					values.put(name, res.getBoolean(field));
					break;
				case "String":
					values.put(name, res.getString(field));
					break;
				case "Calendar":
					values.put(name, res.getString(field));
					break;
				default:
					EntityFactory joined = factory.getJoinedEntities().get(name);
					
					// Si le champ courant comprend une jointure
					if (joined != null)
						values.put(name, joined.getFromResultSet(res));
			}
		}
		
		return values;
	}
	
	// Représentation sous forme de String
	public String toString() {
		return names.toString();
	}
	
	// Formatage du nom des champs pour utilisation dans une requête
	public String toQueryString(String prefix) {
		String str = "";
		for (int i = 0; i < size(); i++)
			str += "`"+prefix+getName(i)+"`, ";
		
		return str.substring(0, str.length() - 2);
	}
}