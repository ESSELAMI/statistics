package dz.me.dashboard.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dz.me.dashboard.entities.Service;
import dz.me.dashboard.entities.User;

public interface UserService {

    public Optional<User> findByUsername(String username);

    public Optional<User> findById(UUID idUser);

    public User save(User user);

    public User savePassword(User user);

    public void delete(UUID idUser);

    public List<User> findByService(Service service);

    public List<User> findAll();

}
