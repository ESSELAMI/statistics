package dz.me.dashboard.services.implement;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import dz.me.dashboard.entities.Rubrique;
import dz.me.dashboard.entities.RubriqueValue;
import dz.me.dashboard.exceptions.ResourceForbiddenException;
import dz.me.dashboard.repositories.RubriqueRepository;
import dz.me.dashboard.repositories.RubriqueValueRepository;
import dz.me.dashboard.services.RubriqueService;
import dz.me.dashboard.services.RubriqueValueService;

@Service
public class RubriqueValueServiceImpl implements RubriqueValueService {

    @Autowired
    RubriqueValueRepository repository;
    @Autowired
    RubriqueService rubriqueService;

    @Override
    public Optional<RubriqueValue> findById(UUID uuid) {

        return repository.findById(uuid);
    }

    @Override
    public RubriqueValue save(RubriqueValue rubriqueValue) {
        return repository.save(rubriqueValue);
    }

    @Override
    public List<RubriqueValue> findAll() {
        return repository.findAll();
    }

    // @Override
    // public List<RubriqueValue> findByRubriqueId(Rubrique rubrique) {

    // return repository.findByRubriqueId(rubrique);
    // }

    @Override
    public List<RubriqueValue> findByRubriqueId(UUID rubriqueId) {
        Optional<Rubrique> rubrique = rubriqueService.findById(rubriqueId);

        List<RubriqueValue> rubriqueValues = new ArrayList<>();
        rubriqueValues = repository.findByRubrique(rubrique.get());
        return rubriqueValues;
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
