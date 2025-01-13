package fr.uga.l3miage.pc.prisonersdilemma.controllers;

import fr.uga.l3miage.pc.prisonersdilemma.application.services.PartiesService;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.TypeStrategy;
import fr.uga.l3miage.pc.prisonersdilemma.domain.exceptions.GameNotInitializedException;
import fr.uga.l3miage.pc.prisonersdilemma.domain.exceptions.MaximumPlayersReachedException;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.inputs.controllers.GameController;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.inputs.requests.DecisionRequest;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.inputs.requests.PseudoRequest;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.inputs.requests.StartGameRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PartiesService partiesService;

    // Start Game Tests
    @Test
    void testStartGameSuccess() throws Exception {
        StartGameRequest request = new StartGameRequest(5, "Francis");

        mockMvc.perform(post("/api/start-game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("La partie a ete demarrée avec succès avec 5 tours."));

        verify(partiesService).demarrerPartie(5);
    }

    @Test
    void testStartGameFailure() throws Exception {
        StartGameRequest request = new StartGameRequest(5, "Francis");
        
        doThrow(new RuntimeException("Game start error")).when(partiesService).demarrerPartie(5);

        mockMvc.perform(post("/api/start-game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Erreur lors du démarrage de la partie : Game start error"));
    }

    // Join Game Tests
    @Test
    void testJoinGameSuccess() throws Exception {
        PseudoRequest request = new PseudoRequest("player1", 5, "ALEATOIRE", true);

        mockMvc.perform(post("/api/join-game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("player1 a rejoint la partie"));

        verify(partiesService).demarrerPartie(5);
        verify(partiesService).addPlayer("player1", true, "ALEATOIRE");
    }

    @Test
    void testJoinGameMaximumPlayersReached() throws Exception {
        PseudoRequest request = new PseudoRequest("player1", 5, "ALEATOIRE", true);
        
        doThrow(new MaximumPlayersReachedException("Maximum players reached")).when(partiesService)
                .addPlayer(anyString(), anyBoolean(), anyString());

        mockMvc.perform(post("/api/join-game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Maximum players reached"));
    }

    // Abandon Game Tests
    @Test
    void testAbandonGameSuccess() throws Exception {
        PseudoRequest request = new PseudoRequest("player1", 5, "ALEATOIRE", true);

        mockMvc.perform(post("/api/abandon")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .param("typeStrategy", "ALEATOIRE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("player1 a abandonné la partie"));

        verify(partiesService).abandonner("player1", TypeStrategy.ALEATOIRE);
    }

    @Test
    void testAbandonGameInvalidStrategy() throws Exception {
        PseudoRequest request = new PseudoRequest("player1", 5, "INVALID", true);

        mockMvc.perform(post("/api/abandon")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .param("typeStrategy", "INVALID_STRATEGY"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Type de stratégie invalide: INVALID_STRATEGY"));
    }

    // Player Count Tests
    @Test
    void testGetPlayerCountSuccess() throws Exception {
        when(partiesService.getNumberOfPlayers()).thenReturn(2);

        mockMvc.perform(get("/api/player-count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(2));
    }

    @Test
    void testGetPlayerCountFailure() throws Exception {
        when(partiesService.getNumberOfPlayers()).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(get("/api/player-count"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.count").value(-1));
    }

    // Submit Decision Tests
    @Test
    void testSubmitDecisionSuccess() throws Exception {
        DecisionRequest request = new DecisionRequest("player1", "COOPERER");
        
        when(partiesService.soumettreDecision(anyString(), any(Decision.class))).thenReturn(true);

        mockMvc.perform(post("/api/soumettre-decision")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Décision soumise avec succès."));
    }

    @Test
    void testSubmitDecisionAlreadySubmitted() throws Exception {
        DecisionRequest request = new DecisionRequest("player1", "COOPERER");
        
        when(partiesService.soumettreDecision(anyString(), any(Decision.class))).thenReturn(false);

        mockMvc.perform(post("/api/soumettre-decision")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Décision déjà soumise ou joueur non trouvé."));
    }

    @Test
    void testSubmitDecisionGameNotInitialized() throws Exception {
        DecisionRequest request = new DecisionRequest("player1", "COOPERER");
        
        when(partiesService.soumettreDecision(anyString(), any(Decision.class)))
                .thenThrow(new GameNotInitializedException("Game not started"));

        mockMvc.perform(post("/api/soumettre-decision")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("La partie n'est pas initialisée."));
    }

    // Get Other Player Decision Tests
    @Test
    void testGetOtherPlayerDecisionSuccess() throws Exception {
        when(partiesService.getDecisionOfOtherPlayer(anyString())).thenReturn(true);

        mockMvc.perform(get("/api/get-decision")
                .param("pseudo", "player1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.decision").value(true));
    }

    @Test
    void testGetOtherPlayerDecisionError() throws Exception {
        when(partiesService.getDecisionOfOtherPlayer(anyString())).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(get("/api/get-decision")
                .param("pseudo", "player1"))
                .andExpect(status().isInternalServerError());
    }

    // Get Player History Tests
    @Test
    void testGetPlayerHistorySuccess() throws Exception {
        when(partiesService.getHistorique(anyString()))
                .thenReturn(Arrays.asList(Decision.COOPERER, Decision.TRAHIR));

        mockMvc.perform(get("/api/get-historique")
                .param("pseudo", "player1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.history").isArray())
                .andExpect(jsonPath("$.history.length()").value(2));
    }

    @Test
    void testGetPlayerHistoryGameNotInitialized() throws Exception {
        when(partiesService.getHistorique(anyString()))
                .thenThrow(new GameNotInitializedException("Game not started"));

        mockMvc.perform(get("/api/get-historique")
                .param("pseudo", "player1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.history").isEmpty());
    }

    @Test
    void testGetPlayerHistoryInternalError() throws Exception {
        when(partiesService.getHistorique(anyString()))
                .thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/api/get-historique")
                .param("pseudo", "player1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.history").isEmpty());
    }
}