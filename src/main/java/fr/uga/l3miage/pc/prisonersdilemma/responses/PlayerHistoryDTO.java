package fr.uga.l3miage.pc.prisonersdilemma.responses;

import java.util.List;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;

public record PlayerHistoryDTO(List<Decision> history) {

}
