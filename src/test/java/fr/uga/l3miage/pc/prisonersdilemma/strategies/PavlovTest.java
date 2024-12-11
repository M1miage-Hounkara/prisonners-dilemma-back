package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;

public class PavlovTest {

    private Pavlov pavlov;

    @BeforeEach
    public void setup() {
        pavlov = new Pavlov();
    }

    @Test
    public void testPremiereDecision_Cooperer() {
        // Historique vide pour les deux joueurs
        ArrayList<Decision> historiqueJoueur1 = new ArrayList<>();
        ArrayList<Decision> historiqueJoueur2 = new ArrayList<>();

        // Première décision devrait être COOPERER
        assertEquals(Decision.COOPERER, pavlov.execute(historiqueJoueur1, historiqueJoueur2));
    }

    @Test
    public void testDernierCoupGagnant_CoopereApresSuccesMutuel() {
        // Les deux joueurs ont coopéré dans le dernier tour
        ArrayList<Decision> historiqueJoueur1 = new ArrayList<>();
        ArrayList<Decision> historiqueJoueur2 = new ArrayList<>();
        historiqueJoueur1.add(Decision.COOPERER);
        historiqueJoueur2.add(Decision.COOPERER);

        // Pavlov devrait répéter la dernière action (coopérer)
        assertEquals(Decision.COOPERER, pavlov.execute(historiqueJoueur1, historiqueJoueur2));
    }

    @Test
    public void testDernierCoupGagnant_TrahitApresGain() {
        // Joueur 1 a trahi, Joueur 2 a coopéré dans le dernier tour
        ArrayList<Decision> historiqueJoueur1 = new ArrayList<>();
        ArrayList<Decision> historiqueJoueur2 = new ArrayList<>();
        historiqueJoueur1.add(Decision.TRAHIR);
        historiqueJoueur2.add(Decision.COOPERER);

        // Pavlov devrait répéter la dernière action (trahir)
        assertEquals(Decision.TRAHIR, pavlov.execute(historiqueJoueur1, historiqueJoueur2));
    }

    @Test
    public void testDernierCoupPerdant_RetourCooperation() {
        // Joueur 1 a trahi, mais Joueur 2 a également trahi dans le dernier tour
        ArrayList<Decision> historiqueJoueur1 = new ArrayList<>();
        ArrayList<Decision> historiqueJoueur2 = new ArrayList<>();
        historiqueJoueur1.add(Decision.TRAHIR);
        historiqueJoueur2.add(Decision.TRAHIR);

        // Pavlov devrait coopérer après un coup perdant
        assertEquals(Decision.COOPERER, pavlov.execute(historiqueJoueur1, historiqueJoueur2));
    }

    @Test
    public void testCycleAlternanceGagnantPerdant() {
        // Cas où les joueurs alternent entre gagner et perdre
        ArrayList<Decision> historiqueJoueur1 = new ArrayList<>();
        ArrayList<Decision> historiqueJoueur2 = new ArrayList<>();

        // Tour 1 : Joueur 1 coopère, Joueur 2 coopère
        historiqueJoueur1.add(Decision.COOPERER);
        historiqueJoueur2.add(Decision.COOPERER);
        assertEquals(Decision.COOPERER, pavlov.execute(historiqueJoueur1, historiqueJoueur2));

        // Tour 2 : Joueur 1 coopère, Joueur 2 trahit
        historiqueJoueur1.add(Decision.COOPERER);
        historiqueJoueur2.add(Decision.TRAHIR);
        assertEquals(Decision.COOPERER, pavlov.execute(historiqueJoueur1, historiqueJoueur2));

        // Tour 3 : Joueur 1 coopère, Joueur 2 coopère
        historiqueJoueur1.add(Decision.COOPERER);
        historiqueJoueur2.add(Decision.COOPERER);
        assertEquals(Decision.COOPERER, pavlov.execute(historiqueJoueur1, historiqueJoueur2));
    }

    @Test
    public void testRetourCooperationApresDefaite() {
        // Joueur 1 coopère, Joueur 2 trahit plusieurs fois
        ArrayList<Decision> historiqueJoueur1 = new ArrayList<>();
        ArrayList<Decision> historiqueJoueur2 = new ArrayList<>();
        historiqueJoueur1.add(Decision.COOPERER);
        historiqueJoueur2.add(Decision.TRAHIR);

        // Pavlov devrait coopérer après une perte
        assertEquals(Decision.COOPERER, pavlov.execute(historiqueJoueur1, historiqueJoueur2));
    }
}

