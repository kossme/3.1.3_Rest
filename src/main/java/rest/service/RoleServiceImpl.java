package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rest.model.Role;
import rest.repositories.RoleRepository;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<Role> listRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(int id) {
        return roleRepository.getOne(id);
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }
}
