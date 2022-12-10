package dz.me.dashboard.services.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

import dz.me.dashboard.entities.GroupService;
import dz.me.dashboard.entities.Guichet;
import dz.me.dashboard.entities.Service;
import dz.me.dashboard.entities.Traitement;
import dz.me.dashboard.exceptions.ResourceForbiddenException;
import dz.me.dashboard.repositories.GuichetRepository;
import dz.me.dashboard.repositories.ServiceRepository;
import dz.me.dashboard.repositories.TraitementRepository;
import dz.me.dashboard.services.ServiceService;

/**
 *
 * @author Tarek Mekriche
 */
@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private GuichetRepository guichetRepository;

    @Autowired
    private TraitementRepository traitementRepository;

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

            List<Guichet> guichets = new ArrayList<Guichet>();
            guichets = guichetRepository.findByService(serviceRepository.findById(serviceId).get());
            Set<Traitement> traitements;
            traitements = serviceRepository.findById(serviceId).get().getTraitements();
            traitementRepository.deleteAll(traitements);
            guichetRepository.deleteAll(guichets);
            serviceRepository.deleteById(serviceId);
        } catch (Exception e) {
            throw new ResourceForbiddenException(e.getMessage());
        }

    }

    @Override
    public List<Service> findByGroupService(GroupService groupService) {
        return serviceRepository.findByGroupService(groupService);
    }

}
