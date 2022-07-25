package com.mindhub.homebanking2.services;

import com.mindhub.homebanking2.dtos.LoanDTO;
import com.mindhub.homebanking2.models.Loan;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LoanService {
    Loan findById(Long id);
    Loan findByName(String name);
    List<LoanDTO> getLoans();

    void saveLoan(Loan loan);
//    ResponseEntity<Object> createNewLoan(String loanName, Double maxAmount, List<Integer> payments, Double interestRate);
}
