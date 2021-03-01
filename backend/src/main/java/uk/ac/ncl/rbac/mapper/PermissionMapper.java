package uk.ac.ncl.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import uk.ac.ncl.rbac.common.entity.Permission;

import java.util.List;

/**
 * Permission table (Permission) database access layer
 */
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * Get all permissions of the user by user id
     * @param userId user id
     * @return Permissions the user have
     */
    List<Permission> listPermissionsByUserId(Integer userId);

}
