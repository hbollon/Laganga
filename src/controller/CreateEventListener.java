package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import com.toedter.calendar.JCalendar;

import java.sql.Time;

import view.MainWin;
import view.MainWinCalendar;
import view.Mois;
import view.WinCreatEvent;

/**
 * Listener créant un Event dans la base de donnée et localement
 * 
 * @author hbollon
 *
 */

public class CreateEventListener implements ActionListener {
	private String name;
	private String type = null;
	private int priority = 0;
	private JCalendar dateBegin;
	private JCalendar dateEnd;
	private int timeHourBegin;
	private int timeMinuteBegin;
	private int timeHourEnd;
	private int timeMinuteEnd;
	private String desc;
	
	private WinCreatEvent win;
	 
	public CreateEventListener(WinCreatEvent win) {
		this.win = win;
	}
	
	public void createEventLocal()
	{
		name = win.getName();
		dateBegin = win.getDateBegin();
		dateEnd = win.getDateEnd();
		timeHourBegin = win.getHourBegin();
		timeMinuteBegin = win.getMinuteBegin();
		timeHourEnd = win.getHourEnd();
		timeMinuteEnd = win.getMinuteEnd();
		desc = win.getDesc();
		MainWin.callAddEvent(name, desc, dateBegin, dateEnd, timeHourBegin, timeMinuteBegin, timeHourEnd, timeMinuteEnd);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		createEventLocal();
		
	}
}
