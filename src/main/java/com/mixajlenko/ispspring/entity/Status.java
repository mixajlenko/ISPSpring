package com.mixajlenko.ispspring.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "t_status")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private long id;
    @NonNull
    private String name;

    @Transient
    @ManyToMany(mappedBy = "status")
    @NonNull
    private Set<User> users;

    public Status() {

    }

    public Status(@NonNull long id) {
        this.id = id;
    }

    public Status(@NonNull long id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }
}
