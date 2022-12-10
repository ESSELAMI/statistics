package dz.me.dashboard.services.implement;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import dz.me.dashboard.entities.Traitement;
import dz.me.dashboard.exceptions.ResourceForbiddenException;
import dz.me.dashboard.repositories.TraitementRepository;
import dz.me.dashboard.services.TraitementService;

/**
 *
 * @author Tarek Mekriche
 */
@Service
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TICKET') or hasRole('ROLE_GUICHET') or hasRole('ROLE_AFFICHAGE' )")
public class TraitementServiceImpl implements TraitementService {

    @Autowired
    private TraitementRepository traitementRepository;

    @Override
    public List<Traitement> findTraitementByService(UUID idService) {

        return traitementRepository.findByService(idService.toString());
    }

    @Override
    public Traitement save(Traitement traitement) {
        return traitementRepository.save(traitement);
    }

    @Override
    public void deleteById(UUID traitement) {
        try {
            traitementRepository.deleteById(traitement);
        } catch (Exception e) {
            throw new ResourceForbiddenException(e.getMessage());
        }

    }

    @Override
    public List<Traitement> findAll() {
        return traitementRepository.findAll();
    }

    @Override
    public Optional<Traitement> findById(UUID traitement) {
        // TODO Auto-generated method stub
        return traitementRepository.findById(traitement);
    }

}
