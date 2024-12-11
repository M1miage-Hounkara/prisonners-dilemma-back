package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;

class PacificateurNaifTest {
    private PacificateurNaif strategy;
    private ArrayList<Decision> historiqueJoueur1;
    private ArrayList<Decision> historiqueJoueur2;

    @BeforeEach
    public void setUp() {
        strategy = new PacificateurNaif(new Random(0));
        historiqueJoueur1 = new ArrayList<>();
        historiqueJoueur2 = new ArrayList<>();
    }

    @Test
    void testCooperateWhenEmptyHistory() {
        Decision decision = strategy.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.COOPERER, decision, "Devrait coopérer lorsque l'historique de l'adversaire est vide");
    }

    @Test
    void testImitateLastDecisionWhenNoPacification() {
        historiqueJoueur2.add(Decision.TRAHIR);
        Decision decision = strategy.execute(historiqueJoueur1, historiqueJoueur2);
        historiqueJoueur1.add(decision);
        assertEquals(Decision.TRAHIR, decision, "Devrait imiter la dernière décision de l'adversaire");
    }

    @Test
    void testPacificationOccurs() {
        int cooperateCount = 0;
        int iterations = 1000;
        PacificateurNaif randomStrat = new PacificateurNaif();
        historiqueJoueur2.add(Decision.TRAHIR);

        for (int i = 0; i < iterations; i++) {
            Decision decision = randomStrat.execute(historiqueJoueur1, historiqueJoueur2);
            historiqueJoueur1.add(decision);
            if (decision == Decision.COOPERER) {
                cooperateCount++;
            }
        }
        assertTrue(cooperateCount > 0.2 * iterations && cooperateCount < 0.30 * iterations, "La fréquence des pacifications devrait être proche de 15%");
    }

    @Test
    void testImitateAfterCooperation() {
        historiqueJoueur2.add(Decision.COOPERER);
        Decision decision = strategy.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.COOPERER, decision, "Devrait imiter la coopération de l'adversaire");
    }
}
