package view.elements;

import model.entities.Event;

/**
 * L'EntityTree permettant de sélectionner les utilisateurs dont il faut afficher les évènements
 * 
 * @author Julien Valverdé
 */
public class AgendaUsersEntityTree extends SelectableEntityTree {
	private static final long serialVersionUID = -1156581786365101846L;
	
	/*
	 * Constructeur
	 */
	public AgendaUsersEntityTree() throws Exception {
		super("Utilisateurs", Event.factory.getAll(), true, "Utilisateurs actifs");
	}
}