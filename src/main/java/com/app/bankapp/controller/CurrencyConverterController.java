package com.app.bankapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CurrencyConverterController {

    private static final Map<String, Double> exchangeRates = new HashMap<>();

    static {
        exchangeRates.put("USD", 0.012);  // 1 INR = 0.012 USD (example)
        exchangeRates.put("EUR", 0.011);
        exchangeRates.put("GBP", 0.0098);
        exchangeRates.put("JPY", 1.72);
    }

    @GetMapping("/currency-converter")
    public String showCurrencyForm() {
        return "currency-converter";
    }

    @PostMapping("/currency-converter")
    public String convertCurrency(
            @RequestParam double amount,
            @RequestParam String targetCurrency,
            Model model) {

        double rate = exchangeRates.getOrDefault(targetCurrency, 0.0);
        double result = amount * rate;

        model.addAttribute("amount", amount);
        model.addAttribute("currency", targetCurrency);
        model.addAttribute("convertedAmount", result);
        return "currency-converter";
    }
}
