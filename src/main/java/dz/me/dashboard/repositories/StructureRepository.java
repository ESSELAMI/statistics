package dz.me.dashboard.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dz.me.dashboard.entities.Structure;
import dz.me.dashboard.entities.Wilaya;

/**
 *
 * @author Tarek Mekriche
 */
public interface StructureRepository extends JpaRepository<Structure, UUID> {
    @Query(value = "delete from Structure", nativeQuery = true)
    public void deleteAll();

    public List<Structure> findByWilaya(Wilaya wilaya);

    public Optional<Structure> findByStructure(String structureId);
}
