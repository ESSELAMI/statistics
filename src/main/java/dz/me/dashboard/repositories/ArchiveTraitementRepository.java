package dz.me.dashboard.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dz.me.dashboard.entities.ArchiveTraitement;

/**
 *
 * @author Tarek Mekriche
 */
public interface ArchiveTraitementRepository extends JpaRepository<ArchiveTraitement, UUID> {
    @Query(value = "delete from Archive_Traitement", nativeQuery = true)
    public void deleteAll();
}
