package model;

import java.sql.PreparedStatement;
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
		entityClass = Class.forName(className); // Classe de l'entité
		
		table = (String) entityClass.getDeclaredField("TABLE").get(null);
		single = (String) entityClass.getDeclaredField("SINGLE").get(null);
		
		try {
			joins = (EntityFactory[]) entityClass.getDeclaredField("JOINS").get(null);
		}
		catch (Exception e) {}
	}
	
	// Créateurs d'objets
	private Entity newEntity(ResultSet res) throws Exception {
		Entity entity = (Entity) entityClass.getConstructor(EntityFactory.class).newInstance(this);
		
		entities.put((int) entity.getId(), entity);
		
		return entity;
	}
	
	public void save(Entity ent, ResultSet res) throws Exception {
		ent.save(res);
	}
	
	public int bindUpdateFields(Entity ent, PreparedStatement st) throws Exception {
		return ent.bindUpdateFields(st);
	}
	
	/**
	 * Assemble une requête SQL pour récupérer des objets.
	 * 
	 * @param whereClause Clause WHERE de la requête (null pour ne pas la préciser).
	 * @param additionalClauses Clauses additionnelles (null pour ne pas en préciser).
	 * @return La requête SQL.
	 */
	private String getSelectQuery(String clauses) {
		String query = "SELECT * FROM `"+getTable()+"`";
		
		if (joins != null && joins.length > 0) {
			for (int i = 0; i < joins.length; i++) {
				String table = joins[i].getTable();
				String row = joins[i].getPrefix()+"id";
				
				query += "\nJOIN `"+table+"` ON `"+table+"`.`"+row+"` = `"+getTable()+"`.`"+row+"`";
			}
		}
		
		if (clauses == null)
			clauses = "ORDER BY `"+getPrefix()+"id` DESC";
		
		return query+"\n"+clauses;
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
	public ArrayList<Entity> get(String clauses, Object[] values, String[] types) throws Exception {
		ArrayList<Entity> list = new ArrayList<Entity>();
		ResultSet res = Database.database.prepareAndExecute(getSelectQuery(clauses), values, types);
		
		while (res.next())
			list.add(getEntityFromResultSet(res));
		
		return list;
	}
	
	/*
	 * getAll
	 * Retourne une liste de tous les objets du modèle.
	 */
	public ArrayList<Entity> getAll() throws Exception {
		return get(null, null, null);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Entity getSingle(String clauses, Object[] values, String[] types) throws Exception {
		ArrayList<Entity> list = get(clauses, values, types);
		
		if (list.size() == 0)
			return null;
		
		return list.get(0);
	}
	
	/*
	 * getByID
	 * 	id : ID de la ligne du modèle à retourner
	 * 
	 * Retourne l'objet du modèle correspondant à l'ID indiqué.
	 */
	public Entity getSingleByID(int id) throws Exception {
		Object[] values = {id};
		String[] types = {"int"};
		
		return getSingle("WHERE `"+getPrefix()+"id` = ?", values, types);
	}
	
	
	/*
	 * Insertion
	 */
	
	private String getInsertQuery() {
		String query = "INSERT INTO `"+table+"`(";
		
		// Récupération des noms des champs
		for (int i = 0; i < fields.size(); i++)
			query += "`"+getPrefix()+fields.get(i)+"`, ";
		
		return query;
	}
	
	/*
	 * Construction des requêtes SQL
	 */
	
	public String getUpdateQuery(Entity ent) {
		String query = "";
		
		query += "UPDATE `"+getTable()+"`\n";
		query += "SET "+ent.getUpdateFields()+"\n";
		query += "WHERE `"+getPrefix()+"id` = ?";
		
		return query;
	}
}