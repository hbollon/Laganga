package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.LocalUser;
import model.entities.Entity;
import model.entities.Event;
import model.entities.Group;
import model.entities.Location;
import model.entities.User;
import view.MainWin;
import view.tabs.EventTab;

/**
 * Listener récupérant les attributs nécéssaires à la creation d'un event
 * Il appel ensuite la méthode statique "MainWin.callAddEvent()"
 * 
 * @author hbollon
 *
 */

public class CreateEventListener implements ActionListener {
	private String name;
	private int priority = 0;
	private GregorianCalendar dateBegin;
	private GregorianCalendar dateEnd;
	private Location location;
	private String desc;
	private boolean hide;
	private List<Entity> users;
	private List<Entity> groups;
	private User author;
	
	
	private EventTab win;
	 
	public CreateEventListener(EventTab eventTab) {
		this.win = eventTab;
	}
	
	public void createEventLocal()
	{
		try
		{
			//Récupération des attributs depuis l'instance de EventTab
			name = win.getName();
			dateBegin = new GregorianCalendar(win.getDateBegin().getCalendar().get(Calendar.YEAR), win.getDateBegin().getCalendar().get(Calendar.MONTH), win.getDateBegin().getCalendar().get(Calendar.DAY_OF_MONTH), win.getHourBegin(), win.getMinuteBegin());
			dateEnd = new GregorianCalendar(win.getDateEnd().getCalendar().get(Calendar.YEAR), win.getDateEnd().getCalendar().get(Calendar.MONTH), win.getDateEnd().getCalendar().get(Calendar.DAY_OF_MONTH), win.getHourEnd(), win.getMinuteEnd());
			location = win.getSelectedLocation();
			desc = win.getDesc();
			priority = win.getPriority().getSelectedIndex();
			hide = win.getHide();
			author = LocalUser.localUser.getUser();
			users = win.getUsersP();
			groups = win.getGroupsP();
			
			if(win.getSelectedLocation() != null)
				location = win.getSelectedLocation();
			else
				throw new Exception();
			
			// Ajout l'utilisateur courant dans les participants à l'évènement
			if (!users.contains(LocalUser.localUser.getUser()))
				users.add(LocalUser.localUser.getUser());
			
			//Méthode statique de MainWin, elle permet de démarrer l'ajout d'évènement dans la base
			//MainWin.callAddEvent(name, desc, priority, author, dateBegin, dateEnd, hide, location, users, groups);
			
			/*
			 * Ajout dans la base de données
			 */
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("name", name);
			values.put("type", desc);
			values.put("priority", priority);
			values.put("creator", author);
			values.put("begin", dateBegin);
			values.put("end", dateEnd);
			values.put("location", location);
			values.put("hidden", hide);
			
			Event event = (Event) Event.factory.insert(values);
			
			// Ajout des utilisateurs
			for (int i = 0; i < users.size(); i++)
				event.addParticipant((User) users.get(i));
			
			// Ajout des groupes
			for (int i = 0; i < groups.size(); i++)
				event.addParticipant((Group) groups.get(i));
			
			// Mettre à jour la vue et fermer l'onglet
			win.close();
			MainWin.mainWin.refresh();
		}
		catch (Exception e)
		{
			System.err.println("Error : location is null");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		createEventLocal();		
		win.close();
	}
}
