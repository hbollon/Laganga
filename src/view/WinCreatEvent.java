package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import com.toedter.calendar.JCalendar;

import controller.AnnuleEvent;
import controller.CloseWindow;

public class WinCreatEvent extends JFrame{

	public WinCreatEvent() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		this.setTitle("Création événement");
	    this.setSize(1000, 500);
	  
		this.setBackground(Color.white);
		
		this.setLayout(new BorderLayout());
		
		//Panel au centre de la fenêtre
		JPanel newEvent = new JPanel(new GridLayout(4,2));
		
		//Panel pour le nom de l'événement
		JPanel name = new JPanel(new GridLayout(1,2));
		JLabel labelName = new JLabel("Nom de l'événement : ");
		JTextField textName = new JTextField();
		name.add(labelName);
		name.add(textName);
		
		//Panel début de l'événement
		JPanel dateBegin = new JPanel(new GridLayout(1,3));
		
		//Date
		JLabel labelBegin = new JLabel("Début de l'événement : ");
		JCalendar calendarBegin = new JCalendar();
		
		//Heure
		JPanel timeBegin = new JPanel(new FlowLayout());
		JSpinner hourBegin = new JSpinner(new SpinnerNumberModel(00, 00, 23, 1));
		JSpinner minuteBegin = new JSpinner(new SpinnerNumberModel(00, 00, 59, 1));
		timeBegin.add(hourBegin);
		timeBegin.add(minuteBegin);
		
		dateBegin.add(labelBegin);
		dateBegin.add(calendarBegin);
		dateBegin.add(timeBegin);
		
		//Panel fin de l'événement
		JPanel dateEnd = new JPanel(new GridLayout(1,3));
		//Date
		JLabel labelEnd = new JLabel("Fin de l'événement : ");
		JCalendar calendarEnd = new JCalendar();
		
		//Heure
		JPanel timeEnd = new JPanel(new FlowLayout());
		JSpinner hourEnd = new JSpinner(new SpinnerNumberModel(00, 00, 23, 1));
		JSpinner minuteEnd = new JSpinner(new SpinnerNumberModel(00, 00, 59, 1));
		timeEnd.add(hourEnd);
		timeEnd.add(minuteEnd);
		
		dateEnd.add(labelEnd);
		dateEnd.add(calendarEnd);
		dateEnd.add(timeEnd);
		
		//Panel description
		JPanel description = new JPanel(new GridLayout(1,2));
		JLabel labelDescription = new JLabel("Description de l'énévement : ");
		JTextField textDescription = new JTextField();
		description.add(labelDescription);
		description.add(textDescription);
		
		newEvent.add(name);
		newEvent.add(dateBegin);
		newEvent.add(dateEnd);
		newEvent.add(description);
		
		this.add(newEvent, BorderLayout.CENTER);
		
		//Panel bouton
		JPanel buttonPane = new JPanel();
		JButton annuleEvent = new JButton("Annuler");
		JButton ajoutEvent = new JButton("Ajouter l'événement");
		buttonPane.add(annuleEvent);
		buttonPane.add(ajoutEvent);
		this.add(buttonPane, BorderLayout.SOUTH);

	    this.setLocationRelativeTo(null);
	    this.setVisible(true);
	    
	    annuleEvent.addActionListener(new AnnuleEvent(this));
	    this.addWindowListener(new CloseWindow(this));
	}
}
