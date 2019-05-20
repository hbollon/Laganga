package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import model.entities.Entity;
import model.entities.Notification;

/**
 * Permet de récupérer la liste des notifications de l'utilisateur courant
 * 
 * @author Julien Valverdé
 */
@SuppressWarnings("deprecation")
public class NotificationsManager extends Observable {
	/*
	 * Attributs
	 */
	private List<Notification> notifications = new ArrayList<Notification>();
	
	
	/*
	 * Getteurs
	 */
	public List<Notification> getNotifications() {
		return notifications;
	}
	
	
	/*
	 * Méthodes
	 */
	
	// Rafraîchir la liste des notifications
	private static FieldsList refreshQueryFields = new FieldsList();
	static {
		//refreshQueryFields.add("time", "DateTime");
		refreshQueryFields.add("target", "model.entities.User");
	}
	public void refresh() throws Exception {
		notifications.clear();
		
		Map<String, Object> values = new HashMap<String, Object>();
		//values.put("time", Calendar.getInstance());
		values.put("target", LocalUser.localUser.getUser());
		
		/*
		List<Entity> list = Notification.factory.get(
				"WHERE `"+Notification.factory.getPrefix()+"time` >= ? AND `"+Notification.factory.getPrefix()+"target` = ?",
				refreshQueryFields,
				values);
		*/
		List<Entity> list = Notification.factory.get(
				"WHERE `"+Notification.factory.getPrefix()+"target` = ?",
				refreshQueryFields,
				values);
		
		for (int i = 0; i < list.size(); i++)
			notifications.add((Notification) list.get(i));
		
		setChanged();
		notifyObservers();
	}
	
	// Supprimer toutes les notifications
	public void clear() throws SQLException, Exception {
		for (int i = 0; i < notifications.size(); i++)
			notifications.get(i).delete();
		
		refresh();
		
		setChanged();
		notifyObservers();
	}
	
	// Supprimer une notification
	public void clear(Notification notification) throws SQLException, Exception {
		notification.delete();
		refresh();
		
		setChanged();
		notifyObservers();
	}
}