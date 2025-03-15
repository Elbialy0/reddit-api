package com.elbialy.reddit.service;

import com.elbialy.reddit.dto.RegisterRequest;
import com.elbialy.reddit.exceptions.SpringRedditException;
import com.elbialy.reddit.model.NotificationEmail;
import com.elbialy.reddit.model.RefreshToken;
import com.elbialy.reddit.model.User;
import com.elbialy.reddit.model.VerificationToken;
import com.elbialy.reddit.repository.RefreshTokenRepository;
import com.elbialy.reddit.repository.UserRepository;
import com.elbialy.reddit.repository.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    @Transactional
    public void signup(RegisterRequest registerRequest){
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);
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

    public void verifyAccount(String token) {
      Optional<VerificationToken> actualToken = verificationTokenRepository.findVerificationTokenByToken(token);
        actualToken.orElseThrow(()->new SpringRedditException("Invalid token!"));
        fetchUser(actualToken);

    }
@Transactional
    public void fetchUser(Optional<VerificationToken> actualToken) {
        String username = actualToken.get().getUser().getUsername();
        User user = userRepository.findUserByUsername(username).orElseThrow(()->new SpringRedditException("User not found with name:"+username));
        user.setEnabled(true);
        log.info("User {} is enabled",user.getUsername());
        userRepository.save(user);

    }
    @Transactional
    public void  saveJwt(String token) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setCreatedDate(Instant.now());
        refreshTokenRepository.save(refreshToken);
    }




}
