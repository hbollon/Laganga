package view.elements;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
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

import controller.EntityTreeMouseListener;
import controller.EntityTreeSearchBarListener;
import model.entities.Entity;

/**
 * Un JTree spécialisé permettant d'afficher une liste d'entités sous forme d'arbre
 * 
 * @author Julien Valverdé
 */
public class EntityTree extends JPanel {
	private static final long serialVersionUID = 6453784844876350543L;
	
	/*
	 * Attributs
	 */
	private String treeName; // Nom du noeud père de l'arbre des éléments
	private List<Entity> baseList; // Liste originale
	private List<Entity> displayedList = new ArrayList<Entity>(); // Liste filtrée contenant les entités affichées dans l'arbre
	
	private JTextField searchBar; // Barre de recherche
	private JTree tree = new JTree(); // Arbre des entités
	
	
	/*
	 * Getteurs
	 */
	public String getTreeName() {
		return treeName;
	}
	public List<Entity> getBaseList() {
		return baseList;
	}
	public List<Entity> getDisplayedList() {
		return displayedList;
	}
	public JTextField getSearchBar() {
		return searchBar;
	}
	public JTree getTree() {
		return tree;
	}
	
	
	/*
	 * Constructeur interne
	 */
	protected EntityTree(String treeName, List<Entity> baseList, boolean isFilterable, boolean callInternalConstructor) {
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
		
		// Scroll zone du tree
		tree.addMouseListener(new EntityTreeMouseListener(this, tree, displayedList));
		add(new JScrollPane(tree), BorderLayout.CENTER);
	}
	
	
	/*
	 * Constructeurs publics
	 */
	public EntityTree(String treeName, List<Entity> baseList, boolean isFilterable) {
		this(treeName, baseList, isFilterable, true);
		update();
	}
	public EntityTree(String treeName, List<Entity> baseList) {
		this(treeName, baseList, false);
	}
	
	
	/*
	 * Mise à jour de l'arbre
	 */
	
	// Mettre à jour l'affichage
	public void update() {
		filterDisplayedList(searchBar.getText());
		applyListToTree(tree, displayedList, treeName);
	}
	
	// Filtrer les entités à afficher en fonction de la recherche
	protected void filterDisplayedList(String search) {
		displayedList.clear();
		
		for (int i = 0; i < baseList.size(); i++) {
			Entity entity = baseList.get(i);
			
			if (entity.getTreeDisplayName().toLowerCase().matches(".*"+search.toLowerCase()+".*"))
				displayedList.add(entity);
		}
	}
	
	// Appliquer une liste à un JTree
	protected void applyListToTree(JTree tree, List<Entity> list, String name) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(name);
		
		for (int i = 0; i < list.size(); i++)
			node.add(new DefaultMutableTreeNode(list.get(i).getTreeDisplayName()));
		
		tree.setModel(new DefaultTreeModel(node));
	}
	
	
	/*
	 * Méthodes sur les entités
	 */
	
	// Obtenir l'entité située à une certaine ligne
	public Entity getRowEntity(JTree jTree, List<Entity> entities, MouseEvent e) {
		int row = jTree.getRowForLocation(e.getX(), e.getY());
		if (row <= 0 || row > entities.size())
			return null;
		
		return entities.get(row - 1);
	}
	
	
	/*
	 * Callbacks
	 */
	public void onTreeClicked(JTree jTree, List<Entity> entities, Entity entity) {
	}
	public void onTreeDoubleClicked(JTree jTree, List<Entity> entities, Entity entity) {
	}
}