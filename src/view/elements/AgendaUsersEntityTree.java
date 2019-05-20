package view.elements;

import model.Agenda;
import model.LocalUser;
import model.entities.User;

/**
 * L'EntityTree permettant de sélectionner les utilisateurs dont il faut afficher les évènements
 * 
 * @author Julien Valverdé
 */
public class AgendaUsersEntityTree extends SelectableEntityTree {
	private static final long serialVersionUID = -1156581786365101846L;
	
	/*
	 * Constructeur interne
	 */
	protected AgendaUsersEntityTree(boolean callInternalConstructor) throws Exception {
		super("Tous les utilisateurs", User.factory.getAll(), true, "Utilisateurs actifs", SelectableEntityTree.NO_MAX, true);
		setEntitySelected(LocalUser.localUser.getUser());
	}
	
	
	/*
	 * Constructeur public
	 */
	public AgendaUsersEntityTree() throws Exception {
		this(true);
		
		updateModel();
		updateView();
	}
	
	
	/*
	 * Mise à jour des modèles attachés à la vue
	 */
	public void updateModel() {
		Agenda.agenda.setActiveUsers(getSelectedList());
		
		try {
			Agenda.agenda.refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}