package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

import controller.CloseWindow;
import controller.Inscription;

public class WinInscription extends JFrame {

	private static final long serialVersionUID = -5918855599444972396L;
	
	private JTextField firstName;
	private JTextField name;
	private JTextField mail;
	private JTextField mdp;

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
		firstName = new JTextField();
		firstName.setPreferredSize(new Dimension(100, 24));
		JLabel labelName = new JLabel("Nom de famille : ");
		name = new JTextField();
		name.setPreferredSize(new Dimension(100, 24));
		panelName.add(labelFirstName);
		panelName.add(firstName);
		panelName.add(labelName);
		panelName.add(name);
		
		JPanel adresseMail = new JPanel(new FlowLayout());
		JLabel labelMail = new JLabel("Adresse mail : ");
		mail = new JTextField();
		mail.setPreferredSize(new Dimension(100, 24));
		adresseMail.add(labelMail);
		adresseMail.add(mail);
		
		JPanel password = new JPanel(new FlowLayout());
		JLabel labelmdp = new JLabel("Mot de passe : ");
		mdp = new JTextField();
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
		
		inscription.addActionListener(new Inscription(this));
		this.addWindowListener(new CloseWindow(this));
	}
	
	public String getFirstName()
	{
		return ((JTextField)firstName).getText();
	}
	
	public String getName()
	{
		return ((JTextField)name).getText();
	}
	
	public String getMail()
	{
		return ((JTextField)mail).getText();
	}
	
	public String getmdp()
	{
		return ((JTextField)mdp).getText();
	}
}
