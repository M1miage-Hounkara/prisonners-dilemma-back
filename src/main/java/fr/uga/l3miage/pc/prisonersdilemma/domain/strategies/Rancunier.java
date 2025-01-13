package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import java.util.List;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.interfaces.Strategy;
import lombok.Getter;

@Getter
public class Rancunier implements Strategy{
    protected boolean trahison = false;

    @Override
    public Decision execute(List<Decision> historiqueJoueur1, List<Decision> historiqueJoueur2){
        setTrahison(historiqueJoueur2);
        if(trahison){
            return Decision.TRAHIR;
        }
        return Decision.COOPERER;
    }

    protected void setTrahison(List<Decision> historiqueJoueur2){
        if(trahison || historiqueJoueur2.isEmpty()){
            return;
        }
        if(historiqueJoueur2.get(historiqueJoueur2.size()-1)== Decision.TRAHIR){
            trahison = true;
        }
    }
}
