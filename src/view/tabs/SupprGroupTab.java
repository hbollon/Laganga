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
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import controller.SupprGroup;
import model.FieldsList;
import model.LocalUser;
import model.entities.Entity;
import model.entities.Group;

public class SupprGroupTab extends MainWinTab {
	private static final long serialVersionUID = -618260664434187272L;
	
	private static FieldsList groupsQueryFieldList = new FieldsList();
	static {
		groupsQueryFieldList.add("owner", "model.entities.User");
	}
	
	public SupprGroupTab(JTabbedPane parent) throws Exception {
		super(parent);
		setName("Supprimer un groupe");
		
		JPanel panMain = new JPanel(new BorderLayout());
		
		JPanel barreRecherche = new JPanel(new FlowLayout());
		JTextField text = new JTextField("Rechercher"); //champ de texte pour la recherche
		text.setPreferredSize(new Dimension(200, 25));
		JButton image = new JButton(new ImageIcon(new ImageIcon("./res/loading.gif").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT))); //boutton qui lance la recherche
		barreRecherche.add(text);
		barreRecherche.add(image);
		panMain.add(barreRecherche, BorderLayout.NORTH);
		
		DefaultMutableTreeNode allGroupsTree = new DefaultMutableTreeNode("Mes groupes");
		
		// Obtention de la liste des groupes dont on est le propriétaire
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("owner", LocalUser.localUser.getUser());
		
		List<Entity> allGroups = Group.factory.get(
				"WHERE `"+Group.factory.getPrefix()+"owner` = ?",
				groupsQueryFieldList,
				values);
		
		for(int i = 0; i < allGroups.size(); i++) {
			Group group = (Group) allGroups.get(i);
			allGroupsTree.add(new DefaultMutableTreeNode(group.getName()));
		}
		JTree listeGroupes = new JTree(allGroupsTree);
		JScrollPane listeGroupe = new JScrollPane(listeGroupes);
		panMain.add(listeGroupe, BorderLayout.CENTER);
		
		JButton buttonSuppr = new JButton("Supprimer groupe");
		buttonSuppr.addActionListener(new SupprGroup(this, allGroups, listeGroupes));
		panMain.add(buttonSuppr, BorderLayout.SOUTH);
		
		content.add(panMain);
		open();
	}
}
