package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import model.entities.User;
import view.MainWin;
import view.WinInscription;

public class Inscription implements ActionListener {

	private WinInscription win;
	
	private String nom;
	private String prenom;
	private String email;
	private String mdp;
	
	
	public Inscription(WinInscription win) {
		this.win = win;
	}

	public void actionPerformed(ActionEvent e) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("firstName", win.getFirstName());
		values.put("lastName", win.getName());
		values.put("email", win.getMail());
		values.put("password", win.getmdp());
		values.put("birth", Calendar.getInstance());
		
		try {
			User.factory.insert(values);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		win.dispose();
	}
}