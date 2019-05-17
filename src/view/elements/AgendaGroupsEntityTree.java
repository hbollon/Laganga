package view.elements;

import model.entities.Group;

/**
 * L'EntityTree permettant de sélectionner les groupes dont il faut afficher les évènements
 * 
 * @author Julien Valverdé
 */
public class AgendaGroupsEntityTree extends SelectableEntityTree {
	private static final long serialVersionUID = -1156581786365101846L;
	
	/*
	 * Constructeur
	 */
	public AgendaGroupsEntityTree() throws Exception {
		super("Groupes", Group.factory.getAll(), true, "Groupes actifs");
	}
}