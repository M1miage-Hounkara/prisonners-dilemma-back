package fr.uga.l3miage.pc.prisonersdilemma.StrategiesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.DonnantDonnantAleatoire;

public class DonnantDonnantAleatoireTest {
    private DonnantDonnantAleatoire strategy;
    private ArrayList<Decision> historiqueJoueur1;
    private ArrayList<Decision> historiqueJoueur2;

    @BeforeEach
    public void setUp() {
        strategy = new DonnantDonnantAleatoire(new Random(0));
        historiqueJoueur1 = new ArrayList<>();
        historiqueJoueur2 = new ArrayList<>();
    }

    @Test
    public void testCooperateWhenEmptyHistory() {
        Decision decision = strategy.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.COOPERER, decision, "Devrait coopérer lorsque l'historique est vide");
    }

    @Test
    public void testImitateLastDecisionWhenNoRandomDecision() {
        historiqueJoueur2.add(Decision.TRAHIR);
        Decision decision = strategy.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.TRAHIR, decision, "Devrait imiter la dernière décision de l'adversaire");
    }

    @Test
    public void testRandomDecisionOccurs() {
        DonnantDonnantAleatoire randomStrat = new DonnantDonnantAleatoire();
        int cooperateCount = 0;
        int iterations = 1000; 

        historiqueJoueur2.add(Decision.TRAHIR);

        for (int i = 0; i < iterations; i++) {
            Decision decision = randomStrat.execute(historiqueJoueur1, historiqueJoueur2);
            if (decision == Decision.COOPERER) {
                cooperateCount++;
            }
        }

        assertTrue(cooperateCount > 0.2 * iterations && cooperateCount < 0.30 * iterations, "La fréquence des décisions aléatoires devrait être proche de 50%");
    }
}