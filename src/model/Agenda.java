package model;

import java.util.List;
import java.util.Observable;

import model.entities.Entity;
import model.entities.Event;

@SuppressWarnings("deprecation")
public class Agenda extends Observable {
	// Agenda principal
	public static Agenda agenda = new Agenda();
	
	
	/*
	 * Attributs
	 */
	
	// Liste des évènements
	private List<Entity> events;
	
	
	/*
	 * Getteurs
	 */
	
	public List<Entity> getEvents() {
		return events;
	}
	
	
	/*
	 * Récupération des évènements
	 */
	
	// Récupérer les évènements donc l'utilisateur courant participe
	private List<Entity> fetchEvents() throws Exception {
		return Event.factory.getAll();
	}
	
	// Mettre à jour l'agenda et prévenir les observeurs
	public void refresh() throws Exception {
		fetchEvents();
		
		setChanged();
		notifyObservers();
	}
}