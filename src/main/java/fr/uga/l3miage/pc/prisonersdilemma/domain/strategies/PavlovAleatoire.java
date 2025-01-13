package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import java.util.List;
import java.util.Random;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;

public class PavlovAleatoire extends Pavlov{
    private final Random random = new Random();

    @Override
    public Decision execute(List<Decision> historiqueJoueur1, List<Decision> historiqueJoueur2) {
        if (random.nextDouble() > 0.25) {
            return super.execute(historiqueJoueur1, historiqueJoueur2);
        } else {
            return random.nextBoolean() ? Decision.COOPERER : Decision.TRAHIR;
        }
    }
}
