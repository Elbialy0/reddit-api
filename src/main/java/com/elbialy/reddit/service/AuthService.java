package com.elbialy.reddit.service;

import com.elbialy.reddit.dto.LoginRequest;
import com.elbialy.reddit.dto.LoginResponse;
import com.elbialy.reddit.dto.RegisterRequest;
import com.elbialy.reddit.dto.UserDto;
import com.elbialy.reddit.exceptions.SpringRedditException;
import com.elbialy.reddit.exceptions.UserNotFoundException;
import com.elbialy.reddit.model.NotificationEmail;
import com.elbialy.reddit.model.RefreshToken;
import com.elbialy.reddit.model.User;
import com.elbialy.reddit.model.VerificationToken;
import com.elbialy.reddit.repository.RefreshTokenRepository;
import com.elbialy.reddit.repository.UserRepository;
import com.elbialy.reddit.repository.VerificationTokenRepository;
import com.elbialy.reddit.security.JwtGenerator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private  final RefreshTokenRepository refreshTokenRepository;
    private final JwtGenerator jwtGenerator;
    private final AuthenticationManager authenticationManager;
    private final  RefreshTokenService refreshTokenService;
    @Transactional
    public void signup(RegisterRequest registerRequest){
        User user = new User();
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new SpringRedditException("This user is exists!!");
        }
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        userRepository.save(user);
       String token = generateVerificationToken(user);
       mailService.sendEmail(new NotificationEmail("Please Activate your account",user.getEmail(),"thank you "+
               "http://localhost:8080/api/auth/accountVerification/"+token));
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public UserDto verifyAccount(String token) {
      Optional<VerificationToken> actualToken = verificationTokenRepository.findVerificationTokenByToken(token);
        actualToken.orElseThrow(()->new SpringRedditException("Invalid token!"));
       User user =  fetchUser(actualToken);
       UserDto userDto = new UserDto(user.getUsername(), user.getEmail());
       return userDto;


    }
@Transactional
    public User fetchUser(Optional<VerificationToken> actualToken) {
        User user = userRepository.findById(actualToken.get().getUser().getId())
                .orElseThrow(()->new UserNotFoundException("User not found with name:"));
        user.setEnabled(true);
        log.info("User {} is enabled",user.isEnabled());
        userRepository.save(user);
        return user;

    }
    @Transactional
    public void  saveJwt(String token) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setCreatedDate(Instant.now());
        refreshTokenRepository.save(refreshToken);
    }
    public User getCurrentUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(()->new UserNotFoundException("User not found"));
    }
    public LoginResponse login(LoginRequest loginRequest) throws AccessDeniedException {
        String jwt = "";
        Authentication authentication = UsernamePasswordAuthenticationToken
                .unauthenticated(loginRequest.getUsername(),loginRequest.getPassword());
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);
        if(null!=authenticationResponse && authenticationResponse.isAuthenticated()){
            jwt = jwtGenerator.jwtGenerator(authenticationResponse);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken();
        }
        else throw new BadCredentialsException("Bad credentials");
        return new LoginResponse(jwt, HttpStatus.OK.toString());

    }




}
