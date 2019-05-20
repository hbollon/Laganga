package view.elements;

import model.LocalUser;
import model.entities.User;

/**
 * Arbre permettant de séléctionner les membres participant à l'évènement en cours de création
 * 
 * @author hbollon
 *
 */

public class ParticipationUsersEntityTree extends SelectableEntityTree {
	
	private static final long serialVersionUID = 7751283723102681638L;

	protected ParticipationUsersEntityTree(boolean callInternalConstructor) throws Exception {
		super("Utilisateurs", User.factory.getAll(), true, "Utilisateurs participant");
		setEntitySelected(LocalUser.localUser.getUser());
	}
	
	public ParticipationUsersEntityTree() throws Exception {
		this(true);
		
		updateModel();
		updateView();
	}
	
}