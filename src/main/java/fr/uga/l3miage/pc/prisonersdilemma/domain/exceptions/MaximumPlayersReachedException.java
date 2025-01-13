package fr.uga.l3miage.pc.prisonersdilemma.domain.exceptions;

public class MaximumPlayersReachedException extends Exception {
    public MaximumPlayersReachedException() {
        super("La partie est pleine, réessaie plus tard");
    }

    public MaximumPlayersReachedException(String message) {
        super(message);
    }
}