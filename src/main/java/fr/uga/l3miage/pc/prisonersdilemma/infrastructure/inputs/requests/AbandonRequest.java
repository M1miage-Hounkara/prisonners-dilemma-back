package fr.uga.l3miage.pc.prisonersdilemma.infrastructure.inputs.requests;

import org.springframework.boot.autoconfigure.web.WebProperties.Resources.Chain.Strategy;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AbandonRequest {
    private String pseudo;
    private Strategy strategy;
}