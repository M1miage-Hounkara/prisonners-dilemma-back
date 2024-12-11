package fr.uga.l3miage.pc.prisonersdilemma.controllers;

import java.io.Console;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.enums.TypeStrategy;
import fr.uga.l3miage.pc.prisonersdilemma.exceptions.GameNotInitializedException;
import fr.uga.l3miage.pc.prisonersdilemma.exceptions.MaximumPlayersReachedException;
import fr.uga.l3miage.pc.prisonersdilemma.requests.DecisionRequest;
import fr.uga.l3miage.pc.prisonersdilemma.requests.PseudoRequest;
import fr.uga.l3miage.pc.prisonersdilemma.requests.StartGameRequest;
import fr.uga.l3miage.pc.prisonersdilemma.services.PartiesService;
import jakarta.annotation.Generated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api") 
public class GameController {
    @Autowired
    PartiesService partiesService;

    @PostMapping("/start-game")
    public ResponseEntity<Map<String, String>> startGame(@RequestBody StartGameRequest request) {
        try {
            partiesService.demarrerPartie(request.getNbTours());
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "La partie a ete demarrée avec succès avec " + request.getNbTours() + " tours.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Erreur lors du démarrage de la partie : " + e.getMessage()));
        }
    }

    @PostMapping("/join-game")
    public ResponseEntity<Map<String, String>> joinGame(@RequestBody PseudoRequest request) {
        String pseudo = request.getPseudo();
        Integer nbTours = request.getNbTours(); 
        boolean isConnected = request.isConnected();
        System.out.println("Pseudo: " + pseudo);
        System.out.println("NbTours: " + nbTours);
        System.out.println("Strategie: " + request.getStrategy());
        try {
            if (!partiesService.isGameStarted()) {
              
                partiesService.demarrerPartie(nbTours); 
            }

            partiesService.addPlayer(pseudo, isConnected, request.getStrategy());
        } catch (MaximumPlayersReachedException e) {
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


    // @PostMapping("/play")
    // public ResponseEntity<Map<String, String>> play(@RequestBody PseudoRequest request, @RequestParam String decision) throws GameNotInitializedException {
    //     String pseudo = request.getPseudo();
    //     Map<String, String> response = new HashMap<>();

    //     try {
    //         boolean success = partiesService.soumettreDecision(pseudo, Decision.valueOf(decision));

    //         if (!success) {
    //             return ResponseEntity.badRequest().body(Map.of("message", "Erreur lors de la soumission de la décision pour " + pseudo));
    //         }

    //         if (partiesService.peutJouerTour()) {
    //             partiesService.jouerTour();
    //             response.put("message", "Tour joué avec succès.");
    //             return ResponseEntity.ok(response);
    //         }

    //         response.put("message", pseudo + " a joué " + decision + ". En attente de l'autre joueur.");
    //         return ResponseEntity.ok(response);

    //     } catch (IllegalArgumentException e) {
    //         return ResponseEntity.badRequest().body(Map.of("message", "Erreur de décision: " + decision));
    //     } catch (IllegalStateException e) {
    //         return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
    //     }
    // }

    @GetMapping("/player-count")
    public ResponseEntity<Map<String, Integer>> getPlayerCount() {
        try {
            int playerCount = partiesService.getNumberOfPlayers();
            Map<String, Integer> response = new HashMap<>();
            response.put("playerCount", playerCount);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("playerCount", -1));
        }
    }
 

    @PostMapping("/soumettre-decision")
    public ResponseEntity<Boolean> soumettreDecision(@RequestBody DecisionRequest request) {
        try {

            boolean success = partiesService.soumettreDecision(request.getPseudo(), Decision.valueOf(request.getDecision()));
            if (success) {
                System.out.println("Décision soumise, pseudo: " + request.getPseudo() + ", decision: " + request.getDecision());
                return ResponseEntity.ok(true);
            } else {
                return ResponseEntity.badRequest().body(false);
            }
        } catch (GameNotInitializedException e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @GetMapping("/get-decision")
    public ResponseEntity<Boolean> getDecisionOfOtherPlayerController(@RequestParam String pseudo) {
        try {
            Boolean otherPlayerDecision = partiesService.getDecisionOfOtherPlayer(pseudo);
            return ResponseEntity.ok(otherPlayerDecision);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    } 

    @GetMapping("/get-historique")
    public ResponseEntity<List<Decision>> getHistorique(@RequestParam String pseudo) {
        try {
            List<Decision> historique = partiesService.getHistorique(pseudo);
            return ResponseEntity.ok(historique);
        } catch (GameNotInitializedException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }   

    @GetMapping("/get-status") 
    public ResponseEntity<Boolean> getGameStatus() {
       try{
            boolean status = partiesService.getGameStatus();
            return ResponseEntity.ok(status);
       }
       catch(Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
       }
    }

    @GetMapping("/get-score")
    public ResponseEntity<Integer> getScore(@RequestParam String pseudo) {
        try {
            Integer score = partiesService.getScore(pseudo);
            return ResponseEntity.ok(score);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/get-winner")
    public ResponseEntity<Integer> getWinner(@RequestParam String pseudo) {
        try {
            Integer winner = partiesService.getWinner(pseudo);
            return ResponseEntity.ok(winner);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    

}