package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

public class ControllerWeek implements ActionListener {
	private JPanel week;
	private JPanel month;
	private JPanel year;
	
	public ControllerWeek(JPanel week, JPanel month,JPanel year) {
		this.week = week;
		this.month = month;
		this.year = year;
	}
	
	public void actionPerformed(ActionEvent e) {
		week.setVisible(true);
		month.setVisible(false);
		year.setVisible(false);
	}
}
