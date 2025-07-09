package com.jain.vaultnote;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyClass {

    @GetMapping("hi")
    public String sayHello(){
        return "Helloo";
    }
}
