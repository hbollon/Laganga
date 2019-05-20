package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.tabs.SupprEventTab;

public class OpenDeleteEvent implements ActionListener {
	public void actionPerformed(ActionEvent arg0) {
		try {
			SupprEventTab supprEventTab = new SupprEventTab();
			supprEventTab.switchTo();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}