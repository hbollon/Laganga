package view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import com.mindfusion.scheduling.CalendarView;

/**
 * Classe Mois héritant de JPanel permettant d'afficher une instance de Calendar contenue dans un JPanel
 * 
 * @author hbollon
 *
 */

public class Mois extends JPanel{
	private view.Planner calendar;;
	private static final long serialVersionUID = 1L;
	
	public Mois() {
		super(new BorderLayout());
		
		setSize(368, 362);
		calendar = new view.Planner(CalendarView.SingleMonth);
		this.add(calendar, BorderLayout.CENTER);
	}
	
	public view.Planner getCalendar()
	{
		return calendar;
	}
}