package uk.ac.ncl.rbac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import uk.ac.ncl.rbac.common.entity.User;
import uk.ac.ncl.rbac.common.entity.vo.UserVo;

/**
 * UserService interface
 */
public interface UserService {

    /**
     * Get user by account
     * @param account account of user
     * @return User
     */
    User getUserByAccount(String account);

    /**
     * Get all users by page
     * @param page
     * @return users
     */
    IPage<User> listUserPage(Page<?> page);

    /**
     * Select all info of the user by account, includes roles and permissions
     * @param account
     * @return UserVo has both roles and permissions
     */
    //UserVo getUserInfo(String account);

}
