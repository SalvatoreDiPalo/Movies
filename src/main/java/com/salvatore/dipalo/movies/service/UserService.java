package com.salvatore.dipalo.movies.service;

import com.salvatore.dipalo.movies.exception.DuplicateResourseException;
import com.salvatore.dipalo.movies.model.RegistrationRequest;
import com.salvatore.dipalo.movies.model.db.User;
import com.salvatore.dipalo.movies.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not findUser with username = " + username));
        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))); // Sets the role of the found user
    }

    public User getUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not findUser with username = " + username));
    }

    public User createUser(RegistrationRequest request) {
        log.info("Creating user with username: {}", request.getUsername());
        User duplicateUser = userRepository.findByUsernameIgnoreCaseOrEmailIgnoreCase(request.getUsername(), request.getEmail())
                .orElse(null);

        if (duplicateUser != null) {
            log.error("Error creating an user. There is already a user with the same username or email");
            throw new DuplicateResourseException();
        }

        String encodedPass = passwordEncoder.encode(request.getPassword());

        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(encodedPass);

        user.setCreateDate(LocalDateTime.now());

        userRepository.save(user);
        return user;
    }

}