package model.exceptions;

/*
 * Cette exception est levée lorsqu'un utilisateur est ajouté comme participant à un évènement alors qu'il participe déjà à un autre évènement au même moment.
 */
public class UserOccupiedException extends Exception {
	private static final long serialVersionUID = -3043823676160257202L;
	
	public UserOccupiedException() {
		super("L'un des participants prend déjà part à un évènement au même moment.");
	}
}