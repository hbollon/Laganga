package view.elements;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import controller.buttons.EntityTreeFilterButton;
import model.entities.Entity;

/**
 * Un EntitySelectionJTree est un JTree spécialisé permettant d'afficher une liste d'entités et d'en séléctionner des éléments
 * 
 * @author Julien Valverdé
 */
public class EntityTree extends JPanel {
	private static final long serialVersionUID = 6453784844876350543L;
	
	private JTree tree = new JTree(); // Tree des entités
	
	private String name; // Nom du noeud père
	private List<Entity> baseList; // Liste originale
	private List<Entity> filteredList = new ArrayList<Entity>(); // Liste filtrée
	
	public EntityTree(String name, List<Entity> baseList) {
		super();
		this.name = name;
		this.baseList = baseList;
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		// Zone de recherche
		JPanel searchPanel = new JPanel();
			searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.LINE_AXIS));
			searchPanel.setPreferredSize(new Dimension(10, 20));
			add(searchPanel);
		
		/*
		JPanel searchPanelContent = new JPanel();
			searchPanelContent.setLayout(new BoxLayout(searchPanelContent, BoxLayout.LINE_AXIS));
			searchPanel.add(searchPanelContent);
		*/
		
		JTextField searchBar = new JTextField();
			searchPanel.add(searchBar);
		
		JButton searchButton = new JButton("Filtrer");
			searchButton.addActionListener(new EntityTreeFilterButton(this, searchBar));
			searchPanel.add(searchButton);
		
		// Initialisation du tree
		filter("");
		
		// Scroll zone du tree
		add(new JScrollPane(tree));
	}
	
	// Générer la liste d'entités filtrée
	private void createFilteredList(String search) {
		filteredList.clear();
		
		for (int i = 0; i < baseList.size(); i++) {
			Entity entity = baseList.get(i);
			
			if (entity.getTreeDisplayName().matches(".*"+search+".*"))
				filteredList.add(entity);
		}
	}
	
	// Appliquer la listre filtrée au JTree
	private void applyTreeModel() {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(name);
		
		for (int i = 0; i < filteredList.size(); i++)
			node.add(new DefaultMutableTreeNode(filteredList.get(i).getTreeDisplayName()));
		
		tree.setModel(new DefaultTreeModel(node));
	}
	
	// Filtrer la liste et appliquer les modifications sur le JTree
	public void filter(String search) {
		createFilteredList(search);
		applyTreeModel();
	}
}