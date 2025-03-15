package com.elbialy.reddit.security;

import com.elbialy.reddit.model.RefreshToken;
import com.elbialy.reddit.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class JwtChecker {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    public  Boolean validateJwt(String token) {
        Optional<RefreshToken> refreshToken = Optional.ofNullable(refreshTokenRepository.findByToken(token));
        return refreshToken.isPresent();
    }
}
