package org.psi.myfinappfrontapp;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.psi.myfinappfrontapp.api.api.ApiApi;
import org.psi.myfinappfrontapp.api.model.AccountHeaderDTO;
import org.psi.myfinappfrontapp.api.model.AccountLineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import reactor.core.publisher.Mono;

//import reactor.core.publisher.Mono;





	@SpringBootApplication
	@EnableScheduling
	public class MyFinAppFrontApplication implements CommandLineRunner{

		@Value("${APP_TEST:testNonTrouve}")
    	private String monTest;

		
		/*@Bean
	    public ApiApi orderApi(WebClient.Builder webClientBuilder) {
	        String basePath = "http://localhost:8080"; // Your API base URL
	        WebClient webClient = webClientBuilder.baseUrl(basePath).build();
	        return new AccountService(webClient);
	    }*/

		public static void main(String[] args) {
			SpringApplication.run(MyFinAppFrontApplication.class, args);
		}

		@Override
		public void run(String... args) throws Exception {
			// TODO Auto-generated method stub

			if(monTest == null){

				System.out.println("monTest est null");

			}else{
				
				System.out.println("valur de monTest : " + monTest);

			}
			
			ApiApi api = new ApiApi();
			
			
			
			Mono<AccountHeaderDTO> ac = api.getAccountById(Long.valueOf(1l));
			
			ac.subscribe(
				    accountHeaderDTO -> {
				        // Use the accountHeaderDTO object here (this code runs when the Mono emits)
				        System.out.println(accountHeaderDTO.getId() + " " + accountHeaderDTO.getAccountType() + " " + accountHeaderDTO.getName() + " " + accountHeaderDTO.getAccountLines() );
				        
				        List<AccountLineDTO> lines = accountHeaderDTO.getAccountLines();
				        
				        for(AccountLineDTO line : lines) {
				        	
				        	System.out.println(line.getDate() + " " + line.getSolde().getAmount() + " " + line.getSolde().getCurrency());
				        	
				        }
				        
				  
				        
				    },
				    error -> {
				        // Handle any errors that occurred during the Mono's execution
				        System.err.println("Error: " + error.getMessage());
				    },
				    () -> {
				        // This code runs when the Mono completes (no more values will be emitted)
				        System.out.println("Mono completed.");
				    }
				);
			
			
			
		}

		
	}
	

