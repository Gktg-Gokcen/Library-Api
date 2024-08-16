package com.docuart.library.security;


import com.docuart.library.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetail implements UserDetails {

    @Autowired
    private User user;

    public UserDetail(User user) {
        super();
        this.user = user;
    }


    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return user.getUserPassword();
    }


    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return user.getUsername();
    }


    public String getUserId() {
        // TODO Auto-generated method stub
        return user.getUserId().toString();
    }


    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }


    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }


    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return null;
    }



}
