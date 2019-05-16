package controller.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import view.elements.EntityTree;

/**
 * Il s'agit du bouton utilisé dans les EntityTree pour filtrer l'arbre
 * 
 * @author Julien Valverdé
 */
public class EntityTreeFilterButton implements ActionListener {
	private EntityTree tree;
	private JTextField field;
	
	public EntityTreeFilterButton(EntityTree tree, JTextField field) {
		this.tree = tree;
		this.field = field;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		tree.filter(field.getText());
	}
}