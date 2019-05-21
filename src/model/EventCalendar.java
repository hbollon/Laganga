package model;

import com.mindfusion.common.DateTime;
import com.mindfusion.scheduling.model.Appointment;
import com.mindfusion.scheduling.model.Contact;
import com.mindfusion.scheduling.model.Resource;

import model.entities.Event;
import model.entities.Group;

/**
 * Classe permettant de créé un objet évènement pour l'interface graphique
 * Hérite de la classe Appointement de la lib mindfusion.scheduling
 * 
 * @author hbollon
 *
 */

public class EventCalendar extends Appointment {
	//Attribut indiquant si l'évènement a été gardé ou pas
	private boolean _kept;
	private String _customField;
	private Group groupe;
	private Event event;
	
	public EventCalendar(Event ev)
	{
		super();
		this.event = ev;
		_kept = true;
		_customField = DateTime.now().toString("HH:mm a");
	}
	
	public EventCalendar() {
		super();
		_kept = true;
		_customField = DateTime.now().toString("HH:mm a");
	}

	public boolean getKept()
	{
		return _kept;
	}

	public void setKept(boolean value)
	{
		_kept = value;
	}
	
	public void setGroup(Group value)
	{
		groupe = value;
	}
	

	/**
	 * Fonction clonant un object (en locurence un event)
	 */
	public Object Clone()
	{
		EventCalendar clone = new EventCalendar(event);

		clone.setAllDayEvent(this.getAllDayEvent());
		clone.setDescriptionText(this.getDescriptionText());
		clone.setEndTime(this.getEndTime());
		clone.setHeaderText(this.getHeaderText());
		clone.setLocation(this.getLocation());
		clone.setLocked(this.getLocked());
		clone.setPriority(this.getPriority());
		clone.setReminder(this.getReminder());
		// TODO: implement
		//clone.setSelectedStyle(new Style(this.getSelectedStyle(), this.getSelectedStyle().));
		clone.setStartTime(this.getStartTime());
		clone.setStyle(this.getStyle().cloneShallow());
		clone.setTag(this.getTag());
		clone.setTask(this.getTask());
		clone.setVisible(this.getVisible());

		for (Resource resource : this.getResources())
			clone.getResources().add(resource);

		for (Contact contact : this.getContacts())
			clone.getContacts().add(contact);

		clone.setCustomField(this.getCustomField());

		return clone;
	}

	public Event getEvent()
	{
		return event;
	}
	
	public String getCustomField()
	{
		return _customField;
	}

	public void setCustomField(String value)
	{
		_customField = value;

		if (getRecurrence() != null)
			getRecurrence().markException(this, false);
	}
}
