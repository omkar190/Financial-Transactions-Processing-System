package org.safepay.user_registration.controllers;

import jakarta.validation.Valid;
import org.safepay.user_registration.dto.NewUserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewUserRegistrationController {

    @PostMapping("/Sign-up")
    public ResponseEntity<String> acceptIncomingUserDetails(@Valid @RequestBody NewUserDTO newUserDetails){
        return ResponseEntity.ok("Welcome ");
    }

}
