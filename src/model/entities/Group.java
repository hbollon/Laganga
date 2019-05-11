package model.entities;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.FieldsList;

public class Group extends Entity {
	// Objet usine
	public static EntityFactory factory;
	static {
		try {
			// Champs
			FieldsList fields = new FieldsList();
			fields.add("name", "String");
			fields.add("owner", "int");
			
			// Entités jointes
			ArrayList<EntityFactory> joinedEntities = new ArrayList<EntityFactory>();
			ArrayList<String> joinedFields = new ArrayList<String>();
			
			joinedEntities.add(User.factory); joinedFields.add("owner");
			
			// Création de l'objet
			factory = new EntityFactory(
					"model.entities.Group",
					"groups",
					"group",
					fields,
					joinedEntities,
					joinedFields);
		} catch (Exception e) {
			System.out.println("Initialisation de Group impossible : "+e);
		}
	}
	
	// Attributs de l'entité
	private String name;
	private User owner;
	private ArrayList<Entity> members;
	
	// Getteurs des attributs
	public String getName() {
		return name;
	}
	public User getOwner() {
		return owner;
	}
	public ArrayList<Entity> getMembers() {
		return members;
	}
	
	// Setteurs des attributs
	public void setName(String name) {
		this.name = name;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	// Constructeur
	public Group(EntityFactory factory) throws Exception {
		super(factory);
	}
	
	public void save(ResultSet res) throws Exception {
		super.save(res);
		
		name = res.getString(getPrefix()+"name");
		owner = (User) User.factory.getFromResultSet(res);
		
		// Récupération de la liste des membres du groupe
		members = refreshMembers();
	}
	
	/**
	 * Renvoie la liste des champs à mettre à jour lors d'une opération update.
	 */
	/*
	public String getUpdateFields() {
		String fields = super.getUpdateFields();
		
		fields += "`"+getPrefix()+"name` = ?, ";
		fields += "`"+getPrefix()+"owner` = ? ";
		
		return fields;
	}
	*/
	
	private static FieldsList refreshMembersQueryFields = new FieldsList();
	static {
		refreshMembersQueryFields.add("id", "int");
	}
	
	/**
	 * Récupère la liste des membres du groupe.
	 * @throws Exception 
	 */
	private ArrayList<Entity> refreshMembers() throws Exception {
		String usersTable = User.factory.getTable();
		String usersPrefix = User.factory.getPrefix();
		String usersGroupsTable = usersTable+"_"+getTable();
		
		String clauses = "";
		clauses += "JOIN `"+usersGroupsTable+"` ON `"+usersGroupsTable+"`.`"+usersPrefix+"id` = `"+usersTable+"`.`"+usersPrefix+"id`";
		clauses += "WHERE `"+usersGroupsTable+"`.`"+getPrefix()+"id` = ?";
		
		// Valeurs à binder
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("id", getID());
		
		return User.factory.get(clauses, refreshMembersQueryFields, values);
	}
	
	private void updateMembers() throws Exception {
		ArrayList<Entity> oldList = refreshMembers();
		ArrayList<Entity> updatedList = getMembers();
		
		ArrayList<Entity> toRemoveList = toRemove(oldList, updatedList);
		
		/*
		 * Requête d'ajout des nouveaux membres
		 */
		ArrayList<Entity> toAddList = toAdd(oldList, updatedList);
		
		String usersGroupsTable = User.factory.getTable()+"_"+getTable();
		String addQuery = "INSERT INTO `"+usersGroupsTable+"` VALUES(";
		
		for (int i = 0; i < toAddList.size(); i++)
			addQuery += "?, ";
	}
}