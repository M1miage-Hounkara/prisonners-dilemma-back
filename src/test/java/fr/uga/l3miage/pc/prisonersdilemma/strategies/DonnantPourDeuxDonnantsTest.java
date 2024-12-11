package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;

class DonnantPourDeuxDonnantsTest {
    private DonnantPourDeuxDonnants donnantPourDeuxDonnants;
    private ArrayList<Decision> historiqueJoueur1;
    private ArrayList<Decision> historiqueJoueur2;

    @BeforeEach
    public void setUp(){
        donnantPourDeuxDonnants = new DonnantPourDeuxDonnants();
        historiqueJoueur1 = new ArrayList<>();
        historiqueJoueur2 = new ArrayList<>();
    }
    
    @Test
    void testExecuteHistoriqueAdverseVide(){
        Decision result = donnantPourDeuxDonnants.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.COOPERER, result);
    }

    @Test
    void testExecuteHistoriqueAdverseInferieurA2(){
        historiqueJoueur2.add(Decision.TRAHIR);
        Decision result = donnantPourDeuxDonnants.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.COOPERER, result);
    }

    @Test
    void testExecuteDecisionsDifferentes(){
        historiqueJoueur2.add(Decision.TRAHIR);
        historiqueJoueur2.add(Decision.COOPERER);
        Decision result = donnantPourDeuxDonnants.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.COOPERER, result);
    }

    @Test
    void testExecuteDecisionsSimilaires(){
        historiqueJoueur2.add(Decision.TRAHIR);
        historiqueJoueur2.add(Decision.TRAHIR);
        Decision result = donnantPourDeuxDonnants.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.TRAHIR, result);
    }
}
