package model.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import model.FieldsList;

/**
 * Une Entity est une entrée de table.
 * 
 * @author Julien Valverdé
 */
public class User extends Entity {
	/*
	 * Objet usine
	 */
	public static EntityFactory factory = new EntityFactory();
	static {
		factory.setClassName("model.entities.User");
		factory.setTable("users");
		factory.setSingle("user");
		factory.setParent(Entity.factory);
		
		// Champs
		FieldsList fields = new FieldsList();
		fields.add("firstName", "String");
		fields.add("lastName", "String");
		fields.add("email", "String");
		fields.add("password", "String");
		fields.add("birth", "Date");
		
		factory.setFieldsList(fields);
	}
	
	
	/*
	 * Getteurs
	 */
	public String getFirstName() {
		return (String) getFieldsValues().get("firstName");
	}
	public String getLastName() {
		return (String) getFieldsValues().get("lastName");
	}
	public String getEmail() {
		return (String) getFieldsValues().get("email");
	}
	public String getPassword() {
		return (String) getFieldsValues().get("password");
	}
	public Calendar getBirth() {
		return (Calendar) getFieldsValues().get("birth");
	}
	
	
	/*
	 * Setteurs
	 */
	public void setFirstName(String firstName) {
		getFieldsValues().put("firstName", firstName);
	}
	public void setLastName(String lastName) {
		getFieldsValues().put("lastName", lastName);
	}
	public void setEmail(String email) {
		getFieldsValues().put("email", email);
	}
	public void setPassword(String password) {
		getFieldsValues().put("password", password);
	}
	public void setBirth(Calendar birth) {
		getFieldsValues().put("birth", birth);
	}
	
	
	/*
	 * Méthodes liées aux évènements
	 */
	
	// Obtenir la liste des évènements auxquels l'utilisateur participe (dans une certaine plage horaire si précisée)
	public List<Entity> getAttendedEvents(Calendar from, Calendar to) throws Exception {
		List<Entity> events = new ArrayList<Entity>(); // Liste des évènements auxquels l'utilisateur participe (à remplir)
		List<Entity> allEvents = Event.factory.getAll(); // Liste des tous les évènements
		
		for (int i = 0; i < allEvents.size(); i++) {
			Event event = (Event) allEvents.get(i);
			
			// Si l'utilisateur participe à l'évènement et que ledit évènement chevauche la plage horaire [from, to] (si définie)
			if (isAttendingEvent(event) && ((from == null || to == null) || event.isOverlapping(from, to)))
				events.add(event);
		}
		
		return events;
	}
	public List<Entity> getAttendedEvents() throws Exception {
		return getAttendedEvents(null, null);
	}
	
	// Est-ce que l'utilisateur participe à un évènement dans la plage horaire indiquée ?
	public boolean isBusy(Calendar from, Calendar to) throws Exception {
		List<Entity> allEvents = Event.factory.getAll();
		
		for (int i = 0; i < allEvents.size(); i++) {
			Event event = (Event) allEvents.get(i);
			
			if (isAttendingEvent(event) && event.isOverlapping(from, to))
				return true;
		}
		
		return false;
	}
	
	// Est-ce que l'utilisateur peut-il participer à l'évènement ?
	public boolean canAttendEvent(Event eventToAttend) throws Exception {
		List<Entity> events = Event.getOverlapping(eventToAttend);
		
		for (int i = 0; i < events.size(); i++) {
			Event event = (Event) events.get(i);
			
			if (isAttendingEvent(event) && event.getPriority() >= eventToAttend.getPriority())
				return false;
		}
		
		return true;
	}
	
	// Est-ce que l'utilisateur participe à l'évènement ?
	public boolean isAttendingEvent(Event event) {
		return (event.getParticipants().contains(this));
	}
}