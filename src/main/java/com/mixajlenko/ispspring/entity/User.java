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
@Builder
@Table(name = "t_user")
@NoArgsConstructor
@RequiredArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Long id;

    @NonNull
    @Column(name = "email")
    @Email
    private String username;

    @NonNull
    @Size(min=2, message = "No Less than 2 characters")
    private String password;

//    @NonNull
//    @Transient
//    private String passwordConfirm;

    @NonNull
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @NonNull
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Status> statuses;

    @NonNull
    @Size(min=2, message = "No Less than 2 characters")
    private String firstName;

    @NonNull
    @Size(min=2, message = "No Less than 2 characters")
    private String secondName;

    @NonNull
    @Size(min=10, message = "No Less than 10 digits")
    private String phone;

    @NonNull
    private String wallet;


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