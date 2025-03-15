package com.elbialy.reddit.security;

import com.elbialy.reddit.Constant;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtGenerator {
    @Value("${jwt.expiration.time}")
    private long expirationTime;

    @Autowired
    private  Environment env;
    public String jwtGenerator(Authentication authentication) {
        String jwt = null;
        if (null!=env){
            String secret = env.getProperty(Constant.JWT_SECRET,
                    Constant.JWT_SECRET_DEFAULT_KEY);// return the default if the JWT_SECRET_KEY not found
            SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); //The Keys.hmacShaKeyFor method does not hash the key. Instead, it creates a SecretKey object that is suitable for use with HMAC-SHA (Hash-based Message Authentication Code using SHA) algorithms. Specifically:
//It takes the byte array of your secret string.
//
//It wraps it into a SecretKey object that can be used for signing and verifying JWTs using HMAC-SHA algorithms (e.g., HS256, HS384, HS512).

             jwt = Jwts.builder().issuer("Eazy Bank").subject("JWT Token")
                    .claim("username", authentication.getName())
                    .claim("authorities",authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                    .issuedAt(new Date())
                     .expiration(Date.from(Instant.now().plusMillis(expirationTime)))
                    .signWith(secretKey).compact();


        }


return jwt;
    }
}
