package fr.uga.l3miage.pc.prisonersdilemma.services;

import java.util.List;

import org.springframework.boot.autoconfigure.web.WebProperties.Resources.Chain.Strategy;
import org.springframework.stereotype.Service;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.enums.TypeStrategy;
import fr.uga.l3miage.pc.prisonersdilemma.exceptions.GameNotInitializedException;
import fr.uga.l3miage.pc.prisonersdilemma.exceptions.MaximumPlayersReachedException;
import fr.uga.l3miage.pc.prisonersdilemma.modules.Joueur;
import fr.uga.l3miage.pc.prisonersdilemma.modules.Partie;

@Service
public class PartiesService {
    private Partie partie;

    public void demarrerPartie(int nbTours) {
        this.partie = new Partie(nbTours);
    }

    public boolean isGameStarted() {
        return partie != null;
    }

    public void addPlayer(String pseudo, boolean isConnected, String strategy) throws MaximumPlayersReachedException {
        if (partie == null) {
            throw new IllegalStateException("La partie n'a pas été initialisée. Veuillez démarrer une nouvelle partie.");
        }

        
        if (partie.getNbJoueurs() < 2) {
            partie.addJoueur(pseudo, isConnected, TypeStrategy.valueOf(strategy));
        } else {
            throw new MaximumPlayersReachedException();
        }
    }

    public void abandonner(String pseudo, TypeStrategy typeStrategy) {
        Joueur joueur = partie.getJoueurs().stream()
                .filter(j -> j.getName().equals(pseudo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Joueur non trouvé: " + pseudo));
        partie.abandonner(joueur, typeStrategy);
    }

    public boolean soumettreDecision(String pseudo, Decision decision) throws GameNotInitializedException {
        if (partie == null) {
            throw new GameNotInitializedException();
        }
        return partie.soumettreDecision(pseudo, decision);
    }


    public boolean getGameStatus() {
        return partie.isPartieTerminee();
     }

     public Integer getScore(String pseudo) {
        return partie.getScore(pseudo);
     }

    public Integer getWinner(String pseudo) {
        return partie.getWinner(pseudo);
    }

    
    public boolean peutJouerTour() throws GameNotInitializedException {
        if (partie == null) {
            throw new GameNotInitializedException();
        }
        return partie.peutJouerTour();
    }

    public int getNumberOfPlayers() {
        return partie.getNbJoueurs(); 
    }

    public boolean getDecisionOfOtherPlayer(String pseudo) {
        return partie.getDecisionOfOtherPlayer(pseudo);
    }


    public List<Decision> getHistorique(String pseudo) throws GameNotInitializedException {
        if (!isGameStarted()) {
            throw new GameNotInitializedException("La partie n'est pas initialisée.");
        }
    
        return partie.getHistorique(pseudo);
    }
    
}
