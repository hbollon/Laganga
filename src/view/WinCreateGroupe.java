package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

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
import controller.Inscription;

public class WinCreateGroupe extends JFrame implements Observer {

	private JLabel status = new JLabel("Ajouter un groupe", JLabel.CENTER);
			
	public WinCreateGroupe() {
		super();
		
		this.setTitle("Création Groupe");
	    this.setSize(1000, 500);
		this.setBackground(Color.white);
		this.setLayout(new BorderLayout());
		
		this.add(status, BorderLayout.NORTH);
		
		//Panel au centre de la fenêtre
		JPanel newEvent = new JPanel(new GridLayout(3,1));
		
		//Panel pour le nom du groupe
		
		
		JPanel nomGroupe = new JPanel(new FlowLayout());
		JLabel labelGroupe = new JLabel("Nom du Groupe : ");
		JTextField Groupe = new JTextField();
		Groupe.setPreferredSize(new Dimension(300, 24));
		nomGroupe.add(labelGroupe);
		nomGroupe.add(Groupe);
		
		JPanel membre = new JPanel(new FlowLayout());
		JLabel labelmembre = new JLabel("Ajouter Membres:");
		JTextField memb = new JTextField();
		memb.setPreferredSize(new Dimension(300, 24));
		membre.add(labelmembre);
		membre.add(memb);
		
		JButton creergroupe = new JButton("créer groupe");
		this.add(nomGroupe, BorderLayout.CENTER);
		this.add(membre, BorderLayout.SOUTH);
		
		this.setVisible(true);
		creergroupe.addActionListener(new CreateGroupe());
		this.addWindowListener(new CloseWindow(this));
		
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}