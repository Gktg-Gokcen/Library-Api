package com.docuart.library.controller;


import com.docuart.library.entity.User;
import com.docuart.library.repository.UserRepository;
import com.docuart.library.service.UserServices;
import com.docuart.library.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserServices userServices;

    private final PasswordEncoder passEncoder = new BCryptPasswordEncoder();


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User authRequest) throws Exception {
        Optional<User> user = this.userServices.login(authRequest);

        String jwt = jwtUtil.generateToken(user.get().getUsername(),null);

        boolean isMatch = passEncoder.matches(authRequest.getUserPassword(), user.get().getUserPassword());

        if (!isMatch) {
            throw new Exception("Kullanıcı adı veya parola hatalı");
        }

        user.get().setToken(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}