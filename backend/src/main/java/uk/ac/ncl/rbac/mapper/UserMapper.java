package uk.ac.ncl.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import uk.ac.ncl.rbac.common.entity.User;

/**
 * User table (User) database access layer
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * Get all users by page
     * @param page
     * @return users
     */
    IPage<User> listUserPage(Page<?> page);

    /**
     * Insert a user into database
     * @param user
     */
    void insertUser(User user);

    /**
     * Insert the role of user
     */
    void insertUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);
}
