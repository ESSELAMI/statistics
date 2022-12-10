package dz.me.dashboard.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dz.me.dashboard.entities.GroupService;
import dz.me.dashboard.entities.Structure;

/**
 *
 * @author Tarek Mekriche
 */
public interface GroupServiceRepository extends JpaRepository<GroupService, UUID> {
    @Query(value = "delete from Group_Service", nativeQuery = true)
    public void deleteAll();

    List<GroupService> findByStructure(Structure structure);
}
