package fr.uga.l3miage.pc.prisonersdilemma.strategies;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.enums.TypeStrategy;

class StrategyAdapterTest {
    private StrategyAdapter strategyAdapter;
    private ArrayList<Decision> owDecisions;
    private ArrayList<Decision> opponentDecisions;

    @BeforeEach
    void setUp() {
        strategyAdapter = new StrategyAdapter(TypeStrategy.TOUJOURS_COOPERER);
        owDecisions = new ArrayList<>();
        opponentDecisions = new ArrayList<>();
    }

    @Test
    void testExecuteWithEmptyLists() {
        Decision result = strategyAdapter.execute(owDecisions, opponentDecisions);
        assertNotNull(result, "Decision should not be null");
    }

    @Test
    void testExecuteWithCooperateDecisions() {
        owDecisions.add(Decision.COOPERER);
        opponentDecisions.add(Decision.COOPERER);
        
        Decision result = strategyAdapter.execute(owDecisions, opponentDecisions);
        assertNotNull(result, "Decision should not be null");
    }

    @Test
    void testExecuteWithBetrayDecisions() {
        owDecisions.add(Decision.TRAHIR);
        opponentDecisions.add(Decision.TRAHIR);
        
        Decision result = strategyAdapter.execute(owDecisions, opponentDecisions);
        assertNotNull(result, "Decision should not be null");
    }


    @Test
    void testInvalidDecisionListSizes() {
        owDecisions.add(Decision.COOPERER);
        opponentDecisions.add(Decision.COOPERER);
        opponentDecisions.add(Decision.TRAHIR);
        
        assertThrows(IllegalArgumentException.class, 
            () -> strategyAdapter.execute(owDecisions, opponentDecisions),
            "Should throw exception when decision list sizes differ"
        );
    }

    @Test
    void testMultipleRoundExecution() {
        owDecisions.add(Decision.COOPERER);
        owDecisions.add(Decision.TRAHIR);
        opponentDecisions.add(Decision.COOPERER);
        opponentDecisions.add(Decision.TRAHIR);
        
        Decision result = strategyAdapter.execute(owDecisions, opponentDecisions);
        assertNotNull(result, "Decision should not be null after multiple rounds");
    }
}