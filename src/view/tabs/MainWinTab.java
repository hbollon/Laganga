package view.tabs;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import controller.buttons.CloseTabButtonListener;

/**
 * Le MainWinTab est la classe mère des onglets de la fenêtre principale
 * 
 * @author Julien Valverdé
 */
public abstract class MainWinTab extends JPanel {
	private static final long serialVersionUID = 1707149621277512327L;
	
	private JTabbedPane parent; // Conteneur des onglets
	private String name = ""; // Nom de l'onglet
	protected JPanel header = new JPanel(new BorderLayout()); // Barre du haut de l'onglet (avec le bouton pour le fermer)
	protected JPanel content = new JPanel(); // Contenu de l'onglet
	private JScrollPane contentScrollPane = new JScrollPane(content); // Panel à défilement du contenu
	
	public void setName(String name) {
		this.name = name;
	}
	
	public MainWinTab(JTabbedPane parent) {
		super();
		
		this.parent = parent;
		
		// Remplissage de l'header
		JButton close = new JButton("Fermer");
		close.addActionListener(new CloseTabButtonListener(this));
		header.add(close, BorderLayout.EAST);
		
		// Mise en place du contenu
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		
		// Mise en place de l'onglet
		setLayout(new BorderLayout());
		add(header, BorderLayout.NORTH);
		add(contentScrollPane, BorderLayout.CENTER);
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
	protected JPanel newLinePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		
		return panel;
	}
}