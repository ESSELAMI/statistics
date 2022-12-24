package dz.me.dashboard.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dz.me.dashboard.entities.Rubrique;
import dz.me.dashboard.entities.RubriqueValue;

public interface RubriqueValueService {
    RubriqueValue save(RubriqueValue rubriqueValue);

    public Optional<RubriqueValue> findById(UUID uuid);

    public List<RubriqueValue> findByRubriqueId(UUID uuid);

    public List<RubriqueValue> findAll();

    void deleteById(UUID serviceId);

}
