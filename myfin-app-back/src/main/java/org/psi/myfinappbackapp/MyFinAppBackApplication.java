package org.psi.myfinappbackapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.psi.myfinappbackapp.entities.UserEntity;
import org.psi.myfinappbackapp.market.WebClientMarket;
import org.psi.myfinappbackapp.repository.UserRepository;
import org.psi.myfinappbackapp.util.AddBDForTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import reactor.core.publisher.Mono;

@SpringBootApplication
public class MyFinAppBackApplication implements CommandLineRunner {


	private Logger logger = LoggerFactory.getLogger(MyFinAppBackApplication.class);


	@Autowired
	AddBDForTest addBDForTest;

	@Autowired
	WebClientMarket webClientMarket;

	@Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

	@Value("${ADMIN_PASSWORD:default_secure_pass}") 
    private String adminPassword;

    @Value("${ADMIN_NAME:default_secure_name}") 
    private String adminName;

	@Value("${auth.source:memory}")
	private String authSource;

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


		logger.info(f.block());

		logger.info(authSource);

		if(authSource.equals("db")){

			logger.info("Auth source is set to DB. Initializing database with default admin user if not present...");
			if (userRepository.count() == 0) {
				UserEntity admin = new UserEntity();
				admin.setUsername(adminName);
				// Use the variable from the environment!
				admin.setPassword(passwordEncoder.encode(adminPassword));
				admin.setRoles("ROLE_ADMIN");
				userRepository.save(admin);
				logger.info("DatabaseInitializer is running. Initialized default admin user.");
        	}
		} else {
			logger.info("Auth source is set to MEMORY. Using in-memory authentication.");
		}

	}

}
