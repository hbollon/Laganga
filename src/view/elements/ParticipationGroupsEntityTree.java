package view.elements;

import java.util.ArrayList;

import model.entities.Entity;
import model.entities.Group;

/**
 * Arbre permettant de séléctionner les groupes participant à l'évènement en cours de création
 * 
 * @author hbollon
 *
 */

public class ParticipationGroupsEntityTree extends SelectableEntityTree {
	
	private static final long serialVersionUID = 7751283723102681638L;

	protected ParticipationGroupsEntityTree(boolean callInternalConstructor) {
		super("Groupes", new ArrayList<Entity>(), true, "Groupes participant");
		update();
	}
	
	public ParticipationGroupsEntityTree() {
		this(true);
	}
	
	public void update() {
		try {
			setBaseList(Group.factory.getAll());
			updateView();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}