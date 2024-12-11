package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;

public class PavlovAleatoireTest {

    private PavlovAleatoire pavlovAleatoire;

    @BeforeEach
    public void setup() {
        pavlovAleatoire = new PavlovAleatoire();
    }

    @Test
    public void testComportementHeritePavlov() {
        // Historique où Pavlov aurait une logique déterministe
        ArrayList<Decision> historiqueJoueur1 = new ArrayList<>();
        ArrayList<Decision> historiqueJoueur2 = new ArrayList<>();

        // Cas 1 : Les deux coopèrent
        historiqueJoueur1.add(Decision.COOPERER);
        historiqueJoueur2.add(Decision.COOPERER);

        // Tester plusieurs fois pour vérifier la probabilité d'aléatoire et logique de Pavlov
        int nombreExecutions = 1000;
        int respectLogiquePavlov = 0;

        for (int i = 0; i < nombreExecutions; i++) {
            Decision decision = pavlovAleatoire.execute(historiqueJoueur1, historiqueJoueur2);

            // Si Pavlov coopérerait, vérifier si la logique est respectée
            if (decision == Decision.COOPERER) {
                respectLogiquePavlov++;
            }
        }

        // Une majorité des décisions devrait respecter la logique Pavlov (~75%)
        double proportionRespectPavlov = respectLogiquePavlov / (double) nombreExecutions;
        assertTrue(proportionRespectPavlov > 0.7, "La proportion de décisions respectant Pavlov est hors des limites attendues.");
    }

    @Test
    public void testDecisionsAleatoires() {
        // Historique quelconque
        ArrayList<Decision> historiqueJoueur1 = new ArrayList<>();
        ArrayList<Decision> historiqueJoueur2 = new ArrayList<>();
        historiqueJoueur1.add(Decision.COOPERER);
        historiqueJoueur2.add(Decision.TRAHIR);

        // Vérifie si une décision aléatoire est bien choisie dans le cas aléatoire
        boolean aCoopere = false;
        boolean aTrahi = false;

        for (int i = 0; i < 1000; i++) {
            Decision decision = pavlovAleatoire.execute(historiqueJoueur1, historiqueJoueur2);

            if (decision == Decision.COOPERER) {
                aCoopere = true;
            }
            if (decision == Decision.TRAHIR) {
                aTrahi = true;
            }

            // Sortir si les deux décisions ont été rencontrées
            if (aCoopere && aTrahi) {
                break;
            }
        }

        assertTrue(aCoopere, "La décision COOPERER n'a jamais été rencontrée dans le mode aléatoire.");
        assertTrue(aTrahi, "La décision TRAHIR n'a jamais été rencontrée dans le mode aléatoire.");
    }
}

