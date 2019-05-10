package model.entities;

import java.sql.Date;
import java.sql.ResultSet;

import model.FieldsList;

/**
 * Une Entity est une entrée de table.
 * 
 * @author Julien Valverdé
 */
public class User extends Entity {
	// Objet usine
	public static EntityFactory factory;
	static {
		try {
			// Champs
			FieldsList fields = new FieldsList();
			fields.add("firstName", "String");
			fields.add("lastName", "String");
			fields.add("email", "String");
			fields.add("password", "String");
			fields.add("birth", "Date");
			
			// Création de l'objet
			factory = new EntityFactory(
					"model.entities.User",
					"users",
					"user",
					fields);
		} catch (Exception e) {
			System.out.println("Initialisation de User impossible : "+e);
		}
	}
	
	// Attributs de l'entité
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Date birth;
	
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public Date getBirth() {
		return birth;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	
	public User(EntityFactory factory) throws Exception {
		super(factory);
	}
	
	public void save(ResultSet res) throws Exception {
		super.save(res);
		
		firstName = res.getString(getPrefix()+"firstName");
		lastName = res.getString(getPrefix()+"lastName");
		email = res.getString(getPrefix()+"email");
		password = res.getString(getPrefix()+"password");
		birth = res.getDate(getPrefix()+"birth");
	}
	
	/**
	 * Renvoie la liste des champs à mettre à jour lors d'une opération update.
	 */
	/*
	public ArrayList<String> getFields() {
		ArrayList<String> fields = super.getUpdateFields();
		fields.add("firstName");
		fields.add("lastName");
		fields.add("email");
		fields.add("password");
		fields.add("birth");
		
		return fields;
	}
	*/
	
	/*
	 * toString
	 * Affiche l'instance sous forme textuelle
	 */
	public String toString() {
		String str = "User no. "+getID()+":\n";
		str += "\t- firstName: "+getFirstName()+"\n";
		str += "\t- lastName: "+getLastName()+"\n";
		str += "\t- email: "+getEmail()+"\n";
		str += "\t- birth: "+getBirth()+"\n";
		
		return str;
	}
}