package view.elements;

import java.util.List;

import javax.swing.JTree;

import model.entities.Entity;
import model.entities.Group;
import view.WinGestionGroup;

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
		super("Tous les groupes", Group.factory.getAll(), true);
		updateView();
	}
	
	
	/*
	 * Évènements
	 */
	public void onTreeDoubleClicked(JTree jTree, List<Entity> entities, Entity entity) {
		new WinGestionGroup((Group) entity);
	}
}