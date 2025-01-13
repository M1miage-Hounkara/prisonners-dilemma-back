package fr.uga.l3miage.pc.prisonersdilemma.domain.models;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.interfaces.Strategy;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Joueur {
    
    private Strategy strategy;
    private Decision decision ;
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
