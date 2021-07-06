package com.mixajlenko.ispspring.repository;

import com.mixajlenko.ispspring.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
