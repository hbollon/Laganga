package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import model.entities.User;

/**
 * Le LocalUser représente l'utilisateur connecté (ou non) localement
 * 
 * @author Julien Valverdé
 */
@SuppressWarnings("deprecation")
public class LocalUser extends Observable {
	// Objet principal
	public static LocalUser localUser = new LocalUser();
	
	// États de connexion
	public static final int SUCCESS = 0;
	public static final int ERROR_ALREADY_LOGGED_IN = 1;
	public static final int ERROR_NO_SUCH_USER = 2;
	public static final int ERROR_WRONG_PASSWORD = 3;
	
	private User user;
	public User getUser() {
		return user;
	}
	
	public boolean isLoggedIn() {
		return (user != null);
	}
	
	// Champs à binder pour la requête de connexion
	private static FieldsList loginQueryFields = new FieldsList();
	static {
		loginQueryFields.add("email", "String");
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
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("email", email);
			
			User user = (User) User.factory.getSingle("WHERE `user_email` = ?", loginQueryFields, values);
			
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