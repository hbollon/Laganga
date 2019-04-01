package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.LocalUser;

public class LoginButtonListener implements ActionListener {
	private LocalUser localUser;
	private JTextField emailField;
	private JPasswordField passwordField;
	
	public LoginButtonListener(LocalUser localUser, JTextField emailField, JPasswordField passwordField) {
		this.localUser = localUser;
		this.emailField = emailField;
		this.passwordField = passwordField;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			localUser.login(emailField.getText(), passwordField.getText());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}