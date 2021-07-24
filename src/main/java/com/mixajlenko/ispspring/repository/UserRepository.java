package com.mixajlenko.ispspring.repository;

import com.mixajlenko.ispspring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

//    List<User> findAllByStatusIsTrue();
//
//    List<User> findAllByStatusIsFalse();

}
