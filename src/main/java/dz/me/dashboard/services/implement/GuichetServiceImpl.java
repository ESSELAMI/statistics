package dz.me.dashboard.services.implement;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dz.me.dashboard.entities.Guichet;
import dz.me.dashboard.exceptions.ResourceForbiddenException;
import dz.me.dashboard.repositories.GuichetRepository;
import dz.me.dashboard.services.GuichetService;

/**
 *
 * @author Tarek Mekriche
 */
@Service
public class GuichetServiceImpl implements GuichetService {
    /* */
    @Autowired
    private GuichetRepository guichetRepository;

    @Override
    public Optional<Guichet> findGuichetById(UUID idGuichet) {
        return guichetRepository.findById(idGuichet);
    }

    @Override
    public Optional<Guichet> findByServiceAndGuichet(dz.me.dashboard.entities.Service service, int guichet) {
        return guichetRepository.findByServiceAndGuichet(service, guichet);
    }

    @Override
    public List<Guichet> findAllByService(dz.me.dashboard.entities.Service service) {
        return guichetRepository.findByService(service);
    }

    @Override
    public Guichet save(Guichet guichet) {
        return guichetRepository.save(guichet);
    }

    @Override
    public void deleteByid(UUID idGuichet) {

        try {
            guichetRepository.deleteById(idGuichet);
        } catch (Exception e) {
            throw new ResourceForbiddenException(e.getMessage());
        }

    }

}
