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
	 * Constructeur
	 */
	public LocationsSelectableEntityTree() throws Exception {
		super("Tous les lieux", Location.factory.getAll(), true, "Lieu sélectionné", 1, true);
		updateView();
	}
}