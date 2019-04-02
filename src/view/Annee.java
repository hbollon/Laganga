package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.toedter.calendar.JCalendar;

public class Annee extends JPanel {
		
	public Annee(Date aujourdhui) {
		super(new BorderLayout());
		
		JPanel changerAnnee = new JPanel(new FlowLayout());
		changerAnnee.add(new JButton("Année précédente"));
		changerAnnee.add(new JButton("Année suivante"));
		
		JPanel calendars = new JPanel(new GridLayout(4,3));
		LocalDate localDate = aujourdhui.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year  = localDate.getYear();
		int mois = 0;
		for(int i=1; i<5; i++) {
			for (int j=1; j<4; j++) {
				calendars.add(new JCalendar(new GregorianCalendar(year, mois, 1)));
				mois += 1;
			}
		}
		
		this.add(changerAnnee, BorderLayout.NORTH);
		this.add(calendars, BorderLayout.CENTER);
	}
}