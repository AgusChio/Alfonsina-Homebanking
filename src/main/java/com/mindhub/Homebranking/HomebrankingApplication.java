package com.mindhub.Homebranking;

import com.mindhub.Homebranking.models.*;
import com.mindhub.Homebranking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication // el homebanking va a ser aplicacion de springboot
public class HomebrankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebrankingApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository,
									  AccountRepository accountRepository,
									  TransactionRepository transactionRepository,
									  LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository,
									  CardRepository cardRepository) {
		return (args) -> {
//			Client melba2 = new Client();
//			melba2.setFirstName("melba2");
			Client melba = new Client("Melba", "Lorenzo","melba@mindhub.com", passwordEncoder.encode("melbita"));
			clientRepository.save(melba);
			Client jack = new Client("Jack", "Bauer","jackB@mindhub.com",passwordEncoder.encode("jacksito"));
			clientRepository.save(jack);
			Client adminSupremo = new Client("admin","supremo","adminSupremo@Homebanking.com", passwordEncoder.encode("adminsupremo257902340"));
			clientRepository.save(adminSupremo);
//			Client juancito = new Client();
//			juancito.addAccount(accountOne);
//
//			String nombredeJuan = juancito.getFirstName(); // guardo el valor de una propiedad en una variable


//
			Account accountOne = new Account( "VIN001", LocalDateTime.now(), 0 , melba,true,AccountType.SAVING);
			accountRepository.save(accountOne);
//			Account accountTwo = new Account("VIN002", LocalDateTime.now().plusDays(1), 7500, melba, true, AccountType.CURRENT);
//			accountRepository.save(accountTwo);
			Account accountThree = new Account("VIN003", LocalDateTime.now().plusDays(1), 0, jack,true, AccountType.SAVING);
			accountRepository.save(accountThree);


//			Transaction transactionOne = new Transaction(1500, TransactionType.CREDIT, LocalDateTime.now(), "hairdressing", accountOne,true);
//			transactionRepository.save(transactionOne);
//			Transaction transactionTwo = new Transaction(1000, TransactionType.DEBIT, LocalDateTime.now(), "telephone subscription", accountOne,true);
//			transactionRepository.save(transactionTwo);
//			Transaction transactionThree = new Transaction(5000, TransactionType.DEBIT, LocalDateTime.now(), "Supermarket", accountTwo,true);
//			transactionRepository.save(transactionThree);
//			Transaction transactionFour = new Transaction(500, TransactionType.CREDIT, LocalDateTime.now(), "Bread", accountTwo,true);
//			transactionRepository.save(transactionFour);
//			Transaction transactionFive = new Transaction(500, TransactionType.CREDIT, LocalDateTime.now(), "Bread", accountThree,true);
//			transactionRepository.save(transactionFive);


			Loan loanMortgage = new Loan("Mortgage", 500000, List.of(12,24,36,48,60), 1.50);
			loanRepository.save(loanMortgage);
			Loan loanPersonal = new Loan("Personal", 100000, List.of(6,12,24),1.30 );
			loanRepository.save(loanPersonal);
			Loan loanAutomotive = new Loan("Automotive", 300000, List.of(6,12,24,36),1.60);
			loanRepository.save(loanAutomotive);


//			ClientLoan loanMelbaOne = new ClientLoan(400000, 60, LocalDateTime.now(), melba, loanMortgage);
//			clientLoanRepository.save(loanMelbaOne);
//			ClientLoan loanMelbaTwo = new ClientLoan(50000, 12, LocalDateTime.now(), melba, loanPersonal);
//			clientLoanRepository.save(loanMelbaTwo);
//			ClientLoan loanJackOne = new ClientLoan(100000, 24, LocalDateTime.now(), jack, loanPersonal);
//			clientLoanRepository.save(loanJackOne);
//			ClientLoan loanJackTwo = new ClientLoan(200000, 36, LocalDateTime.now(), jack, loanAutomotive);
//			clientLoanRepository.save(loanJackTwo);

			Card cardMelba = new Card(melba.getFirstName() + " " + melba.getLastName(), "4235 5643 6785 8975", 457, LocalDate.now(), LocalDate.now().plusYears(5), CardType.DEBIT , CardColor.GOLD, melba,true, accountOne);
			cardRepository.save(cardMelba);
			Card cardMelbaTwo = new Card(melba.getFirstName() + " " + melba.getLastName(), "4367 8754 9087 7896", 875, LocalDate.now(), LocalDate.now().plusYears(5), CardType.CREDIT , CardColor.TITANIUM, melba,true, accountOne);
			cardRepository.save(cardMelbaTwo);
			Card cardMelbaThree = new Card(melba.getFirstName() + " " + melba.getLastName(), "4562 0788 9956 5437", 657, LocalDate.now(), LocalDate.now().plusYears(5), CardType.CREDIT , CardColor.SILVER, melba,true, accountOne);
			cardRepository.save(cardMelbaThree);
			Card cardJack = new Card(jack.getFirstName() + " " + jack.getLastName(), "4467 2878 9912 2304", 902, LocalDate.now(), LocalDate.now().plusYears(5), CardType.CREDIT , CardColor.SILVER, jack,true, accountThree);
			cardRepository.save(cardJack);

		};
	}
}
