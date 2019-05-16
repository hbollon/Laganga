package controller.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JTree;

import view.MainWin;


public class RechercherListener implements ActionListener {
	
	private MainWin fenetre;
	private JTree arbre;
	private JTextField recherche;

	public RechercherListener(MainWin fenetre, JTree arbre, JTextField recherche) {
		this.fenetre = fenetre;
		this.arbre = arbre;
		this.recherche = recherche;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}
}