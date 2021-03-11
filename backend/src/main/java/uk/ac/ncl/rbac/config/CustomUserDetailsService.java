package uk.ac.ncl.rbac.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import uk.ac.ncl.rbac.service.UserService;

import java.util.ArrayList;
import java.util.List;


public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	uk.ac.ncl.rbac.common.entity.User user = UserService.getUserByAccount(username);
    	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (user != null) {
        	UserDetails u = User.withUsername(user.getAccount()).password(passwordEncoder.encode(user.getPassword())).authorities(user.getRole()).build();
            return u;
        }
        throw new UsernameNotFoundException("Username not found");
    }
}
