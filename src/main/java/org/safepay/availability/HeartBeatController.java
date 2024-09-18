package org.safepay.availability;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeartBeatController {

    @GetMapping("/OK")
    public String healthCheck(){
        return "OK";
    }

}
