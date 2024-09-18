package org.safepay.availability;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityCheckController {

    @GetMapping("/checkSecurity")
    public String checkIfWebPageIsSecured(){
        return "Yup....I am Secured";
    }

}
