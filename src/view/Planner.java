package view;

import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Dimension2D;
import java.sql.SQLException;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.mindfusion.common.DateTime;
import com.mindfusion.drawing.AwtGraphics;
import com.mindfusion.drawing.Brushes;
import com.mindfusion.drawing.Pens;
import com.mindfusion.scheduling.CalendarAdapter;
import com.mindfusion.scheduling.CalendarDrawEvent;
import com.mindfusion.scheduling.CalendarView;
import com.mindfusion.scheduling.CustomDrawElements;
import com.mindfusion.scheduling.ItemMouseEvent;
import com.mindfusion.scheduling.ThemeType;
import com.mindfusion.scheduling.WeekRangeHeaderStyle;

import model.Agenda;
import model.EventCalendar;
import model.entities.Entity;
import model.entities.Event;
import model.entities.Location;
import model.entities.User;
import model.exceptions.ParticipantsBusyException;
import view.tabs.EventTab;

/**
 * Classe Planner héritant de Calendar (lib mindfusion) permettant d'afficher une instance de Calendar personnalisé
 * 
 * @author hbollon
 *
 */

public class Planner extends com.mindfusion.scheduling.Calendar implements Observer {
	private static final long serialVersionUID = 426476155072439893L;
	
	public Planner(CalendarView test) {
		super();
		
		//Début initialisation du Calendar
		this.beginInit();
		this.setCurrentView(test);
		this.getWeekRangeSettings().setHeaderStyle(EnumSet.of(WeekRangeHeaderStyle.Title));
		this.setTheme(ThemeType.Light);
		this.setInteractiveItemType(EventCalendar.class);
		this.setCustomDraw(EnumSet.of(CustomDrawElements.MonthRangeHeader));
		this.endInit();
		
		//Ajout listeners
		this.addCalendarListener(new CalendarAdapter(){
			public void draw(CalendarDrawEvent e) {
				calendarDraw(e);
			}
			public void itemClick(ItemMouseEvent e) {
				calendarItemClicked(e);
			}
		});
		
		Agenda.agenda.addObserver(this);

	}
	
	//Listener pour ouvrir une fenetre quand on clique su un évènement
	protected void calendarItemClicked(ItemMouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON1 && e.getClicks() > 1)
		{
			this.resetDrag();
			try {
				EventTab eventTab = new EventTab((Event) Event.factory.getByID(((EventCalendar)e.getItem()).getEvent().getID()));
				eventTab.switchTo();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	//Méthode qui redéssine le Calendar
	protected void calendarDraw(CalendarDrawEvent e)
	{
		if (e.getElement() == CustomDrawElements.TimetableItem)
		{
			AwtGraphics g = new AwtGraphics(e.getGraphics());

			EventCalendar myEvent = (EventCalendar)this.getSchedule().getItems().get(e.getIndex());
			String customField = myEvent.getCustomField();

			Font font = e.getStyle().getFont();
			if (font == null)
				font = this.getItemSettings().getStyle().getFont();
			if (font == null)
				font = this.getTimetableSettings().getStyle().getFont();

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
	
	/* Méthode d'ajout d'évènement dans la base de donnée
	 * Elle appelle insert de la Classe event et lui passe en paramètre tout le nécéssaire pour créer une entrée dans la base
	 */
	public void addEventBD(String name, String desc, int priority, User author, GregorianCalendar dateBegin, GregorianCalendar dateEnd, boolean hide, List<Entity> groups, List<Entity> users, Location location)
	{
		try {
			Event.insert(name, desc, priority, author, dateBegin, dateEnd, location, hide, users, groups);
		} catch (ParticipantsBusyException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		try {
			Agenda.agenda.refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 *  Méthodes qui ajoute un événement localement dans le Calendar (permet d'afficher les événements)
	 */
	public void addEventCalendar(Event ev)
	{
		if(this != null)
		{
			EventCalendar newEvent = new EventCalendar(ev);
			
	        newEvent.setHeaderText(ev.getName());
	        newEvent.setDescriptionText(ev.getType());
	        newEvent.setStartTime(new DateTime(ev.getBegin().get(java.util.Calendar.YEAR), ev.getBegin().get(java.util.Calendar.MONTH) + 1, ev.getBegin().get(java.util.Calendar.DAY_OF_MONTH), ev.getBegin().get(java.util.Calendar.HOUR_OF_DAY), ev.getBegin().get(java.util.Calendar.MINUTE), 00));
	        newEvent.setEndTime(new DateTime(ev.getEnd().get(java.util.Calendar.YEAR), ev.getEnd().get(java.util.Calendar.MONTH) + 1, ev.getEnd().get(java.util.Calendar.DAY_OF_MONTH), ev.getEnd().get(java.util.Calendar.HOUR_OF_DAY), ev.getEnd().get(java.util.Calendar.MINUTE), 00));
	        
	        this.getSchedule().getItems().add(newEvent);
		}
	}
	
	public void addEventCalendar(String name, String desc, int priority, GregorianCalendar dateBegin,
			GregorianCalendar dateEnd, boolean hide) {
		if(this != null)
		{
			EventCalendar newEvent = new EventCalendar();
			
	        newEvent.setHeaderText(name);
	        newEvent.setDescriptionText(desc);
	        newEvent.setStartTime(new DateTime(dateBegin.get(java.util.Calendar.YEAR), dateBegin.get(java.util.Calendar.MONTH) + 1, dateBegin.get(java.util.Calendar.DAY_OF_MONTH), dateBegin.get(java.util.Calendar.HOUR_OF_DAY), dateBegin.get(java.util.Calendar.MINUTE), 00));
	        newEvent.setEndTime(new DateTime(dateEnd.get(java.util.Calendar.YEAR), dateEnd.get(java.util.Calendar.MONTH) + 1, dateEnd.get(java.util.Calendar.DAY_OF_MONTH), dateEnd.get(java.util.Calendar.HOUR_OF_DAY), dateEnd.get(java.util.Calendar.MINUTE), 00));
	        
	        this.getSchedule().getItems().add(newEvent);
		}
		
	}

	/*
	 * Méthode d'Observer
	 * Permet de mettre à jour le Calendar quand il est notifié
	 */
	@Override
	public void update(Observable o, Object arg) {
		List<Entity> listeEvent= Agenda.agenda.getEvents();
		this.getSchedule().getItems().clear();
		
		for(int i = 0; i < listeEvent.size(); i++)
		{
			Event ev = (Event)listeEvent.get(i);
			
			GregorianCalendar dateBegin = new GregorianCalendar(ev.getBegin().get(java.util.Calendar.YEAR), ev.getBegin().get(java.util.Calendar.MONTH) + 1, ev.getBegin().get(java.util.Calendar.DAY_OF_MONTH), ev.getBegin().get(java.util.Calendar.HOUR_OF_DAY), ev.getBegin().get(java.util.Calendar.MINUTE));
			GregorianCalendar dateEnd = new GregorianCalendar(ev.getEnd().get(java.util.Calendar.YEAR), ev.getEnd().get(java.util.Calendar.MONTH) + 1, ev.getEnd().get(java.util.Calendar.DAY_OF_MONTH), ev.getEnd().get(java.util.Calendar.HOUR_OF_DAY), ev.getEnd().get(java.util.Calendar.MINUTE));

			addEventCalendar(ev);
		}
	}
}