package com.docuart.library.security;


import com.docuart.library.entity.Role;
import com.docuart.library.entity.User;
import com.docuart.library.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserServices userServices;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userServices.findByKullaniciAdi(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getUserPassword(),mapRoles(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRoles(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }


}