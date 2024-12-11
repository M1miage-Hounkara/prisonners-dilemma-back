package fr.uga.l3miage.pc.prisonersdilemma.services;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.enums.TypeStrategy;
import fr.uga.l3miage.pc.prisonersdilemma.exceptions.GameNotInitializedException;
import fr.uga.l3miage.pc.prisonersdilemma.exceptions.MaximumPlayersReachedException;

class PartiesServiceTest {
    @InjectMocks
    private PartiesService partiesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDemarrerPartie() {
        partiesService.demarrerPartie(10);
        assertTrue(partiesService.isGameStarted());
    }

    @Test
    void testAddPlayer() throws MaximumPlayersReachedException, GameNotInitializedException {
        partiesService.demarrerPartie(10);
        partiesService.addPlayer("Player1", true, "TOUJOURS_COOPERER");
        assertEquals(1, partiesService.getNumberOfPlayers());
    }

    @Test
    void testAddPlayerBeforeGameStart() {
        assertThrows(IllegalStateException.class, 
            () -> partiesService.addPlayer("Player1", true, "TOUJOURS_COOPERER"));
    }

    @Test
    void testMaximumPlayersReached() throws MaximumPlayersReachedException, GameNotInitializedException {
        partiesService.demarrerPartie(10);
        partiesService.addPlayer("Player1", true, "TOUJOURS_COOPERER");
        partiesService.addPlayer("Player2", true, "TOUJOURS_TRAHIR");
        
        assertThrows(MaximumPlayersReachedException.class, 
            () -> partiesService.addPlayer("Player3", true, "TOUJOURS_COOPERER"));
    }

    @Test
    void testSoumettreDecision() throws Exception {
        partiesService.demarrerPartie(10);
        partiesService.addPlayer("Player1", true, "TOUJOURS_COOPERER");
        partiesService.addPlayer("Player2", true, "TOUJOURS_TRAHIR");
        
        boolean result = partiesService.soumettreDecision("Player1", Decision.COOPERER);
        assertTrue(result);
    }

    @Test
    void testSoumettreDecisionBeforeGameStart() {
        assertThrows(GameNotInitializedException.class, 
            () -> partiesService.soumettreDecision("Player1", Decision.COOPERER));
    }

    @Test
    void testAbandonner() throws MaximumPlayersReachedException, GameNotInitializedException {
        partiesService.demarrerPartie(10);
        partiesService.addPlayer("Player1", true, "TOUJOURS_COOPERER");
        partiesService.addPlayer("Player2", true, "TOUJOURS_COOPERER");
        
        assertDoesNotThrow(() -> partiesService.abandonner("Player1", TypeStrategy.TOUJOURS_COOPERER));
    }

    @Test
    void testAbandonnerNonExistentPlayer() {
        partiesService.demarrerPartie(10);
        assertThrows(IllegalArgumentException.class, 
            () -> partiesService.abandonner("NonExistentPlayer", TypeStrategy.TOUJOURS_COOPERER));
    }

    @Test
    void testGetHistorique() throws Exception {
        partiesService.demarrerPartie(10);
        partiesService.addPlayer("Player1", true, "TOUJOURS_COOPERER");
        partiesService.addPlayer("Player2", true, "TOUJOURS_TRAHIR");
        partiesService.soumettreDecision("Player1", Decision.COOPERER);
        
        List<Decision> historique = partiesService.getHistorique("Player1");
        assertNotNull(historique);
    }

    @Test
    void testGetHistoriqueBeforeGameStart() {
        assertThrows(GameNotInitializedException.class, 
            () -> partiesService.getHistorique("Player1"));
    }
}