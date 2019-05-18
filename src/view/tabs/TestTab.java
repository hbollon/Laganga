package view.tabs;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.entities.User;
import view.elements.SelectableEntityTree;

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
		JPanel l1 = createLinePanel();
			l1.add(new JLabel("Nom :"));
			l1.add(new JTextField());
			content.add(l1);
		
		JPanel l2 = createLinePanel();
			l2.add(new JLabel("Description :"));
			l2.add(new JTextField());
			content.add(l2);
		
		JPanel l3 = createLinePanel();
			l3.add(new JLabel("Un autre champ inutile :"));
			l3.add(new JTextField());
			content.add(l3);
		
		content.add(new SelectableEntityTree("Utilisateurs", User.factory.getAll(), true, "Utilisateurs sélectionnés"));
		
		open(); // Ouverture de l'onglet
	}
}