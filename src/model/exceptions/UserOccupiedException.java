package model.exceptions;

public class UserOccupiedException extends Exception {
	private static final long serialVersionUID = -3043823676160257202L;
	
	public UserOccupiedException() {
		super("L'un des participants participe déjà à un évènement à ce moment.");
	}
}