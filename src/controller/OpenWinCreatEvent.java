package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import view.Mois;
import view.WinCreatEvent;

public class OpenWinCreatEvent implements ActionListener {
	
	public OpenWinCreatEvent() {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			new WinCreatEvent();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
