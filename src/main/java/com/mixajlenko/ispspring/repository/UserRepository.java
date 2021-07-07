package com.mixajlenko.ispspring.repository;

import com.mixajlenko.ispspring.entity.Status;
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

//    @Modifying
//    @Query("update User set statuses = ?1 where id = ?2")
//    void setUserInfoById(Set<Status> statuses, Long userId);

}
