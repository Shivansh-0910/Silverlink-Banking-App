package com.app.bankapp.controller;

import com.app.bankapp.model.CreditCard;
import com.app.bankapp.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/creditcard")
@CrossOrigin
public class CreditCardController {

    @Autowired
    private CreditCardService service;

    @PostMapping("/apply")
    public CreditCard applyCard(@RequestBody CreditCard card) {
        return service.applyCard(card);
    }

    @GetMapping("/all")
    public List<CreditCard> getAll() {
        return service.getAllCards();
    }

    @GetMapping("/{cardNumber}")
    public CreditCard getByNumber(@PathVariable String cardNumber) {
        return service.getByCardNumber(cardNumber);
    }

    @PostMapping("/spend/{cardNumber}/{amount}")
    public CreditCard spend(@PathVariable String cardNumber, @PathVariable double amount) {
        return service.spend(cardNumber, amount);
    }
}
