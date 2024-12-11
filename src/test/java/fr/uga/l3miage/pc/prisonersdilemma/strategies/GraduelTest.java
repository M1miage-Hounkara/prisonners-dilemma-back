package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;

class GraduelTest {

    private Graduel graduel;

    @BeforeEach
    public void setup() {
        graduel = new Graduel();
    }

    @Test
    void testPremiereDecision_Cooperer() {
        // Historique vide pour les deux joueurs
        ArrayList<Decision> historiqueJoueur1 = new ArrayList<>();
        ArrayList<Decision> historiqueJoueur2 = new ArrayList<>();
        
        // Première décision devrait être de coopérer
        assertEquals(Decision.COOPERER, graduel.execute(historiqueJoueur1, historiqueJoueur2));
    }

    @Test
    void testCooperationContinue() {
        // Historique où les deux joueurs coopèrent toujours
        ArrayList<Decision> historiqueJoueur1 = new ArrayList<>();
        ArrayList<Decision> historiqueJoueur2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            historiqueJoueur1.add(Decision.COOPERER);
            historiqueJoueur2.add(Decision.COOPERER);
        }
        
        // Décision devrait rester COOPERER
        assertEquals(Decision.COOPERER, graduel.execute(historiqueJoueur1, historiqueJoueur2));
    }

    @Test
    void testReactionTrahisonSimple() {
        // Joueur 2 trahit une fois
        ArrayList<Decision> historiqueJoueur1 = new ArrayList<>();
        ArrayList<Decision> historiqueJoueur2 = new ArrayList<>();
        historiqueJoueur1.add(Decision.COOPERER);
        historiqueJoueur2.add(Decision.TRAHIR);

        // Graduel devrait répondre par TRAHIR
        assertEquals(Decision.TRAHIR, graduel.execute(historiqueJoueur1, historiqueJoueur2));
    }

    @Test
    void testReactionTrahisonMultiple() {
        // Joueur 2 trahit deux fois de suite
        ArrayList<Decision> historiqueJoueur1 = new ArrayList<>();
        ArrayList<Decision> historiqueJoueur2 = new ArrayList<>();
        historiqueJoueur1.add(Decision.COOPERER);
        historiqueJoueur2.add(Decision.TRAHIR);
        historiqueJoueur1.add(Decision.TRAHIR);
        historiqueJoueur2.add(Decision.TRAHIR);

        // Graduel devrait répondre par TRAHIR deux fois
        assertEquals(Decision.TRAHIR, graduel.execute(historiqueJoueur1, historiqueJoueur2));
        assertEquals(Decision.TRAHIR, graduel.execute(historiqueJoueur1, historiqueJoueur2));
    }

    @Test
    void testRetourCooperationApresTrahison() {
        // Joueur 2 trahit une fois, puis coopère
        ArrayList<Decision> historiqueJoueur1 = new ArrayList<>();
        ArrayList<Decision> historiqueJoueur2 = new ArrayList<>();
        historiqueJoueur1.add(Decision.COOPERER);
        historiqueJoueur2.add(Decision.TRAHIR);

        // Graduel trahit une fois pour "rembourser" la trahison
        assertEquals(Decision.TRAHIR, graduel.execute(historiqueJoueur1, historiqueJoueur2));
        historiqueJoueur2.add(Decision.COOPERER);
        assertEquals(Decision.COOPERER, graduel.execute(historiqueJoueur1, historiqueJoueur2));
    }

    @Test
    void testPasDeReponseSansTrahison() {
        // Historique uniquement avec coopération
        ArrayList<Decision> historiqueJoueur1 = new ArrayList<>();
        ArrayList<Decision> historiqueJoueur2 = new ArrayList<>();
        historiqueJoueur1.add(Decision.COOPERER);
        historiqueJoueur2.add(Decision.COOPERER);

        // Graduel devrait continuer à coopérer
        assertEquals(Decision.COOPERER, graduel.execute(historiqueJoueur1, historiqueJoueur2));
    }
}
