package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import java.util.ArrayList;
import java.util.Random;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.interfaces.Strategy;

public class VraiPacificateur implements Strategy{

    private final Random random;

    public VraiPacificateur() {
        this.random = new Random();
    }


    @Override
    public Decision execute(ArrayList<Decision> historiqueJoueur1, ArrayList<Decision> historiqueJoueur2) {
        int taille = historiqueJoueur2.size();
        if (taille>=2) {
            Decision dernierAdversaire = historiqueJoueur2.get(taille - 1);
            Decision avantDernierAdversaire = historiqueJoueur2.get(taille - 2);
            if (dernierAdversaire == Decision.TRAHIR && avantDernierAdversaire == Decision.TRAHIR) {
                if (random.nextDouble() < 0.25) {
                    return Decision.COOPERER; 
                } else {
                    return Decision.TRAHIR;
                }
            }
        }
        return Decision.COOPERER;
    }
}
