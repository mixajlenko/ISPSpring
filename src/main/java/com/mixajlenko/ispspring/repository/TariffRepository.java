package com.mixajlenko.ispspring.repository;

import com.mixajlenko.ispspring.dto.TariffsByServiceDto;
import com.mixajlenko.ispspring.entity.Payment;
import com.mixajlenko.ispspring.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, Long> {

    Tariff findByName(String name);


    List<Tariff> findTariffBySvcId(Long id);


    List<Tariff> findTariffBySvcIdOrderByNameDesc(Long id);

    List<Tariff> findTariffBySvcIdOrderByNameAsc(Long id);

    List<Tariff> findTariffBySvcIdOrderByPrice(Long id);



//    List<Tariff> findAllByUsersId(Long id);



}
