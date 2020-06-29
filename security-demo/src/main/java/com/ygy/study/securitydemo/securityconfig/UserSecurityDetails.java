package com.ygy.study.securitydemo.securityconfig;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

//@Component
public class UserSecurityDetails implements UserDetails {

    private String name;
    private String pwd;

    public UserSecurityDetails(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    public static UserSecurityDetails create(String name, String pwd) {
        return new UserSecurityDetails(name, pwd);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return pwd;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "UserSecurityDetails{" +
                "name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
