package com.app.bankapp.controller;

import com.app.bankapp.model.Account;
import com.app.bankapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    private AccountService accountService;

    // Map both "/" and "/home" to the home page
    @GetMapping({"/", "/home"})
    public String home(Model model, Principal principal) {
        // If a user is logged in, Principal will not be null.
        if (principal != null) {
            Account account = accountService.findAccountByUsername(principal.getName());
            model.addAttribute("account", account);
        }
        return "home";  // Returns home.html from the templates folder.
    }
}
