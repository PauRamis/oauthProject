package com.esliceu.oauthProject.Controller;

import com.esliceu.oauthProject.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @Autowired
    LoginService loginService;
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/logingoogle")
    public String logingoogle(){

        return "redirect:" + loginService.getGoogleRedirection();
    }
}
