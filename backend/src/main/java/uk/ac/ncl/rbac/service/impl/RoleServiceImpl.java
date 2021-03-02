package uk.ac.ncl.rbac.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.ac.ncl.rbac.common.entity.Role;
import uk.ac.ncl.rbac.mapper.RoleMapper;
import uk.ac.ncl.rbac.service.RoleService;

import javax.annotation.Resource;
import java.util.List;

/**
 * The implement of RoleService
 */
@Service("RoleService")
public class RoleServiceImpl implements RoleService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RoleMapper roleMapper;

    /**
     * Get all roles of the user by user id
     * @param userId user id
     * @return Roles the user have
     * @throws IllegalArgumentException if userId is invalid
     */
    @Override
    public List<Role> listRolesByUserId(Integer userId) {
        if (userId == null || userId < 0) {
            logger.warn("Invalid user id");
            throw new IllegalArgumentException("Invalid user id");
        }
        List<Role> roles = roleMapper.listRolesByUserId(userId);
        return roles;
    }
}
