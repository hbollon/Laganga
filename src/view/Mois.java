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
		
		String  title[] = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
		Object[][] data = { 
				{"", "", "", "", "1", "2", "3"},
				{"4", "5", "6", "7", "8", "9", "10"},
				{"11", "12", "13", "14", "15", "16", "17"},
				{"18", "19", "20", "21", "22", "23", "24"},
				{"25", "26", "27", "28", "29", "30", "31"}
		};
		
		tableau = new JTable(data, title){
	        private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
	    };
	    tableContainer = new JScrollPane(tableau);
	    tableau.getTableHeader().setReorderingAllowed(false);
	    tableau.getTableHeader().setResizingAllowed(false);
		this.add(tableContainer, BorderLayout.CENTER);
	}
}
