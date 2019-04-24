package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Un GroupMembership représente l'appartenance d'un utilisateur (User) à un groupe (Group).
 * 
 * @author Julien Valverdé
 */
public class GroupMembership extends Entity {
	// Propriétés du type d'entité
	public static final String TABLE = "groupMemberships";
	public static final String SINGLE = "groupMembership";
	public static final EntityFactory[] JOINED_ENTITIES = {User.factory, Group.factory};
	public static final String[] JOINED_IDS = {"user", "group"};
	
	// Objet usine
	public static EntityFactory factory;
	static {
		try {
			factory = new EntityFactory("model.GroupMembership");
		} catch (Exception e) {}
	}
	
	// Attributs de l'entité
	private User user;
	private Group group;
	
	// Getteurs des attributs
	public User getUser() {
		return user;
	}
	public Group getGroup() {
		return group;
	}
	
	// Setteurs des attributs
	public void setUser(User user) {
		this.user = user;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	
	// Constructeur
	public GroupMembership(EntityFactory factory) throws Exception {
		super(factory);
	}
	
	public void save(ResultSet res) throws Exception {
		super.save(res);
		
		user = (User) User.factory.getFromResultSet(res);
		group = (Group) Group.factory.getFromResultSet(res);
	}
	
	protected int bindUpdateFields(PreparedStatement st) throws Exception {
		int i = super.bindUpdateFields(st);
		
		st.setInt(i, user.getID()); i++;
		st.setInt(i, group.getID()); i++;
		
		return i;
	}
	
	/**
	 * Renvoie la liste des champs à mettre à jour lors d'une opération update.
	 */
	public String getUpdateFields() {
		String fields = super.getUpdateFields();
		
		fields += "`"+getPrefix()+"user` = ?, ";
		fields += "`"+getPrefix()+"group` = ? ";
		
		return fields;
	}
}