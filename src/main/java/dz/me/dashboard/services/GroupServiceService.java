package dz.me.dashboard.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dz.me.dashboard.entities.GroupService;

public interface GroupServiceService {

    public Optional<GroupService> findById(UUID idGroupService);

    public GroupService save(GroupService groupService);

    public void delete(UUID idGroupService);

    public List<GroupService> findByIdStructure(UUID idStructure);

    public Object saveAll(List<GroupService> groupS);

}
