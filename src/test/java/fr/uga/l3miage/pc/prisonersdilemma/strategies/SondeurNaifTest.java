package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;

class SondeurNaifTest {
    private SondeurNaif strategy;
    private ArrayList<Decision> historiqueJoueur1;
    private ArrayList<Decision> historiqueJoueur2;

    @BeforeEach
    public void setUp() {
        strategy = new SondeurNaif(new Random(0));
        historiqueJoueur1 = new ArrayList<>();
        historiqueJoueur2 = new ArrayList<>();
    }

    @Test
    void testCooperateWhenEmptyHistory() {
        Decision decision = strategy.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.COOPERER, decision, "Devrait coopérer lorsque l'historique est vide");
    }

    @Test
    void testImitateLastDecisionWhenNoRandomTrahir() {
        historiqueJoueur2.add(Decision.TRAHIR);
        Decision decision = strategy.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.TRAHIR, decision, "Devrait imiter la dernière décision de l'adversaire");
    }

    @Test
    void testRandomTrahirOccurs() {
        int betrayCount = 0;
        int iterations = 1000;
        SondeurNaif randNaif = new SondeurNaif();
        historiqueJoueur2.add(Decision.COOPERER);

        for (int i = 0; i < iterations; i++) {
            Decision decision = randNaif.execute(historiqueJoueur1, historiqueJoueur2);
            if (decision == Decision.TRAHIR) {
                betrayCount++;
            }
        }
        assertTrue(betrayCount > 0.20 * iterations && betrayCount < 0.3 * iterations, "La fréquence des trahisons aléatoires devrait être proche de 10%");
    }
}
