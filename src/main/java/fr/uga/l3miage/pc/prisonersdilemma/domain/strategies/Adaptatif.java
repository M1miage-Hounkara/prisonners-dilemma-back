package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;
import java.util.ArrayList;
import java.util.List;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.interfaces.Strategy;

public class Adaptatif implements Strategy {
    private int meanScoreTrahison = 0;
    private int meanScoreCooperation = 0;
    private final ArrayList<Decision> initierStrategie = new ArrayList<>();
    public Adaptatif(){
            initierStrategie.add(Decision.COOPERER);
            initierStrategie.add(Decision.COOPERER);
            initierStrategie.add(Decision.COOPERER);
            initierStrategie.add(Decision.COOPERER);
            initierStrategie.add(Decision.COOPERER);
            initierStrategie.add(Decision.COOPERER);
            initierStrategie.add(Decision.TRAHIR);
            initierStrategie.add(Decision.TRAHIR);
            initierStrategie.add(Decision.TRAHIR);
            initierStrategie.add(Decision.TRAHIR);
            initierStrategie.add(Decision.TRAHIR);
        }
    @Override
    public Decision execute(List<Decision> historiqueJoueur1, List<Decision> historiqueJoueur2) {
        if(!initierStrategie.isEmpty()) {
            return initierStrategie.remove(0);
        }
        updateMeanScore(historiqueJoueur1, historiqueJoueur2);
        if(meanScoreCooperation > meanScoreTrahison) {
            return Decision.COOPERER;
        }
        return Decision.TRAHIR;
    }
    private void updateMeanScore(List<Decision> historiqueJoueur1, List<Decision> historiqueJoueur2){
        if(historiqueJoueur1.isEmpty() || historiqueJoueur2.isEmpty()) {
            return;
        }
        if(historiqueJoueur1.get(historiqueJoueur1.size() - 1) == Decision.COOPERER) {
            meanScoreCooperation = ((meanScoreCooperation*(historiqueJoueur1.size() - 1)) + getScore(historiqueJoueur1, historiqueJoueur2))/historiqueJoueur1.size();
        }
        else {
            meanScoreTrahison = ((meanScoreTrahison*(historiqueJoueur1.size()-1)) + getScore(historiqueJoueur1, historiqueJoueur2))/historiqueJoueur1.size();
        }
    }

    private int getScore(List<Decision> historiqueJoueur1, List<Decision> historiqueJoueur2){
        if(historiqueJoueur1.isEmpty() || historiqueJoueur2.isEmpty()) {
            return -1;
        }
        if(historiqueJoueur1.get(historiqueJoueur1.size() - 1) == Decision.COOPERER && historiqueJoueur2.get(historiqueJoueur2.size() - 1) == Decision.COOPERER) {
            return 3;
        }
        if(historiqueJoueur1.get(historiqueJoueur1.size() - 1) == Decision.TRAHIR && historiqueJoueur2.get(historiqueJoueur2.size() - 1) == Decision.COOPERER) {
            return 5;
        }
        if(historiqueJoueur1.get(historiqueJoueur1.size() - 1) == Decision.TRAHIR && historiqueJoueur2.get(historiqueJoueur2.size() - 1) == Decision.TRAHIR) {
            return 1;
        }
        return 0;
    }
}
