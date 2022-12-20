package dz.me.dashboard.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dz.me.dashboard.entities.Role;

/**
 *
 * @author ABDELLATIF ESSELAMI
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

  @Query(value = "delete from Roles", nativeQuery = true)
  public void deleteAll();

  public List<Role> findByName(String string);

  @Query(value = "select  * from roles where name in ?1 ", nativeQuery = true)
  public List<Role> findAll(List<String> names);

}
