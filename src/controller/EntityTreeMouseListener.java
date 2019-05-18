package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JTree;

import model.entities.Entity;
import view.elements.EntityTree;

/**
 * Permet d'affecter une action aux clics et double-clics sur les éléments d'un EntityTree
 * 
 * @author Julien Valverdé
 */
public class EntityTreeMouseListener extends MouseAdapter {
	/*
	 * Attributs
	 */
	private EntityTree tree;
	private JTree jTree;
	private List<Entity> entities;
	
	
	/*
	 * Constructeur
	 */
	public EntityTreeMouseListener(EntityTree tree, JTree jTree, List<Entity> entities) {
		this.tree = tree;
		this.jTree = jTree;
		this.entities = entities;
	}
	
	
	/*
	 * Actions
	 */
	public void mousePressed(MouseEvent e) {
		Entity entity = tree.getRowEntity(jTree, entities, e);
		
		if (entity != null) {
			switch (e.getClickCount()) {
				case 1:
					tree.onTreeClicked(jTree, entities, entity);
					break;
				
				case 2:
					tree.onTreeDoubleClicked(jTree, entities, entity);
					break;
			}
		}
	}
}