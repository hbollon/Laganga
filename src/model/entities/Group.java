package model.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
		fields.add("owner", "model.entities.User");
		
		factory.setFieldsList(fields);
		
		// Entités jointes
		Map<String, EntityFactory> joinedEntities = new HashMap<String, EntityFactory>();
		joinedEntities.put("owner", User.factory);
		
		factory.setJoinedEntities(joinedEntities);
	}
	
	
	/*
	 * Attributs
	 */
	private List<Entity> memberships = new ArrayList<Entity>(); // Liste des appartenances au groupe
	private List<User> members = new ArrayList<User>();
	
	
	/*
	 * Getteurs
	 */
	public String getName() {
		return (String) getFieldsValues().get("name");
	}
	public User getOwner() {
		return (User) getFieldsValues().get("owner");
	}
	public List<User> getMembers() {
		return members;
	}
	
	
	/*
	 * Setteurs
	 */
	public void setName(String name) {
		getFieldsValues().put("name", name);
	}
	public void setOwner(User owner) {
		getFieldsValues().put("owner", owner);
	}
	
	public void save(ResultSet res, String tableAlias) throws SQLException, Exception {
		super.save(res, tableAlias);
		refreshMemberships();
	}
	
	
	/*
	 * Gestion des membres du groupe
	 */
	
	// Rafraîchir la liste des appartenances au groupe
	private static FieldsList refreshMembershipsQueryFields = new FieldsList();
	static {
		refreshMembershipsQueryFields.add("id", "int");
	}
	public void refreshMemberships() throws SQLException, Exception {
		memberships.clear();
		memberships.addAll(GroupMembership.factory.get("WHERE `"+GroupMembership.factory.getPrefix()+"group` = ?", refreshMembershipsQueryFields, getFieldsValues()));
		
		refreshMembersList();
	}
	
	// Récupérer l'objet d'appartenance au groupe à partir d'un utilisateur (s'il est bien membre, dans le cas contraire la fonction renvoie null)
	public GroupMembership getMembership(User user) {
		for (int i = 0; i < memberships.size(); i++) {
			GroupMembership membership = (GroupMembership) memberships.get(i);
			
			if (membership.getUser() == user)
				return membership;
		}
		
		return null;
	}
	
	// L'utilisateur est-il membre du groupe ?
	public boolean isUserMember(User user) {
		return (getMembership(user) != null);
	}
	
	// Obtenir la liste des membres
	public void refreshMembersList() {
		members.clear();
		
		for (int i = 0; i < memberships.size(); i++)
			members.add(((GroupMembership) memberships.get(i)).getUser());
	}
	
	// Ajouter un membre
	public boolean addMember(User user) throws SQLException, Exception {
		// Si l'utilisateur est déjà membre du groupe, tout arrêter et retourner false
		if (isUserMember(user))
			return false;
		
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("user", user);
		values.put("group", this);
		
		GroupMembership.factory.insert(values);
		refreshMemberships();
		
		// Ajout réussi, retourner true
		return true;
	}
	
	// Supprimer un membre
	public boolean removeMember(User user) throws SQLException, Exception {
		// Si l'utilisateur est le propriétaire du groupe, tout arrêter et retourner false
		if (user == getOwner())
			return false;
		
		GroupMembership membership = getMembership(user);
		// Si l'utilisateur n'est pas membre du groupe, tout arrêter et retourner false
		if (membership == null)
			return false;
		
		membership.delete();
		refreshMemberships();
		
		// Suppression réussie, retourner true
		return true;
	}
	
	
	/*
	 * Méthodes liées aux évènements
	 */
	
	// Est-ce que le groupe participe à l'évènement ?
	public boolean isAttendingEvent(Event event) {
		return (event.getAttendingGroups().contains(this));
	}
	
	
	/*
	 * Affichage dans un EntityTree
	 */
	@Override
	public String getTreeDisplayName() {
		return getName();
	}
}