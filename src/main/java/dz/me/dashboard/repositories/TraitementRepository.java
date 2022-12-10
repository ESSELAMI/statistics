package dz.me.dashboard.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dz.me.dashboard.entities.Traitement;

/**
 *
 * @author Tarek Mekriche
 */
public interface TraitementRepository extends JpaRepository<Traitement, UUID> {

    @Query(value = "SELECT * FROM traitement WHERE id IN (SELECT traitement_id from service_traitement WHERE service_id =?)", nativeQuery = true)
    List<Traitement> findByService(String idService);

    @Query(value = "delete from Traitement", nativeQuery = true)
    public void deleteAll();

}
