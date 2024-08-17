package com.docuart.library.repository;

import com.docuart.library.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByname(String name);
}
