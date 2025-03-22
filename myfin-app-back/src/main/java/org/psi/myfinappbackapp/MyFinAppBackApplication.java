package org.psi.myfinappbackapp;

import org.psi.myfinappbackapp.util.AddBDForTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyFinAppBackApplication implements CommandLineRunner {

	@Autowired
	AddBDForTest addBDForTest;

	public static void main(String[] args) {
		SpringApplication.run(MyFinAppBackApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

		addBDForTest.deleteAll();

		//addBDForTest.addBD();

		addBDForTest.readAcc();
		
		//addBDForTest.testSum();

	}

}
