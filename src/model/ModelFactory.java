/*
 * Fichier : ModelFactory.java
 * Auteur : Julien Valverdé
 */
package model;

import java.util.*;
import java.sql.*;


/*
 * ModelFactory
 * Permet de créer des instances du modèle.
 */
public class ModelFactory {
	// Usines
	public static ModelFactory users;
	public static ModelFactory events;
	
	static {
		try {
			users = new ModelFactory("model.User");
			events = new ModelFactory("model.Event");
		}
		catch (Exception e) {}
	}
	
	
	// Propriétés du modèle
	private Class<?> modelClass;
	private Map<Integer, Model> objects;
	private String table;
	private String single;
	private String prefix;
	private ModelFactory[] relations;
	
	public Class<?> getModelClass() {
		return modelClass;
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
	public ModelFactory[] getRelations() {
		return relations;
	}
	
	
	// Constructeur
	public ModelFactory(String className) throws Exception {
		modelClass = Class.forName(className);
		
		objects = new HashMap<Integer, Model>();		
		table = (String) modelClass.getDeclaredField("TABLE").get(null);
		single = (String) modelClass.getDeclaredField("SINGLE").get(null);
		prefix = single+"_";
		
		try {
			relations = (ModelFactory[]) modelClass.getDeclaredField("RELATIONS").get(null);
		}
		catch (Exception e) {}
	}
	
	// Créateurs d'instances du modèle
	private Model newObject(ResultSet res) throws Exception {
		Model object = (Model) modelClass.getConstructor(ModelFactory.class).newInstance(this);
		object.save(res);
		
		objects.put(object.getID(), object);
		
		return object;
	}
	
	/*
	 * getSQLQuery
	 * Génère la requête SQL
	 */
	private String getSelectQuery(String whereClause, String additionalClauses) {
		String query = "SELECT * FROM "+getTable();
		
		if (relations != null && relations.length > 0) {
			for (int i = 0; i < relations.length; i++) {
				query += "\nINNER JOIN "+relations[i].getTable()+" ON "+relations[i].getPrefix()+"id = "+getPrefix()+relations[i].getSingle();
				
				if (i == 0 && whereClause != null)
					query += " AND "+whereClause;
			}
		}
		else if (whereClause != null)
			query += "\nWHERE "+whereClause;
		
		if (additionalClauses == null)
			additionalClauses = "ORDER BY "+getPrefix()+"id ASC";
		
		return query+"\n"+additionalClauses;
	}
	
	/*
	 * getInstanceFromID
	 * Renvoie l'instance du modèle d'ID id. Si une telle instance n'existe pas, elle est crée.
	 */
	public Model getObjectFromResultSet(ResultSet res) throws Exception {
		Model object = objects.get(res.getInt(getPrefix()+"id"));
		if (object == null)
			object = newObject(res);
		
		return object;
	}
	
	/*
	 * get
	 * 	clause: Clause WHERE de la requête
	 * 
	 * Retourne les instances du modèle vérifiant la requête passée.
	 */
	public ArrayList<Model> get(String whereClause, String additionalClauses) throws Exception {
		ArrayList<Model> list = new ArrayList<Model>();
		ResultSet res = Database.database.execute(getSelectQuery(whereClause, additionalClauses));
		
		while (res.next())
			list.add(getObjectFromResultSet(res));
		
		return list;
	}
	
	/*
	 * getAll
	 * Retourne une liste de tous les objets du modèle.
	 */
	public ArrayList<Model> getAll() throws Exception {
		return get(null, null);
	}
	
	/*
	 * getByID
	 * 	id : ID de la ligne du modèle à retourner
	 * 
	 * Retourne l'objet du modèle correspondant à l'ID indiqué.
	 */
	public Model getByID(int id) throws Exception {
		ArrayList<Model> list = get(getPrefix()+"id = "+id, "");
		
		if (list.size() == 0)
			return null;

		return list.get(0);
	}
}