package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JTree;

import model.entities.Group;
import model.entities.User;

public class SupprimerMembreListener implements ActionListener {
	private Group group;
	private JTree liste;
	private List<User> members;
	
	public SupprimerMembreListener(Group group, JTree liste, List<User> members) {
		this.group = group;
		this.liste = liste;
		this.members = members;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int[] selections = liste.getSelectionRows();
		
		for (int i = 0; i < selections.length; i++) {
			int selection = selections[i] - 1;
			
			if (selection >= 0 && selection < members.size()) {
				try {
					group.removeMember(members.get(selection));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
