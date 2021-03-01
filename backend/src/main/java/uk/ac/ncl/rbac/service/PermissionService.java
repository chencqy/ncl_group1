package uk.ac.ncl.rbac.service;

import uk.ac.ncl.rbac.common.entity.Permission;

import java.util.List;

/**
 * PermissionService interface
 */
public interface PermissionService {

    /**
     * Get all permissions of the user by user id
     * @param userId user id
     * @return Permissions the user have
     */
    List<Permission> listPermissionsByUserId(Integer userId);

}
