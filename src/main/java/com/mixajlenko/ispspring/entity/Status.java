package com.mixajlenko.ispspring.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "t_status")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Transient
    @ManyToMany(mappedBy = "status", fetch=FetchType.EAGER)
    private List<User> users;

    public Status() {

    }

    public Status(long id) {
        this.id = id;
    }

    public Status(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
