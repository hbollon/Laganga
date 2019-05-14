package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.entities.Group;
import view.WinGestionGroup;

public class SupprimerGroupeListener implements ActionListener {

	private WinGestionGroup fenetre;
	private Group cuck;

	public SupprimerGroupeListener(WinGestionGroup fenetre, Group cuck) {
		this.fenetre = fenetre;
		this.cuck = cuck;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			cuck.delete();
			fenetre.dispose();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
