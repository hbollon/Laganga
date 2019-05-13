package view;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import model.Database;

/**
 * Permet de changer le curseur lors d'une opération sur la base de données.
 * 
 * @author Julien Valverdé
 */
@SuppressWarnings("deprecation")
public class CursorChanger implements Observer {
	private static final Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
	private static final Image LOADING_CURSOR_IMAGE = new ImageIcon("./res/loading.gif").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
	private static final Cursor LOADING_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(
			LOADING_CURSOR_IMAGE,
			new Point(25, 25),
			"loading"
			);
	
	private JFrame window;
	
	public CursorChanger(JFrame window) {
		Database.database.addObserver(this);
		this.window = window;
	}
	
	@Override
	public void update(Observable database, Object status) {
		switch ((int) status) {
			case Database.QUERY_STARTED:
				window.setCursor(LOADING_CURSOR);
				break;
			
			case Database.QUERY_FINISHED:
				window.setCursor(DEFAULT_CURSOR);
				break;
		}
	}
}