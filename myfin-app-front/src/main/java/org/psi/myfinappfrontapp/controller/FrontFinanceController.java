package org.psi.myfinappfrontapp.controller;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import org.psi.myfinappfrontapp.custom.CustomApiApi;
import org.psi.myfinappfrontapp.custom.CustomApiApi2;
import org.psi.myfinappfrontapp.api.model.AccountDateSumDTO;
import org.psi.myfinappfrontapp.api.model.AccountHeaderDTO;
import org.psi.myfinappfrontapp.api2.model.FileBase64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

	@Autowired
	CustomApiApi2 customapiapi2;
	
	
	
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

	@RequestMapping(value = "/uploadBase64Files", method = RequestMethod.POST)
	public Mono<ResponseEntity<String>> loadFiles(@RequestBody List<FileBase64> list){



		return customapiapi2.uploadBase64Files(list)
                .map(result -> ResponseEntity.ok(result)) // Successful response (200 OK)
                .onErrorResume(e -> {
                    e.printStackTrace(); // Log the error
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Error during tasklet execution")); // Error response (500)
                });
		
	
	}


	@RequestMapping(value = "/getAccountTypes", method = RequestMethod.GET)
	public Mono<List<String>> getAccountTtypes(){

	
		return customapiapi.getAccounTypes();
		
	
	}

	@RequestMapping(value = "/sumdatetype/{type}/{start}/{end}", method = RequestMethod.GET)
	public Flux<AccountDateSumDTO> getSumByDateType(@PathVariable String type, @PathVariable String start, @PathVariable String end){

		LocalDate localDateStart = LocalDate.parse(start);
		
		LocalDate localDateEnd = LocalDate.parse(end);


		return customapiapi.getTypeSumByDate(type, localDateStart, localDateEnd);
		
		
	}



}