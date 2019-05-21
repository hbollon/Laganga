package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import controller.OpenCreatEvent;
import controller.OpenCreatGroup;
import controller.OpenDeleteEvent;
import controller.OpenOngletSupprGroup;
import model.Agenda;
import model.entities.Entity;
import model.entities.Location;
import model.entities.User;
import view.elements.AgendaGroupsEntityTree;
import view.elements.AgendaUsersEntityTree;
import view.elements.NotificationsBar;
import view.tabs.GroupsTab;

/**
 * Classe MainWin, fenetre principale de notre programme
 * 
 * 
 * @author hbollon
 *
 */

public class MainWin extends JFrame { 
	private static final long serialVersionUID = 1L;
	
	public static MainWin mainWin;
	
	private JPanel windowPanel = null;
	private JPanel centerPanel = null;
	private JPanel leftPanel = null;
	private JPanel rightPanel = null;
	private JPanel jPanelTree = null;
	private NotificationsBar notificationBar = null;
	private JMenuBar barMenu = null;
	private JMenu fichier = null;
	private JMenu edition = null;
	private JMenu option = null;
	private JMenu help = null;
	private JMenuItem createEvent = null;
	private JMenuItem deleteEvent = null;
	private JMenuItem createGroupe = null;
	private JMenuItem delateGroupe = null;
	private JMenuItem editGroupe = null;
	private JMenuItem settings = null;
	private JMenuItem aide = null;
	private JMenuItem credit = null;
	private AgendaUsersEntityTree usersTree;
	private AgendaGroupsEntityTree groupsTree;
	private GroupsTab groupsTab;
	
	public static MainWinCalendar tabbedPane;
	
	public MainWin() throws Exception 
	{
		super();
		mainWin = this;
		initialize();
	}
	
	public void initialize()
	{
		tabbedPane = new MainWinCalendar(this);
		this.setTitle("Laganga");
	    this.setSize(1280, 720);
	    this.setJMenuBar(getMenu());
	    ImageIcon img = new ImageIcon("./icon.png");
	    this.setIconImage(img.getImage());
		this.setContentPane(getWindowPane());
	    this.getContentPane().setBackground(Color.white);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    this.setVisible(true);
	    
	    // Ajout des onglets
	    try {
			groupsTab = new GroupsTab();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    // Permet de changer le curseur lors d'une opération avec la BDD
	    new CursorChanger(this);
	}
	
	private JPanel getWindowPane()
	{
		if(windowPanel == null)
		{
			windowPanel = new JPanel(new BorderLayout());
			centerPanel = new JPanel(new BorderLayout());
			leftPanel = getJPanelTree();
			rightPanel = new JPanel(new BorderLayout());
			
			rightPanel.add(getNotificationBar(), BorderLayout.EAST);
			centerPanel.add(tabbedPane);
			windowPanel.add(leftPanel, BorderLayout.WEST);
			windowPanel.add(centerPanel, BorderLayout.CENTER);
			windowPanel.add(rightPanel, BorderLayout.EAST);
		}
		return windowPanel;
	}
	
	private JPanel getJPanelTree() {
		if (jPanelTree == null) {
			jPanelTree = new JPanel(new GridLayout(2, 1));
			
			try {
				usersTree = new AgendaUsersEntityTree();
				groupsTree = new AgendaGroupsEntityTree();
				jPanelTree.add(usersTree);
				jPanelTree.add(groupsTree);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return jPanelTree;
	}
	
	private NotificationsBar getNotificationBar()
	{
		if(notificationBar == null)
		{
			notificationBar = new NotificationsBar();
		}
		
		return notificationBar;
	}
	
	private JMenuBar getMenu()
	{
		barMenu = new JMenuBar();
		fichier = new JMenu("Evénements");
		edition = new JMenu("Groupes");
		option = new JMenu("Options");
		help = new JMenu("Help");
		createEvent = new JMenuItem("Créer un nouvel évènement");
		deleteEvent = new JMenuItem("Supprimer un évènement");
		createGroupe = new JMenuItem("Créer un nouveau groupe");
		delateGroupe = new JMenuItem("Supprimer un groupe");
		settings = new JMenuItem("Options");
		aide = new JMenuItem("Aide");
		credit = new JMenuItem("A propos");
		
		fichier.add(createEvent);
		fichier.add(deleteEvent);
		
		edition.add(createGroupe);
		edition.add(delateGroupe);
		
		option.add(settings);
		
		help.add(aide);
		help.add(credit);
		
		barMenu.add(fichier);
		barMenu.add(edition);
		barMenu.add(option);
		barMenu.add(help);
		
		createEvent.addActionListener(new OpenCreatEvent());
		deleteEvent.addActionListener(new OpenDeleteEvent());
		createGroupe.addActionListener(new OpenCreatGroup());
		delateGroupe.addActionListener(new OpenOngletSupprGroup());
		
		return barMenu;
	}
	
	public static void callAddEvent(String name, String desc, int priority, User author, GregorianCalendar dateBegin, GregorianCalendar dateEnd, boolean hide, Location location, List<Entity> users, List<Entity> groups)
	{
		tabbedPane.getMoisP().getCalendar().addEventBD(name, desc, priority, author, dateBegin, dateEnd, hide, groups, users, location);
		tabbedPane.getSemaineP().getCalendar().addEventCalendar(name, desc, priority, dateBegin, dateEnd, hide);
	}
	
	public void refresh() {
		try {
			usersTree.refreshBaseList(); // Mise à jour de la liste des utilisateurs
			groupsTree.refreshBaseList(); // Mise à jour de la liste des groupes
			groupsTab.refresh();
			Agenda.agenda.refresh(); // Mise à jour des évènements
			notificationBar.getNotificationsManager().refresh(); // Mise à jour des notifications
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}