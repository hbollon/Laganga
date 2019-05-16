package view.elements;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import controller.EntityTreeSearchBarListener;
import model.entities.Entity;

/**
 * Un EntitySelectionJTree est un JTree spécialisé permettant d'afficher une liste d'entités et d'en séléctionner des éléments
 * 
 * @author Julien Valverdé
 */
public class EntityTree extends JPanel {
	private static final long serialVersionUID = 6453784844876350543L;
	
	private JTextField searchBar; // Barre de recherche
	
	private JTree topTree = new JTree(); // Arbre des entités
	private JTree bottomTree = new JTree(); // Arbre des entités sélectionnées
	
	private String name; // Nom du noeud père
	private boolean selectionEnabled; // Autorisation de la sélection d'éléments
	
	private List<Entity> baseList; // Liste originale
	private List<Entity> selectedList = new ArrayList<Entity>(); // Liste des entités sélectionnées
	
	public EntityTree(String name, List<Entity> baseList) {
		this(name, baseList, false);
	}
	public EntityTree(String name, List<Entity> baseList, boolean selectionEnabled) {
		super();
		this.name = name;
		this.baseList = baseList;
		this.selectionEnabled = selectionEnabled;
		
		setLayout(new BorderLayout());
		
		// Zone de recherche
		JPanel searchPanel = new JPanel(new BorderLayout());
			searchPanel.setBorder(new EmptyBorder(2, 2, 2, 2));
			add(searchPanel, BorderLayout.NORTH);
		
		JLabel searchLabel = new JLabel("Filtrer : ");
			searchPanel.add(searchLabel, BorderLayout.WEST);
		
		JTextField searchBar = new JTextField();
			searchBar.getDocument().addDocumentListener(new EntityTreeSearchBarListener(this));
			searchPanel.add(searchBar, BorderLayout.CENTER);
			this.searchBar = searchBar;
		
		// Initialisation des tree
		update();
		
		// Scroll zones des tree
		add(new JScrollPane(topTree), BorderLayout.CENTER);
		
		if (selectionEnabled)
			add(new JScrollPane(bottomTree), BorderLayout.SOUTH);
	}
	
	// Mettre à jour les listes et les arbres
	public void update() {
		List<Entity> filteredList = filterList(searchBar.getText());
		applyListToTree(topTree, filteredList, name);
	}
	
	
	/*
	 * Filtrage de l'arbre du haut
	 */
	
	// Filtrer la liste du haut en fonction de la recherche
	private List<Entity> filterList(String search) {
		List<Entity> filteredList = new ArrayList<Entity>();
		
		for (int i = 0; i < baseList.size(); i++) {
			Entity entity = baseList.get(i);
			
			if (!selectedList.contains(entity) && entity.getTreeDisplayName().toLowerCase().matches(".*"+search.toLowerCase()+".*"))
				filteredList.add(entity);
		}
		
		return filteredList;
	}
	
	// Appliquer une liste à un JTree
	private void applyListToTree(JTree tree, List<Entity> list, String name) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(name);
		
		for (int i = 0; i < list.size(); i++)
			node.add(new DefaultMutableTreeNode(list.get(i).getTreeDisplayName()));
		
		tree.setModel(new DefaultTreeModel(node));
	}
	
	
	/*
	 * Séléctions des entités
	 */
	
	// Ajouter un élément à l'arbre des entités sélectionnées
	public void setEntitySelected(Entity entity) {
		if (selectionEnabled && baseList.contains(entity) && !selectedList.contains(entity))
			selectedList.add(entity);
	}
	
	// Enlever un élément de l'arbre des entités sélectionnées
	public void setEntityDeselected(Entity entity) {
		if (selectionEnabled)
			selectedList.remove(entity);
	}
}