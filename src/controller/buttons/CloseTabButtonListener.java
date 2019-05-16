package controller.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.tabs.MainWinTab;

/**
 * Listener du bouton permettant de quitter un onglet
 * 
 * @author Julien Valverdé
 */
public class CloseTabButtonListener implements ActionListener {
	private MainWinTab tab; // Onglet à fermer
	
	public CloseTabButtonListener(MainWinTab tab) {
		this.tab = tab;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		tab.close();
	}
}