package view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.LoginButtonListener;
import model.LocalUser;

public class LoginWin extends JFrame {
	private static final long serialVersionUID = 4411916212116263837L;
	
	private LocalUser localUser;
	
	private JTextField emailField = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	
	public JTextField getEmailField() {
		return emailField;
	}
	public JPasswordField getPasswordField() {
		return passwordField;
	}
	
	public LoginWin(LocalUser localUser) {
		this.localUser = localUser;
		
		setTitle("Laganga - Connexion");
		setSize(600, 200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setContentPane(getWindowPane());
		
		setVisible(true);
	}
	
	private JPanel getWindowPane() {
		JPanel panel = new JPanel(new GridLayout(6, 1, 5, 5));
		
		panel.add(new JLabel("Bienvenue dans l'Aganga ! Merci de bien vouloir vous connecter.", JLabel.CENTER));
		
		panel.add(new JLabel("Adresse E-mail :", JLabel.CENTER));
		panel.add(emailField);
		
		panel.add(new JLabel("Mot de passe :", JLabel.CENTER));
		panel.add(passwordField);
		
		JButton login = new JButton("Connexion");
		login.addActionListener(new LoginButtonListener(localUser, emailField, passwordField));
		panel.add(login);
		
		return panel;
	}
}