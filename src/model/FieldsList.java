package model;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;

import model.entities.Entity;

public class FieldsList {
	// Noms et types
	private ArrayList<String> names = new ArrayList<String>();
	private HashMap<String, String> types = new HashMap<String, String>();
	
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
	
	// Binder les valeurs des champs à une requête préparée
	public void bind(PreparedStatement st, ArrayList<Object> values) throws Exception {
		// Si la taille de l'ArrayList des valeurs à binder est incorrecte
		if (size() != values.size())
			throw new Exception("La liste des valeurs doit faire la même taille que la liste des champs.");
		
		for (int i = 0; i < size(); i++) {
			Object value = values.get(i);
			
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
				default:
					if (value instanceof Entity)
						st.setInt(i + 1, ((Entity) value).getID());
					else
						throw new Exception("Le type enregistré pour le champ "+getName(i)+" n'existe pas.");
			}
		}
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