package com.mindhub.homebanking2.controllers;


import com.mindhub.homebanking2.dtos.TransactionDTO;
import com.mindhub.homebanking2.models.Account;
import com.mindhub.homebanking2.models.Client;
import com.mindhub.homebanking2.models.Transaction;
import com.mindhub.homebanking2.models.TransactionType;
import com.mindhub.homebanking2.services.AccountService;
import com.mindhub.homebanking2.services.ClientService;
import com.mindhub.homebanking2.services.TransactionService;
import com.mindhub.homebanking2.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    TransactionService transactionService;
    @Autowired
    AccountService accountService;
    @Autowired
    ClientService clientService;

    @GetMapping("/transactions")
    private List<Transaction> getTransaction() {
        return transactionService.getTransactions();
    }

    @GetMapping("/transactions/{id}")
    public TransactionDTO getTransactionById(@PathVariable Long id) {
        return transactionService.findById(id);
//        return transactionRepository.findById(id).map(transaction -> new TransactionDTO(transaction).orElse(null);

    }
    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> makeTransaction(@RequestParam String amount, @RequestParam String description, @RequestParam String originAccountNum, @RequestParam String destinationAccountNum, Authentication authentication) {
        Client currentClient = clientService.findByEmail(authentication.getName());
        Account originAccount = accountService.findByNumber(originAccountNum);
        Account destinationAccount = accountService.findByNumber(destinationAccountNum);
        double amountTransaction;

        //Válido que el mónto a tranferir sea un número y no sea negativo.
        if (amount == null | !Utilities.isNumeric(amount)) {
            return new ResponseEntity<>("missing data: Debe ingresar un número", HttpStatus.FORBIDDEN);
        } else if (Double.parseDouble(amount) < 0D) {
            return new ResponseEntity<>("missing data: Debe ingresar un mónto válido mayor a cero", HttpStatus.FORBIDDEN);
        } else {
            amountTransaction = Double.parseDouble(amount);
        }


        if ( description == null | description.isEmpty()){
            return new ResponseEntity<>("Missing data Description", HttpStatus.FORBIDDEN);
        }
        if (originAccountNum == null | originAccountNum.isEmpty()){
            return new ResponseEntity<>("Missing data Origin Account Number", HttpStatus.FORBIDDEN);
        }
        if (destinationAccountNum.isEmpty()){
            return new ResponseEntity<>("Missing data Destination Account Number", HttpStatus.FORBIDDEN);
        }
        //Valido que la cuenta exista y que sea propia del cliente
        if (originAccount == null | !Objects.equals(originAccount.getClient(), currentClient)){
                return new ResponseEntity<>("Cuenta de origen inexistente", HttpStatus.FORBIDDEN);
            }

        if (destinationAccountNum == null | originAccountNum.equals(destinationAccountNum)){
            return new ResponseEntity<>("No puede realizar una transacción a la misma cuenta", HttpStatus.FORBIDDEN);
        }

        if (originAccount.getBalance() < amountTransaction){
            return new ResponseEntity<>("No tiene saldo suficiente para la operación", HttpStatus.FORBIDDEN);
        }

        if (destinationAccount == null){
            return new ResponseEntity<>("La cuenta de destino es inexistente", HttpStatus.FORBIDDEN);

        }
        else {
            double updateOriginAccount = originAccount.getBalance() - amountTransaction;
            double updateDestinationAccount = destinationAccount.getBalance() + amountTransaction;

            Transaction debitTransaction = new Transaction(TransactionType.DEBIT,- amountTransaction, description + " " + destinationAccountNum, LocalDateTime.now(), true,originAccount);
            Transaction creditTransaction = new Transaction(TransactionType.CREDIT, amountTransaction, description + " " + originAccountNum, LocalDateTime.now(), true,destinationAccount);

            transactionService.saveTransaction(debitTransaction);
            transactionService.saveTransaction(creditTransaction);

            originAccount.setBalance(updateOriginAccount);
            destinationAccount.setBalance(updateDestinationAccount);
            return new ResponseEntity<>("Transacción exitosa", HttpStatus.CREATED);
        }
    }
}