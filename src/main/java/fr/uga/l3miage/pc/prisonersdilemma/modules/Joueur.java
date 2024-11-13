package fr.uga.l3miage.pc.prisonersdilemma.modules;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.interfaces.Strategy;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Joueur {
    
    private Strategy strategy = null;
    private Decision decision = null;
    private String name = null;
    private Integer score = 0;
    private boolean connected;  

    public Joueur(){
        this.connected = false;
    }

    public void ajouterScore(Integer score) {
        this.score += score;
    }
}