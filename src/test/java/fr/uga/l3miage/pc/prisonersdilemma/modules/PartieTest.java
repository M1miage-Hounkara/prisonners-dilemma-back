package fr.uga.l3miage.pc.prisonersdilemma.modules;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.TypeStrategy;
import fr.uga.l3miage.pc.prisonersdilemma.domain.interfaces.Strategy;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Joueur;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Partie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.strategies.StrategyFactory;






 class PartieTest {

    private Partie partie;
    private StrategyFactory strategyFactory;

    @BeforeEach
     void setUp() {
        strategyFactory = Mockito.mock(StrategyFactory.class);
        partie = new Partie(5);
        partie.setStrategyFactory(strategyFactory);
    }

    @Test
     void testAddJoueur() {
        Mockito.when(strategyFactory.create(TypeStrategy.TOUJOURS_COOPERER)).thenReturn(Mockito.mock(Strategy.class));
        partie.addJoueur("Player1", true, TypeStrategy.TOUJOURS_COOPERER);
        assertEquals(1, partie.getNbJoueurs());
        assertEquals("Player1", partie.getJoueurs().get(0).getName());
    }

    @Test
     void testAddJoueurWithEmptyName() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            partie.addJoueur("", true, TypeStrategy.TOUJOURS_COOPERER);
        });
        assertEquals("Le nom du joueur ne peut pas être vide.", exception.getMessage());
    }

    @Test
     void testAddJoueurWithDuplicateName() {
        Mockito.when(strategyFactory.create(TypeStrategy.TOUJOURS_COOPERER)).thenReturn(Mockito.mock(Strategy.class));
        partie.addJoueur("Player1", true, TypeStrategy.TOUJOURS_COOPERER);
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            partie.addJoueur("Player1", true, TypeStrategy.TOUJOURS_COOPERER);
        });
        assertEquals("Le nom du joueur est déjà utilisé.", exception.getMessage());
    }

    // @Test
    //  void testAbandonner() {
    //     Mockito.when(strategyFactory.create(TypeStrategy.TOUJOURS_COOPERER)).thenReturn(Mockito.mock(Strategy.class));
    //     partie.addJoueur("Player1", true, TypeStrategy.TOUJOURS_COOPERER);
    //     Joueur joueur = partie.getJoueurs().get(0);
    //     partie.abandonner(joueur, TypeStrategy.TOUJOURS_COOPERER);
    //     assertFalse(joueur.isConnected());
    // }

    @Test
     void testSoumettreDecision() {
        Mockito.when(strategyFactory.create(TypeStrategy.TOUJOURS_COOPERER)).thenReturn(Mockito.mock(Strategy.class));
        partie.addJoueur("Player1", true, TypeStrategy.TOUJOURS_COOPERER);
        partie.addJoueur("Player2", true, TypeStrategy.TOUJOURS_COOPERER);
        boolean result = partie.soumettreDecision("Player1", Decision.COOPERER);
        assertTrue(result);
        assertEquals(Decision.COOPERER, partie.getJoueurs().get(0).getDecision());
    }

    @Test
     void testProcessRound() {
        Mockito.when(strategyFactory.create(TypeStrategy.TOUJOURS_COOPERER)).thenReturn(Mockito.mock(Strategy.class));
        partie.addJoueur("Player1", true, TypeStrategy.TOUJOURS_COOPERER);
        partie.addJoueur("Player2", true, TypeStrategy.TOUJOURS_COOPERER);
        partie.soumettreDecision("Player1", Decision.COOPERER);
        partie.soumettreDecision("Player2", Decision.COOPERER);
        assertEquals(3, partie.getJoueurs().get(0).getScore());
        assertEquals(3, partie.getJoueurs().get(1).getScore());
    }
    


    @Test
     void testGetDecisionOfOtherPlayer() {
        Mockito.when(strategyFactory.create(TypeStrategy.TOUJOURS_COOPERER)).thenReturn(Mockito.mock(Strategy.class));
        partie.addJoueur("Player1", true, TypeStrategy.TOUJOURS_COOPERER);
        partie.addJoueur("Player2", true, TypeStrategy.TOUJOURS_COOPERER);
        partie.soumettreDecision("Player1", Decision.COOPERER);
        assertFalse(partie.getDecisionOfOtherPlayer("Player2"));
    }

    @Test
     void testGetHistorique() {
        Mockito.when(strategyFactory.create(TypeStrategy.TOUJOURS_COOPERER)).thenReturn(Mockito.mock(Strategy.class));
        partie.addJoueur("Player1", true, TypeStrategy.TOUJOURS_COOPERER);
        partie.soumettreDecision("Player1", Decision.COOPERER);
        assertEquals(1, partie.getHistorique("Player1").size());
    }

    @Test
     void testResetDecisions() {
        Mockito.when(strategyFactory.create(TypeStrategy.TOUJOURS_COOPERER)).thenReturn(Mockito.mock(Strategy.class));
        partie.addJoueur("Player1", true, TypeStrategy.TOUJOURS_COOPERER);
        partie.addJoueur("Player2", true, TypeStrategy.TOUJOURS_COOPERER);
        partie.soumettreDecision("Player1", Decision.COOPERER);
        partie.soumettreDecision("Player2", Decision.COOPERER);
        partie.resetDecisions();
        assertNull(partie.getJoueurs().get(0).getDecision());
        assertNull(partie.getJoueurs().get(1).getDecision());
    }

@Test
 void testPeutJouerTour() {
    Mockito.when(strategyFactory.create(TypeStrategy.TOUJOURS_COOPERER)).thenReturn(Mockito.mock(Strategy.class));
    partie.addJoueur("Player1", true, TypeStrategy.TOUJOURS_COOPERER);
    partie.addJoueur("Player2", true, TypeStrategy.TOUJOURS_COOPERER);
    
    // Test when no decisions are made
    assertFalse(partie.peutJouerTour());
    
    // Test when only one player has made a decision
    partie.soumettreDecision("Player1", Decision.COOPERER);
    assertFalse(partie.peutJouerTour());
    
    // Test when both players have made decisions
    partie.soumettreDecision("Player2", Decision.COOPERER);
    assertTrue(partie.peutJouerTour());
}

@Test
void testProcessRoundAllCombinations() {
    Mockito.when(strategyFactory.create(TypeStrategy.TOUJOURS_COOPERER)).thenReturn(Mockito.mock(Strategy.class));
    partie.addJoueur("Player1", true, TypeStrategy.TOUJOURS_COOPERER);
    partie.addJoueur("Player2", true, TypeStrategy.TOUJOURS_COOPERER);
    
    // Test COOPERER-COOPERER
    partie.soumettreDecision("Player1", Decision.COOPERER);
    partie.soumettreDecision("Player2", Decision.COOPERER);
    assertEquals(3, partie.getJoueurs().get(0).getScore(), "Score incorrect pour COOPERER-COOPERER");
    assertEquals(3, partie.getJoueurs().get(1).getScore(), "Score incorrect pour COOPERER-COOPERER");

    // Test COOPERER-TRAHIR
    partie.resetDecisions();
    partie.soumettreDecision("Player1", Decision.COOPERER);
    partie.soumettreDecision("Player2", Decision.TRAHIR);
    assertEquals(3, partie.getJoueurs().get(0).getScore(), "Score incorrect pour COOPERER-TRAHIR");
    assertEquals(8, partie.getJoueurs().get(1).getScore(), "Score incorrect pour COOPERER-TRAHIR");

    // Test TRAHIR-COOPERER
    partie.resetDecisions();
    partie.soumettreDecision("Player1", Decision.TRAHIR);
    partie.soumettreDecision("Player2", Decision.COOPERER);
    assertEquals(8, partie.getJoueurs().get(0).getScore(), "Score incorrect pour TRAHIR-COOPERER");
    assertEquals(8, partie.getJoueurs().get(1).getScore(), "Score incorrect pour TRAHIR-COOPERER");

    // Test TRAHIR-TRAHIR
    partie.resetDecisions();
    partie.soumettreDecision("Player1", Decision.TRAHIR);
    partie.soumettreDecision("Player2", Decision.TRAHIR);
    assertEquals(9, partie.getJoueurs().get(0).getScore(), "Score incorrect pour TRAHIR-TRAHIR");
    assertEquals(9, partie.getJoueurs().get(1).getScore(), "Score incorrect pour TRAHIR-TRAHIR");
}


@Test
 void testAddDisconnectedJoueur() {
    partie.addJoueur("Bot", false, TypeStrategy.TOUJOURS_COOPERER);
    Joueur joueur = partie.getJoueurs().get(0);
    assertEquals("Blip-Boup-Bap", joueur.getName());
    assertFalse(joueur.isConnected());
}

@Test
 void testGetDecisionOfOtherPlayerWithNoOtherPlayer() {
    Mockito.when(strategyFactory.create(TypeStrategy.TOUJOURS_COOPERER)).thenReturn(Mockito.mock(Strategy.class));
    partie.addJoueur("Player1", true, TypeStrategy.TOUJOURS_COOPERER);
    assertFalse(partie.getDecisionOfOtherPlayer("Player1"));
}


@Test
 void testGetHistoriqueForNonExistentPlayer() {
    assertTrue(partie.getHistorique("NonExistentPlayer").isEmpty());
}

@Test
 void testMultipleRounds() {
    Mockito.when(strategyFactory.create(TypeStrategy.TOUJOURS_COOPERER)).thenReturn(Mockito.mock(Strategy.class));
    partie.addJoueur("Player1", true, TypeStrategy.TOUJOURS_COOPERER);
    partie.addJoueur("Player2", true, TypeStrategy.TOUJOURS_COOPERER);
    
    // Play multiple rounds
    for(int i = 0; i < 3; i++) {
        partie.soumettreDecision("Player1", Decision.COOPERER);
        partie.soumettreDecision("Player2", Decision.COOPERER);
    }
    
    assertEquals(3, partie.getHistorique("Player1").size());
    assertEquals(3, partie.getHistorique("Player2").size());
    assertEquals(9, partie.getJoueurs().get(0).getScore()); // 3 points * 3 rounds
}

@Test
void testGetWinnerPlayer1Wins() {
    Mockito.when(strategyFactory.create(TypeStrategy.TOUJOURS_COOPERER)).thenReturn(Mockito.mock(Strategy.class));
    partie.addJoueur("Player1", true, TypeStrategy.TOUJOURS_COOPERER);
    partie.addJoueur("Player2", true, TypeStrategy.TOUJOURS_COOPERER);

    partie.getJoueurs().get(0).ajouterScore(10); // Player1 score
    partie.getJoueurs().get(1).ajouterScore(5);  // Player2 score

    int result = partie.getWinner("Player1");
    assertEquals(1, result);
}

@Test
void testGetWinnerPlayer2Wins() {
    Mockito.when(strategyFactory.create(TypeStrategy.TOUJOURS_COOPERER)).thenReturn(Mockito.mock(Strategy.class));
    partie.addJoueur("Player1", true, TypeStrategy.TOUJOURS_COOPERER);
    partie.addJoueur("Player2", true, TypeStrategy.TOUJOURS_COOPERER);

    partie.getJoueurs().get(0).ajouterScore(5);  // Player1 score
    partie.getJoueurs().get(1).ajouterScore(10); // Player2 score

    int result = partie.getWinner("Player2");
    assertEquals(1, result);
}

@Test
void testGetWinnerDraw() {
    Mockito.when(strategyFactory.create(TypeStrategy.TOUJOURS_COOPERER)).thenReturn(Mockito.mock(Strategy.class));
    partie.addJoueur("Player1", true, TypeStrategy.TOUJOURS_COOPERER);
    partie.addJoueur("Player2", true, TypeStrategy.TOUJOURS_COOPERER);

    partie.getJoueurs().get(0).ajouterScore(10); // Player1 score
    partie.getJoueurs().get(1).ajouterScore(10); // Player2 score

    int result = partie.getWinner("Player1");
    assertEquals(0, result);
}

@Test
void testGetWinnerPlayerNotFound() {
    Mockito.when(strategyFactory.create(TypeStrategy.TOUJOURS_COOPERER)).thenReturn(Mockito.mock(Strategy.class));
    partie.addJoueur("Player1", true, TypeStrategy.TOUJOURS_COOPERER);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        partie.getWinner("NonExistentPlayer");
    });

    assertEquals("Player with pseudo NonExistentPlayer not found.", exception.getMessage());
}

@Test
void testGetWinnerOtherPlayerNotFound() {
    Mockito.when(strategyFactory.create(TypeStrategy.TOUJOURS_COOPERER)).thenReturn(Mockito.mock(Strategy.class));
    partie.addJoueur("Player1", true, TypeStrategy.TOUJOURS_COOPERER);

    Exception exception = assertThrows(IllegalStateException.class, () -> {
        partie.getWinner("Player1");
    });

    assertEquals("Other player not found.", exception.getMessage());
}

}