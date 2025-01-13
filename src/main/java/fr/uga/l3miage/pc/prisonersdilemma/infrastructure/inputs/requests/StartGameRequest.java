package fr.uga.l3miage.pc.prisonersdilemma.infrastructure.inputs.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StartGameRequest {
    private int nbTours;
    private String pseudo;
}
