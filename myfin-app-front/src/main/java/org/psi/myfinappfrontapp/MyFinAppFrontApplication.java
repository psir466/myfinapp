package org.psi.myfinappfrontapp;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.psi.myfinappfrontapp.api.ApiApi;
import org.psi.myfinappfrontapp.model.AccountHeaderDTO;
import org.psi.myfinappfrontapp.model.AccountLineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

//import reactor.core.publisher.Mono;





	@SpringBootApplication
	public class MyFinAppFrontApplication implements CommandLineRunner{

		
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
	

