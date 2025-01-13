package fr.uga.l3miage.pc.prisonersdilemma.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import fr.uga.l3miage.pc.prisonersdilemma.application.services.PartiesService;
import fr.uga.l3miage.pc.prisonersdilemma.application.ports.outputs.PartieRepository;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.TypeStrategy;
import fr.uga.l3miage.pc.prisonersdilemma.domain.exceptions.GameNotInitializedException;
import fr.uga.l3miage.pc.prisonersdilemma.domain.exceptions.MaximumPlayersReachedException;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Partie;

class PartiesServiceTest {
    @Mock
    private PartieRepository partieRepository;

    @InjectMocks
    private PartiesService partiesService;

    private Partie partieTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        partieTest = new Partie(10);
        partieTest.addJoueur("Player1", true, TypeStrategy.TOUJOURS_COOPERER);
        partieTest.addJoueur("Player2", true, TypeStrategy.TOUJOURS_TRAHIR);
    }

    @Test
    void testDemarrerPartie() {
        // Given
        when(partieRepository.exists()).thenReturn(false);
        
        // When
        partiesService.demarrerPartie(10);
        
        // Then
        verify(partieRepository).save(any(Partie.class));
    }

    @Test
    void testDemarrerPartieQuandPartieExiste() {
        // Given
        when(partieRepository.exists()).thenReturn(true);
        
        // When/Then
        assertThrows(IllegalStateException.class, 
            () -> partiesService.demarrerPartie(10));
    }

    @Test
    void testAddPlayer() throws MaximumPlayersReachedException, GameNotInitializedException {
        // Given
        Partie partie = new Partie(10);
        when(partieRepository.find()).thenReturn(Optional.of(partie));
        
        // When
        partiesService.addPlayer("Player1", true, "TOUJOURS_COOPERER");
        
        // Then
        assertEquals(1, partie.getNbJoueurs());
        verify(partieRepository).find();
    }

    @Test
    void testAddPlayerBeforeGameStart() {
        // Given
        when(partieRepository.find()).thenReturn(Optional.empty());
        
        // When/Then
        assertThrows(GameNotInitializedException.class,
            () -> partiesService.addPlayer("Player1", true, "TOUJOURS_COOPERER"));
    }

    @Test
    void testMaximumPlayersReached() throws MaximumPlayersReachedException, GameNotInitializedException {
        // Given
        Partie partie = new Partie(10);
        when(partieRepository.find()).thenReturn(Optional.of(partie));
        
        // When
        partiesService.addPlayer("Player1", true, "TOUJOURS_COOPERER");
        partiesService.addPlayer("Player2", true, "TOUJOURS_TRAHIR");
        
        // Then
        assertThrows(MaximumPlayersReachedException.class,
            () -> partiesService.addPlayer("Player3", true, "TOUJOURS_COOPERER"));
    }

    @Test
    void testSoumettreDecision() throws Exception {
        // Given
        Partie partie = new Partie(10);
        partie.addJoueur("Player1", true, TypeStrategy.TOUJOURS_COOPERER);
        partie.addJoueur("Player2", true, TypeStrategy.TOUJOURS_TRAHIR);
        when(partieRepository.find()).thenReturn(Optional.of(partie));
        
        // When
        boolean result = partiesService.soumettreDecision("Player1", Decision.COOPERER);
        
        // Then
        assertTrue(result);
    }

    @Test
    void testAbandonner() throws MaximumPlayersReachedException, GameNotInitializedException {
        // Given
        Partie partie = new Partie(10);
        partie.addJoueur("Player1", true, TypeStrategy.TOUJOURS_COOPERER);
        partie.addJoueur("Player2", true, TypeStrategy.TOUJOURS_COOPERER);
        when(partieRepository.find()).thenReturn(Optional.of(partie));
        
        // When/Then
        assertDoesNotThrow(() -> 
            partiesService.abandonner("Player1", TypeStrategy.TOUJOURS_COOPERER));
    }

    @Test
    void testGetHistorique() throws Exception {
        // Given
        Partie partie = new Partie(10);
        partie.addJoueur("Player1", true, TypeStrategy.TOUJOURS_COOPERER);
        partie.addJoueur("Player2", true, TypeStrategy.TOUJOURS_TRAHIR);
        when(partieRepository.find()).thenReturn(Optional.of(partie));
        when(partieRepository.exists()).thenReturn(true);
        
        // When
        partie.soumettreDecision("Player1", Decision.COOPERER);
        List<Decision> historique = partiesService.getHistorique("Player1");
        
        // Then
        assertNotNull(historique);
    }

    @Test
    void testTerminerPartie() {
        // Given
        when(partieRepository.exists()).thenReturn(true);
        
        // When
        partiesService.terminerPartie();
        
        // Then
        verify(partieRepository).delete();
    }

    @Test
    void testTerminerPartieQuandPasDePartie() {
        // Given
        when(partieRepository.exists()).thenReturn(false);
        
        // When/Then
        assertThrows(IllegalStateException.class, 
            () -> partiesService.terminerPartie());
    }

    @Test
    void testGetScore() throws GameNotInitializedException {
        // Given
        when(partieRepository.find()).thenReturn(Optional.of(partieTest));
        
        // When
        Integer score = partiesService.getScore("Player1");
        
        // Then
        assertNotNull(score);
        verify(partieRepository).find();
    }


    @Test
    void testGetWinner() throws GameNotInitializedException {
        // Given
        when(partieRepository.find()).thenReturn(Optional.of(partieTest));
        
        // When
        Integer winner = partiesService.getWinner("Player1");
        
        // Then
        assertNotNull(winner);
        verify(partieRepository).find();
    }

    @Test
    void testGetWinnerPartieNonInitialisee() {
        // Given
        when(partieRepository.find()).thenReturn(Optional.empty());
        
        // When/Then
        assertThrows(GameNotInitializedException.class, 
            () -> partiesService.getWinner("Player1"));
    }

    @Test
    void testGetGameStatus() throws GameNotInitializedException {
        // Given
        when(partieRepository.find()).thenReturn(Optional.of(partieTest));
        
        // When
        boolean status = partiesService.getGameStatus();
        
        // Then
        assertFalse(status); // La partie vient d'être créée, elle ne devrait pas être terminée
        verify(partieRepository).find();
    }

    @Test
    void testGetNumberOfPlayers() throws GameNotInitializedException {
        // Given
        when(partieRepository.find()).thenReturn(Optional.of(partieTest));
        
        // When
        int nbPlayers = partiesService.getNumberOfPlayers();
        
        // Then
        assertEquals(2, nbPlayers);
        verify(partieRepository).find();
    }


    @Test
    void testPeutJouerTourPartieNonInitialisee() {
        // Given
        when(partieRepository.find()).thenReturn(Optional.empty());
        
        // When/Then
        assertThrows(GameNotInitializedException.class, 
            () -> partiesService.peutJouerTour());
    }

    @Test
    void testGetDecisionOfOtherPlayer() throws GameNotInitializedException {
        // Given
        when(partieRepository.find()).thenReturn(Optional.of(partieTest));
        partieTest.soumettreDecision("Player2", Decision.TRAHIR);
        
        // When
        boolean decision = partiesService.getDecisionOfOtherPlayer("Player1");
        
        // Then
        verify(partieRepository).find();
    }

    @Test
    void testGetDecisionOfOtherPlayerPartieNonInitialisee() {
        // Given
        when(partieRepository.find()).thenReturn(Optional.empty());
        
        // When/Then
        assertThrows(GameNotInitializedException.class, 
            () -> partiesService.getDecisionOfOtherPlayer("Player1"));
    }
}