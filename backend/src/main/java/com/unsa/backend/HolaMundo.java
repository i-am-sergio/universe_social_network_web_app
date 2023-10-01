package com.unsa.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaMundo {
    
    @GetMapping("/")
    String gretting(){
        return "Hola Mundo";
    }    
}
