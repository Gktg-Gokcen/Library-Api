package com.docuart.library.service;
import com.docuart.library.entity.Role;
import com.docuart.library.entity.User;
import com.docuart.library.repository.RoleRepository;
import com.docuart.library.utils.Utils;
import com.docuart.library.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServices implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<User> findAll(){
        return userRepository.findAll(Sort.by(Sort.Direction.DESC, "userId"));
    }

    public User add(User user, Long roleId) {
        user.setUserPassword(bCryptPasswordEncoder.encode(user.getUserPassword()));
        Role userRole = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role bulunamadi ..."));
        user.setRoles(Collections.singletonList(userRole));
        return userRepository.save(user);
    }

    public User update(Long userId,User user){
        User guncellenecekUser = userRepository.findById(userId).orElseThrow(()->new RuntimeException("İd bulunamadı."));
        Utils.copyNonNullProperties(user,guncellenecekUser);
        return userRepository.save(guncellenecekUser);
    }

    public String delete(Long userId){
        User silinecekUser = userRepository.findById(userId).orElseThrow(()->new RuntimeException("İd bulunamadı."));
        userRepository.delete(silinecekUser);
        return "Silme işlemi başarılı.";
    }

    public Optional<User> login(User authRequest){
        Optional<User> user = this.userRepository.findByUsername(authRequest.getUsername());

        if (!user.isPresent()) {
            throw new RuntimeException("Kullanici bulunamadi ...");
        }

        return user;
    }

    public Long getcountusers(){
        return userRepository.countUsers();
    }

    public User logout(){
        System.out.println("dasdas");
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("Kullanıcı adı bulunamadı."));
    }

    public User findByKullaniciAdi(String kullaniciAdi){
        return userRepository.getByUsername(kullaniciAdi);
    }

    public List<Object> isUserPresent(User user){
        boolean userExists = false;
        String message = null;
        Optional<User> existingUsername = userRepository.findByUsername(user.getUsername());
        if(existingUsername.isPresent()){
            userExists = true;
            message = "Username Already Present!";
        }
        System.out.println("existingUserEmail.isPresent() - "+existingUsername.isPresent()+"existingUserMobile.isPresent() - ");
        return Arrays.asList(userExists, message);
    }
}


