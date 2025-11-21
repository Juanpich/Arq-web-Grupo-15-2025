package org.example.gateway.security;


import org.example.gateway.infraestructure.feign.UserFeignClient;
import org.example.gateway.security.jwt.UserPrincipal;
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
    private final UserFeignClient userFeign;

    public DomainUserDetailsService(UserFeignClient userFeign) {
        this.userFeign = userFeign;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username) {
        log.debug("Authenticating {}", username);

        return userFeign
                .findByEmail(username)
                .map(UserPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe"));
    }
}

