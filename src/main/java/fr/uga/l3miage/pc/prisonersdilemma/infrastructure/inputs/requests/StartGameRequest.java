package fr.uga.l3miage.pc.prisonersdilemma.infrastructure.inputs.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StartGameRequest {
    private int nbTours;
    private String pseudo;
}
