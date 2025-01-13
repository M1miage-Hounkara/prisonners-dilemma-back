package fr.uga.l3miage.pc.prisonersdilemma.infrastructure.inputs.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fr.uga.l3miage.pc.prisonersdilemma.application.services.PartiesService;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.TypeStrategy;
import fr.uga.l3miage.pc.prisonersdilemma.domain.exceptions.GameNotInitializedException;
import fr.uga.l3miage.pc.prisonersdilemma.domain.exceptions.MaximumPlayersReachedException;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.inputs.requests.DecisionRequest;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.inputs.requests.PseudoRequest;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.inputs.requests.StartGameRequest;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.inputs.responses.CountDTO;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.inputs.responses.PlayerDecisionDTO;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.inputs.responses.PlayerHistoryDTO;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.inputs.responses.SystemResponseDTO;
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RestController
@RequestMapping("/api")
public class GameController {
    @Autowired
    PartiesService partiesService;

    @PostMapping("/start-game")
    public ResponseEntity<SystemResponseDTO> startGame(@RequestBody StartGameRequest request) {
        try {
            partiesService.demarrerPartie(request.getNbTours());
            
            return ResponseEntity.ok(new SystemResponseDTO(
                "La partie a ete demarrée avec succès avec " + request.getNbTours() + " tours."
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new SystemResponseDTO("Erreur lors du démarrage de la partie : " + e.getMessage())
            );
        }
    }

    @PostMapping("/join-game")
    public ResponseEntity<SystemResponseDTO> joinGame(@RequestBody PseudoRequest request) {
        String pseudo = request.getPseudo();
        Integer nbTours = request.getNbTours(); 
        boolean isConnected = request.isConnected();

        try {
            
            partiesService.demarrerPartie(nbTours); 
            partiesService.addPlayer(pseudo, isConnected, request.getStrategy());

            return ResponseEntity.ok(new SystemResponseDTO(
                request.getPseudo() + " a rejoint la partie"
            ));
        } catch (MaximumPlayersReachedException | GameNotInitializedException e) {
            return ResponseEntity.badRequest().body(
                new SystemResponseDTO(e.getMessage())
            );
        }
    }

    @PostMapping("/abandon")
    public ResponseEntity<SystemResponseDTO> abandon(@RequestBody PseudoRequest request, @RequestParam String typeStrategy) {
        String pseudo = request.getPseudo();
        try {
            partiesService.abandonner(pseudo, TypeStrategy.valueOf(typeStrategy));
            return ResponseEntity.ok(new SystemResponseDTO(
                pseudo + " a abandonné la partie"
            ));
        } catch (IllegalArgumentException  | GameNotInitializedException e) {
            return ResponseEntity.badRequest().body(
                new SystemResponseDTO("Type de stratégie invalide: " + typeStrategy)
            );
        }
    }

    @GetMapping("/player-count")
    public ResponseEntity<CountDTO> getPlayerCount() {
        try {
            int playerCount = partiesService.getNumberOfPlayers();
            return ResponseEntity.ok(new CountDTO(playerCount));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CountDTO(-1));
        }
    }

    @PostMapping("/soumettre-decision")
    public ResponseEntity<SystemResponseDTO> soumettreDecision(@RequestBody DecisionRequest request) {
        try {
            boolean success = partiesService.soumettreDecision(request.getPseudo(), Decision.valueOf(request.getDecision()));
            if (success) {
                return ResponseEntity.ok(new SystemResponseDTO(
                    "Décision soumise avec succès."
                ));
            } else {
                return ResponseEntity.badRequest().body(
                    new SystemResponseDTO("Décision déjà soumise ou joueur non trouvé.")
                );
            }
        } catch (GameNotInitializedException e) {
            return ResponseEntity.badRequest().body(
                new SystemResponseDTO("La partie n'est pas initialisée.")
            );
        }
    }

    @GetMapping("/get-decision")
    public ResponseEntity<PlayerDecisionDTO> getDecisionOfOtherPlayerController(@RequestParam String pseudo) {
        try {
            Boolean otherPlayerDecision = partiesService.getDecisionOfOtherPlayer(pseudo);
            return ResponseEntity.ok(new PlayerDecisionDTO(otherPlayerDecision));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null);
        }
    } 

    @GetMapping("/get-historique")
    public ResponseEntity<PlayerHistoryDTO> getHistorique(@RequestParam String pseudo) {
        try {
            List<Decision> historique = partiesService.getHistorique(pseudo);
            return ResponseEntity.ok(new PlayerHistoryDTO(historique));
        } catch (GameNotInitializedException e) {
            return ResponseEntity.badRequest().body(new PlayerHistoryDTO(null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new PlayerHistoryDTO(null));
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
    public ResponseEntity<CountDTO> getScore(@RequestParam String pseudo) {
        try {
            Integer score = partiesService.getScore(pseudo);
            return ResponseEntity.ok(new CountDTO(score));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/get-winner")
    public ResponseEntity<CountDTO> getWinner(@RequestParam String pseudo) {
        try {
            Integer winner = partiesService.getWinner(pseudo);
            return ResponseEntity.ok(new CountDTO(winner));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @DeleteMapping("/terminer")
    public void terminerPartie() {
        partiesService.terminerPartie();
    }
}