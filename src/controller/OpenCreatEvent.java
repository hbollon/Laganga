package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.tabs.EventTab;

public class OpenCreatEvent implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			EventTab newEventTab = new EventTab(); //, (Event) Event.factory.getByID(1658)
			newEventTab.switchTo();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}