package com.mixajlenko.ispspring.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "t_tariff")
public class Tariff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private int price;

    @Transient
    @ManyToOne(fetch=FetchType.EAGER)
    private Svc svc;

    @Transient
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> users;

    public Tariff() {
    }

    public Tariff(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Tariff(String name, String description, int price, Svc svc) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.svc = svc;
    }

    public Tariff(String name, String description, int price, Svc svc, Set<User> users) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.svc = svc;
        this.users = users;
    }

    public Tariff(Long id, String name, String description, int price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Tariff(Long id, String name, String description, int price, Svc svc, Set<User> users) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.svc = svc;
        this.users = users;
    }
}
