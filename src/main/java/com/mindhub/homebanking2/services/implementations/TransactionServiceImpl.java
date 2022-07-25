package com.mindhub.homebanking2.services.implementations;

import com.mindhub.homebanking2.dtos.TransactionDTO;
import com.mindhub.homebanking2.models.Transaction;
import com.mindhub.homebanking2.repository.TransactionRepository;
import com.mindhub.homebanking2.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public TransactionDTO findById(Long id) {
        TransactionDTO transactionDTO = transactionRepository.findById(id).map(TransactionDTO::new).orElse(null);
        return transactionDTO;
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
