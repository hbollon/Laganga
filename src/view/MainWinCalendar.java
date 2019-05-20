package view;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class MainWinCalendar extends JTabbedPane {
	private static final long serialVersionUID = 1L;
	
	private Mois mois;
	private Semaine semaine;
	
	public MainWinCalendar(JFrame fenetre) {
		super();
		
		semaine = new Semaine();
		mois = new Mois();
		
		this.addTab("Mois", mois);
		this.addTab("Semaine", semaine);
	}
	
	public Mois getMoisP()
	{
		return mois;
	}
	
	public Semaine getSemaineP()
	{
		return semaine;
	}
}