package dz.me.dashboard.services.implement;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dz.me.dashboard.entities.Rubrique;
import dz.me.dashboard.repositories.RubriqueRepository;
import dz.me.dashboard.services.RubriqueService;

@Service
public class RubriqueServiceImpl implements RubriqueService {

    @Autowired
    RubriqueRepository repository;

    @Override
    public Optional<Rubrique> findById(UUID uuid) {

        return repository.findById(uuid);
    }

}
