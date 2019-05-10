package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;

public class FieldsList {
	// Noms et types
	private ArrayList<String> names = new ArrayList<String>();
	private HashMap<String, String> types = new HashMap<String, String>();
	
	// Récupération d'un champ
	public String get(int index) {
		return names.get(index);
	}
	
	// Récupération du type d'un champ
	public String getType(String name) {
		return types.get(name);
	}
	public String getType(int index) {
		return getType(get(index));
	}
	
	// Ajout d'un champ
	public void add(String name, String type) {
		names.add(name);
		types.put(name, type);
	}
	
	// Ajout de multiple champs
	public void addAll(FieldsList list) {
		for (int i = 0; i < list.size(); i++)
			add(list.get(i), list.getType(i));
	}
	
	// Suppression d'un champ
	public void remove(String name) {
		names.remove(name);
		types.remove(name);
	}
	public void remove(int index) {
		remove(get(index));
	}
	
	// Taille de la liste
	public int size() {
		return names.size();
	}
	
	// Binder les valeurs d'un array à une requête préparée
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
				case "Date":
					st.setDate(i + 1, (Date) value);
					break;
				default:
					throw new Exception("Le type enregistré pour le champ "+get(i)+" n'existe pas.");
			}
		}
	}
}