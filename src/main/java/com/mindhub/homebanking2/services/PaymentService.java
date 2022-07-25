package com.mindhub.homebanking2.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface PaymentService {

    ResponseEntity<Object> createPayment(String cardNumber, String cardHolder, String expirationMonth, String expirationYear, Double amount, int cvv, String description, Authentication authentication);
}
