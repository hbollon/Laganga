package view;

import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

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
	
	public Mois getCalendarP()
	{
		return mois;
	}
}