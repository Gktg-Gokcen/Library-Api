package com.docuart.library.controller;


import com.docuart.library.entity.User;
import com.docuart.library.service.UserServices;
import com.docuart.library.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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

        boolean isMatch = passEncoder.matches(authRequest.getUserPassword(), user.get().getUserPassword());

        if (!isMatch) {
            throw new Exception("Kullanıcı adı veya parola hatalı");
        }

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();
        InetAddress address = InetAddress.getLocalHost();
        String serverIp = address.getHostAddress();

        String jwt = jwtUtil.generateToken(user.get().getUsername(),null);
        user.get().setToken(jwt);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.OK.toString());
        responseBody.put("path", "/auth/login");
        responseBody.put("user", user);
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        numbers.add(5);
        numbers.forEach( (n) -> { System.out.println(n); } );
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @PostMapping("/logout")
    public ResponseEntity<User> logout(){
        return new ResponseEntity<>(userServices.logout(), HttpStatus.OK);
    }

}