package org.psi.myfinappbackapp.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.mapping.Array;
import org.psi.myfinappbackapp.dto.AccountDateSumDTO;
import org.psi.myfinappbackapp.entities.AccountHeader;
import org.psi.myfinappbackapp.entities.AccountLine;
import org.psi.myfinappbackapp.repository.AccountHeaderRepository;
import org.psi.myfinappbackapp.repository.AccountLineRepository;
import org.psi.myfinappbackapp.util.AccountType;
import org.psi.myfinappbackapp.util.AmountOfMoney;
import org.psi.myfinappbackapp.util.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AddBDForTest {
	
	@Autowired
	private AccountHeaderRepository accountHeaderRepository;
	
	@Autowired
	private AccountLineRepository accountLineRepository;
	
	
	public void deleteAll() {

		List<AccountHeader> acall = accountHeaderRepository.findAll();

		acall.stream().forEach(ac ->{

			System.out.print("***************** remove");

			ac.removeAllAccountLine();

		});

		accountHeaderRepository.deleteAll();

	}

	public void addBD() {
		// TODO Auto-generated method stub

	
		
		System.out.println("********addbd*********"); 
		
		AccountHeader accountHeader1 = new AccountHeader();
		accountHeader1.setAccountType(AccountType.PEA);
		accountHeader1.setName("Mon PEA");
		
		AccountLine accountLine1 = new AccountLine();
		accountLine1.setDate(LocalDate.of(2024, 10, 26));
		accountLine1.setSolde(new AmountOfMoney(BigDecimal.valueOf(100.75), Currency.EUR));
		
		accountHeader1.addAccountLine(accountLine1);
		
		AccountLine accountLine4 = new AccountLine();
		accountLine4.setDate(LocalDate.of(2025, 10, 26));
		accountLine4.setSolde(new AmountOfMoney(BigDecimal.valueOf(2000.75), Currency.EUR));
		
		accountHeader1.addAccountLine(accountLine4);
		
		accountHeaderRepository.save(accountHeader1);
		
		
		AccountHeader accountHeader2 = new AccountHeader();
		accountHeader2.setAccountType(AccountType.CTO);
		accountHeader2.setName("Mon CTO");
		
		AccountLine accountLine2 = new AccountLine();
		accountLine2.setDate(LocalDate.of(2024, 10, 27));
		accountLine2.setSolde(new AmountOfMoney(BigDecimal.valueOf(200.75), Currency.EUR));
		
		accountHeader2.addAccountLine(accountLine2);
		
		accountHeaderRepository.save(accountHeader2);

		AccountHeader accountHeader8 = new AccountHeader();
		accountHeader8.setAccountType(AccountType.CTO);
		accountHeader8.setName("Mon CTO");
		
		AccountLine accountLine8 = new AccountLine();
		accountLine8.setDate(LocalDate.of(2025, 01, 27));
		accountLine8.setSolde(new AmountOfMoney(BigDecimal.valueOf(1400.88), Currency.EUR));
		
		accountHeader8.addAccountLine(accountLine8);
		
		accountHeaderRepository.save(accountHeader8);
		
	
		System.out.println("******finadbd***********"); 
		
	}



	public void readAcc() {
		
		List<AccountHeader> acc = accountHeaderRepository.findAll();
		
		for(AccountHeader account : acc) {
			
			System.out.println(account.toString());
			
			for(AccountLine line : account.getAccountLines()) {
				
				System.out.println("**** " + line);
				
			}
		}
		
		
	}
	
	
	public void testSum() {
		
		LocalDate start = LocalDate.of(1990, 01, 01);
		LocalDate end = LocalDate.of(2050, 01, 01);
		
		List<AccountDateSumDTO> accountDateSumDTOs = accountLineRepository.getSumByDate(start, end);
		
		System.out.println(accountDateSumDTOs.toString());
		
	}
	

}
