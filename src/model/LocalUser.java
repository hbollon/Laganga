package model;

/**
 * Le LocalUser représente l'utilisateur connecté (ou non) localement
 * 
 * @author Julien Valverdé
 */
public class LocalUser {
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
	
	/**
	 * Permet de connecter un utilisateur localement
	 * 
	 * @param email
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public int login(String email, String password) throws Exception {
		// Si l'utilisateur est déjà connecté
		if (user != null)
			return ERROR_ALREADY_LOGGED_IN;
		
		// Récupération de l'utilisateur à partir de l'email donné
		Object[] values = {email};
		String[] types = {"String"};
		
		User user = (User) User.factory.getSingle("WHERE `user_email` = ?", values, types);
		
		// Cet utilisateur n'existe pas
		if (user == null)
			return ERROR_NO_SUCH_USER;
		
		// Vérification du mot de passe
		if (!testPassword(password, (String) user.get("password")))
			return ERROR_WRONG_PASSWORD;
		
		// Connexion réussie
		this.user = user;
		return SUCCESS;
	}
	
	private String hashPassword(String password) {
		return password;
	}
	
	private boolean testPassword(String password, String hash) {
		return (password.equals(hash));
	}
}
