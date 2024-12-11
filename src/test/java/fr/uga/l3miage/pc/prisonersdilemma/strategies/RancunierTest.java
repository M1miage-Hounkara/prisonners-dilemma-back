package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;

class RancunierTest {
    private Rancunier rancunier;
    private ArrayList<Decision> historiqueJoueur1;
    private ArrayList<Decision> historiqueJoueur2;
    @BeforeEach
    void setUp(){
        rancunier = new Rancunier();
        historiqueJoueur1 = new ArrayList<>();
        historiqueJoueur2 = new ArrayList<>();
    }

    @Test
    void testExecuteNoBetrayal(){
        Decision result = rancunier.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.COOPERER, result);
    }

    @Test
    void testExecuteBetrayal(){
        historiqueJoueur2.add(Decision.TRAHIR);
        Decision result = rancunier.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.TRAHIR, result);
        //On vérifie que même après que l'adversaire coopère, le rancunier continue de trahir
        historiqueJoueur2.add(Decision.COOPERER);
        result = rancunier.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.TRAHIR, result);
    }
}
