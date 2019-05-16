package view.tabs;

import java.awt.BorderLayout;

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
		
		// Le JPanel content représente le contenu de l'onglet
		content.setLayout(new BorderLayout());
		content.add(new JLabel("Onglet de test !"), BorderLayout.CENTER);
		
		open(); // Ouverture de l'onglet
	}
}