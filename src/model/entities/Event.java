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
		fields.add("creator", "model.entities.User");
		fields.add("begin", "DateTime");
		fields.add("end", "DateTime");
		fields.add("location", "model.entities.Location");
		fields.add("hidden", "boolean");
		
		factory.setFieldsList(fields);
		
		// Entités jointes
		Map<String, EntityFactory> joinedEntities = new HashMap<String, EntityFactory>();
		joinedEntities.put("creator", User.factory);
		joinedEntities.put("location", Location.factory);
		
		factory.setJoinedEntities(joinedEntities);
	}
	
	
	/*
	 * Attributs
	 */
	private List<Entity> userParticipations; // Participations des utilisateurs
	private List<Entity> groupParticipations; // Participations des groupes
	
	private List<User> attendingUsers = new ArrayList<User>(); // Utilisateurs individuels participants
	private List<Group> attendingGroups = new ArrayList<Group>(); // Groupes participants
	private List<User> participants = new ArrayList<User>(); // Tous les utilisateurs participants (individuellement ou par appartenance à un groupe)
	
	
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
	public User getCreator() {
		return (User) getFieldsValues().get("creator");
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
	public boolean getHidden() {
		return (boolean) getFieldsValues().get("hidden");
	}
	public List<User> getAttendingUsers() {
		return attendingUsers;
	}
	public List<Group> getAttendingGroups() {
		return attendingGroups;
	}
	public List<User> getParticipants() {
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
	public void setCreator(User creator) {
		getFieldsValues().put("creator", creator);
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
	public void setHidden(boolean hidden) {
		getFieldsValues().put("hidden", hidden);
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
	public static Event insert(String name, String type, int priority, User creator, Calendar begin, Calendar end, Location location, boolean hidden, List<Entity> users, List<Entity> groups) throws ParticipantsBusyException, SQLException, Exception {
		// Insertion dans la BDD
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("name", name);
		values.put("type", type);
		values.put("priority", priority);
		values.put("creator", creator);
		values.put("begin", begin);
		values.put("end", end);
		values.put("location", location);
		values.put("hidden", hidden);
		
		Event event = (Event) Event.factory.insert(values);
		
		// Si certains participants prennent déjà part à un évènement dans cette plage horaire, lever une exception
		/*
		List<Entity> busyParticipants = getBusyParticipants(users, groups, begin, end);
		if (busyParticipants.size() > 0)
			throw new ParticipantsBusyException(busyParticipants);
		*/
		
		return event;
	}
	public static Event insert(String name, String type, int priority, User creator, Calendar begin, Calendar end, Location location, boolean hidden) throws SQLException, Exception {
		return insert(name, type, priority, creator, begin, end, location, hidden, EMPTY_ENTITY_LIST, EMPTY_ENTITY_LIST);
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
		
		refreshAttendingUsersList();
		refreshAttendingGroupsList();
		refreshParticipantsList();
	}
	
	// Rafraîchir la liste des utilisateurs participant individuellement
	public void refreshAttendingUsersList() {
		attendingUsers.clear();
		
		for (int i = 0; i < userParticipations.size(); i++)
			attendingUsers.add(((EventUserParticipation) userParticipations.get(i)).getUser());
	}
	
	// Rafraîchir la liste des groupes participants
	public void refreshAttendingGroupsList() {
		attendingGroups.clear();
		
		for (int i = 0; i < groupParticipations.size(); i++)
			attendingGroups.add(((EventGroupParticipation) groupParticipations.get(i)).getGroup());
	}
	
	// Rafraîchir la liste de tous les utilisateurs participant à l'évènement (individuellement + membres des groupes participants)
	public void refreshParticipantsList() {
		participants.clear();
		participants.addAll(attendingUsers);
		
		for (int i = 0; i < attendingGroups.size(); i++)
			participants.addAll(attendingGroups.get(i).getMembers());
	}
	
	// Retourne les utilisateurs ne pouvant pas participer à l'évènement parmis les utilisateurs passés
	public List<User> getBusyUsers(List<Entity> users, List<Entity> groups) throws Exception {
		List<User> busy = new ArrayList<User>(); // Liste des utilisateurs occupés (à remplir)
		
		// Recherche des utilisateurs occupés
		for (int i = 0; i < users.size(); i++) {
			User user = (User) users.get(i);
			
			if (!user.canAttendEvent(this))
				busy.add(user);
		}
		
		// Recherche des membres de groupes occupés
		for (int i = 0; i < groups.size(); i++) {
			Group group = (Group) groups.get(i);
			
			for (int j = 0; j < group.getMembers().size(); j++) {
				User user = group.getMembers().get(j);
				
				if (!user.canAttendEvent(this) && !busy.contains(user))
					busy.add(user);
			}
		}
		
		return busy;
	}
	
	
	/*
	 * Ajout/suppression de participants
	 */
	
	// Ajouter un utilisateur participant
	public boolean addParticipant(User user) throws SQLException, Exception {
		// Si l'utilisateur est déjà membre du groupe, tout arrêter et retourner false
		if (user.isAttendingEvent(this))
			return false;
		
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("user", user);
		values.put("event", this);
		
		EventUserParticipation.factory.insert(values);
		refreshParticipations();
		
		// Envoyer une notification à l'utilisateur
		Notification.create("Vous avez été ajouté à un évènement", "Vous participez désormais à l'évènement \""+getName()+"\"", user);
		
		// Ajout réussi, retourner true
		return true;
	}
	
	// Ajouter un groupe participant
	public boolean addParticipant(Group group) throws SQLException, Exception {
		// Si l'utilisateur est déjà membre du groupe, tout arrêter et retourner false
		if (group.isAttendingEvent(this))
			return false;
		
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("group", group);
		values.put("event", this);
		
		EventGroupParticipation.factory.insert(values);
		refreshParticipations();
		
		// Envoyer une notification aux membres du groupe
		for (int i = 0; i < group.getMembers().size(); i++)
			Notification.create("Vous avez été ajouté à un évènement", "Vous participez désormais à l'évènement \""+getName()+"\"", group.getMembers().get(i));
		
		// Ajout réussi, retourner true
		return true;
	}
}