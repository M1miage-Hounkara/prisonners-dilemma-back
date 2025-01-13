package fr.uga.l3miage.pc.prisonersdilemma.domain.exceptions;

public class GameNotInitializedException extends Exception {
    public GameNotInitializedException() {
        super("La partie n'est pas initialisée");
    }

    public GameNotInitializedException(String message) {
        super(message);
    }

}
