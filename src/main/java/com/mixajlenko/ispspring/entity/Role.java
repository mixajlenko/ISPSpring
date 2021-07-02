package com.mixajlenko.ispspring.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "t_role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private long id;
    @NonNull
    private String name;

    @Transient
    @ManyToMany(mappedBy = "role")
    @NonNull
    private Set<User> users;

    public Role() {

    }

    public Role(@NonNull long id) {
        this.id = id;
    }

    public Role(@NonNull long id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
