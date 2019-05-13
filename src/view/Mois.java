package view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.mindfusion.scheduling.*;
import com.mindfusion.scheduling.model.*;

public class Mois extends JPanel {
		
	public Mois() {
		super(new BorderLayout());
		
		/*JPanel changerMois = new JPanel(new FlowLayout());
		changerMois.add(new JButton("Mois précédent"));
		changerMois.add(new JButton("Mois suivant"));
		
	    JCalendar calendar = new JCalendar();
	    
	    this.add(changerMois, BorderLayout.NORTH);
	    this.add(calendar, BorderLayout.CENTER);*/
		
		setSize(368, 362);

		Calendar calendar = new Calendar();
		calendar.setTheme(ThemeType.Light);
		
		
		this.add(calendar, BorderLayout.CENTER);

        // Initialize the date file
        String _dataFile = new java.io.File("Schedule.dat").getAbsolutePath();
        if (new java.io.File(_dataFile).exists())
			calendar.getSchedule().loadFrom(_dataFile, ContentType.Xml);
        calendar.getSchedule().saveTo(_dataFile, ContentType.Xml);
	}
}
