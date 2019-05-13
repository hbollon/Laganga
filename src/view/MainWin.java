package view;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.tree.*;

import com.toedter.calendar.JCalendar;

import controller.OpenWinCreatEvent;
import model.entities.Entity;
import model.entities.Group;
import model.entities.User;

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
	private JTextArea notificationBar = null;
	private JMenuBar barMenu = null;
	private JMenu fichier = null;
	private JMenu edition = null;
	private JMenu option = null;
	private JMenu help = null;
	private JMenuItem createEvent = null;
	private JMenuItem deleteEvent = null;
	private JMenuItem settings = null;
	private JMenuItem close = null;
	private JMenuItem aide = null;
	private JMenuItem credit = null;
	
	private static MainWinCalendar winCalendar;
	
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
		winCalendar = new MainWinCalendar(this);
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
		
		javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Liste Users");
		  jTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
		  jScrollTree.setViewportView(jTree);
		  
		for (int i = 0; i < usersList.size(); i++) {
			usersNames.add(((String) ((User) usersList.get(i)).getFirstName()) + " " + ((String) ((User) usersList.get(i)).getLastName()));
			javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode(usersNames.get(i));
		    treeNode1.add(treeNode2);
		}
		
		TreeModel model = jTree.getModel();
		Object[] getNode = 
		TreePath tPath = new TreePath(getNode);
		jTree.setSelectionPath(tPath);
	}
	
	public void setTreeGroupe() throws Exception
	{
		List<Entity> groupeList = Group.factory.getAll();
		List<String> groupeNames = new ArrayList<String>();
		
		javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Liste Groupe");
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
			leftPanel = new JPanel(new GridLayout(2, 1));
			rightPanel = new JPanel(new BorderLayout());
			
			leftPanel.add(getJScrollPaneGroup());
			leftPanel.add(getJScrollPane());
			rightPanel.add(getNotificationBar(), BorderLayout.EAST);
			centerPanel.add(winCalendar);
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
		fichier = new JMenu("Fichier");
		edition = new JMenu("Edition");
		option = new JMenu("Options");
		help = new JMenu("Help");
		createEvent = new JMenuItem("Créer un nouvel évènement");
		deleteEvent = new JMenuItem("Supprimer un évènement");
		settings = new JMenuItem("Options");
		close = new JMenuItem("Quitter");
		aide = new JMenuItem("Aide");
		credit = new JMenuItem("A propos");
		
		fichier.add(createEvent);
		fichier.add(deleteEvent);
		fichier.add(close);
		
		option.add(settings);
		
		help.add(aide);
		help.add(credit);
		
		barMenu.add(fichier);
		barMenu.add(edition);
		barMenu.add(option);
		barMenu.add(help);
		
		createEvent.addActionListener(new OpenWinCreatEvent());
		
		return barMenu;
	}
	
	public static void callAddEvent(String name, String desc, JCalendar dateBegin, JCalendar dateEnd, int timeHourBegin, int timeMinuteBegin, int timeHourEnd, int timeMinuteEnd)
	{
		winCalendar.getCalendarP().addEventMois(name, desc, dateBegin, dateEnd, timeHourBegin, timeMinuteBegin, timeHourEnd, timeMinuteEnd);
	}
}