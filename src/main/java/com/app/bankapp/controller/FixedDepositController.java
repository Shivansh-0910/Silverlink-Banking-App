package com.app.bankapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class FixedDepositController {

    // Show the form page
    @GetMapping("/fd-calculator")
    public String showFDForm() {
        return "fd-calculator";
    }

    // Handle form submission
    @PostMapping("/fd-calculator")
    public String calculateFD(
            @RequestParam double principal,
            @RequestParam double rate,
            @RequestParam int time,
            @RequestParam int frequency,
            Model model) {

        double amount = principal * Math.pow((1 + (rate / 100) / frequency), frequency * time);
        model.addAttribute("result", amount);
        return "fd-calculator";
    }
}
