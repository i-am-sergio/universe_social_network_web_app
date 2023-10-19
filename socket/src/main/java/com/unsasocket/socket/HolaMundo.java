package com.unsasocket.socket;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaMundo {
    
    @GetMapping("/")
    String gretting(){
        return "Hola Mundo";
    }    
}