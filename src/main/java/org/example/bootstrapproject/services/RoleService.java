package org.example.bootstrapproject.services;


import org.example.bootstrapproject.model.Role;

import java.util.Set;

public interface RoleService {
    Set<Role> getRoles();

    Role findByStringId(String sid);

    Role findById(Long id);

    Role findByName(String name);

    void saveRole(Role role);

}
