package com.mixajlenko.ispspring.service;

import com.mixajlenko.ispspring.entity.Role;
import com.mixajlenko.ispspring.entity.User;
import com.mixajlenko.ispspring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    TariffRepository tariffRepository;

    @Autowired
    UserPlansRepository userPlansRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(name);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

//    public List<User> findUsersByStatusTrue() {
//        return userRepository.findAllByStatusIsTrue();
//    }
//
//    public List<User> findUsersByStatusFalse() {
//        return userRepository.findAllByStatusIsFalse();
//    }

    @Transactional
    public boolean manageUserStatus(Long userId, int command) {

        User user = userRepository.getById(userId);

        user.setStatus(command != 1);
        userRepository.save(user);

        return true;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setStatus(false);
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public String passwordEncode(String decodedPass) {
        return bCryptPasswordEncoder.encode(decodedPass);
    }

    public boolean passwordVerify(User user, String pass) {
        return bCryptPasswordEncoder.matches(pass, user.getPassword());
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

}
