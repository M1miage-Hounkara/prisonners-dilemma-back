package fr.uga.l3miage.pc.prisonersdilemma.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.parser.Part;
import org.springframework.stereotype.Service;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.enums.TypeStrategy;
import fr.uga.l3miage.pc.prisonersdilemma.exceptions.GameNotInitializedException;
import fr.uga.l3miage.pc.prisonersdilemma.exceptions.MaximumPlayersReachedException;
import fr.uga.l3miage.pc.prisonersdilemma.modules.Joueur;
import fr.uga.l3miage.pc.prisonersdilemma.modules.Partie;

@Service
public class PartiesService {
    private Optional<Partie> partie = Optional.empty();

    public void demarrerPartie(int nbTours) {
        if(isGameStarted()) {
            throw new IllegalStateException("Une partie est déjà en cours. Veuillez la terminer avant d'en démarrer une nouvelle.");
        }
        Partie new_partie = new Partie(nbTours);
        this.partie = Optional.of(new_partie);
    }

    public boolean isGameStarted() {
        return !partie.isEmpty();
    }

    private Partie getPartie() throws GameNotInitializedException {
        return partie.orElseThrow(() -> new GameNotInitializedException("La partie n'est pas initialisée."));
    }

    public void addPlayer(String pseudo, boolean isConnected, String strategy) throws MaximumPlayersReachedException, GameNotInitializedException {
        Partie partie = getPartie();
        
        if (partie.getNbJoueurs() < 2) {
            partie.addJoueur(pseudo, isConnected, TypeStrategy.valueOf(strategy));
        } else {
            throw new MaximumPlayersReachedException();
        }
    }

    public void abandonner(String pseudo, TypeStrategy typeStrategy) throws GameNotInitializedException {
        Partie partie = getPartie();
        Joueur joueur = partie.getJoueurs().stream()
                .filter(j -> j.getName().equals(pseudo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Joueur non trouvé: " + pseudo));
        partie.abandonner(joueur, typeStrategy);
    }

    public boolean soumettreDecision(String pseudo, Decision decision) throws GameNotInitializedException {
        Partie partie = getPartie();
        return partie.soumettreDecision(pseudo, decision);
    }


    public boolean getGameStatus() throws GameNotInitializedException {
        Partie partie = getPartie();
        return partie.isPartieTerminee();
     }

     public Integer getScore(String pseudo) throws GameNotInitializedException {
        Partie partie = getPartie();
        return partie.getScore(pseudo);
     }

    public Integer getWinner(String pseudo) throws GameNotInitializedException {
        Partie partie = getPartie();
        return partie.getWinner(pseudo);
    }

    
    public boolean peutJouerTour() throws GameNotInitializedException {
        Partie partie = getPartie();
        return partie.peutJouerTour();
    }

    public int getNumberOfPlayers() throws GameNotInitializedException {
        Partie partie = getPartie();
        return partie.getNbJoueurs(); 
    }

    public boolean getDecisionOfOtherPlayer(String pseudo) throws GameNotInitializedException {
        Partie partie = getPartie();
        return partie.getDecisionOfOtherPlayer(pseudo);
    }


    public List<Decision> getHistorique(String pseudo) throws GameNotInitializedException {
        Partie partie = getPartie();
        if (!isGameStarted()) {
            throw new GameNotInitializedException("La partie n'est pas initialisée.");
        }
    
        return partie.getHistorique(pseudo);
    }

    public void terminerPartie() {
        if (partie.isEmpty()) {
            throw new IllegalStateException("Aucune partie à terminer.");
        }
        partie = Optional.empty();
    }
    
}
