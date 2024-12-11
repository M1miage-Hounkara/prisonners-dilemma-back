package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import java.util.ArrayList;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.interfaces.Strategy;

public class Pavlov implements Strategy{
    @Override
    public Decision execute(ArrayList<Decision> historiqueJoueur1, ArrayList<Decision> historiqueJoueur2) {
        if(historiqueJoueur1.isEmpty() || historiqueJoueur2.isEmpty()) {
            return Decision.COOPERER;
        }
        if(dernierCoupGagnant(historiqueJoueur1, historiqueJoueur2)) {
            return historiqueJoueur1.get(historiqueJoueur1.size() - 1);
        }
        return Decision.COOPERER;
    }

    protected boolean dernierCoupGagnant(ArrayList<Decision> historiqueJoueur1, ArrayList<Decision> historiqueJoueur2) {
        return (historiqueJoueur1.get(historiqueJoueur1.size() - 1) == Decision.TRAHIR && historiqueJoueur2.get(historiqueJoueur2.size() - 1) == Decision.COOPERER) || (historiqueJoueur1.get(historiqueJoueur1.size() - 1) == Decision.COOPERER && historiqueJoueur2.get(historiqueJoueur2.size() - 1) == Decision.COOPERER);
    }
}
