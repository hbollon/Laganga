package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.*;

public class Mois extends JPanel {
	private JTable tableau;
	private JScrollPane tableContainer;
	private Date date = new Date();
		
	public Mois() {
		super(new FlowLayout(FlowLayout.CENTER));
		
		DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
		JLabel label_date = new JLabel(shortDateFormat.format(date));
		this.add(label_date, BorderLayout.CENTER);
		
		String  title[] = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
		Object[][] data = { 
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""}
		};
		
		tableau = new JTable(data, title);
	    tableContainer = new JScrollPane(tableau);
		this.add(tableContainer, BorderLayout.CENTER);
	}
}
