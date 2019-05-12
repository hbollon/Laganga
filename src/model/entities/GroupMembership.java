package model.entities;

import java.util.HashMap;
import java.util.Map;

import model.FieldsList;

/*
 * Un GroupMembership représente l'appartenance d'un User à un Group
 */
public class GroupMembership extends Entity {
	/*
	 * Objet usine
	 */
	public static EntityFactory factory = new EntityFactory();
	static {
		factory.setClassName("model.entities.GroupMembership");
		factory.setTable("groupMemberships");
		factory.setSingle("groupMembership");
		factory.setParent(Entity.factory);
		
		// Champs
		FieldsList fields = new FieldsList();
		fields.add("user", "model.entities.User");
		fields.add("group", "model.entities.Group");
		
		factory.setFieldsList(fields);
		
		// Entités jointes
		Map<String, EntityFactory> joinedEntities = new HashMap<String, EntityFactory>();
		joinedEntities.put("user", User.factory);
		joinedEntities.put("group", Group.factory);
		
		factory.setJoinedEntities(joinedEntities);
	}
	
	/*
	 * Getteurs des attributs
	 */
	public User getUser() {
		return (User) getFieldsValues().get("user");
	}
	public Group getGroup() {
		return (Group) getFieldsValues().get("group");
	}
}