package com.esliceu.oauthProject.Controller;

import com.esliceu.oauthProject.Services.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    LoginService loginService;
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/logingoogle")
    public String logingoogle() throws Exception {

        return "redirect:" + loginService.getGoogleRedirection();
    }

    @GetMapping("/oauth2/callback")
    public String googleCombecack(@RequestParam String code, HttpSession session) throws Exception{
        String email = loginService.getGoogleUserEmail(code);
        session.setAttribute("email", email);
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String successController(HttpSession session){
        if (session.getAttribute("email") == null) return "error";
        return "success";
    }
}
