package com.app.bankapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoanCalculatorController {

    @GetMapping("/emi-calculator")
    public String showEMIForm() {
        return "emi-calculator";
    }

    @PostMapping("/emi-calculator")
    public String calculateEMI(
            @RequestParam double loanAmount,
            @RequestParam double annualRate,
            @RequestParam int tenureYears,
            Model model) {

        double monthlyRate = annualRate / 12 / 100;
        int totalMonths = tenureYears * 12;

        double emi = (loanAmount * monthlyRate * Math.pow(1 + monthlyRate, totalMonths)) /
                (Math.pow(1 + monthlyRate, totalMonths) - 1);

        model.addAttribute("emi", emi);
        return "emi-calculator";
    }
}
