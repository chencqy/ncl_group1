package uk.ac.ncl.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import uk.ac.ncl.rbac.common.entity.Role;

import java.util.List;

/**
 * Role table (Role) database access layer
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * Get all roles of the user by user id
     * @param userId user id
     * @return Roles the user have
     */
    List<Role> listRolesByUserId(Integer userId);
}
