package com.mixajlenko.ispspring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "t_payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    private int bill;

    private int status;

    private int balance;

    private String type;

    private Date date;


    public Payment(long id) {
        this.id = id;
    }

    public Payment(long id, User user) {
        this.id = id;
        this.user = user;
    }

    public Payment(long id, User user, int bill, int status, int balance, String type, Date date) {
        this.id = id;
        this.user = user;
        this.bill = bill;
        this.status = status;
        this.balance = balance;
        this.type = type;
        this.date = date;
    }

    public Payment() {
    }
}
