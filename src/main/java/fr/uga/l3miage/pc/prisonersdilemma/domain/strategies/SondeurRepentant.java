package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import java.util.List;
import java.util.Random;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.interfaces.Strategy;

public class SondeurRepentant implements Strategy{

    private Random random;

    public SondeurRepentant() {
        this.random = new Random();
    }

    public SondeurRepentant(Random random) {
        this.random = random;
    }

    @Override
    public Decision execute(List<Decision> historiqueJoueur1, List<Decision> historiqueJoueur2) {
        if (historiqueJoueur2.isEmpty()) {
            return Decision.COOPERER;
        }
        if (!historiqueJoueur1.isEmpty() && historiqueJoueur1.get(historiqueJoueur1.size()-1) == Decision.TRAHIR && historiqueJoueur2.get(historiqueJoueur2.size() - 1) == Decision.TRAHIR) {
            return Decision.COOPERER;
        }
        if (random.nextDouble() < 0.25) {
            return Decision.TRAHIR;
        }
        return historiqueJoueur2.get(historiqueJoueur2.size() - 1);
    }
}
