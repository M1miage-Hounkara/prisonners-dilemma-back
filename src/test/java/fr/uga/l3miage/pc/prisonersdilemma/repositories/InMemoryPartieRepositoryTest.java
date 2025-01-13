package fr.uga.l3miage.pc.prisonersdilemma.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Partie;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.outputs.memory.InMemoryPartieRepository;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.TypeStrategy;
import fr.uga.l3miage.pc.prisonersdilemma.domain.exceptions.MaximumPlayersReachedException;

import java.util.Optional;

class InMemoryPartieRepositoryTest {
    private InMemoryPartieRepository repository;
    private Partie partie;

    @BeforeEach
    void setUp(){
        repository = new InMemoryPartieRepository();
        partie = new Partie(10);
        partie.addJoueur("Player1", true, TypeStrategy.TOUJOURS_COOPERER);
    }

    @Test
    void testSavePartie() {
        // When
        repository.save(partie);

        // Then
        assertTrue(repository.exists());
        Optional<Partie> savedPartie = repository.find();
        assertTrue(savedPartie.isPresent());
        assertEquals(partie, savedPartie.get());
    }

    @Test
    void testFindWhenEmpty() {
        // When
        Optional<Partie> result = repository.find();

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAfterSave() {
        // Given
        repository.save(partie);

        // When
        Optional<Partie> result = repository.find();

        // Then
        assertTrue(result.isPresent());
        Partie foundPartie = result.get();
        assertEquals(partie, foundPartie);
        assertEquals(1, foundPartie.getNbJoueurs());
    }

    @Test
    void testDelete() {
        // Given
        repository.save(partie);
        assertTrue(repository.exists());

        // When
        repository.delete();

        // Then
        assertFalse(repository.exists());
        assertTrue(repository.find().isEmpty());
    }

    @Test
    void testExistsWhenEmpty() {
        // When/Then
        assertFalse(repository.exists());
    }

    @Test
    void testExistsAfterSave() {
        // When
        repository.save(partie);

        // Then
        assertTrue(repository.exists());
    }

    @Test
    void testExistsAfterDelete() {
        // Given
        repository.save(partie);
        
        // When
        repository.delete();

        // Then
        assertFalse(repository.exists());
    }

    @Test
    void testSaveUpdatesExistingPartie() throws MaximumPlayersReachedException {
        // Given
        repository.save(partie);
        Partie updatedPartie = new Partie(20);
        updatedPartie.addJoueur("Player2", false, TypeStrategy.TOUJOURS_TRAHIR);

        // When
        repository.save(updatedPartie);

        // Then
        Optional<Partie> result = repository.find();
        assertTrue(result.isPresent());
        Partie foundPartie = result.get();
        assertEquals(updatedPartie, foundPartie);
        assertEquals(1, foundPartie.getNbJoueurs());
        assertNotEquals(partie, foundPartie);
    }
}
