package com.app.bankapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CreditCardWebController {

    @GetMapping("/credit-card")
    public String showCreditCardPage() {
        return "credit-card"; // This should match the credit-card.html file in templates
    }
}
