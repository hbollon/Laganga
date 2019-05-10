package model;

import com.mindfusion.scheduling.model.Appointment;

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
	private Group groupe;
	
	public EventCalendar()
	{
		super();
		_kept = true;
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
}
