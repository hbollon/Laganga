package view.elements;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;

import model.entities.Entity;
import model.entities.Group;
import view.tabs.GroupTab;

/**
 * L'EntityTree permettant de sélectionner un groupe à afficher
 * 
 * @author Julien Valverdé
 */
public class GroupsEntityTree extends EntityTree {
	private static final long serialVersionUID = 3081810255657032496L;
	
	/*
	 * Constructeur
	 */
	public GroupsEntityTree() throws Exception {
		super("Tous les groupes", new ArrayList<Entity>(), true);
		refresh();
	}
	
	
	/*
	 * Évènements
	 */
	public void onTreeDoubleClicked(JTree jTree, List<Entity> entities, Entity entity) {
		try {
			GroupTab groupTab = new GroupTab((Group) entity);
			groupTab.switchTo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void refresh() {
		try {
			setBaseList(Group.factory.getAll());
			updateView();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}