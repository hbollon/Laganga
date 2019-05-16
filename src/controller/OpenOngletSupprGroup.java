package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.tabs.SupprGroupTab;

public class OpenOngletSupprGroup implements ActionListener {
	public void actionPerformed(ActionEvent arg0) {
		try {
			SupprGroupTab supprGroupTab = new SupprGroupTab();
			supprGroupTab.switchTo();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}