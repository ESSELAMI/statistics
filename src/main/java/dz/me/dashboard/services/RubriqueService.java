package dz.me.dashboard.services;

import java.util.Optional;
import java.util.UUID;

import dz.me.dashboard.entities.Rubrique;

public interface RubriqueService {

    public Optional<Rubrique> findById(UUID uuid);

}
