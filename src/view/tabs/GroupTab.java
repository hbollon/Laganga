package view.tabs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
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
import javax.swing.SpinnerNumberModel;
import javax.swing.tree.DefaultMutableTreeNode;

import com.mindfusion.scheduling.Calendar;

import controller.CreateEventListener;
import controller.CreateGroupe;
import model.LocalUser;
import model.entities.Entity;
import model.entities.Event;
import model.entities.Group;
import model.entities.User;

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
	
	public GroupTab() throws Exception {
		this(null);
	}
	
	public GroupTab(Group group) throws Exception {
		super();
		
		// Nom de l'onglet
		if (group == null)
			setName("Nouvel évènement");
		else
			setName(group.getName());
		
		// Le JPanel content représente le contenu de l'onglet
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		
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
		
		
		//panel membres du groupe
		JPanel participants = new JPanel(new FlowLayout());
		JLabel labelParticipants = new JLabel("Membres participants du groupe : "); 
		participants.add(labelParticipants);
		
		JPanel panMembres = new JPanel(new BorderLayout()); 
		JPanel listes = new JPanel(new FlowLayout());
		
		JPanel search = new JPanel(new FlowLayout()); //paneau pour la barre recherche et la liste de membres dans la base de donnée
		JTextField text = new JTextField("Rechercher membre"); //champ de texte pour la recherche
		text.setPreferredSize(new Dimension(200, 25));
		JButton image = new JButton(new ImageIcon(new ImageIcon("./res/loading.gif").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT))); //boutton qui lance la recherche
		/** Ajout des element de la barre de recherche dans le paneau **/
		search.add(text); 
		search.add(image);
		panMembres.add(search, BorderLayout.NORTH);
		
		/** 
		 * Liste des membres qui peut-etre rajouter dans un événement 
		 * **/
		DefaultMutableTreeNode allUsersTree = new DefaultMutableTreeNode("Membres");
		List<Entity> allUsers = User.factory.getAll();
		for(int i = 0; i < allUsers.size(); i++) {
			User member = (User) allUsers.get(i);
			allUsersTree.add(new DefaultMutableTreeNode(member.getFirstName() + " " + member.getLastName()));
		}
		JTree listeMembres = new JTree(allUsersTree);
		JScrollPane liste = new JScrollPane(listeMembres); //ajout de la liste dans un JScroll pan pour avoir une barre de scroll
		liste.setPreferredSize(new Dimension(200,200));
		
		participants.add(panMembres);
		listes.add(liste);
		/**
		 * deuxieme liste
		 */
		DefaultMutableTreeNode allparticipantsTree = new DefaultMutableTreeNode("Membres participants");
		allparticipantsTree.add(new DefaultMutableTreeNode("Rachid Ben mha dit 'La salope' "));
		JTree listeMembresParticipantsTree = new JTree(allparticipantsTree);
		JScrollPane listeMembresParticipants = new JScrollPane(listeMembresParticipantsTree);
		listeMembresParticipants.setPreferredSize(new Dimension(200,200));
		
		listes.add(listeMembresParticipants);
		panMembres.add(listes, BorderLayout.CENTER);
		
		
		//Panel bouton
		JPanel buttonPane = new JPanel(new FlowLayout());
		JButton annule = new JButton("Annuler");
		JButton ajoutGroupe = new JButton("Créer groupe");
		ajoutGroupe.addActionListener(new CreateGroupe(this));
		buttonPane.add(annule);
		buttonPane.add(ajoutGroupe);
	   
		content.add(participants);
	    content.add(buttonPane);
		
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
	
}