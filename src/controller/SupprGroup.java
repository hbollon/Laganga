package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JTree;

import model.entities.Entity;
import view.tabs.SupprGroupTab;

public class SupprGroup implements ActionListener {
	private List<Entity> allGroups;
	private JTree listeGroupes;
	private SupprGroupTab tab;
	
	public SupprGroup(SupprGroupTab tab, List<Entity> allGroups, JTree listeGroupes) {
		this.tab = tab;
		this.allGroups = allGroups;
		this.listeGroupes = listeGroupes;
	}

	public void actionPerformed(ActionEvent arg0) {
		int[] selected = listeGroupes.getSelectionRows();
		
		for (int i = 0; i < selected.length; i++) {
			if (selected[i] > 0) {
				try {
					allGroups.get(selected[i] - 1).delete();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		tab.close();
	}
}
