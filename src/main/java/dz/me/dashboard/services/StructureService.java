package dz.me.dashboard.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dz.me.dashboard.entities.Structure;
import dz.me.dashboard.entities.Wilaya;

public interface StructureService {

    public Optional<Structure> findById(UUID structureId);

    public List<Structure> findByWilaya(Wilaya wilaya);

    public Structure save(Structure structure);

    public void deleteById(UUID structureId);

    public Object saveAll(List<Structure> structure);

    // public Optional<GroupService> findByStructure(UUID fromString);

}
