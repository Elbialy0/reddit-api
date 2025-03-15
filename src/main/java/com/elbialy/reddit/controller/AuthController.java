package com.elbialy.reddit.controller;

import com.elbialy.reddit.Constant;
import com.elbialy.reddit.dto.LoginRequest;
import com.elbialy.reddit.dto.LoginResponse;
import com.elbialy.reddit.dto.RegisterRequest;
import com.elbialy.reddit.model.RefreshToken;
import com.elbialy.reddit.model.User;
import com.elbialy.reddit.security.JwtGenerator;
import com.elbialy.reddit.service.AuthService;
import com.elbialy.reddit.service.RefreshTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;

import static org.springframework.boot.logging.log4j2.Log4J2LoggingSystem.getEnvironment;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor

public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final RefreshTokenService refreshTokenService;
    private final Environment env;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity<>("User Registration success", HttpStatus.OK);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> accountVerification(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Verification successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) throws AccessDeniedException {
        String jwt = null;
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getUsername()
                , loginRequest.getPassword());
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);
        if (null != authenticationResponse && authenticationResponse.isAuthenticated()) {
            jwt = jwtGenerator.jwtGenerator(authenticationResponse);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken();
            return ResponseEntity.status(HttpStatus.OK).header("Authorization", jwt).
                    header("RefreshToken", refreshToken.getToken()).body("sucessfully logged in");
        } else throw new AccessDeniedException("Bad credentials");


    }

    @PostMapping("/refreshToken")
    public ResponseEntity<String> refreshToken(@RequestHeader("RefreshToken") String refreshToken, @RequestHeader("Authorization") String jwt) {
        RefreshToken refreshToken1 = refreshTokenService.getRefreshToken(refreshToken);
        if (jwt != null) {
            if (null != env && jwt.startsWith("Bearer ")) {
                try {
                    jwt = jwt.substring(7);
                    String secret = env.getProperty(Constant.JWT_SECRET, Constant.JWT_SECRET_DEFAULT_KEY);

                    SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                    if (null != secretKey) {
                        Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(jwt).getPayload();
                        String username = String.valueOf(claims.get("username"));
                        String authorities = String.valueOf(claims.get("authorities"));
                        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                        jwt =jwtGenerator.jwtGenerator(authentication);

                    }
                } catch (Exception ex) {
                    throw new BadCredentialsException("Invalid token");
                }
            }

        }
        return ResponseEntity.status(HttpStatus.OK).header("Authorization", jwt)
                .header("RefreshToken", refreshToken1.getToken()).body("sucessfully logged in");
    }
}
