package view.tabs;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

/**
 * Un onglet de test pour servir d'exemple
 * 
 * @author Julien Valverdé
 */
public class TestTab extends MainWinTab {
	private static final long serialVersionUID = -618260664434187272L;
	
	public TestTab(JTabbedPane parent) {
		super(parent);
		setName("Onglet de test"); // Nom de l'onglet
		
		// Ajouter un bouton dans la barre d'en-tête
		header.add(new JButton("Modifier"), BorderLayout.WEST);
		
		// Le JPanel content représente le contenu de l'onglet
		content.add(new JLabel("Onglet de test !"));
		
		open(); // Ouverture de l'onglet
	}
}