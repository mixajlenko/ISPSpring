package com.mixajlenko.ispspring.service;

import com.mixajlenko.ispspring.dto.TariffInfoForClientDTO;
import com.mixajlenko.ispspring.entity.UserPlans;
import com.mixajlenko.ispspring.repository.TariffRepository;
import com.mixajlenko.ispspring.repository.UserPlansRepository;
import com.mixajlenko.ispspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserPlansService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TariffRepository tariffRepository;

    @Autowired
    UserPlansRepository userPlansRepository;


    @Transactional
    public boolean addUserTariff(Long userId, Long tariffId) {

        UserPlans newPlan = new UserPlans();
        UserPlans userPlanFromDb = userPlansRepository.findByUserIdAndTariffId(userId, tariffId);

        if (userPlanFromDb != null) {
            return false;
        }


        newPlan.setUser(userRepository.getById(userId));
        newPlan.setTariff(tariffRepository.getById(tariffId));
        newPlan.setStatus(false);
        newPlan.setSubDate(Date.valueOf(LocalDate.now()));
        userPlansRepository.save(newPlan);
        return true;
    }

    public List<TariffInfoForClientDTO> tariffsByUser(Long id) {
        List<TariffInfoForClientDTO> tar = new ArrayList<>();

        for (UserPlans u : userPlansRepository.findAllByUserId(id)) {
            TariffInfoForClientDTO tt = TariffInfoForClientDTO.builder()
                    .name(u.getTariff().getName())
                    .tariffStatus(u.isStatus())
                    .nextBill(u.getNextBill())
                    .subDate(u.getSubDate())
                    .build();
            tar.add(tt);
        }
        return tar;
    }


}
