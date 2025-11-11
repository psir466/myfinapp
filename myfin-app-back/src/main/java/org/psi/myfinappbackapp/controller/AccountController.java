package org.psi.myfinappbackapp.controller;

import java.time.LocalDate;
import java.util.List;

import org.psi.myfinappbackapp.dto.AccountDatePercentageDTO;
import org.psi.myfinappbackapp.dto.AccountDateSumDTO;
import org.psi.myfinappbackapp.dto.AccountHeaderDTO;
import org.psi.myfinappbackapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Api", description = "Operations related to accounts")
public class AccountController {

	//http://localhost:8080/swagger-ui/index.html
	
	@Autowired
	AccountService accountService;

	@RequestMapping(value = "/accounts", method = RequestMethod.GET)
	@Operation(summary = "Get all the account", description = "Retrieves all the account")
	public ResponseEntity<List<AccountHeaderDTO>> getAllAccount() {

		List<AccountHeaderDTO> accountHeaderDTOs = accountService.getAllAccount();

		if (accountHeaderDTOs != null) {
			return ResponseEntity.ok(accountHeaderDTOs);
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@RequestMapping(value = "/account/{id}", method = RequestMethod.GET)
	@Operation(summary = "Get account by id", description = "Retrieves an account by its id")
	public ResponseEntity<AccountHeaderDTO> getAccountById(@PathVariable Long id) {

		AccountHeaderDTO accountHeaderDTO = accountService.getById(id);

		if (accountHeaderDTO != null) {
			return ResponseEntity.ok(accountHeaderDTO);
		} else {
			return ResponseEntity.notFound().build();
		}

	}
	
	@GetMapping(value = "/accountSumDate/{start}/{end}")
	@Operation(summary = "Get sum of solde by month and year", description = "Retrieves sum of solde of all accounts by month and year")
	public ResponseEntity<List<AccountDateSumDTO>> getSumByDate(@PathVariable("start") LocalDate start, @PathVariable("end") LocalDate end) {

		List<AccountDateSumDTO> accountsums = accountService.getDateSum(start, end);

		if (accountsums != null) {
			return ResponseEntity.ok(accountsums);
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@GetMapping(value = "/accountPercentageDate/{start}/{end}")
	@Operation(summary = "Get sum of solde by month and year", description = "Retrieves percentage of solde of all accounts by month and year")
	public ResponseEntity<List<AccountDatePercentageDTO>> getPercentageByDate(@PathVariable("start") LocalDate start, @PathVariable("end") LocalDate end) {

		System.out.println("getPercentageByDate");

		List<AccountDatePercentageDTO> accountPercentages = accountService.getDatePercentage(start, end);


		if (accountPercentages != null) {
			return ResponseEntity.ok(accountPercentages);
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@GetMapping(value = "/accountByTypeSumDate/{accountType}/{start}/{end}")
	@Operation(summary = "Get sum of type of solde by month and year", description = "Retrieves sum of solde of all accounts by month and year")
	public ResponseEntity<List<AccountDateSumDTO>> getTypeSumByDate(@PathVariable("accountType") String accountType, @PathVariable("start") LocalDate start, @PathVariable("end") LocalDate end) {

		List<AccountDateSumDTO> accountsums = accountService.getTypeDateSum(accountType, start, end);

		if (accountsums != null) {
			return ResponseEntity.ok(accountsums);
		} else {
			return ResponseEntity.notFound().build();
		}

	}

		@GetMapping(value = "/accountByTypePercentageDate/{accountType}/{start}/{end}")
	@Operation(summary = "Get percentage of type of solde by month and year", description = "Retrieves percentage of solde of all accounts by month and year")
	public ResponseEntity<List<AccountDatePercentageDTO>> getTypePercentageByDate(@PathVariable("accountType") String accountType, @PathVariable("start") LocalDate start, @PathVariable("end") LocalDate end) {

		List<AccountDatePercentageDTO> accountPercentages = accountService.getTypeDatePercentage(accountType, start, end);

		if (accountPercentages != null) {
			return ResponseEntity.ok(accountPercentages);
		} else {
			return ResponseEntity.notFound().build();
		}

	}


	
	@GetMapping(value = "/accountTypes")
	@Operation(summary = "Get all the accoun types", description = "Retrieves all the account types")
	public ResponseEntity<List<String>> getAccounTypes() {

		List<String> accountTypes = accountService.getAccountTypes();

		if (accountTypes != null) {
			return ResponseEntity.ok(accountTypes);
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@RequestMapping(value = "/saveaccounts", method = RequestMethod.POST)
	@Operation(summary = "Post accounts to the database", description = "Post accounts to the database")
	public ResponseEntity<String> postAccountsToDataBase(@RequestBody List<AccountHeaderDTO> body) {

		try {

			System.out.println(body);

			accountService.deleteAll();

			accountService.saveAccountsToDataBase(body);

			return new ResponseEntity<>("OK", HttpStatus.CREATED);
			
		} catch (Exception e) {
			// TODO: handle exception

			System.out.println(e);

			return new ResponseEntity<>("PAS OK", HttpStatus.INTERNAL_SERVER_ERROR);
		}


	

	}

}
