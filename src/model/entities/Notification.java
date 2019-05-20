package model.entities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.FieldsList;
import model.LocalUser;

/**
 * Un Notification représente une notification à l'attention d'un utilisateur
 * 
 * @author Julien Valverdé
 */
public class Notification extends Entity {
	/*
	 * Objet usine
	 */
	public static EntityFactory factory = new EntityFactory();
	static {
		factory.setClassName("model.entities.Notification");
		factory.setTable("notifications");
		factory.setSingle("notification");
		factory.setParent(Entity.factory);
		
		// Champs
		FieldsList fields = new FieldsList();
		fields.add("title", "String");
		fields.add("description", "String");
		fields.add("time", "DateTime");
		fields.add("target", "model.entities.User");
		
		factory.setFieldsList(fields);
	}
	
	
	/*
	 * Getteurs
	 */
	public String getTitle() {
		return (String) getFieldsValues().get("title");
	}
	public String getDescription() {
		return (String) getFieldsValues().get("description");
	}
	public Calendar getTime() {
		return (Calendar) getFieldsValues().get("time");
	}
	public User getTarget() {
		return (User) getFieldsValues().get("target");
	}
	
	
	/*
	 * Setteurs
	 */
	public void setTitle(String title) {
		getFieldsValues().put("title", title);
	}
	public void setDescription(String description) {
		getFieldsValues().put("description", description);
	}
	public void setTime(Calendar time) {
		getFieldsValues().put("time", time);
	}
	public void setTarget(User target) {
		getFieldsValues().put("target", target);
	}
	
	
	/*
	 * Wrappers d'opérations sur la BDD
	 */
	
	// Insérer une nouvelle notification
	public static Notification create(String title, String description, Calendar time, User target) throws SQLException, Exception {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("title", title);
		values.put("description", description);
		values.put("time", time);
		values.put("target", target);
		
		return (Notification) factory.insert(values);
	}
	
	// Insérer une nouvelle notification au moment présent
	public static Notification create(String title, String description, User target) throws SQLException, Exception {
		return create(title, description, Calendar.getInstance(), target);
	}
	
	// Insérer une nouvelle notification au moment présent pour l'utilisateur courant
	public static Notification create(String title, String description) throws SQLException, Exception {
		return create(title, description, Calendar.getInstance(), LocalUser.localUser.getUser());
	}
	
	// Insérer une nouvelle notification à destination de plusieurs utilisateurs
	public static List<Notification> create(String title, String description, Calendar time, List<User> targets) throws SQLException, Exception {
		List<Notification> notifications = new ArrayList<Notification>();
		
		for (int i = 0; i < targets.size(); i++)
			notifications.add(create(title, description, time, targets.get(i)));
		
		return notifications;
	}
	
	// Insérer une nouvelle notification à destination de plusieurs utilisateurs au moment présent
	public static List<Notification> create(String title, String description, List<User> targets) throws SQLException, Exception {
		return create(title, description, Calendar.getInstance(), targets);
	}
}