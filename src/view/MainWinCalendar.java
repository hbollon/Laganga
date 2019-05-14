package view;

import javax.swing.*;

import com.toedter.calendar.JCalendar;

import java.util.Date;

public class MainWinCalendar extends JTabbedPane {
	private static final long serialVersionUID = 1L;
	
	private Date date = new Date();
	private Mois mois;
	
	public MainWinCalendar(JFrame fenetre) {
		super();
		
		Semaine semaine = new Semaine();
		mois = new Mois();
		Annee annee = new Annee(date);
		
		this.addTab("Semaine", semaine);
		this.addTab("Mois", mois);
		this.addTab("Année", annee);		
	}
	
	public void addEvent(String name, String desc, JCalendar dateBegin, JCalendar dateEnd, int timeHourBegin,
			int timeMinuteBegin, int timeHourEnd, int timeMinuteEnd)
	{
		mois.addEventMois(name, desc, dateBegin, dateEnd, timeHourBegin, timeMinuteBegin, timeHourEnd, timeMinuteEnd);
	}
	
	public Mois getCalendarP()
	{
		return mois;
	}
}