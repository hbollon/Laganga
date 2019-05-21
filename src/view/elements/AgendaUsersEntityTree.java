package view.elements;

import java.util.ArrayList;

import model.Agenda;
import model.LocalUser;
import model.entities.Entity;
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
	protected AgendaUsersEntityTree(boolean callInternalConstructor) {
		super("Tous les utilisateurs", new ArrayList<Entity>(), true, "Utilisateurs actifs", SelectableEntityTree.NO_MAX, true);
		refreshBaseList();
		setEntitySelected(LocalUser.localUser.getUser());
	}
	
	
	/*
	 * Constructeur public
	 */
	public AgendaUsersEntityTree() {
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
	
	public void refreshBaseList() {
		try {
			setBaseList(User.factory.getAll());
			updateView();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}