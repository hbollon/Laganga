package model.entities;

import java.sql.ResultSet;
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
		fields.add("creator", "model.entities.User");
		
		factory.setFieldsList(fields);
		
		// Entités jointes
		Map<String, EntityFactory> joinedEntities = new HashMap<String, EntityFactory>();
		joinedEntities.put("location", Location.factory);
		joinedEntities.put("creator", User.factory);
		
		factory.setJoinedEntities(joinedEntities);
	}
	
	
	/*
	 * Attributs
	 */
	private List<Entity> userParticipations; // Participations des utilisateurs
	private List<Entity> groupParticipations; // Participations des groupes
	private List<Entity> participants; // Utilisateurs participants
	
	
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
	public User getCreator() {
		return (User) getFieldsValues().get("creator");
	}
	public List<Entity> getParticipants() {
		return participants;
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
	public void setCreator(User creator) {
		getFieldsValues().put("creator", creator);
	}
	
	
	/*
	 * Méthodes d'entité
	 */
	
	// Sauvegarder les champs depuis la BDD
	public void save(ResultSet res, String tableAlias) throws SQLException, Exception {
		super.save(res, tableAlias);
		refreshParticipations();
	}
	
	
	/*
	 * Wrappers d'opérations sur la BDD
	 */
	
	// Ajouter un nouvel évènement
	public static Event insert(String name, String type, int priority, Calendar begin, Calendar end, Location location, User creator, List<Entity> users, List<Entity> groups) throws ParticipantsBusyException, SQLException, Exception {
		// Insertion dans la BDD
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("name", name);
		values.put("type", type);
		values.put("priority", priority);
		values.put("begin", begin);
		values.put("end", end);
		values.put("location", location);
		values.put("creator", creator);
		
		Event event = (Event) Event.factory.insert(values);
		
		// Si certains participants prennent déjà part à un évènement dans cette plage horaire, lever une exception
		/*
		List<Entity> busyParticipants = getBusyParticipants(users, groups, begin, end);
		if (busyParticipants.size() > 0)
			throw new ParticipantsBusyException(busyParticipants);
		*/
		
		return event;
	}
	public static Event insert(String name, String type, int priority, Calendar begin, Calendar end, Location location, User creator) throws SQLException, Exception {
		return insert(name, type, priority, begin, end, location, creator, EMPTY_ENTITY_LIST, EMPTY_ENTITY_LIST);
	}
	
	// Obtenir les évènements chevauchant une certaine plage horaire (ou évènement)
	private static FieldsList getOverlappingQueryFields = new FieldsList();
	static {
		getOverlappingQueryFields.add("from", "DateTime");
		getOverlappingQueryFields.add("to", "DateTime");
		getOverlappingQueryFields.add("from", "DateTime");
		getOverlappingQueryFields.add("to", "DateTime");
	}
	public static List<Entity> getOverlapping(Calendar from, Calendar to) throws SQLException, Exception {
		String prefix = factory.getPrefix();
		
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("from", from);
		values.put("to", to);
		
		return factory.get(
				"WHERE NOT ("+
						"(? <= `"+prefix+"begin` AND ? <= `"+prefix+"begin`) OR "+
						"(`"+prefix+"end` <= ? AND `"+prefix+"end` <= ?)"+
				")",
				getOverlappingQueryFields,
				values);
	}
	public static List<Entity> getOverlapping(Event event) throws SQLException, Exception {
		return getOverlapping(event.getBegin(), event.getEnd());
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
	
	// Rafraîchir les listes des participations
	private static FieldsList refreshParticipationsQueryFields = new FieldsList();
	static {
		refreshParticipationsQueryFields.add("id", "int");
	}
	public void refreshParticipations() throws SQLException, Exception {
		userParticipations = EventUserParticipation.factory.get("WHERE `"+EventUserParticipation.factory.getPrefix()+"event` = ?", refreshParticipationsQueryFields, getFieldsValues());
		groupParticipations = EventGroupParticipation.factory.get("WHERE `"+EventGroupParticipation.factory.getPrefix()+"event` = ?", refreshParticipationsQueryFields, getFieldsValues());
		participants = buildParticipantsList();
	}
	
	// Récupérer la liste des participants (directs + membres des groupes participants)
	public List<Entity> buildParticipantsList() {
		List<Entity> participants = new ArrayList<Entity>();
		
		// Participations directes
		for (int i = 0; i < userParticipations.size(); i++)
			participants.add(((EventUserParticipation) userParticipations.get(i)).getUser());
		
		// Participations des groupes
		for (int i = 0; i < groupParticipations.size(); i++) {
			Group group = ((EventGroupParticipation) groupParticipations.get(i)).getGroup();
			List<Entity> groupMembers = group.getMembers();
			
			for (int j = 0; j < groupMembers.size(); j++)
				participants.add(groupMembers.get(j));
		}
		
		return participants;
	}
	
	// Est-ce que les participants passés (utilisateurs et membres des groupes) sont libres à cette plage horaire ?
	public static List<Entity> getBusyParticipants(List<Entity> users, List<Entity> groups, Calendar from, Calendar to) throws Exception {
		List<Entity> participants = new ArrayList<Entity>(); // Liste de tous les participants (individuels + membres des groupes)
		List<Entity> busyParticipants = new ArrayList<Entity>(); // Liste des utilisateurs occupés (à remplir)
		
		participants.addAll(users); // Ajout des participants individuels
		
		// Ajout des participants membres des groupes
		for (int i = 0; i < groups.size(); i++) {
			List<Entity> members = ((Group) groups.get(i)).getMembers();
			
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