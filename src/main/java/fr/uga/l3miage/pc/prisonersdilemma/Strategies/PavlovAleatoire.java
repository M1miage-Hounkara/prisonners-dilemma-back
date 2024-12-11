package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import java.util.ArrayList;
import java.util.Random;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;

public class PavlovAleatoire extends Pavlov{
    private final Random random = new Random();

    @Override
    public Decision execute(ArrayList<Decision> historiqueJoueur1, ArrayList<Decision> historiqueJoueur2) {
        if (random.nextDouble() > 0.25) {
            return super.execute(historiqueJoueur1, historiqueJoueur2);
        } else {
            return random.nextBoolean() ? Decision.COOPERER : Decision.TRAHIR;
        }
    }
}
