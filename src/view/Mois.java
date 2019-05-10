package view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.mindfusion.common.DateTime;
import com.mindfusion.scheduling.*;
import com.mindfusion.scheduling.model.*;

import model.EventCalendar;

/**
 * Classe Mois permettant d'afficher une instance de Calendar 
 * 
 * @author hbollon
 *
 */

public class Mois extends JPanel {
		
	public Mois() {
		super(new BorderLayout());
		
		setSize(368, 362);

		Calendar calendar = new Calendar();
		calendar.setTheme(ThemeType.Light);
		calendar.setInteractiveItemType(EventCalendar.class);
				
		this.add(calendar, BorderLayout.CENTER);

        // Initialize the date file
        String _dataFile = new java.io.File("Schedule.dat").getAbsolutePath();
        if (new java.io.File(_dataFile).exists())
			calendar.getSchedule().loadFrom(_dataFile, ContentType.Xml);
        calendar.getSchedule().saveTo(_dataFile, ContentType.Xml);
        
        // Test temporaire
        EventCalendar newEvent = new EventCalendar();
        newEvent.setHeaderText("test");
        newEvent.setDescriptionText("test description");
        newEvent.setStartTime(new DateTime(2019, 8, 12, 8, 00, 00));
        newEvent.setEndTime(new DateTime(2019, 8, 12, 12, 00, 00));
        
        calendar.getSchedule().getItems().add(newEvent);
	}
}
