package view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;

import com.toedter.calendar.JCalendar;

import controller.OpenOngletSupprGroup;
import controller.OpenCreatEvent;
import model.entities.Entity;
import model.entities.Group;
import model.entities.User;

import com.mindfusion.scheduling.*;

/**
 * Classe MainWin, fenetre principale de notre programme
 * 
 * 
 * @author hbollon
 *
 */

public class MainWin extends JFrame { 
	private static final long serialVersionUID = 1L;
	
	private JPanel windowPanel = null;
	private JPanel centerPanel = null;
	private JPanel leftPanel = null;
	private JPanel rightPanel = null;
	private JTree jTree = null;
	private JTree jTreeGroupe = null;
	private JScrollPane jScrollTree = null;
	private JScrollPane jScrollTreeGroup = null;
	private JPanel jPanelTree = null;
	private JPanel jPanelTreeGroup = null;
	private JTextArea notificationBar = null;
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
	
	public static MainWinCalendar tabbedPane;
	
	private JTree getJTree() {
		if (jTree == null) {
			jTree = new JTree();
		}
		return jTree;
	}
	
	private JTree getJTreeGroupe() {
		if (jTreeGroupe == null) {
			jTreeGroupe = new JTree();
		}
		return jTreeGroupe;
	} 
	
	public MainWin() throws Exception 
	{
		super();
		initialize();
		setTree();
		setTreeGroupe();
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
	    
	    // Permet de changer le curseur lors d'une opération avec la BDD
	    new CursorChanger(this);
	}
	
	public void setTree() throws Exception
	{
		List<Entity> usersList = User.factory.getAll();
		List<String> usersNames = new ArrayList<String>();
		
		javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Utilisateurs");
		  jTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
		  jScrollTree.setViewportView(jTree);
		  
		for (int i = 0; i < usersList.size(); i++) {
			usersNames.add(((String) ((User) usersList.get(i)).getFirstName()) + " " + ((String) ((User) usersList.get(i)).getLastName()));
			javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode(usersNames.get(i));
		    treeNode1.add(treeNode2);
		}
	}
	
	public void setTreeGroupe() throws Exception
	{
		List<Entity> groupeList = Group.factory.getAll();
		List<String> groupeNames = new ArrayList<String>();
		
		javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Groupes");
		  jTreeGroupe.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
		  jScrollTreeGroup.setViewportView(jTreeGroupe);
		  
		for (int i = 0; i < groupeList.size(); i++) {
			groupeNames.add(((Group) groupeList.get(i)).getName());
			javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode(groupeNames.get(i));
		    treeNode1.add(treeNode2);
		}
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
	
	private JScrollPane getJScrollPane()
	{
		if(jScrollTree == null)
		{
			jScrollTree = new JScrollPane();
			jScrollTree.setViewportView(getJTree());
			jScrollTree.setPreferredSize(new Dimension(200,450));
		}
		return jScrollTree;
	}
	
	private JScrollPane getJScrollPaneGroup()
	{
		if(jScrollTreeGroup == null)
		{
			jScrollTreeGroup = new JScrollPane();
			jScrollTreeGroup.setViewportView(getJTreeGroupe());
			jScrollTreeGroup.setPreferredSize(new Dimension(200,450));
		}
		return jScrollTreeGroup;
	}
	
	private JPanel getJPanelTree() {
		
		// Création des barres de recherche (zone de texte + bouton)
		JPanel barreRechercheUsers = new JPanel(new FlowLayout());
		JPanel barreRechercheGroupe = new JPanel(new FlowLayout());
		
		// Création des zones de texte
		JTextField texteUsers = new JTextField();
		JTextField texteGroupe = new JTextField();
		
		// Création des boutons
		JButton searchUsers = new JButton("Rechercher");
		JButton searchGroupe = new JButton("Rechercher");
		
		// Changement des tailles afin de s'adapter à la fenêtre
		texteUsers.setPreferredSize(new Dimension(150, 25));
		texteGroupe.setPreferredSize(new Dimension(150, 25));
		
		// On ajoute les composants des barres de recherche
		barreRechercheUsers.add(texteUsers);
		barreRechercheUsers.add(searchUsers);
		barreRechercheGroupe.add(texteGroupe);
		barreRechercheGroupe.add(searchGroupe);
		
		// On ajoute les barres de recherche complétées au nord des panels Users et Groupe
		JPanel ensemble1 = new JPanel(new BorderLayout());
		ensemble1.add(barreRechercheUsers, BorderLayout.NORTH);
		
		JPanel ensemble2 = new JPanel(new BorderLayout());
		ensemble2.add(barreRechercheGroupe, BorderLayout.NORTH);
		
		if (jPanelTree == null) {
			jPanelTree = new JPanel(new GridLayout(2, 1));
			
			// On ajoute les deux panels dans le GridLayout pour les mettre l'un sous l'autre
			ensemble1.add(getJScrollPane(), BorderLayout.CENTER);
			jPanelTree.add(ensemble1);
			ensemble2.add(getJScrollPaneGroup(), BorderLayout.CENTER);
			jPanelTree.add(ensemble2);
		}
		
		return jPanelTree;
	}
	
	private JTextArea getNotificationBar()
	{
		if(notificationBar == null)
		{
			notificationBar = new JTextArea();
			notificationBar.setPreferredSize(new Dimension(200, 720));
			notificationBar.setEditable(false);
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
		editGroupe = new JMenuItem("Modifier un groupe existant");
		settings = new JMenuItem("Options");
		aide = new JMenuItem("Aide");
		credit = new JMenuItem("A propos");
		
		fichier.add(createEvent);
		fichier.add(deleteEvent);
		
		edition.add(createGroupe);
		edition.add(delateGroupe);
		edition.add(editGroupe);
		
		option.add(settings);
		
		help.add(aide);
		help.add(credit);
		
		barMenu.add(fichier);
		barMenu.add(edition);
		barMenu.add(option);
		barMenu.add(help);
		
		createEvent.addActionListener(new OpenCreatEvent());
		delateGroupe.addActionListener(new OpenOngletSupprGroup());
		
		return barMenu;
	}
	
	public static void callAddEvent(String name, String desc, int priority, GregorianCalendar dateBegin, GregorianCalendar dateEnd, int timeHourBegin, int timeMinuteBegin, int timeHourEnd, int timeMinuteEnd, boolean hide)
	{
		tabbedPane.getCalendarP().addEventBD(name, desc, priority, dateBegin, dateEnd, timeHourBegin, timeMinuteBegin, timeHourEnd, timeMinuteEnd, hide);
	}

}