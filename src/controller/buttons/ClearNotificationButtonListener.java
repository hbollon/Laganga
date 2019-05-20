package controller.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.NotificationsManager;
import model.entities.Notification;

/**
 * Appelé lorsque le bouton pour nettoyer la/les notification(s) est appelé
 * 
 * @author Julien Valverdé
 */
public class ClearNotificationButtonListener implements ActionListener {
	private NotificationsManager notificationsManager; // Modèle
	private Notification notification; // Notification à supprimer (si null, supprimer toutes les notifications)
	
	public ClearNotificationButtonListener(NotificationsManager notificationsManager, Notification notification) {
		this.notificationsManager = notificationsManager;
		this.notification = notification;
	}
	public ClearNotificationButtonListener(NotificationsManager notificationsManager) {
		this(notificationsManager, null);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			if (notification != null)
				notificationsManager.clear(notification);
			else
				notificationsManager.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}