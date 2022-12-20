package dz.me.dashboard.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dz.me.dashboard.entities.Service;
import dz.me.dashboard.entities.User;

/**
 *
 * @author ABDELLATIF ESSELAMI
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  @Query(value = "delete from Users", nativeQuery = true)
  public void deleteAll();

  @Query(value = "select * from users where username not like 'Admin%'", nativeQuery = true)
  public List<User> findAll1();

  List<User> findByService(Service service);

}
