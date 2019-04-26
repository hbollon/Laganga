package view;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import model.Entity;
import model.Group;
import model.User;

import controller.OpenWinCreatEvent;

public class MainWin extends JFrame { 
	private static final long serialVersionUID = 1L;
	
	private JPanel windowPanel = null;
	private JPanel centerPanel = null;
	private JPanel leftPanel = null;
	private JPanel rightPanel = null;
	private JPanel jContentPane = null;
	private JPanel jContentPaneGroup = null;
	private JTree jTree = null;
	private JTree jTreeGroupe = null;
	private JScrollPane jScrollTree = null;
	private JScrollPane jScrollTreeGroup = null;
	private JTextArea notificationBar = null;
	private JMenuBar barMenu = null;
	private JMenu fichier = null;
	private JMenu edition = null;
	private JMenu option = null;
	private JMenu aPropos = null;
	private JMenuItem createEvent = null;
	private JMenuItem deleteEvent = null;
	private JMenuItem settings = null;
	private JMenuItem close = null;
	private JMenuItem aide = null;
	private JMenuItem credit = null;
	
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
		ArrayList<Entity> usersList = User.factory.getAll();
		ArrayList<String> usersNames = new ArrayList<String>();
		
		javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Liste Users");
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
		ArrayList<Entity> groupeList = Group.factory.getAll();
		ArrayList<String> groupeNames = new ArrayList<String>();
		
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
			leftPanel = new JPanel(new BorderLayout());
			rightPanel = new JPanel(new BorderLayout());
			
			leftPanel.add(getJContentPane(), BorderLayout.SOUTH);
			leftPanel.add(getJContentPaneGroupe(), BorderLayout.NORTH);
			rightPanel.add(getNotificationBar(), BorderLayout.EAST);
			centerPanel.add(new MainWinCalendar(this));
			windowPanel.add(leftPanel, BorderLayout.WEST);
			windowPanel.add(centerPanel, BorderLayout.CENTER);
			windowPanel.add(rightPanel, BorderLayout.EAST);
		}
		return windowPanel;
	}
	 
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel(new BorderLayout());
			jContentPane.add(getJScrollPane(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}
	
	private JPanel getJContentPaneGroupe() {
		if (jContentPaneGroup == null) {
			jContentPaneGroup = new JPanel(new BorderLayout());
			jContentPaneGroup.add(getJScrollPaneGroup(), BorderLayout.NORTH);
		}
		return jContentPaneGroup;
	}
	
	private JScrollPane getJScrollPane()
	{
		if(jScrollTree == null)
		{
			jScrollTree = new JScrollPane();
			jScrollTree.setViewportView(getJTree());
			jScrollTree.setPreferredSize(new Dimension(240,450));
		}
		return jScrollTree;
	}
	
	private JScrollPane getJScrollPaneGroup()
	{
		if(jScrollTreeGroup == null)
		{
			jScrollTreeGroup = new JScrollPane();
			jScrollTreeGroup.setViewportView(getJTreeGroupe());
			jScrollTreeGroup.setPreferredSize(new Dimension(240,450));
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
		JMenuBar barMenu = new JMenuBar();
		JMenu fichier = new JMenu("Fichier");
		JMenu edition = new JMenu("Edition");
		JMenu option = new JMenu("Options");
		JMenu help = new JMenu("Help");
		JMenuItem createEvent = new JMenuItem("Créer un nouvel évènement");
		JMenuItem deleteEvent = new JMenuItem("Supprimer un évènement");
		JMenuItem settings = new JMenuItem("Options");
		JMenuItem close = new JMenuItem("Quitter");
		JMenuItem helpItem = new JMenuItem("Aide");
		JMenuItem credit = new JMenuItem("A propos");
		
		fichier.add(createEvent);
		fichier.add(deleteEvent);
		fichier.add(close);
		
		option.add(settings);
		
		help.add(helpItem);
		help.add(credit);
		
		barMenu.add(fichier);
		barMenu.add(edition);
		barMenu.add(option);
		barMenu.add(help);
		
		createEvent.addActionListener(new OpenWinCreatEvent());
		
		return barMenu;
	}
}