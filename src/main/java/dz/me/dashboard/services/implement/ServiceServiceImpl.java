package dz.me.dashboard.services.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

import dz.me.dashboard.entities.Service;

import dz.me.dashboard.exceptions.ResourceForbiddenException;

import dz.me.dashboard.repositories.ServiceRepository;

import dz.me.dashboard.services.ServiceService;

/**
 *
 * @author Tarek Mekriche
 */
@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public Optional<Service> findById(UUID idService) {
        return serviceRepository.findById(idService);
    }

    /*
     * @Override
     * public List<Service> findByStructure(Structure structure) {
     * return serviceRepository.findByStructure(structure);
     * }
     */
    @Override
    public Service save(Service service) {
        return serviceRepository.save(service);
    }

    @Override
    public void deleteById(UUID serviceId) {
        try {

            serviceRepository.deleteById(serviceId);
        } catch (Exception e) {
            throw new ResourceForbiddenException(e.getMessage());
        }

    }

    @Override
    public List<Service> findAll() {
        return serviceRepository.findAll();
    }

}
