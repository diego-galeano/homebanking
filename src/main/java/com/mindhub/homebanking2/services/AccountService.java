package com.mindhub.homebanking2.services;

import com.mindhub.homebanking2.dtos.AccountDTO;
import com.mindhub.homebanking2.models.Account;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountService {
    List<AccountDTO> getAccounts();
    AccountDTO getAccount(Long id);
    Account findByNumber(String number);
    void saveAccount(Account account);

    ResponseEntity<Object> deleteAccount(String number);
}
