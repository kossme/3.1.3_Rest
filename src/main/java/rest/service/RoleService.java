package rest.service;


import rest.model.Role;

import java.util.List;

public interface RoleService {

    public List<Role> listRoles();

    Role getRoleById(int id);

    Role getRoleByName(String name);

    void save(Role role);
}
