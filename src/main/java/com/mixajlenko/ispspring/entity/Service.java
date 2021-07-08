package com.mixajlenko.ispspring.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "t_service")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToMany(fetch=FetchType.EAGER)
    private List<Tariff> tariffs;

    public Service() {
    }

    public Service(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Service(Long id, String name, String description, List<Tariff> tariffs) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tariffs = tariffs;
    }
}
