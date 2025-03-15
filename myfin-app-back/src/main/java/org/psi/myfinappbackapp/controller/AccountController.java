package org.psi.myfinappbackapp.controller;

import java.time.LocalDate;
import java.util.List;

import org.psi.myfinappbackapp.dto.AccountDateSumDTO;
import org.psi.myfinappbackapp.dto.AccountHeaderDTO;
import org.psi.myfinappbackapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}
