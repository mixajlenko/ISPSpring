package com.mixajlenko.ispspring.repository;

import com.mixajlenko.ispspring.entity.Status;
import com.mixajlenko.ispspring.entity.Tariff;
import com.mixajlenko.ispspring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    List<User> findAllByStatusesId(Long id);

}
