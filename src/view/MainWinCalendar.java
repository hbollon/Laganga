package view;

import javax.swing.*;
import java.util.Date;

public class MainWinCalendar extends JTabbedPane {
	private static final long serialVersionUID = 1L;
	
	private Date date = new Date();
	
	public MainWinCalendar(JFrame fenetre) {
		super();
		
		Semaine semaine = new Semaine();
		JPanel mois = new Mois();
		JPanel annee = new Annee(date);
		
		this.addTab("Semaine", semaine);
		this.addTab("Mois", mois);
		this.addTab("Année", annee);		
	}
}