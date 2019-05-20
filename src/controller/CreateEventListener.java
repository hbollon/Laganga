package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import model.LocalUser;
import model.entities.Entity;
import model.entities.Location;
import model.entities.User;
import view.MainWin;
import view.tabs.EventTab;

/**
 * Listener créant un Event dans la base de donnée et localement
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
			name = win.getName();
			dateBegin = new GregorianCalendar(win.getDateBegin().getCalendar().get(Calendar.YEAR), win.getDateBegin().getCalendar().get(Calendar.MONTH) - 1, win.getDateBegin().getCalendar().get(Calendar.DAY_OF_MONTH), win.getHourBegin(), win.getMinuteBegin());
			dateEnd = new GregorianCalendar(win.getDateEnd().getCalendar().get(Calendar.YEAR), win.getDateEnd().getCalendar().get(Calendar.MONTH) - 1, win.getDateEnd().getCalendar().get(Calendar.DAY_OF_MONTH), win.getHourEnd(), win.getMinuteEnd());
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
					
			MainWin.callAddEvent(name, desc, priority, author, dateBegin, dateEnd, hide, location, users, groups);
		}
		catch (Exception e)
		{
			System.err.println("Error : location is null");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		createEventLocal();		
	}
}
