package Window;

import java.awt.*;
import javax.swing.*;

public class MainWin extends JFrame { 

private static final long serialVersionUID = 1L;

private JPanel windowPanel = null;
private JPanel centerPanel = null;
private JPanel leftPanel = null;
private JPanel rightPanel = null;
private JPanel jContentPane = null;
private JTree jTree = null;
private JScrollPane jScrollTree = null;
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

public MainWin() 
{
	super();
	initialize();
	setTree();
}
 
public void initialize()
{
	JFrame.setDefaultLookAndFeelDecorated(true);
	this.setTitle("Laganga");
    this.setSize(1280, 720);
    this.setJMenuBar(getMenu());
    ImageIcon img = new ImageIcon("icon.png");
    this.setIconImage(img.getImage());
	this.setContentPane(getWindowPane());
	this.getContentPane().setBackground(Color.white);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
    this.setVisible(true);
}

public void setTree()
{
	
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
		rightPanel.add(getNotificationBar(), BorderLayout.EAST);
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
	
	return barMenu;
}


}