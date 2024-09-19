package org.safepay.user_registration.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Integer userId;

    @NotNull
    @Column(name = "user_name")
    String username;

    @NotNull
    String surname;

    @NotNull
    @Column(name = "mob_no")
    String mobNo;

    @NotNull
    @Column(name = "email_id")
    String emailId;

    @NotNull
    @Column(name = "user_password")
    String userPassword;

    @Column(name = "is_active")
    Character isActive;
}
