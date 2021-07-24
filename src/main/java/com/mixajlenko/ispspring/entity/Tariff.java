package com.mixajlenko.ispspring.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;


@Builder
@AllArgsConstructor
@NoArgsConstructor
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

//    @Transient
//    private int tariffStatus;
//
//    @Transient
//    private Date nextBill;
//
//    @Transient
//    private Date subDate;


    @ManyToOne(fetch=FetchType.EAGER)
    private Svc svc;




//    @Transient
//    @ManyToMany(fetch = FetchType.EAGER)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private Set<User> users;
//
}
