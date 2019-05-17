package view;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class MainWinCalendar extends JTabbedPane {
	private static final long serialVersionUID = 1L;
	
	private Mois mois;
	
	public MainWinCalendar(JFrame fenetre) {
		super();
		
		Semaine semaine = new Semaine();
		mois = new Mois();
		
		this.addTab("Semaine", semaine);
		this.addTab("Mois", mois);
	}
	
	public Mois getCalendarP()
	{
		return mois;
	}
}