package view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import com.mindfusion.scheduling.CalendarView;

/**
 * Classe Semaine héritant de JPanel permettant d'afficher une instance de Calendar contenue dans un JPanel
 * 
 * @author hbollon
 *
 */

public class Semaine extends JPanel{
	private static final long serialVersionUID = 6569641352308296962L;
	private view.Planner calendar;;
	
	public Semaine() {
		super(new BorderLayout());
		
		setSize(368, 362);
		calendar = new view.Planner(CalendarView.List);
		this.add(calendar, BorderLayout.CENTER);
	}
	
	public view.Planner getCalendar()
	{
		return calendar;
	}
}