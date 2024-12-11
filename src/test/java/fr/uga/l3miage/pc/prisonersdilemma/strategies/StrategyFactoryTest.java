package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.uga.l3miage.pc.prisonersdilemma.enums.TypeStrategy;
import fr.uga.l3miage.pc.prisonersdilemma.interfaces.Strategy;

public class StrategyFactoryTest {

    private StrategyFactory strategyFactory;

    @BeforeEach
    public void setup() {
        strategyFactory = new StrategyFactory();
    }

    @Test
    public void testCreateAleatoire() {
        Strategy strategy = strategyFactory.create(TypeStrategy.ALEATOIRE);
        assertTrue(strategy instanceof Aleatoire, "La stratégie créée n'est pas une instance de Aleatoire");
    }

    @Test
    public void testCreateToujoursCooperer() {
        Strategy strategy = strategyFactory.create(TypeStrategy.TOUJOURS_COOPERER);
        assertTrue(strategy instanceof ToujoursCooperer, "La stratégie créée n'est pas une instance de ToujoursCooperer");
    }

    @Test
    public void testCreateToujoursTrahir() {
        Strategy strategy = strategyFactory.create(TypeStrategy.TOUJOURS_TRAHIR);
        assertTrue(strategy instanceof ToujoursTrahir, "La stratégie créée n'est pas une instance de ToujoursTrahir");
    }

    @Test
    public void testCreatePavlov() {
        Strategy strategy = strategyFactory.create(TypeStrategy.PAVLOV);
        assertTrue(strategy instanceof Pavlov, "La stratégie créée n'est pas une instance de Pavlov");
    }

    @Test
    public void testCreateGraduel() {
        Strategy strategy = strategyFactory.create(TypeStrategy.GRADUEL);
        assertTrue(strategy instanceof Graduel, "La stratégie créée n'est pas une instance de Graduel");
    }

    @Test
    public void testCreateSondeurNaif() {
        Strategy strategy = strategyFactory.create(TypeStrategy.SONDEUR_NAIF);
        assertTrue(strategy instanceof SondeurNaif, "La stratégie créée n'est pas une instance de SondeurNaif");
    }

    @Test
    public void testCreateDonnantDonnant() {
        Strategy strategy = strategyFactory.create(TypeStrategy.DONNANT_DONNANT);
        assertTrue(strategy instanceof DonnantDonnant, "La stratégie créée n'est pas une instance de DonnantDonnant");
    }

    @Test
    public void testCreateRancunier() {
        Strategy strategy = strategyFactory.create(TypeStrategy.RANCUNIER);
        assertTrue(strategy instanceof Rancunier, "La stratégie créée n'est pas une instance de Rancunier");
    }

    @Test
    public void testCreatePacificateurNaif() {
        Strategy strategy = strategyFactory.create(TypeStrategy.PACIFICATEUR_NAIF);
        assertTrue(strategy instanceof PacificateurNaif, "La stratégie créée n'est pas une instance de PacificateurNaif");
    }

    @Test
    public void testCreateVraiPacificateur() {
        Strategy strategy = strategyFactory.create(TypeStrategy.VRAI_PACIFICATEUR);
        assertTrue(strategy instanceof VraiPacificateur, "La stratégie créée n'est pas une instance de VraiPacificateur");
    }

    @Test
    public void testCreateRancunierDoux() {
        Strategy strategy = strategyFactory.create(TypeStrategy.RANCUNIER_DOUX);
        assertTrue(strategy instanceof RancunierDoux, "La stratégie créée n'est pas une instance de RancunierDoux");
    }

    @Test
    public void testCreatePavlovAleatoire() {
        Strategy strategy = strategyFactory.create(TypeStrategy.PAVLOV_ALEATOIRE);
        assertTrue(strategy instanceof PavlovAleatoire, "La stratégie créée n'est pas une instance de PavlovAleatoire");
    }

    @Test
    public void testCreateAdaptative() {
        Strategy strategy = strategyFactory.create(TypeStrategy.ADAPTATIVE);
        assertTrue(strategy instanceof Adaptatif, "La stratégie créée n'est pas une instance de Adaptatif");
    }

    @Test
    public void testCreateSondeurRepentant() {
        Strategy strategy = strategyFactory.create(TypeStrategy.SONDEUR_REPENTANT);
        assertTrue(strategy instanceof SondeurRepentant, "La stratégie créée n'est pas une instance de SondeurRepentant");
    }

    @Test
    public void testCreateDonnantDonnantAleatoire() {
        Strategy strategy = strategyFactory.create(TypeStrategy.DONNANT_DONNANT_ALEATOIRE);
        assertTrue(strategy instanceof DonnantDonnantAleatoire, "La stratégie créée n'est pas une instance de DonnantDonnantAleatoire");
    }

    @Test
    public void testCreateDonnantDonnantSoupconneux() {
        Strategy strategy = strategyFactory.create(TypeStrategy.DONNANT_DONNANT_SOUPCONNEUX);
        assertTrue(strategy instanceof DonnantDonnantSoupconneux, "La stratégie créée n'est pas une instance de DonnantDonnantSoupconneux");
    }

    @Test
    public void testCreateDonnantPourDeuxDonnants() {
        Strategy strategy = strategyFactory.create(TypeStrategy.DONNANT_POUR_DEUX_DONNANTS);
        assertTrue(strategy instanceof DonnantPourDeuxDonnants, "La stratégie créée n'est pas une instance de DonnantPourDeuxDonnants");
    }

    @Test
    public void testCreateDonnantPourDeuxDonnantsAleatoire() {
        Strategy strategy = strategyFactory.create(TypeStrategy.DONNANT_POUR_DEUX_DONNANTS_ALEATOIRE);
        assertTrue(strategy instanceof DonnantPourDeuxDonnantsEtAleatoire, "La stratégie créée n'est pas une instance de DonnantPourDeuxDonnantsEtAleatoire");
    }
}
