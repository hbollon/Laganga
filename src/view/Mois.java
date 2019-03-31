package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.*;
import com.toedter.calendar.JCalendar;

public class Mois extends JPanel {
		
	public Mois() {
		super(new FlowLayout(FlowLayout.CENTER));
		    
	    JCalendar calendar = new JCalendar();
	    calendar.setPreferredSize(new Dimension(800, 735));
	    this.add(calendar);
	}
}
