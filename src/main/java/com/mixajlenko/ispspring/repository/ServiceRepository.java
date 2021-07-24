package com.mixajlenko.ispspring.repository;

import com.mixajlenko.ispspring.entity.Svc;
import com.mixajlenko.ispspring.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Svc, Long> {


//    @Query("SELECT  tariff.name, tariff.description, tariff.price FROM t_service_tariffs ids  LEFT JOIN t_tariff tariff ON ids.tariffs_id = tariff.id INNER JOIN t_service service ON ids.svc_id = service.id where svc_id = ?1 order by tariff.price")
//    List<TariffsByServiceDto> findTariffsIdOrderByPrice(Long service_id);


}
