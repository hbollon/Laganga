package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import model.entities.Entity;
import model.entities.Event;
import model.entities.Group;
import model.entities.User;

@SuppressWarnings("deprecation")
public class Agenda extends Observable {
	// Agenda principal
	public static Agenda agenda = new Agenda();
	
	
	/*
	 * Attributs
	 */
	private List<User> activeUsers = new ArrayList<User>(); // Utilisateurs dont il faut afficher les évènement
	private List<Group> activeGroups = new ArrayList<Group>(); // Groupes dont il faut afficher les évènement
	private List<Entity> events; // Liste des évènements
	
	
	/*
	 * Getteurs
	 */
	public List<User> getActiveUsers() {
		return activeUsers;
	}
	public List<Group> getActiveGroups() {
		return activeGroups;
	}
	public List<Entity> getEvents() {
		return events;
	}
	
	
	/*
	 * Gestion des utilisateurs et groupes actifs
	 */
	public void reset() {
		activeUsers.clear();
		activeUsers.add(LocalUser.localUser.getUser());
		
		activeGroups.clear();
	}
	public void setActive(User user) {
		if (!activeUsers.contains(user))
			activeUsers.add(user);
	}
	public void setActive(Group group) {
		if (!activeGroups.contains(group))
			activeGroups.add(group);
	}
	public void setInactive(User user) {
		if (activeUsers.contains(user))
			activeUsers.remove(user);
	}
	public void setInactive(Group group) {
		if (activeGroups.contains(group))
			activeGroups.remove(group);
	}
	
	
	/*
	 * Constructeur
	 */
	public Agenda() {
		reset();
	}
	
	
	/*
	 * Récupération des évènements
	 */
	
	// Récupérer les évènements donc l'utilisateur courant participe
	private List<Entity> fetchEvents() throws Exception {
		List<Entity> events = new ArrayList<Entity>();
		events.addAll(Event.factory.getAll());
		
		return events;
	}
	
	// Mettre à jour l'agenda et prévenir les observers
	public void refresh() throws Exception {
		events = fetchEvents();
		
		setChanged();
		notifyObservers();
	}
}