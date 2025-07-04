package com.app.bankapp.controller;

import com.app.bankapp.model.Account;
import com.app.bankapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/products")
    public String products() {
        return "products";
    }

    @GetMapping("/deals")
    public String deals() {
        return "deals";
    }

    @GetMapping("/payments")
    public String payments(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("transactions", accountService.findTransactionsByUsername(principal.getName()));
        }
        return "payments";
    }

    @GetMapping("/open-digital")
    public String openDigital() {
        return "open-digital";
    }

    @PostMapping("/open-digital")
    public String openDigitalSubmit(@RequestParam String name, @RequestParam String email, @RequestParam String phone, @RequestParam String username, @RequestParam String password, Model model) {
        // Here you would add logic to create the account, send confirmation, etc.
        model.addAttribute("success", true);
        return "open-digital";
    }

    @GetMapping("/personal-loan")
    public String personalLoan() {
        return "personal-loan";
    }

    @PostMapping("/personal-loan")
    public String personalLoanSubmit(@RequestParam String name, @RequestParam String email, @RequestParam String phone, @RequestParam int amount, @RequestParam int tenure, Model model) {
        model.addAttribute("success", true);
        return "personal-loan";
    }

    @GetMapping("/home-loan")
    public String homeLoan() {
        return "home-loan";
    }

    @PostMapping("/home-loan")
    public String homeLoanSubmit(@RequestParam String name, @RequestParam String email, @RequestParam String phone, @RequestParam int propertyValue, @RequestParam int amount, @RequestParam int tenure, Model model) {
        model.addAttribute("success", true);
        return "home-loan";
    }
}
