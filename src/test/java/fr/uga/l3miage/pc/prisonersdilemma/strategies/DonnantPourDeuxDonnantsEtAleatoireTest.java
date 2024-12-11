package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;

class DonnantPourDeuxDonnantsEtAleatoireTest {
    private DonnantPourDeuxDonnantsEtAleatoire strategy;
    private ArrayList<Decision> historiqueJoueur1;
    private ArrayList<Decision> historiqueJoueur2;

    @BeforeEach
    public void setUp() {
        strategy = new DonnantPourDeuxDonnantsEtAleatoire(new Random(0));
        historiqueJoueur1 = new ArrayList<>();
        historiqueJoueur2 = new ArrayList<>();
    }

    @Test
    void testCooperateWhenEmptyHistory() {
        Decision decision = strategy.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.COOPERER, decision);
    }

    @Test
    void testCooperateWhenSingleEntryInHistory() {
        historiqueJoueur2.add(Decision.COOPERER);
        Decision decision = strategy.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.COOPERER, decision);
    }

    @Test
    void testRepeatLastDecisionWhenTwoSameDecisionsInHistory() {
        historiqueJoueur2.add(Decision.TRAHIR);
        historiqueJoueur2.add(Decision.TRAHIR);
        Decision decision = strategy.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.TRAHIR, decision);
    }

    @Test
    void testCooperateWhenTwoDifferentDecisionsInHistory() {
        historiqueJoueur2.add(Decision.COOPERER);
        historiqueJoueur2.add(Decision.TRAHIR);
        Decision decision = strategy.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.COOPERER, decision);
    }
    @Test
    void testRandomDecisionOccurs() {
        DonnantPourDeuxDonnantsEtAleatoire strategyWithRandom = new DonnantPourDeuxDonnantsEtAleatoire();
        int cooperateCount = 0;
        int iterations = 10000;

        historiqueJoueur2.add(Decision.TRAHIR);
        historiqueJoueur2.add(Decision.TRAHIR);

        for (int i = 0; i < iterations; i++) {
            Decision decision = strategyWithRandom.execute(historiqueJoueur1, historiqueJoueur2);
            if (decision == Decision.COOPERER) {
                cooperateCount++;
            }
        }
        assertTrue(cooperateCount > 0.2 * iterations && cooperateCount < 0.30 * iterations, "La fréquence des décisions aléatoires devrait être proche de 50%");
    }
}
