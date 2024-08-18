package com.docuart.library.service;

import com.docuart.library.entity.Role;
import com.docuart.library.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServices {

    private final RoleRepository roleRepository;


    public RoleServices(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getRoles(){
        return roleRepository.findAll();
    }
}
