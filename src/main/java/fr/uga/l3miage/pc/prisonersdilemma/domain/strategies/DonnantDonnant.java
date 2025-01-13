package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import java.util.ArrayList;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.interfaces.Strategy;

public class DonnantDonnant implements Strategy {
    @Override
    public Decision execute(ArrayList<Decision> historiqueJoueur1, ArrayList<Decision> historiqueJoueur2) {
        if(historiqueJoueur2.isEmpty()) {
            return Decision.COOPERER;
        }
        else {
            return historiqueJoueur2.get(historiqueJoueur2.size()-1);
        }
    }
}
