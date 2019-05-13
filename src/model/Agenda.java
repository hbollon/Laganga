package model;

import java.util.List;
import java.util.Observable;

import model.entities.Entity;
import model.entities.Event;
import model.entities.User;

@SuppressWarnings("deprecation")
public class Agenda extends Observable {
	// Agenda principal
	public static Agenda agenda = new Agenda();
	
	
	/*
	 * Attributs
	 */
	private User user; // Utilisateur dont il faut afficher les évènement auxquels il participe
	private List<Entity> events; // Liste des évènements
	
	
	/*
	 * Getteurs
	 */
	public User getUser() {
		return user;
	}
	public List<Entity> getEvents() {
		return events;
	}
	
	
	/*
	 * Setteurs
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	
	/*
	 * Constructeurs
	 */
	public Agenda(User user) {
		this.user = user;
	}
	public Agenda() {
		this(LocalUser.localUser.getUser());
	}
	
	
	/*
	 * Récupération des évènements
	 */
	
	// Récupérer les évènements donc l'utilisateur courant participe
	private List<Entity> fetchEvents() throws Exception {
		return Event.factory.getAll();
	}
	
	// Mettre à jour l'agenda et prévenir les observers
	public void refresh() throws Exception {
		fetchEvents();
		
		setChanged();
		notifyObservers();
	}
}