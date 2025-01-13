package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import java.util.List;
import java.util.Random;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.interfaces.Strategy;

public class DonnantPourDeuxDonnantsEtAleatoire implements Strategy {
    private final Random random;
    public DonnantPourDeuxDonnantsEtAleatoire() {
        this.random = new Random();
    }

    public DonnantPourDeuxDonnantsEtAleatoire(Random random) {
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
        if (historiqueJoueur2.size() < 2) {
            return Decision.COOPERER;
        }
        if (historiqueJoueur2.get(historiqueJoueur2.size() - 1) == historiqueJoueur2.get(historiqueJoueur2.size() - 2)) {
            return historiqueJoueur2.get(historiqueJoueur2.size() - 1);
        }
        
        return Decision.COOPERER;
    }
}
