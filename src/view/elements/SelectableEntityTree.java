package view.elements;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTree;

import model.entities.Entity;

/**
 * Une version d'EntityTree permettant de séléctionner des éléments dans l'arbre
 * 
 * @author Julien Valverdé
 */
public class SelectableEntityTree extends EntityTree {
	private static final long serialVersionUID = -441239061979776560L;
	
	protected JTree selectedEntitiesTree = new JTree(); // Arbre des entités sélectionnées
	protected String selectedEntitiesTreeName; // Nom du noeud père de l'arbre des éléments séléctionnés
	protected List<Entity> selectedEntitiesList = new ArrayList<Entity>(); // Liste des éléments sélectionnés
	
	
	/*
	 * Constructeurs
	 */
	public SelectableEntityTree(String treeName, List<Entity> baseList, boolean isFilterable, String selectedEntitiesTreeName) {
		super(treeName, baseList, isFilterable);
		this.selectedEntitiesTreeName = selectedEntitiesTreeName;
		
		add(new JScrollPane(selectedEntitiesTree), BorderLayout.SOUTH);
		
		// Initialisation du tree
		//update();
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
		applyListToTree(selectedEntitiesTree, selectedEntitiesList, selectedEntitiesTreeName);
	}
	
	// Filtrer les entités à afficher en fonction de la recherche
	/*
	protected List<Entity> filterList(String search) {
		List<Entity> filteredList = new ArrayList<Entity>();
		
		for (int i = 0; i < baseList.size(); i++) {
			Entity entity = baseList.get(i);
			
			System.out.println(selectedEntitiesList);
			if (!selectedEntitiesList.contains(entity) && entity.getTreeDisplayName().toLowerCase().matches(".*"+search.toLowerCase()+".*"))
				filteredList.add(entity);
		}
		
		return filteredList;
	}
	*/
	
	
	/*
	 * Séléctions des entités
	 */
	
	// Ajouter un élément à l'arbre des entités sélectionnées
	public void setEntitySelected(Entity entity) {
		if (baseList.contains(entity) && !selectedEntitiesList.contains(entity))
			selectedEntitiesList.add(entity);
	}
	
	// Enlever un élément de l'arbre des entités sélectionnées
	public void setEntityDeselected(Entity entity) {
		selectedEntitiesList.remove(entity);
	}
}