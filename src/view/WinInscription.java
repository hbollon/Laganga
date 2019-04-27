package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.CloseWindow;
import controller.Inscription;

public class WinInscription extends JFrame {

	public WinInscription() {
		super();
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		this.setTitle("Insription");
	    this.setSize(600, 250);
	  
		this.setBackground(Color.white);
		
		this.setLayout(new BorderLayout());
		JPanel panelCenter = new JPanel(new GridLayout(3,1));
		
		JPanel panelName = new JPanel(new FlowLayout());
		JLabel labelFirstName = new JLabel("Prénom : ");
		JTextField firstName = new JTextField();
		firstName.setPreferredSize(new Dimension(100, 24));
		JLabel labelName = new JLabel("Nom de famille : ");
		JTextField name = new JTextField();
		name.setPreferredSize(new Dimension(100, 24));
		panelName.add(labelFirstName);
		panelName.add(firstName);
		panelName.add(labelName);
		panelName.add(name);
		
		JPanel adresseMail = new JPanel(new FlowLayout());
		JLabel labelMail = new JLabel("Adresse mail : ");
		JTextField mail = new JTextField();
		mail.setPreferredSize(new Dimension(100, 24));
		adresseMail.add(labelMail);
		adresseMail.add(mail);
		
		JPanel password = new JPanel(new FlowLayout());
		JLabel labelmdp = new JLabel("Mot de passe : ");
		JTextField mdp = new JTextField();
		mdp.setPreferredSize(new Dimension(100, 24));
		password.add(labelmdp);
		password.add(mdp);
		
		JButton inscription = new JButton("S'inscrire");
		
		panelCenter.add(panelName);
		panelCenter.add(adresseMail);
		panelCenter.add(password);
		this.add(panelCenter, BorderLayout.CENTER);
		this.add(inscription, BorderLayout.SOUTH);
		

		
		this.setVisible(true);
		
		inscription.addActionListener(new Inscription());
		this.addWindowListener(new CloseWindow(this));
	}
}
