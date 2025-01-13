package fr.uga.l3miage.pc.prisonersdilemma.domain.interfaces;

import java.util.ArrayList;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;

public interface Strategy {
    public Decision execute(ArrayList<Decision> historiqueJoueur1, ArrayList<Decision> historiqueJoueur2);
}