package model;

import java.util.Observable;

/**
 * Le LocalUser représente l'utilisateur connecté (ou non) localement
 * 
 * @author Julien Valverdé
 */
public class LocalUser extends Observable {
	// États de connexion
	public static final int SUCCESS = 0;
	public static final int ERROR_ALREADY_LOGGED_IN = 1;
	public static final int ERROR_NO_SUCH_USER = 2;
	public static final int ERROR_WRONG_PASSWORD = 3;
	
	private User user;
	public User getUser() {
		return user;
	}
	
	public LocalUser() {
	}
	
	public boolean isLoggedIn() {
		return (user != null);
	}
	
	/**
	 * Permet de connecter un utilisateur localement
	 * 
	 * @param email
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public void login(String email, String password) throws Exception {
		int state;
		
		// Si l'utilisateur est déjà connecté
		if (user != null)
			state = ERROR_ALREADY_LOGGED_IN;
		
		else {
			// Récupération de l'utilisateur à partir de l'email donné
			Object[] values = {email};
			String[] types = {"String"};
			
			User user = (User) User.factory.getSingle("WHERE `user_email` = ?", values, types);
			
			// Cet utilisateur n'existe pas
			if (user == null)
				state = ERROR_NO_SUCH_USER;
			
			else {
				// Vérification du mot de passe
				if (!testPassword(password, (String) user.getPassword()))
					state = ERROR_WRONG_PASSWORD;
				
				else {
					// Connexion réussie
					this.user = user;
					
					state = SUCCESS;
				}
			}
		}
		
		setChanged();
		notifyObservers(state);
	}
	
	private String hashPassword(String password) {
		return password;
	}
	
	private boolean testPassword(String password, String hash) {
		return password.equals(hash);
	}
}