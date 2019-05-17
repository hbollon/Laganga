package view.elements;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTree;

import controller.EntityTreeMouseListener;
import model.entities.Entity;

/**
 * Une version d'EntityTree permettant de séléctionner des éléments dans l'arbre
 * 
 * @author Julien Valverdé
 */
public class SelectableEntityTree extends EntityTree {
	private static final long serialVersionUID = -441239061979776560L;
	
	/*
	 * Attributs
	 */
	private String selectedTreeName; // Nom du noeud père de l'arbre des éléments séléctionnés
	
	private JTree selectedTree = new JTree(); // Arbre des entités sélectionnées
	private List<Entity> selectedList = new ArrayList<Entity>(); // Liste des éléments sélectionnés
	
	
	/*
	 * Getteurs
	 */
	public String getSelectedTreeName() {
		return selectedTreeName;
	}
	public JTree getSelectedTree() {
		return selectedTree;
	}
	public List<Entity> getSelectedList() {
		return selectedList;
	}
	
	
	/*
	 * Constructeur interne
	 */
	protected SelectableEntityTree(String treeName, List<Entity> baseList, boolean isFilterable, String selectedTreeName, boolean callInternalConstructor) {
		super(treeName, baseList, isFilterable, true);
		this.selectedTreeName = selectedTreeName;
		
		selectedTree.addMouseListener(new EntityTreeMouseListener(this, selectedTree, selectedList));
		add(new JScrollPane(selectedTree), BorderLayout.SOUTH);
	}
	
	
	/*
	 * Constructeurs publics
	 */
	public SelectableEntityTree(String treeName, List<Entity> baseList, boolean isFilterable, String selectedEntitiesTreeName) {
		this(treeName, baseList, isFilterable, selectedEntitiesTreeName, true);
		update();
	}
	public SelectableEntityTree(String treeName, List<Entity> baseList, String selectedEntitiesTreeName) {
		this(treeName, baseList, false, selectedEntitiesTreeName);
	}
	
	
	/*
	 * Mise à jour des arbres
	 */
	
	// Mettre à jour les listes et les arbres
	public void update() {
		super.update();
		//applyListToTree(tree, filterList(searchBar.getText()), treeName);
		applyListToTree(selectedTree, selectedList, selectedTreeName);
	}
	
	// Filtrer les entités à afficher en fonction de la recherche
	protected void filterDisplayedList(String search) {
		getDisplayedList().clear();
		
		for (int i = 0; i < getBaseList().size(); i++) {
			Entity entity = getBaseList().get(i);
			
			if (!selectedList.contains(entity) && entity.getTreeDisplayName().toLowerCase().matches(".*"+search.toLowerCase()+".*"))
				getDisplayedList().add(entity);
		}
	}
	
	
	/*
	 * Séléctions des entités
	 */
	
	// Ajouter un élément à l'arbre des entités sélectionnées
	public void setEntitySelected(Entity entity) {
		if (getBaseList().contains(entity) && !selectedList.contains(entity))
			selectedList.add(entity);
	}
	
	// Enlever un élément de l'arbre des entités sélectionnées
	public void setEntityDeselected(Entity entity) {
		if (selectedList.contains(entity))
			selectedList.remove(entity);
	}
	
	
	/*
	 * Callbacks des clics
	 */
	public void onTreeClicked(JTree jTree, List<Entity> entities, Entity entity) {
		if (jTree == getTree()) // Arbre du haut cliqué
			setEntitySelected(entity);
		else if (jTree == selectedTree) // Arbre du bas cliqué
			setEntityDeselected(entity);
		
		update();
	}
}