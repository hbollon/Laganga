package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.tabs.GroupTab;

public class OpenCreatGroup implements ActionListener {
	public void actionPerformed(ActionEvent arg0) {
		try {
			GroupTab groupTab = new GroupTab();
			groupTab.switchTo();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}