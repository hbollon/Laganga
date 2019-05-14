package model.exceptions;

import java.util.List;

import model.entities.Entity;

/*
 * Cette exception est levée lorsqu'un utilisateur est ajouté comme participant à un évènement alors qu'il participe déjà à un autre évènement au même moment.
 */
public class ParticipantsBusyException extends Exception {
	private static final long serialVersionUID = -3043823676160257202L;
	
	private List<Entity> busyParticipants;
	
	public ParticipantsBusyException(List<Entity> busyParticipants) {
		super("L'un des participants prend déjà part à un évènement au même moment.");
		
		this.busyParticipants = busyParticipants;
	}
	
	public List<Entity> getBusyParticipants() {
		return busyParticipants;
	}
}