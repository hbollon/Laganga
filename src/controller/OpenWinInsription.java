package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.WinInscription;

public class OpenWinInsription implements ActionListener {

	public OpenWinInsription() {
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		new WinInscription();
	}

}
