package org.psi.myfinappbackapp.service;

import java.time.LocalDate;
import java.util.List;

import org.psi.myfinappbackapp.dto.AccountDateSumDTO;
import org.psi.myfinappbackapp.dto.AccountHeaderDTO;
import org.psi.myfinappbackapp.entities.AccountHeader;
import org.psi.myfinappbackapp.mapper.AccountMapper;
import org.psi.myfinappbackapp.repository.AccountHeaderRepository;
import org.psi.myfinappbackapp.repository.AccountLineRepository;
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

	public void saveAccountsToDataBase(List<AccountHeaderDTO> listAccountDTO) throws Exception{

		List<AccountHeader> accounts = accountMapper.mapHeaderList(listAccountDTO);


		accountHeaderRepository.saveAll(accounts);


	}
	
}
