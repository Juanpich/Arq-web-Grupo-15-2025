package org.example.gateway.security;


import org.example.gateway.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserRepository userRepository;

    public DomainUserDetailsService( UserRepository userRepository ) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username ) {
        log.debug("Authenticating {}", username);

        //final var user = this.userRepository.findOneWithAuthoritiesByUsernameIgnoreCase( username ).orElseThrow();
        //return this.createSpringSecurityUser( user );

        return userRepository
            .findOneWithAuthoritiesByUsernameIgnoreCase( username.toLowerCase() )
            .map( this::createSpringSecurityUser )
            .orElseThrow( () -> new UsernameNotFoundException( "El usuario " + username + " no existe" ) );
    }

    private UserDetails createSpringSecurityUser( User user ) {
        return new org.springframework.security.core.userdetails.User( user );
    }

}
