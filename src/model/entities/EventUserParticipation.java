package model.entities;

import java.util.HashMap;
import java.util.Map;

import model.FieldsList;

/**
 * Un EventUserParticipation représente la participation d'un utilisateur à un évènement
 * 
 * @author Julien Valverdé
 */
public class EventUserParticipation extends Entity {
	/*
	 * Objet usine
	 */
	public static EntityFactory factory = new EntityFactory();
	static {
		factory.setClassName("model.entities.EventUserParticipation");
		factory.setTable("eventUserParticipations");
		factory.setSingle("eventUserParticipation");
		factory.setParent(Entity.factory);
		
		// Champs
		FieldsList fields = new FieldsList();
		fields.add("user", "model.entities.User");
		fields.add("event", "model.entities.Event");
		
		factory.setFieldsList(fields);
		
		// Entités jointes
		Map<String, EntityFactory> joinedEntities = new HashMap<String, EntityFactory>();
		joinedEntities.put("user", User.factory);
		joinedEntities.put("event", Event.factory);
		
		factory.setJoinedEntities(joinedEntities);
	}
	
	/*
	 * Getteurs des attributs
	 */
	public User getUser() {
		return (User) getFieldsValues().get("user");
	}
	public Event getEvent() {
		return (Event) getFieldsValues().get("event");
	}
}