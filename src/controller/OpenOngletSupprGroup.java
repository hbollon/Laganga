package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTabbedPane;

import view.MainWinCalendar;
import view.tabs.EventTab;
import view.tabs.SupprGroupTab;

public class OpenOngletSupprGroup implements ActionListener {
	private JTabbedPane parent;
	
	public OpenOngletSupprGroup(JTabbedPane parent) {
		this.parent = parent;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		try {
			SupprGroupTab supprGroupTab = new SupprGroupTab(parent);
			supprGroupTab.switchTo();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
