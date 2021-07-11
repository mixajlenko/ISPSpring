package com.mixajlenko.ispspring.repository;

import com.mixajlenko.ispspring.entity.Svc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Svc, Long> {

}
