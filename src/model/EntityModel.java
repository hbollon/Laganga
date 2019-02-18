package model;

import java.util.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

/**
 * Un ModelFactory est associé à une table de la base de données et à une classe du modèle (ex: users/User).
 * Il permet d'effectuer plusieurs opérations sur le modèle, comme récupérer un certain nombre de lignes depuis la
 * base de données, instancier la classe qui leur est associée et créer de nouvelles entrées.
 * 
 * @author Julien Valverdé
 */
public class EntityModel {
	// Factories
	public static EntityModel users;
	public static EntityModel events;
	
	static {
		try {
			users = new EntityModel("model.User");
			events = new EntityModel("model.Event");
		}
		catch (Exception e) {}
	}
	
	
	// Propriétés du modèle
	private Class<?> entityClass;
	private Map<Integer, Entity> entities = new HashMap<Integer, Entity>();
	
	private String table;
	private String single;
	private String prefix;
	private EntityFields fields;
	private EntityModel[] relations;
	
	public Class<?> getModelClass() {
		return entityClass;
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
	public EntityFields getFields() {
		return fields;
	}
	public EntityModel[] getRelations() {
		return relations;
	}
	
	
	// Constructeur
	public EntityModel(String className) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		entityClass = Class.forName(className);
		
		table = (String) entityClass.getDeclaredField("TABLE").get(null);
		single = (String) entityClass.getDeclaredField("SINGLE").get(null);
		prefix = single+"_";
		
		fields = (EntityFields) entityClass.getDeclaredField("FIELDS").get(null);
		
		try {
			relations = (EntityModel[]) entityClass.getDeclaredField("RELATIONS").get(null);
		}
		catch (Exception e) {}
	}
	
	// Créateurs d'instances du modèle
	private Entity newEntity(ResultSet res) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Entity entity = (Entity) entityClass.getConstructor(EntityModel.class, ResultSet.class).newInstance(this, res);
		
		entities.put((int) entity.get("id"), entity);
		
		return entity;
	}
	
	/**
	 * Assemble une requête SQL pour récupérer les lignes du modèle.
	 * 
	 * @param whereClause Clause WHERE de la requête (null pour ne pas la préciser).
	 * @param additionalClauses Clauses additionnelles (null pour ne pas en préciser).
	 * @return Un String contenant la requête SQL.
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
	public Entity getEntityFromResultSet(ResultSet res) throws SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Entity entity = entities.get(res.getInt(getPrefix()+"id"));
		if (entity == null)
			entity = newEntity(res);
		
		return entity;
	}
	
	/*
	 * get
	 * 	clause: Clause WHERE de la requête
	 * 
	 * Retourne les instances du modèle vérifiant la requête passée.
	 */
	public ArrayList<Entity> get(String whereClause, String additionalClauses) throws SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		ArrayList<Entity> list = new ArrayList<Entity>();
		ResultSet res = Database.database.execute(getSelectQuery(whereClause, additionalClauses));
		
		while (res.next())
			list.add(getEntityFromResultSet(res));
		
		return list;
	}
	
	/*
	 * getAll
	 * Retourne une liste de tous les objets du modèle.
	 */
	public ArrayList<Entity> getAll() throws Exception {
		return get(null, null);
	}
	
	/*
	 * getByID
	 * 	id : ID de la ligne du modèle à retourner
	 * 
	 * Retourne l'objet du modèle correspondant à l'ID indiqué.
	 */
	public Entity getByID(int id) throws Exception {
		ArrayList<Entity> list = get(getPrefix()+"id = "+id, "");
		
		if (list.size() == 0)
			return null;

		return list.get(0);
	}
}