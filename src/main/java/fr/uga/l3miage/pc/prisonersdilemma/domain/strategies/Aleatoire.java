package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import java.util.ArrayList;
import java.util.Random;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.interfaces.Strategy;
import lombok.Getter;

@Getter
public class Aleatoire implements Strategy {
    private final Random random = new Random();
    public Decision execute(ArrayList<Decision> historiqueJoueur1, ArrayList<Decision> historiqueJoueur2) {
        return random.nextBoolean() ? Decision.COOPERER : Decision.TRAHIR;
    }
}
