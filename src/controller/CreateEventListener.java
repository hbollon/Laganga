package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private int timeHourBegin;
	private int timeMinuteBegin;
	private int timeHourEnd;
	private int timeMinuteEnd;
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
		name = win.getName();
		dateBegin = new GregorianCalendar(win.getDateBegin().getDate().getYear(), win.getDateBegin().getDate().getMonth() - 1, win.getDateBegin().getDate().getDay());
		dateEnd = new GregorianCalendar(win.getDateEnd().getDate().getYear(), win.getDateEnd().getDate().getMonth() - 1, win.getDateEnd().getDate().getDay());
		timeHourBegin = win.getHourBegin();
		timeMinuteBegin = win.getMinuteBegin();
		timeHourEnd = win.getHourEnd();
		timeMinuteEnd = win.getMinuteEnd();
		desc = win.getDesc();
		priority = win.getPriority().getSelectedIndex();
		hide = win.getHide();
		author = LocalUser.localUser.getUser();
		users = win.getUsersP();
		groups = win.getGroupsP();
		
		Location testLoc = new Location();
		
		MainWin.callAddEvent(name, desc, priority, author, dateBegin, dateEnd, timeHourBegin, timeMinuteBegin, timeHourEnd, timeMinuteEnd, hide, testLoc, users, groups);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		createEventLocal();		
	}
}
