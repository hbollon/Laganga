package model.entities;

import java.util.HashMap;
import java.util.Map;

import model.FieldsList;

/**
 * Un EventGroupParticipation représente la participation des membres d'un groupe à un évènement
 * 
 * @author Julien Valverdé
 */
public class EventGroupParticipation extends Entity {
	/*
	 * Objet usine
	 */
	public static EntityFactory factory = new EntityFactory();
	static {
		factory.setClassName("model.entities.EventGroupParticipation");
		factory.setTable("eventGroupParticipations");
		factory.setSingle("eventGroupParticipation");
		factory.setParent(Entity.factory);
		
		// Champs
		FieldsList fields = new FieldsList();
		fields.add("group", "model.entities.Group");
		fields.add("event", "model.entities.Event");
		
		factory.setFieldsList(fields);
		
		// Entités jointes
		Map<String, EntityFactory> joinedEntities = new HashMap<String, EntityFactory>();
		joinedEntities.put("group", Group.factory);
		joinedEntities.put("event", Event.factory);
		
		factory.setJoinedEntities(joinedEntities);
	}
	
	/*
	 * Getteurs des attributs
	 */
	public Group getGroup() {
		return (Group) getFieldsValues().get("group");
	}
	public Event getEvent() {
		return (Event) getFieldsValues().get("event");
	}
}