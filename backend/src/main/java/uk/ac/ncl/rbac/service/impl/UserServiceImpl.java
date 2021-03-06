package uk.ac.ncl.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.ac.ncl.rbac.common.entity.Permission;
import uk.ac.ncl.rbac.common.entity.Role;
import uk.ac.ncl.rbac.common.entity.User;
import uk.ac.ncl.rbac.common.entity.vo.UserVo;
import uk.ac.ncl.rbac.mapper.UserMapper;
import uk.ac.ncl.rbac.service.PermissionService;
import uk.ac.ncl.rbac.service.RoleService;
import uk.ac.ncl.rbac.service.UserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * The implement of UserService
 */
@Service("UserService")
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

    /**
     * Get user by account
     * @param account account of user
     * @return User
     * @throws IllegalArgumentException if account is null or can not find the user
     */
    @Override
    public User getUserByAccount(String account) {
        if (account == null || account.equals("")) {
            logger.warn("Account is null");
            throw new IllegalArgumentException("Account is null");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            logger.warn("This user could not be found");
            throw new IllegalArgumentException("This user could not be found");
        }
        return user;
    }

    /**
     * Get all users by page
     * @param page
     * @return users
     */
    @Override
    public IPage<User> listUserPage(Page<?> page) {
        return userMapper.listUserPage(page);
    }

    /**
     * Select all info of the user by account, includes roles and permissions
     * @param account
     * @return UserVo has both roles and permissions
     */
//    @Override
//    public UserVo getUserInfo(String account) {
//        User user = getUserByAccount(account);
//        List<Role> roles = roleService.listRolesByUserId(user.getUserId());
//        List<Permission> permissions = permissionService.listPermissionsByUserId(user.getUserId());
//
//        UserVo userVo = new UserVo(user, roles, permissions);
//
//        return userVo;
//    }
}
