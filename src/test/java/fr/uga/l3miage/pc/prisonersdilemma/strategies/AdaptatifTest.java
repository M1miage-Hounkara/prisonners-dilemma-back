package fr.uga.l3miage.pc.prisonersdilemma.strategies;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;

class AdaptatifTest {
    private Adaptatif strategy;
    private ArrayList<Decision> historiqueJoueur1;
    private ArrayList<Decision> historiqueJoueur2;

    @BeforeEach
    void setUp() {
        strategy = new Adaptatif();
        historiqueJoueur1 = new ArrayList<>();
        historiqueJoueur2 = new ArrayList<>();
    }

    @Test
    void testInitialStrategySequence() {
        Decision[] expectedSequence = {
            Decision.COOPERER, Decision.COOPERER, Decision.COOPERER, 
            Decision.COOPERER, Decision.COOPERER, Decision.COOPERER,
            Decision.TRAHIR, Decision.TRAHIR, Decision.TRAHIR, 
            Decision.TRAHIR, Decision.TRAHIR
        };

        for (Decision expectedDecision : expectedSequence) {
            assertEquals(expectedDecision, strategy.execute(historiqueJoueur1, historiqueJoueur2));
        }
    }

    @Test
    void testStrategyAdaptation() {
        for (int i = 0; i < 11; i++) {
            strategy.execute(historiqueJoueur1, historiqueJoueur2);
        }

        testScenario(Decision.COOPERER, Decision.COOPERER, 3);  
        testScenario(Decision.TRAHIR, Decision.COOPERER, 5);  
        testScenario(Decision.TRAHIR, Decision.TRAHIR, 1);  
    }

    private void testScenario(Decision lastPlayerDecision, Decision lastOpponentDecision, int expectedScore) {
        historiqueJoueur1.clear();
        historiqueJoueur2.clear();

        historiqueJoueur1.add(lastPlayerDecision);
        historiqueJoueur2.add(lastOpponentDecision);
        Decision adaptedDecision = strategy.execute(historiqueJoueur1, historiqueJoueur2);
        assertNotNull(adaptedDecision, "Decision should not be null");
        assertTrue(Arrays.asList(Decision.COOPERER, Decision.TRAHIR).contains(adaptedDecision));
    }
}