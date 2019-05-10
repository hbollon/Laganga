package model.entities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import model.Database;
import model.FieldsList;

/**
 * Un ModelFactory est associé à une table de la base de données et à une classe du modèle (ex: users/User).
 * Il permet d'effectuer plusieurs opérations sur le modèle, comme récupérer un certain nombre de lignes depuis la
 * base de données, instancier la classe qui leur est associée et créer de nouvelles entrées.
 * 
 * @author Julien Valverdé
 */
public class EntityFactory {
	private HashMap<Integer, Entity> entities = new HashMap<Integer, Entity>(); // Liste des objets
	
	/*
	 * Attributs
	 */
	private Class<?> classObject; // Classe de l'entité
	
	private String table; // Nom de la table associée à l'entité
	private String single; // Appellation d'une entité seule
	private String prefix; // Préfixe des champs
	private EntityFactory parent; // Entité parente
	private FieldsList fields; // Champs
	
	private ArrayList<EntityFactory> joinedEntities; // Entités jointes
	private ArrayList<String> joinedFields;
	
	/*
	 * Getteurs
	 */
	public Class<?> getClassObject() {
		return classObject;
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
	public EntityFactory getParent() {
		return parent;
	}
	public FieldsList getFields() {
		return fields;
	}
	public ArrayList<EntityFactory> getJoinedEntities() {
		return joinedEntities;
	}
	public ArrayList<String> getJoinedFields() {
		return joinedFields;
	}
	
	/*
	 * Constructeurs
	 */
	public EntityFactory(String className, String table, String single, EntityFactory parent, FieldsList fields, ArrayList<EntityFactory> joinedEntities, ArrayList<String> joinedFields) throws ClassNotFoundException {
		this.classObject = Class.forName(className);
		
		this.table = table;
		this.single = single;
		this.prefix = single+"_";
		this.parent = parent;
		this.fields = getFieldsList(fields);
		
		this.joinedEntities = joinedEntities;
		this.joinedFields = joinedFields;
	}
	public EntityFactory(String className, String table, String single, FieldsList fields) throws ClassNotFoundException {
		this(className, table, single, null, fields, null, null);
	}
	public EntityFactory(String className, String table, String single, EntityFactory parent, FieldsList fields) throws ClassNotFoundException {
		this(className, table, single, parent, fields, null, null);
	}
	public EntityFactory(String className, String table, String single, FieldsList fields, ArrayList<EntityFactory> joinedEntities, ArrayList<String> joinedFields) throws ClassNotFoundException {
		this(className, table, single, null, fields, joinedEntities, joinedFields);
	}
	
	/*
	 * getFieldsList
	 * Construit la liste des champs
	 */
	public FieldsList getFieldsList(FieldsList fields) {
		FieldsList list = new FieldsList();
		
		// L'entité a un parent
		if (parent != null)
			list.addAll(parent.getFields());
		
		list.addAll(fields);
		return list;
	}
	
	// Créateurs d'objets
	private Entity newEntity(ResultSet res) throws Exception {
		Entity entity = (Entity) classObject.getConstructor(EntityFactory.class).newInstance(this);
		entity.save(res);
		
		entities.put((int) entity.getID(), entity);
		
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
		
		if (joinedEntities != null && joinedEntities.size() > 0) {
			for (int i = 0; i < joinedEntities.size(); i++) {
				EntityFactory joinedEnt = joinedEntities.get(i);				
				query += "\nJOIN `"+joinedEnt.getTable()+"` ON `"+joinedEnt.getTable()+"`.`"+joinedEnt.getPrefix()+"id` = `"+getTable()+"`.`"+getPrefix()+joinedFields.get(i)+"`";
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
	public Entity getFromResultSet(ResultSet res) throws Exception {
		Entity entity = entities.get(res.getInt(getTable()+"."+getPrefix()+"id"));
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
	private ArrayList<Entity> get(ResultSet res) throws SQLException, Exception {
		ArrayList<Entity> list = new ArrayList<Entity>();
		
		// ID de la dernière ligne traitée
		int previousId = -1;
		
		// Parcours des lignes récupérées
		while (res.next()) {
			Entity ent = getFromResultSet(res); // Récupération/création de l'entité
			
			// On traite une nouvelle entité, l'enregistrer dans la liste
			if (ent.getID() != previousId) {
				list.add(ent);
				previousId = ent.getID();
			}
			
			ent.saveJoined(res); // Sauvegarde des champs joins
		}
		
		return list;
	}
	public ArrayList<Entity> get(String clauses) throws SQLException, Exception {
		return get(Database.database.prepareAndExecute(getSelectQuery(clauses)));
	}
	public ArrayList<Entity> get(String clauses, FieldsList fields, ArrayList<Object> values) throws SQLException, Exception {
		return get(Database.database.prepareAndExecute(getSelectQuery(clauses), fields, values));
	}
	
	/*
	 * getAll
	 * Retourne une liste de tous les objets du modèle.
	 */
	public ArrayList<Entity> getAll() throws Exception {
		return get((String) null);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws SQLException 
	 * @throws Exception
	 */
	public Entity getSingle(String clauses, FieldsList fields, ArrayList<Object> values) throws SQLException, Exception {
		ArrayList<Entity> list = get(clauses, fields, values);
		
		if (list.size() == 0)
			return null;
		return list.get(0);
	}
	public Entity getSingle(String clauses) throws SQLException, Exception {
		ArrayList<Entity> list = get(clauses);
		
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
	public Entity getByID(int id) throws Exception {
		// Champs
		FieldsList fields = new FieldsList();
		fields.add("id", "int");
		
		// Valeurs
		ArrayList<Object> values = new ArrayList<Object>();
		values.add(id);
		
		return getSingle("WHERE `"+getPrefix()+"id` = ?", fields, values);
	}
	
	
	/*
	 * Insertion
	 */
	
	public String getInsertQuery() {
		String query = "INSERT INTO `"+table+"`("+fields.toQueryString(prefix)+") VALUES(";
		
		// Liste des valeurs à binder
		for (int i = 0; i < fields.size(); i++)
			query += "?, ";
		
		return query.substring(0, query.length() - 2)+")";
	}
	
	// Insertion d'une nouvelle entité
	public Entity insert(ArrayList<Object> values) throws SQLException, Exception {
		ResultSet res = Database.database.prepareUpdateAndExecute(getInsertQuery(), fields, values);
		
		res.next();
		return getByID(res.getInt(1));
	}
	
	/*
	 * Construction des requêtes SQL
	 */
	
	/*
	public String getUpdateQuery(Entity ent) {
		String query = "";
		
		query += "UPDATE `"+getTable()+"`\n";
		query += "SET "+ent.getUpdateFields()+"\n";
		query += "WHERE `"+getPrefix()+"id` = ?";
		
		return query;
	}
	*/
}