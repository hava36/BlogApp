package com.skillbox.blogapp.security;

import com.skillbox.blogapp.model.entity.User;
import com.skillbox.blogapp.repository.UserRepository;
import java.util.Collections;
import java.util.Locale;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserRepository userRepository;

    public DomainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);

        if (new EmailValidator().isValid(login, null)) {
            return userRepository
                .findOneByEmailIgnoreCase(login)
                .map(this::createSpringSecurityUser)
                .orElseThrow(() -> new UsernameNotFoundException(
                    "User with email " + login + " was not found in the database"));
        }

        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        return userRepository
            .findOneByName(lowercaseLogin)
            .map(this::createSpringSecurityUser)
            .orElseThrow(() -> new UsernameNotFoundException(
                "User " + lowercaseLogin + " was not found in the database"));
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(User user) {
        return new org.springframework.security.core.userdetails.User(user.getName(),
            user.getPassword(),
            Collections.singleton(new SimpleGrantedAuthority(AuthoritiesConstants.ADMIN)));
    }
}
