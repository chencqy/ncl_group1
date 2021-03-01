package uk.ac.ncl.rbac.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.ac.ncl.rbac.mapper.PermissionMapper;
import uk.ac.ncl.rbac.common.entity.Permission;
import uk.ac.ncl.rbac.service.PermissionService;

import javax.annotation.Resource;
import java.util.List;

/**
 * The implement of PermissionService
 */
@Service("PermissionService")
public class PermissionServiceImpl implements PermissionService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private PermissionMapper permissionMapper;

    /**
     * Get all permissions of the user by user id
     * @param userId user id
     * @return Permissions the user have
     * @throws IllegalArgumentException if userId is invalid
     */
    @Override
    public List<Permission> listPermissionsByUserId(Integer userId) {
        if (userId == null || userId < 0) {
            logger.warn("Invalid user id");
            throw new IllegalArgumentException("Invalid user id");
        }
        List<Permission> permissions = permissionMapper.listPermissionsByUserId(userId);
        return permissions;
    }
}
