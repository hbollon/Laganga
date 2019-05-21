package view.elements;

import java.util.ArrayList;

import model.LocalUser;
import model.entities.Entity;
import model.entities.User;

/**
 * Arbre permettant de séléctionner les membres participant à l'évènement en cours de création
 * 
 * @author hbollon
 *
 */

public class ParticipationUsersEntityTree extends SelectableEntityTree {
	
	private static final long serialVersionUID = 7751283723102681638L;

	protected ParticipationUsersEntityTree(boolean callInternalConstructor) {
		super("Utilisateurs", new ArrayList<Entity>(), true, "Utilisateurs participant");
		update();
		setEntitySelected(LocalUser.localUser.getUser());
	}
	
	public ParticipationUsersEntityTree() {
		this(true);
		updateModel();
	}
	
	public void update() {
		try {
			setBaseList(User.factory.getAll());
			updateView();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}