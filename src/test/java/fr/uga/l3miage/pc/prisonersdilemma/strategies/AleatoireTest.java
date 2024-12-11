package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;

class AleatoireTest {
    @Test
    void testRandomDecisionOccurs() {
        Aleatoire randomStrat = new Aleatoire();
        int cooperateCount = 0;
        int iterations = 1000;

        for (int i = 0; i < iterations; i++) {
            Decision decision = randomStrat.execute(null, null);
            if (decision == Decision.COOPERER) {
                cooperateCount++;
            }
        }

        assertTrue(cooperateCount > 0.45 * iterations && cooperateCount < 0.55 * iterations, "La fréquence des décisions aléatoires devrait être proche de 50%");
    }
}
