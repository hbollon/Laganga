package view.tabs;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;

import controller.OpenCreatGroup;
import view.elements.GroupsEntityTree;

/**
 * Un onglet affichant la liste des groupes
 * 
 * @author Julien Valverdé
 */
public class GroupsTab extends Tab {
	private static final long serialVersionUID = -4425413103657256174L;
	
	private GroupsEntityTree groupsTree = new GroupsEntityTree();
	
	public GroupsTab() throws Exception {
		super(false);
		setName("Groupes");
		
		// Header
		JButton newGroup = new JButton("Nouveau");
			headerWest.add(newGroup, BorderLayout.WEST);
		
		// Contenu
		content.setLayout(new BorderLayout());
		content.add(new JLabel("Double-cliquez sur un groupe pour l'ouvrir", JLabel.CENTER), BorderLayout.NORTH);
		content.add(groupsTree, BorderLayout.CENTER);
		newGroup.addActionListener(new OpenCreatGroup());
		open();
	}
	
	public void refresh() {
		groupsTree.refresh();
	}
}
