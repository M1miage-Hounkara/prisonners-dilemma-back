package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;

class SondeurRepentantTest {
     private SondeurRepentant strategy;
    private ArrayList<Decision> historiqueJoueur1;
    private ArrayList<Decision> historiqueJoueur2;

    @BeforeEach
    public void setUp() {
        strategy = new SondeurRepentant(new Random(0));
        historiqueJoueur1 = new ArrayList<>();
        historiqueJoueur2 = new ArrayList<>();
    }

    @Test
    void testCooperateWhenEmptyHistory() {
        Decision decision = strategy.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.COOPERER, decision, "Devrait coopérer lorsque l'historique de l'adversaire est vide");
    }

    @Test
    void testImitateLastDecisionWhenNoRandomTrahir() {
        historiqueJoueur2.add(Decision.TRAHIR);
        Decision decision = strategy.execute(historiqueJoueur1, historiqueJoueur2);
        historiqueJoueur1.add(decision);
        assertEquals(Decision.TRAHIR, decision, "Devrait imiter la dernière décision de l'adversaire");
    }

    @Test
    void testRandomTrahirOccurs() {
        int betrayCount = 0;
        int iterations = 1000;
        SondeurRepentant randRepentant = new SondeurRepentant();
        historiqueJoueur2.add(Decision.COOPERER);

        for (int i = 0; i < iterations; i++) {
            Decision decision = randRepentant.execute(historiqueJoueur1, historiqueJoueur2);
            historiqueJoueur1.add(decision);
            if (decision == Decision.TRAHIR) {
                betrayCount++;
            }
        }
        assertTrue(betrayCount > 0.2 * iterations && betrayCount < 0.3 * iterations, "La fréquence des trahisons aléatoires devrait être proche de 25%");
    }

    @Test
    void testRepentanceAfterBetrayalResponse() {
        historiqueJoueur1.add(Decision.TRAHIR);
        historiqueJoueur2.add(Decision.TRAHIR);
        Decision decision = strategy.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.COOPERER, decision, "Devrait coopérer pour se repentir après une réponse de trahison de l'adversaire");
    }
}
