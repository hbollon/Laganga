package model.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.FieldsList;

public class Group extends Entity {
	/*
	 * Objet usine
	 */
	public static EntityFactory factory = new EntityFactory();
	static {
		factory.setClassName("model.entities.Group");
		factory.setTable("groups");
		factory.setSingle("group");
		factory.setParent(Entity.factory);
		
		// Champs
		FieldsList fields = new FieldsList();
		fields.add("name", "String");
		fields.add("owner", "User");
		
		factory.setFieldsList(fields);
		
		// Entités jointes
		Map<String, EntityFactory> joinedEntities = new HashMap<String, EntityFactory>();
		joinedEntities.put("owner", User.factory);
		
		factory.setJoinedEntities(joinedEntities);
	}
	
	// Liste des membres
	private List<Entity> members;
	
	// Getteurs des attributs
	public String getName() {
		return (String) getFieldsValues().get("name");
	}
	public User getOwner() {
		return (User) getFieldsValues().get("owner");
	}
	public List<Entity> getMembers() {
		return members;
	}
	
	// Setteurs des attributs
	public void setName(String name) {
		getFieldsValues().put("name", name);
	}
	public void setOwner(User owner) {
		getFieldsValues().put("owner", owner);
	}
	
	private static FieldsList refreshMembersQueryFields = new FieldsList();
	static {
		refreshMembersQueryFields.add("id", "int");
	}
	
	/**
	 * Récupère la liste des membres du groupe.
	 * @throws Exception 
	 */
	private List<Entity> refreshMembers() throws Exception {
		String usersTable = User.factory.getTable();
		String usersPrefix = User.factory.getPrefix();
		String usersGroupsTable = usersTable+"_"+getFactory().getTable();
		
		String clauses = "";
		clauses += "JOIN `"+usersGroupsTable+"` ON `"+usersGroupsTable+"`.`"+usersPrefix+"id` = `"+usersTable+"`.`"+usersPrefix+"id`";
		clauses += "WHERE `"+usersGroupsTable+"`.`"+getFactory().getPrefix()+"id` = ?";
		
		// Valeurs à binder
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("id", getID());
		
		return User.factory.get(clauses, refreshMembersQueryFields, values);
	}
	
	private void updateMembers() throws Exception {
		List<Entity> oldList = refreshMembers();
		List<Entity> updatedList = getMembers();
		
		List<Entity> toRemoveList = toRemove(oldList, updatedList);
		
		/*
		 * Requête d'ajout des nouveaux membres
		 */
		List<Entity> toAddList = toAdd(oldList, updatedList);
		
		String usersGroupsTable = User.factory.getTable()+"_"+getFactory().getTable();
		String addQuery = "INSERT INTO `"+usersGroupsTable+"` VALUES(";
		
		for (int i = 0; i < toAddList.size(); i++)
			addQuery += "?, ";
	}
}