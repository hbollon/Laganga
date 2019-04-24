package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Un UserGroup représente l'appartenance d'un utilisateur (User) à un groupe (Group).
 * 
 * @author Julien Valverdé
 */
public class UserGroup extends Entity {
	// Propriétés du type d'entité
	public static final String TABLE = "users_groups";
	public static final String SINGLE = "usergroup";
	
	// Objet usine
	public static EntityFactory factory;
	static {
		try {
			factory = new EntityFactory("model.UserGroup");
		} catch (Exception e) {}
	}
	
	// Attributs de l'entité
	private User user;
	private Group group;
	
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
	public void setMembers(ArrayList<Entity> members) {
		this.members = members;
	}
	
	// Constructeur
	public UserGroup(EntityFactory factory) throws Exception {
		super(factory);
	}
	
	public void save(ResultSet res) throws Exception {
		super.save(res);
		
		name = res.getString(getPrefix()+"name");
		
		// Récupération de la liste des membres du groupe
		
	}
	
	protected int bindUpdateFields(PreparedStatement st) throws Exception {
		int i = super.bindUpdateFields(st);
		
		st.setString(i, name); i++;
		
		return i;
	}
	
	/**
	 * Renvoie la liste des champs à mettre à jour lors d'une opération update.
	 */
	public String getUpdateFields() {
		String fields = super.getUpdateFields();
		
		fields += "`"+getPrefix()+"name` = ?, ";
		fields += "`"+getPrefix()+"owner` = ? ";
		
		return fields;
	}
}