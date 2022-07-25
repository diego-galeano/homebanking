package com.mindhub.homebanking2.controllers;

import com.mindhub.homebanking2.models.*;
import com.mindhub.homebanking2.repository.CardRepository;
import com.mindhub.homebanking2.repository.ClientRepository;
import com.mindhub.homebanking2.repository.TransactionRepository;
import com.mindhub.homebanking2.services.PaymentService;
import com.mindhub.homebanking2.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;

@RestController
    @RequestMapping("/api")
    public class PaymentsController {

        @Autowired
        PaymentService paymentService;


        @Transactional
        @PostMapping(path = "/clients/current/payment")
        public ResponseEntity<Object> makePayment(
                                                  @RequestParam String cardNumber,
                                                  @RequestParam String cardHolder,
                                                  @RequestParam String expirationMonth,
                                                  @RequestParam String expirationYear,
                                                  @RequestParam Double amount,
                                                  @RequestParam int cvv,
                                                  @RequestParam String description,
                                                  Authentication authentication) {

           return paymentService.createPayment(cardNumber,cardHolder,expirationMonth,expirationYear,amount,cvv,description,authentication);
        }
    }


