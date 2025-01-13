package fr.uga.l3miage.pc.prisonersdilemma.infrastructure.inputs.responses;

import java.util.List;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;

public record PlayerHistoryDTO(List<Decision> history) {

}
