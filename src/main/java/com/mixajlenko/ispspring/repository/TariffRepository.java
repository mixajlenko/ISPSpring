package com.mixajlenko.ispspring.repository;

import com.mixajlenko.ispspring.entity.Payment;
import com.mixajlenko.ispspring.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, Long> {

    Tariff findByName(String name);

}
