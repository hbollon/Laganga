package view.elements;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.buttons.ClearNotificationButtonListener;
import model.NotificationsManager;
import model.entities.Notification;

/**
 * La barre de notifications à droite de la fenêtre
 * 
 * @author Julien Valverdé
 */
@SuppressWarnings("deprecation")
public class NotificationsBar extends JPanel implements Observer {
	private static final long serialVersionUID = 3937600731095604851L;
	
	/*
	 * Constantes
	 */
	private static final int WIDTH = 250; // Largeur du panneau
	
	
	/*
	 * Attributs
	 */
	private NotificationsManager notificationsManager; // Modèle de la gestion des notifications
	private JPanel notificationsPanel; // Panneau contenant toutes les notifications
	
	
	/*
	 * Constructeur
	 */
	public NotificationsBar() {
		super();
		
		// Création du modèle
		notificationsManager = new NotificationsManager();
			notificationsManager.addObserver(this);
		
		// Mise en forme du panel
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(WIDTH, 720));
		
		// Bouton du haut pour effacer toutes les notifications
		JPanel clearPanel = new JPanel(new FlowLayout());
			add(clearPanel, BorderLayout.NORTH);
		
		JButton clear = new JButton("Tout effacer");
			clear.addActionListener(new ClearNotificationButtonListener(notificationsManager));
			clearPanel.add(clear);
		
		// Panel de la des notifications
		JPanel notificationsPanel = new JPanel();
			notificationsPanel.setLayout(new BoxLayout(notificationsPanel, BoxLayout.PAGE_AXIS));
			add(notificationsPanel, BorderLayout.CENTER);
			this.notificationsPanel = notificationsPanel;
		
		// Rafraîchir le modèle
		try {
			notificationsManager.refresh();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	
	/*
	 * Méthodes
	 */
	
	// Ajouter une notification
	private void addNotification(Notification notification) {
		// Panneau de la notification
		JPanel notifPanel = new JPanel();
			notifPanel.setLayout(new BoxLayout(notifPanel, BoxLayout.LINE_AXIS));
			notifPanel.setPreferredSize(new Dimension(WIDTH, 25));
			notifPanel.setBorder(new EmptyBorder(0, 2, 10, 2));
		notificationsPanel.add(notifPanel);
		
		// Contenu de la notification
		notifPanel.add(new JLabel("<html><h5>"+notification.getTitle()+"<br />"+notification.getTime().getTime()+"</h5>"+notification.getDescription()+"</html>"));
		
		JButton delete = new JButton("X");
			notifPanel.add(delete);
	}
	
	
	/*
	 * Méthodes d'observer
	 */
	
	// Mise à jour du modèle
	@Override
	public void update(Observable arg0, Object arg1) {
		notificationsPanel.removeAll();
		notificationsPanel.updateUI();
		
		List<Notification> list = notificationsManager.getNotifications();
		
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++)
				addNotification(list.get(i));
		}
		else
			notificationsPanel.add(new JLabel("Les notifications apparaîtront ici", JLabel.CENTER));
	}
}