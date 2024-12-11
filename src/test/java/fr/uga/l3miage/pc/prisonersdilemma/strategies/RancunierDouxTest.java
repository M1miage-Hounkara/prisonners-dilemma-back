package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;

class RancunierDouxTest {
    private RancunierDoux rancunierDoux;
    private ArrayList<Decision> historiqueJoueur1;
    private ArrayList<Decision> historiqueJoueur2;
    @BeforeEach
    public void setUp(){
        rancunierDoux = new RancunierDoux();
        historiqueJoueur1 = new ArrayList<>();
        historiqueJoueur2 = new ArrayList<>();
    }
    @Test
    void testExecuteTrahisonFalse(){
        Decision result = rancunierDoux.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.COOPERER, result);
    }

    @Test
    void testExecuteTrahisonTrue(){
        historiqueJoueur2.add(Decision.TRAHIR);
        Decision result = rancunierDoux.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.TRAHIR, result);
        result = rancunierDoux.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.TRAHIR, result);
        result = rancunierDoux.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.TRAHIR, result);
        result = rancunierDoux.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.TRAHIR, result);
        result = rancunierDoux.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.COOPERER, result);
        result = rancunierDoux.execute(historiqueJoueur1, historiqueJoueur2);
        assertEquals(Decision.COOPERER, result);
        assertEquals(0, (int) rancunierDoux.getCount());
        assertEquals(false, rancunierDoux.isTrahison());
    }
    
}
