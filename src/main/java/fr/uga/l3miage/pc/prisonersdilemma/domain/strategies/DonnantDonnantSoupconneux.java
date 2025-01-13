package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import java.util.List;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.interfaces.Strategy;

public class DonnantDonnantSoupconneux implements Strategy
{
    @Override
    public Decision execute(List<Decision> historiqueJoueur1, List<Decision> historiqueJoueur2)
    {
        if (historiqueJoueur2.isEmpty())
        {
            return Decision.TRAHIR;
        }
        return historiqueJoueur2.get(historiqueJoueur2.size() - 1);
    }

}
