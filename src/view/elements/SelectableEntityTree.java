package view.elements;

import java.awt.GridLayout;
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
	 * Constantes
	 */
	public static final int NO_MAX = -1; // Aucune limite d'éléments sélectionnables
	
	
	/*
	 * Attributs
	 */
	private String selectedTreeName; // Nom du noeud père de l'arbre des éléments séléctionnés
	private int maxSelectableItems; // Nombre maximum d'éléments sélectionnables (-1 pour aucun max)
	
	private JTree selectedTree = new JTree(); // Arbre des entités sélectionnées
	private List<Entity> selectedList = new ArrayList<Entity>(); // Liste des éléments sélectionnés
	
	
	/*
	 * Getteurs
	 */
	public String getSelectedTreeName() {
		return selectedTreeName;
	}
	public int maxSelectableItems() {
		return maxSelectableItems;
	}
	public JTree getSelectedTree() {
		return selectedTree;
	}
	public List<Entity> getSelectedList() {
		return selectedList;
	}
	
	
	/*
	 * Setteurs
	 */
	public void setSelectedList(List<Entity> selectedList) {
		this.selectedList = selectedList;
	}
	
	
	/*
	 * Constructeur interne
	 */
	protected SelectableEntityTree(String treeName, List<Entity> baseList, boolean isFilterable, String selectedTreeName, int maxSelectableItems, boolean callInternalConstructor) {
		super(treeName, baseList, isFilterable, true);
		this.selectedTreeName = selectedTreeName;
		this.maxSelectableItems = maxSelectableItems;
		
		selectedTree.addMouseListener(new EntityTreeMouseListener(this, selectedTree, selectedList));
		
		// Ajout de l'arbre des sélections
		getTreePanel().setLayout(new GridLayout(2, 1));
		getTreePanel().add(new JScrollPane(selectedTree));
	}
	
	
	/*
	 * Constructeurs publics
	 */
	public SelectableEntityTree(String treeName, List<Entity> baseList, boolean isFilterable, String selectedEntitiesTreeName, int maxSelectableItems) {
		this(treeName, baseList, isFilterable, selectedEntitiesTreeName, maxSelectableItems, true);
		
		updateView();
		updateModel();
	}
	public SelectableEntityTree(String treeName, List<Entity> baseList, boolean isFilterable, String selectedEntitiesTreeName) {
		this(treeName, baseList, isFilterable, selectedEntitiesTreeName, -1);
	}
	public SelectableEntityTree(String treeName, List<Entity> baseList, String selectedEntitiesTreeName) {
		this(treeName, baseList, false, selectedEntitiesTreeName);
	}
	
	
	/*
	 * Mise à jour des arbres
	 */
	
	// Mettre à jour les listes et les arbres
	public void updateView() {
		super.updateView();
		
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
	 * Callbacks
	 */
	
	// Mettre à jour les modèles auxquels cette vue est associée
	public void updateModel() {}
	
	public void onTreeClicked(JTree jTree, List<Entity> entities, Entity entity) {
		boolean updated = false; // Définit si le modèle et la vue doivent être mises à jour suite à l'action
		
		if (jTree == getTree()) { // Arbre du haut cliqué
			if (maxSelectableItems == NO_MAX || selectedList.size() < maxSelectableItems) {
				setEntitySelected(entity);
				updated = true;
			}
		}
		else if (jTree == selectedTree) { // Arbre du bas cliqué
			setEntityDeselected(entity);
			updated = true;
		}
		
		if (updated) {
			updateModel();
			updateView();
		}
	}
}