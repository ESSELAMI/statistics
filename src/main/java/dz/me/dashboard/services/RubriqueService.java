package dz.me.dashboard.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dz.me.dashboard.entities.Rubrique;

public interface RubriqueService {
    Rubrique save(Rubrique rubrique);

    public Optional<Rubrique> findById(UUID uuid);

    public List<Rubrique> findAll();

}
