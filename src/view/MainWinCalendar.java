package view;

import javax.swing.*;

import com.toedter.calendar.JCalendar;

import view.tabs.TestTab;

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
		
		// Tests
		TestTab test = new TestTab(this);
		test.switchTo();
	}
	
	public Mois getCalendarP()
	{
		return mois;
	}
}