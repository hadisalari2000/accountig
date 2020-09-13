package com.salari.accounting.security;

import com.salari.accounting.configuration.ApplicationContextHolder;
import com.salari.accounting.configuration.ApplicationProperties;
import com.salari.accounting.exception.ServiceException;
import com.salari.accounting.model.entity.User;
import com.salari.accounting.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsersDetails extends ApplicationContextHolder implements UserDetailsService {

    private final UserRepository userRepository;

    public UsersDetails(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)  {
        Optional<User> user = userRepository.findUserByUserNameIgnoreCase(username);
        if (!user.isPresent()) {
            throw ServiceException.builder()
                    .key(ApplicationProperties.getProperty("invalidLogin.code"))
                    .message(ApplicationProperties.getProperty("invalidLogin.text"))
                    .httpStatus(HttpStatus.UNAUTHORIZED).build();
        }
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority(user.get().getRole().getTitle()));

        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.get().getPassword())
                .authorities(simpleGrantedAuthorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .build();
    }
}
