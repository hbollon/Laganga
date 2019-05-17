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
 * Un JTree spécialisé permettant d'afficher une liste d'entités sous forme d'arbre
 * 
 * @author Julien Valverdé
 */
public class EntityTree extends JPanel {
	private static final long serialVersionUID = 6453784844876350543L;
	
	protected JTree tree = new JTree(); // Arbre des entités
	protected String treeName; // Nom du noeud père de l'arbre des éléments
	protected List<Entity> baseList; // Liste originale
	
	protected JTextField searchBar; // Barre de recherche
	
	
	/*
	 * Constructeurs
	 */
	public EntityTree(String treeName, List<Entity> baseList, boolean isFilterable) {
		super();
		this.treeName = treeName;
		this.baseList = baseList;
		
		setLayout(new BorderLayout());
		
		// Zone de recherche
		JPanel searchPanel = new JPanel(new BorderLayout());
			searchPanel.setBorder(new EmptyBorder(2, 2, 2, 2));
			if (isFilterable)
				add(searchPanel, BorderLayout.NORTH);
		
		JLabel searchLabel = new JLabel("Filtrer : ");
			searchPanel.add(searchLabel, BorderLayout.WEST);
		
		JTextField searchBar = new JTextField();
			searchBar.getDocument().addDocumentListener(new EntityTreeSearchBarListener(this));
			searchPanel.add(searchBar, BorderLayout.CENTER);
			this.searchBar = searchBar;
		
		// Initialisation du tree
		update();
		
		// Scroll zone du tree
		add(new JScrollPane(tree), BorderLayout.CENTER);
	}
	public EntityTree(String treeName, List<Entity> baseList) {
		this(treeName, baseList, false);
	}
	
	
	/*
	 * Mise à jour de l'arbre
	 */
	
	// Mettre à jour la liste et l'arbre
	public void update() {
		applyListToTree(tree, filterList(searchBar.getText()), treeName);
	}
	
	// Filtrer les entités à afficher en fonction de la recherche
	protected List<Entity> filterList(String search) {
		List<Entity> filteredList = new ArrayList<Entity>();
		
		for (int i = 0; i < baseList.size(); i++) {
			Entity entity = baseList.get(i);
			
			if (entity.getTreeDisplayName().toLowerCase().matches(".*"+search.toLowerCase()+".*"))
				filteredList.add(entity);
		}
		
		return filteredList;
	}
	
	// Appliquer une liste à un JTree
	protected void applyListToTree(JTree tree, List<Entity> list, String name) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(name);
		
		for (int i = 0; i < list.size(); i++)
			node.add(new DefaultMutableTreeNode(list.get(i).getTreeDisplayName()));
		
		tree.setModel(new DefaultTreeModel(node));
	}
}