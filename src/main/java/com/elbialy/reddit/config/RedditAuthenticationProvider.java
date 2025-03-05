package com.elbialy.reddit.config;


import com.elbialy.reddit.security.JwtGenerator;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RedditAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password =  authentication.getCredentials().toString();
        UserDetails user =  userDetailsService.loadUserByUsername(username);
        if (passwordEncoder.matches(password,user.getPassword())){
            return new UsernamePasswordAuthenticationToken(username,password,user.getAuthorities());
        }
        else throw new BadCredentialsException("Bad credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
       return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
