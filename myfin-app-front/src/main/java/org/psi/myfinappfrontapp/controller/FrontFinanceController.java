package org.psi.myfinappfrontapp.controller;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import org.psi.myfinappfrontapp.custom.CustomApiApi;
import org.psi.myfinappfrontapp.custom.CustomApiApi2;
import org.psi.myfinappfrontapp.custom.CustomSecurityControllerApi;
import org.slf4j.LoggerFactory;
import org.psi.myfinappfrontapp.api.api.SecurityControllerApi;
import org.psi.myfinappfrontapp.api.model.AccountDateSumDTO;
import org.psi.myfinappfrontapp.api.model.AccountHeaderDTO;
import org.psi.myfinappfrontapp.api.model.LoginRequest;
import org.psi.myfinappfrontapp.api.model.MarketDTO;
import org.psi.myfinappfrontapp.api.model.MarketDTODetail;
import org.psi.myfinappfrontapp.api2.model.FileBase64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/backfront")
public class FrontFinanceController {
	
	@Autowired
	CustomApiApi customapiapi;

	@Autowired
	CustomApiApi2 customapiapi2;

	@Autowired
	CustomSecurityControllerApi customSecurityControllerApi;

	private static final Logger logger = LoggerFactory.getLogger(FrontFinanceController.class);
	
	

	@RequestMapping(value = "/markets/{code}/{start}/{end}", method = RequestMethod.GET)
	public List<MarketDTODetail> getMarketByCode(@PathVariable String code, @PathVariable String start, @PathVariable String end, @RequestHeader("Authorization") String authorizationHeader ){
			
		LocalDate localDateStart = LocalDate.parse(start);
		
		LocalDate localDateEnd = LocalDate.parse(end);

		logger.info("base ref : " + customapiapi2.getApiClient().getBasePath());

		customapiapi.getApiClient().setBearerToken(authorizationHeader.substring(7));

		MarketDTO marketDTO = customapiapi.getMarketByCode(code, localDateStart, localDateEnd).block();

		return marketDTO.getMarkets();
		
	}

	
	@RequestMapping(value = "/accounts", method = RequestMethod.GET)
	public Flux<AccountHeaderDTO> getAllAccount(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader, @RequestHeader("Authorization") String authorizationHeader2){

		customapiapi.getApiClient().setBearerToken(authorizationHeader.substring(7));
				
		return customapiapi.getAllAccount();
		
	}
	
	
	@RequestMapping(value = "/account/{id}", method = RequestMethod.GET)
	public Mono<AccountHeaderDTO> getAccountById(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader){
		
		customapiapi.getApiClient().setBearerToken(authorizationHeader.substring(7));
	
		return customapiapi.getAccountById(id);
		
		
	}

	@RequestMapping(value = "/sumdate/{start}/{end}", method = RequestMethod.GET)
	public Flux<AccountDateSumDTO> getSumByDate(@PathVariable String start, @PathVariable String end, @RequestHeader("Authorization") String authorizationHeader){

		LocalDate localDateStart = LocalDate.parse(start);
		
		LocalDate localDateEnd = LocalDate.parse(end);

		customapiapi.getApiClient().setBearerToken(authorizationHeader.substring(7));

		return customapiapi.getSumByDate(localDateStart, localDateEnd);
		
		
	}

	@RequestMapping(value = "/uploadBase64Files", method = RequestMethod.POST)
	public Mono<ResponseEntity<String>> loadFiles(@RequestBody List<FileBase64> list, @RequestHeader("Authorization") String authorizationHeader){

	String token = authorizationHeader.substring(7);

	logger.info("le token pour batch:" + token);

	return customapiapi2.uploadBase64Files(token, list)
                .map(result -> ResponseEntity.ok(result)) // Successful response (200 OK)
                .onErrorResume(e -> {
                    e.printStackTrace(); // Log the error
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Error during tasklet execution")); // Error response (500)
                });
		
	}


	@RequestMapping(value = "/getAccountTypes", method = RequestMethod.GET)
	public Mono<List<String>> getAccountTtypes(@RequestHeader("Authorization") String authorizationHeader){

		logger.info("recup token dans account type :" + authorizationHeader);

		customapiapi.getApiClient().setBearerToken(authorizationHeader.substring(7));
	
		return customapiapi.getAccounTypes();
		
	
	}

	@RequestMapping(value = "/sumdatetype/{type}/{start}/{end}", method = RequestMethod.GET)
	public Flux<AccountDateSumDTO> getSumByDateType(@PathVariable String type, @PathVariable String start, @PathVariable String end, @RequestHeader("Authorization") String authorizationHeader){

		LocalDate localDateStart = LocalDate.parse(start);
		
		LocalDate localDateEnd = LocalDate.parse(end);

		
		customapiapi.getApiClient().setBearerToken(authorizationHeader.substring(7));

		return customapiapi.getTypeSumByDate(type, localDateStart, localDateEnd);
		
		
	}


	@RequestMapping(value = "/login", method = RequestMethod.POST)
    public Mono<Object> authenticateUser(@RequestBody LoginRequest loginRequest) {

		Flux<AccountHeaderDTO> f = customapiapi.getAllAccount();

		logger.info("*****************" + f + "*************** TEST ***************");

		customSecurityControllerApi.authenticateUser(loginRequest);

        return customSecurityControllerApi.authenticateUser(loginRequest);
    }


}