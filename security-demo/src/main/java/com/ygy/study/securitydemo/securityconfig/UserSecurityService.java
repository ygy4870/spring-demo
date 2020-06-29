package com.ygy.study.securitydemo.securityconfig;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("zhangsan".equals(username)) {
            return UserSecurityDetails.create("zhangsan", "123456");
        }
        return UserSecurityDetails.create("lisi", "123456");
    }

}
