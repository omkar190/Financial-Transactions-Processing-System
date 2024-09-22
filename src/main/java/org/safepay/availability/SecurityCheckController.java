package org.safepay.availability;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityCheckController {

    //For checking if Spring Security is forcing the authentication or not.
    @GetMapping("/checkSecurity")
    public String checkIfWebPageIsSecured(){
        return "Yup....I am Secured";
    }

}
