package view.tabs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.mindfusion.scheduling.Calendar;

import controller.buttons.ModifyGroupButtonListener;
import model.LocalUser;
import model.entities.Entity;
import model.entities.Group;
import model.entities.User;
import view.elements.EntityTree;
import view.elements.SelectableEntityTree;

/**
 * Un onglet de test pour servir d'exemple
 * 
 * @author Julien Valverdé
 */
public class GroupTab extends Tab {
	private static final long serialVersionUID = -618260664434187272L;

	private Component textName;
	private JTextArea textDescription;
	private Calendar calendarBegin;
	private Calendar calendarEnd;
	private JSpinner hourBegin;
	private JSpinner hourEnd;
	private JSpinner minuteBegin;
	private JSpinner minuteEnd;
	private JComboBox<Object> importance;
	private JCheckBox eventVisible;
	private SelectableEntityTree membersSelector;
	
	public GroupTab() throws Exception {
		this(null);
	}
	
	public GroupTab(Group group) throws Exception {
		super();
		
		// Nom de l'onglet
		if (group == null)
			setName("Nouveau groupe");
		else
			setName(group.getName());
		
		// Le JPanel content représente le contenu de l'onglet
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		
		/*
		 * Boutons de la header
		 */
		
		// Validation des modifications
		if (group == null || group.getOwner() == LocalUser.localUser.getUser()) {
			JButton ok = new JButton("Valider");
				ok.addActionListener(new ModifyGroupButtonListener(group, this));
				headerWest.add(ok);
		}
		
		
		//Panel pour le nom de l'événement
		JPanel name = new JPanel(new FlowLayout());
		JLabel labelName = new JLabel("Nom du groupe : ");
		
		if (group != null) {
			if (group.getOwner() == LocalUser.localUser.getUser())
				textName = new JTextField(group.getName());
			else
				textName = new JLabel(group.getName());
		}
		else {
			textName = new JTextField();
			textName.setPreferredSize(new Dimension(500, 25));
		}
		
		name.add(labelName);
		name.add(textName);
		content.add(name);
		
		
		/*
		 * Affichage/modification des membres
		 */
		if (group == null) { // Création d'un nouveau groupe
			membersSelector = new SelectableEntityTree("Tous les utilisateurs", User.factory.getAll(), true, "Membres");
				content.add(membersSelector);
		}
		else { // Affichage/modification d'un groupe existant
			// Conversion de List<User> vers List<Entity>
			List<User> members = group.getMembers();
			List<Entity> treeMembers = new ArrayList<Entity>();
			for (int i = 0; i < members.size(); i++)
				treeMembers.add(members.get(i));
			
			if (group.getOwner() == LocalUser.localUser.getUser()) {
				membersSelector = new SelectableEntityTree("Tous les utilisateurs", User.factory.getAll(), true, "Membres");
					membersSelector.setSelectedList(treeMembers);
					membersSelector.updateView();
					content.add(membersSelector);
			}
			else
				content.add(new EntityTree("Membres", treeMembers, true));
		}
		
		
		open(); // Ouverture de l'onglet
	}
	
	public String getName()
	{
		return ((JTextField)textName).getText();
	}
	
	public String getDesc()
	{
		return textDescription.getText();
	}
	
	public Calendar getDateBegin()
	{
		return calendarBegin;
	}
	
	public Calendar getDateEnd()
	{
		return calendarEnd;
	}
	
	public int getHourBegin()
	{
		return (Integer)hourBegin.getValue();
	}
	
	public int getMinuteBegin()
	{
		return (Integer)minuteBegin.getValue();
	}
	
	public int getHourEnd()
	{
		return (Integer)hourEnd.getValue();
	}
	
	public int getMinuteEnd()
	{
		return (Integer)minuteEnd.getValue();
	}
	
	public JComboBox<Object> getPriority()
	{
		return importance;
	}
	
	public JCheckBox getHide()
	{
		return eventVisible;
	}
	
	public SelectableEntityTree getMembersSelector() {
		return membersSelector;
	}
}