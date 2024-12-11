package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import java.util.ArrayList;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.interfaces.Strategy;

public class Graduel implements Strategy{
    private int nbTrahison = 0;
    @Override
    public Decision execute(ArrayList<Decision> historiqueJoueur1, ArrayList<Decision> historiqueJoueur2) {
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
