package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.entities.Event;
import view.tabs.EventTab;

public class OpenWinCreatEvent implements ActionListener {
	private Event event;
	
	public OpenWinCreatEvent() {
		this.event = null;
	}
	public OpenWinCreatEvent(Event event) {
		this.event = event;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			EventTab newEventTab;
			if (event != null)
				newEventTab = new EventTab(event);
			else
				newEventTab = new EventTab();
			
			newEventTab.switchTo();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}