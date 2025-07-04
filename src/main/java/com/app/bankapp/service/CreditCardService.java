package com.app.bankapp.service;

import com.app.bankapp.model.CreditCard;
import com.app.bankapp.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditCardService {

    @Autowired
    private CreditCardRepository repo;

    public CreditCard applyCard(CreditCard card) {
        // Check if card number already exists
        CreditCard existingCard = repo.findByCardNumber(card.getCardNumber());

        if (existingCard != null) {
            throw new RuntimeException("Credit card with this number already exists.");
        }

        card.setBalanceUsed(0);
        return repo.save(card);
    }

    public List<CreditCard> getAllCards() {
        return repo.findAll();
    }

    public CreditCard getByCardNumber(String number) {
        return repo.findByCardNumber(number);
    }

    public CreditCard spend(String number, double amount) {
        CreditCard card = repo.findByCardNumber(number);
        if (card != null && (card.getBalanceUsed() + amount) <= card.getCreditLimit()) {
            card.setBalanceUsed(card.getBalanceUsed() + amount);
            return repo.save(card);
        }
        throw new RuntimeException("Insufficient credit limit or card not found.");
    }
}
