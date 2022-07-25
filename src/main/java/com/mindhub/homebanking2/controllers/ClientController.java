package com.mindhub.homebanking2.controllers;
import com.mindhub.homebanking2.dtos.ClientDTO;
import com.mindhub.homebanking2.models.Account;
import com.mindhub.homebanking2.models.AccountType;
import com.mindhub.homebanking2.models.Client;
import com.mindhub.homebanking2.repository.AccountRepository;
import com.mindhub.homebanking2.services.AccountService;
import com.mindhub.homebanking2.services.ClientService;
import com.mindhub.homebanking2.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountService accountService;

    @GetMapping("/clients")
    public List<ClientDTO> getClientsDTO() {
        return clientService.getClientsDTO();
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientService.getClientDTO(id);
    }
    @GetMapping("/clients/current")
    public ClientDTO getAuthClientDTO(Authentication authentication) {
        Client authClient = clientService.getAuthClientDTO(authentication);
        return new ClientDTO(authClient);
    }

    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty()) {
            return new ResponseEntity<>("Missing data: First Name is required", HttpStatus.FORBIDDEN);
        }
        if (lastName.isEmpty()) {
            return new ResponseEntity<>("Missing data: Last Name is required", HttpStatus.FORBIDDEN);
        }
        if (email.isEmpty()) {
            return new ResponseEntity<>("Missing data: Email is required", HttpStatus.FORBIDDEN);
        }
        if (password.isEmpty()) {
            return new ResponseEntity<>("Missing data: Password is required", HttpStatus.FORBIDDEN);
        }
        if (clientService.findByEmail(email) !=  null) {
            return new ResponseEntity<>("El email ingresado ya está en uso", HttpStatus.FORBIDDEN);
        }

        Client newClient = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientService.saveClient(newClient);


        String accountNumber;
        do {accountNumber = AccountUtils.getRandomAccountNumber();}
        while ( accountService.findByNumber(accountNumber) != null);

//        Creo Nueva Cuenta
        Account newAccount= new Account(AccountType.AHORRO ,accountNumber, LocalDateTime.now(),0D, true,newClient);
        newClient.addAccount(newAccount);
        accountService.saveAccount(newAccount);
        return new ResponseEntity<>("New client created",HttpStatus.CREATED);
    }
}

