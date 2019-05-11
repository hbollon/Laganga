package model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Observable;

@SuppressWarnings("deprecation")
public class Agenda extends Observable {
	// Agenda principal
	public static Agenda agenda = new Agenda();
	
	// Date du premier jour de la semaine
	private Calendar weekBeginning;
	
	// Getteurs
	public Calendar getWeekBeginning() {
		return weekBeginning;
	}
	
	// Setteurs
	public void nextWeek() {
		weekBeginning.add(Calendar.WEEK_OF_YEAR, 1);
	}
	public void previousWeek() {
		weekBeginning.add(Calendar.WEEK_OF_YEAR, -1);
	}
	
	// Constructeur
	public Agenda() {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		
		this.weekBeginning = cal;
	}
	
	// Représentation sous forme de String
	public String toString() {
		return weekBeginning.get(Calendar.YEAR)+"-"+weekBeginning.get(Calendar.MONTH)+"-"+weekBeginning.get(Calendar.DAY_OF_MONTH)+" "+weekBeginning.get(Calendar.HOUR_OF_DAY)+":"+weekBeginning.get(Calendar.MINUTE)+":"+weekBeginning.get(Calendar.SECOND);
	}
}