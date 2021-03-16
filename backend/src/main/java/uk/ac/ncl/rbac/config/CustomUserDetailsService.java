package uk.ac.ncl.rbac.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uk.ac.ncl.rbac.common.entity.Role;
import uk.ac.ncl.rbac.mapper.UserMapper;
import uk.ac.ncl.rbac.service.RoleService;
import uk.ac.ncl.rbac.service.UserService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Resource
    private UserService userService;
    @Resource
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	uk.ac.ncl.rbac.common.entity.User user = userService.getUserByAccount(username);
    	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (user != null) {
        	UserDetails u = null;
            List<Role> roles = roleService.listRolesByUserId(user.getUserId());
        	u = User.withUsername(user.getAccount()).password(passwordEncoder.encode(user.getPassword())).authorities(roles.get(0).getRoleName()).build();
            return u;
        }
        throw new UsernameNotFoundException("Username not found");
    }
}
