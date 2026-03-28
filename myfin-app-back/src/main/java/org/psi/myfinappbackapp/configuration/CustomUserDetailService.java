package org.psi.myfinappbackapp.configuration;

import org.psi.myfinappbackapp.repository.UserRepository;
import org.psi.myfinappbackapp.service.CustomDatabaseUserDetailsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class CustomUserDetailService {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    

     // Remplacez l'ancien UserDetailsService si vous le souhaitez, ou gardez l'InMemory pour le test
    @Bean
    @ConditionalOnProperty(name = "${auth.source}", havingValue = "memory")
    public UserDetailsService customUserDetailsService() {
        UserDetails user = User.builder()
            .username("user")
            .password(passwordEncoder().encode("password"))
            .roles("USER")
            .build();
        UserDetails admin = User.builder()
            .username("admin")
            .password(passwordEncoder().encode("adminpassword"))
            .roles("ADMIN", "USER")
            .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    // This bean is created ONLY if auth.source = db
    @Bean
    @ConditionalOnProperty(name = "${auth.source}", havingValue = "db")
    public UserDetailsService databaseUserDetailsService(UserRepository repo) {
        return new CustomDatabaseUserDetailsService(repo);
    }

}
