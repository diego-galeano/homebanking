package com.mindhub.homebanking2;

import com.mindhub.homebanking2.models.*;
import com.mindhub.homebanking2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@SpringBootApplication
public class Homebanking2Application {

	@Autowired
	private PasswordEncoder passwordEncoder;

	
	public static void main(String[] args) {
		SpringApplication.run(Homebanking2Application.class, args);


		System.out.println("======finish========");

	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository){
		return args -> {

			Client client1 = clientRepository.save(new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("mindhub27")));
			Client client2 = clientRepository.save(new Client("Vitalik","Buterin","vitalik@ethereum.com", passwordEncoder.encode("cripto")));
			Client client3 = clientRepository.save(new Client("Tim","Cook", "tim@appel.com", passwordEncoder.encode("manzanaM1")));


//			Admin example:
			Client admin = clientRepository.save(new Client("admin","admin","admin", passwordEncoder.encode("1234")));

			LocalDateTime today = LocalDateTime.now();
			LocalDateTime tomorrow = today.plusDays(1);


			Account account1 = accountRepository.save(new Account(AccountType.CORRIENTE,"VIN001",today, 5000D, true,client1 ));
			Account account2 =accountRepository.save(new Account(AccountType.AHORRO,"VIN002", tomorrow, 7500D, true, client1));

			accountRepository.save(new Account(AccountType.AHORRO ,"VIN003", today.minusDays(1), 1_000_000.05, true,client2));

			accountRepository.save(new Account(AccountType.CORRIENTE,"VIN004", LocalDateTime.now().plusDays(2), 2000D,true, client3));

//			transactionRepository.save(new Transaction(TransactionType.DEBIT, -1234.04, "AYSA", LocalDateTime.now(), account2));
//			transactionRepository.save(new Transaction(TransactionType.CREDIT, 25000D,"mercadopago", LocalDateTime.now(),  account1));

			Loan mortgageLoan = loanRepository.save(new Loan("Hipotecario" , 500_000D,List.of(12,24,36,48,60), 10d));
			Loan personalLoan = loanRepository.save(new Loan("Personal" , 100_000D,List.of(6,12,24), 20d));
			Loan carLoan = loanRepository.save(new Loan("Automotriz" , 300_000D,List.of(6,12,24,36), 19d));

			ClientLoan clientLoan1 = clientLoanRepository.save(new ClientLoan(400_000D, 60 , client1,mortgageLoan));
			ClientLoan clientLoan2= clientLoanRepository.save(new ClientLoan(50_000D, 12, client1, personalLoan));

			ClientLoan clientLoan3 = clientLoanRepository.save(new ClientLoan(100000D, 24 , client2, personalLoan));
			ClientLoan clientLoan4 = clientLoanRepository.save(new ClientLoan(200000D, 36 , client2,carLoan));

			Card card1= cardRepository.save(new Card(CardType.DEBIT, CardColor.GOLD, "4040 5050 1234 5678", 945, LocalDate.now(), LocalDate.now().plusYears(5), client1, true));
			Card card2 = cardRepository.save(new Card(CardType.CREDIT, CardColor.TITANIUM, "5045 1050 3456 2145", 432, LocalDate.now(), LocalDate.now().plusYears(5), client1,true));
			Card card3 = cardRepository.save(new Card(CardType.CREDIT, CardColor.SILVER, "4040 4532 6236 8434", 425, LocalDate.now(), LocalDate.now().plusYears(5), client2, true));
			Card expiredCard = cardRepository.save(new Card(CardType.CREDIT, CardColor.GOLD, "4040 5050 9876 4321", 123, LocalDate.now().minusYears(7), LocalDate.now().minusYears(2), client1, true));
		};
	}

}
