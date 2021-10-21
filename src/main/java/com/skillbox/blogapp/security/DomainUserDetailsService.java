package com.skillbox.blogapp.security;

import com.skillbox.blogapp.model.entity.User;
import com.skillbox.blogapp.model.entity.enums.Role;
import com.skillbox.blogapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public UserDetails loadUserByUsername(final String email) {
        log.debug("Authenticating {}", email);

        return userRepository
            .findByEmail(email)
            .map(this::createSpringSecurityUser)
            .orElseThrow(() -> new UsernameNotFoundException(
                "User " + email + " was not found in the database"));
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(User user) {
        Role role = user.getIsModerator() == 1 ? Role.MODERATOR : Role.USER;
        return new org.springframework.security.core.userdetails.User(user.getName(),
            user.getPassword(), role.getAuthorities());
    }
}
