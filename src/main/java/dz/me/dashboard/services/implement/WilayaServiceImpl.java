package dz.me.dashboard.services.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import dz.me.dashboard.entities.GroupService;
import dz.me.dashboard.entities.Guichet;
import dz.me.dashboard.entities.Service;
import dz.me.dashboard.entities.Structure;
import dz.me.dashboard.entities.Traitement;
import dz.me.dashboard.entities.Wilaya;
import dz.me.dashboard.exceptions.ResourceForbiddenException;
import dz.me.dashboard.repositories.GroupServiceRepository;
import dz.me.dashboard.repositories.GuichetRepository;
import dz.me.dashboard.repositories.ServiceRepository;
import dz.me.dashboard.repositories.StructureRepository;
import dz.me.dashboard.repositories.TraitementRepository;
import dz.me.dashboard.repositories.WilayaRepository;
import dz.me.dashboard.services.WilayaSerivce;

/**
 *
 * @author Tarek Mekriche
 */
@org.springframework.stereotype.Service
public class WilayaServiceImpl implements WilayaSerivce {

    @Autowired
    private WilayaRepository wilayaRepository;

    @Autowired
    private StructureRepository structureRepository;

    @Autowired
    private GroupServiceRepository groupServiceRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private GuichetRepository guichetRepository;

    @Autowired
    private TraitementRepository traitementRepository;

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TICKET') or hasRole('ROLE_GUICHET') or hasRole('ROLE_AFFICHAGE' )")
    public List<Wilaya> all() {
        return wilayaRepository.findAll();
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TICKET') or hasRole('ROLE_GUICHET') or hasRole('ROLE_AFFICHAGE' )")
    public Optional<Wilaya> findById(UUID id) {
        return wilayaRepository.findById(id);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TICKET') or hasRole('ROLE_GUICHET') or hasRole('ROLE_AFFICHAGE' )")
    public Wilaya save(Wilaya wilaya) {

        return wilayaRepository.save(wilaya);
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_GUICHET','ROLE_AFFICHAGE')")
    // "hasRole('ROLE_ADMIN') or hasRole('ROLE_TICKET') or hasRole('ROLE_GUICHET')
    // or hasRole('ROLE_AFFICHAGE' )")
    public void deleteById(UUID wilayaId) {
        try {

            List<Guichet> guichets = new ArrayList<Guichet>();
            List<Service> services = new ArrayList<Service>();
            List<GroupService> groupServices = new ArrayList<GroupService>();
            List<Structure> strutures = new ArrayList<Structure>();
            Optional<Wilaya> wilaya = wilayaRepository.findById(wilayaId);
            strutures = structureRepository.findByWilaya(wilaya.get());
            if (strutures.size() != 0)
                for (int i = 0; i < strutures.size(); i++) {
                    groupServices = groupServiceRepository.findByStructure(strutures.get(i));
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
                }

            guichetRepository.deleteAll(guichets);
            serviceRepository.deleteAll(services);
            groupServiceRepository.deleteAll(groupServices);
            structureRepository.deleteAll(strutures);
            wilayaRepository.deleteById(wilayaId);
        } catch (Exception e) {
            throw new ResourceForbiddenException(e.getMessage());
        }

    }

    @Override
    public Object saveAll(List<Wilaya> wilaya) {
        return wilayaRepository.saveAll(wilaya);
    }

    @Override
    public Optional<Wilaya> findByCodeWilaya(String Idwilaya) {
        return wilayaRepository.findByCodeWilaya(Idwilaya);
    }

}
