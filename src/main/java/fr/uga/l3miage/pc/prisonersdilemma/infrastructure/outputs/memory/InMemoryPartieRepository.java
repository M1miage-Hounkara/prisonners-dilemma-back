package fr.uga.l3miage.pc.prisonersdilemma.infrastructure.outputs.memory;

import fr.uga.l3miage.pc.prisonersdilemma.application.ports.outputs.PartieRepository;


import org.springframework.stereotype.Repository;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Partie;
import java.util.Optional;

@Repository
public class InMemoryPartieRepository implements PartieRepository {
    private Optional<Partie> partie = Optional.empty();

    @Override
    public void save(Partie partie) {
        this.partie = Optional.of(partie);
    }

    @Override
    public Optional<Partie> find() {
        return partie;
    }

    @Override
    public void delete() {
        this.partie = Optional.empty();
    }

    @Override
    public boolean exists() {
        return partie.isPresent();
    }

}
