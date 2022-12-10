package dz.me.dashboard.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dz.me.dashboard.entities.Role;

/**
 *
 * @author Tarek Mekriche
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

  @Query(value = "delete from Roles", nativeQuery = true)
  public void deleteAll();

  public Optional<Role> findByName(String string);
}
