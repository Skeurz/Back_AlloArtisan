package com.projet.artisan;

import com.projet.artisan.models.AppRole;
import com.projet.artisan.models.AppUser;
import com.projet.artisan.services.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@EntityScan("com.projet.artisan.models")
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)


public class ArtisanApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(ArtisanApplication.class, args);

	}

	public static void deleteUser(long id) {

	}


	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*@Bean
	CommandLineRunner start(AccountService accountService) {

		return args -> {
			accountService.addRole(new AppRole(null, "Admin"));
			accountService.addRole(new AppRole(null, "Client"));
			accountService.addRole(new AppRole(null, "Artisan"));

	   accountService.addUser(new AppUser(null,"latifa","latifa","latifa","lhafidi@gmail.com","latifa123", new ArrayList<>()));
	   accountService.addUser(new AppUser(null,"soufiane","lhafidi","soufiane","mahzinho59@gmail.com","ahmed2022", new ArrayList<>()));
	    accountService.addUser(new AppUser(null,"zahou","anas","anas","anas@gmail.com","123", new ArrayList<>()));
	   accountService.addUser(new AppUser(null,"Elkhoudri","fadoua","fadoua","fadoua@gmail.com","123", new ArrayList<>()));

		//accountService.addRoleToUser("latifa", "admin");
	 //   accountService.addRoleToUser("anas", "admin");
		//accountService.addRoleToUser("soufiane","client");
	    //accountService.addRoleToUser("fadoua", "admin");



};
		};*/
	}



