package org.psi.myfinappbackapp.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.psi.myfinappbackapp.dto.AccountDatePercentageDTO;
import org.psi.myfinappbackapp.dto.AccountDateSumDTO;
import org.psi.myfinappbackapp.dto.AccountHeaderDTO;
import org.psi.myfinappbackapp.entities.AccountHeader;
import org.psi.myfinappbackapp.mapper.AccountMapper;
import org.psi.myfinappbackapp.repository.AccountHeaderRepository;
import org.psi.myfinappbackapp.repository.AccountLineRepository;
import org.psi.myfinappbackapp.util.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class AccountService {
	
	@Autowired
	AccountHeaderRepository accountHeaderRepository;
	
	@Autowired
	AccountLineRepository accountLineRepository;

	@Autowired
	AccountMapper accountMapper;
	
	
	public List<AccountHeaderDTO> getAllAccount(){
		
		System.out.println("****************** Nb dans liste ***********  " + accountHeaderRepository.findAll().size());
		
		return accountMapper.mapHeaderListToDTO(accountHeaderRepository.findAll());
	}
	
	public AccountHeaderDTO getById(Long id) {
		
		return accountMapper.mapHeaderToDTO(accountHeaderRepository.findById(id).orElse(null));
		
	}

	public List<AccountDateSumDTO> getDateSum(LocalDate start, LocalDate end) {
	
		return accountLineRepository.getSumByDate(start, end);
	}

	public List<AccountDateSumDTO> getTypeDateSum(String accountType, LocalDate start, LocalDate end) {
	
		return accountHeaderRepository.getSumByAccountTypeAndByDate(AccountType.getAccountType(accountType), start, end);
	}



	public void deleteAll(){

		
		System.out.println("*********************DELETE*****************");
		accountHeaderRepository.deleteAll();

	}

	public void saveAccountsToDataBase(List<AccountHeaderDTO> listAccountDTO) throws Exception{

	

		List<AccountHeader> accounts = accountMapper.mapHeaderList(listAccountDTO);

		System.out.println("*********************SAVE*****************");
		accountHeaderRepository.saveAll(accounts);


	}


	public List<String> getAccountTypes(){

		return AccountType.getAccountypes();		
	}

	public List<AccountDatePercentageDTO> getDatePercentage(LocalDate start, LocalDate end) {
		// TODO Auto-generated method stub
			
		List<AccountDateSumDTO> list = getDateSum(start, end);

		sortListMonthYear(list);

		List<AccountDatePercentageDTO> percentages = new ArrayList<>();

		BigDecimal firstSum = list.get(0).getSum();

		for (AccountDateSumDTO ads : list) {
			percentages.add(new AccountDatePercentageDTO(ads.getYear(), ads.getMonth(), calculatePercentage(firstSum, ads.getSum())));
		}
		return percentages;
	}


	public List<AccountDatePercentageDTO> getTypeDatePercentage(String accountType, LocalDate start, LocalDate end) {
		// TODO Auto-generated method stub
		
		List<AccountDateSumDTO> list = getTypeDateSum(accountType, start, end);

		sortListMonthYear(list);

		List<AccountDatePercentageDTO> percentages = new ArrayList<>();

		BigDecimal firstSum = list.get(0).getSum();

		for (AccountDateSumDTO ads : list) {
			percentages.add(new AccountDatePercentageDTO(ads.getYear(), ads.getMonth(), calculatePercentage(firstSum, ads.getSum())));
		}
		return percentages;
	}


	private void sortListMonthYear(List<AccountDateSumDTO> sums) {
		// TODO Auto-generated method stub
		sums.sort((p1, p2) -> {
			int yearComparison = Integer.compare(p1.getYear(), p2.getYear());
			if (yearComparison != 0) {
				return yearComparison;
			}
			return Integer.compare(p1.getMonth(), p2.getMonth());
		});
	}

	private Double calculatePercentage(BigDecimal firstSum, BigDecimal sum) {
		// TODO Auto-generated method stub
		
		Double firstSumDouble = firstSum.doubleValue();
		Double sumDouble = sum.doubleValue();
		Double percentage = ((sumDouble - firstSumDouble) / firstSumDouble) * 100;
		
		return roundDouble(percentage);
	}

	private static double roundDouble(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP); // Rounds to 2 decimal places using standard rounding (0.5 rounds up)
        return bd.doubleValue();
    }



	
}
