package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import java.util.ArrayList;
import java.util.Arrays;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import lombok.Getter;

@Getter
public class RancunierDoux extends Rancunier {
    private ArrayList<Decision> punition = new ArrayList<>(Arrays.asList(Decision.TRAHIR,Decision.TRAHIR,Decision.TRAHIR,Decision.TRAHIR,Decision.COOPERER,Decision.COOPERER));
    private Integer count = 0;
    @Override
    public Decision execute(ArrayList<Decision> historiqueJoueur1, ArrayList<Decision> historiqueJoueur2){
        setTrahison(historiqueJoueur2);
        if(trahison){
           Decision decision = punition.get(count);
           count ++;
           return decision;
        }
        return Decision.COOPERER;
    }

    @Override
    protected void setTrahison(ArrayList<Decision> historiqueJoueur2){
        if(historiqueJoueur2.isEmpty()){
            return;
        }
        if(historiqueJoueur2.get(historiqueJoueur2.size()-1)== Decision.TRAHIR){
            trahison = true;
        }
        if(count == punition.size()-1){
            trahison = false;
            count = 0;
        }
    }
}
