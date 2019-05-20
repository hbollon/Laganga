package view.tabs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import controller.buttons.CloseTabButtonListener;
import view.MainWin;

/**
 * Le MainWinTab est la classe mère des onglets de la fenêtre principale
 * 
 * @author Julien Valverdé
 */
public abstract class Tab extends JPanel {
	private static final long serialVersionUID = 1707149621277512327L;
	
	private static JTabbedPane parent = MainWin.tabbedPane; // Conteneur des onglets
	
	private String name = ""; // Nom de l'onglet
	
	private JPanel header = new JPanel(new BorderLayout()); // Barre du haut de l'onglet (avec le bouton pour le fermer)
	protected JPanel headerWest = new JPanel(new FlowLayout());
	protected JPanel headerCenter = new JPanel(new FlowLayout());
	protected JPanel headerEast = new JPanel(new FlowLayout());
	
	protected JPanel content = new JPanel(); // Contenu de l'onglet
	private JScrollPane contentScrollPane = new JScrollPane(content); // Panel à défilement du contenu
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Tab(boolean closable) {
		super(new BorderLayout());
		
		// Remplissage de l'header
		if (closable) {
			JButton close = new JButton("Fermer");
				close.addActionListener(new CloseTabButtonListener(this));
				headerEast.add(close, BorderLayout.EAST);
		}
		
		header.add(headerWest, BorderLayout.WEST);
		header.add(headerCenter, BorderLayout.CENTER);
		header.add(headerEast, BorderLayout.EAST);
		
		// Mise en place du contenu
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		
		// Mise en place de l'onglet
		add(header, BorderLayout.NORTH);
		add(contentScrollPane, BorderLayout.CENTER);
	}
	public Tab() {
		this(true);
	}
	
	// Afficher l'onglet
	public void open() {
		parent.addTab(name, this);
	}
	
	// Basculer vers l'onglet
	public void switchTo() {
		parent.setSelectedComponent(this);
	}
	
	// Fermer l'onglet
	public void close() {
		parent.remove(this);
	}
	
	// Création d'un panel en ligne
	protected JPanel createLinePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		
		return panel;
	}
}