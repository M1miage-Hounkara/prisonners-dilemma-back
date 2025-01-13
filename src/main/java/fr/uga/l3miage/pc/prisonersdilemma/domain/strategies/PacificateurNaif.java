package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import java.util.List;
import java.util.Random;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.interfaces.Strategy;

public class PacificateurNaif implements Strategy{
    private final Random random;

    public PacificateurNaif() {
        this.random = new Random();
    }

    public PacificateurNaif(Random random) {
        this.random = random;
    }

    @Override
    public Decision execute(List<Decision> historiqueJoueur1, List<Decision> historiqueJoueur2) {
        if (historiqueJoueur2.isEmpty()) {
            return Decision.COOPERER;
        }
        if (historiqueJoueur2.get(historiqueJoueur2.size() - 1) == Decision.TRAHIR && random.nextDouble() < 0.25) {
            return Decision.COOPERER;
        }
        return historiqueJoueur2.get(historiqueJoueur2.size() - 1);
    }
}
