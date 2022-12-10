package dz.me.dashboard.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dz.me.dashboard.entities.Wilaya;

/**
 *
 * @author Tarek Mekriche
 */
public interface WilayaRepository extends JpaRepository<Wilaya, UUID> {
    @Query(value = "delete from Wilaya", nativeQuery = true)
    public void deleteAll();

    @Query(value = "select * from  Wilaya where code_wilaya < 100", nativeQuery = true)
    public List<Wilaya> findAll();

    public Optional<Wilaya> findByCodeWilaya(String idwilaya);
}
