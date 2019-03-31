package model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Un ModelFactory est associé à une table de la base de données et à une classe du modèle (ex: users/User).
 * Il permet d'effectuer plusieurs opérations sur le modèle, comme récupérer un certain nombre de lignes depuis la
 * base de données, instancier la classe qui leur est associée et créer de nouvelles entrées.
 * 
 * @author Julien Valverdé
 */
public class EntityFactory {
	private Class<?> entityClass; // Classe de l'entité
	private Map<Integer, Entity> entities = new HashMap<Integer, Entity>(); // Liste des objets
	
	private String table; // Table associée à l'entitée
	private String single; // Appellation d'une entité seule
	private EntityFactory[] joins; // Entités jointes
	private EntityFields fields; // Champs
	
	// Getteurs
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
		return single+"_";
	}
	public EntityFactory[] getJoins() {
		return joins;
	}
	public EntityFields getFields() {
		return fields;
	}
	
	// Constructeur
	public EntityFactory(String className) throws Exception {
		entityClass = Class.forName(className); // Objet de la classe de l'entité
		
		table = (String) entityClass.getDeclaredField("TABLE").get(null);
		single = (String) entityClass.getDeclaredField("SINGLE").get(null);
		
		try {
			joins = (EntityFactory[]) entityClass.getDeclaredField("JOINS").get(null);
		}
		catch (Exception e) {}
	}
	
	// Créateurs d'objets
	private Entity newEntity(ResultSet res) throws Exception {
		Entity entity = (Entity) entityClass.getConstructor(EntityFactory.class, ResultSet.class).newInstance(this, res);
		
		entities.put((int) entity.get("id"), entity);
		
		return entity;
	}
	
	/**
	 * Assemble une requête SQL pour récupérer des objets.
	 * 
	 * @param whereClause Clause WHERE de la requête (null pour ne pas la préciser).
	 * @param additionalClauses Clauses additionnelles (null pour ne pas en préciser).
	 * @return La requête SQL.
	 */
	private String getSelectQuery(String whereClause, String additionalClauses) {
		String query = "SELECT * FROM `"+getTable()+"`";
		
		if (joins != null && joins.length > 0) {
			for (int i = 0; i < joins.length; i++) {
				String table = joins[i].getTable();
				String row = joins[i].getPrefix()+"id";
				
				query += "\nJOIN `"+table+"` ON `"+table+"`.`"+row+"` = `"+getTable()+"`.`"+row+"`";
			}
		}
		
		if (whereClause != null)
			query += "\nWHERE "+whereClause;
		
		if (additionalClauses == null)
			additionalClauses = "ORDER BY `"+getPrefix()+"id` DESC";
		
		return query+"\n"+additionalClauses;
	}
	
	/*
	 * getInstanceFromID
	 * Renvoie l'instance du modèle d'ID id. Si une telle instance n'existe pas, elle est crée.
	 */
	public Entity getEntityFromResultSet(ResultSet res) throws Exception {
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
	public ArrayList<Entity> get(String whereClause, String additionalClauses) throws Exception {
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
		ArrayList<Entity> list = get(getPrefix()+"id = "+id, null);
		
		if (list.size() == 0)
			return null;

		return list.get(0);
	}
}