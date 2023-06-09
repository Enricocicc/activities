package com.enrico.activities.service.impl;

import com.enrico.activities.model.User;
import com.enrico.activities.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    //@Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final User customer = userRepository.findByEmail(email);
        if (customer == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        UserDetails user = org.springframework.security.core.userdetails.User.withUsername(customer.getEmail())
                .password(customer.getPassword())
                .authorities("USER").build();
        return user;
    }

}
