package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.*;
import com.toedter.calendar.JCalendar;

public class Mois extends JPanel {
		
	public Mois() {
		super(new BorderLayout());
		
		JPanel changerMois = new JPanel(new FlowLayout());
		changerMois.add(new JButton("Mois précédent"));
		changerMois.add(new JButton("Mois suivant"));
		
	    JCalendar calendar = new JCalendar();
	    
	    this.add(changerMois, BorderLayout.NORTH);
	    this.add(calendar, BorderLayout.CENTER);
	}
}
