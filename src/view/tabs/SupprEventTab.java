package view.tabs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import controller.SupprGroup;
import model.FieldsList;
import model.LocalUser;
import model.entities.Entity;
import model.entities.Event;
import model.entities.Group;

public class SupprEventTab extends Tab {
	private static final long serialVersionUID = -618260664434187272L;
	
	private static FieldsList groupsQueryFieldList = new FieldsList();
	static {
		groupsQueryFieldList.add("owner", "model.entities.User");
	}
	
	public SupprEventTab() throws Exception {
		super();
		setName("Supprimer un événement");
		
		JPanel panMain = new JPanel(new BorderLayout());
		
		JPanel barreRecherche = new JPanel(new FlowLayout());
		JTextField text = new JTextField("Rechercher"); //champ de texte pour la recherche
		text.setPreferredSize(new Dimension(200, 25));
		JButton image = new JButton(new ImageIcon(new ImageIcon("./res/loading.gif").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT))); //boutton qui lance la recherche
		barreRecherche.add(text);
		barreRecherche.add(image);
		panMain.add(barreRecherche, BorderLayout.NORTH);
		
		DefaultMutableTreeNode allEventsTree = new DefaultMutableTreeNode("Mes événements");
		
		// Obtention de la liste des groupes dont on est le propriétaire
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("owner", LocalUser.localUser.getUser());
		
		List<Entity> allEvents = Event.factory.get(
				"WHERE `"+ Event.factory.getPrefix() + "creator` = ?",
				groupsQueryFieldList,
				values);
		
		for(int i = 0; i < allEvents.size(); i++) {
			Event event = (Event) allEvents.get(i);
			allEventsTree.add(new DefaultMutableTreeNode(event.getName()));
		}
		JTree listeEvents = new JTree(allEventsTree);
		JScrollPane listeGroupe = new JScrollPane(listeEvents);
		panMain.add(listeGroupe, BorderLayout.CENTER);
		
		JButton buttonSuppr = new JButton("Supprimer événement");
		//buttonSuppr.addActionListener(new SupprEvent(this, allGroups, listeGroupes));
		panMain.add(buttonSuppr, BorderLayout.SOUTH);
		
		content.add(panMain);
		open();
	}
}
