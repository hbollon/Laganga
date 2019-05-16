package view.elements;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import model.entities.Entity;

/**
 * Un EntitySelectionJTree est un JTree spécialisé permettant d'afficher une liste d'entités et d'en séléctionner des éléments
 * 
 * @author Julien Valverdé
 */
public class EntityTree extends JPanel {
	private static final long serialVersionUID = 6453784844876350543L;
	
	public EntityTree(String name, List<Entity> entities, boolean displaySearchField) {
		super();
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	}
}