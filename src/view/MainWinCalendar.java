package view;

import java.awt.*;
import javax.swing.*;

import controller.ControllerWeek;

import java.text.DateFormat;
import java.util.Date;

public class MainWinCalendar extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private int noSemaine = 1;
	private Date aujourdhui = new Date();
	
	public MainWinCalendar(JFrame fenetre) {
		super(new BorderLayout());
		
		JButton weekButton = new JButton("Semaine");
		JButton monthButton = new JButton("Mois");
		JButton yearButton = new JButton("Année");
		
		JPanel choixSemaine = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		choixSemaine.add(weekButton, BorderLayout.WEST);
		choixSemaine.add(monthButton, BorderLayout.CENTER);
		choixSemaine.add(yearButton, BorderLayout.EAST);
		
		JPanel semaine = new Semaine();
		JPanel mois = new Mois();
		JPanel annee = new Annee();
		
		this.add(choixSemaine, BorderLayout.NORTH);
		this.add(mois, BorderLayout.CENTER);

		fenetre.add(this);
		
		weekButton.addActionListener(new ControllerWeek(semaine, mois, annee));
		monthButton.addActionListener(new ControllerWeek(semaine, mois, annee));
		yearButton.addActionListener(new ControllerWeek(semaine, mois, annee));
	}
}