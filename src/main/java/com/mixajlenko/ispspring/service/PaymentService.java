package com.mixajlenko.ispspring.service;

import com.mixajlenko.ispspring.entity.Payment;
import com.mixajlenko.ispspring.entity.Role;
import com.mixajlenko.ispspring.entity.Status;
import com.mixajlenko.ispspring.entity.User;
import com.mixajlenko.ispspring.repository.PaymentRepository;
import com.mixajlenko.ispspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    UserRepository userRepository;

    public List<Payment> allPayments() {
        return paymentRepository.findAll();
    }

    @Transactional
    public boolean savePayment(Payment payment) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        payment.setBalance(((User)user).getWallet()+payment.getBill());
        payment.setDate(Date.valueOf(LocalDate.now()));
        payment.setStatus(0);
        payment.setType("Pay");
        payment.setUser((User) user);
        paymentRepository.save(payment);
        ((User) user).setWallet(((User)user).getWallet()+payment.getBill());
        ((User)user).setStatuses(Collections.singleton(new Status(2L,"STATUS_UNBLOCKED")));

        userRepository.save(((User)user));
        return true;
    }

}
