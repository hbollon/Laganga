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
import javax.swing.JTextField;

import controller.AnnuleEvent;
import controller.CloseWindow;

public class WinCreatEvent extends JFrame{

	public WinCreatEvent() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		this.setTitle("Création événement");
	    this.setSize(1000, 500);
	  
		this.setBackground(Color.white);
		
		this.setLayout(new BorderLayout());
		
		JPanel newEvent = new JPanel(new GridLayout(4,2));
		
		JPanel name = new JPanel(new GridLayout(1,2));
		JLabel labelName = new JLabel("Nom de l'événement : ");
		JTextField textName = new JTextField();
		name.add(labelName);
		name.add(textName);
		
		JPanel begin = new JPanel(new GridLayout(1,3));
		JLabel labelBegin = new JLabel("Début de l'événement : ");
		JTextField dateBegin = new JTextField();
		JTextField timeBegin = new JTextField();
		begin.add(labelBegin);
		begin.add(dateBegin);
		begin.add(timeBegin);
		
		JPanel end = new JPanel(new GridLayout(1,3));
		JLabel labelEnd = new JLabel("Fin de l'événement : ");
		JTextField textEnd = new JTextField();
		JTextField dateEnd = new JTextField();
		JTextField timeEnd = new JTextField();
		end.add(labelEnd);
		end.add(dateEnd);
		end.add(timeEnd);
		
		JPanel description = new JPanel(new GridLayout(1,2));
		JLabel labelDescription = new JLabel("Description de l'énévement : ");
		JTextField textDescription = new JTextField();
		description.add(labelDescription);
		description.add(textDescription);
		
		newEvent.add(name);
		newEvent.add(begin);
		newEvent.add(end);
		newEvent.add(description);
		
		this.add(newEvent, BorderLayout.CENTER);
		
		JPanel buttonPane = new JPanel();
		JButton annuleEvent = new JButton("Annulé");
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
