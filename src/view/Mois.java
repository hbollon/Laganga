package view;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Dimension2D;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.EnumSet;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import com.mindfusion.common.DateTime;
import com.mindfusion.drawing.AwtGraphics;
import com.mindfusion.drawing.Brushes;
import com.mindfusion.drawing.Pens;
import com.mindfusion.scheduling.*;
import com.toedter.calendar.JCalendar;

import model.EventCalendar;

/**
 * Classe Mois héritant de JPanel permettant d'afficher une instance de Calendar contenue dans un JPanel
 * 
 * @author hbollon
 *
 */

public class Mois extends JPanel implements Observer {
		
	private Calendar calendar = null;
	private static final long serialVersionUID = 1L;
	
	public Mois() {
		super(new BorderLayout());
		
		setSize(368, 362);

		calendar = new Calendar();
		calendar.beginInit();
		calendar.setCurrentView(CalendarView.SingleMonth);
		calendar.getWeekRangeSettings().setHeaderStyle(EnumSet.of(WeekRangeHeaderStyle.Title));
		calendar.setTheme(ThemeType.Light);
		calendar.setInteractiveItemType(EventCalendar.class);
		calendar.setCustomDraw(EnumSet.of(CustomDrawElements.MonthRangeHeader));
		calendar.endInit();
		
		calendar.addCalendarListener(new CalendarAdapter(){
			public void draw(CalendarDrawEvent e) {
				calendarDraw(e);
			}
			public void itemClick(ItemMouseEvent e) {
				calendarItemClicked(e);
			}
		});
				
		this.add(calendar, BorderLayout.CENTER);
	}
	
	protected void calendarItemClicked(ItemMouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON1 && e.getClicks() > 1)
		{
			calendar.resetDrag();
			JOptionPane.showMessageDialog(this, ((EventCalendar)e.getItem()).getCustomField(), "Description", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	protected void calendarDraw(CalendarDrawEvent e)
	{
		if (e.getElement() == CustomDrawElements.TimetableItem)
		{
			AwtGraphics g = new AwtGraphics(e.getGraphics());

			EventCalendar myEvent = (EventCalendar)calendar.getSchedule().getItems().get(e.getIndex());
			String customField = myEvent.getCustomField();

			Font font = e.getStyle().getFont();
			if (font == null)
				font = calendar.getItemSettings().getStyle().getFont();
			if (font == null)
				font = calendar.getTimetableSettings().getStyle().getFont();

			Dimension2D size = g.measureString(customField, font);

			int width = (int)size.getWidth();
			int height = (int)size.getHeight();
			if (width > e.getBounds().width - 4)
				width = e.getBounds().width - 4;
			if (height > e.getBounds().height - 4)
				height = e.getBounds().height - 4;

			Rectangle bounds = new Rectangle(
				(int)e.getBounds().getMaxX() - width - 3,
				(int)e.getBounds().getMaxY() - height - 3,
				width, height);

			g.fillRectangle(Brushes.Yellow, bounds);
			g.drawRectangle(Pens.Black, bounds);
			g.drawString(customField, font, Brushes.Black, new Point(bounds.x, bounds.y));
		}
	}
	
	public void addEventMois(String name, String desc, JCalendar dateBegin, JCalendar dateEnd, int timeHourBegin,
			int timeMinuteBegin, int timeHourEnd, int timeMinuteEnd)
	{
		if(calendar != null)
		{
			EventCalendar newEvent = new EventCalendar();
			
			LocalDate dateB = dateBegin.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate dateE = dateEnd.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			
	        newEvent.setHeaderText(name);
	        newEvent.setDescriptionText(desc);
	        newEvent.setStartTime(new DateTime(dateB.getYear(), dateB.getMonthValue(), dateB.getDayOfMonth(), timeHourBegin, timeMinuteBegin, 00));
	        newEvent.setEndTime(new DateTime(dateE.getYear(), dateE.getMonthValue(), dateE.getDayOfMonth(), timeHourEnd, timeMinuteEnd, 00));
	        
	        calendar.getSchedule().getItems().add(newEvent); 
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
		
	}
}
