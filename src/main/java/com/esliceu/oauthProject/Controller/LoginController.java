package com.esliceu.oauthProject.Controller;

import com.esliceu.oauthProject.Services.LoginDiscordService;
import com.esliceu.oauthProject.Services.LoginMicrosoftService;
import com.esliceu.oauthProject.Services.LoginGoogleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    LoginGoogleService loginService;

    @Autowired
    LoginMicrosoftService loginMicrosoftService;

    @Autowired
    LoginDiscordService loginDiscordService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    //Google
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
    public String successController(HttpSession session, Model model){
        String email = (String) session.getAttribute("email");
        if (email == null) return "error";
        model.addAttribute("email", email);
        return "success";
    }

    //Discord
    @GetMapping("/logindiscord")
    public String logindiscord() throws Exception{
        return "redirect:" + loginDiscordService.getDiscordRedirection();
    }

    @GetMapping("/discord/callback")
    public String discordCallback(@RequestParam String code, HttpSession session) throws Exception {
        String email = loginDiscordService.getDiscordUserEmail(code);
        session.setAttribute("email", email);
        return "redirect:/success";
    }


    /*@GetMapping("/oauth2/callback")
    public String discordCombecack(@RequestParam String code, HttpSession session) throws Exception{
        System.out.println("Code:");
        System.out.println(code);
        /*String email = loginService.getGoogleUserEmail(code);
        session.setAttribute("email", email);
        return "redirect:/success";
    }*/

}
