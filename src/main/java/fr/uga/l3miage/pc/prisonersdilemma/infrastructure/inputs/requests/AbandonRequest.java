package fr.uga.l3miage.pc.prisonersdilemma.infrastructure.inputs.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AbandonRequest {
    private String pseudo;
    private String strategy;
}