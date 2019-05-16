package controller;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import view.elements.EntityTree;

/**
 * Permet de filtrer les éléments d'un EntityTree lorsque l'on tape dans la barre de recherche
 * 
 * @author Julien Valverdé
 */
public class EntityTreeSearchBarListener implements DocumentListener {
	private EntityTree tree;
	
	public EntityTreeSearchBarListener(EntityTree tree) {
		this.tree = tree;
	}
	
	private void update() {
		tree.update();
	}
	
	@Override
	public void changedUpdate(DocumentEvent arg0) {
		update();
	}
	
	@Override
	public void insertUpdate(DocumentEvent arg0) {
		update();
	}
	
	@Override
	public void removeUpdate(DocumentEvent arg0) {
		update();
	}
}