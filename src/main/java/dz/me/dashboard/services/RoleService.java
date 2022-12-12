package dz.me.dashboard.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dz.me.dashboard.entities.Role;

public interface RoleService {
    public Optional<Role> findById(UUID roleId);

    public void deleteById(UUID roleId);

    public Role save(Role role);

    public List<Role> findAll();

    public List<Role> findAll(List<String> names);
}
