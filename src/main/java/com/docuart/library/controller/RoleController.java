package com.docuart.library.controller;

import com.docuart.library.service.RoleServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final RoleServices roleServices;

    public RoleController(RoleServices roleServices) {
        this.roleServices = roleServices;
    }

    @GetMapping
    public ResponseEntity<?> getRoles(){
        return new ResponseEntity<>(roleServices.getRoles(), HttpStatus.OK);
    }
}
