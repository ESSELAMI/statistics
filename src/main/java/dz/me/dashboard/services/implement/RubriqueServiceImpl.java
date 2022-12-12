package dz.me.dashboard.services.implement;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import dz.me.dashboard.entities.Rubrique;
import dz.me.dashboard.exceptions.ResourceForbiddenException;
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

    @Override
    public Rubrique save(Rubrique rubrique) {
        return repository.save(rubrique);
    }

    @Override
    public List<Rubrique> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(UUID rubrique) {
        try {

            repository.deleteById(rubrique);
        } catch (Exception e) {
            throw new ResourceForbiddenException(e.getMessage());
        }

    }
}
