package model;

/**
 * Le LocalUser représente l'utilisateur connecté (ou non) localement
 * 
 * @author Julien Valverdé
 */
public class LocalUser {
	private User user;
	
	public LocalUser() {
	}
	
	public int login(String email, String password) throws Exception {
		// Récupération de l'utilisateur de l'email donné
		Object[] values = {email};
		String[] types = {"String"};
		
		User user = (User) User.factory.getOne("WHERE `user_email` = ?", values, types);
		
		// Cet utilisateur n'existe pas
		if (user == null)
			return 1;
		
		// Vérification du mot de passe
		
		
		return 0;
	}
}
