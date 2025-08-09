package org.psi.myfinappbackapp.configuration;

import org.psi.myfinappbackapp.filters.JwtAuthenticationFilter;
import org.psi.myfinappbackapp.service.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy; // Important pour le stateless
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration; // Pour CORS
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // Pour CORS
import org.springframework.web.filter.CorsFilter; // Pour CORS


@Configuration
@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {


     @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailsService userDetailsService; // Assurez-vous d'avoir ce bean (ex: InMemoryUserDetailsManager ou votre implémentation custom)

    // Optionnel: Un point d'entrée pour gérer les erreurs 401 (non autorisé)
    // @Autowired
    // private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider, userDetailsService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configure(http)).csrf(csrf -> csrf.disable()) // Désactiver CSRF car nous utilisons des tokens stateless. Configurer CORS.
                // .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler)) // Optionnel: gérer les erreurs 401
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Très important: session stateless
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/api/auth/**").permitAll() // Le login endpoint est public
                                //.requestMatchers("/api/accountTypes").permitAll()
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll() // Le login endpoint est public
                                .anyRequest().authenticated() // Toutes les autres requêtes nécessitent une authentification
                );

        // Ajoute notre filtre personnalisé pour la validation des JWTs avant le filtre Spring standard
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
    // Bean pour la configuration CORS
    // Ne sert pas dans mon cas le back n'est pas appelé par le navigateur. 
    //Les règles CORS servent si le des scripts du navigateur appellent des API Back dans un autre daomaine (exemple de http://localhost:4200 (Front) vers http://localhost:8080 (back)) 
   /**  @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // Important pour les cookies, les en-têtes d'autorisation, etc.
        config.addAllowedOrigin("http://localhost:4200"); // L'origine de votre application Angular
        config.addAllowedHeader("*"); // Autoriser tous les en-têtes
        config.addAllowedMethod("*"); // Autoriser toutes les méthodes (GET, POST, PUT, etc.)
        source.registerCorsConfiguration("/**", config); // Appliquer cette configuration à toutes les routes
        return new CorsFilter(source);
    }**/

}
