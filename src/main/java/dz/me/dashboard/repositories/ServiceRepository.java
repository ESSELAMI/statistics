package dz.me.dashboard.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dz.me.dashboard.entities.GroupService;
import dz.me.dashboard.entities.Service;

/**
 *
 * @author Tarek Mekriche
 */
public interface ServiceRepository extends JpaRepository<Service, UUID> {

    // List<Service> findByStructure(Structure structure);

    @Query(value = "delete from Service", nativeQuery = true)
    public void deleteAll();

    List<Service> findByGroupService(GroupService groupService);
}
