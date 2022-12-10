package dz.me.dashboard.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dz.me.dashboard.entities.Guichet;
import dz.me.dashboard.entities.Service;

public interface GuichetService {

    public Optional<Guichet> findGuichetById(UUID idGuichet);

    public Optional<Guichet> findByServiceAndGuichet(Service service, int guichet);

    public List<Guichet> findAllByService(Service service);

    public Guichet save(Guichet guichet);

    public void deleteByid(UUID idGuichet);

}
