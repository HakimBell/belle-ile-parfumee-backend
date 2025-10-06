package com.belleileperfumee.belle_ile_parfumee.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "clients")
@Data
public class Client {

    @Id
    @Column(length = 255)
    private String email;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "email_1", nullable = false, unique = true)
    private Account account;
}
