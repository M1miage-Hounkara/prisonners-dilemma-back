package fr.uga.l3miage.pc.prisonersdilemma.application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources.Chain.Strategy;
import org.springframework.stereotype.Service;

import fr.uga.l3miage.pc.prisonersdilemma.application.ports.inputs.IPartieService;
import fr.uga.l3miage.pc.prisonersdilemma.application.ports.outputs.PartieRepository;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.TypeStrategy;
import fr.uga.l3miage.pc.prisonersdilemma.domain.exceptions.GameNotInitializedException;
import fr.uga.l3miage.pc.prisonersdilemma.domain.exceptions.MaximumPlayersReachedException;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Joueur;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Partie;

@Service
public class PartiesService implements IPartieService {
    @Autowired
    PartieRepository partieRepository;

    public void demarrerPartie(int nbTours) {
        if(partieRepository.exists()) {
            throw new IllegalStateException("Une partie est déjà en cours. Veuillez la terminer avant d'en démarrer une nouvelle.");
        }
        Partie newPartie = new Partie(nbTours);
        partieRepository.save(newPartie);
    }

    private Partie getPartie() throws GameNotInitializedException {
        return partieRepository.find().orElseThrow(() -> new GameNotInitializedException("La partie n'est pas initialisée."));
    }

    public boolean isGameStarted() {
        return partieRepository.exists();
    }

    public void addPlayer(String pseudo, boolean isConnected, String strategy) throws MaximumPlayersReachedException, GameNotInitializedException {
        Partie partie = getPartie();
        
        if (partie.getNbJoueurs() < 2) {
            partie.addJoueur(pseudo, isConnected, TypeStrategy.valueOf(strategy));
        } else {
            throw new MaximumPlayersReachedException();
        }
    }

    public boolean abandonner(String pseudo, TypeStrategy strategy) throws IllegalArgumentException, GameNotInitializedException {
        Partie partie = getPartie();
        return partie.abandonner(pseudo, strategy);
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
        if (!partieRepository.exists()) {
            throw new GameNotInitializedException("La partie n'est pas initialisée.");
        }
    
        return partie.getHistorique(pseudo);
    }

    public void terminerPartie() {
        if (!partieRepository.exists()) {
            throw new IllegalStateException("Aucune partie à terminer.");
        }
        partieRepository.delete();
    }
    
}
