package com.elbialy.reddit.service;

import com.elbialy.reddit.model.User;
import com.elbialy.reddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@AllArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("User not found with name:"+username));
        GrantedAuthority authorities = ()->"USER";
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), Collections.singleton(authorities)); //new User(user.getUsername(),user.getPassword(), authorities);
    }
}
