package view.elements;

import model.LocalUser;
import model.entities.Group;
import model.entities.User;

public class ParticipationGroupsEntityTree extends SelectableEntityTree {
	
	private static final long serialVersionUID = 7751283723102681638L;

	protected ParticipationGroupsEntityTree(boolean callInternalConstructor) throws Exception {
		super("Groupes", Group.factory.getAll(), true, "Groupes participant", true);
		setEntitySelected(LocalUser.localUser.getUser());
	}
	
	public ParticipationGroupsEntityTree() throws Exception {
		this(true);
		
		updateModel();
		updateView();
	}
	
}