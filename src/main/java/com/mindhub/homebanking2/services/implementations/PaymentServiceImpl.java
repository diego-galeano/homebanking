package com.mindhub.homebanking2.services.implementations;

import com.mindhub.homebanking2.models.*;
import com.mindhub.homebanking2.repository.CardRepository;
import com.mindhub.homebanking2.repository.ClientRepository;
import com.mindhub.homebanking2.services.PaymentService;
import com.mindhub.homebanking2.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    TransactionService transactionService;

    @Override
    public ResponseEntity<Object> createPayment(String cardNumber, String cardHolder, String expirationMonth, String expirationYear, Double amount, int cvv, String description, Authentication authentication) {
        Client currentClient= clientRepository.findByEmail(authentication.getName());
        Card currentCard = cardRepository.findByNumber(cardNumber);
        boolean isCardAuth = currentClient.getCards().contains(currentCard);
        if (!isCardAuth){
            return new ResponseEntity<>("Missing data: Card Number. El número de tarjeta es inválido", HttpStatus.FORBIDDEN);
        }

        if (!currentCard.getCardHolder().toUpperCase(Locale.ROOT).equals(cardHolder.toUpperCase(Locale.ROOT))){
            return new ResponseEntity<>("Missing data: Card Holder. El nombre no corresponde a una tarjeta asociada.", HttpStatus.FORBIDDEN);
        }

        Integer thruMonth = Integer.valueOf(currentCard.getThruDate().getMonthValue());
        Integer paramMonth = Integer.valueOf(expirationMonth);
        if (thruMonth != paramMonth){
            return new ResponseEntity<>("Missing data: Mes de vencimiento incorrecto", HttpStatus.FORBIDDEN);
        }

        String thruYear = String.valueOf(currentCard.getThruDate().getYear());
        if ( !thruYear.equals(expirationYear) || thruYear.isEmpty()){
            return new ResponseEntity<>("Missing data: Año de vencimiento incorrecto", HttpStatus.FORBIDDEN);
        }
        if (currentCard.getCvv() != cvv || Objects.isNull(cvv)){
            return new ResponseEntity<>("Missing data: CVV. El número del código de seguridad es incorrecto", HttpStatus.FORBIDDEN);
        }

        Account account = currentClient.getAccounts().stream().findFirst().orElse(null);
        double accountBalance = account.getBalance();

        if (accountBalance < amount){
            return new ResponseEntity<>("Saldo disponible insuficiente. Disponible: $" + accountBalance , HttpStatus.FORBIDDEN);
        }

        LocalDate thruDate = currentCard.getThruDate();
        if (thruDate.isBefore(LocalDate.now())){
            return new ResponseEntity<>("La tarjeta se encuentra vencida", HttpStatus.FORBIDDEN);
        }
        Transaction transaction = new Transaction(TransactionType.DEBIT,- amount,description, LocalDateTime.now(),true, account);
        transactionService.saveTransaction(transaction);
        account.setBalance(accountBalance - amount);
        return new ResponseEntity<>("Operación exitosa",HttpStatus.CREATED);
    }
}
