package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;

public class FieldsList extends ArrayList<String> {
	private static final long serialVersionUID = 1957378835428349704L;
	
	// Types
	private HashMap<String, String> types = new HashMap<String, String>();
	
	// Ajout d'un champ
	public void add(String name, String type) {
		super.add(name);
		types.put(name, type);
	}
	
	// Récupération du type d'un champ
	public String getType(String name) {
		return types.get(name);
	}
	public String getType(int index) {
		return getType(get(index));
	}
	
	// Binder les valeurs d'un array à une requête préparée
	public void bind(PreparedStatement st, ArrayList<Object> values) throws Exception {
		// Si la taille de l'ArrayList des valeurs à binder est incorrecte
		if (size() != values.size())
			throw new Exception("L'ArrayList des valeurs doit faire la même taille que le FieldsList.");
		
		for (int i = 0; i < size(); i++) {
			Object value = values.get(i);
			
			switch (getType(i)) {
				case "int":
					st.setInt(i + 1, (int) value);
					break;
				case "float":
					st.setFloat(i + 1, (float) value);
					break;
				case "String":
					st.setString(i + 1, (String) value);
					break;
				case "Date":
					st.setDate(i + 1, (Date) value);
					break;
			}
		}
	}
}