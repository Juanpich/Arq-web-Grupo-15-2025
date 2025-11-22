package org.example.gateway.config;



import org.example.gateway.security.jwt.JwtFilter;
import org.example.gateway.security.jwt.TokenProvider;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    public SecurityConfig( TokenProvider tokenProvider ) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);

        http.sessionManagement(s ->
                s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(authz -> authz
                .requestMatchers(HttpMethod.POST, "/authenticate").permitAll()
                /*MIcroservicio de usuario*/
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers(HttpMethod.GET, "/users").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/{id}").hasAuthority("USER")
                .requestMatchers(HttpMethod.GET, "/users/{id}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/{id}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/users/{id}").hasAuthority("USER")
                .requestMatchers(HttpMethod.POST, "/users/{id}/account/{id-account}").hasAuthority("USER")
                .requestMatchers(HttpMethod.PUT, "/users/{id}/change-type").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/usage/top-users").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/{id}/scooter/nearby/{gps}").hasAuthority("USER")
                .requestMatchers(HttpMethod.GET, "/users/email/{email}").hasAuthority("ADMIN")
                /* Microservicio de cuenta*/
                .requestMatchers(HttpMethod.GET, "/account").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/account/{id}").hasAuthority("USER")
                .requestMatchers(HttpMethod.GET, "/account/{id}/users").hasAuthority("USER")
                .requestMatchers(HttpMethod.GET, "/account/user/{id}").hasAuthority("USER")
                .requestMatchers(HttpMethod.POST, "/account").hasAuthority("USER")
                .requestMatchers(HttpMethod.PUT, "/account/{id}/load-amount").hasAuthority("USER")
                .requestMatchers(HttpMethod.PUT, "/account/{id}/change-type").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/account/{id}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/account/{id}/journey/{id-journey}" ).hasAuthority("USER")
                /*Microservicio de viaje*/
                .requestMatchers(HttpMethod.GET, "/journey").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/journey/{id}" ).hasAuthority("USER")
                .requestMatchers(HttpMethod.GET, "/journey/{id}" ).hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/journey/kmByScooter/{kmSearch}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/journey/{journeyId}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/journey").hasAuthority("USER")
                .requestMatchers(HttpMethod.GET, "/journey/scooter/{scoter_id}" ).hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/journey/user/{userId}").hasAuthority("USER")
                .requestMatchers(HttpMethod.GET, "/journey/scooter/{id}/year/{anio}").hasAuthority("ADMIN")
                /*Microservicio de rate*/
                .requestMatchers(HttpMethod.GET, "/rate").hasAuthority("USER")
                .requestMatchers(HttpMethod.GET, "/rate/{id}").hasAuthority("USER")
                .requestMatchers(HttpMethod.POST, "/rate").hasAuthority("USER")
                .requestMatchers(HttpMethod.PUT, "/rate/{id}").hasAuthority("USER")
                .requestMatchers(HttpMethod.DELETE, "/rate/{id}").hasAuthority("USER")
                .requestMatchers(HttpMethod.GET, "/rate/current").hasAuthority("USER")
                .requestMatchers(HttpMethod.GET, "/rate/byDate").hasAuthority("USER")
                /*Microservicio de scooter*/
                .requestMatchers(HttpMethod.GET, "/scooter").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/scooter/{id}").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/scooter").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/scooter/{id}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/scooter/{id}/changeState").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/scooter/{id}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/scooter/year/{anio}/countJourneys/{count}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/scooter/gps/{gps}").hasAuthority("ADMIN")
                /*microservicio de parkin*/
                .requestMatchers(HttpMethod.GET, "/parkingdock").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/parkingdock/{id}").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/parkingdock").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/parkingdock/{id}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/parkingdock/{id}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/parkingdock/{id}/scooters").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT, "/parkingdock/{id}/addscooter").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/parkingdock/{parking_id}/removescooter/{scooter_id}").hasAuthority("ADMIN")
                /*microservicio de movimiento*/
                .requestMatchers(HttpMethod.GET, "/movement").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/movement/{movementId}").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/movement/userId/{userId}").hasAuthority("USER")
                .requestMatchers(HttpMethod.GET, "/movement/accountId/{accountId}").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/movement").hasAuthority("USER")
                .requestMatchers(HttpMethod.PUT, "/movement/{movementId}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/movement/{id}").hasAuthority("ADMIN")
                /*microservicoo de mantenimineto*/
                .requestMatchers(HttpMethod.GET, "/maintenance").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/maintenance/{id}").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/maintenance/scooter/{scooterId}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/maintenance/active").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/maintenance").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/maintenance/start").hasAuthority("USER")
                .requestMatchers(HttpMethod.PUT, "/maintenance/{id}/finish").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/maintenance/{id}").hasAuthority("ADMIN")
                .anyRequest().hasAuthority("ADMIN")







                .anyRequest().authenticated()
        );

        http.addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



}
