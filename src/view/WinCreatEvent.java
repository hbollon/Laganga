package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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

public class WinCreatEvent extends JFrame implements Observer {

	private JLabel status = new JLabel("Ajouter un événement", JLabel.CENTER);
			
	public WinCreatEvent() {
		super();
		
		this.setTitle("Création événement");
	    this.setSize(1000, 700);
		this.setBackground(Color.white);
		this.setLayout(new BorderLayout());
		
		this.add(status, BorderLayout.NORTH);
		
		//Panel au centre de la fenêtre
		JPanel newEvent = new JPanel(new GridLayout(5,2));
		
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
		
		//CheckBox pour si les detailles de l'événement est visible ou non
		JPanel eventVisible = new JPanel(new GridLayout(1,2));
		JLabel labelVisible = new JLabel("Descrition de l'événement caché : ");
		JCheckBox checkVisible = new JCheckBox();
		eventVisible.add(labelVisible);
		eventVisible.add(checkVisible);
		
		newEvent.add(name);
		newEvent.add(dateBegin);
		newEvent.add(dateEnd);
		newEvent.add(description);
		newEvent.add(eventVisible);
		
		
		//Panel bouton
		JPanel buttonPane = new JPanel();
		JButton annuleEvent = new JButton("Annuler");
		JButton ajoutEvent = new JButton("Ajouter l'événement");
		buttonPane.add(annuleEvent);
		buttonPane.add(ajoutEvent);

	    this.setLocationRelativeTo(null);
	    
	    annuleEvent.addActionListener(new AnnuleEvent(this));
	    
	    this.add(newEvent, BorderLayout.CENTER);
	    this.add(buttonPane, BorderLayout.SOUTH);
	    this.setVisible(true);
	    this.addWindowListener(new CloseWindow(this));
	}

	@Override
	public void update(Observable arg0, Object state) {
//		switch((int) state) {
//			case success:
//				this.dispose();
//				break;
//				
//			case gange:
				status.setText("Cette date n'est pas libre");
		}
}
