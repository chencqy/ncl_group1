package uk.ac.ncl.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.ac.ncl.rbac.mapper.UserMapper;
import uk.ac.ncl.rbac.common.entity.User;
import uk.ac.ncl.rbac.service.UserService;

import javax.annotation.Resource;

/**
 * The implement of UserService
 */
@Service("UserService")
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserMapper userMapper;

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
}
