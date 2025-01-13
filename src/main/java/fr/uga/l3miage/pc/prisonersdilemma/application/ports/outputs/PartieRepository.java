package fr.uga.l3miage.pc.prisonersdilemma.application.ports.outputs;

import java.util.Optional;

import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Partie;

public interface PartieRepository {
    void save(Partie partie);
    Optional<Partie> find();
    void delete();
    boolean exists();
}
