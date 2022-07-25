package com.mindhub.homebanking2.services.implementations;

import com.mindhub.homebanking2.models.ClientLoan;
import com.mindhub.homebanking2.repository.ClientLoanRepository;
import com.mindhub.homebanking2.services.ClientLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientLoanServiceImpl implements ClientLoanService {
    @Autowired
    ClientLoanRepository clientLoanRepository;
    @Override
    public void saveClientLoan(ClientLoan clientLoan) {
        clientLoanRepository.save(clientLoan);
    }

}
