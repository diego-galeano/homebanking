package com.mindhub.homebanking2.services.implementations;

import com.mindhub.homebanking2.dtos.LoanDTO;
import com.mindhub.homebanking2.models.Loan;
import com.mindhub.homebanking2.repository.LoanRepository;
import com.mindhub.homebanking2.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public  class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Override
    public Loan findById(Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    @Override
    public Loan findByName(String name) {
        return loanRepository.findByName(name);
    }

    @Override
    public List<LoanDTO> getLoans() {
        List<LoanDTO> loanDTOS = loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toList());
        return loanDTOS;
    }
    @Override
    public void saveLoan(Loan loan){
        loanRepository.save(loan);
    }

}
