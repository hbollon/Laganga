package view.elements;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * La barre de notifications à droite de la fenêtre
 * 
 * @author Julien Valverdé
 */
public class NotificationsBar extends JPanel {
	private static final long serialVersionUID = 3937600731095604851L;
	
	public NotificationsBar() {
		super();
		
		setLayout(new BorderLayout());
		//setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setPreferredSize(new Dimension(200, 720));
		
		// Bouton du haut pour effacer toutes les notifications
		/*
		JPanel topPanel = new JPanel(new BorderLayout());
			add(topPanel, BorderLayout.NORTH);
		*/
		
		JButton clear = new JButton("Tout effacer");
			clear.setPreferredSize(new Dimension(20, 20));
			add(clear, BorderLayout.NORTH);
	}
}