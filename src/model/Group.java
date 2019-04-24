package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Group extends Entity {
	// Propriétés du type d'entité
	public static final String TABLE = "groups";
	public static final String SINGLE = "group";
	public static final String[] JOIN_FIELDS = {"owner"};
	public static final EntityFactory[] JOIN_ENTITIES = {User.factory};
	
	// Objet usine
	public static EntityFactory factory;
	static {
		try {
			factory = new EntityFactory("model.Group");
		} catch (Exception e) {}
	}
	
	// Attributs de l'entité
	private String name;
	private User owner;
	private ArrayList<Entity> members;
	
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
	public Group(EntityFactory factory) throws Exception {
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