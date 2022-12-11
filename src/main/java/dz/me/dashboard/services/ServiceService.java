package dz.me.dashboard.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dz.me.dashboard.entities.Service;

public interface ServiceService {

    Optional<Service> findById(UUID idService);

    // List<Service> findByStructure(Structure structure);

    Service save(Service service);

    void deleteById(UUID serviceId);

    List<Service> findAll();

}
