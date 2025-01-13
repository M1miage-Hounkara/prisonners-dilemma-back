package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import java.util.List;
import java.util.Random;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.interfaces.Strategy;
import lombok.Getter;


@Getter
public class DonnantDonnantAleatoire implements Strategy {
    private final Random random;
    
    public DonnantDonnantAleatoire() {
        this.random = new Random();
    }

    public DonnantDonnantAleatoire(Random random) {
        this.random = random;
    }

    @Override
    public Decision execute(List<Decision> historiqueJoueur1, List<Decision> historiqueJoueur2) {
        if (random.nextDouble() < 0.5) {
            return random.nextBoolean() ? Decision.COOPERER : Decision.TRAHIR;
        }
        if (historiqueJoueur2.isEmpty()) {
            return Decision.COOPERER;
        }
        return historiqueJoueur2.get(historiqueJoueur2.size() - 1);
    }
}
