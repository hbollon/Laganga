package view;

import java.awt.*;
import javax.swing.*;

import java.text.DateFormat;
import java.util.Date;

public class MainWinCalendar extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private int noSemaine = 1;
	private Date aujourdhui = new Date();
	
	public MainWinCalendar(JFrame fenetre) {
		super(new BorderLayout());
		
		JButton semainePrec = new JButton("Semaine");
		JButton semaineActu = new JButton("Mois");
		JButton semaineSuiv = new JButton("Année");
		
		JPanel choixSemaine = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		choixSemaine.add(semainePrec, BorderLayout.WEST);
		choixSemaine.add(semaineActu, BorderLayout.CENTER);
		choixSemaine.add(semaineSuiv, BorderLayout.EAST);
		
		JPanel semaine = new Semaine();
		
		this.add(choixSemaine, BorderLayout.NORTH);
		this.add(semaine, BorderLayout.CENTER);

		fenetre.add(this);
		
		DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
		System.out.println(shortDateFormat.format(aujourdhui));
	}
}