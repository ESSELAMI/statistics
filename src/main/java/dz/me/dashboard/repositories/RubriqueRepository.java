package dz.me.dashboard.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dz.me.dashboard.entities.Rubrique;

public interface RubriqueRepository extends JpaRepository<Rubrique, UUID> {

}
