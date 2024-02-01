package com.esliceu.oauthProject.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/logingoogle")
    public String logingoogle(){
        return "redirect:https://accounts.google.com/o/oauth2/v2/auth";
    }
}
