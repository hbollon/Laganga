package view.tabs;

import java.awt.BorderLayout;

import javax.swing.JButton;

import model.entities.User;
import view.elements.EntityTree;

/**
 * Un onglet de test pour servir d'exemple
 * 
 * @author Julien Valverdé
 */
public class TestTab extends Tab {
	private static final long serialVersionUID = -618260664434187272L;
	
	public TestTab() throws Exception {
		super();
		setName("Onglet de test"); // Nom de l'onglet
		
		// Ajouter un bouton dans la barre d'en-tête
		header.add(new JButton("Modifier"), BorderLayout.WEST);
		
		// Le JPanel content représente le contenu de l'onglet
		content.add(new EntityTree("Utilisateurs", User.factory.getAll(), true));
		
		open(); // Ouverture de l'onglet
	}
}