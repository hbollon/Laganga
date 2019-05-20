package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.entities.Entity;
import model.entities.Notification;

/**
 * Permet d'actualiser la liste des notifications
 * 
 * @author Julien Valverdé
 */
public class NotificationsList extends ArrayList<Notification> {
	private static final long serialVersionUID = 3175062159922737360L;
	
	/*
	 * Instance principale
	 */
	public static NotificationsList notificationsList = new NotificationsList();
	
	
	/*
	 * Méthodes
	 */
	
	// Rafraîchir la liste des notifications
	private static FieldsList refreshQueryFields = new FieldsList();
	static {
		refreshQueryFields.add("time", "DateTime");
		refreshQueryFields.add("target", "model.entities.User");
	}
	public void refresh() throws Exception {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("time", Calendar.getInstance());
		values.put("target", LocalUser.localUser.getUser());
		
		clear();
		List<Entity> list = Notification.factory.get(
				"WHERE `"+Notification.factory.getPrefix()+"time` >= ? AND `"+Notification.factory.getPrefix()+"target` = ?",
				refreshQueryFields,
				values);
		
		for (int i = 0; i < list.size(); i++)
			add((Notification) list.get(i));
	}
}