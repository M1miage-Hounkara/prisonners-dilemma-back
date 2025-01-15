package fr.uga.l3miage.pc.prisonersdilemma.domain.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.TypeStrategy;
import fr.uga.l3miage.pc.prisonersdilemma.domain.strategies.StrategyFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Partie {
    private final Map<String, ArrayList<Decision>> historiqueDecisionsMap = new HashMap<>();
    private StrategyFactory strategyFactory;
    private int nbTours;
    private int tourActuel = 1;
    private boolean decisionsProcessed = false;
    private boolean partieTerminee = false;
    private boolean processingDecisions = false;
    private boolean done = false;




    private final List<Joueur> joueurs = new ArrayList<>();

    public Partie(int nbTours) {
        this.strategyFactory = new StrategyFactory();
        this.nbTours = nbTours;
    }

    public void addJoueur(String pseudo, boolean isConnected, TypeStrategy strategy) throws IllegalStateException {
        Joueur joueur = new Joueur();
        joueur.setConnected(isConnected);
        if (isConnected) {
            if (pseudo == null || pseudo.isEmpty()) {
                throw new IllegalStateException("Le nom du joueur ne peut pas être vide.");
            } else if (joueurs.stream().anyMatch(j -> j.getName().equals(pseudo))) {
                throw new IllegalStateException("Le nom du joueur est déjà utilisé.");
            }
            joueur.setName(pseudo);
        } else {
            joueur.setName("Blip-Boup-Bap");
            joueur.setConnected(false);
            joueur.setStrategy(strategyFactory.create(strategy));
        }
        joueurs.add(joueur);
        historiqueDecisionsMap.put(joueur.getName(), new ArrayList<>());
    }

    public int getNbJoueurs() {
        return joueurs.size();
    }



    public synchronized boolean soumettreDecision(String pseudo, Decision decision) {
        if (processingDecisions) {
            return false;
        }
    
        processingDecisions = true; 
    
        try {
            Joueur joueur1 = joueurs.get(0);
            Joueur joueur2 = joueurs.size() > 1 ? joueurs.get(1) : null;
    
            int historiqueDecisionsMapSize1 = historiqueDecisionsMap.get(joueur1.getName()).size();
            int historiqueDecisionsMapSize2 = joueur2 != null ? historiqueDecisionsMap.get(joueur2.getName()).size() : 0;
    
            if (joueur1.getName().equals(pseudo) && historiqueDecisionsMapSize1 < tourActuel && joueur1.isConnected()) {
                joueur1.setDecision(decision);
                historiqueDecisionsMapSize1++;
                historiqueDecisionsMap.get(joueur1.getName()).add(decision);

            }
            else if (!joueur1.isConnected() && joueur2!=null) {
                Decision decision1 = joueur1.getStrategy().execute(historiqueDecisionsMap.get(joueur1.getName()),historiqueDecisionsMap.get(joueur2.getName()));
                joueur1.setDecision(decision1);
                historiqueDecisionsMapSize1++;
                historiqueDecisionsMap.get(joueur1.getName()).add(decision1);
            }
            if (joueur2 != null && joueur2.getName().equals(pseudo) && historiqueDecisionsMapSize2 < tourActuel && joueur2.isConnected()) {
                joueur2.setDecision(decision);
                historiqueDecisionsMapSize2++;
                historiqueDecisionsMap.get(joueur2.getName()).add(decision);
            }
            else if (joueur2 != null && !joueur2.isConnected()) {
                Decision decision2 = joueur1.getStrategy().execute(historiqueDecisionsMap.get(joueur1.getName()),historiqueDecisionsMap.get(joueur2.getName()));
                joueur2.setDecision(decision2);
                historiqueDecisionsMapSize1++;
                historiqueDecisionsMap.get(joueur2.getName()).add(decision2);
            }


    
            if (historiqueDecisionsMapSize1 == tourActuel && historiqueDecisionsMapSize2  == tourActuel) {
                processRound(joueur1, joueur2);
                tourActuel++;
                
            }
    
            return true;
    
        } finally {
            processingDecisions = false; 
        }
    }

    public synchronized boolean abandonner(String pseudo,TypeStrategy strategy) {
        Joueur joueur = joueurs.stream()
                .filter(j -> j.getName().equals(pseudo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Player with pseudo " + pseudo + " not found."));
        joueur.setConnected(false);
        joueur.setStrategy(strategyFactory.create(strategy));
        Joueur otherPlayer = joueurs.stream()
                .filter(j -> !j.getName().equals(pseudo))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Other player not found."));
        if (otherPlayer.isConnected() && otherPlayer.getDecision() != null) {
            soumettreDecision(pseudo, null);
            
        }
        return true;
    }
    
    
    public Integer getScore(String pseudo) {
        Joueur joueur = joueurs.stream()
                .filter(j -> j.getName().equals(pseudo))
                .findFirst()
                .orElse(null);
    
        if (joueur == null) {
            return null;
        }
    
        return joueur.getScore();
    }

    public Integer getWinner(String pseudo) {
        Joueur joueur = joueurs.stream()
                .filter(j -> j.getName().equals(pseudo))
                .findFirst()
                .orElse(null);
    
        if (joueur == null) {
            throw new IllegalArgumentException("Player with pseudo " + pseudo + " not found.");
        }
    
        Joueur otherPlayer = joueurs.stream()
                .filter(j -> !j.getName().equals(pseudo))
                .findFirst()
                .orElse(null);
    
        if (otherPlayer == null) {
            throw new IllegalStateException("Other player not found.");
        }
    
        if (joueur.getScore() > otherPlayer.getScore()) {
            return 1;
        } else if (joueur.getScore() < otherPlayer.getScore()) {
            return -1;
        } else {
            return 0;
            
        }
    }




    private void processRound(Joueur joueur1, Joueur joueur2) {
        Decision decision1 = joueur1.getDecision();
        Decision decision2 = joueur2.getDecision();
    
        if (decision1 == Decision.COOPERER && decision2 == Decision.COOPERER) {
            joueur1.ajouterScore(3);
            joueur2.ajouterScore(3);
        } else if (decision1 == Decision.COOPERER && decision2 == Decision.TRAHIR) {
            joueur1.ajouterScore(0);
            joueur2.ajouterScore(5);
        } else if (decision1 == Decision.TRAHIR && decision2 == Decision.COOPERER) {
            joueur1.ajouterScore(5);
            joueur2.ajouterScore(0);
        } else if (decision1 == Decision.TRAHIR && decision2 == Decision.TRAHIR) {
            joueur1.ajouterScore(1);
            joueur2.ajouterScore(1);
        }
    }
    


    public boolean peutJouerTour() {
        return (joueurs.get(0).getDecision() != null && joueurs.get(1).getDecision() != null) && tourActuel <= nbTours;
        
    }
    

    public synchronized boolean getDecisionOfOtherPlayer(String pseudo) {
        if (processingDecisions) {
            return false;
        }
    
        Joueur requestingPlayer = findPlayerByName(pseudo);
        if (requestingPlayer == null) {
            return false;
        }
    
        Joueur otherPlayer = findOtherPlayer(pseudo);
        if (otherPlayer == null) {
            return false;
        }
    
        List<Decision> historique = historiqueDecisionsMap.get(otherPlayer.getName());
        if (isHistoriqueInvalid(historique)) {
            return false;
        }
    
        if (tourActuel <= 1) {
            return false;
        }
    
        int previousTurnIndex = tourActuel - 2;
        if (!isValidDecision(historique, previousTurnIndex)) {
            return false;
        }
    
        if (isGameComplete()) {
            partieTerminee = true;
        }
    
        return true;
    }
    
    private Joueur findPlayerByName(String pseudo) {
        return joueurs.stream()
                .filter(j -> j.getName().equals(pseudo))
                .findFirst()
                .orElse(null);
    }
    
    private Joueur findOtherPlayer(String pseudo) {
        return joueurs.stream()
                .filter(j -> !j.getName().equals(pseudo))
                .findFirst()
                .orElse(null);
    }
    
    private boolean isHistoriqueInvalid(List<Decision> historique) {
        return historique == null || historique.isEmpty();
    }
    
    private boolean isValidDecision(List<Decision> historique, int index) {
        return index < historique.size() && historique.get(index) != null;
    }
    
    private boolean isGameComplete() {
        int historiqueSize1 = historiqueDecisionsMap.get(joueurs.get(0).getName()).size();
        int historiqueSize2 = joueurs.size() > 1 
                ? historiqueDecisionsMap.get(joueurs.get(1).getName()).size()
                : 0;
    
        return historiqueSize1 == historiqueSize2 && historiqueSize1 == nbTours;
    }
    

    public List<Decision> getHistorique(String pseudo) {
        return historiqueDecisionsMap.getOrDefault(pseudo, new ArrayList<>());
    }

    public void resetDecisions() {
        for (Joueur j : joueurs) {
            j.setDecision(null);
        }
    }

}
