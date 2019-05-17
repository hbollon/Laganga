package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTree;

import view.elements.EntityTree;

/**
 * Permet de détecter les clics/double clics sur un EntityTree
 * 
 * @author Julien Valverdé
 */
public class EntityTreeMouseListener extends MouseAdapter {
	/*
	 * Modes
	 */
	public static final int MODE_SELECT = 0;
	public static final int MODE_DESELECT = 1;
	
	
	/*
	 * Attributs
	 */
	private EntityTree tree;
	private JTree jTree;
	private int mode;
	
	
	/*
	 * Constructeur
	 */
	public EntityTreeMouseListener(EntityTree tree, JTree jTree, int mode) {
		this.tree = tree;
		this.jTree = jTree;
		this.mode = mode;
	}
	
	
	/*
	 * Actions
	 */
	public void mousePressed(MouseEvent e) {
		
	}
}