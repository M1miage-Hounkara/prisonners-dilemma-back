package fr.uga.l3miage.pc.prisonersdilemma.controllers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.uga.l3miage.pc.prisonersdilemma.controllers.GameController;
import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.enums.TypeStrategy;
import fr.uga.l3miage.pc.prisonersdilemma.exceptions.GameNotInitializedException;
import fr.uga.l3miage.pc.prisonersdilemma.exceptions.MaximumPlayersReachedException;
import fr.uga.l3miage.pc.prisonersdilemma.services.PartiesService;
import fr.uga.l3miage.pc.prisonersdilemma.requests.StartGameRequest;
import fr.uga.l3miage.pc.prisonersdilemma.requests.PseudoRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@WebMvcTest(GameController.class)
class GameControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PartiesService partiesService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testStartGame() throws Exception {
        StartGameRequest request = new StartGameRequest();
        request.setNbTours(10);

        mockMvc.perform(post("/api/start-game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void testJoinGame() throws Exception {
        when(partiesService.isGameStarted()).thenReturn(false);
        doNothing().when(partiesService).demarrerPartie(anyInt());
        doNothing().when(partiesService).addPlayer(anyString(), anyBoolean(), anyString());

        PseudoRequest request = new PseudoRequest();
        request.setPseudo("Player1");
        request.setNbTours(10);
        request.setConnected(true);
        request.setStrategy("CONSTANTE_TRUE");

        mockMvc.perform(post("/api/join-game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists());
    }


    @Test
    void testGetPlayerCount() throws Exception {
        when(partiesService.getNumberOfPlayers()).thenReturn(2);

        mockMvc.perform(get("/api/player-count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerCount").value(2));
    }

    @Test
    void testGetHistorique() throws Exception {
        when(partiesService.getHistorique(anyString()))
            .thenReturn(List.of(Decision.COOPERER, Decision.TRAHIR));

        mockMvc.perform(get("/api/get-historique")
                .param("pseudo", "Player1"))
                .andExpect(status().isOk());
    }
}