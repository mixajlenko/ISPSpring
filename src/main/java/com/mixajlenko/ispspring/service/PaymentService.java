package com.mixajlenko.ispspring.service;

import com.mixajlenko.ispspring.entity.Payment;
import com.mixajlenko.ispspring.entity.User;
import com.mixajlenko.ispspring.entity.UserPlans;
import com.mixajlenko.ispspring.repository.PaymentRepository;
import com.mixajlenko.ispspring.repository.UserPlansRepository;
import com.mixajlenko.ispspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    UserPlansRepository userPlansRepository;

    @Autowired
    UserRepository userRepository;

    public List<Payment> allPayments() {
        return paymentRepository.findAll();
    }

    public List<Payment> allPaymentsByUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return paymentRepository.findAllByUserId(user.getId());
    }


    @Transactional
    public boolean savePayment(Payment payment) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        payment.setBalance(((User)user).getWallet()+payment.getBill());
        payment.setDate(Date.valueOf(LocalDate.now()));
        payment.setStatus(0);
        payment.setType("refund");
        payment.setUser((User) user);
        paymentRepository.save(payment);
        ((User) user).setWallet(((User)user).getWallet()+payment.getBill());
        if(!((User)user).isStatus()){
            ((User) user).setStatus(true);
        }
        userRepository.save(((User)user));
        return true;
    }

    @Transactional
    public boolean payService(Long service, Integer price) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Payment payment = new Payment();
        if(((User)user).getWallet()-price>=0){
            payment.setBalance(((User)user).getWallet()-price);
            payment.setDate(Date.valueOf(LocalDate.now()));
            payment.setStatus(0);
            payment.setType("payService");
            payment.setUser((User) user);
            paymentRepository.save(payment);
            ((User) user).setWallet(((User)user).getWallet()-price);
            UserPlans up = userPlansRepository.findByUserIdAndTariffId(((User) user).getId(),service);
            up.setStatus(true);
            up.setNextBill(Date.valueOf(LocalDate.now().plusDays(30)));
            userPlansRepository.save(up);
        } else {
            payment.setBill(price);
            payment.setDate(Date.valueOf(LocalDate.now()));
            payment.setStatus(1);
            payment.setType("payService");
            payment.setUser((User) user);
            paymentRepository.save(payment);
        }
        userRepository.save(((User)user));
        return true;
    }

}
