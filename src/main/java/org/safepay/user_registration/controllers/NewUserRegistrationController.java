package org.safepay.user_registration.controllers;

import jakarta.validation.Valid;
import org.safepay.user_registration.dto.NewUserDTO;
import org.safepay.user_registration.services.NewUserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewUserRegistrationController {

    private final NewUserRegistrationService newUserRegistrationService;

    @Autowired
    NewUserRegistrationController(NewUserRegistrationService newUserRegistrationService){
        this.newUserRegistrationService = newUserRegistrationService;
    }

    @PostMapping("/Sign-up")
    public ResponseEntity<String> acceptIncomingUserDetails(@Valid @RequestBody NewUserDTO newUserDetails){
        return ResponseEntity.ok(newUserRegistrationService.registerNewUser(newUserDetails));
    }

}
