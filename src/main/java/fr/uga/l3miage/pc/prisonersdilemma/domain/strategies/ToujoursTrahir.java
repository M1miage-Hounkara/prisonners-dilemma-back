package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;


import java.util.List;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.interfaces.Strategy;
import lombok.Getter;

@Getter
public class ToujoursTrahir implements Strategy {

    public Decision execute(List<Decision> historiqueJoueur1, List<Decision> historiqueJoueur2) {
        return Decision.TRAHIR;
    }
}
