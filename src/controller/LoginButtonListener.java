package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.LocalUser;

public class LoginButtonListener implements ActionListener {
	private LocalUser localUser;
	private JButton login;
	private JTextField emailField;
	private JPasswordField passwordField;
	
	public LoginButtonListener(LocalUser localUser, JButton login, JTextField emailField, JPasswordField passwordField) {
		this.localUser = localUser;
		this.login = login;
		this.emailField = emailField;
		this.passwordField = passwordField;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		login.setText("Connexion en cours...");
		
		try {
			localUser.login(emailField.getText(), passwordField.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}