package fr.uga.l3miage.pc.prisonersdilemma.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.enums.TypeStrategy;
import fr.uga.l3miage.pc.prisonersdilemma.exceptions.GameNotInitializedException;
import fr.uga.l3miage.pc.prisonersdilemma.exceptions.MaximumPlayersReachedException;
import fr.uga.l3miage.pc.prisonersdilemma.requests.PseudoRequest;
import fr.uga.l3miage.pc.prisonersdilemma.requests.StartGameRequest;
import fr.uga.l3miage.pc.prisonersdilemma.services.PartiesService;

@RestController
@RequestMapping("/api") 
public class GameController {
    @Autowired
    PartiesService partiesService;

    @PostMapping("/start-game")
    public ResponseEntity<Map<String, String>> startGame(@RequestBody StartGameRequest request) {
        try {
            partiesService.demarrerPartie(request.getNbTours(), request.getPseudo());
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "La partie a ete demarrée avec succès avec " + request.getNbTours() + " tours.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Erreur lors du démarrage de la partie : " + e.getMessage()));
        }
    }

    @PostMapping("/join-game")
    public ResponseEntity<Map<String, String>> joinGame(@RequestBody PseudoRequest request) {
        try {
            partiesService.addPlayer(request.getPseudo(), request.isConnected(), request.getStrategy());
        } catch (MaximumPlayersReachedException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", request.getPseudo() + " a rejoint la partie");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/abandon")
    public ResponseEntity<Map<String, String>> abandon(@RequestBody PseudoRequest request, @RequestParam String typeStrategy) {
        String pseudo = request.getPseudo();
        try {
            partiesService.abandonner(pseudo, TypeStrategy.valueOf(typeStrategy));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Type de stratÃ©gie invalide: " + typeStrategy));
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", pseudo + " a abandonnÃ© la partie");
        return ResponseEntity.ok(response);
    }


    @PostMapping("/play")
    public ResponseEntity<Map<String, String>> play(@RequestBody PseudoRequest request, @RequestParam String decision) throws GameNotInitializedException {
        String pseudo = request.getPseudo();
        Map<String, String> response = new HashMap<>();

        try {
            boolean success = partiesService.soumettreDecision(pseudo, Decision.valueOf(decision));

            if (!success) {
                return ResponseEntity.badRequest().body(Map.of("message", "Erreur lors de la soumission de la décision pour " + pseudo));
            }

            if (partiesService.peutJouerTour()) {
                partiesService.jouerTour();
                response.put("message", "Tour joué avec succès.");
                return ResponseEntity.ok(response);
            }

            response.put("message", pseudo + " a joué " + decision + ". En attente de l'autre joueur.");
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Erreur de décision: " + decision));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}