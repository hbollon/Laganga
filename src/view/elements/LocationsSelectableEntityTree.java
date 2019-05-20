package view.elements;

import model.entities.Location;

/**
 * Un EntityTree permettant de sélectionner un lieu
 * 
 * @author Julien Valverdé
 */
public class LocationsSelectableEntityTree extends SelectableEntityTree {
	private static final long serialVersionUID = 7046397397940249386L;
	
	/*
	 * Constructeur interne
	 */
	protected LocationsSelectableEntityTree(int maxSelectableItems, boolean callInternalConstructor) throws Exception {
		super("Tous les lieux", Location.factory.getAll(), true, "Lieu sélectionné", maxSelectableItems, true);
	}
	
	
	/*
	 * Constructeurs publics
	 */
	public LocationsSelectableEntityTree(int maxSelectableItems) throws Exception {
		this(maxSelectableItems, true);
		updateView();
	}
	public LocationsSelectableEntityTree() throws Exception {
		this(SelectableEntityTree.NO_MAX);
	}
}