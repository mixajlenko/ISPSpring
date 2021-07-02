package com.mixajlenko.ispspring.repository;

import com.mixajlenko.ispspring.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {
}
