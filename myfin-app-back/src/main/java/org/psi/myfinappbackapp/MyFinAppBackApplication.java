package org.psi.myfinappbackapp;

import org.psi.myfinappbackapp.market.WebClientMarket;
import org.psi.myfinappbackapp.util.AddBDForTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.core.publisher.Mono;

@SpringBootApplication
public class MyFinAppBackApplication implements CommandLineRunner {

	@Autowired
	AddBDForTest addBDForTest;

	@Autowired
	WebClientMarket webClientMarket;

	public static void main(String[] args) {
		SpringApplication.run(MyFinAppBackApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

		//addBDForTest.deleteAll();

		//addBDForTest.addBD();

		//addBDForTest.readAcc();
		
		//addBDForTest.testSum();

		Mono<String> f= webClientMarket.fetchDataMarket("^GSPC")
				.map(response -> "Réponse de l'URL : " + response)
                .onErrorResume(e -> Mono.just("Erreur lors de la récupération des données : " + e.getMessage()));;


		System.out.println(f.block());

	}

}
