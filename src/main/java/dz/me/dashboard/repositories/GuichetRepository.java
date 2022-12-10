package dz.me.dashboard.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dz.me.dashboard.entities.Guichet;
import dz.me.dashboard.entities.Service;

/**
 *
 * @author Tarek Mekriche
 */
public interface GuichetRepository extends JpaRepository<Guichet, UUID> {

    public Optional<Guichet> findByServiceAndGuichet(Service service, int guichet);

    public List<Guichet> findByService(Service service);

    @Query(value = "delete from Guichet", nativeQuery = true)
    public void deleteAll();

}
