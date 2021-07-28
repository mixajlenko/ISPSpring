package com.mixajlenko.ispspring.service;

import com.mixajlenko.ispspring.dto.TariffInfoForClientDTO;
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

    public TariffInfoForClientDTO findTariffById (Long tariffId){
        TariffInfoForClientDTO tariffDto = new TariffInfoForClientDTO();
        Tariff tariff = tariffRepository.getById(tariffId);

        tariffDto.setName(tariff.getName());
        tariffDto.setPrice(tariff.getPrice());
        tariffDto.setDescription(tariff.getDescription());

        return tariffDto;
    }

    public List<TariffsByServiceDto> allTariffs(){
        List<TariffsByServiceDto> tar = new ArrayList<>();
        for(Tariff t : tariffRepository.findAll()){
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
            case "nameR":
                return sortByNameR(service);
            case "price":
                return sortByPrice(service);
            default:
                return sortByName(service);
        }
    }

    private List<TariffsByServiceDto> sortByName(Long servId){
        List<TariffsByServiceDto> tar = new ArrayList<>();
        for(Tariff t : tariffRepository.findTariffBySvcIdOrderByNameAsc(servId)){
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

    private List<TariffsByServiceDto> sortByNameR(Long servId){
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

    private List<TariffsByServiceDto> sortByPrice(Long servId){
        List<TariffsByServiceDto> tar = new ArrayList<>();
        for(Tariff t : tariffRepository.findTariffBySvcIdOrderByPrice(servId)){
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
