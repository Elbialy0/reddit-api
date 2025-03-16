package com.elbialy.reddit.service;

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




}
