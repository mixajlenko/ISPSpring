package com.mixajlenko.ispspring.repository;

import com.mixajlenko.ispspring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
