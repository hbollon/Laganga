package view;

import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.LoginButtonListener;
import model.LocalUser;

public class LoginWin extends JFrame implements Observer {
	private static final long serialVersionUID = 4411916212116263837L;
	
	private LocalUser localUser;
	
	private JLabel status = new JLabel("Bienvenue dans l'Aganga ! Merci de bien vouloir vous connecter.", JLabel.CENTER);
	private JButton login = new JButton("Connexion");
	
	public LoginWin(LocalUser localUser) {
		this.localUser = localUser;
		localUser.addObserver(this);
		
		setTitle("Laganga - Connexion");
		setSize(600, 200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setContentPane(getWindowPane());
		
		setVisible(true);
	}
	
	private JPanel getWindowPane() {
		JPanel panel = new JPanel(new GridLayout(6, 1, 5, 5));
		
		panel.add(status);
		
		panel.add(new JLabel("Adresse E-mail :", JLabel.CENTER));
		JTextField emailField = new JTextField();
		panel.add(emailField);
		
		panel.add(new JLabel("Mot de passe :", JLabel.CENTER));
		JPasswordField passwordField = new JPasswordField();
		panel.add(passwordField);
		
		login.addActionListener(new LoginButtonListener(localUser, login, emailField, passwordField));
		panel.add(login);
		
		return panel;
	}
	
	@Override
	public void update(Observable arg0, Object state) {
		login.setText("Connexion");
		
		switch ((int) state) {
			case LocalUser.SUCCESS:
				// Fermeture de la fenêtre de connexion
				dispose();
				
				try {
					// Fenêtre principale
					MainWin fenetre;
					fenetre = new MainWin();
					fenetre.setVisible(true);
					
					// Calendrier de la fenêtre principale
					MainWinCalendar calendar = new MainWinCalendar(fenetre);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
			
			case LocalUser.ERROR_ALREADY_LOGGED_IN:
				status.setText("Erreur : vous êtes déjà connecté (wtf ?)");
			break;
			
			case LocalUser.ERROR_NO_SUCH_USER:
				status.setText("Erreur : cet E-mail ne correspond à aucun compte d'utilisateur !");
			break;
			
			case LocalUser.ERROR_WRONG_PASSWORD:
				status.setText("Erreur : le mot de passe est incorrect !");
			break;
		}
	}
}