package com.mixajlenko.ispspring.entity;

import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Data
@Entity
@Table(name = "t_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    @Email
    private String username;

    @Size(min=2, message = "No Less than 2 characters")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Status> statuses;

    @Size(min=2, message = "No Less than 2 characters")
    private String firstName;

    @Size(min=2, message = "No Less than 2 characters")
    private String secondName;

    @Size(min=10, message = "No Less than 10 digits")
    private String phone;

    private int wallet;

    public User() {

    }

    public User(Long id, String username, String password, Set<Role> roles, Set<Status> statuses, String firstName, String secondName, String phone, int wallet) {
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