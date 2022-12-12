package dz.me.dashboard.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dz.me.dashboard.entities.Privilege;

/**
 *
 * @author Tarek Mekriche
 */
@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, UUID> {

  @Query(value = "delete from privilege", nativeQuery = true)
  public void deleteAll();

  public List<Privilege> findByName(String string);

  @Query(value = "select * from privilege where name in ?1 ", nativeQuery = true)
  public List<Privilege> findAll1(List<Privilege> names);

  public List<Privilege> findAll();
}
