package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.TypeStrategy;
import fr.uga.l3miage.pc.prisonersdilemma.domain.interfaces.Strategy;
import fr.uga.l3miage.pc.prisonersdilemma.domain.strategies.Adaptatif;
import fr.uga.l3miage.pc.prisonersdilemma.domain.strategies.Aleatoire;
import fr.uga.l3miage.pc.prisonersdilemma.domain.strategies.DonnantDonnant;
import fr.uga.l3miage.pc.prisonersdilemma.domain.strategies.DonnantDonnantAleatoire;
import fr.uga.l3miage.pc.prisonersdilemma.domain.strategies.DonnantDonnantSoupconneux;
import fr.uga.l3miage.pc.prisonersdilemma.domain.strategies.DonnantPourDeuxDonnants;
import fr.uga.l3miage.pc.prisonersdilemma.domain.strategies.DonnantPourDeuxDonnantsEtAleatoire;
import fr.uga.l3miage.pc.prisonersdilemma.domain.strategies.Graduel;
import fr.uga.l3miage.pc.prisonersdilemma.domain.strategies.PacificateurNaif;
import fr.uga.l3miage.pc.prisonersdilemma.domain.strategies.Pavlov;
import fr.uga.l3miage.pc.prisonersdilemma.domain.strategies.PavlovAleatoire;
import fr.uga.l3miage.pc.prisonersdilemma.domain.strategies.Rancunier;
import fr.uga.l3miage.pc.prisonersdilemma.domain.strategies.RancunierDoux;
import fr.uga.l3miage.pc.prisonersdilemma.domain.strategies.SondeurNaif;
import fr.uga.l3miage.pc.prisonersdilemma.domain.strategies.SondeurRepentant;
import fr.uga.l3miage.pc.prisonersdilemma.domain.strategies.StrategyFactory;
import fr.uga.l3miage.pc.prisonersdilemma.domain.strategies.ToujoursCooperer;
import fr.uga.l3miage.pc.prisonersdilemma.domain.strategies.ToujoursTrahir;
import fr.uga.l3miage.pc.prisonersdilemma.domain.strategies.VraiPacificateur;

class StrategyFactoryTest {

    private StrategyFactory strategyFactory;

    @BeforeEach
    public void setup() {
        strategyFactory = new StrategyFactory();
    }

    @Test
    void testCreateAleatoire() {
        Strategy strategy = strategyFactory.create(TypeStrategy.ALEATOIRE);
        assertTrue(strategy instanceof Aleatoire, "La stratégie créée n'est pas une instance de Aleatoire");
    }

    @Test
    void testCreateToujoursCooperer() {
        Strategy strategy = strategyFactory.create(TypeStrategy.TOUJOURS_COOPERER);
        assertTrue(strategy instanceof ToujoursCooperer, "La stratégie créée n'est pas une instance de ToujoursCooperer");
    }

    @Test
    void testCreateToujoursTrahir() {
        Strategy strategy = strategyFactory.create(TypeStrategy.TOUJOURS_TRAHIR);
        assertTrue(strategy instanceof ToujoursTrahir, "La stratégie créée n'est pas une instance de ToujoursTrahir");
    }

    @Test
    void testCreatePavlov() {
        Strategy strategy = strategyFactory.create(TypeStrategy.PAVLOV);
        assertTrue(strategy instanceof Pavlov, "La stratégie créée n'est pas une instance de Pavlov");
    }

    @Test
    void testCreateGraduel() {
        Strategy strategy = strategyFactory.create(TypeStrategy.GRADUEL);
        assertTrue(strategy instanceof Graduel, "La stratégie créée n'est pas une instance de Graduel");
    }

    @Test
    void testCreateSondeurNaif() {
        Strategy strategy = strategyFactory.create(TypeStrategy.SONDEUR_NAIF);
        assertTrue(strategy instanceof SondeurNaif, "La stratégie créée n'est pas une instance de SondeurNaif");
    }

    @Test
    void testCreateDonnantDonnant() {
        Strategy strategy = strategyFactory.create(TypeStrategy.DONNANT_DONNANT);
        assertTrue(strategy instanceof DonnantDonnant, "La stratégie créée n'est pas une instance de DonnantDonnant");
    }

    @Test
    void testCreateRancunier() {
        Strategy strategy = strategyFactory.create(TypeStrategy.RANCUNIER);
        assertTrue(strategy instanceof Rancunier, "La stratégie créée n'est pas une instance de Rancunier");
    }

    @Test
    void testCreatePacificateurNaif() {
        Strategy strategy = strategyFactory.create(TypeStrategy.PACIFICATEUR_NAIF);
        assertTrue(strategy instanceof PacificateurNaif, "La stratégie créée n'est pas une instance de PacificateurNaif");
    }

    @Test
    void testCreateVraiPacificateur() {
        Strategy strategy = strategyFactory.create(TypeStrategy.VRAI_PACIFICATEUR);
        assertTrue(strategy instanceof VraiPacificateur, "La stratégie créée n'est pas une instance de VraiPacificateur");
    }

    @Test
    void testCreateRancunierDoux() {
        Strategy strategy = strategyFactory.create(TypeStrategy.RANCUNIER_DOUX);
        assertTrue(strategy instanceof RancunierDoux, "La stratégie créée n'est pas une instance de RancunierDoux");
    }

    @Test
    void testCreatePavlovAleatoire() {
        Strategy strategy = strategyFactory.create(TypeStrategy.PAVLOV_ALEATOIRE);
        assertTrue(strategy instanceof PavlovAleatoire, "La stratégie créée n'est pas une instance de PavlovAleatoire");
    }

    @Test
    void testCreateAdaptative() {
        Strategy strategy = strategyFactory.create(TypeStrategy.ADAPTATIVE);
        assertTrue(strategy instanceof Adaptatif, "La stratégie créée n'est pas une instance de Adaptatif");
    }

    @Test
    void testCreateSondeurRepentant() {
        Strategy strategy = strategyFactory.create(TypeStrategy.SONDEUR_REPENTANT);
        assertTrue(strategy instanceof SondeurRepentant, "La stratégie créée n'est pas une instance de SondeurRepentant");
    }

    @Test
    void testCreateDonnantDonnantAleatoire() {
        Strategy strategy = strategyFactory.create(TypeStrategy.DONNANT_DONNANT_ALEATOIRE);
        assertTrue(strategy instanceof DonnantDonnantAleatoire, "La stratégie créée n'est pas une instance de DonnantDonnantAleatoire");
    }

    @Test
    void testCreateDonnantDonnantSoupconneux() {
        Strategy strategy = strategyFactory.create(TypeStrategy.DONNANT_DONNANT_SOUPCONNEUX);
        assertTrue(strategy instanceof DonnantDonnantSoupconneux, "La stratégie créée n'est pas une instance de DonnantDonnantSoupconneux");
    }

    @Test
    void testCreateDonnantPourDeuxDonnants() {
        Strategy strategy = strategyFactory.create(TypeStrategy.DONNANT_POUR_DEUX_DONNANTS);
        assertTrue(strategy instanceof DonnantPourDeuxDonnants, "La stratégie créée n'est pas une instance de DonnantPourDeuxDonnants");
    }

    @Test
    void testCreateDonnantPourDeuxDonnantsAleatoire() {
        Strategy strategy = strategyFactory.create(TypeStrategy.DONNANT_POUR_DEUX_DONNANTS_ALEATOIRE);
        assertTrue(strategy instanceof DonnantPourDeuxDonnantsEtAleatoire, "La stratégie créée n'est pas une instance de DonnantPourDeuxDonnantsEtAleatoire");
    }
}
