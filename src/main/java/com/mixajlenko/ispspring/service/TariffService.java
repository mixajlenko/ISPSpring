package com.mixajlenko.ispspring.service;

import com.mixajlenko.ispspring.dto.TariffsByServiceDto;
import com.mixajlenko.ispspring.entity.*;
import com.mixajlenko.ispspring.entity.Svc;
import com.mixajlenko.ispspring.repository.ServiceRepository;
import com.mixajlenko.ispspring.repository.TariffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


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

    public List<TariffsByServiceDto> allTariffsBySvcId(Long id){
        List<TariffsByServiceDto> tar = new ArrayList<>();
        for(Tariff t : tariffRepository.findTariffBySvcId(id)){
            TariffsByServiceDto tt = TariffsByServiceDto.builder()
                    .id(t.getId())
                    .name(t.getName())
                    .description(t.getDescription())
                    .price(t.getPrice())
                    .build();
            tar.add(tt);
        }
        return tar;
    }

    public List<TariffsByServiceDto> sort(@RequestParam("service") Long service,  String word) {
        switch (word) {
            case "name":
                return sortByName(service);
//            case "nameR":
//                return sortByAuthor();
//            case "price":
//                return sortPublisher();
            default:
//                return sortPublisherDate();
        }
        return Collections.emptyList();
    }

    private List<TariffsByServiceDto> sortByName(Long servId){
        List<TariffsByServiceDto> tar = new ArrayList<>();
        for(Tariff t : tariffRepository.findTariffBySvcIdOrderByNameDesc(servId)){
            TariffsByServiceDto tt = TariffsByServiceDto.builder()
                    .id(t.getId())
                    .name(t.getName())
                    .description(t.getDescription())
                    .price(t.getPrice())
                    .build();
            tar.add(tt);
        }
        return tar;
    }

}
