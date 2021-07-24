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
@Table(name="t_user_tariffs")
public class UserPlans {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne
    @JoinColumn(name = "tariff_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Tariff tariff;

    @Column(name="next_bill")
    private Date nextBill;

    @Column(name="sub_date")
    private Date subDate;


    private boolean status;


}
