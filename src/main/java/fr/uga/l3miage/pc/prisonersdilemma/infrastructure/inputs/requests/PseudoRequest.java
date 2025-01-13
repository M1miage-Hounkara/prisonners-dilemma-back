package fr.uga.l3miage.pc.prisonersdilemma.infrastructure.inputs.requests;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PseudoRequest {
    private String pseudo; 
    private Integer nbTours;
    private String strategy;
    private boolean connected;
    

    
}