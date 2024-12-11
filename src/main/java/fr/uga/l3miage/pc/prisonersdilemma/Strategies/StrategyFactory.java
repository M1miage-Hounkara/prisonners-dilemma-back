package fr.uga.l3miage.pc.prisonersdilemma.strategies;
import fr.uga.l3miage.pc.prisonersdilemma.enums.TypeStrategy;
import fr.uga.l3miage.pc.prisonersdilemma.interfaces.Strategy;

public class StrategyFactory {

    public Strategy create(TypeStrategy typeStrategy) {
        switch (typeStrategy) {
            case ALEATOIRE:
                return new Aleatoire();
            case TOUJOURS_COOPERER:
                return new ToujoursCooperer();
            case TOUJOURS_TRAHIR:
                return new ToujoursTrahir();
            case PAVLOV:
                return new Pavlov();
            case GRADUEL:
                return new Graduel();
            case SONDEUR_NAIF:
                return new SondeurNaif();
            case DONNANT_DONNANT:
                return new DonnantDonnant();
            case RANCUNIER:
                return new Rancunier();
            case PACIFICATEUR_NAIF:
                return new PacificateurNaif();
            case VRAI_PACIFICATEUR:
                return new VraiPacificateur();
            case RANCUNIER_DOUX:
                return new RancunierDoux();
            case PAVLOV_ALEATOIRE:
                return new PavlovAleatoire();
            case ADAPTATIVE:
                return new Adaptatif();
            case SONDEUR_REPENTANT:
                return new SondeurRepentant();
            case DONNANT_DONNANT_ALEATOIRE:
                return new DonnantDonnantAleatoire();
            case DONNANT_DONNANT_SOUPCONNEUX:
                return new DonnantDonnantSoupconneux();
            case DONNANT_POUR_DEUX_DONNANTS:
                return new DonnantPourDeuxDonnants();
            case DONNANT_POUR_DEUX_DONNANTS_ALEATOIRE:
                return new DonnantPourDeuxDonnantsEtAleatoire();
            default:
            return new Aleatoire();
        }
    }
}
