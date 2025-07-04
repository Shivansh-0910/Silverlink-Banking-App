package com.app.bankapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.bankapp.model.CreditCard;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    CreditCard findByCardNumber(String cardNumber);
}
