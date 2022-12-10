package dz.me.dashboard.services.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dz.me.dashboard.entities.GroupService;
import dz.me.dashboard.entities.Guichet;
import dz.me.dashboard.entities.Structure;
import dz.me.dashboard.entities.Traitement;
import dz.me.dashboard.exceptions.ResourceForbiddenException;
import dz.me.dashboard.repositories.GroupServiceRepository;
import dz.me.dashboard.repositories.GuichetRepository;
import dz.me.dashboard.repositories.ServiceRepository;
import dz.me.dashboard.repositories.TraitementRepository;
import dz.me.dashboard.services.GroupServiceService;
import dz.me.dashboard.services.ServiceService;
import dz.me.dashboard.services.StructureService;

/**
 *
 * @author Tarek Mekriche
 */
@Service
public class GroupServiceImpl implements GroupServiceService {
    @Autowired
    private GroupServiceRepository groupServiceRepository;
    @Autowired
    StructureService structureService;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private GuichetRepository guichetRepository;

    @Autowired
    ServiceService serviceService;

    @Autowired
    private TraitementRepository traitementRepository;

    @Override
    public Optional<GroupService> findById(UUID idGroupService) {
        return groupServiceRepository.findById(idGroupService);
    }

    @Override
    public GroupService save(GroupService groupService) {
        return groupServiceRepository.save(groupService);
    }

    @Override
    public void delete(UUID idGroupService) {
        try {

            List<Guichet> guichets = new ArrayList<Guichet>();
            List<dz.me.dashboard.entities.Service> services = new ArrayList<dz.me.dashboard.entities.Service>();

            services = serviceRepository.findByGroupService(groupServiceRepository.findById(idGroupService).get());
            if (services.size() != 0)
                for (int k = 0; k < services.size(); k++) {
                    guichets = guichetRepository.findByService(services.get(k));
                    Set<Traitement> traitements;
                    traitements = services.get(k).getTraitements();
                    traitementRepository.deleteAll(traitements);
                }

            guichetRepository.deleteAll(guichets);
            serviceRepository.deleteAll(services);
            groupServiceRepository.deleteById(idGroupService);

        } catch (Exception e) {
            throw new ResourceForbiddenException(e.getMessage());
        }
    }

    @Override
    public List<GroupService> findByIdStructure(UUID idStructure) {
        Optional<Structure> structure = structureService.findById(idStructure);

        List<GroupService> groupServices = new ArrayList<>();
        groupServices = groupServiceRepository.findByStructure(structure.get());
        return groupServices;
    }

    @Override
    public Object saveAll(List<GroupService> groupS) {
        // TODO Auto-generated method stub
        return groupServiceRepository.saveAll(groupS);
    }

}
