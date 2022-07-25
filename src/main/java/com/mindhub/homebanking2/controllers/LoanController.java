package com.mindhub.homebanking2.controllers;

import com.mindhub.homebanking2.dtos.AccountDTO;
import com.mindhub.homebanking2.dtos.LoanApplicationDTO;
import com.mindhub.homebanking2.dtos.LoanDTO;
import com.mindhub.homebanking2.models.*;
import com.mindhub.homebanking2.repository.*;
import com.mindhub.homebanking2.services.*;
import com.mindhub.homebanking2.utils.LoanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientLoanService clientLoanService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private LoanRepository loanRepository;

    @GetMapping("/loans")
    private List<LoanDTO> getLoans() {
        return loanService.getLoans();
//        return loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @Transactional
    @PostMapping("/clients/current/loans")
    private ResponseEntity<Object> applyLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){

        Loan loanApplication = loanService.findById(loanApplicationDTO.getId());
        List<AccountDTO> accountList = accountService.getAccounts();
        Client currentClient = clientService.findByEmail(authentication.getName());
        Set<Account> accountsClient = currentClient.getAccounts();
        double amount = loanApplicationDTO.getAmount();

        if (loanApplication == null) {
            return new ResponseEntity<>("No se encontró el prestamo solicitado", HttpStatus.FORBIDDEN);
        }

        if(amount <= 0){
        return new ResponseEntity<>("Debe ingresar un monto mayor a 0", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getAmount() > loanApplication.getMaxAmount()){
            return new ResponseEntity<>("Excede el límite del préstamo", HttpStatus.FORBIDDEN);
        }

        boolean isPayments = loanApplication.getPayments().contains(loanApplicationDTO.getPayments());
        if (!isPayments){
            return new ResponseEntity<>("No se encontró la cantidad de cuotas solicitadas. Las opciones son:"+loanApplication.getPayments().toString(), HttpStatus.FORBIDDEN);
        }

        boolean isAccountExist = accountList.stream().anyMatch(destinationAccount -> destinationAccount.getNumber().equals(loanApplicationDTO.getAccountNum()));
        if (!isAccountExist){
            return new ResponseEntity<>("Error: Cuenta de destino inexistente", HttpStatus.FORBIDDEN);
        }

        boolean isAccountAuth = accountsClient.stream().anyMatch(account -> account.getNumber().equals(loanApplicationDTO.getAccountNum()));
        if ( !isAccountAuth){
            return new ResponseEntity<>("Cuenta de destino no es una cuenta propia.", HttpStatus.FORBIDDEN);
        }


        Account accountCredited = accountService.findByNumber(loanApplicationDTO.getAccountNum());
//        double interestRate = LoanUtils.getInterest(loanApplication);
        double interestRate = loanApplication.getInterestRate();
        double totalInterestCost = LoanUtils.calcInterest(interestRate, amount);
        double updateBalance = accountCredited.getBalance() + loanApplicationDTO.getAmount();

        accountCredited.setBalance(updateBalance);

        ClientLoan newClientLoan = new ClientLoan(totalInterestCost, loanApplicationDTO.getPayments() , currentClient, loanApplication);
        clientLoanService.saveClientLoan(newClientLoan);

        Transaction newTransaction = new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(),loanApplication.getName() + " loan approved", LocalDateTime.now(),  true,accountCredited);
        transactionService.saveTransaction(newTransaction);
        return new ResponseEntity<>("Loan approved", HttpStatus.CREATED);
    }
//
//    @Transactional
//    @PostMapping(path = "/admin/loans")
//    public ResponseEntity<Object>CreateNewLoans(@RequestParam String loanName,
//                                           @RequestParam Double maxAmount,
//                                           @RequestParam Double interestRate,
//                                           @RequestParam List<Integer> payments
//    ){
//
//            if ( loanName.isEmpty() ){
//                return new ResponseEntity<>("Missing data: Debe ingresar el tipo nombre del préstamo", HttpStatus.FORBIDDEN);
//            }
//            if( maxAmount < 0 ){
//                return new ResponseEntity<>("Missing data: Monto máximo. Debe ingresar un número válido mayor a cero", HttpStatus.FORBIDDEN);
//            }
//            if (interestRate.isNaN() || interestRate < 0) {
//                return new ResponseEntity<>("Missing data: Porcentaje de interes. Debe ingresar un número válido mayor a ceroDebe ingresar ", HttpStatus.FORBIDDEN);
//            }
//
//            if (loanService.findByName(loanName) !=  null){
//                return new ResponseEntity<>("" +
//                        "El préstamo quiere crear ya existente", HttpStatus.FORBIDDEN);
//            }
//            List<Integer> paymentsAllowed = new ArrayList<Integer>();
//            paymentsAllowed.add(6);
//            paymentsAllowed.add(12);
//
//            if(payments.isEmpty() || !paymentsAllowed.containsAll(payments)){
//                return new ResponseEntity<>("No ingresó un numero correcto de las cuotas" + paymentsAllowed, HttpStatus.FORBIDDEN);
//            }
//            if (interestRate.isNaN()) {
//                return new ResponseEntity<>("No ingreso un número válido", HttpStatus.FORBIDDEN);
//            }
//            Loan newLoan = new Loan(loanName, maxAmount, payments, interestRate);
//            loanService.saveLoan(newLoan);
//            return new ResponseEntity<>("Préstamo creado.", HttpStatus.ACCEPTED);
//        }

}
