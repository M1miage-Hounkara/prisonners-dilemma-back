package fr.uga.l3miage.pc.prisonersdilemma.application.ports.inputs;

import java.util.List;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.TypeStrategy;
import fr.uga.l3miage.pc.prisonersdilemma.domain.exceptions.GameNotInitializedException;
import fr.uga.l3miage.pc.prisonersdilemma.domain.exceptions.MaximumPlayersReachedException;


public interface IPartieService {
    void demarrerPartie(int nbTours);
    void addPlayer(String pseudo, boolean isConnected, String strategy) throws MaximumPlayersReachedException, GameNotInitializedException;
    void abandonner(String pseudo, TypeStrategy typeStrategy) throws IllegalArgumentException, GameNotInitializedException;
    boolean soumettreDecision(String pseudo, Decision decision) throws GameNotInitializedException;
    boolean getGameStatus() throws GameNotInitializedException;
    Integer getScore(String pseudo) throws GameNotInitializedException;
    Integer getWinner(String pseudo) throws GameNotInitializedException;
    boolean peutJouerTour() throws GameNotInitializedException;
    int getNumberOfPlayers() throws GameNotInitializedException;
    boolean getDecisionOfOtherPlayer(String pseudo) throws GameNotInitializedException;
    List<Decision> getHistorique(String pseudo) throws GameNotInitializedException;
    void terminerPartie();
}
