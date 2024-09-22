package org.safepay.user_registration.controllers;

import org.safepay.user_registration.services.AccountVerificationService;
import org.safepay.user_registration.util.ResponseUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountVerificationController {

    private final AccountVerificationService accountVerificationService;

    @Autowired
    AccountVerificationController(AccountVerificationService accountVerificationService){
        this.accountVerificationService = accountVerificationService;
    }

    @GetMapping("/verify-account/new-user")
    public ResponseEntity<String> verifyMyAccount(@RequestParam String token){

        //Passes the received data to service to check if the request is valid. If encounters any error or the data is not valid returns false
        ResponseUtility response = accountVerificationService.activateNewUserAccount(token);

        if(Boolean.FALSE.equals(response.getResult())){
            return new ResponseEntity<>(response.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response.getMessage(), HttpStatus.ACCEPTED);
    }

}
