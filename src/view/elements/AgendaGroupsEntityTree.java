package view.elements;

import model.Agenda;
import model.entities.Group;

/**
 * L'EntityTree permettant de sélectionner les groupes dont il faut afficher les évènements
 * 
 * @author Julien Valverdé
 */
public class AgendaGroupsEntityTree extends SelectableEntityTree {
	private static final long serialVersionUID = -1156581786365101846L;
	
	/*
	 * Constructeur interne
	 */
	protected AgendaGroupsEntityTree(boolean callInternalConstructor) throws Exception {
		super("Tous les groupes", Group.factory.getAll(), true, "Groupes actifs", true);
	}
	
	
	/*
	 * Constructeur public
	 */
	public AgendaGroupsEntityTree() throws Exception {
		this(true);
		updateView();
	}
	
	
	/*
	 * Mise à jour des modèles attachés à la vue
	 */
	public void updateModel() {
		Agenda.agenda.setActiveGroups(getSelectedList());
		
		try {
			Agenda.agenda.refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}