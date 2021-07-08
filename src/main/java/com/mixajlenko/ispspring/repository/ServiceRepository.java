package com.mixajlenko.ispspring.repository;

import com.mixajlenko.ispspring.entity.Payment;
import com.mixajlenko.ispspring.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
}
