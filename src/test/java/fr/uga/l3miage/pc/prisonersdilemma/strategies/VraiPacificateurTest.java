package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;

class VraiPacificateurTest {
    private VraiPacificateur strategie;
    private ArrayList<Decision> historiqueJoueur1;
    private ArrayList<Decision> historiqueJoueur2;

    @BeforeEach
    void setUp() {
        strategie = new VraiPacificateur();
        historiqueJoueur1 = new ArrayList<>();
        historiqueJoueur2 = new ArrayList<>();
    }

    @Test
    void testCooperationQuandPasDeuxTrahisonsConsecutives() {
        historiqueJoueur2.add(Decision.COOPERER);
        historiqueJoueur2.add(Decision.TRAHIR);

        Decision decisionJoueur1 = strategie.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.COOPERER, decisionJoueur1);
    }

    @Test
    void testTrahirApresDeuxTrahisonsConsecutives() {
        int cooperateCount = 0;
        int iterations = 1000;
        historiqueJoueur2.add(Decision.TRAHIR);
        historiqueJoueur2.add(Decision.TRAHIR);
        for (int i = 0; i < iterations; i++) {
            Decision decision = strategie.execute(historiqueJoueur1, historiqueJoueur2);
            historiqueJoueur1.add(decision);
            if (decision == Decision.COOPERER) {
                cooperateCount++;
            }
        }
        assertTrue(cooperateCount > 0.2 * iterations && cooperateCount < 0.30 * iterations, "La fréquence des pacifications devrait être proche de 15%");
    }

    @Test
    void testCoopererQuandAdversaireCoopere() {
        historiqueJoueur2.add(Decision.COOPERER);
        historiqueJoueur2.add(Decision.COOPERER);

        Decision decisionJoueur1 = strategie.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.COOPERER, decisionJoueur1);
    }
}

