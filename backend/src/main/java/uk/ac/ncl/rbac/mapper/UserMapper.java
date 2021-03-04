package uk.ac.ncl.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
}
