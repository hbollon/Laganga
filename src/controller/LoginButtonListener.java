package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.LocalUser;

public class LoginButtonListener implements ActionListener {
	private JButton login;
	private JTextField emailField;
	private JPasswordField passwordField;
	
	public LoginButtonListener(JButton login, JTextField emailField, JPasswordField passwordField) {
		this.login = login;
		this.emailField = emailField;
		this.passwordField = passwordField;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent arg0) {
		login.setText("Connexion en cours...");
		
		try {
			LocalUser.localUser.login(emailField.getText(), passwordField.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}