package com.mixajlenko.ispspring.entity;

import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.Set;

@Data
@Entity
@Table(name = "t_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    @Pattern(regexp = "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;" +
            ":\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\." +
            "[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$", message = "{lang.emailError}")
    @NotBlank(message = "Email is mandatory")
    private String username;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", message = "{lang.passwordError}")
    @NotBlank(message = "Password is mandatory")
    private String password;


    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;


    @ManyToMany(fetch = FetchType.EAGER)
    private List<Status> statuses;


    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Tariff> tariffs;

    @NotBlank(message = "First name is mandatory")
    @Size(min = 2, message = "{lang.firstNameError}")
    private String firstName;

    @NotBlank(message = "Second name is mandatory")
    @Size(min = 2, message = "{lang.secondNameError}")
    private String secondName;

    @Size(min = 10, message = "{lang.phoneError}")
    private String phone;

    private int wallet;

    public User() {

    }

    public User(Long id, String username, String password, Set<Role> roles, List<Status> statuses, Set<Tariff> tariffs, String firstName, String secondName, String phone, int wallet) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.statuses = statuses;
        this.tariffs = tariffs;
        this.firstName = firstName;
        this.secondName = secondName;
        this.phone = phone;
        this.wallet = wallet;
    }

    public User(Long id, String username, String password, Set<Role> roles, List<Status> statuses, String firstName, String secondName, String phone, int wallet) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.statuses = statuses;
        this.firstName = firstName;
        this.secondName = secondName;
        this.phone = phone;
        this.wallet = wallet;
    }

    public void setStatuses(Status status) {
        this.statuses = new ArrayList<>();
        this.statuses.add(status);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

}