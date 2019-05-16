package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTabbedPane;
import view.tabs.EventTab;

public class OpenWinCreatEvent implements ActionListener {
	
	private JTabbedPane parent;

	public OpenWinCreatEvent(JTabbedPane parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			EventTab newEventTab = new EventTab(parent /*(Event) Event.factory.getByID(1658)*/); //, (Event) Event.factory.getByID(1658)
			newEventTab.switchTo();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
