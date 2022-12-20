package dz.me.dashboard.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dz.me.dashboard.entities.Rubrique;
import dz.me.dashboard.entities.RubriqueValue;

/**
 *
 * @author ABDELLATIF ESSELAMI
 */
public interface RubriqueValueRepository extends JpaRepository<RubriqueValue, UUID> {

    // List<RubriqueValue> findByStructure(Structure structure);
    List<RubriqueValue> findByRubrique(Rubrique rubrique);

    @Query(value = "delete from RubriqueValue", nativeQuery = true)
    public void deleteAll();
}