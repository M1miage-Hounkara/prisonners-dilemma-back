package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import java.util.List;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.interfaces.Strategy;

public class Graduel implements Strategy{
    private int nbTrahison = 0;
    @Override
    public Decision execute(List<Decision> historiqueJoueur1, List<Decision> historiqueJoueur2) {
    if(historiqueJoueur2.isEmpty()) {
            return Decision.COOPERER;
        }
      if(historiqueJoueur2.get(historiqueJoueur2.size()-1) == Decision.TRAHIR) {
        nbTrahison++;
      }
      if(nbTrahison > 0) {
        nbTrahison--;
        return Decision.TRAHIR;
      }
        return Decision.COOPERER;
    }

}
