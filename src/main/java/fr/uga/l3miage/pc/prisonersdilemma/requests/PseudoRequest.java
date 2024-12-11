package fr.uga.l3miage.pc.prisonersdilemma.requests;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PseudoRequest {
    private String pseudo; 
    private Integer nbTours;
    private String strategy;
    private boolean connected;
    

    
}