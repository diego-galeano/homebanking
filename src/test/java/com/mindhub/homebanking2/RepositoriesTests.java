package com.mindhub.homebanking2;

import com.mindhub.homebanking2.models.*;
import com.mindhub.homebanking2.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.mindhub.homebanking2.models.CardColor.GOLD;
import static com.mindhub.homebanking2.models.CardType.DEBIT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;


//    @DataJpaTest
    @SpringBootTest
    @AutoConfigureTestDatabase(replace = NONE)
    public class RepositoriesTests {

        @Autowired
        LoanRepository loanRepository;

        @Autowired
        AccountRepository accountRepository;

        @Autowired
        CardRepository cardRepository;

        @Autowired
        ClientRepository clientRepository;

        @Autowired
        TransactionRepository transactionRepository;

        @Test
        public void existLoans(){
            List<Loan> loans = loanRepository.findAll();
            assertThat(loans,is(not(empty())));
        }

        @Test
        public void existPersonalLoan(){
            List<Loan> loans = loanRepository.findAll();
            assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
        }

        @Test
        public void existHipotecarioLoan(){
            List<Loan> loans = loanRepository.findAll();
            assertThat(loans, hasItem(hasProperty("name", is("Hipotecario"))));
        }

        @Test
        public void existAutomotrizLoan(){
             List<Loan> loans = loanRepository.findAll();
             assertThat(loans, hasItem(hasProperty("name", is("Automotriz"))));
        }

        @Test
        public void existAccounts(){
            List<Account> accounts = accountRepository.findAll() ;
            assertThat(accounts,is(not(empty())));
        }

        @Test
        public  void existClientsCreated(){
            List<Client> clients = clientRepository.findAll();
            assertThat(clients, is(not(empty())));
    }

        @Test
        public void existClient(){
            List<Client> clients = clientRepository.findAll();
         assertThat(clients,hasItem(hasProperty("firstName",is("Melba"))));
    }
        @Test
        public void existAccountCreated(){
            List<Account> account = accountRepository.findAll();
            assertThat(account,hasItem(hasProperty("number", is("VIN001"))));
            assertThat(account,hasItem(hasProperty("number", is("VIN002"))));
    }

        @Test
        public void existCardsCreated(){
            List<Card> cardList = cardRepository.findAll();
            assertThat(cardList, is(not(empty())));
    }
        @Test
        public void existCreditCards(){
            List<Card> creditCards = cardRepository.findAll();
            CardType cardTypeGold = CardType.CREDIT;
            assertThat(creditCards,hasItem((hasProperty("cardType", is(cardTypeGold)))));
            assertThat(creditCards, hasItem(hasProperty("cardColor", is(GOLD))));
    }

        @Test
        public void existDebitCards(){
            List<Card> creditCards = cardRepository.findAll();
            assertThat(creditCards, hasItem(hasProperty("cardType", is(DEBIT))));
            assertThat(creditCards, hasItem(hasProperty("cardColor", is(GOLD))));
    }

        @Test
        public void existTransactionsCreated(){
            List<Transaction> transactions = transactionRepository.findAll();
            assertThat(transactions, is(not(empty())));
    }

        @Test
        public void existTransactions(){
            List<Transaction> transactions = transactionRepository.findAll();
            TransactionType transactionType = TransactionType.CREDIT;
            assertThat(transactions, hasItem(hasProperty("TransactionType", is(transactionType))));
    }
}
