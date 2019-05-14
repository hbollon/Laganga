package model.entities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.FieldsList;
import model.exceptions.ParticipantsBusyException;

public class Event extends Entity {
	private static final List<Entity> EMPTY_ENTITY_LIST = new ArrayList<Entity>();
	
	/*
	 * Objet usine
	 */
	public static EntityFactory factory = new EntityFactory();
	static {
		factory.setClassName("model.entities.Event");
		factory.setTable("events");
		factory.setSingle("event");
		factory.setParent(Entity.factory);
		
		// Champs
		FieldsList fields = new FieldsList();
		fields.add("name", "String");
		fields.add("type", "String");
		fields.add("priority", "int");
		fields.add("begin", "DateTime");
		fields.add("end", "DateTime");
		fields.add("location", "model.entities.Location");
		
		factory.setFieldsList(fields);
		
		// Entités jointes
		Map<String, EntityFactory> joinedEntities = new HashMap<String, EntityFactory>();
		joinedEntities.put("location", Location.factory);
		
		factory.setJoinedEntities(joinedEntities);
	}
	
	// Getteurs des attributs
	public String getName() {
		return (String) getFieldsValues().get("name");
	}
	public String getType() {
		return (String) getFieldsValues().get("type");
	}
	public int getPriority() {
		return (int) getFieldsValues().get("priority");
	}
	public Calendar getBegin() {
		return (Calendar) getFieldsValues().get("begin");
	}
	public Calendar getEnd() {
		return (Calendar) getFieldsValues().get("end");
	}
	public Location getLocation() {
		return (Location) getFieldsValues().get("location");
	}
	
	// Setteurs des attributs
	public void setName(String name) {
		getFieldsValues().put("name", name);
	}
	public void setType(String type) {
		getFieldsValues().put("type", type);
	}
	public void setPriority(int priority) {
		getFieldsValues().put("priority", priority);
	}
	public void setBegin(Calendar begin) {
		getFieldsValues().put("begin", begin);
	}
	public void setEnd(Calendar end) {
		getFieldsValues().put("end", end);
	}
	public void setLocation(Location location) {
		getFieldsValues().put("location", location);
	}
	
	
	/*
	 * Wrappers d'opérations sur la BDD
	 */
	
	// Ajouter un nouvel évènement
	public static Event insert(String name, String type, int priority, Calendar begin, Calendar end, Location location, List<Entity> users, List<Entity> groups) throws ParticipantsBusyException, SQLException, Exception {
		// Si certains participants prennent déjà part à un évènement dans cette plage horaire, lever une exception
		List<Entity> busyParticipants = getBusyParticipants(users, groups, begin, end);
		if (busyParticipants.size() > 0)
			throw new ParticipantsBusyException(busyParticipants);
		
		// Insertion dans la BDD
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("name", name);
		values.put("type", type);
		values.put("priority", priority);
		values.put("begin", begin);
		values.put("end", end);
		values.put("location", location);
		
		return (Event) Event.factory.insert(values);
	}
	public static Event insert(String name, String type, int priority, Calendar begin, Calendar end, Location location) throws SQLException, Exception {
		return insert(name, type, priority, begin, end, location, EMPTY_ENTITY_LIST, EMPTY_ENTITY_LIST);
	}
	
	
	/*
	 * Méthodes diverses
	 */
	
	// Cet évènement chevauche t-il la plage horraire (ou l'évènement) passé(e) ?
	public boolean isOverlapping(Calendar from, Calendar to) {
		return !(
				((from.before(getBegin()) || from.equals(getBegin())) && (to.before(getBegin()) || to.equals(getBegin()))) ||
				((from.equals(getEnd()) || from.after(getEnd())) && (to.equals(getEnd()) || to.after(getEnd())))
				);
	}
	public boolean isOverlapping(Event event) {
		return isOverlapping(event.getBegin(), event.getEnd());
	}
	
	
	/*
	 * Gestion des participants
	 */
	
	// Est-ce que les participants passés (utilisateurs et membres des groupes) sont libres à cette plage horaire ?
	public static List<Entity> getBusyParticipants(List<Entity> users, List<Entity> groups, Calendar from, Calendar to) throws Exception {
		List<Entity> participants = new ArrayList<Entity>(); // Liste de tous les participants (individuels + membres des groupes)
		List<Entity> busyParticipants = new ArrayList<Entity>(); // Liste des utilisateurs occupés (à remplir)
		
		participants.addAll(users); // Ajout des participants individuels
		
		// Ajout des participants membres des groupes
		for (int i = 0; i < groups.size(); i++) {
			List<Entity> members = ((Group) groups.get(i)).getMembersList();
			
			// Parcourir les membres du groupe
			for (int j = 0; j < members.size(); j++) {
				User user = (User) members.get(i);
				
				if (!participants.contains(user))
					participants.add(user);
			}
		}
		
		// Recherche des utilisateurs occupés
		for (int i = 0; i < participants.size(); i++) {
			User user = (User) participants.get(i);
			
			if (user.isBusy(from, to))
				busyParticipants.add(user);
		}
		
		return busyParticipants;
	}
}