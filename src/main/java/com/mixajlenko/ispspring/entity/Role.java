package com.mixajlenko.ispspring.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "t_role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Transient
    @ManyToMany(mappedBy = "role", fetch=FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<User> users;

    public Role() {

    }

    public Role(long id) {
        this.id = id;
    }

    public Role(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
