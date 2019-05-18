package view.tabs;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;

import model.entities.Group;
import view.elements.EntityTree;

/**
 * Un onglet affichant la liste des groupes
 * 
 * @author Julien Valverdé
 */
public class GroupsTab extends Tab {
	private static final long serialVersionUID = -4425413103657256174L;
	
	public GroupsTab() throws Exception {
		super(false);
		setName("Groupes");
		
		// Header
		JButton newGroup = new JButton("Nouveau groupe");
			header.add(newGroup, BorderLayout.WEST);
		
		// Contenu
		content.setLayout(new BorderLayout());
		content.add(new JLabel("Double-cliquez sur un groupe pour l'ouvrir dans un nouvel onglet", JLabel.CENTER), BorderLayout.NORTH);
		content.add(new EntityTree("Tous les groupes", Group.factory.getAll(), true), BorderLayout.CENTER);
		
		open();
	}
}