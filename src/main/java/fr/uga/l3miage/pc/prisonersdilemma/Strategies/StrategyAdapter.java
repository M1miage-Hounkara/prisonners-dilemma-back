package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import java.util.ArrayList;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.enums.TypeStrategy;
import fr.uga.l3miage.pc.prisonersdilemma.interfaces.Strategy;
import fr.uga.l3miage.pc.prisonersdilemma.models.strategies.History;
import fr.uga.l3miage.pc.prisonersdilemma.models.strategies.PlayerRole;
import fr.uga.l3miage.pc.prisonersdilemma.models.strategies.StrategyFactory;

public class StrategyAdapter implements Strategy {
    private final TypeStrategy strategyType;

    public StrategyAdapter(TypeStrategy strategyType) {
        this.strategyType = strategyType;
    }

    @Override
    public Decision execute(ArrayList<Decision> owDecisions, ArrayList<Decision> opponentDecisions) {
        Boolean choix = null;
        fr.uga.l3miage.pc.prisonersdilemma.models.strategies.Strategy strat = StrategyFactory.getStrategyInstance(this.strategyType.ordinal());
        choix = strat.play(adaptHistorique(owDecisions, opponentDecisions), PlayerRole.J1);
                
        Decision decision;
        decision = choix.booleanValue() ? Decision.COOPERER : Decision.TRAHIR;
        
        return decision;
    }

    private History adaptHistorique(ArrayList<Decision> owDecisions, ArrayList<Decision> opponentDecisions) {
        if (owDecisions.size() != opponentDecisions.size()) {
            throw new IllegalArgumentException("Les listes de décisions des deux joueurs doivent avoir la même taille !");
        }
        History history = new History();
    
        for (int i = 0; i < owDecisions.size()-1; i++) {
            Boolean player1Decision = adaptDecision(owDecisions.get(i));
            Boolean player2Decision = adaptDecision(opponentDecisions.get(i));
            history.addTour(player1Decision, player2Decision);
        }
        return history;
    }

    private Boolean adaptDecision(Decision decision) {
        switch (decision) {
            case COOPERER:
                return true;
            case TRAHIR:
                return false;
            default:
                throw new IllegalArgumentException("La décision doit être soit COOPERER soit TRAHIR !");
        }
    }
    
}
