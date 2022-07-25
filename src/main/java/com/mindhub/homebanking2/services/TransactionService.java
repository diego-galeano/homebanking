package com.mindhub.homebanking2.services;

import com.mindhub.homebanking2.dtos.TransactionDTO;
import com.mindhub.homebanking2.models.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> getTransactions();
    TransactionDTO findById(Long id);
    void saveTransaction(Transaction transaction);

}
