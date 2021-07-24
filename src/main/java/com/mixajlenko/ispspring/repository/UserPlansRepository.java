package com.mixajlenko.ispspring.repository;

import com.mixajlenko.ispspring.entity.Tariff;
import com.mixajlenko.ispspring.entity.UserPlans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPlansRepository extends JpaRepository<UserPlans, Long> {

    UserPlans findByUserIdAndTariffId(Long userId, long tariffId);

    List<UserPlans> findAllByUserId(Long userId);

}
