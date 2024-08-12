package com.docuart.library.service;
import com.docuart.library.entity.User;
import com.docuart.library.utils.Utils;
import com.docuart.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {

    @Autowired
    private  PasswordEncoder passEncoder ;

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll(Sort.by(Sort.Direction.DESC, "userId"));
    }

    public User add(User user){
        user.setUserPassword(passEncoder.encode(user.getUserPassword()));
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
        return null;
    }

}


