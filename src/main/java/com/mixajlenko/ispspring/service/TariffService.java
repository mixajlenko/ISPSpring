package com.mixajlenko.ispspring.service;

import com.mixajlenko.ispspring.entity.*;
import com.mixajlenko.ispspring.entity.Svc;
import com.mixajlenko.ispspring.repository.ServiceRepository;
import com.mixajlenko.ispspring.repository.TariffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TariffService {

    @Autowired
    TariffRepository tariffRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Transactional
    public boolean manageTariff(Tariff tariffUpdate) {

        Tariff tariff = tariffRepository.getById(tariffUpdate.getId());

        tariff.setDescription(tariffUpdate.getDescription());
        tariff.setName(tariffUpdate.getName());
        tariff.setPrice(tariffUpdate.getPrice());
        tariffRepository.save(tariff);

        return true;

    }

    @Transactional
    public boolean saveTariff(Tariff tariff, Long serviceId) {
        Svc svcFromDb = serviceRepository.getById(serviceId);

        tariff.setSvc(svcFromDb);
        svcFromDb.getTariffs().add(tariff);
        tariffRepository.save(tariff);
        serviceRepository.save(svcFromDb);

        return true;
    }

    public boolean deleteTariff(Long tariffId) {
        if (tariffRepository.findById(tariffId).isPresent()) {
            tariffRepository.deleteById(tariffId);
            return true;
        }
        return false;
    }


}
