package view;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Dimension2D;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Date;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import com.mindfusion.common.DateTime;
import com.mindfusion.drawing.AwtGraphics;
import com.mindfusion.drawing.Brushes;
import com.mindfusion.drawing.Pens;
import com.mindfusion.scheduling.*;
import com.toedter.calendar.JCalendar;

import model.Agenda;
import model.EventCalendar;
import model.entities.Entity;
import model.entities.Event;
import model.entities.Location;

/**
 * Classe Mois héritant de JPanel permettant d'afficher une instance de Calendar contenue dans un JPanel
 * 
 * @author hbollon
 *
 */

@SuppressWarnings("deprecation")
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
		
		Agenda.agenda.addObserver(this);
		try {
			Agenda.agenda.refresh();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
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
	
	public void addEventBD(String name, String desc, JCalendar dateBegin, JCalendar dateEnd, int timeHourBegin,
			int timeMinuteBegin, int timeHourEnd, int timeMinuteEnd)
	{
		Map<String, Object> values = new HashMap<String, Object>();
		LocalDate dateB = dateBegin.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate dateE = dateEnd.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		values.put("name", name);
		values.put("type", desc);
		values.put("priority", 1);
		values.put("begin", new GregorianCalendar(dateB.getYear(), dateB.getMonthValue() - 1, dateB.getDayOfMonth(), timeHourBegin, timeMinuteBegin));
		values.put("end", new GregorianCalendar(dateE.getYear(), dateE.getMonthValue() - 1, dateE.getDayOfMonth(), timeHourEnd, timeMinuteEnd));
		try {
			values.put("location", Location.factory.getByID(2));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Event.factory.insert(values);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		addEventCalendar(name, desc, dateBegin, dateEnd, timeHourBegin, timeMinuteBegin, timeHourEnd, timeMinuteEnd);
	}
	
	public void addEventCalendar(String name, String desc, JCalendar dateBegin, JCalendar dateEnd, int timeHourBegin,
			int timeMinuteBegin, int timeHourEnd, int timeMinuteEnd)
	{
		LocalDate dateB = dateBegin.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate dateE = dateEnd.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		if(calendar != null)
		{
			EventCalendar newEvent = new EventCalendar();
			
	        newEvent.setHeaderText(name);
	        newEvent.setDescriptionText(desc);
	        newEvent.setStartTime(new DateTime(dateB.getYear(), dateB.getMonthValue(), dateB.getDayOfMonth(), timeHourBegin, timeMinuteBegin, 00));
	        newEvent.setEndTime(new DateTime(dateE.getYear(), dateE.getMonthValue(), dateE.getDayOfMonth(), timeHourEnd, timeMinuteEnd, 00));
	        
	        calendar.getSchedule().getItems().add(newEvent);
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		List<Entity> listeEvent= Agenda.agenda.getEvents();
		
		for(int i = 0; i < listeEvent.size(); i++)
		{
			System.out.println((Event) listeEvent.get(i));
			Event ev = (Event)listeEvent.get(i);
			JCalendar dateBegin = new JCalendar(ev.getBegin().getTime());
			JCalendar dateEnd = new JCalendar(ev.getEnd().getTime());
			addEventCalendar(ev.getName(), ev.getType(), dateBegin, dateEnd, ev.getBegin().get(java.util.Calendar.HOUR_OF_DAY), ev.getBegin().get(java.util.Calendar.MINUTE), ev.getEnd().get(java.util.Calendar.HOUR_OF_DAY), ev.getEnd().get(java.util.Calendar.MINUTE));
		}
	}
}