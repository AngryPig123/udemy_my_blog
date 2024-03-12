package org.example.rest_practice.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.rest_practice.entity.User;
import org.example.rest_practice.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User findUser = userRepository.findUserByEmail(email).orElseThrow(
                () -> new BadCredentialsException(String.format("User not found with email : %s", email))
        );

        log.info("user email = {}", findUser.getEmail());
        log.info("user roles = {}", findUser.getAuthorities().toArray());

        return findUser;

    }

}
