package view;

import javax.swing.*;
import java.util.Date;

public class MainWinCalendar extends JTabbedPane {
	private static final long serialVersionUID = 1L;
	
	private int noSemaine = 1;
	private Date aujourdhui = new Date();
	
	public MainWinCalendar(JFrame fenetre) {
		super();
		
		JPanel semaine = new Semaine();
		JPanel mois = new Mois();
		JPanel annee = new Annee();
		
		this.addTab("Semaine", semaine);
		this.addTab("Mois", mois);
		this.addTab("Année", annee);
	}
}