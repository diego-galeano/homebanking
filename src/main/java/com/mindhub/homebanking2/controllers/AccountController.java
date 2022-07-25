package com.mindhub.homebanking2.controllers;

import com.mindhub.homebanking2.dtos.AccountDTO;
import com.mindhub.homebanking2.models.Account;
import com.mindhub.homebanking2.models.AccountType;
import com.mindhub.homebanking2.models.Client;
import com.mindhub.homebanking2.services.AccountService;
import com.mindhub.homebanking2.services.ClientService;
import com.mindhub.homebanking2.utils.AccountUtils;
import com.mindhub.homebanking2.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


//Servlets
@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;

    @GetMapping("/accounts")
    private List<AccountDTO> getAccount(){
        return accountService.getAccounts();
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return accountService.getAccount(id);
    }

    @PostMapping(path = "/clients/current/accounts")
    public ResponseEntity<Object> createAccount(@RequestParam String accountType, Authentication authentication) {
        Client currentClient =  clientService.findByEmail(authentication.getName());
        if (accountType.isEmpty()){
            return new ResponseEntity<>("Missing data: Tipo de cuenta", HttpStatus.FORBIDDEN);
        }

        if (clientService.getActiveAccounts(currentClient) > 2){
            return new ResponseEntity<>( "Excede el número de 3 cuentas activas.",HttpStatus.FORBIDDEN);
        }

//        Verifico que no se repita el número de cuenta
        Utilities utilities = new Utilities();
        String accountNumber;

        do {accountNumber = AccountUtils.getRandomAccountNumber();}
        while ( accountService.findByNumber(accountNumber) != null);

//        Creo Nueva Cuenta
        AccountType type = AccountType.valueOf(accountType);
        Account newAccount= new Account(type ,accountNumber,LocalDateTime.now(),0D, true,currentClient);
        currentClient.addAccount(newAccount);
        accountService.saveAccount(newAccount);
        return new ResponseEntity<>("New Account created",HttpStatus.CREATED);
    }

    @PutMapping(path = "/clients/current/deleteAccount")
    public ResponseEntity<Object> deleteAccount(@RequestParam String number) {
        return accountService.deleteAccount(number);
    }
}
