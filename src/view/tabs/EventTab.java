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
import javax.swing.SpinnerNumberModel;

import com.mindfusion.scheduling.Calendar;

import controller.CreateEventListener;
import model.LocalUser;
import model.entities.Entity;
import model.entities.Event;
import model.entities.User;
import view.elements.ParticipationGroupsEntityTree;
import view.elements.ParticipationUsersEntityTree;

/**
 * Un onglet de test pour servir d'exemple
 * 
 * @author Julien Valverdé
 */
public class EventTab extends Tab {
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
	private JCheckBox checkVisible;
	private ParticipationUsersEntityTree listeMembresParticipantsTree;
	private ParticipationGroupsEntityTree listeGroupesParticipantsTree;
	
	public EventTab() throws Exception {
		this(null);
	}
	
	public EventTab(Event event) throws Exception {
		super();
		
		// Nom de l'onglet
		if (event == null)
			setName("Nouvel évènement");
		else
			setName(event.getName());
		
		// Le JPanel content représente le contenu de l'onglet
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		
		//Panel pour le nom de l'événement
		JPanel name = new JPanel(new FlowLayout());
		JLabel labelName = new JLabel("Nom de l'événement : ");
		
		if (event != null) {
			if (event.getCreator() == LocalUser.localUser.getUser())
				textName = new JTextField(event.getName());
			else
				textName = new JLabel(event.getName());
		}
		else {
			textName = new JTextField();
			textName.setPreferredSize(new Dimension(500, 25));
		}
		
		name.add(labelName);
		name.add(textName);
		content.add(name);
		
		if(event != null)
		{
			if (event.getCreator() == LocalUser.localUser.getUser()) {
				//Panel début de l'événement
				JPanel dateBegin = new JPanel(new FlowLayout());
				
				//Date
				JLabel labelBegin = new JLabel("Début de l'événement : ");
				calendarBegin = new Calendar();
						
				//Heure
				JPanel timeBegin = new JPanel(new FlowLayout());
				hourBegin = new JSpinner(new SpinnerNumberModel(00, 00, 23, 1));
				minuteBegin = new JSpinner(new SpinnerNumberModel(00, 00, 59, 1));
				timeBegin.add(hourBegin);
				timeBegin.add(minuteBegin);
					
				dateBegin.add(labelBegin);
				dateBegin.add(calendarBegin);
				dateBegin.add(timeBegin);
				
				//Panel fin de l'événement
				JPanel dateEnd = new JPanel(new FlowLayout());
				//Date
				JLabel labelEnd = new JLabel("Fin de l'événement : ");
				calendarEnd = new Calendar();
				
				//Heure
				JPanel timeEnd = new JPanel(new FlowLayout());
				hourEnd = new JSpinner(new SpinnerNumberModel(00, 00, 23, 1));
				minuteEnd = new JSpinner(new SpinnerNumberModel(00, 00, 59, 1));
				timeEnd.add(hourEnd);
				timeEnd.add(minuteEnd);
				
				dateEnd.add(labelEnd);
				dateEnd.add(calendarEnd);
				dateEnd.add(timeEnd);
				
				content.add(dateBegin);
				content.add(dateEnd);
			}
			else
			{
				content.add(new JLabel("Début : " + event.getBegin().getTime()));
				content.add(new JLabel("Fin : " + event.getEnd().getTime()));
			}
		}
		else {
			//Panel début de l'événement
			JPanel dateBegin = new JPanel(new FlowLayout());
			
			//Date
			JLabel labelBegin = new JLabel("Début de l'événement : ");
			calendarBegin = new Calendar();
					
			//Heure
			JPanel timeBegin = new JPanel(new FlowLayout());
			hourBegin = new JSpinner(new SpinnerNumberModel(00, 00, 23, 1));
			minuteBegin = new JSpinner(new SpinnerNumberModel(00, 00, 59, 1));
			timeBegin.add(hourBegin);
			timeBegin.add(minuteBegin);
				
			dateBegin.add(labelBegin);
			dateBegin.add(calendarBegin);
			dateBegin.add(timeBegin);
			
			//Panel fin de l'événement
			JPanel dateEnd = new JPanel(new FlowLayout());
			//Date
			JLabel labelEnd = new JLabel("Fin de l'événement : ");
			calendarEnd = new Calendar();
			
			//Heure
			JPanel timeEnd = new JPanel(new FlowLayout());
			hourEnd = new JSpinner(new SpinnerNumberModel(00, 00, 23, 1));
			minuteEnd = new JSpinner(new SpinnerNumberModel(00, 00, 59, 1));
			timeEnd.add(hourEnd);
			timeEnd.add(minuteEnd);
			
			dateEnd.add(labelEnd);
			dateEnd.add(calendarEnd);
			dateEnd.add(timeEnd);
			
			content.add(dateBegin);
			content.add(dateEnd);
		}
		
		
		//Panel description
		JPanel description = new JPanel(new FlowLayout());
		if(event != null)
		{
			if (event.getCreator() == LocalUser.localUser.getUser()) {
				JLabel labelDescription = new JLabel("Description de l'énévement : ");
				textDescription = new JTextArea(event.getType());
				textDescription.setPreferredSize(new Dimension(400, 40));
				description.add(labelDescription);
				description.add(textDescription);
			}
			else {
				JLabel labelDescription = new JLabel("Description de l'énévement : \n" + event.getType());
				description.add(labelDescription);
			}
		}
		else {
			JLabel labelDescription = new JLabel("Description de l'énévement : ");
			textDescription = new JTextArea();
			textDescription.setPreferredSize(new Dimension(400, 40));
			description.add(labelDescription);
			description.add(textDescription);
		}
		content.add(description);
		
		
		//panel membres participant aux evenements
		JPanel participants = new JPanel(new FlowLayout());
		JLabel labelParticipants = new JLabel("Membres participants à l'événement : "); 
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
		List<Entity> allUsers = User.factory.getAll();
		listeMembresParticipantsTree = new ParticipationUsersEntityTree();
		JScrollPane liste = new JScrollPane(listeMembresParticipantsTree); //ajout de la liste dans un JScroll pan pour avoir une barre de scroll
		liste.setPreferredSize(new Dimension(200,200));
		
		participants.add(panMembres);
		listes.add(liste);
		panMembres.add(listes, BorderLayout.CENTER);
		
		
		//groupes pparticipant a l'événement
		JPanel groupesParticipants = new JPanel(new FlowLayout()); //Panel ligne groupe
		
		JLabel panelTextGroupe = new JLabel("Groupes Participant à l'événement : ");
		groupesParticipants.add(panelTextGroupe);
		
		JPanel listesGroupes = new JPanel(new BorderLayout()); //Panel gestion groupe
		JPanel listeDesGroupes = new JPanel(new FlowLayout()); //Panel listes
		
		JPanel searchGroup = new JPanel(new FlowLayout()); //paneau pour la barre recherche et la liste de membres dans la base de donnée
		JTextField textsearchGroup = new JTextField("Rechercher groupe"); //champ de texte pour la recherche
		textsearchGroup.setPreferredSize(new Dimension(200, 25));
		JButton imageGroupe = new JButton(new ImageIcon(new ImageIcon("./res/loading.gif").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
		/** Ajout des element de la barre de recherche dans le paneau **/
		searchGroup.add(textsearchGroup); 
		searchGroup.add(imageGroupe); //boutton qui lance la recherche
		listesGroupes.add(searchGroup, BorderLayout.NORTH);
		
		listeGroupesParticipantsTree = new ParticipationGroupsEntityTree();		
		JScrollPane listeG = new JScrollPane(listeGroupesParticipantsTree); //ajout de la liste dans un JScroll pan pour avoir une barre de scroll
		listeG.setPreferredSize(new Dimension(200,200));
		listeDesGroupes.add(listeG);
		
		listesGroupes.add(listeDesGroupes, BorderLayout.CENTER);
		groupesParticipants.add(listesGroupes);
		
		
		
		//panel degré d'importance de l'évenement
		JPanel degreeImportance = new JPanel(new FlowLayout());
		JLabel labelImportance = new JLabel("Style d'événement : ");
		Object[] elements = new Object[] {"", "RDV personnel déplaçable", "RDV proffessionel déplaçable", "RDV personnel non déplaçable", "RDV proffessionnel non déplaçable", "Autre"};
		importance = new JComboBox<Object>(elements);
		degreeImportance.add(labelImportance);
		degreeImportance.add(importance);
		
		//CheckBox pour si les details de l'événement est visible ou non
		JPanel eventVisible = new JPanel(new FlowLayout());
		JLabel labelVisible = new JLabel("Descrition de l'événement caché : ");
		checkVisible = new JCheckBox();
		eventVisible.add(labelVisible);
		eventVisible.add(checkVisible);
		
		
		
		//Panel bouton
		JPanel buttonPane = new JPanel(new FlowLayout());
		JButton annuleEvent = new JButton("Annuler");
		JButton ajoutEvent = new JButton("Ajouter l'événement");
		ajoutEvent.addActionListener(new CreateEventListener(this));
		buttonPane.add(annuleEvent);
		buttonPane.add(ajoutEvent);
	   
		content.add(participants);
		content.add(groupesParticipants);
		content.add(degreeImportance);
		content.add(eventVisible);
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
	
	public boolean getHide()
	{
		return checkVisible.isSelected();
	}
	
	public List<Entity> getUsersP()
	{
		return listeMembresParticipantsTree.getSelectedList();
	}
	
	public List<Entity> getGroupsP()
	{
		return listeGroupesParticipantsTree.getSelectedList();
	}
	

	
}