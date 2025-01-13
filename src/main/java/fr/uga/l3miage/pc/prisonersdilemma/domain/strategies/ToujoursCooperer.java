package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;
import java.util.ArrayList;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.interfaces.Strategy;
import lombok.Getter;

@Getter
public class ToujoursCooperer implements Strategy {

    public Decision execute(ArrayList<Decision> historiqueJoueur1, ArrayList<Decision> historiqueJoueur2) {
        return Decision.COOPERER;
    }
}
