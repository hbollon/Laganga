package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.entities.Group;
import view.WinGestionGroup;

public class SupprimerGroupeListener implements ActionListener {

	private WinGestionGroup fenetre;
	private Group groupe;

	public SupprimerGroupeListener(WinGestionGroup fenetre, Group groupe) {
		this.fenetre = fenetre;
		this.groupe = groupe;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			groupe.delete();
			fenetre.dispose();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
