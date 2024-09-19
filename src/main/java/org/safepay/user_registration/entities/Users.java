package org.safepay.user_registration.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer userId;

    @NotNull
    String userName;

    @NotNull
    String surname;

    @NotNull
    String mobNo;

    @NotNull
    String emailId;

    @NotNull
    String userPassWord;

}
