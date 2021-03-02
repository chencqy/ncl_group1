package uk.ac.ncl.rbac.service;

import uk.ac.ncl.rbac.common.entity.Role;

import java.util.List;

/**
 * RoleService interface
 */
public interface RoleService {

    /**
     * Get all roles of the user by user id
     * @param userId user id
     * @return Roles the user have
     */
    List<Role> listRolesByUserId(Integer userId);
}
