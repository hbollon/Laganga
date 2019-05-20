package view.elements;

import model.LocalUser;
import model.entities.Group;

/**
 * Arbre permettant de séléctionner les groupes participant à l'évènement en cours de création
 * 
 * @author hbollon
 *
 */

public class ParticipationGroupsEntityTree extends SelectableEntityTree {
	
	private static final long serialVersionUID = 7751283723102681638L;

	protected ParticipationGroupsEntityTree(boolean callInternalConstructor) throws Exception {
		super("Groupes", Group.factory.getAll(), true, "Groupes participant");
		setEntitySelected(LocalUser.localUser.getUser());
	}
	
	public ParticipationGroupsEntityTree() throws Exception {
		this(true);
		
		updateModel();
		updateView();
	}
	
}