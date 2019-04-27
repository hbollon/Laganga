package model;

import java.sql.Date;
import java.util.Observable;

@SuppressWarnings("deprecation")
public class Agenda extends Observable {
	// Agenda principal
	public static Agenda agenda = new Agenda();
	
	private Date weekDate; // Date du premier jour de la semaine
	
	// Getteurs
	public Date getWeekDate() {
		return weekDate;
	}
	
	// Setteurs
	public void setWeekDate(Date weekDate) {
		this.weekDate = weekDate;
		
		setChanged();
		notifyObservers();
	}
}