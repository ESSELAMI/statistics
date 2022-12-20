package dz.me.dashboard.services.implement;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dz.me.dashboard.entities.Role;
import dz.me.dashboard.exceptions.ResourceForbiddenException;
import dz.me.dashboard.repositories.RoleRepository;
import dz.me.dashboard.services.RoleService;

/**
 *
 * @author ABDELLATIF ESSELAMI
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Optional<Role> findById(UUID roleId) {
        return roleRepository.findById(roleId);
    }

    @Override
    public void deleteById(UUID roleId) {
        try {
            roleRepository.deleteById(roleId);
        } catch (Exception e) {
            throw new ResourceForbiddenException(e.getMessage());
        }
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> findAll(List<String> names) {
        roleRepository.findAll(names);
        return null;
    }

}
