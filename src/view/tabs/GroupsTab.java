package view.tabs;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;

import view.elements.GroupsEntityTree;

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
		JButton newGroup = new JButton("Nouveau");
			headerWest.add(newGroup, BorderLayout.WEST);
		
		// Contenu
		content.setLayout(new BorderLayout());
		content.add(new JLabel("Double-cliquez sur un groupe pour l'ouvrir", JLabel.CENTER), BorderLayout.NORTH);
		content.add(new GroupsEntityTree(), BorderLayout.CENTER);
		
		open();
	}
}