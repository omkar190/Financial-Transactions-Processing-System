package org.safepay.availability;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeartBeatController {

    //Just to check if the application is running or not.
    @GetMapping("/OK")
    public String healthCheck(){
        return "OK";
    }

}
