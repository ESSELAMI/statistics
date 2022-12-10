package dz.me.dashboard.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dz.me.dashboard.entities.Traitement;

public interface TraitementService {

    public List<Traitement> findTraitementByService(UUID idService);

    public Traitement save(Traitement traitement);

    public Optional<Traitement> findById(UUID traitement);

    public void deleteById(UUID fromString);

    public List<Traitement> findAll();

}
