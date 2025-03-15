package org.psi.myfinappfrontapp.controller;

import java.time.LocalDate;

import org.psi.myfinappfrontapp.custom.CustomApiApi;
import org.psi.myfinappfrontapp.model.AccountDateSumDTO;
import org.psi.myfinappfrontapp.model.AccountHeaderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/backfront")
public class FrontFinanceController {
	
	@Autowired
	CustomApiApi customapiapi;
	
	
	@RequestMapping(value = "/accounts", method = RequestMethod.GET)
	public Flux<AccountHeaderDTO> getAllAccount(){
				
		return customapiapi.getAllAccount();
		
	}
	
	
	@RequestMapping(value = "/account/{id}", method = RequestMethod.GET)
	public Mono<AccountHeaderDTO> getAccountById(@PathVariable Long id){
		
		return customapiapi.getAccountById(id);
		
		
	}

	@RequestMapping(value = "/sumdate/{start}/{end}", method = RequestMethod.GET)
	public Flux<AccountDateSumDTO> getSumByDate(@PathVariable String start, @PathVariable String end){

		LocalDate localDateStart = LocalDate.parse(start);
		
		LocalDate localDateEnd = LocalDate.parse(end);


		return customapiapi.getSumByDate(localDateStart, localDateEnd);
		
		
	}

}