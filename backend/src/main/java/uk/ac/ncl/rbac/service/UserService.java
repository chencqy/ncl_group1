package uk.ac.ncl.rbac.service;

import uk.ac.ncl.rbac.common.entity.User;

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

}
