package fr.uga.l3miage.pc.prisonersdilemma.domain.interfaces;

import java.util.List;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;

public interface Strategy {
    public Decision execute(List<Decision> historiqueJoueur1, List<Decision> historiqueJoueur2);
}