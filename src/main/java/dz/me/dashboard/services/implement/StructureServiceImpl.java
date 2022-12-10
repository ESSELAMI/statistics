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
import dz.me.dashboard.entities.Wilaya;
import dz.me.dashboard.exceptions.ResourceForbiddenException;
import dz.me.dashboard.repositories.GroupServiceRepository;
import dz.me.dashboard.repositories.GuichetRepository;
import dz.me.dashboard.repositories.ServiceRepository;
import dz.me.dashboard.repositories.StructureRepository;
import dz.me.dashboard.repositories.TraitementRepository;
import dz.me.dashboard.services.StructureService;

@Service
public class StructureServiceImpl implements StructureService {
    @Autowired
    StructureRepository structureRepository;

    @Autowired
    private GroupServiceRepository groupServiceRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private GuichetRepository guichetRepository;

    @Autowired
    private TraitementRepository traitementRepository;

    @Override
    public Optional<Structure> findById(UUID structureId) {
        return structureRepository.findById(structureId);
    }

    @Override
    public List<Structure> findByWilaya(Wilaya wilaya) {
        return structureRepository.findByWilaya(wilaya);
    }

    @Override
    public Structure save(Structure structure) {
        return structureRepository.save(structure);
    }

    @Override
    public void deleteById(UUID structureId) {
        try {

            List<Guichet> guichets = new ArrayList<Guichet>();
            List<dz.me.dashboard.entities.Service> services = new ArrayList<dz.me.dashboard.entities.Service>();
            List<GroupService> groupServices = new ArrayList<GroupService>();

            groupServices = groupServiceRepository.findByStructure(structureRepository.findById(structureId).get());
            if (groupServices.size() != 0)
                for (int j = 0; j < groupServices.size(); j++) {
                    services = serviceRepository.findByGroupService(groupServices.get(j));
                    if (services.size() != 0)
                        for (int k = 0; k < services.size(); k++) {
                            guichets = guichetRepository.findByService(services.get(k));
                            Set<Traitement> traitements;
                            traitements = services.get(k).getTraitements();
                            traitementRepository.deleteAll(traitements);

                        }
                }

            guichetRepository.deleteAll(guichets);
            serviceRepository.deleteAll(services);
            groupServiceRepository.deleteAll(groupServices);
            structureRepository.deleteById(structureId);
        } catch (Exception e) {
            throw new ResourceForbiddenException(e.getMessage());
        }
    }

    @Override
    public Object saveAll(List<Structure> structure) {
        // TODO Auto-generated method stub
        return structureRepository.saveAll(structure);
    }

}