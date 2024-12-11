package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;

class DonnantDonnantTest {
    private DonnantDonnant donnantDonnant;
    private ArrayList<Decision> historiqueJoueur1;
    private ArrayList<Decision> historiqueJoueur2;

    @BeforeEach
    public void setUp() {
        donnantDonnant = new DonnantDonnant();
        historiqueJoueur1 = new ArrayList<>();
        historiqueJoueur2 = new ArrayList<>();
    }

    @Test
    void testExecuteHistoriqueAdverseVide() {
        Decision result = donnantDonnant.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.COOPERER, result);
    }

    @Test
    void testExecuteCasNormal(){
        historiqueJoueur2.add(Decision.COOPERER);
        Decision result = donnantDonnant.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.COOPERER, result);
    }
}
