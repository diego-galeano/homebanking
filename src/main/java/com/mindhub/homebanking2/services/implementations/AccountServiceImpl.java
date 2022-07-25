package com.mindhub.homebanking2.services.implementations;

import com.mindhub.homebanking2.dtos.AccountDTO;
import com.mindhub.homebanking2.models.Account;
import com.mindhub.homebanking2.models.Transaction;
import com.mindhub.homebanking2.repository.AccountRepository;
import com.mindhub.homebanking2.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(Collectors.toList());
    }

    @Override
    public AccountDTO getAccount(Long id) {
        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
//        Account account = this.accountRepository.findById(id).get();
//        return new AccountDTO(account);
    }

    @Override
    public Account findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public ResponseEntity<Object> deleteAccount(String number) {
        if(number.isEmpty())
            return new ResponseEntity<>("Debe ingresar un numer de cuenta", HttpStatus.FORBIDDEN);
        Account account = accountRepository.findByNumber(number);
        account.setActive(false);
        for(Transaction transaction  : account.getTransactions()){
            transaction.setActive(false);
        }
        accountRepository.save(account);
        return new ResponseEntity<>("Cuenta eliminada",HttpStatus.ACCEPTED);
    }

}
